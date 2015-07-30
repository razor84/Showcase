package com.lovejoy777.showcase.filters;

import com.lovejoy777.showcase.Theme;
import com.lovejoy777.showcase.enums.AndroidVersion;

public class FilterSystemVersion extends Filter {
    private AndroidVersion androidVersion;

    public FilterSystemVersion(AndroidVersion androidVersion) {
        this.androidVersion = androidVersion;
    }

    @Override
    public boolean filterTheme(Theme theme) {
        return theme.isSupportingAndroidVersion(androidVersion);
    }

}