package com.lovejoy777.showcase.enums;

public enum AndroidPlatform {
    Touchwiz("Touchwiz"),
    LG("LG"),
    Sense("Sense"),
    Xperia("Xperia"),
    Asus("Asus ZenUI");

    private String text;

    AndroidPlatform(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static AndroidPlatform fromString(String text) {
        for (AndroidPlatform platform : AndroidPlatform.values()) {
            if (text.equalsIgnoreCase(platform.text)) {
                return platform;
            }
        }

        throw new IllegalArgumentException("Wrong name");
    }
}
