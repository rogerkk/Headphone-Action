package no.rkkc.headphoneaction2;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

public class HeadphoneReceiverService extends Service {

    HeadphoneReceiver receiver;
    String TAG = "HeadphoneAction-Service";
    
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean active = prefs.getBoolean("active", false);
        
        if (active) {
            Log.i(TAG, "Registering intent receiver.");
            
            /* register receiver */
            receiver = HeadphoneReceiver.getInstance();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.HEADSET_PLUG");
            registerReceiver(receiver, intentFilter);
        } else {
            Log.i(TAG, "App not activated in preferences, killing service");
            stopSelf();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }
    
@Override
    public void onDestroy() {
        try {
            unregisterReceiver(receiver);
        } catch (IllegalArgumentException e) {
            // Receiver wasn't registered.
        }
    } 
}
