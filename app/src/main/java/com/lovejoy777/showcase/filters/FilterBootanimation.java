package com.lovejoy777.showcase.filters;

import com.lovejoy777.showcase.Theme;
import com.lovejoy777.showcase.enums.Density;

public class FilterBootanimation extends Filter {

    @Override
    public boolean filterTheme(Theme theme) {
        return theme.isBootani();
    }

}