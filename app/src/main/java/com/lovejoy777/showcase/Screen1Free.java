package com.lovejoy777.showcase;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lovejoy777.showcase.adapters.CardViewAdapter;
import com.lovejoy777.showcase.adapters.RecyclerItemClickListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by lovejoy777 on 24/06/15.
 */
public class Screen1Free extends AppCompatActivity  {

    private RecyclerView mRecyclerView;
    ArrayList<Themes> themesList;
    private CardViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefresh = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen1);

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        setSupportActionBar(toolbar);
        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        themesList = new ArrayList<Themes>();


         final DownloadTask downloadTask = new DownloadTask(Screen1Free.this);
         downloadTask.execute("https://api.github.com/repos/BitSyko/layers_showcase_json/releases/latest");

      //  final Download1Task download1Task = new Download1Task(Screen1Free.this);
       // download1Task.execute("https://api.github.com/repos/BitSyko/layers_showcase_json/releases/latest");

        // download json file
      //  String jsonlink = "https://api.github.com/repos/BitSyko/layers_showcase_json/releases/latest";

     //   Intent installtheme = new Intent(Intent.ACTION_VIEW, Uri.parse(jsonlink));

      //  Bundle bndlanimation =
      //          ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anni1, R.anim.anni2).toBundle();
      //  startActivity(installtheme, bndlanimation);



        try {

            File yourFile = new File(Environment.getExternalStorageDirectory() + "/temp.json");
            FileInputStream stream = new FileInputStream(yourFile);
            String jString = null;
            try {
                FileChannel fc = stream.getChannel();
                MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
                /* Instead of using default, pass in a decoder. */
                jString = Charset.defaultCharset().decode(bb).toString();
            }
            finally {
                stream.close();
            }

            JSONObject jObject = new JSONObject(jString);

            String tag_name = jObject.getString("tag_name");


            String jsonLink = "https://github.com/BitSyko/layers_showcase_json/releases/download/" + tag_name + "/showcase.json";
            // Toast.makeText(getApplicationContext(), tag_name, Toast.LENGTH_LONG).show();

            new JSONAsyncTask().execute(jsonLink);

        } catch (Exception e) {e.printStackTrace();}





        mRecyclerView = (RecyclerView)findViewById(R.id.cardList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new CardViewAdapter(themesList, R.layout.adapter_card_layout, this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(Screen1Free.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String free = themesList.get(position).getfree();
                        String title = themesList.get(position).gettitle();
                        String link = themesList.get(position).getlink();
                        String googleplus = themesList.get(position).getgoogleplus();
                        String promo = themesList.get(position).getpromo();
                        String developer = themesList.get(position).getauthor();
                        String screenshot_1 = themesList.get(position).getscreenshot_1();
                        String screenshot_2 = themesList.get(position).getscreenshot_2();
                        String screenshot_3 = themesList.get(position).getscreenshot_3();
                        String description = themesList.get(position).getdescription();


                        Intent Detailsactivity = new Intent(Screen1Free.this, Details.class);



                        Detailsactivity.putExtra("free", free);
                        Detailsactivity.putExtra("keytitle", title);
                        Detailsactivity.putExtra("keylink", link);
                        Detailsactivity.putExtra("keygoogleplus", googleplus);
                        Detailsactivity.putExtra("keypromo", promo);
                        Detailsactivity.putExtra("keyscreenshot_1", screenshot_1);
                        Detailsactivity.putExtra("keyscreenshot_2", screenshot_2);
                        Detailsactivity.putExtra("keyscreenshot_3", screenshot_3);
                        Detailsactivity.putExtra("keydescription", description);
                        Detailsactivity.putExtra("keydeveloper", developer);

                        Bundle bndlanimation =
                                ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anni1, R.anim.anni2).toBundle();
                        startActivity(Detailsactivity, bndlanimation);
                    }
                })
        );
        //initialize swipetorefresh

        mSwipeRefresh.setColorSchemeResources(R.color.accent,R.color.primary);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               // themesList.clear();
              //  new JSONAsyncTask().execute("https://github.com/BitSyko/layers_showcase_json/releases/download/master-v0.1.2/showcase.json");
             //   onItemsLoadComplete();
            }

            void onItemsLoadComplete(){
            }
        });
    }


    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/temp.json");

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
          //  mWakeLock.release();
            if (result != null)
                Toast.makeText(context,"Download error: "+result, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context,"checking for new themes", Toast.LENGTH_SHORT).show();
        }
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

                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);


                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("Themes");

                    Random rnd = new Random();
                    for (int i = jarray.length() - 1; i >= 0; i--)
                    {
                        int j = rnd.nextInt(i + 1);

                        // Simple swap
                        JSONObject object = jarray.getJSONObject(j);
                        jarray.put(j, jarray.get(i));
                        jarray.put(i, object);
                        Themes theme = new Themes();

                        theme.settitle(object.getString("title"));
                        theme.setdescription(object.getString("description"));
                        theme.setauthor(object.getString("author"));
                        theme.setlink(object.getString("link"));
                        theme.seticon(object.getString("icon"));
                        theme.setpromo(object.getString("promo"));
                        theme.setscreenshot_1(object.getString("screenshot_1"));
                        theme.setscreenshot_2(object.getString("screenshot_2"));
                        theme.setscreenshot_3(object.getString("screenshot_3"));
                        theme.setgoogleplus(object.getString("googleplus"));
                        theme.setversion(object.getString("version"));
                        theme.setdonate_link(object.getString("donate_link"));
                        theme.setdonate_version(object.getString("donate_version"));
                        theme.setbootani(object.getString("bootani"));
                        theme.setfont(object.getString("font"));
                        theme.setwallpaper(object.getString("wallpaper"));
                        theme.setplugin_version(object.getString("plugin_version"));
                        theme.setfor_L(object.getString("for_L"));
                        theme.setfor_M(object.getString("for_M"));
                        theme.setbasic(object.getString("basic"));
                        theme.setbasic_m(object.getString("basic_m"));
                        theme.settype2(object.getString("type2"));
                        theme.settype3(object.getString("type3"));
                        theme.settype3_m(object.getString("type3_m"));
                        theme.settouchwiz(object.getString("touchwiz"));
                        theme.setlg(object.getString("lg"));
                        theme.setsense(object.getString("sense"));
                        theme.setxperia(object.getString("xperia"));
                        theme.sethdpi(object.getString("hdpi"));
                        theme.setmdpi(object.getString("mdpi"));
                        theme.setxhdpi(object.getString("xhdpi"));
                        theme.setxxhdpi(object.getString("xxhdpi"));
                        theme.setxxxhdpi(object.getString("xxhdpi"));
                        theme.setfree(object.getString("free"));
                        theme.setdonate(object.getString("donate"));
                        theme.setpaid(object.getString("paid"));

                        if (theme.getfree().contains("true")) {
                            themesList.add(theme);
                        }
                    }
                    return true;
                }

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
            if(result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
            mSwipeRefresh.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefresh.setRefreshing(false);
                }
            });

        }
    }

}