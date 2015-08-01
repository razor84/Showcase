package com.lovejoy777.showcase.filters;

import com.lovejoy777.showcase.beans.Theme;
import com.lovejoy777.showcase.enums.AndroidPlatform;

public class FilterSystemPlatform extends Filter {
    private AndroidPlatform platform;

    public FilterSystemPlatform(AndroidPlatform platform) {
        this.platform = platform;
    }

    @Override
    public boolean filterTheme(Theme theme) {
        return theme.isSupportingAndroidPlatform(platform);
    }

}