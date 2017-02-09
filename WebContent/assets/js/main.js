(function() {
    var spinner_opts = {
        lines: 10 // The number of lines to draw
            ,
        length: 0 // The length of each line
            ,
        width: 6 // The line thickness
            ,
        radius: 10 // The radius of the inner circle
            ,
        scale: 1 // Scales overall size of the spinner
            ,
        corners: 1 // Corner roundness (0..1)
            ,
        color: '#fff' // #rgb or #rrggbb or array of colors
            ,
        opacity: 0 // Opacity of the lines
            ,
        rotate: 0 // The rotation offset
            ,
        direction: 1 // 1: clockwise, -1: counterclockwise
            ,
        speed: 2 // Rounds per second
            ,
        trail: 100 // Afterglow percentage
            ,
        fps: 20 // Frames per second when using setTimeout() as a fallback for
            // CSS
            ,
        zIndex: 2e9 // The z-index (defaults to 2000000000)
            ,
        className: 'spinner' // The CSS class to assign to the spinner
            ,
        top: '50%' // Top position relative to parent
            ,
        left: '50%' // Left position relative to parent
            ,
        shadow: true // Whether to render a shadow
            ,
        hwaccel: true // Whether to use hardware acceleration
            ,
        position: 'absolute' // Element positioning
    }

    function requestStatus(container) {
        var spinner = new Spinner(spinner_opts).spin();
        var status = container.find('p.status');
        var application = container.find('p.application');

        status.removeClass("alert alert-success");
        status.removeClass("alert alert-danger");

        application.removeClass("alert alert-success");
        application.removeClass("alert alert-warning");
        application.removeClass("alert alert-danger");

        status.after(spinner.el);
        status.html("");
        application.html("");
        $.ajax({
            type: "GET",
            url: "rest/status/get/" + container.data('ip'),
            beforeSend: function() {
                container.find(".panelrefresh").attr("disabled", "disabled");
            }
        }).done(function(data, textStatus, jqXHR) {
            var obj = JSON.parse(data);
            status.html(obj['status']);
            if (obj['status'] === "online") {
                status.addClass("alert alert-success");
                application.addClass("alert alert-success");
                // the Backdrop shall not be default ...
                if (obj['application'] === "Backdrop")
                    application.addClass("alert alert-warning");
                application.html(obj['application']);
                if (obj['application'] == "chromecast-kiosk-web" && obj['application']) {
                    application.html('<a href="' + obj['url'] + '" target="_blank">' + obj['application'] + '</a>');
                }
            } else {
                status.addClass("alert alert-danger");
                application.addClass("alert alert-danger");
                application.html("-");
            }
        }).fail(function(jqXHR, textStatus, errorThrown) {
            // TODO error
            console.log("error");
        }).always(function() {
            spinner.stop();
            container.find(".panelrefresh").removeAttr("disabled", "disabled");
        });
    }

    function requestEach() {
        $('.chromecastpanel').each(function() {
            var attr = $(this).find(".panelrefresh").attr('disabled');
            if (typeof attr !== typeof undefined && attr !== false) {
                // if its disabled do nothing
            } else {
                requestStatus($(this));
            }
        });
    }

    function addCast(ip, name) {
        $.ajax({
            type: "POST",
            url: "rest/settings/add/" + ip + "/" + name
        }).done(function(data, textStatus, jqXHR) {
            var obj = JSON.parse(data);
            if (obj['error']) {
                toastr['warning'](obj['error']);
                console.log("error:" + obj['error']);
            } else {
                location.reload();
            }
        }).fail(function(jqXHR, textStatus, errorThrown) {
            console.log("error");
        }).always(function() {

        });
    }

    $(document).ready(function() {
        requestEach();

        $('#addcastbutton').click(function(ev) {
            ev.preventDefault();
            var ip = $('#addip').val();
            var name = $('#addname').val();
            if (ip && name) {
                $('#addip').val("");
                $('#addname').val("");
                addCast(ip, name);
            }
        });

        $('.paneltrash').click(
            function(ev) {
                ev.preventDefault();
                $(this).parent().parent().parent().find(
                    '.trashmsg').slideToggle();
            });

        $('a.option-info').click(function(ev) {
            ev.preventDefault();
            var ip = $(this).data("ip");
            if (ip) {
                $.ajax({
                    type: "POST",
                    url: "rest/settings/update/" + ip + "/false"
                }).done(function(data, textStatus, jqXHR) {
                    var obj = JSON.parse(data);
                    if (obj['error']) {
                        toastr['warning'](obj['error']);
                    } else {
                        toastr['info']("change done");
                    }
                }).fail(function(jqXHR, textStatus, errorThrown) {
                    console.log("error");
                }).always(function() {

                });
            }
        });

        $('a.option-default').click(function(ev) {
            console.log("grrr");
            ev.preventDefault();
            var ip = $(this).data("ip");
            if (ip) {
                $.ajax({
                    type: "POST",
                    url: "rest/settings/update/" + ip + "/true"
                }).done(function(data, textStatus, jqXHR) {
                    var obj = JSON.parse(data);
                    if (obj['error']) {
                        toastr['warning'](obj['error']);
                    } else {
                        //location.reload();
                        toastr['info']("change done");
                    }
                }).fail(function(jqXHR, textStatus, errorThrown) {
                    console.log("error");
                }).always(function() {

                });
            }
        });

        $('.notrash').click(function(ev) {
            ev.preventDefault();
            $(this).parent().parent().slideToggle();
        });

        $('.yestrash').click(function(ev) {
            ev.preventDefault();
            var ip = $(this).data("ip");
            if (ip) {
                $.ajax({
                    type: "DELETE",
                    url: "rest/settings/remove/" + ip
                }).done(function(data, textStatus, jqXHR) {
                    var obj = JSON.parse(data);
                    if (obj['error']) {
                        toastr['warning'](obj['error']);
                    } else {
                        location.reload();
                    }
                }).fail(function(jqXHR, textStatus, errorThrown) {
                    console.log("error");
                }).always(function() {

                });
            }
        });

        $('.panelrefresh').click(
            function(ev) {
                ev.preventDefault();
                requestStatus($(this).parent().parent()
                    .parent().parent());
            });

        $('#refreshall').click(function(ev) {
            ev.preventDefault();
            requestEach();
        });

        $('#searchcasts').click(function(ev) {
            // ev.preventDefault();

            $.ajax({
                    type: "GET",
                    url: "rest/discovered/get",
                    beforeSend: function() {
                        $('#discoverchromecast').find('.modal-body').html("");
                    }
                }).done(function(data, textStatus, jqXHR) {
                    var obj = JSON.parse(data);
                    $.each(obj, function(i, elem) {
                        console.log(elem);
                        var row = '<div class="row"><div class="col-xs-12">';
                        row += '<button class="btn btn-default discover-add" data-name="' +
                            elem.name +
                            '" data-ip="' +
                            elem.ip +
                            '">'+$.i18n( 'main_discover_add' )+' ' +
                            elem.name +
                            " " +
                            elem.ip +
                            '</button>';
                        row += "</div></div>"
                        $('#discoverchromecast').find('.modal-body').append(row);
                    });
                    $('.discover-add').click(function(ev) {
                        ev.preventDefault();
                        addCast($(this).data("ip"),
                            $(this).data("name"));
                    });
                })
                .fail(function(jqXHR, textStatus, errorThrown) {
                    // TODO error
                    consolelog("error");
                });
        });

        $('#senderbutton').click(function(ev) {
            ev.preventDefault();
            $(this).parent().addClass("active");
            $('#main').slideUp();
            $('#help').slideUp();
            $('#cron').slideUp();
            $('#sender').slideDown();
            $('#mainbutton').parent().removeClass("active");
            $('#cronbutton').parent().removeClass("active");
            $('#helpbutton').parent().removeClass("active");
        });

        $('#mainbutton').click(function(ev) {
            ev.preventDefault();
            $(this).parent().addClass("active");
            $('#sender').slideUp();
            $('#help').slideUp();
            $('#cron').slideUp();
            $('#main').slideDown();
            $('#senderbutton').parent().removeClass("active");
            $('#cronbutton').parent().removeClass("active");
            $('#helpbutton').parent().removeClass("active");
        });

        $('#helpbutton').click(function(ev) {
            ev.preventDefault();
            $(this).parent().addClass("active");
            $('#main').slideUp();
            $('#sender').slideUp();
            $('#cron').slideUp();
            $('#help').slideDown();
            $('#senderbutton').parent().removeClass("active");
            $('#cronbutton').parent().removeClass("active");
            $('#mainbutton').parent().removeClass("active");
        });

        $('#cronbutton').click(function(ev) {
            ev.preventDefault();
            $(this).parent().addClass("active");
            $('#help').slideUp();
            $('#sender').slideUp();
            $('#main').slideUp();
            $('#cron').slideDown();
            $('#senderbutton').parent().removeClass("active");
            $('#helpbutton').parent().removeClass("active");
            $('#mainbutton').parent().removeClass("active");
            $('#refreshjobs').trigger('click');
        });

    });
})();
