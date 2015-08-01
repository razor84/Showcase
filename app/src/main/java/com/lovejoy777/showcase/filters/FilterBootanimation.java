package com.lovejoy777.showcase.filters;

import com.lovejoy777.showcase.beans.Theme;

public class FilterBootanimation extends Filter {

    @Override
    public boolean filterTheme(Theme theme) {
        return theme.isBootani();
    }

}