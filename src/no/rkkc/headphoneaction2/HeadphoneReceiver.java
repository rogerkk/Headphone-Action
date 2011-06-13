package no.rkkc.headphoneaction2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Log;

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

        Integer state = intent.getIntExtra("state", -1);

        if (state > 0) {
            Log.v(TAG, "Something was plugged in!");
            
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            String appPackage = prefs.getString("appPackage", null);
            String appClass = prefs.getString("appClass", null);
            
            if (appPackage == null || appClass == null) {
                return;
            }
            
            try{
                Log.v(TAG, "Starting activity " + appPackage + "." + appClass);
                
                // Make sure app exists on device before trying to launch.
                context.getPackageManager().getApplicationInfo(appPackage, 0 );
                
                // Application exists. Launch it!
                Intent newAppIntent = new Intent();
                newAppIntent.setClassName(appPackage, appClass);
                newAppIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newAppIntent);

            } catch( PackageManager.NameNotFoundException e ){
                // Application doesn't exist
           }
        }
    }

}
