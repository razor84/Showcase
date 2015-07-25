package com.lovejoy777.showcase;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {
    private Activity context;
    private String url;
    private ImageView imageView;

    public ImageLoadTask(Activity context, String url, ImageView imageView) {
        this.url = url;
        this.imageView = imageView;
        this.context = context;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        if (!(result == null)) {

            Point point = new Point();

            //Get scale (picture height = 1/2 device height)
            context.getWindowManager().getDefaultDisplay().getRealSize(point);

            int newHeight = point.y / 2;

            float scale = (float) newHeight / (float) result.getHeight();

            //  imageView.setImageBitmap(result);
            imageView.setImageBitmap(Bitmap.createScaledBitmap(result, (int) (result.getWidth() * scale), (int) (result.getHeight() * scale), true));
        } else {
            Toast.makeText(context, "There seems to be a problem with the selected Theme", Toast.LENGTH_SHORT).show();
        }
    }
}
