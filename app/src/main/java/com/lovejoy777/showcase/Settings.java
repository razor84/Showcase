package com.lovejoy777.showcase;

import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by lovejoy777 on 02/07/15.
 */
public class Settings extends PreferenceActivity implements

        SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager prefMgr = getPreferenceManager();
        prefMgr.setSharedPreferencesName("myPrefs");
        addPreferencesFromResource(R.xml.settings);



    }


    private void killLauncherIcon() {

        Process p1 = null;
        String noIcon = "";
        try {
            p1 = new ProcessBuilder("/system/bin/getprop", "ro.layers.noIcon").redirectErrorStream(true).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p1.getInputStream()));
            String line = "";
            if ((line=br.readLine()) != null){
                noIcon = line;

                if (noIcon.length() >= 3) {

                    PackageManager p = getPackageManager();
                    ComponentName componentName = new ComponentName(this, com.lovejoy777.showcase.MainActivity.class); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
                    p.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                   // Toast.makeText(Settings.this, "Launcher icon removed", Toast.LENGTH_SHORT).show();

                } else{

                    Toast.makeText(Settings.this, "Your rom needs support for this function", Toast.LENGTH_LONG).show();
                    SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
                    SharedPreferences.Editor editor = myPrefs.edit();
                    editor.putBoolean("switch1", false);
                    editor.commit();

                }

            } else {

                Toast.makeText(Settings.this, "null build.prop commit", Toast.LENGTH_LONG).show();
                SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.putBoolean("switch1", false);
                editor.commit();
            }
            p1.destroy();
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void ReviveLauncherIcon() {

        PackageManager p = getPackageManager();
        ComponentName componentName = new ComponentName(this, com.lovejoy777.showcase.MainActivity.class);
        p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.back2, R.anim.back1);
    }



    @Override
    protected void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key)
    {
        SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = myPrefs.edit();
        Boolean HideLauncherIcon = myPrefs.getBoolean("switch1",false);

        if(HideLauncherIcon){
            killLauncherIcon();
        } else{
            ReviveLauncherIcon();
        }
    }


}
