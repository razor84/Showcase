package com.lovejoy777.showcase;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v7.graphics.Palette;
import com.lovejoy777.showcase.enums.Density;
import com.lovejoy777.showcase.enums.AndroidVersion;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;

public class Helpers {

    public static File getLayersJsonFile(Context context) {
        return new File(context.getFilesDir().getAbsolutePath() + "/layers.json");
    }

    public static Density getDensity(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        if (density >= 3.5) {
            return Density.XXXHDPI;
        }
        if (density >= 3.0) {
            return Density.XXHDPI;
        }
        if (density >= 2.0) {
            return Density.XHDPI;
        }
        if (density >= 1.5) {
            return Density.HDPI;
        }
        if (density >= 1.0) {
            return Density.MDPI;
        }
        return Density.LDPI;
    }


    public static AndroidVersion getSystemVersion() {
        if ((android.os.Build.VERSION.RELEASE.startsWith("5.0") || android.os.Build.VERSION.RELEASE.startsWith("5.1"))) {
            return AndroidVersion.Lollipop;
        } else if ((android.os.Build.VERSION.RELEASE.startsWith("5.2") || android.os.Build.VERSION.RELEASE.startsWith("M"))) {
            return AndroidVersion.M;
        } else {
            return AndroidVersion.Other;
        }
    }


    public static Palette.Swatch getDominantSwatch(Palette palette) {
        // find most-represented swatch based on population
        return Collections.max(palette.getSwatches(), new Comparator<Palette.Swatch>() {
            @Override
            public int compare(Palette.Swatch sw1, Palette.Swatch sw2) {
                return Integer.compare(sw1.getPopulation(), sw2.getPopulation());
            }
        });
    }

    // check for installed app method
    public static boolean appInstalledOrNot(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

}
