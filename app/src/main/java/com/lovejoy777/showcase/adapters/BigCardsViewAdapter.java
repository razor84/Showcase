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

public class BigCardsViewAdapter extends AbsFilteredCardViewAdapter {

    public BigCardsViewAdapter(ArrayList<Layer> layers, Context context) {
        super(layers, R.layout.adapter_card_layout_big_image, context);
    }

    @Override
    public BigCardsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new BigCardsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AbsViewHolder holder, int i) {

        BigCardsViewHolder viewHolder = (BigCardsViewHolder) holder;

        Layer layer = filteredLayers.get(i);
        viewHolder.themeName.setText(layer.getTitle());
        viewHolder.themeDeveloper.setText(layer.getAuthor());

        Picasso.with(mContext).load(layer.getPromo()).placeholder(R.drawable.load).fit().centerCrop().error(R.drawable.loading_error).into(viewHolder.themeImage);


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
