package com.lovejoy777.showcase;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by lovejoy777 on 23/06/15.
 */
public class Screen1Adapter extends ArrayAdapter<Themes> {
    ArrayList<Themes> themeList;
    ArrayList<Themes> themeList1;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;






    public Screen1Adapter(Context context, int resource, ArrayList<Themes> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.iconview = (ImageView) v.findViewById(R.id.ivicon);
            holder.tvtitle = (TextView) v.findViewById(R.id.tvtitle);
            holder.tvauthor = (TextView) v.findViewById(R.id.tvauthor);
            holder.tvversion = (TextView) v.findViewById(R.id.tvversion);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.iconview.setImageResource(R.mipmap.ic_launcher);
        new DownloadImageTask(holder.iconview).execute(themeList.get(position).geticon());
        holder.tvtitle.setText(themeList.get(position).gettitle());
        holder.tvauthor.setText("Author: " + themeList.get(position).getauthor());
        holder.tvversion.setText("version: " + themeList.get(position).getversion());
        return v;

    }

    static class ViewHolder {
        public ImageView iconview;
        public TextView tvtitle;
        public TextView tvauthor;
        public TextView tvversion;

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmicon;

        public DownloadImageTask(ImageView bmimage) {
            this.bmicon = bmimage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap micon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                micon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return micon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmicon.setImageBitmap(result);
        }

    }
}
