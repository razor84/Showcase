package com.lovejoy777.showcase.fragments;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.florent37.materialleanback.MaterialLeanBack;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.lovejoy777.showcase.Callback;
import com.lovejoy777.showcase.LeanBackViewHolder;
import com.lovejoy777.showcase.R;
import com.lovejoy777.showcase.UpgradeJson;
import com.lovejoy777.showcase.activities.DetailActivity;
import com.lovejoy777.showcase.beans.Layer;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.lovejoy777.showcase.Helpers.getLayersJsonFile;

public class MainFragment extends AbsBackButtonFragment {

    MaterialLeanBack materialLeanBack;
    MaterialLeanBack materialLeanBack2;
    ArrayList<Layer> layersListFree = new ArrayList<>();
    ArrayList<Layer> layersListPaid = new ArrayList<>();
    private String mode;
    ViewGroup root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        ((NavigationView) getActivity().findViewById(R.id.nav_view)).getMenu().getItem(0).setChecked(true);

        Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(" ");

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, height
        );
        toolbar.setLayoutParams(layoutParams);
        setHasOptionsMenu(true);

        new UpgradeJson(getActivity(), false, new Callback() {
            @Override
            public void callback() {
                loadLeanback();
            }
        }).execute();


        return root;
    }

    private void loadLeanback() {
        try {
            String jString = Files.toString(getLayersJsonFile(getActivity()), Charsets.UTF_8);
            JSONObject jsono = new JSONObject(jString);
            JSONArray jarray = jsono.getJSONArray("Themes");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<Layer> layers = Arrays.asList(objectMapper.readValue(jarray.toString(), Layer[].class));
            Collections.shuffle(layers);
            /*mode = "Free";
            for (Layer layer : layers) {
                if ((layer.isFree() && mode.equals("Free")) || (layer.isPaid() && mode.equals("Paid")) || (layer.isDonate() && mode.equals("Donate"))) {
                    layersListFree.add(layer);
                }
            } */


            for (Layer layer : layers) {
                if (layer.isFree() || layer.isDonate()) {
                    layersListFree.add(layer);
                } else {
                    if (layer.isPaid()) {
                        layersListPaid.add(layer);
                    }

                }
            }
            //discoverText.setText("Find your favourite Layer out of " +jarray.length()+" submitted Layers.");
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        materialLeanBack = (MaterialLeanBack) root.findViewById(R.id.materialLeanBack);
        materialLeanBack.setAdapter(new MaterialLeanBack.Adapter<LeanBackViewHolder>() {
            @Override
            public int getLineCount() {
                return 4;
            }

            @Override
            public int getCellsCount(int line) {
                return 7;
            }

            @Override
            public LeanBackViewHolder onCreateViewHolder(ViewGroup viewGroup, int line) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.leanbackcell, viewGroup, false);
                return new LeanBackViewHolder(view);
            }

            @Override
            public void onButtonClick(int row) {

                Bundle data = new Bundle();

                switch (row) {
                    case 1:
                        data.putString("type", "Free");
                        break;
                    case 2:
                        data.putString("type", "Paid");
                        break;
                    default:
                        //It should NEVER happer
                        throw new RuntimeException();
                }

                AbsBackButtonFragment fragment;

                fragment = new LayerListFragment();
                fragment.setArguments(data);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main, fragment).addToBackStack(null).commit();
            }


            @Override
            public void onBindViewHolder(final LeanBackViewHolder viewHolder, final int i) {
                viewHolder.leanimageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent Detailsactivity = new Intent(getActivity(), DetailActivity.class);
                        if (viewHolder.row == 1) {
                            Detailsactivity.putExtra("theme", layersListFree.get(i));
                        } else {
                            if (viewHolder.row == 2) {
                                Detailsactivity.putExtra("theme", layersListPaid.get(i));
                            }
                        }
                        Bundle bndlanimation =
                                ActivityOptions.makeCustomAnimation(getActivity(), R.anim.anni1, R.anim.anni2).toBundle();
                        getActivity().startActivity(Detailsactivity, bndlanimation);
                    }
                });
                System.out.println("row: " + viewHolder.row);
                if (viewHolder.row == 1) {
                    viewHolder.leantextView.setText(layersListFree.get(i).getTitle());
                    Picasso.with(viewHolder.leanimageView.getContext()).load(layersListFree.get(i).getPromo()).fit().centerCrop().into(viewHolder.leanimageView);
                } else {
                    viewHolder.leantextView.setText(layersListPaid.get(i).getTitle());
                    Picasso.with(viewHolder.leanimageView.getContext()).load(layersListPaid.get(i).getPromo()).fit().centerCrop().into(viewHolder.leanimageView);
                }

            }

            @Override
            public String getTitleForRow(int row) {
                if (row == 1) {
                    return "Free Layers";
                } else {
                    if (row == 2) {
                        return "Paid Layers";
                    }
                }
                return null;
            }

        }, getActivity());
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
