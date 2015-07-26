package com.lovejoy777.showcase;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created by lovejoy777 on 03/07/15.
 */
public class MainActivity1 extends AppCompatActivity {

    CardView card1, card2, card3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ENDS SWVALUE ELSE
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        setSupportActionBar(toolbar);

        // mk dir showcase
        File dir = new File(Environment.getExternalStorageDirectory() + "/showcase");
        if (!dir.exists()) {
            dir.mkdir();
        }

        // mk dir showcase/tagname
        File dir1 = new File(Environment.getExternalStorageDirectory() + "/showcase/tagname");
        if (!dir1.exists()) {
            dir1.mkdir();
        }

        // mk dir showcase/showcasejson
        File dir2 = new File(Environment.getExternalStorageDirectory() + "/showcase/showcasejson");
        if (!dir2.exists()) {
            dir2.mkdir();
        }

        // download tagname.json
        final DownloadTagnameTask downloadTagnameTask = new DownloadTagnameTask(MainActivity1.this);
        downloadTagnameTask.execute("https://api.github.com/repos/BitSyko/layers_showcase_json/releases/latest");

        /**   // if /showcase/tagname/tagname.json doesnt exist then copy file.
         File dir3 = new File(Environment.getExternalStorageDirectory() + "/showcase/tagname/tagname.json");
         if (!dir3.exists()) {
         try
         {
         InputStream in = new FileInputStream(Environment.getExternalStorageDirectory() + "/showcase/tagname.json");
         OutputStream out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/showcase/tagname/tagname.json");

         // Copy the bits from instream to outstream
         byte[] buf = new byte[1024];
         int len;
         while ((len = in.read(buf)) > 0) {
         out.write(buf, 0, len);
         }
         in.close();
         out.close();
         }
         catch (IOException e)
         {
         e.printStackTrace();
         }
         } */

        card1 = (CardView) findViewById(R.id.CardView_freethemes1);
        card2 = (CardView) findViewById(R.id.CardView_paidthemes2);
        card3 = (CardView) findViewById(R.id.CardView_nonpsthemes3);

        // CARD 1
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent freeactivity = new Intent(MainActivity1.this, Screen1Free.class);
                freeactivity.putExtra("type", "Free");

                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anni1, R.anim.anni2).toBundle();
                startActivity(freeactivity, bndlanimation);

            }
        }); // end card1

        // CARD 2
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent donateactivity = new Intent(MainActivity1.this, Screen1Free.class);
                donateactivity.putExtra("type", "Paid");

                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anni1, R.anim.anni2).toBundle();
                startActivity(donateactivity, bndlanimation);

            }
        }); // end card2

        // CARD 3
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent noneplaystoreactivity = new Intent(MainActivity1.this, Screen1Free.class);
                noneplaystoreactivity.putExtra("type", "Donate");

                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anni1, R.anim.anni2).toBundle();
                startActivity(noneplaystoreactivity, bndlanimation);

            }
        }); // end card3

    } // ends onCreate

    private class DownloadTagnameTask extends AsyncTask<String, Integer, String> {

        private Context context;

        public DownloadTagnameTask(Context context) {
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
                output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/showcase/tagname.json");

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
                Toast.makeText(context, "Download tagname error: ", Toast.LENGTH_LONG).show();

            else


                movetagnamejson();

            // compare new tagname json with existing tagname
            try {

                File tagname1 = new File(Environment.getExternalStorageDirectory() + "/showcase/tagname.json");
                FileInputStream stream1 = new FileInputStream(tagname1);
                String jString1 = null;
                try {
                    FileChannel fc = stream1.getChannel();
                    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
                /* Instead of using default, pass in a decoder. */
                    jString1 = Charset.defaultCharset().decode(bb).toString();
                } finally {
                    stream1.close();
                }

                JSONObject jObject1 = new JSONObject(jString1);

                String tag_name1 = jObject1.getString("tag_name");

                File tagname = new File(Environment.getExternalStorageDirectory() + "/showcase/tagname/tagname.json");
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

                JSONObject jObject = new JSONObject(jString);

                String tag_name = jObject.getString("tag_name");

                // check new against existing tagnames
                if (!tag_name1.equals(tag_name)) {

                    //Toast.makeText(getApplicationContext(), "update", Toast.LENGTH_LONG).show();
                    // cp new tagname json to existing tagname json
                    InputStream in = new FileInputStream(Environment.getExternalStorageDirectory() + "/showcase/tagname.json");
                    OutputStream out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/showcase/tagname/tagname.json");

                    // Copy the bits from instream to outstream
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();

                    // if update is available download showcase.json
                    final DownloadShowcaseTask downloadShowcaseTask = new DownloadShowcaseTask(MainActivity1.this);
                    downloadShowcaseTask.execute("https://github.com/BitSyko/layers_showcase_json/releases/download/" + tag_name + "/showcase.json");

                }


                // if showcase.json doesnt exist download
                File dir4 = new File(Environment.getExternalStorageDirectory() + "/showcase/showcasejson/showcase.json");
                if (!dir4.exists()) {

                    // download showcase.json
                    final DownloadShowcaseTask downloadShowcaseTask = new DownloadShowcaseTask(MainActivity1.this);
                    downloadShowcaseTask.execute("https://github.com/BitSyko/layers_showcase_json/releases/download/" + tag_name + "/showcase.json");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public Void movetagnamejson() {

        // if /showcase/tagname/tagname.json doesnt exist then copy file.
        File dir3 = new File(Environment.getExternalStorageDirectory() + "/showcase/tagname/tagname.json");
        if (!dir3.exists()) {
            try {
                InputStream in = new FileInputStream(Environment.getExternalStorageDirectory() + "/showcase/tagname.json");
                OutputStream out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/showcase/tagname/tagname.json");

                // Copy the bits from instream to outstream
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private class DownloadShowcaseTask extends AsyncTask<String, Integer, String> {

        ProgressDialog progressShowcase;

        protected void onPreExecute() {

            progressShowcase = ProgressDialog.show(MainActivity1.this, "Downloading",
                    "updating themes...", true);
        }

        private Context context;

        public DownloadShowcaseTask(Context context) {
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

                // download showcase json file
                input = connection.getInputStream();
                output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/showcase/showcasejson/showcase.json");

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
            progressShowcase.dismiss();
            if (result != null)
                Toast.makeText(context, "Download showcase error: ", Toast.LENGTH_LONG).show();


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.back2, R.anim.back1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_settings:
                Intent intent1 = new Intent();
                intent1.setClass(this, Settings.class);
                startActivity(intent1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}