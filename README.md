# Cordova Plugin Android HotSpot
### (cordova-plugin-hotspot v1.0.0)
<br>
<img src="https://github.com/xonoxitron/cordova-plugin-hotspot/blob/master/cordova-plugin-hotspot.png?raw=true" width="300"/>&nbsp;

## Author
```
Matteo Pisani
E-mail: matteo.pisani.91@gmail.com
Linkedin: http://www.linkedin.com/in/matteopisani
```

## Description
This plugin allows developer to manage Taptic Engine feedback of supported Apple devices.
With a simple call to the "TapticEngine" object, vibration can be performed.

## Supported Devices (Apple)
- iPhone 7
- iPhone 7 Plus
- iPhone 6S
- iPhone 6S Plus
- iPad Pro

## Supported OS
- iOS >= 9.0

## Starting
Create a new Cordova Project

    $ cordova create TapticEngineApplication com.development.tapticengine TapticEngineApplication

## Installing the plugin (from GIT)
Clone the plugin

    $ git clone https://github.com/xonoxitron/cordova-plugin-tapticengine.git

Install the plugin

    $ cd TapticEngineApplication
    $ cordova -d plugin add ../cordova-plugin-tapticengine

Or alternatively

## Installing the plugin (from NPM)
Clone the plugin

    $ cd TapticEngineApplication
    $ git clone cordova-plugin-tapticengine

## Using
Edit `~/TapticEngineApplication/www/yourfile.js` and add the following code inside or after that `onDeviceReady` event has been triggered

```js
document.addEventListener('touchstart',function()
{
  TapticEngine.generateTapticFeedback(); // perform a vibration
},false);
```

## Testing
Install iOS platform

    cordova platform add ios

Run the code

    cordova run ios

Or

    cordova run ios --device

## Test Application
- Inside the path `~/cordova-plugin-tapticengine/test/TapticEngineApplication` there's the `XCode iOS` test application,
just open it, build it, connect your device and deploy it in.
- When test application opens `TapticEngine (object)` will be created.
- Cordova core brings to JavaScript the object in question exposing private API of `UITapticEngine` Class.
- By pressing the touchscreen, if your device is supported, you should feed TapticEngine vibration performed under your finger.
