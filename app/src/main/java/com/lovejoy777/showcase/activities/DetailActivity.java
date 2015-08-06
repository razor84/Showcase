package com.lovejoy777.showcase.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.lovejoy777.showcase.Helpers;
import com.lovejoy777.showcase.R;
import com.lovejoy777.showcase.beans.Layer;
import com.lovejoy777.showcase.enums.AndroidVersion;
import com.lovejoy777.showcase.enums.Density;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity {

    private Activity activity;
    ImageView promoimg;
    Layer layer;
    CollapsingToolbarLayout collapsingToolbar;
    android.support.v7.widget.Toolbar toolbar;
    private boolean customColors = false;
    Button info;
    Button download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        activity = this;

        // Handle ToolBar
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(screenSize);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        // Get String SZP
        final Intent extras = getIntent();

        layer = (Layer) extras.getSerializableExtra("theme");

        // Asign Views
        promoimg = (ImageView) findViewById(R.id.promo);
        ImageView icon = (ImageView) findViewById(R.id.icon);
        TextView txt2 = (TextView) findViewById(R.id.textView2);
        TextView developertv = (TextView) findViewById(R.id.textView);

        info = (Button) findViewById(R.id.moreinfo);
        download = (Button) findViewById(R.id.download);

        //Toolbar color (if available)
        if (layer.getToolbar_background_color() != null) {
            customColors = true;

            //No transparency
            int myColor = 0xFF000000 | Integer.parseInt(layer.getToolbar_background_color(), 16);
            setColor(myColor);
        }

        // Set text & image Views
        collapsingToolbar.setTitle(layer.getTitle());

        Picasso.with(this).load(layer.getIcon()).placeholder(R.drawable.ic_launcher).into(icon);

        new DownloadPromo().execute();


        txt2.setText(layer.getDescription());
        developertv.setText("by " + layer.getAuthor());

        // Scroll view with screenshots
        LinearLayout screenshotLayout = (LinearLayout) findViewById(R.id.LinearLayoutScreenshots);

        final String[] screenshotsUrls = {
                layer.getScreenshot_1(),
                layer.getScreenshot_2(),
                layer.getScreenshot_3()};

        float height = screenSize.y / 2;

        Log.d("Height: ", String.valueOf(height));

        for (String url : screenshotsUrls) {
            ImageView screenshotImageView = new ImageView(this);

            screenshotLayout.addView(screenshotImageView);

            screenshotImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FullscreenActivity2.launch(activity, (ImageView) view, "img");
                }
            });


            new DownloadScreenshot(screenshotImageView, (int) height).execute(url.split(","));

            /*
            Picasso.with(this)
                    .load(screenshotsUrls[finalI])
                    .centerInside()
                    .resize((int) (height * 0.66), (int) height)
                    .into(ScreenshotimageView[finalI]);
                    */
        }

        // Get Theme button

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String link = layer.getLink();

                Intent installtheme = new Intent(Intent.ACTION_VIEW, Uri.parse(link));

                startActivity(installtheme);

            }
        }); // End Get Theme button

        // Donate button
        LinearLayout donatebutton;
        donatebutton = (LinearLayout) findViewById(R.id.donateLayout);
        String link = layer.getDonate_link();

        if (link == null || link.equals("false")) {
            donatebutton.setVisibility(View.GONE);
        }

        donatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String link = layer.getDonate_link();

                Intent donatetheme = new Intent(Intent.ACTION_VIEW, Uri.parse(link));

                startActivity(donatetheme);

            }
        }); // End Donate button */

        // Info button

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent googleplustheme = new Intent(Intent.ACTION_VIEW, Uri.parse(layer.getGoogleplus()));

                startActivity(googleplustheme);

            }
        }); // End Info button


        //Properties table


        TextView screendensity = (TextView) findViewById(R.id.textView7);
        ImageView screendensityImage = (ImageView) findViewById(R.id.imageView3);


        TextView androidversion = (TextView) findViewById(R.id.textView4);
        ImageView androidversionImage = (ImageView) findViewById(R.id.imageView4);

        AndroidVersion androidVersion = Helpers.getSystemVersion();

        if (androidVersion == AndroidVersion.Lollipop && layer.isFor_L()) {
            androidversion.setText(getString(R.string.supportsL));
            androidversionImage.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
        } else if (androidVersion == AndroidVersion.M && layer.isFor_M()) {
            androidversion.setText(getString(R.string.supportsM));
            androidversionImage.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
        } else {
            androidversionImage.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            androidversion.setText(getString(R.string.nosupport));
        }

        Density deviceDensity = Helpers.getDensity(this);

        if (layer.isSupportingDpi(deviceDensity)) {
            screendensityImage.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            screendensity.setText(getString(R.string.densitySupport) + " " + deviceDensity.toString().toLowerCase());
        } else {
            screendensityImage.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            screendensity.setText(getString(R.string.nodensitysupport));
        }

    } // End onCreate

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.back2, R.anim.back1);
    }


    public View createRow(String text, boolean isChecked) {

        CheckBox checkBox = new CheckBox(this);
        checkBox.setText(text);
        checkBox.setChecked(isChecked);
        checkBox.setClickable(false);

        TableRow row = new TableRow(this);
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        row.addView(checkBox);

        return row;

    }

    private void setColor(int color) {

        collapsingToolbar.setContentScrimColor(color);
        info.setBackgroundColor(color);
        download.setBackgroundColor(color);


        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f;
        collapsingToolbar.setStatusBarScrimColor(Color.HSVToColor(hsv));
    }

    private class DownloadScreenshot extends AsyncTask<String, Void, Pair<Bitmap, String>> {

        ImageView screenShotView;
        int height;

        public DownloadScreenshot(ImageView screenShotView, int height) {
            this.screenShotView = screenShotView;
            this.height = height;
        }

        @Override
        protected void onPreExecute() {

            /*
            Bitmap bitmap = BitmapFactory.decodeResource(DetailActivity.this.getResources(),
                    R.drawable.loading);

            screenShotView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, (int) (height * 0.66),
                    height, true));
*/

        }

        @Override
        protected Pair<Bitmap, String> doInBackground(String... params) {

            for (String url : params) {

                String parsedUrl = url.replaceAll(" ", "");

                try {
                    return new Pair<>(Picasso.with(DetailActivity.this)
                            .load(parsedUrl)
                            .centerInside()
                            .resize((int) (height * 0.66), height)
                            .get(), parsedUrl);
                } catch (IOException | IllegalStateException | IllegalArgumentException ignored) {
                }

            }

            return null;

        }

        @Override
        protected void onPostExecute(Pair<Bitmap, String> bitmap) {

            if (bitmap != null) {
                screenShotView.setImageBitmap(bitmap.first);
                screenShotView.setTag(bitmap.second);
            } else {
                Bitmap errorBitmap = BitmapFactory.decodeResource(DetailActivity.this.getResources(),
                        R.drawable.error);

                screenShotView.setImageBitmap(Bitmap.createScaledBitmap(errorBitmap, (int) (height * 0.66),
                        height, true));
            }

        }
    }


    private class DownloadPromo extends AsyncTask<Void, Void, Pair<Bitmap, Palette>> {

        @Override
        protected void onPreExecute() {
            promoimg.setImageResource(R.drawable.loadingpromo);
            super.onPreExecute();
        }


        @Override
        protected Pair<Bitmap, Palette> doInBackground(Void... params) {

            try {
                Bitmap bitmap = Picasso.with(DetailActivity.this).load(layer.getPromo()).get();
                Palette p = Palette.from(bitmap).generate();

                return new Pair<>(bitmap, p);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (IllegalArgumentException e) {
                //No promo image
                Log.w("Promo image", "Not found or corrupted");
                return null;
            }
        }


        @Override
        protected void onPostExecute(Pair<Bitmap, Palette> bitmapIntegerPair) {

            if (bitmapIntegerPair == null) {
                //Setting error drawable
                promoimg.setImageResource(R.drawable.no_heroimage);
                return;
            }

            promoimg.setImageBitmap(bitmapIntegerPair.first);

            if (customColors) {
                return;
            }

            Palette.Swatch swatch = bitmapIntegerPair.second.getVibrantSwatch();

            //If doesn't exist choose the most dominant one (if you have better idea - do pull request)
            if (swatch == null) {
                swatch = Helpers.getDominantSwatch(bitmapIntegerPair.second);
            }

            if (swatch != null) {
                setColor(swatch.getRgb());
            }


            super.onPostExecute(bitmapIntegerPair);
        }


    }

}
