package com.lovejoy777.showcase;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {

    private Activity activity;
    final ImageView ScreenshotimageView[] = new ImageView[3];
    Bitmap bitmap[] = new Bitmap[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        activity = this;

        // Handle ToolBar
        final android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(screenSize);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        // Get String SZP
        final Intent extras = getIntent();

        final Theme theme = (Theme) extras.getSerializableExtra("theme");

        // Asign Views
        ImageView promoimg = (ImageView) findViewById(R.id.promo);
        ImageView icon = (ImageView) findViewById(R.id.icon);
        TextView txt2 = (TextView) findViewById(R.id.textView2);
        TextView developertv = (TextView) findViewById(R.id.textView);

        // Set text & image Views
        collapsingToolbar.setTitle(theme.getTitle());

        Picasso.with(this).load(theme.getPromo()).placeholder(R.drawable.loadingpromo).into(promoimg);
        Picasso.with(this).load(theme.getIcon()).placeholder(R.drawable.ic_launcher).into(icon);

        txt2.setText(theme.getDescription());
        developertv.setText("by "+theme.getAuthor());

        // Scroll view with screenshots
        LinearLayout screenshotLayout = (LinearLayout) findViewById(R.id.LinearLayoutScreenshots);

        //   screenshotLayout.getLayoutParams().height = screenSize.y / 2;
        //   screenshotLayout.requestLayout();

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
                    FullScreenActivity.launch(activity, (ImageView) view, screenshotsUrls[finalI], "img");
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

                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anni1, R.anim.anni2).toBundle();
                startActivity(installtheme, bndlanimation);

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

                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anni1, R.anim.anni2).toBundle();
                startActivity(donatetheme, bndlanimation);

            }
        }); // End Donate button */

        // Info button
        Button infobutton;
        infobutton = (Button) findViewById(R.id.button2);

        infobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent googleplustheme = new Intent(Intent.ACTION_VIEW, Uri.parse(theme.getGoogleplus()));

                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anni1, R.anim.anni2).toBundle();
                startActivity(googleplustheme, bndlanimation);

            }
        }); // End Info button


        //Properties table

        //LinearLayout propertiesHolder = (LinearLayout) findViewById(R.id.properties);

        TextView screendensity = (TextView) findViewById(R.id.textView7);
        ImageView screendensityImage = (ImageView) findViewById(R.id.imageView3);
        ImageView density2 = (ImageView)findViewById(R.id.density2);

        TextView androidversion = (TextView) findViewById(R.id.textView4);
        ImageView androidversionImage = (ImageView) findViewById(R.id.imageView4);
        ImageView androidversion2 = (ImageView)findViewById(R.id.androidversion2);

        density2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder installdialog = new AlertDialog.Builder(Details.this);

                installdialog.setTitle("Display density");
                installdialog.setMessage("Icon indicates if this theme supports your devices display density\nGreen = supported\nRed = not supported");
                installdialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                installdialog.show();

            }
        });

        float Density = getResources().getDisplayMetrics().density;
        if (((android.os.Build.VERSION.RELEASE.startsWith("5.0")||android.os.Build.VERSION.RELEASE.startsWith("5.1"))& theme.getFor_L().equals("true"))){
            androidversion.setText("This Theme supports your Android version (Android Lollipop)");
            androidversionImage.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
        }else {
            if (((android.os.Build.VERSION.RELEASE.startsWith("5.2")||android.os.Build.VERSION.RELEASE.startsWith("M"))& theme.getFor_M().equals("true"))){
                androidversion2.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                androidversion.setText("This Theme supports your Android version (Android M)");
                androidversionImage.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }else{
                androidversionImage.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                androidversion2.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                androidversion.setText("This Theme does not support the Android Version you are currently running.\nInstalling this theme can cause Bootloops or Force Closes.");
            }
        }
        if (Density ==3.0 & theme.isXxhdpi()){
            density2.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            screendensityImage.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            screendensity.setText("This Theme supports you devices display density which is XXHDPI");
        }else if(Density ==4.0 & theme.isXxxhdpi()){
            density2.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            screendensityImage.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            screendensity.setText("This Theme supports you devices display density which is XXXHDPI");
        }else if(Density ==2.0 & theme.isXhdpi()){
            density2.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            screendensityImage.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            screendensity.setText("This Theme supports you devices display density which is XHDPI");
        }else if(Density ==1.5 & theme.isHdpi()){
            density2.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            screendensityImage.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            screendensity.setText("This Theme supports you devices display density which is HDPI");
        }else if(Density ==1.0 & theme.isMdpi()){
            density2.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            screendensityImage.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            screendensity.setText("This Theme supports you devices display density which is MDPI");
        }else{
            density2.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            screendensityImage.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            screendensity.setText("This Theme does not support your device´s display density.\nThis may cause blurry looking drawables or drawables which are not themed.");
        }

        //propertiesHolder.addView(createRow("mdpi", theme.isMdpi()));
        //propertiesHolder.addView(createRow("hdpi", theme.isHdpi()));
        //propertiesHolder.addView(createRow("xhdpi", theme.isXhdpi()));
        //propertiesHolder.addView(createRow("xxhdpi", theme.isXxhdpi()));
       // propertiesHolder.addView(createRow("xxxhdpi", theme.isXxxhdpi()));


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

}

