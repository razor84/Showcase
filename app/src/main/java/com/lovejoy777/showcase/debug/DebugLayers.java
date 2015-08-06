package com.lovejoy777.showcase.debug;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lovejoy777.showcase.beans.Layer;

import java.io.IOException;

public class DebugLayers {

    public static Layer createDebugLayers() {

        String themeWithWrongScreenshotImages = "{\n" +
                "    \"title\": \"Theme with wrong images\",\n" +
                "    \"description\": \"AMAMAMAMAM\",\n" +
                "    \"author\": \"N00ne\",\n" +
                "    \"link\": \"www.google.pl\",\n" +
                "    \"backup_link\": false,\n" +
                "    \"icon\": \"NO LINK HERE\",\n" +
                "    \"screenshot_1\": \"https://plus.google.com/u/0/communities\",\n" +
                "    \"screenshot_2\": \"https://inbox.google.com/u/1/?pli=1\",\n" +
                "    \"screenshot_3\": \"vasdvas\",\n" +
                "    \"googleplus\": \"NO GOOGLE+ FOR YOU\",\n" +
                "    \"bootani\": \"false\",\n" +
                "    \"font\": \"false\",\n" +
                "    \"wallpaper\": \"false\",\n" +
                "    \"plugin_version\": \"1\",\n" +
                "    \"for_L\": \"true\",\n" +
                "    \"for_M\": \"false\",\n" +
                "    \"basic\": \"true\",\n" +
                "    \"basic_m\": \"true\",\n" +
                "    \"type2\": \"true\",\n" +
                "    \"type3\": \"true\",\n" +
                "    \"type3_m\": \"false\",\n" +
                "    \"touchwiz\": \"false\",\n" +
                "    \"lg\": \"false\",\n" +
                "    \"sense\": \"false\",\n" +
                "    \"xperia\": \"false\",\n" +
                "    \"zenui\": \"false\",\n" +
                "    \"hdpi\": \"true\",\n" +
                "    \"mdpi\": \"false\",\n" +
                "    \"xhdpi\": \"true\",\n" +
                "    \"xxhdpi\": \"true\",\n" +
                "    \"xxxhdpi\": \"true\",\n" +
                "    \"free\": \"true\",\n" +
                "    \"donate\": \"true\",\n" +
                "    \"paid\": \"false\",\n" +
                "    \"needs_update\": \"true\",\n" +
                "    \"will_update\": \"true\",\n" +
                "    \"iconpack\": \"false\"\n" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return objectMapper.readValue(themeWithWrongScreenshotImages, Layer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


}
