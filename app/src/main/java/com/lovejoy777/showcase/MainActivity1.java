package com.lovejoy777.showcase;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.lovejoy777.showcase.activities.SettingsActivity;
import com.lovejoy777.showcase.fragments.AbsBackButtonFragment;
import com.lovejoy777.showcase.fragments.LayerListFragment;
import com.lovejoy777.showcase.fragments.MainFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.lovejoy777.showcase.Helpers.getLayersJsonFile;

public class MainActivity1 extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private AbsBackButtonFragment lastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ENDS SWVALUE ELSE
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_menu);
        setSupportActionBar(toolbar);

        //set NavigationDrawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        new UpgradeJson(this, false).execute();

        //Home fragment
        Fragment fragment = new MainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main, fragment).commit();


    } // ends onCreate

    //set NavigationDrawerContent
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mDrawerLayout.closeDrawers();
                        menuItem.setChecked(true);
                        Bundle bndlanimation =
                                ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anni1, R.anim.anni2).toBundle();
                        int id = menuItem.getItemId();
                        FragmentManager fragmentManager;
                        switch (id) {
                            case R.id.nav_home:
                                lastFragment = new MainFragment();
                                fragmentManager = getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.main, lastFragment).addToBackStack(null).commit();
                                break;
                            case R.id.nav_about:
                                Intent about = new Intent(MainActivity1.this, com.lovejoy777.showcase.activities.AboutActivity.class);
                                startActivity(about, bndlanimation);
                                break;
                            case R.id.nav_free:
                                Bundle data = new Bundle();
                                data.putString("type", "Free");

                                lastFragment = new LayerListFragment();
                                lastFragment.setArguments(data);
                                fragmentManager = getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.main, lastFragment).addToBackStack(null).commit();
                                break;
                            case R.id.nav_paid:
                                data = new Bundle();
                                data.putString("type", "Paid");

                                lastFragment = new LayerListFragment();
                                lastFragment.setArguments(data);
                                fragmentManager = getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.main, lastFragment).addToBackStack(null).commit();
                                break;
                            case R.id.nav_manager:
                                boolean installed = appInstalledOrNot("com.lovejoy777.rroandlayersmanager");
                                if (installed) {
                                    //This intent will help you to launch if the package is already installed
                                    Intent intent = new Intent();
                                    intent.setComponent(new ComponentName("com.lovejoy777.rroandlayersmanager", "com.lovejoy777.rroandlayersmanager.menu"));
                                    startActivity(intent);
                                    break;
                                } else {
                                    Toast.makeText(MainActivity1.this, "Please install the Layers Manager App", Toast.LENGTH_LONG).show();
                                    System.out.println("App is not currently installed on your phone");
                                    break;
                                }
                            case R.id.nav_settings:
                                Intent settings = new Intent(MainActivity1.this, SettingsActivity.class);
                                startActivity(settings, bndlanimation);
                                break;
                            case R.id.nav_playStore:
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/search?q=Layers+Theme&c=apps&docType=1&sp=CAFiDgoMTGF5ZXJzIFRoZW1legIYAIoBAggB:S:ANO1ljK_ZAY")));
                                break;
                        }
                        return false;
                    }
                });
    }

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

    public void replaceFragment(AbsBackButtonFragment fragment) {
        lastFragment = fragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onBackPressed() {

//TODO: Use getFragmentManager().findFragmentById()
        if (lastFragment == null || lastFragment.onBackButton()) {

            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawers();
            } else {
                super.onBackPressed();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
