package com.lovejoy777.showcase.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.lovejoy777.showcase.R;
import com.lovejoy777.showcase.Theme;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    private ArrayList<Theme> themes;
    private ArrayList<Theme> filteredThemes;
    private int rowLayout;
    private Context mContext;
    private  int mode;

    public CardViewAdapter(ArrayList<Theme> themes, int rowLayout, Context context,int mode) {
        this.themes = themes;
        this.filteredThemes = new ArrayList<Theme>(themes);
        this.rowLayout = rowLayout;
        this.mContext = context;
        this.mode = mode;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Theme theme = filteredThemes.get(i);
        viewHolder.themeName.setText(theme.getTitle());
        viewHolder.themeDeveloper.setText(theme.getAuthor());
        if (mode==1) {
            Picasso.with(mContext).load(theme.getPromo()).placeholder(R.drawable.loadingpromo).fit().centerCrop().into(viewHolder.themeImage);
        }else{
            Picasso.with(mContext).load(theme.getIcon()).placeholder(R.mipmap.ic_launcher).noFade().into(viewHolder.themeImage);
        }
    }

    public Theme getItem(int id) {
        return filteredThemes.get(id);
    }

    public void filter(String filter) {

        filteredThemes.clear();

        if (filter == null || filter.isEmpty() || filter.equals("")) {
            filteredThemes.addAll(themes);
            notifyDataSetChanged();
            return;
        }

        for (Theme theme : themes) {
            if (theme.getTitle().toLowerCase().contains(filter)) {
                filteredThemes.add(theme);
            }
        }

        notifyDataSetChanged();

    }


    @Override
    public int getItemCount() {
        return filteredThemes.size();
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
