package com.lovejoy777.showcase.filters;

import com.lovejoy777.showcase.Theme;
import com.lovejoy777.showcase.enums.AndroidPlatform;
import com.lovejoy777.showcase.enums.Density;

public class FilterDensity extends Filter {
    private Density density;

    public FilterDensity(Density density) {
        this.density = density;
    }

    @Override
    public boolean filterTheme(Theme theme) {
        return theme.isSupportingDpi(density);
    }

}