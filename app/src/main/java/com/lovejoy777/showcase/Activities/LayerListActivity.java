package com.lovejoy777.showcase.Activities;

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
import android.speech.RecognizerIntent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lovejoy777.showcase.R;
import com.lovejoy777.showcase.Theme;
import com.lovejoy777.showcase.adapters.AbsFilteredCardViewAdapter;
import com.lovejoy777.showcase.adapters.BigCardsViewAdapter;
import com.lovejoy777.showcase.adapters.RecyclerItemClickListener;
import com.lovejoy777.showcase.adapters.SmallCardsViewAdapter;
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

public class LayerListActivity extends AppCompatActivity {

    ArrayList<Theme> themesList;
    private AbsFilteredCardViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefresh = null;
    private String mode;
    boolean searchopened;
    private SearchBox search;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen1);

        searchopened = false;


        mode = getIntent().getStringExtra("type");

        // Handle Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setTitle(mode + " Layers");
        setSupportActionBar(toolbar);


        search = (SearchBox) findViewById(R.id.searchbox);
        search.enableVoiceRecognition(this);
        search.setLogoText("Showcase");
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        themesList = new ArrayList<Theme>();

        new JSONAsyncTask().execute();

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.cardList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        SharedPreferences prefs = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        boolean bigCards = prefs.getBoolean("bigCards", true);

        if (bigCards) {
            mAdapter = new BigCardsViewAdapter(themesList, this);
        } else {
            mAdapter = new SmallCardsViewAdapter(themesList, this);
        }

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.filter("");

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(LayerListActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent Detailsactivity = new Intent(LayerListActivity.this, DetailActivity.class);

                        Detailsactivity.putExtra("theme", mAdapter.getItem(position));

                        Bundle bndlanimation =
                                ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anni1, R.anim.anni2).toBundle();
                        startActivity(Detailsactivity, bndlanimation);
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
    }


    public void openSearch() {
        toolbar.setTitle("");
        search.revealFromMenuItem(R.id.action_search, this);
       // for (suggestions.) {



       //     SearchResult option = new SearchResult("Result "
       //            + Integer.toString(x), getResources().getDrawable(
       ////             R.drawable.ic_history));
      //      search.addSearchable(option);
      //  }
        search.setMenuListener(new SearchBox.MenuListener() {

            @Override
            public void onMenuClick() {
                // Hamburger has been clicked
                Toast.makeText(LayerListActivity.this, "Menu click",
                        Toast.LENGTH_LONG).show();
            }

        });
        search.setSearchListener(new SearchBox.SearchListener() {

            @Override
            public void onSearchOpened() {
                // Use this to tint the screen
                searchopened = true;
                System.out.println("OnOpened: "+searchopened);
            }

            @Override
            public void onSearchClosed() {
                // Use this to un-tint the screen
                if (searchopened) {
                    closeSearch();
                    searchopened = false;
                    System.out.println("onClosed " + searchopened);
                }
            }

            @Override
            public void onSearchTermChanged() {
                mAdapter.filter(search.getSearchText());
                // React to the search term changing
                // Called after it has updated results
            }

            @Override
            public void onSearch(String searchTerm) {
                mAdapter.filter(searchTerm);
                toolbar.setTitle(searchTerm);
            }

            @Override
            public void onSearchCleared() {
                mAdapter.filter("");
            }

        });

    }

    protected void closeSearch() {
        search.hideCircularly(this);
        if(search.getSearchText().isEmpty())toolbar.setTitle(mode+" Layers");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1234 && resultCode == RESULT_OK) {
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //search.populateEditText(matches.get(0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);


        return super.onCreateOptionsMenu(menu);
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
            mAdapter.filter("");
            if (!result) {
                Toast.makeText(getApplicationContext(), "Unable to fetch database from server", Toast.LENGTH_LONG).show();
            }
            mSwipeRefresh.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefresh.setRefreshing(false);
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_search:
                openSearch();
                return true;
            case R.id.action_filter:
                String[] AndroidVersions = {"Any", "Lollipop", "M"};
                String[] AndroidPlatforms = {"Any", "Touchwiz", "LG", "Sense", "Xperia", "Asus ZenUI"};
                String[] AndroidDensities = {"Any", "hdpi", "mdpi", "xhdpi", "xxhdpi", "xxxhdpi"};
                String[] LayersVersions = {"Any", "Basic RRO L", "Basic RRO M", "Layers Type 2 L", "Layers Type 3", "Layers Type 3 M"};

                final AlertDialog.Builder colorDialog = new AlertDialog.Builder(this);
                final LayoutInflater inflater = this.getLayoutInflater();
                colorDialog.setTitle("Filter Layers");
                View DialogView = inflater.inflate(R.layout.dialog_filter, null);
                colorDialog.setView(DialogView);

                //Android Version spinner
                final Spinner AndroidVersionSpinner= (Spinner) DialogView.findViewById(R.id.androidVersionSpinner);
                AndroidVersionSpinner.setOnItemSelectedListener(new OnSpinnerItemClicked());
                ArrayAdapter<String> AndroidVersionAdapter = new ArrayAdapter<String>(LayerListActivity.this, android.R.layout.simple_spinner_item, AndroidVersions);
                AndroidVersionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                AndroidVersionSpinner.setAdapter(AndroidVersionAdapter);

                //Android Platform spinner
                final Spinner AndroidPlatformSpinner= (Spinner) DialogView.findViewById(R.id.androidPlatformSpinner);
                AndroidPlatformSpinner.setOnItemSelectedListener(new OnSpinnerItemClicked());
                ArrayAdapter<String> AndroidPlatformAdapter = new ArrayAdapter<String>(LayerListActivity.this, android.R.layout.simple_spinner_item, AndroidPlatforms);
                AndroidPlatformAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                AndroidPlatformSpinner.setAdapter(AndroidPlatformAdapter);

                //Android Density spinner
                final Spinner AndroidDensitySpinner= (Spinner) DialogView.findViewById(R.id.androidDensitySpinner);
                AndroidDensitySpinner.setOnItemSelectedListener(new OnSpinnerItemClicked());
                ArrayAdapter<String> AndroidDensityAdapter = new ArrayAdapter<String>(LayerListActivity.this, android.R.layout.simple_spinner_item, AndroidDensities);
                AndroidDensityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                AndroidDensitySpinner.setAdapter(AndroidDensityAdapter);

                //Layers Version Spinner
                final Spinner LayersVersionSpinner= (Spinner) DialogView.findViewById(R.id.LayersVersionSpinne);
                LayersVersionSpinner.setOnItemSelectedListener(new OnSpinnerItemClicked());
                ArrayAdapter<String> LayersVersionAdapter = new ArrayAdapter<String>(LayerListActivity.this, android.R.layout.simple_spinner_item, LayersVersions);
                LayersVersionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                LayersVersionSpinner.setAdapter(LayersVersionAdapter);

                colorDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                colorDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }


                });
                colorDialog.create();
                colorDialog.show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class OnSpinnerItemClicked implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {



        }

        @Override
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }

    @Override
    public void onBackPressed() {

        System.out.println("onbackprreddses");
        if (searchopened) {
            closeSearch();
            search.clearSearchable();
            mAdapter.filter("");
            toolbar.setTitle(mode + " Layers");
            searchopened = false;
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.back2, R.anim.back1);
        }

    }




}