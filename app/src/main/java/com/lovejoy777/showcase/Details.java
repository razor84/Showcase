package com.lovejoy777.showcase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

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

        String title = extras.getStringExtra("keytitle");
        String screenshot_1 = extras.getStringExtra("keyscreenshot_1");
        String screenshot_2 = extras.getStringExtra("keyscreenshot_2");
        String screenshot_3 = extras.getStringExtra("keyscreenshot_3");
        String description = extras.getStringExtra("keydescription");

        TextView txt1 = (TextView) findViewById(R.id.tvtitle);
        ImageView img1= (ImageView) findViewById(R.id.screenshot_1);
        ImageView img2= (ImageView) findViewById(R.id.screenshot_2);
        ImageView img3= (ImageView) findViewById(R.id.screenshot_3);
        TextView txt2 = (TextView) findViewById(R.id.tvdescription);

        txt1.setText(title);
        new ImageLoadTask(screenshot_1, img1).execute();
        new ImageLoadTask(screenshot_2, img2).execute();
        new ImageLoadTask(screenshot_3, img3).execute();
        txt2.setText(description);

    }
}

