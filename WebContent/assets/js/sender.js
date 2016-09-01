function launch(ip, name) {
	$.post("rest/start/" + ip, {
		url : $('#url').val(),
		reload : $('#refresh').val(),
	}).done(function() {
		toastr['info']("Started on " + name + " successfully");
	}).fail(function() {
		toastr['error']("Error on " + name);
	});
}

$(document).ready(function() {

	$('#launch').click(function() {
		if (!$('#url').val()) {
			toastr['warning']("Please insert url");
		} else {
			var all = $('#all').prop("checked");
			if (all) {
				$('#receiver-ip').find('option').each(function() {
					$this = $(this);
					launch($this.val(), $this.text());
				});
			} else {
				var $option = $('#receiver-ip').find('option:selected');
				if ($option) {
					launch($option.val(), $option.text());
				} else {
					toastr['warning']("Please select chromecast");
				}

			}
		}
	});
});