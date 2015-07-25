package com.lovejoy777.showcase;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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


        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        // Get String SZP
        final Intent extras = getIntent();

        final Theme theme = (Theme) extras.getSerializableExtra("theme");

        // Asign Views
        ImageView promoimg = (ImageView) findViewById(R.id.promo);
        TextView txt2 = (TextView) findViewById(R.id.tvdescription);
        TextView developertv = (TextView) findViewById(R.id.tvDeveloper);

        // Set text & image Views
        collapsingToolbar.setTitle(theme.getTitle());
        new ImageLoadTaskPromo(theme.getPromo(), promoimg).execute();
        txt2.setText(theme.getDescription());
        developertv.setText(theme.getAuthor());

        // Scroll view with screenshots
        LinearLayout screenshotLayout = (LinearLayout) findViewById(R.id.LinearLayoutScreenshots);

        for (int i = 0; i < 3; i++) {

            LinearLayout linear = new LinearLayout(this);

            int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());

            LinearLayout.LayoutParams params
                    = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


            params.rightMargin = margin;

            ScreenshotimageView[i] = new ImageView(this);
            ScreenshotimageView[i].setBackgroundColor(getResources().getColor(R.color.accent));

            linear.setLayoutParams(params);
            linear.addView(ScreenshotimageView[i]);
            screenshotLayout.addView(linear);

            ScreenshotimageView[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FullScreenActivity.launch(activity, (ImageView) view, "img");
                }
            });


        }


        new ImageLoadTask(this, theme.getScreenshot_1(), ScreenshotimageView[0]).execute();
        new ImageLoadTask(this, theme.getScreenshot_2(), ScreenshotimageView[1]).execute();
        new ImageLoadTask(this, theme.getScreenshot_3(), ScreenshotimageView[2]).execute();


        // Install button
        Button installbutton;
        installbutton = (Button) findViewById(R.id.button);

        installbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String link = theme.isDonate() ? theme.getDonate_link() : theme.getLink();

                Intent installtheme = new Intent(Intent.ACTION_VIEW, Uri.parse(link));

                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anni1, R.anim.anni2).toBundle();
                startActivity(installtheme, bndlanimation);

            }
        }); // End install button

        // Google Plus button
        Button googleplusbutton;
        googleplusbutton = (Button) findViewById(R.id.button2);

        googleplusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent googleplustheme = new Intent(Intent.ACTION_VIEW, Uri.parse(theme.getGoogleplus()));

                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anni1, R.anim.anni2).toBundle();
                startActivity(googleplustheme, bndlanimation);

            }
        }); // End Google Plus button

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

}

