package com.lovejoy777.showcase.filters;

import com.lovejoy777.showcase.beans.Theme;

public class FilterName extends Filter {
    private String themeName;

    public FilterName(String themeName) {
        this.themeName = themeName;
    }

    @Override
    public boolean filterTheme(Theme theme) {
        return themeName.isEmpty() || theme.getTitle().toLowerCase().contains(themeName.toLowerCase());
    }

}