package no.rkkc.headphoneaction;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class HeadphoneReceiverService extends Service {

    HeadphoneReceiver receiver; 
    
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        /* register receiver */
        receiver = HeadphoneReceiver.getInstance();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.HEADSET_PLUG");
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }
    
@Override
    public void onDestroy() {
            unregisterReceiver(receiver);
    } 
}
