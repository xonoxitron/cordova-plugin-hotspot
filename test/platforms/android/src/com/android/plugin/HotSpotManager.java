package com.android.plugin;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import android.os.Handler;
import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.content.*;
import android.net.wifi.*;
import java.lang.reflect.*;
import org.apache.cordova.PluginResult.Status;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiConfiguration;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;

public class HotSpotManager extends CordovaPlugin {

    private static final String LOGTAG = "HotSpotManager";

    /** Cordova Actions.**/
    private static final String ACTION_ENABLE_ACCESS_POINT = "enableAccessPoint";
    private static final String ACTION_DISABLE_ACCESS_POINT = "disableAccessPoint";


    @Override
    public boolean execute(String action, JSONArray inputs, CallbackContext callbackContext) throws JSONException {

        PluginResult result = null;

        if (ACTION_ENABLE_ACCESS_POINT.equals(action)) {
            result = executeEnableWifiAP(inputs, callbackContext);
        } else if (ACTION_DISABLE_ACCESS_POINT.equals(action)) {
            result = executeDisableWifiAP(callbackContext);
        } else {
            Log.d(LOGTAG, String.format("Invalid action passed: %s", action));
            result = new PluginResult(Status.INVALID_ACTION);
        }

        if (result != null) callbackContext.sendPluginResult(result);

        return true;
    }

    private PluginResult executeEnableWifiAP(JSONArray inputs, CallbackContext callbackContext) {
        Log.w(LOGTAG, "executeEnableAccessPoint");


        Context context = cordova.getActivity().getApplicationContext();
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(context)) {
                Log.w(LOGTAG, "you have WRITING SETTINGS permissions");
            } else {
                Log.w(LOGTAG, "you don't have WRITING SETTINGS permissions");
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + context.getPackageName()));

                intent.addFlags(Int‌​ent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Int‌​ent.FLAG_ACTIVITY_MU‌​LTIPLE_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

                context.startActivity(intent);

                while(Settings.System.canWrite(context) != true) {}
            }
        }

        /*turn off wifi*/
        if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }

        //check if AccessPoint is enabled, if yes turn it off so you cann set your new configuration later
        if(isApOn(context)) {
            callbackContext.error("ap_already_ON");
            return null;
        }

        /*check if phone is connected to network */
        if (!isMObileNetworkEnabled(context)) {
           callbackContext.error("network_is_OFF");
           return null;
        }


        //give time to phone to be able to turn OFF previous AP
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {

            Method invokeMethod = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);

            if ((Boolean) invokeMethod.invoke(wifiManager, initHotspotConfig(inputs, callbackContext), true)) {
                callbackContext.success("apEnabled");

            } else {
                return new PluginResult(Status.JSON_EXCEPTION);
            }

        } catch (Throwable ignoreException) {
            return new PluginResult(Status.JSON_EXCEPTION);
        }
        callbackContext.success();
        return null;
    }

    private PluginResult executeDisableWifiAP(CallbackContext callbackContext) {
        Context context = cordova.getActivity().getApplicationContext();

        try {
            disableAP(context);
            callbackContext.success("apDisabled");
        } catch (Throwable ignoreException) {
            callbackContext.error("disableAp_error");
        }

        return null;
    }

    private static boolean isApOn(Context context) {
        WifiManager wifimanager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        try {
            Method method = wifimanager.getClass().getDeclaredMethod("isWifiApEnabled");
            method.setAccessible(true);
            return (Boolean) method.invoke(wifimanager);
        }
        catch (Throwable ignored) {}
        return false;
    }

    private void disableAP(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        Method[] methods = wifiManager.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals("setWifiApEnabled")) {
                try {
                    method.invoke(wifiManager, null, false);
                } catch (Exception ex) {
                }
                break;
            }
        }
    }

    private boolean isMObileNetworkEnabled(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean dataNetworkEnabled = false;
        try {
            Class cmClass = Class.forName(cm.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true); // Make the method callable
            // get the setting for "mobile data"
            dataNetworkEnabled = (Boolean)method.invoke(cm);

        } catch (Throwable ignoreException) {}

        return dataNetworkEnabled;
    }

    private WifiConfiguration initHotspotConfig(JSONArray inputs, CallbackContext callbackContext) {
        String ssid = null;
        String password = null;

        //read user SSID and PASSWORD
        try {
            JSONObject jsonobject = inputs.getJSONObject(0);
            ssid = jsonobject.getString("ssid");
            password = jsonobject.getString("password");
        }catch (JSONException e) {
            callbackContext.error("wrong_parameters");
        }

        //set AP configuration
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = ssid;
        wifiConfiguration.preSharedKey = password;
        wifiConfiguration.hiddenSSID = false;
        wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        wifiConfiguration.allowedKeyManagement.set(4);
        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);

        return wifiConfiguration;
    }
}











































