package com.lovejoy777.showcase.DebugLayers;

import android.test.ActivityInstrumentationTestCase2;

import com.lovejoy777.showcase.MainActivity1;
import com.lovejoy777.showcase.activities.DetailActivity;
import com.robotium.solo.Solo;

public abstract class DebugLayers extends ActivityInstrumentationTestCase2<DetailActivity> {

    public DebugLayers() {
        super(DetailActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }


}
