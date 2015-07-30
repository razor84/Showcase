package com.lovejoy777.showcase.filters;

import com.lovejoy777.showcase.Theme;

public class FilterFont extends Filter {

    @Override
    public boolean filterTheme(Theme theme) {
        return theme.isFont();
    }

}