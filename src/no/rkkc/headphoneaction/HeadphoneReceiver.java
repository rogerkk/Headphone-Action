package no.rkkc.headphoneaction;

import android.app.LauncherActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class HeadphoneReceiver extends BroadcastReceiver {

    private static HeadphoneReceiver instance;

    private HeadphoneReceiver()
    {
    }

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
        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_HEADSET_PLUG)) {
            String data = intent.getDataString();
            Bundle extraData = intent.getExtras();

            Integer state = intent.getIntExtra("state", -1);
//            String nm = intent.getStringExtra("name");
//            String mic = intent.getStringExtra("microphone");
//            String all = String.format("st=%s, nm=%s, mic=%s", st, nm, mic);

            if (state > 0) {
                Log.v("HeadphoneReceiver", "Plugged in!");
                
//                Intent launcherActivityIntent = new Intent(context, LauncherActivity.class);
//                context.startActivity(launcherActivityIntent);
            }
        }
    }

}
