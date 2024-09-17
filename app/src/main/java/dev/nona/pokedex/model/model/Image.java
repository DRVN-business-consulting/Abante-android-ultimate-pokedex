package dev.nona.pokedex.model.model;

import com.google.gson.annotations.SerializedName;

public class Image{
    public String sprite;
    public String thumbnail;
    @SerializedName("hi_res")
    public String hiRes;

    public String getSprite() {
        return sprite;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getHiRes() {
        return hiRes;
    }
}