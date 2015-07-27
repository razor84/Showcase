package com.lovejoy777.showcase.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lovejoy777.showcase.R;
import com.lovejoy777.showcase.Theme;

import java.io.InputStream;
import java.util.ArrayList;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder>{

    private ArrayList<Theme> themes;
    private ArrayList<Theme> test;
    private int rowLayout;
    private Context mContext;

    public CardViewAdapter(ArrayList<Theme> themes, int rowLayout, Context context) {
        this.themes = themes;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Theme theme = themes.get(i);
        viewHolder.themeName.setText(theme.getTitle());
        viewHolder.themeDeveloper.setText(theme.getAuthor());
        Glide.with(mContext).load(theme.getIcon()).placeholder(R.mipmap.ic_launcher).into(viewHolder.themeImage);

    }

    @Override
    public int getItemCount() {
        return themes == null ? 0 : themes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView themeName;
        public TextView themeDeveloper;
        public ImageView themeImage;

        public ViewHolder(View itemView) {
            super(itemView);

                themeName = (TextView) itemView.findViewById(R.id.txtName);
                themeImage = (ImageView) itemView.findViewById(R.id.iv_themeImage);
                themeDeveloper = (TextView) itemView.findViewById(R.id.txtSurname);

        }

    }

}
