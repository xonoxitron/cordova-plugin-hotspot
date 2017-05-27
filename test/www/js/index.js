var app = {
        // Application Constructor
        initialize: function() {
                document.addEventListener('deviceready', this.onDeviceReady.bind(this), false);
        },

        // deviceready Event Handler
        //
        // Bind any cordova events here. Common events are:
        // 'pause', 'resume', etc.
        onDeviceReady: function() {
                this.receivedEvent('deviceready');
        },

        // Update DOM on a Received Event
        receivedEvent: function(id) {
                var parentElement = document.getElementById(id);
                var listeningElement = parentElement.querySelector('.listening');
                var receivedElement = parentElement.querySelector('.received');

                listeningElement.setAttribute('style', 'display:none;');
                receivedElement.setAttribute('style', 'display:block;');

                console.log('Received Event: ' + id);
        }
};

app.initialize();

document.addEventListener("deviceready", onDeviceReady, false);


// device APIs are available
//
function onDeviceReady() {

                var permissions = cordova.plugins.permissions;

                var list = [
                                    permissions.INTERNET,
                                    permissions.WRITE_EXTERNAL_STORAGE,
                                    permissions.ACCESS_WIFI_STATE,
                                    permissions.CHANGE_WIFI_STATE,
                                    permissions.READ_EXTERNAL_STORAGE,
                                    permissions.ACCESS_NETWORK_STATE,
                                    permissions.ACCESS_FINE_LOCATION,
                                    permissions.CHANGE_NETWORK_STATE,
                                    permissions.CHANGE_WIFI_STATE,

                                    //permissions.WRITE_SETTINGS
                ];

  
       
           


                                permissions.requestPermissions(list, success, error);

                                function error() {
                                       raiseERROR("in request permission");
                                }

                                function success(status) {
                                        if (!status.hasPermission) {
                                                raiseERROR("You haven't allow the permissions");
                                        }
                                        else{
                                                alert("All prmissions enabled");
                                        }
                                }
                        
     

       

        //alert( JSON.stringify( cordova.plugins.permissions ) );

        var wf = window.plugins.WifiAdmin;

        /*       wf.getWifiInfo(function(data){
                        alert( JSON.stringify(data) );
                }, function(){
                        raiseERROR("problems");
                });*/

        //wf.enableWifi(true);
        
        enable.addEventListener("click", function(){
                    
                    var param= {
                            "SSID"                  : "HotSpotManager",
                            "preSharedKey"   : "12345678"
                    };

                            wf.enableWifiAP(true, param, function(data) {
                                    alert("ok");
                            }, function(err) {
                                    alert("error here: " + err);
                            });
        }, false);


      




      

}


function raiseERROR(errMessage) {
        alert("ERROR: " + errMessage);
}