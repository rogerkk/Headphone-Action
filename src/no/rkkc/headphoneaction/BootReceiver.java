package no.rkkc.headphoneaction;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class BootReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Boolean active = prefs.getBoolean("active", false);
        
        if (active) {
            Intent serviceIntent = new Intent();
            serviceIntent.setClassName(context.getPackageName(), HeadphoneReceiverService.class.getName());
            context.startService(serviceIntent);
        }
    }
}
