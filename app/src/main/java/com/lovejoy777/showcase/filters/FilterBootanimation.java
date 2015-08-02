package com.lovejoy777.showcase.filters;

import com.lovejoy777.showcase.beans.Layer;

public class FilterBootanimation extends Filter {

    @Override
    public boolean filterTheme(Layer layer) {
        return layer.isBootani();
    }

}