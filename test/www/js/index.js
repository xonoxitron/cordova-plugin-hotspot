enable.addEventListener("click", function() {

	const PASS_LENGTH = 8;
	const SSID_LENGTH = 4; 
	var password;
	var ssid;

	ssid = document.getElementById('ssid').value;
	password = document.getElementById('password').value;

	if(ssid.length < SSID_LENGTH ){
		alert("ssid must have at least " + SSID_LENGTH + " characters");
		return -1;
	}
	if(password.length < PASS_LENGTH ){
		alert("password must have at least " + PASS_LENGTH + " characters");
		return -1;
	}

	var param = {
		ssid: ssid,
		password: password
	};

	cordova.plugins.hotSpotManager.enableAccessPoint(param, function(res){
		alert("hotSpotEnabled: " + res);
	}, function(err){
		alert("ERROR: " + err);
	});
});

disable.addEventListener("click", function() {

	cordova.plugins.hotSpotManager.disableAccessPoint(function(res){
		alert("ap is disabled: " + res);
	}, function(){
		alert("ERR: cannot disable ap");
	});
});	
