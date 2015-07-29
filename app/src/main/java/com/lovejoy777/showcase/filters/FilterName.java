package com.lovejoy777.showcase.filters;

import com.lovejoy777.showcase.Theme;

public class FilterName implements Filter {
    private String themeName;

    public FilterName(String themeName) {
        this.themeName = themeName;
    }

    @Override
    public boolean filterTheme(Theme theme) {
        return themeName.isEmpty() || theme.getTitle().toLowerCase().contains(themeName.toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        return o.getClass().equals(getClass());
    }

}
