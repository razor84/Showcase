package com.lovejoy777.showcase;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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


        card1 = (CardView) findViewById(R.id.CardView_freethemes1);
        card2 = (CardView) findViewById(R.id.CardView_paidthemes2);
        card3 = (CardView) findViewById(R.id.CardView_nonpsthemes3);

        // CARD 1
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent freeactivity = new Intent(MainActivity1.this, Screen1Free.class);

                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anni1, R.anim.anni2).toBundle();
                startActivity(freeactivity, bndlanimation);

            }
        }); // end card1

        // CARD 2
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent donateactivity = new Intent(MainActivity1.this, Screen1Paid.class);

                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anni1, R.anim.anni2).toBundle();
                startActivity(donateactivity, bndlanimation);

            }
        }); // end card2

        // CARD 3
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent noneplaystoreactivity = new Intent(MainActivity1.this, Screen1Donate.class);

                Bundle bndlanimation =
                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anni1, R.anim.anni2).toBundle();
                startActivity(noneplaystoreactivity, bndlanimation);

            }
        }); // end card3



    } // ends onCreate

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
            case R.id.action_settings:
                Intent intent1 = new Intent();
                intent1.setClass(this, Settings.class);
                startActivity(intent1);
                break;


        }


        return super.onOptionsItemSelected(item);


    }

}