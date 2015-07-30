package com.lovejoy777.showcase;

import com.lovejoy777.showcase.enums.AndroidPlatform;
import com.lovejoy777.showcase.enums.Density;
import com.lovejoy777.showcase.enums.AndroidVersion;
import com.lovejoy777.showcase.enums.LayersVersion;

import java.io.Serializable;

public class Theme implements Serializable {
    private String title;
    private String description;
    private String author;
    private String link;
    private String icon;
    private String promo;
    private String screenshot_1;
    private String screenshot_2;
    private String screenshot_3;
    private String googleplus;
    private String version;
    private String donate_link;
    private String donate_version;
    private boolean bootani;
    private boolean font;
    private String wallpaper;
    private String plugin_version;
    private boolean for_L;
    private boolean for_M;
    private boolean basic;
    private boolean basic_m;
    private boolean type2;
    private boolean type3;
    private boolean type3_m;
    private boolean touchwiz;
    private boolean lg;
    private boolean sense;
    private boolean xperia;
    private boolean zenui;
    private boolean hdpi;
    private boolean mdpi;
    private boolean xhdpi;
    private boolean xxhdpi;
    private boolean xxxhdpi;
    private boolean free;
    private boolean donate;
    private boolean paid;

    public Theme() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPromo() {
        return promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }

    public String getScreenshot_1() {
        return screenshot_1;
    }

    public void setScreenshot_1(String screenshot_1) {
        this.screenshot_1 = screenshot_1;
    }

    public String getScreenshot_2() {
        return screenshot_2;
    }

    public void setScreenshot_2(String screenshot_2) {
        this.screenshot_2 = screenshot_2;
    }

    public String getScreenshot_3() {
        return screenshot_3;
    }

    public void setScreenshot_3(String screenshot_3) {
        this.screenshot_3 = screenshot_3;
    }

    public String getGoogleplus() {
        return googleplus;
    }

    public void setGoogleplus(String googleplus) {
        this.googleplus = googleplus;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDonate_link() {
        return donate_link;
    }

    public void setDonate_link(String donate_link) {
        this.donate_link = donate_link;
    }

    public String getDonate_version() {
        return donate_version;
    }

    public void setDonate_version(String donate_version) {
        this.donate_version = donate_version;
    }

    public boolean isBootani() {
        return bootani;
    }

    public void setBootani(boolean bootani) {
        this.bootani = bootani;
    }

    public boolean isFont() {
        return font;
    }

    public void setFont(boolean font) {
        this.font = font;
    }

    public String getWallpaper() {
        return wallpaper;
    }

    public void setWallpaper(String wallpaper) {
        this.wallpaper = wallpaper;
    }

    public String getPlugin_version() {
        return plugin_version;
    }

    public void setPlugin_version(String plugin_version) {
        this.plugin_version = plugin_version;
    }

    public boolean isFor_L() {
        return for_L;
    }

    public void setFor_L(boolean for_L) {
        this.for_L = for_L;
    }

    public boolean isFor_M() {
        return for_M;
    }

    public void setFor_M(boolean for_M) {
        this.for_M = for_M;
    }

    public boolean isBasic() {
        return basic;
    }

    public void setBasic(boolean basic) {
        this.basic = basic;
    }

    public boolean isBasic_m() {
        return basic_m;
    }

    public void setBasic_m(boolean basic_m) {
        this.basic_m = basic_m;
    }

    public boolean isType2() {
        return type2;
    }

    public void setType2(boolean type2) {
        this.type2 = type2;
    }

    public boolean isType3() {
        return type3;
    }

    public void setType3(boolean type3) {
        this.type3 = type3;
    }

    public boolean isType3_m() {
        return type3_m;
    }

    public void setType3_m(boolean type3_m) {
        this.type3_m = type3_m;
    }

    public boolean isTouchwiz() {
        return touchwiz;
    }

    public void setTouchwiz(boolean touchwiz) {
        this.touchwiz = touchwiz;
    }

    public boolean isLg() {
        return lg;
    }

    public void setLg(boolean lg) {
        this.lg = lg;
    }

    public boolean isSense() {
        return sense;
    }

    public void setSense(boolean sense) {
        this.sense = sense;
    }

    public boolean isXperia() {
        return xperia;
    }

    public void setXperia(boolean xperia) {
        this.xperia = xperia;
    }

    public boolean isZenui() {
        return zenui;
    }

    public void setZenui(boolean zenui) {
        this.zenui = zenui;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public boolean isDonate() {
        return donate;
    }

    public void setDonate(boolean donate) {
        this.donate = donate;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isHdpi() {
        return hdpi;
    }

    public void setHdpi(boolean hdpi) {
        this.hdpi = hdpi;
    }

    public boolean isMdpi() {
        return mdpi;
    }

    public void setMdpi(boolean mdpi) {
        this.mdpi = mdpi;
    }

    public boolean isXhdpi() {
        return xhdpi;
    }

    public void setXhdpi(boolean xhdpi) {
        this.xhdpi = xhdpi;
    }

    public boolean isXxhdpi() {
        return xxhdpi;
    }

    public void setXxhdpi(boolean xxhdpi) {
        this.xxhdpi = xxhdpi;
    }

    public boolean isXxxhdpi() {
        return xxxhdpi;
    }

    public void setXxxhdpi(boolean xxxhdpi) {
        this.xxxhdpi = xxxhdpi;
    }

    public boolean isSupportingDpi(Density dpi) {

        switch (dpi) {
            case MDPI:
                return mdpi;
            case HDPI:
                return hdpi;
            case XHDPI:
                return xhdpi;
            case XXHDPI:
                return xxhdpi;
            case XXXHDPI:
                return xxxhdpi;
            default:
                throw new IllegalArgumentException("Wrong DPI");

        }
    }

    public boolean isSupportingAndroidVersion(AndroidVersion version) {

        switch (version) {
            case M:
                return for_M;
            case Lollipop:
                return for_L;
            default:
                throw new IllegalArgumentException("Wrong System version");
        }

    }

    public boolean isSupportingAndroidPlatform(AndroidPlatform platform) {

        switch (platform) {

            case Touchwiz:
                return touchwiz;
            case LG:
                return lg;
            case Sense:
                return sense;
            case Xperia:
                return xperia;
            case Asus:
                return zenui;
            default:
                throw new IllegalArgumentException("Wrong System platform");


        }

    }

    public boolean isSupportingLayersVersion(LayersVersion layersVersion) {

        switch (layersVersion) {
            case Basic_L:
                return basic;
            case Basic_M:
                return basic_m;
            case Type2L:
                return type2;
            case Type3:
                return type3;
            case Type3_M:
                return type3_m;
            default:
                throw new IllegalArgumentException("Wrong Layers platform");
        }

    }

}
