package com.lovejoy777.showcase.filters;

import com.lovejoy777.showcase.beans.Layer;

public abstract class Filter {
    public abstract boolean filterTheme(Layer layer);

    @Override
    public boolean equals(Object o) {
        return o.getClass().equals(getClass());
    }

}