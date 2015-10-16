package com.lovejoy777.showcase.DebugLayers.InstallButtonTests;

import android.content.Intent;
import android.view.View;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lovejoy777.showcase.DebugLayers.DebugLayers;
import com.lovejoy777.showcase.R;
import com.lovejoy777.showcase.beans.Layer;

public class OnlyDonate extends DebugLayers {

    @Override
    public void setUp() throws Exception {
        super.setUp();

        String themeWithWrongScreenshotImages = "{\n" +
                "    \"title\": \"Theme with wrong images\",\n" +
                "    \"icon\": \"NO LINK HERE\",\n" +
                "    \"screenshot_1\": \"https://plus.google.com/u/0/communities\",\n" +
                "    \"screenshot_2\": \"https://inbox.google.com/u/1/?pli=1\",\n" +
                "    \"screenshot_3\": \"vasdvas\",\n" +
                "    \"link\": \"https://play.google.com/store/apps/details?id=this.will.never.exist\",\n" +
                "    \"donate_link\": \"https://play.google.com/store/apps/details?id=com.android.settings\"\n" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Intent i = new Intent();
        i.putExtra("theme", objectMapper.readValue(themeWithWrongScreenshotImages, Layer.class));
        setActivityIntent(i);
    }


    public void testInstallButtonVisibility() {
        assertEquals(getActivity().findViewById(R.id.installLayer).getVisibility(), View.VISIBLE);
    }

}
