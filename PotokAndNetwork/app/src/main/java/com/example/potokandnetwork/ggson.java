package com.example.potokandnetwork;

import android.graphics.Bitmap;

import com.google.gson.Gson;

public class ggson {

    public static String ImageToGson(Bitmap image){
        Gson gson = new Gson();
        return gson.toJson(image);
    }

    public static Bitmap jsonToImage (String Image){
        Gson gson = new Gson();
        return gson.fromJson(Image, Bitmap.class);
    }
}
