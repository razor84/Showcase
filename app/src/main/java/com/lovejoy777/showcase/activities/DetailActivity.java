package com.lovejoy777.showcase.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
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
import com.lovejoy777.showcase.beans.Theme;
import com.lovejoy777.showcase.enums.AndroidVersion;
import com.lovejoy777.showcase.enums.Density;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity {

    private Activity activity;
    final ImageView ScreenshotimageView[] = new ImageView[3];
    ImageView promoimg;
    Theme theme;
    CollapsingToolbarLayout collapsingToolbar;
    android.support.v7.widget.Toolbar toolbar;

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

        theme = (Theme) extras.getSerializableExtra("theme");

        // Asign Views
        promoimg = (ImageView) findViewById(R.id.promo);
        ImageView icon = (ImageView) findViewById(R.id.icon);
        TextView txt2 = (TextView) findViewById(R.id.textView2);
        TextView developertv = (TextView) findViewById(R.id.textView);

        // Set text & image Views
        collapsingToolbar.setTitle(theme.getTitle());

        Picasso.with(this).load(theme.getIcon()).placeholder(R.drawable.ic_launcher).into(icon);

        new DownloadPromo().execute();


        txt2.setText(theme.getDescription());
        developertv.setText("by " + theme.getAuthor());

        // Scroll view with screenshots
        LinearLayout screenshotLayout = (LinearLayout) findViewById(R.id.LinearLayoutScreenshots);

        final String[] screenshotsUrls = {
                theme.getScreenshot_1(),
                theme.getScreenshot_2(),
                theme.getScreenshot_3()};

        float height = screenSize.y / 2;

        Log.d("Height: ", String.valueOf(height));

        for (int i = 0; i < 3; i++) {
            ScreenshotimageView[i] = new ImageView(this);

            screenshotLayout.addView(ScreenshotimageView[i]);

            final int finalI = i;
            ScreenshotimageView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FullscreenActivity2.launch(activity, (ImageView) view, screenshotsUrls[finalI], "img");
                }
            });

            Picasso.with(this)
                    .load(screenshotsUrls[finalI])
                    .centerInside()
                    .resize((int) (height * 0.66), (int) height)
                    .into(ScreenshotimageView[finalI]);
        }

        // Get Theme button
        Button installbutton;
        installbutton = (Button) findViewById(R.id.button);

        installbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String link = theme.getLink();

                Intent installtheme = new Intent(Intent.ACTION_VIEW, Uri.parse(link));

                startActivity(installtheme);

            }
        }); // End Get Theme button

        // Donate button
        LinearLayout donatebutton;
        donatebutton = (LinearLayout) findViewById(R.id.donateLayout);
        String link = theme.getDonate_link();

        if (link.equals("false")) {

            donatebutton.setVisibility(View.GONE);
        }

        donatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String link = theme.getDonate_link();


                Intent donatetheme = new Intent(Intent.ACTION_VIEW, Uri.parse(link));

                startActivity(donatetheme);

            }
        }); // End Donate button */

        // Info button
        Button infobutton;
        infobutton = (Button) findViewById(R.id.button2);

        infobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent googleplustheme = new Intent(Intent.ACTION_VIEW, Uri.parse(theme.getGoogleplus()));

                startActivity(googleplustheme);

            }
        }); // End Info button


        //Properties table


        TextView screendensity = (TextView) findViewById(R.id.textView7);
        ImageView screendensityImage = (ImageView) findViewById(R.id.imageView3);


        TextView androidversion = (TextView) findViewById(R.id.textView4);
        ImageView androidversionImage = (ImageView) findViewById(R.id.imageView4);

        AndroidVersion androidVersion = Helpers.getSystemVersion();

        if (androidVersion == AndroidVersion.Lollipop && theme.isFor_L()) {
            androidversion.setText(getString(R.string.supportsL));
            androidversionImage.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
        } else if (androidVersion == AndroidVersion.M && theme.isFor_M()) {
            androidversion.setText(getString(R.string.supportsM));
            androidversionImage.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
        } else {
            androidversionImage.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            androidversion.setText(getString(R.string.nosupport));
        }

        Density deviceDensity = Helpers.getDensity(this);

        if (theme.isSupportingDpi(deviceDensity)) {
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


    private class DownloadPromo extends AsyncTask<Void, Void, Pair<Bitmap, Palette>> {

        @Override
        protected void onPreExecute() {
            promoimg.setImageResource(R.drawable.loadingpromo);
            super.onPreExecute();
        }


        @Override
        protected Pair<Bitmap, Palette> doInBackground(Void... params) {

            try {
                Bitmap bitmap = Picasso.with(DetailActivity.this).load(theme.getPromo()).get();
                Palette p = Palette.from(bitmap).generate();

                return new Pair<Bitmap, Palette>(bitmap, p);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }


        @Override
        protected void onPostExecute(Pair<Bitmap, Palette> bitmapIntegerPair) {

            promoimg.setImageBitmap(bitmapIntegerPair.first);
            Button info = (Button) findViewById(R.id.button);
            Button download = (Button) findViewById(R.id.button2);
            Palette.Swatch swatch = bitmapIntegerPair.second.getVibrantSwatch();

            //If doesn't exist choose the most dominant one (if you have better idea - do pull request)
            if (swatch == null) {
                swatch = Helpers.getDominantSwatch(bitmapIntegerPair.second);
            }

            if (swatch != null) {

                //collapsingToolbar.setCollapsedTitleTextColor(swatch.getTitleTextColor());
                collapsingToolbar.setContentScrimColor(swatch.getRgb());
                info.setBackgroundColor(swatch.getRgb());
                download.setBackgroundColor(swatch.getRgb());

                float[] hsv = new float[3];
                Color.colorToHSV(swatch.getRgb(), hsv);
                hsv[2] *= 0.8f;
                Window window = DetailActivity.this.getWindow();
                window.setStatusBarColor(Color.HSVToColor(hsv));

            }


            super.onPostExecute(bitmapIntegerPair);
        }


    }

}
