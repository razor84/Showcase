package com.lovejoy777.showcase.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    Button install;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        activity = this;

        // Handle Toolbar
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        setSupportActionBar(toolbar);
        //toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.collapsing_toolbar);

        setSupportActionBar(toolbar);
        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        final Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(screenSize);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
        install = (Button) findViewById(R.id.installLayer);

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

        }

        download.setOnClickListener(new View.OnClickListener() {
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


        info.setOnClickListener(new View.OnClickListener() {
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
    protected void onResume() {
        super.onResume();

        final String freeId = layer.getPlayStoreID();
        final String donateId = layer.getDonatePlayStoreID();

        boolean freeAppInstalled = Helpers.appInstalledOrNot(this, freeId);
        boolean donateAppInstalled = Helpers.appInstalledOrNot(this, donateId);

        boolean managerInstalled = Helpers.appInstalledOrNot(this, "com.lovejoy777.rroandlayersmanager");

        if (donateAppInstalled && managerInstalled) {

            install.setVisibility(View.VISIBLE);

            install.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("com.lovejoy777.rroandlayersmanager",
                            "com.lovejoy777.rroandlayersmanager.activities.OverlayDetailActivity"));
                    intent.putExtra("PackageName", donateId);
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(DetailActivity.this, "You need Layers Manager beta to use this", Toast.LENGTH_SHORT).show();
                    }


                }
            });

        } else if (freeAppInstalled && managerInstalled) {

            install.setVisibility(View.VISIBLE);

            install.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("com.lovejoy777.rroandlayersmanager",
                            "com.lovejoy777.rroandlayersmanager.activities.OverlayDetailActivity"));
                    intent.putExtra("PackageName", freeId);
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(DetailActivity.this, "You need Layers Manager beta to use this", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            install.setVisibility(View.GONE);

            //Clear onClickListener
            install.setOnClickListener(null);

        }

    }

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


    private void setColor(int color) {

        collapsingToolbar.setContentScrimColor(color);
        info.setBackgroundTintList(ColorStateList.valueOf(color));
        download.setBackgroundTintList(ColorStateList.valueOf(color));
        install.setBackgroundTintList(ColorStateList.valueOf(color));


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
            promoimg.setImageResource(R.drawable.load);
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
                promoimg.setImageResource(R.drawable.loading_error);
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
