package com.lovejoy777.showcase.debug;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lovejoy777.showcase.beans.Layer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DebugLayers {

    public static Collection<Layer> createDebugLayers() {

        List<Layer> layers = new ArrayList<>();

        String themeWithWrongScreenshotImages = "{\n" +
                "    \"title\": \"Theme with wrong images\",\n" +
                "    \"icon\": \"NO LINK HERE\",\n" +
                "    \"screenshot_1\": \"https://plus.google.com/u/0/communities\",\n" +
                "    \"screenshot_2\": \"https://inbox.google.com/u/1/?pli=1\",\n" +
                "    \"screenshot_3\": \"vasdvas\"\n" +
                "}";

        String themeWithBlackToolbar = "{\n" +
                "    \"title\": \"Theme with black toolbar\",\n" +
                "    \"icon\": \"NO LINK HERE\",\n" +
                "    \"link\": \"NO LINK HERE\",\n" +
                "    \"toolbar_background_color\": \"0\",\n" +
                "    \"screenshot_2\": \"https://inbox.google.com/u/1/?pli=1\",\n" +
                "    \"screenshot_3\": \"vasdvas\"\n" +
                "}";

        String themeWithWhiteToolbar = "{\n" +
                "    \"title\": \"Theme with white toolbar\",\n" +
                "    \"icon\": \"NO LINK HERE\",\n" +
                "    \"link\": \"NO LINK HERE\",\n" +
                "    \"toolbar_background_color\": \"FFFFFF\",\n" +
                "    \"screenshot_2\": \"https://inbox.google.com/u/1/?pli=1\",\n" +
                "    \"screenshot_3\": \"vasdvas\"\n" +
                "}";

        String themeWithBackupScreenshot = "{\n" +
                "    \"title\": \"Theme with backup screenshots\",\n" +
                "    \"icon\": \"NO LINK HERE\",\n" +
                "    \"link\": \"NO LINK HERE\",\n" +
                "    \"screenshot_1\": \"https://inbox.google.com/u/1/?pli=1, http://i.imgur.com/poo8Jvs.png \",\n" +
                "    \"screenshot_2\": \"https://inbox.google.com/u/1/?pli=1,http://i.imgur.com/NzyeZ6E.png \",\n" +
                "    \"screenshot_3\": \"vasdvas\"\n" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            layers.add(objectMapper.readValue(themeWithWrongScreenshotImages, Layer.class));
            layers.add(objectMapper.readValue(themeWithBlackToolbar, Layer.class));
            layers.add(objectMapper.readValue(themeWithWhiteToolbar, Layer.class));
            layers.add(objectMapper.readValue(themeWithBackupScreenshot, Layer.class));
            return layers;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


}
