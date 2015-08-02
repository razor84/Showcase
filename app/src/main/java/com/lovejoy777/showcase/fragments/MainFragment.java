package com.lovejoy777.showcase.fragments;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.lovejoy777.showcase.MainActivity1;
import com.lovejoy777.showcase.R;
import com.lovejoy777.showcase.UpgradeJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.lovejoy777.showcase.Helpers.getLayersJsonFile;

public class MainFragment extends AbsBackButtonFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        ((NavigationView) getActivity().findViewById(R.id.nav_view)).getMenu().getItem(0).setChecked(true);

        Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(" ");

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,height
        );
        toolbar.setLayoutParams(layoutParams);
        setHasOptionsMenu(true);

        String jString = null;
        try {
            jString = Files.toString(getLayersJsonFile(getActivity()), Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsono = new JSONObject(jString);
            JSONArray jarray = jsono.getJSONArray("Themes");
            TextView discoverText = (TextView) root.findViewById(R.id.discovertxt);
            discoverText.setText("Find your favourite Layer out of " +jarray.length()+" submitted Layers.");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeRefreshLayout);

        //swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
       //     @Override
       //     public void onRefresh() {
       //         new UpgradeJson(MainFragment.this.getActivity(), true).execute();
       //         swipeRefreshLayout.setRefreshing(false);
       //     }
       // });


        return root;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_refresh:
                new UpgradeJson(MainFragment.this.getActivity(), true).execute();
                return true;
            case R.id.action_submit:
                new AlertDialog.Builder(getActivity())
                        .setTitle("Submit a Layers")
                        .setMessage("How do you want to submit your Layer?")
                        .setPositiveButton("Website", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://wh0cares.github.io/submit.html")));
                            }
                        })
                        .setNegativeButton("App", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                boolean installed = appInstalledOrNot("com.lovejoy777.showcasechecker");
                                if (installed) {
                                    //This intent will help you to launch if the package is already installed
                                    Intent intent = new Intent();
                                    intent.setComponent(new ComponentName("com.lovejoy777.showcasechecker", "com.lovejoy777.showcasechecker.MainActivity"));
                                    startActivity(intent);
                                } else {
                                    //Toast.makeText(getActivity(), "Please install the Layers Manager App", Toast.LENGTH_LONG).show();
                                    //System.out.println("App is not currently installed on your phone");
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/+SteveLovejoy/posts/2oD8WpiuWHb")));
                                }
                            }
                        })
                        .show();
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getActivity().getPackageManager();
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
