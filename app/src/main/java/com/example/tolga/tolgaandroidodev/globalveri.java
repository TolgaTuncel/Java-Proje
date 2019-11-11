package com.example.tolga.tolgaandroidodev;

import android.app.Application;

public class globalveri extends Application {

    private int arabaposition;

    public int getArabaposition() {
        return arabaposition;
    }
    public void setArabaposition(int arabaposition) {
        this.arabaposition = arabaposition;
    }

    private int markaposition;

    public int getmarkaposition() {
        return markaposition;
    }
    public void setmarkaposition(int markaposition) {
        this.markaposition = markaposition;
    }

}
