package com.lovejoy777.showcase.filters;

import com.lovejoy777.showcase.beans.Theme;
import com.lovejoy777.showcase.enums.LayersVersion;

public class FilterLayersVersion extends Filter {
    private LayersVersion layersVersion;

    public FilterLayersVersion(LayersVersion layersVersion) {
        this.layersVersion = layersVersion;
    }

    @Override
    public boolean filterTheme(Theme theme) {
        return theme.isSupportingLayersVersion(layersVersion);
    }

}