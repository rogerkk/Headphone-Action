package no.rkkc.headphoneaction;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent serviceIntent = new Intent();
        serviceIntent.setClassName(context.getPackageName(), HeadphoneReceiverService.class.getName());
        context.startService(serviceIntent);
    }
}
