$(document).ready(function() {

	$('#add').click(function() {
		if (!$('#cron').find('#url').val()) {
			toastr['warning']("Please insert url");
		}else if (!$('#cron').find('#pattern').val()) {
			toastr['warning']("Please insert pattern");
		}else if (!$('#cron').find('#name').val()) {
			toastr['warning']("Please insert name");
		}else {
				var $option = $('#cron').find('#receiver-ip').find('option:selected');
				if ($option) {
					$.post("rest/cron/add/" + $option.val(), {
						url : $('#cron').find('#url').val(),
						reload : $('#cron').find('#refresh').val(),
						pattern : $('#cron').find('#pattern').val(),
						name : $('#cron').find('#name').val()
					}).done(function() {
						toastr['info']("added cronjob for " + $option.text() + " successfully");
					}).fail(function() {
						toastr['error']("error on " + $option.text());
					});
				} else {
					toastr['warning']("Please select chromecast");
				}

		}
	});
	
	$('#refreshjobs').click(function(ev){
		ev.preventDefault();
		var $cjobs = $('#cron').find('#currentjobs');
		$cjobs.html("");
		$.ajax({
			type : "GET",
			url : "rest/cron/get"
		}).done(function(data, textStatus, jqXHR) {
			var obj = JSON.parse(data);
			$.each(obj, function(i, elem){
				var row = '<div class="col-xs-12"><div class="col-xs-2">';
				row += elem.name;
				row+="</div>";
				row +='<div class="col-xs-4">';
				row += elem.url;
				row+="</div>";
				row +='<div class="col-xs-2">';
				row += elem.desc+" ("+elem.pattern+")";
				row+="</div>";
				row +='<div class="col-xs-2">';
				row += '<button data-target="'+elem.target+'" data-name="'+elem.name+'" class="btn btn-default del-cron">delete</button>';
				row+="</div></div>";
				$cjobs.append(row);
			});
			
			$('.del-cron').click(function(ev){
				ev.preventDefault();
				var name = $(this).data("name");
				var target = $(this).data("target");
				var $this = $(this);
				$.ajax({
					type : "DELETE",
					url : "rest/cron/remove/"+target+"/"+name
				}).done(function(data, textStatus, jqXHR) {
					toastr['info']("deleted "+name);
					$this.parent().parent().hide();
				}).fail(function(jqXHR, textStatus, errorThrown) {
					toastr['error']("error");
				});
			});
			
		}).fail(function(jqXHR, textStatus, errorThrown) {
			// TODO error
			toastr['error']("error");
		}).always(function() {
			
		});
	});
});