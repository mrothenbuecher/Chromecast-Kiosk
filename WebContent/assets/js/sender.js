function launch(ip, name) {
	$.post("rest/start/" + ip, {
		url : $('#url').val(),
		reload : $('#refresh').val(),
	}).done(function() {
		toastr['info']("Übertragung auf " + name + " erfolgreich gestartet");
	}).fail(function() {
		toastr['error']("Übertragung auf " + name + " fehlgeschlagen");
	});
}

$(document).ready(function() {

	$('#launch').click(function() {
		if (!$('#url').val()) {
			toastr['warning']("Es muss eine URL angegeben werden");
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
					toastr['warning']("Es muss ein empfänger ausgewählt werden");
				}

			}
		}
	});
});