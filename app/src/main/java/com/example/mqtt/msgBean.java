package com.example.mqtt;
import android.graphics.Bitmap;

public class msgBean {
    private int type;
    private String text;
    private Bitmap icon;

    int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public msgBean(){}
}
