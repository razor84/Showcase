package com.lovejoy777.showcase;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // if sw1 is true && layers manager is installed then remove icon.
        SharedPreferences prefs = getSharedPreferences("switch1", Context.MODE_PRIVATE);
        boolean switch1 = prefs.getBoolean("switch1", false);

        boolean installed = appInstalledOrNot("com.lovejoy777.rroandlayersmanager");
        if (installed && switch1) {

            PackageManager p = getPackageManager();
            ComponentName componentName = new ComponentName(this, com.lovejoy777.showcase.MainActivity.class); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
            p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        }

        // start MainActivity1
        Intent intent = new Intent(MainActivity.this, MainActivity1.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();

    }

    // check for installed app method
    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}