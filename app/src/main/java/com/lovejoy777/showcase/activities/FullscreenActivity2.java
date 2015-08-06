package com.lovejoy777.showcase.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;

import com.lovejoy777.showcase.R;
import com.squareup.picasso.Picasso;

public class FullscreenActivity2 extends Activity {

    //#BlameAndrew
    private static Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen);

        int bgColor = ((BitmapDrawable) drawable).getBitmap().getPixel(0, 0);

        final ImageView image = (ImageView) findViewById(R.id.image);
        image.setBackgroundColor(bgColor);

        getWindow().setNavigationBarColor(bgColor);
        getWindow().setStatusBarColor(bgColor);


        Picasso.with(this)
                .load(getIntent().getStringExtra("url"))
                .fit()
                .centerInside()
                .placeholder(drawable)
                .into(image);

    }

    public static void launch(Activity activity, ImageView transitionView, String id) {

        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, transitionView, id);

        drawable = transitionView.getDrawable();
        transitionView.setTransitionName(id);
        Intent intent = new Intent(activity, FullscreenActivity2.class);
        intent.putExtra("url", (String) transitionView.getTag());

        ActivityCompat.startActivity(activity, intent, options.toBundle());

    }

    public void click(View view) {
        this.finishAfterTransition();
    }

}