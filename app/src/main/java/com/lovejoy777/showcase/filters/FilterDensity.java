package com.lovejoy777.showcase.filters;

import com.lovejoy777.showcase.beans.Layer;
import com.lovejoy777.showcase.enums.Density;

public class FilterDensity extends Filter {
    private Density density;

    public FilterDensity(Density density) {
        this.density = density;
    }

    @Override
    public boolean filterTheme(Layer layer) {
        return layer.isSupportingDpi(density);
    }

}