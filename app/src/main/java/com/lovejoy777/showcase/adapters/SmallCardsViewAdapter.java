package com.lovejoy777.showcase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.lovejoy777.showcase.R;
import com.lovejoy777.showcase.beans.Theme;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SmallCardsViewAdapter extends AbsFilteredCardViewAdapter {

    public SmallCardsViewAdapter(ArrayList<Theme> themes, Context context) {
        super(themes, R.layout.adapter_card_layout, context);
    }

    @Override
    public SmallCardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new SmallCardsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AbsViewHolder holder, int i) {

        SmallCardsViewHolder viewHolder = (SmallCardsViewHolder) holder;

        Theme theme = filteredThemes.get(i);
        viewHolder.themeName.setText(theme.getTitle());
        viewHolder.themeDeveloper.setText(theme.getAuthor());

        Picasso.with(mContext).load(theme.getIcon()).placeholder(R.drawable.ic_launcher).noFade().into(viewHolder.themeImage);


    }

    public static class SmallCardsViewHolder extends AbsViewHolder {

        public TextView themeName;
        public TextView themeDeveloper;
        public ImageView themeImage;

        public SmallCardsViewHolder(View itemView) {
            super(itemView);
            themeName = (TextView) itemView.findViewById(R.id.txtName);
            themeImage = (ImageView) itemView.findViewById(R.id.iv_themeImage);
            themeDeveloper = (TextView) itemView.findViewById(R.id.txtSurname);
        }
    }


}
