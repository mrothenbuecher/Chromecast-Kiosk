function launch(ip, name) {
	$.post("rest/start/" + ip, {
		url : $("#sender").find('#url').val(),
		reload : $("#sender").find('#refresh').val(),
	}).done(function() {
		toastr['info']("Started on " + name + " successfully");
	}).fail(function() {
		toastr['error']("Error on " + name);
	});
}

$(document).ready(function() {
	$('#launch').click(function() {
		if (!$("#sender").find('#url').val()) {
			toastr['warning']("Please insert url");
		} else {
			var all = $("#sender").find('#all').prop("checked");
			if (all) {
				$("#sender").find('#receiver-ip').find('option').each(function() {
					$this = $(this);
					if($this.data('default') == true)
						launch($this.val(), $this.text());
				});
			} else {
				var $option = $("#sender").find('#receiver-ip').find('option:selected');
				if ($option) {
					launch($option.val(), $option.text());
				} else {
					toastr['warning']("Please select chromecast");
				}

			}
		}
	});
});