package com.lovejoy777.showcase.filters;

import com.lovejoy777.showcase.beans.Layer;
import com.lovejoy777.showcase.enums.AndroidVersion;

public class FilterSystemVersion extends Filter {
    private AndroidVersion androidVersion;

    public FilterSystemVersion(AndroidVersion androidVersion) {
        this.androidVersion = androidVersion;
    }

    @Override
    public boolean filterTheme(Layer layer) {
        return layer.isSupportingAndroidVersion(androidVersion);
    }

}