cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "id": "cordova-plugin-android-hotspotmanager.hotSpotManager",
        "file": "plugins/cordova-plugin-android-hotspotmanager/www/hotSpotManager.js",
        "pluginId": "cordova-plugin-android-hotspotmanager",
        "clobbers": [
            "cordova.plugins.hotSpotManager"
        ]
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "cordova-plugin-whitelist": "1.3.2",
    "cordova-plugin-android-hotspotmanager": "1.0.0"
};
// BOTTOM OF METADATA
});