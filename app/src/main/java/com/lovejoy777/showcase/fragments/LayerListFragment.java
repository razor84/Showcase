package com.lovejoy777.showcase.fragments;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.lovejoy777.showcase.R;
import com.lovejoy777.showcase.UpgradeJson;
import com.lovejoy777.showcase.activities.DetailActivity;
import com.lovejoy777.showcase.adapters.AbsFilteredCardViewAdapter;
import com.lovejoy777.showcase.adapters.BigCardsViewAdapter;
import com.lovejoy777.showcase.adapters.RecyclerItemClickListener;
import com.lovejoy777.showcase.adapters.SmallCardsViewAdapter;
import com.lovejoy777.showcase.beans.Layer;
import com.lovejoy777.showcase.debug.DebugLayers;
import com.lovejoy777.showcase.enums.AndroidPlatform;
import com.lovejoy777.showcase.enums.AndroidVersion;
import com.lovejoy777.showcase.enums.Density;
import com.lovejoy777.showcase.enums.LayersVersion;
import com.lovejoy777.showcase.filters.FilterBootanimation;
import com.lovejoy777.showcase.filters.FilterDensity;
import com.lovejoy777.showcase.filters.FilterFont;
import com.lovejoy777.showcase.filters.FilterLayersVersion;
import com.lovejoy777.showcase.filters.FilterNameAuthor;
import com.lovejoy777.showcase.filters.FilterSystemPlatform;
import com.lovejoy777.showcase.filters.FilterSystemVersion;
import com.quinny898.library.persistentsearch.SearchBox;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.lovejoy777.showcase.Helpers.getLayersJsonFile;

public class LayerListFragment extends AbsBackButtonFragment {

    ArrayList<Layer> layersList;
    private AbsFilteredCardViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefresh = null;
    private String mode;
    boolean searchopened;
    private SearchBox search;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.screen1, container, false);

        searchopened = false;

        mode = getArguments().getString("type");

        switch (mode) {
            case "Free":
                ((NavigationView) getActivity().findViewById(R.id.nav_view)).getMenu().getItem(1).setChecked(true);
                break;
            case "Paid":
                ((NavigationView) getActivity().findViewById(R.id.nav_view)).getMenu().getItem(2).setChecked(true);
                break;
            default:
                throw new IllegalArgumentException("Wrong mode");
        }


        search = (SearchBox) getActivity().findViewById(R.id.searchbox);
        setUpSearch();

        toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(mode + " Layers");
        setHasOptionsMenu(true);
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, getResources().getDisplayMetrics());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, height
        );
        toolbar.setLayoutParams(layoutParams);

        mSwipeRefresh = (SwipeRefreshLayout) root.findViewById(R.id.swipeRefreshLayout);

        layersList = new ArrayList<>();

        new JSONAsyncTask(getActivity(), false).execute();

        RecyclerView mRecyclerView = (RecyclerView) root.findViewById(R.id.cardList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        SharedPreferences prefs = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        boolean bigCards = prefs.getBoolean("bigCards", true);

        if (bigCards) {
            mAdapter = new BigCardsViewAdapter(layersList, getActivity());
        } else {
            mAdapter = new SmallCardsViewAdapter(layersList, getActivity());
        }

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.refreshFilteredList();

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(LayerListFragment.this.getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent Detailsactivity = new Intent(getActivity(), DetailActivity.class);

                        Detailsactivity.putExtra("theme", mAdapter.getItem(position));

                        Bundle bndlanimation =
                                ActivityOptions.makeCustomAnimation(getActivity(), R.anim.anni1, R.anim.anni2).toBundle();
                        getActivity().startActivity(Detailsactivity, bndlanimation);
                    }
                })
        );


        //initialize swipetorefresh
        mSwipeRefresh.setColorSchemeResources(R.color.accent, R.color.primary);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layersList.clear();
                new JSONAsyncTask(getActivity(), true).execute();
            }
        });


        return root;

    }

    private void setUpSearch() {
        search.enableVoiceRecognition(this);
        search.setLogoText("");
        search.setSearchString("");
        search.clearFocus();
        search.setMenuListener(new SearchBox.MenuListener() {

            @Override
            public void onMenuClick() {

                DrawerLayout drawerLayout = ((DrawerLayout) getActivity().findViewById(R.id.drawer_layout));

                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawers();
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }

            }

        });
        search.setSearchListener(new SearchBox.SearchListener() {

            @Override
            public void onSearchOpened() {
                // Use this to tint the screen
                searchopened = true;
                System.out.println("OnOpened: ");
            }

            @Override
            public void onSearchClosed() {
                // Use this to un-tint the screen
                if (searchopened) {
                    closeSearch();
                    searchopened = false;
                    System.out.println("onClosed ");
                }
            }

            @Override
            public void onSearchTermChanged() {
                mAdapter.addFilter(new FilterNameAuthor(search.getSearchText()));

            }

            @Override
            public void onSearch(String searchTerm) {
                mAdapter.addFilter(new FilterNameAuthor(searchTerm));
                toolbar.setTitle(searchTerm);
            }

            @Override
            public void onSearchCleared() {
                mAdapter.addFilter(new FilterNameAuthor(""));
            }

        });


    }

    private void openSearch() {
        toolbar.setTitle("");
        search.revealFromMenuItem(R.id.action_search, getActivity());
    }

    private void closeSearch() {
        search.hideCircularly(getActivity());

        if (search.getSearchText().isEmpty()) {
            toolbar.setTitle(mode + " Layers");
        } else {
            toolbar.setTitle(search.getSearchText());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_search, menu);
    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        Context context;
        boolean force;
        UpgradeJson upgradeJson;


        public JSONAsyncTask(Context context, boolean force) {
            this.context = context;
            this.force = force;
            upgradeJson = new UpgradeJson(context, force);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSwipeRefresh.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefresh.setRefreshing(true);
                }
            });
            upgradeJson.execute();
        }

        @Override
        protected Boolean doInBackground(String... urls) {


            try {

                while (upgradeJson.getStatus() != Status.FINISHED) {
                    Thread.sleep(10);
                }

                String jString = Files.toString(getLayersJsonFile(LayerListFragment.this.getActivity()), Charsets.UTF_8);

                JSONObject jsono = new JSONObject(jString);
                JSONArray jarray = jsono.getJSONArray("Themes");


                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                List<Layer> layers = Arrays.asList(objectMapper.readValue(jarray.toString(), Layer[].class));

                Collections.shuffle(layers);

                SharedPreferences prefs = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                boolean debug = prefs.getBoolean("debug_themes", false);

                if (debug) {
                    layersList.addAll(DebugLayers.createDebugLayers());
                }

                for (Layer layer : layers) {
                    if ((layer.isFree() && mode.equals("Free"))
                            || (layer.isPaid() && mode.equals("Paid"))
                            || (layer.isDonate() && mode.equals("Donate"))) {
                        layersList.add(layer);
                    }
                }


                return true;

                //------------------>>

            } catch (ParseException | JSONException | IOException | InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            mAdapter.notifyDataSetChanged();
            mAdapter.refreshFilteredList();
            if (!result) {
                Toast.makeText(getActivity(), "Unable to fetch database from server", Toast.LENGTH_LONG).show();
            }
            mSwipeRefresh.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefresh.setRefreshing(false);
                }
            });
        }
    }

    //4 for spinners and 2 for checkboxes
    int[] lastLocations = new int[6];

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // onBackPressed();
                return true;
            case R.id.action_search:
                openSearch();
                return true;
            case R.id.action_filter:
                final String[] AndroidVersions = {"Any", "Lollipop", "M"};
                String[] AndroidPlatforms = {"Any", "Touchwiz", "LG", "Sense", "Xperia", "Asus ZenUI"};
                String[] AndroidDensities = {"Any", "hdpi", "mdpi", "xhdpi", "xxhdpi", "xxxhdpi"};
                String[] LayersVersions = {"Any", "Basic RRO L", "Basic RRO M", "Layers Type 2 L", "Layers Type 3", "Layers Type 3 M"};

                final AlertDialog.Builder colorDialog = new AlertDialog.Builder(getActivity());
                final LayoutInflater inflater = this.getLayoutInflater(null);
                colorDialog.setTitle("Filter Layers");
                View DialogView = inflater.inflate(R.layout.dialog_filter, null);
                colorDialog.setView(DialogView);

                //Android Version spinner
                final Spinner androidVersionSpinner = (Spinner) DialogView.findViewById(R.id.androidVersionSpinner);
                ArrayAdapter<String> androidVersionAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, AndroidVersions);
                androidVersionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                androidVersionSpinner.setAdapter(androidVersionAdapter);
                androidVersionSpinner.setSelection(lastLocations[0]);

                //Android Platform spinner
                final Spinner androidPlatformSpinner = (Spinner) DialogView.findViewById(R.id.androidPlatformSpinner);
                ArrayAdapter<String> androidPlatformAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, AndroidPlatforms);
                androidPlatformAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                androidPlatformSpinner.setAdapter(androidPlatformAdapter);
                androidPlatformSpinner.setSelection(lastLocations[1]);

                //Android Density spinner
                final Spinner androidDensitySpinner = (Spinner) DialogView.findViewById(R.id.androidDensitySpinner);
                ArrayAdapter<String> androidDensityAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, AndroidDensities);
                androidDensityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                androidDensitySpinner.setAdapter(androidDensityAdapter);
                androidDensitySpinner.setSelection(lastLocations[2]);

                //Layers Version Spinner
                final Spinner layersVersionSpinner = (Spinner) DialogView.findViewById(R.id.LayersVersionSpinne);
                ArrayAdapter<String> layersVersionAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, LayersVersions);
                layersVersionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                layersVersionSpinner.setAdapter(layersVersionAdapter);
                layersVersionSpinner.setSelection(lastLocations[3]);

                final CheckBox fontCheckBox = (CheckBox) DialogView.findViewById(R.id.fontCheckbox);
                fontCheckBox.setChecked((lastLocations[4] != 0));

                final CheckBox bootanimationCheckBox = (CheckBox) DialogView.findViewById(R.id.bootanimationCheckbox);
                bootanimationCheckBox.setChecked((lastLocations[5] != 0));

                colorDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lastLocations[0] = androidVersionSpinner.getSelectedItemPosition();
                        lastLocations[1] = androidPlatformSpinner.getSelectedItemPosition();
                        lastLocations[2] = androidDensitySpinner.getSelectedItemPosition();
                        lastLocations[3] = layersVersionSpinner.getSelectedItemPosition();
                        lastLocations[4] = fontCheckBox.isChecked() ? 1 : 0;
                        lastLocations[5] = bootanimationCheckBox.isChecked() ? 1 : 0;


                        if (lastLocations[0] != 0) {
                            AndroidVersion version = AndroidVersion.valueOf((String) androidVersionSpinner.getSelectedItem());
                            mAdapter.addFilter(new FilterSystemVersion(version));
                        } else {
                            mAdapter.removeFilter(new FilterSystemVersion(null));
                        }

                        if (lastLocations[1] != 0) {
                            AndroidPlatform platform = AndroidPlatform.fromString((String) androidPlatformSpinner.getSelectedItem());
                            mAdapter.addFilter(new FilterSystemPlatform(platform));
                        } else {
                            mAdapter.removeFilter(new FilterSystemPlatform(null));
                        }

                        if (lastLocations[2] != 0) {
                            Density density = Density.valueOf(((String) androidDensitySpinner.getSelectedItem()).toUpperCase());
                            mAdapter.addFilter(new FilterDensity(density));
                        } else {
                            mAdapter.removeFilter(new FilterDensity(null));
                        }

                        if (lastLocations[3] != 0) {
                            LayersVersion layersVersion = LayersVersion.fromString((String) layersVersionSpinner.getSelectedItem());
                            mAdapter.addFilter(new FilterLayersVersion(layersVersion));
                        } else {
                            mAdapter.removeFilter(new FilterLayersVersion(null));
                        }

                        if (lastLocations[4] != 0) {
                            mAdapter.addFilter(new FilterBootanimation());
                        } else {
                            mAdapter.removeFilter(new FilterBootanimation());
                        }

                        if (lastLocations[5] != 0) {
                            mAdapter.addFilter(new FilterFont());
                        } else {
                            mAdapter.removeFilter(new FilterFont());
                        }


                    }
                });


                colorDialog.create();
                colorDialog.show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onBackButton() {

        System.out.println("onbackprreddses");
        if (searchopened) {
            search.toggleSearch();
            search.clearSearchable();
            searchopened = false;
            return false;
        } else {
            //Sometimes it go back with opened searchview
            forceCloseSearch();
            return true;
        }

    }

    //SearchView stuff
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (isAdded() && requestCode == SearchBox.VOICE_RECOGNITION_CODE && resultCode == Activity.RESULT_OK) {
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            search.populateEditText(matches);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        forceCloseSearch();
        super.onDestroyView();
    }

    //#BlameAndrew
    //To make sure it's for 100% closed
    private void forceCloseSearch() {

        try {
            Field field = search.getClass().getDeclaredField("searchOpen");
            field.setAccessible(true);
            field.set(search, true);
            search.toggleSearch();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}