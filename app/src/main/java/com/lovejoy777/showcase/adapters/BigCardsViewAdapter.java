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

public class BigCardsViewAdapter extends AbsFilteredCardViewAdapter {

    public BigCardsViewAdapter(ArrayList<Theme> themes, Context context) {
        super(themes, R.layout.adapter_card_layout_big_image, context);
    }

    @Override
    public BigCardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new BigCardsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AbsViewHolder holder, int i) {

        BigCardsViewHolder viewHolder = (BigCardsViewHolder) holder;

        Theme theme = filteredThemes.get(i);
        viewHolder.themeName.setText(theme.getTitle());
        viewHolder.themeDeveloper.setText(theme.getAuthor());

        Picasso.with(mContext).load(theme.getPromo()).placeholder(R.drawable.loadingpromo).fit().centerCrop().into(viewHolder.themeImage);


    }

    public static class BigCardsViewHolder extends AbsViewHolder {

        public TextView themeName;
        public TextView themeDeveloper;
        public ImageView themeImage;

        public BigCardsViewHolder(View itemView) {
            super(itemView);
            themeName = (TextView) itemView.findViewById(R.id.txtName);
            themeImage = (ImageView) itemView.findViewById(R.id.iv_themeImage);
            themeDeveloper = (TextView) itemView.findViewById(R.id.txtSurname);
        }
    }


}
