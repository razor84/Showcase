package com.lovejoy777.showcase.filters;

import com.lovejoy777.showcase.beans.Theme;

public abstract class Filter {
    public abstract boolean filterTheme(Theme theme);

    @Override
    public boolean equals(Object o) {
        return o.getClass().equals(getClass());
    }

}