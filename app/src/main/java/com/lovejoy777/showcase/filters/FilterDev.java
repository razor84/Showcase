package com.lovejoy777.showcase.filters;

import com.lovejoy777.showcase.Theme;

public class FilterDev implements Filter {
    private String devName;

    public FilterDev(String devName) {
        this.devName = devName;
    }

    @Override
    public boolean filterTheme(Theme theme) {
        return devName.isEmpty() || theme.getAuthor().toLowerCase().contains(devName.toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        return o.getClass().equals(getClass());
    }

}
