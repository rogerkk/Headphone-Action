package no.rkkc.headphoneaction;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class HeadphoneActionPrefs extends PreferenceActivity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        startService(new Intent(this, HeadphoneReceiverService.class));
    }
}
