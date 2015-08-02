package com.lovejoy777.showcase.filters;

import com.lovejoy777.showcase.beans.Layer;

public class FilterNameAuthor extends Filter {
    private String themeNameAuthor;

    public FilterNameAuthor(String themeNameAuthor) {
        this.themeNameAuthor = themeNameAuthor;
    }

    @Override
    public boolean filterTheme(Layer layer) {
        return themeNameAuthor.isEmpty()
                || layer.getAuthor().toLowerCase().contains(themeNameAuthor.toLowerCase())
                || layer.getTitle().toLowerCase().contains(themeNameAuthor.toLowerCase());
    }

}