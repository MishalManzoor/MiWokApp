package com.mishal.miwokapp;


public class word {

    private final String DefaultTranslation;

    private final String MiworkTranslation;

    private final int NO_IMAGE_PROVIDED = -1;

    private int images = NO_IMAGE_PROVIDED;
    private int audio;


    public word(String defaultTranslation, String miworkTranslation, int audio) {
        DefaultTranslation = defaultTranslation;
        MiworkTranslation = miworkTranslation;
        this.audio = audio;
    }

    public word(String defaultTranslation, String miworkTranslation, int images, int audio) {
        DefaultTranslation = defaultTranslation;
        MiworkTranslation = miworkTranslation;
        this.images = images;
        this.audio = audio;
    }


    public int getImages() {
        return images;
    }

    public String getDefaultTranslation() {
        return DefaultTranslation;
    }

    public String getMiworkTranslation() {
        return MiworkTranslation;
    }

    public int getAudio() {
        return audio;
    }

    public boolean hasImage() {

        return images != NO_IMAGE_PROVIDED;
    }
}
