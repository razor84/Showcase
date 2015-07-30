package com.lovejoy777.showcase.enums;

public enum LayersVersion {
    Basic_L("Basic RRO L"),
    Basic_M("Basic RRO M"),
    Type2L("Layers Type 2 L"),
    Type3("Layers Type 3"),
    Type3_M("Layers Type 3 M");

    private String text;

    LayersVersion(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static LayersVersion fromString(String text) {
        for (LayersVersion layersVersion : LayersVersion.values()) {
            if (text.equals(layersVersion.text)) {
                return layersVersion;
            }
        }

        throw new IllegalArgumentException("Wrong layers name");
    }
}
