package com.lovejoy777.showcase;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.materialleanback.MaterialLeanBack;

/**
 * Created by Niklas on 12.09.2015.
 */
public class LeanBackViewHolder extends MaterialLeanBack.ViewHolder {

    public TextView leantextView;
    public ImageView leanimageView;
    public Button button;

    public LeanBackViewHolder(View itemView) {
        super(itemView);
        leantextView = (TextView) itemView.findViewById(R.id.leantextView);
        leanimageView = (ImageView) itemView.findViewById(R.id.leanimageView);
    }
}

