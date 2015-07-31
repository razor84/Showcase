package com.lovejoy777.showcase.fragments;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lovejoy777.showcase.Activities.DetailActivity;
import com.lovejoy777.showcase.R;
import com.lovejoy777.showcase.Theme;
import com.lovejoy777.showcase.adapters.AbsFilteredCardViewAdapter;
import com.lovejoy777.showcase.adapters.BigCardsViewAdapter;
import com.lovejoy777.showcase.adapters.RecyclerItemClickListener;
import com.lovejoy777.showcase.adapters.SmallCardsViewAdapter;
import com.lovejoy777.showcase.enums.AndroidPlatform;
import com.lovejoy777.showcase.enums.AndroidVersion;
import com.lovejoy777.showcase.enums.Density;
import com.lovejoy777.showcase.enums.LayersVersion;
import com.lovejoy777.showcase.filters.*;
import com.quinny898.library.persistentsearch.SearchBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

public class LayerListFragment extends AbsBackButtonFragment {

    ArrayList<Theme> themesList;
    private AbsFilteredCardViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefresh = null;
    private String mode;
    boolean searchopened;
    private SearchBox search;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.screen1, null);

        searchopened = false;

        mode = getArguments().getString("type");

        search = (SearchBox) getActivity().findViewById(R.id.searchbox);
        search.enableVoiceRecognition(this);
        search.setLogoText("");

        toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(mode + " Layers");
        setHasOptionsMenu(true);

        mSwipeRefresh = (SwipeRefreshLayout) root.findViewById(R.id.swipeRefreshLayout);

        themesList = new ArrayList<Theme>();

        new JSONAsyncTask().execute();

        RecyclerView mRecyclerView = (RecyclerView) root.findViewById(R.id.cardList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        SharedPreferences prefs = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        boolean bigCards = prefs.getBoolean("bigCards", true);

        if (bigCards) {
            mAdapter = new BigCardsViewAdapter(themesList, getActivity());
        } else {
            mAdapter = new SmallCardsViewAdapter(themesList, getActivity());
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
                                ActivityOptions.makeCustomAnimation(getActivity().getApplicationContext(), R.anim.anni1, R.anim.anni2).toBundle();
                        getActivity().startActivity(Detailsactivity, bndlanimation);
                    }
                })
        );


        //initialize swipetorefresh
        mSwipeRefresh.setColorSchemeResources(R.color.accent, R.color.primary);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                themesList.clear();
                new JSONAsyncTask().execute();
            }
        });


        return root;

    }

    public void openSearch() {
        toolbar.setTitle("");
        search.revealFromMenuItem(R.id.action_search, getActivity());

        search.setMenuListener(new SearchBox.MenuListener() {

            @Override
            public void onMenuClick() {
                // Hamburger has been clicked
                Toast.makeText(getActivity(), "Menu click",
                        Toast.LENGTH_LONG).show();
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
                mAdapter.addFilter(new FilterName(search.getSearchText()));

            }

            @Override
            public void onSearch(String searchTerm) {
                mAdapter.addFilter(new FilterName(searchTerm));
                toolbar.setTitle(searchTerm);
            }

            @Override
            public void onSearchCleared() {
                mAdapter.addFilter(new FilterName(""));
            }

        });

    }

    private void closeSearch() {
        search.hideCircularly(getActivity());
        if (search.getSearchText().isEmpty()) {
            toolbar.setTitle(mode + " Layers");
        } else {
            toolbar.setTitle(search.getSearchText());
        }
    }

    /*
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == 1234 && resultCode == RESULT_OK) {
                ArrayList<String> matches = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                //search.populateEditText(matches.get(0));
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    */

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_search, menu);
    }

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSwipeRefresh.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefresh.setRefreshing(true);
                }
            });
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                File tagname = new File(Environment.getExternalStorageDirectory() + "/showcase/showcasejson/showcase.json");
                FileInputStream stream = new FileInputStream(tagname);
                String jString = null;
                try {
                    FileChannel fc = stream.getChannel();
                    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
                /* Instead of using default, pass in a decoder. */
                    jString = Charset.defaultCharset().decode(bb).toString();
                } finally {
                    stream.close();
                }

                JSONObject jsono = new JSONObject(jString);
                JSONArray jarray = jsono.getJSONArray("Themes");

                Random rnd = new Random();
                for (int i = jarray.length() - 1; i >= 0; i--) {
                    int j = rnd.nextInt(i + 1);

                    // Simple swap
                    JSONObject object = jarray.getJSONObject(j);
                    jarray.put(j, jarray.get(i));
                    jarray.put(i, object);

                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                    Theme theme = objectMapper
                            .readValue(object.toString(), Theme.class);


                    if ((theme.isFree() && mode.equals("Free"))
                            || (theme.isPaid() && mode.equals("Paid"))
                            || (theme.isDonate() && mode.equals("Donate"))) {
                        themesList.add(theme);
                    }
                }
                return true;

                //------------------>>

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
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
                ArrayAdapter<String> androidVersionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, AndroidVersions);
                androidVersionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                androidVersionSpinner.setAdapter(androidVersionAdapter);
                androidVersionSpinner.setSelection(lastLocations[0]);

                //Android Platform spinner
                final Spinner androidPlatformSpinner = (Spinner) DialogView.findViewById(R.id.androidPlatformSpinner);
                ArrayAdapter<String> androidPlatformAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, AndroidPlatforms);
                androidPlatformAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                androidPlatformSpinner.setAdapter(androidPlatformAdapter);
                androidPlatformSpinner.setSelection(lastLocations[1]);

                //Android Density spinner
                final Spinner androidDensitySpinner = (Spinner) DialogView.findViewById(R.id.androidDensitySpinner);
                ArrayAdapter<String> androidDensityAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, AndroidDensities);
                androidDensityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                androidDensitySpinner.setAdapter(androidDensityAdapter);
                androidDensitySpinner.setSelection(lastLocations[2]);

                //Layers Version Spinner
                final Spinner layersVersionSpinner = (Spinner) DialogView.findViewById(R.id.LayersVersionSpinne);
                ArrayAdapter<String> layersVersionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, LayersVersions);
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
            closeSearch();
            search.clearSearchable();
            toolbar.setTitle(mode + " Layers");
            searchopened = false;
            return false;
        } else {
            return true;
        }

    }


}