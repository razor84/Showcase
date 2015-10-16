package com.lovejoy777.showcase.DebugLayers;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;

import com.lovejoy777.showcase.R;
import com.lovejoy777.showcase.beans.Layer;

public class CustomToolbarColor extends DebugLayers {

    Layer layer = com.lovejoy777.showcase.debug.DebugLayers.debugLayerWithBlackToolbar();

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Intent i = new Intent();
        i.putExtra("theme", layer);
        setActivityIntent(i);
    }

    public void testToolbarColor() {
        Drawable drawable = ((CollapsingToolbarLayout) getActivity().findViewById(R.id.collapsing_toolbar)).getContentScrim();
        assertTrue(drawable instanceof ColorDrawable);
        assertEquals(((ColorDrawable) drawable).getColor(), Integer.parseInt(layer.getToolbar_background_color(), 16));
    }

}
