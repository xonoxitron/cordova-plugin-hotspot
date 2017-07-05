cordova.define("cordova-plugin-android-hotspotmanager.hotSpotManager", function(require, exports, module) {
var HotSpotManager = {};


HotSpotManager.enableAccessPoint = function(settings, successCallBack, failureCallBack)
{
    cordova.exec(successCallBack, failureCallBack, 'HotSpotManager', 'enableAccessPoint', [settings]);
};






// Enable WiFi Tethering HotSpot
/*HotSpotManager.enableAccessPoint = function(settings, successCallBack, failureCallBack)
{
    cordova.exec(successCallBack, failureCallBack, 'HotSpotManager', 'enableAccessPoint', [settings]);
};

HotSpotManager.disableAccessPoint = function(successCallBack, failureCallBack)
{
  cordova.exec(successCallBack, failureCallBack, 'HotSpotManager', 'disableAccessPoint',[]);
};*/

module.exports = HotSpotManager;
});
