package com.lovejoy777.showcase;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by lovejoy777 on 24/06/15.
 */
public class Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        setSupportActionBar(toolbar);

        // GET STRING SZP
        final Intent extras = getIntent();

        // GET STRINGS
        String title = extras.getStringExtra("keytitle");
       final String link = extras.getStringExtra("keylink");
        String screenshot_1 = extras.getStringExtra("keyscreenshot_1");
        String screenshot_2 = extras.getStringExtra("keyscreenshot_2");
        String screenshot_3 = extras.getStringExtra("keyscreenshot_3");
        String description = extras.getStringExtra("keydescription");

        // ASIGN VIEWS
        TextView txt1 = (TextView) findViewById(R.id.tvtitle);
        ImageView img1= (ImageView) findViewById(R.id.screenshot_1);
        ImageView img2= (ImageView) findViewById(R.id.screenshot_2);
        ImageView img3= (ImageView) findViewById(R.id.screenshot_3);
        TextView txt2 = (TextView) findViewById(R.id.tvdescription);

        // SET TEXT/IMAGE VIEWS
        txt1.setText(title);
        new ImageLoadTask(screenshot_1, img1).execute();
        new ImageLoadTask(screenshot_2, img2).execute();
        new ImageLoadTask(screenshot_3, img3).execute();
        txt2.setText(description);

        // INSTALL BUTTON
        Button installbutton;
        installbutton = (Button) findViewById(R.id.installbutton);

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

