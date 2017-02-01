/**
 * Main JavaScript for handling Chromecast interactions.
 */

window.onload = function() {
  cast.receiver.logger.setLevelValue(0);
  window.castReceiverManager = cast.receiver.CastReceiverManager.getInstance();
  console.log('Starting Receiver Manager');

  castReceiverManager.onReady = function(event) {
    console.log('Received Ready event: ' + JSON.stringify(event.data));
    window.castReceiverManager.setApplicationState('chromecast-kiosk-web is ready...');
  };

  castReceiverManager.onSenderConnected = function(event) {
    console.log('Received Sender Connected event: ' + event.senderId);
  };

  castReceiverManager.onSenderDisconnected = function(event) {
    console.log('Received Sender Disconnected event: ' + event.senderId);
  };

  window.messageBus =
    window.castReceiverManager.getCastMessageBus(
        'urn:x-cast:de.michaelkuerbis.kiosk', cast.receiver.CastMessageBus.MessageType.JSON);

  var current = {};
  
  window.messageBus.onMessage = function(event) {
    console.log('Message [' + event.senderId + ']: ', event.data);

    if (event.data['type'] == 'load') {
    	current.url = event.data['url'];
    	current.refresh = event.data['refresh'];
      $('#dashboard').attr('src', event.data['url']);
      if (event.data['refresh'] > 0) {
        $('#dashboard').attr('data-refresh', event.data['refresh'] * 1000);
        setTimeout(reloadDashboard, $('#dashboard').attr('data-refresh'));
      }
      else {
        $('#dashboard').attr('data-refresh', 0);
      }
    }
    // return Information
    if(event.data.status){
    	current.requestId = event.data.requestId;
    	console.log("getstatus",'Message [' + event.senderId + ']: ', event.data);
    	console.log("response", current);
    	window.messageBus.send(event.senderId, current);
    }
  }

  // Initialize the CastReceiverManager with an application status message.
  window.castReceiverManager.start({statusText: 'Application is starting'});
  console.log('Receiver Manager started');

  function reloadDashboard() {
    $('#dashboard').attr('src', $('#dashboard').attr('src'));
    if ($('#dashboard').attr('data-refresh')) {
      setTimeout(reloadDashboard, $('#dashboard').attr('data-refresh'));
    }
  }

  $('#dashboard').load(function() {
    $('#loading').hide();
    console.log('Loading animation hidden.');
  });
};
