(function() {
    $(document).ready(function() {
    	$('#media-upload').click(function(e){
    		if($('#media-file')[0].files && $('#media-file')[0].files.length > 0){
    			var data;

    		    data = new FormData();
    		    data.append( 'file', $('#media-file')[0].files[0] );

    		    $.ajax({
    		        url: 'media',
    		        data: data,
    		        cache: false,
    		        contentType: false,
    		        processData: false,
    		        type: 'POST',
    		        success: function(data){
    		            toastr['info']($.i18n("media_upload_success"));
    		        }
    		    });

    		    e.preventDefault();
    		}
    	});
    	$('#media-refresh').click(function(e){
    		$('.media-list').html("");
    		$('.media-list').append("<option></option>");
    		$.ajax({
                type: "GET",
                url: "rest/media/list"
            }).done(function(data, textStatus, jqXHR) {
                var obj = JSON.parse(data);
                $.each(obj, function(i, elem) {
                	$('.media-list').append("<option>"+elem+"</option>");
                });
            }).fail(function(jqXHR, textStatus, errorThrown) {
                // TODO error
                toastr['error']("error");
            }).always(function() {

            });
    	});
    	$('#media-delete').click(function(e){
    		var option = $('#media-del-list option:selected');
    		if(option && option.text()){
    			$.ajax({
                    type: "DELETE",
                    url: "rest/media/"+encodeURI(option.text())
                }).done(function(data, textStatus, jqXHR) {
                	//TODO success
                	toastr['info']($.i18n("media_delete_success"));
                }).fail(function(jqXHR, textStatus, errorThrown) {
                    // TODO error
                    toastr['error']("error");
                }).always(function() {
                	$('#media-refresh').trigger("click");
                });
    		}
    	});
    });
})();
