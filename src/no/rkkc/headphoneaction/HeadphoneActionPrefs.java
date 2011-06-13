package no.rkkc.headphoneaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;

public class HeadphoneActionPrefs extends PreferenceActivity
{
    private static final int LAUNCHER_ACTIVITY_CODE = 1;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        
        startService(new Intent(this, HeadphoneReceiverService.class));

        setupActivationButton();
        setupAppSelectorButton();
        
        
    }

    private void setupActivationButton() {
        final CheckBoxPreference activationPreference = (CheckBoxPreference)findPreference("active");
        
        activationPreference.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Intent serviceIntent = new Intent(HeadphoneActionPrefs.this, HeadphoneReceiverService.class);
                
                if (activationPreference.isChecked()) {
                    try {
                        unregisterReceiver(HeadphoneReceiver.getInstance());
                    } catch (IllegalArgumentException e) {
                        // Receiver wasn't registered.
                    }
                    stopService(serviceIntent);
                } else {
                    startService(serviceIntent);
                }
                
                return true;
            }
        });
    }

    /**
     * 
     */
    private void setupAppSelectorButton() {
        // Listen for clicks on prefs button for selecting app
        Preference activityPreference = findPreference("activity");
        activityPreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(HeadphoneActionPrefs.this, PrefsLauncherActivity.class);
                startActivityForResult(intent, LAUNCHER_ACTIVITY_CODE);

                return true;
            }
        });
        
        SharedPreferences prefs = getPreferenceManager().getSharedPreferences();
        String appName = prefs.getString("appName", null);
        
        if (appName != null) {
            activityPreference.setSummary(appName);
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        // Get info from result intent
        String appPackage = data.getStringExtra("appPackage");
        String appClass = data.getStringExtra("appClass");
        String appName = data.getStringExtra("appName");
        
        // Store preferences
        Editor editor = getPreferenceManager().getSharedPreferences().edit();
        editor.putString("appPackage", appPackage);
        editor.putString("appClass", appClass);
        editor.putString("appName", appName);
        editor.commit();
        
        // Update UI
        Preference activityPreference = findPreference("activity");
        activityPreference.setSummary(appName);        
    }
}
