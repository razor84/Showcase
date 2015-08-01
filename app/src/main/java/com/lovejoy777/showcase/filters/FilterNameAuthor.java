package com.lovejoy777.showcase.filters;

import com.lovejoy777.showcase.beans.Theme;

public class FilterNameAuthor extends Filter {
    private String themeNameAuthor;

    public FilterNameAuthor(String themeNameAuthor) {
        this.themeNameAuthor = themeNameAuthor;
    }

    @Override
    public boolean filterTheme(Theme theme) {
        return themeNameAuthor.isEmpty()
                || theme.getAuthor().toLowerCase().contains(themeNameAuthor.toLowerCase())
                || theme.getTitle().toLowerCase().contains(themeNameAuthor.toLowerCase());
    }

}