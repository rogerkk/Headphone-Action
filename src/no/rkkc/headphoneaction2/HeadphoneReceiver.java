package no.rkkc.headphoneaction2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class HeadphoneReceiver extends BroadcastReceiver {

    private static HeadphoneReceiver instance;
    String TAG = "HeadphoneAction-Receiver";

    /**
     * Get the HeadphoneReceiver singleton.
     */
    public static HeadphoneReceiver getInstance()
    {
        if(instance == null)
        {
            instance = new HeadphoneReceiver();
        }
        return instance;
    }
    
    @Override
    public void onReceive(Context context, Intent intent) {
        
        // We shouldn't get anything else but ACTION_HEADSET_PLUG, but just to make sure.
        if (!intent.getAction().equalsIgnoreCase(Intent.ACTION_HEADSET_PLUG)) {
            return;
        }

        Log.v(TAG, "Got headset action: " + intent.getAction());

        String name = intent.getStringExtra("name");
        Integer state = intent.getIntExtra("state", -1);
        Integer microphone = intent.getIntExtra("microphone", 0);

        if (state > 0) { // 0 = unplugged, 1 = plugged in
            Log.v(TAG, "Something was plugged in!");
            
            // Get the preferences
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            String appPackage = prefs.getString("appPackage", null);
            String appClass = prefs.getString("appClass", null);
            Boolean fireOnMicrophone = prefs.getBoolean("microphone", false);
            
            // No app defined, do nothing.
            if (appPackage == null || appClass == null || // No action defined?
                    (microphone != null && microphone == 1 && !fireOnMicrophone)) { // Don't fire when headset has microphone
                Log.v(TAG, Integer.toString(microphone) + " " + Boolean.toString(fireOnMicrophone));
                return;
            }
            
            try {
                Log.v(TAG, "Starting activity " + appPackage + "." + appClass);
                
                // Make sure app exists on device before trying to launch.
                ApplicationInfo info = context.getPackageManager().getApplicationInfo(appPackage, 0);

                String appName = info.loadLabel(context.getPackageManager()).toString();
                showToast(context, name, appName);
                
                // Application exists. Launch it!
                Intent newAppIntent = new Intent();
                newAppIntent.setClassName(appPackage, appClass);
                newAppIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newAppIntent);

            } catch( PackageManager.NameNotFoundException e ){
                // Application doesn't exist. Uninstalled? Do nothing.
            }
        }
    }

    /**
     * @param context
     * @param headphonesName
     * @param info
     */
    private void showToast(Context context, String headphonesName, String appName) {
        if (headphonesName == null) {
            headphonesName = " ";
        } else {
            headphonesName = " " + headphonesName + " ";
        }

        String toastText = headphonesName + "detected. Starting " + appName;
        Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();
    }

}
