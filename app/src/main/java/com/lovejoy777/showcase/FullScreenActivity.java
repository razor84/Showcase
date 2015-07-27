package com.lovejoy777.showcase;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

public class FullScreenActivity extends Activity {

    //FIXME:
    private static Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen);

        ImageView image = (ImageView) findViewById(R.id.image);

        Glide.with(this)
                .load(getIntent().getStringExtra("url"))
                .fitCenter()
                .placeholder(drawable)
                .into(image);
    }

    public static void launch(Activity activity, ImageView transitionView, String url, String id) {

        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, transitionView, id);

        drawable = transitionView.getDrawable();
        transitionView.setTransitionName(id);
        Intent intent = new Intent(activity, FullScreenActivity.class);
        intent.putExtra("url", url);
        ActivityCompat.startActivity(activity, intent, options.toBundle());

    }

    public void click(View view) {
        this.finishAfterTransition();
    }


}