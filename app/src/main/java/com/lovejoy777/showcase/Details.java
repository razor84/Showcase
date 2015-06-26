package com.lovejoy777.showcase;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by lovejoy777 on 24/06/15.
 */
public class Details extends AppCompatActivity {

    final ImageView ScreenshotimageView[] = new ImageView[3];
    Bitmap bitmap[] = new Bitmap[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        // Handle Toolbar
        final android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        // GET STRING SZP
        final Intent extras = getIntent();

        // GET STRINGS
        String title = extras.getStringExtra("keytitle");
       final String link = extras.getStringExtra("keylink");
        String screenshot_1 = extras.getStringExtra("keyscreenshot_1");
        String screenshot_2 = extras.getStringExtra("keyscreenshot_2");
        String screenshot_3 = extras.getStringExtra("keyscreenshot_3");
        String description = extras.getStringExtra("keydescription");
        String developer = extras.getStringExtra("keydeveloper");

        // ASIGN VIEWS
        //TextView txt1 = (TextView) findViewById(R.id.tvtitle);
        ImageView img1= (ImageView) findViewById(R.id.backdrop);
        //ImageView img2= (ImageView) findViewById(R.id.screenshot_2);
        //ImageView img3= (ImageView) findViewById(R.id.screenshot_3);
        TextView txt2 = (TextView) findViewById(R.id.tvdescription);
        TextView developertv = (TextView) findViewById(R.id.tvDeveloper);

        // SET TEXT/IMAGE VIEWS
        collapsingToolbar.setTitle(title);
        //new ImageLoadTask(screenshot_2, img2).execute();
        //new ImageLoadTask(screenshot_3, img3).execute();
        txt2.setText(description);
        developertv.setText(developer);


        //Scroll view with screenshots
        LinearLayout screenshotLayout = (LinearLayout)findViewById(R.id.LinearLayoutScreenshots);

        for (int i=0; i<3;i++){
            LinearLayout linear = new LinearLayout(this);

            int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());

            LinearLayout.LayoutParams params
                    = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


            params.rightMargin = margin;


            ScreenshotimageView[i]= new ImageView(this);

            ScreenshotimageView[i].setBackgroundColor(getResources().getColor(R.color.accent));

            linear.setLayoutParams(params);

            linear.addView(ScreenshotimageView[i]);
            screenshotLayout.addView(linear);

        }

        new ImageLoadTask(screenshot_1, ScreenshotimageView[0]).execute();
        new ImageLoadTask(screenshot_2, ScreenshotimageView[1]).execute();
        new ImageLoadTask(screenshot_3, ScreenshotimageView[2]).execute();



        // INSTALL BUTTON
        Button installbutton;
        installbutton = (Button) findViewById(R.id.button);

        installbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(Details.this, "https://" + link, Toast.LENGTH_LONG).show();
                         Intent installtheme = new Intent(Intent.ACTION_VIEW, Uri.parse(link));

                         Bundle bndlanimation =
                                 ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anni1, R.anim.anni2).toBundle();
                         startActivity(installtheme, bndlanimation);

            }
        }); // end INSTALLBUTTON

    }

}

