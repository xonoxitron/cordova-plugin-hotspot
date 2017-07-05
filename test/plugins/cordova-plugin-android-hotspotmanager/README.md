# Cordova Plugin Android HotSpot
### (cordova-plugin-hotspot v1.0.0)
<br>
<img src="https://github.com/xonoxitron/cordova-plugin-hotspot/blob/master/cordova-plugin-hotspot.png?raw=true" width="300"/>&nbsp;

## Author
```
Matteo Pisani
E-mail: matteo.pisani.91@gmail.com
Linkedin: http://www.linkedin.com/in/matteopisani
Dedominici Christopher
Linkedin: https://www.linkedin.com/in/christopher-dedominici
```

## Description
This plugin allows developer to manage WiFi/Internet/Tethering/HotSpot on Google Android devices.
With a simple call to the "hotspot" object, users can:
* set up a hotspot

## Supported OS
- Android >= 6.0 Marshmallow / 7.0 Noughat

## Starting
Create a new Cordova Project

    $ cordova create HotSpotApplication com.development.hotspot HotSpotApplication

## Installing the plugin (from GIT)
Clone the plugin

    $ git clone https://github.com/xonoxitron/cordova-plugin-hotspot.git

Install the plugin

    $ cd HotSpotApplication
    $ cordova -d plugin add ../cordova-plugin-hotspot

Or alternatively

## Installing the plugin (from NPM)
Clone the plugin

    $ cd HotSpotApplication
    $ git clone cordova-plugin-hotspot

## Using
Edit `~/HotSpotApplication/www/yourfile.js` and add the following code inside or after that `onDeviceReady` event has been triggered

```js
document.addEventListener('deviceready',function()
{

},false);
```

## Testing
Install Andfroid platform

    cordova platform add android

Run the code

    cordova run android

Or

    cordova run android --device

## Test Application
- Inside the path `~/cordova-plugin-hotspot/test` there's the `Cordova Android` test application,
just open it, build it, connect your device and deploy it in.
- When the `test` application opens, `hotspot` object will be created.
- Cordova core brings to JavaScript the object in question exposing private API of `Java` Class.
- By pressing the `Enable HotSpot` button, if your device is supported, after `SSID` and `Password` have been set, you should see the `HotSpot` icon appearing on the status bar.
