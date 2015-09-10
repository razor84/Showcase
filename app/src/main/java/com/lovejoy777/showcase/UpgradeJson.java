package com.lovejoy777.showcase;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.lovejoy777.showcase.Helpers.getLayersJsonFile;

public class UpgradeJson extends AsyncTask<Void, String, Void> {

    private Context context;
    private boolean force;
    private ProgressDialog progressShowcase;
    private final String jsonData = "https://api.github.com/repos/BitSyko/layers_showcase_json/releases/latest";

    public UpgradeJson(Context context, boolean force) {
        this.context = context;
        this.force = force;
    }

    @Override
    protected void onPreExecute() {
        progressShowcase = ProgressDialog.show(context, "Downloading",
                "Updating database...", true);
        progressShowcase.setCancelable(false);

    }

    @Override
    protected void onProgressUpdate(String... values) {
        Toast.makeText(context, "Download failed", Toast.LENGTH_LONG).show();
    }

    @Override
    protected Void doInBackground(Void... params) {


        String jsonInfo = downloadFile(jsonData);
        JsonNode actualObj;

        if (jsonInfo == null) {
            publishProgress("Download failed");
            return null;
        }

        try {
            actualObj = new ObjectMapper().readTree(jsonInfo);
        } catch (IOException e) {
            e.printStackTrace();
            publishProgress("Download failed");
            return null;
        }

        String tag = actualObj.get("tag_name").asText();

        //Compare tag with one in preferences
        if (!force && context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE).getString("tag", "0").equals(tag)) {
            return null;
        }


        String layersJsonUrl = "https://github.com/BitSyko/layers_showcase_json/releases/download/" + tag + "/showcase.json";


        String layersJson = downloadFile(layersJsonUrl);

        if (layersJson == null) {
            publishProgress("Download failed");
            return null;
        }

        //Just for testing
        try {
            new ObjectMapper().readTree(layersJson);
        } catch (IOException e) {
            e.printStackTrace();
            publishProgress("Download failed");
            return null;
        }

        //We update settings only after successful download
        context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE).edit().putString("tag", tag).apply();

        try {
            Files.write(layersJson, getLayersJsonFile(context), Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if ((progressShowcase != null) && progressShowcase.isShowing()) {
            progressShowcase.dismiss();
        }
    }


    private String downloadFile(String url) {

        InputStream inputStream = null;
        HttpURLConnection connection = null;
        String theString;

        try {

            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            // download showcase json file
            inputStream = connection.getInputStream();

            theString = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));

        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException ignored) {

            }

            if (connection != null)
                connection.disconnect();
        }


        return theString;

    }


}
