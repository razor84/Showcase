package com.lovejoy777.showcase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lovejoy777.showcase.R;
import com.lovejoy777.showcase.beans.Layer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SmallCardsViewAdapter extends AbsFilteredCardViewAdapter {

    public SmallCardsViewAdapter(ArrayList<Layer> layers, Context context) {
        super(layers, R.layout.adapter_card_layout, context);
    }

    @Override
    public SmallCardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new SmallCardsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AbsViewHolder holder, int i) {

        SmallCardsViewHolder viewHolder = (SmallCardsViewHolder) holder;

        Layer layer = filteredLayers.get(i);
        viewHolder.themeName.setText(layer.getTitle());
        viewHolder.themeDeveloper.setText(layer.getAuthor());

        Picasso.with(mContext).load(layer.getIcon()).placeholder(R.drawable.ic_launcher).noFade().into(viewHolder.themeImage);


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
