package com.example.android.miwok;
public class WordObject {

    private static final int NO_IMAGE_PROVIDED = -1;
    public String numberEnglish ;
    public String numberTranslated;
    public int soundResourceId;
    public int imageResourceId =  NO_IMAGE_PROVIDED;

    public WordObject(String numberEnglish, String numberTranslated , int soundResourceId) {
        this.numberEnglish    = numberEnglish;
        this.numberTranslated = numberTranslated;
        this.soundResourceId  = soundResourceId;
    }

    public WordObject(String numberEnglish, String numberTranslated , int imageResourceId , int soundResourceId) {
        this.numberEnglish    = numberEnglish;
        this.numberTranslated = numberTranslated;
        this.imageResourceId  = imageResourceId;
        this.soundResourceId  = soundResourceId;
    }

    public String getNumberEnglish() {
        return numberEnglish;
    }
    public String getNumberTranslated() {
        return numberTranslated;
    }
    public int getImage() {
        return imageResourceId;
    }
    public int getSound() {
        return soundResourceId;
    }

    public boolean hasImage() {
        return imageResourceId != NO_IMAGE_PROVIDED;
    }
}
