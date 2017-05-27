package hotspot.plugin.cordova;
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

public class hotspot extends CordovaPlugin
{
  private static final String LOGTAG = "hotspot";
  private static final String ACTION_ENABLE_ACCESS_POINT = "enableAccessPoint";
  private static final String ACTION_DISABLE_ACCESS_POINT = "disableAccessPoint";
  private WifiLock wifiLock = null;

  @Override
  public boolean execute(String action, JSONArray inputs, CallbackContext callbackContext) throws JSONException
  {
    PluginResult result = null;
    if (ACTION_ENABLE_ACCESS_POINT.equals(action))
    {
      result = execute_enableAccessPoint(inputs, callbackContext);
    }
    else
    if (ACTION_DISABLE_ACCESS_POINT.equals(action))
    {
      result = execute_disableAccessPoint(inputs, callbackContext);
    }
    else
    {
      Log.d(LOGTAG, String.format("Invalid action passed: %s", action));
      result = new PluginResult(Status.INVALID_ACTION);
    }

    if(result != null) callbackContext.sendPluginResult( result );

    return true;
  }

  private PluginResult execute_enableAccessPoint(JSONArray inputs, CallbackContext callbackContext)
  {
		Log.w(LOGTAG, "execute_enableAccessPoint");

		Context context = cordova.getActivity().getApplicationContext();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    {
			if (Settings.System.canWrite(context))
      {
				Log.w(LOGTAG, "you have WRITING SETTINGS permissions");
			}
			else
      {
				Log.w(LOGTAG, "you don't have WRITING SETTINGS permissions");
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).setClass(context,context.getClass());
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        intent.addFlags(Int‌​ent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Int‌​ent.FLAG_ACTIVITY_MU‌​LTIPLE_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        context.startActivity(intent);
			}
		}

		WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		try
    {
      wifiManager.setWifiEnabled( false ); // TURN OFF WIFI
			Method invokeMethod = wifiMgr.getClass().getMethod("setWifiApEnabled",WifiConfiguration.class,boolean.class);

			if ( (Boolean)invokeMethod.invoke(wifiMgr,configHotSpot(inputs),true) )
      {
				callbackContext.success();
			}
			else
      {
				return new PluginResult(Status.JSON_EXCEPTION);
			}
		}
		catch(Throwable ignoreException)
		{
			return new PluginResult(Status.JSON_EXCEPTION);
		}
		return null;
	}

  private WifiConfiguration configHotSpot(JSONArray inputs)
	{

		String ssid = null;
		String password = null;

		try
    {
			JSONObject jsonobject = inputs.getJSONObject(1);
			ssid = jsonobject.getString("ssid");
			password = jsonobject.getString("password");

      WifiConfiguration wifiConfiguration = new WifiConfiguration();

      wifiConfiguration.SSID = ssid;
      wifiConfiguration.preSharedKey = password;
      // wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.IEEE8021X);
      // wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
      // wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);
      // wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
      // wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.AuthAlgorithm.LEAP);
      // wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.AuthAlgorithm.OPEN);
      // wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.AuthAlgorithm.SHARED);
      // wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
      // wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
      // wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
      // wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.NONE);
      // wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);

      // wifiConfiguration.allowedAuthAlgorithms.set(0);
      // wifiConfiguration.allowedGroupCiphers.set(2);
      // wifiConfiguration.allowedKeyManagement.set(1);
      // wifiConfiguration.allowedPairwiseCiphers.set(1);
      // wifiConfiguration.allowedGroupCiphers.set(3);
      // wifiConfiguration.allowedPairwiseCiphers.set(2);

      // wifiConfiguration.preSharedKey = password;
      // wifiConfiguration.allowedAuthAlgorithms.set(0);
      // wifiConfiguration.allowedProtocols.set(1);
      // wifiConfiguration.allowedProtocols.set(0);
      // wifiConfiguration.allowedKeyManagement.set(1);
      // wifiConfiguration.allowedPairwiseCiphers.set(2);
      // wifiConfiguration.allowedPairwiseCiphers.set(1);

      //wifiConfiguration.preSharedKey = password;
      wifiConfiguration.hiddenSSID = false;
      wifiConfiguration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.WPA_PSK);
      wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
      wifiConfiguration.allowedKeyManagement.set(4);
      wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
      wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);

      return wifiConfiguration;

		}
    catch (JSONException e)
    {
      return null;
		}

    return null;
}
