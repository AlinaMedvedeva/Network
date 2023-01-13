package com.example.potokandnetwork;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.net.URL;

public class Loader extends Worker {

    Data outputdata;

    public Loader(@androidx.annotation.NonNull Context context, @androidx.annotation.NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @androidx.annotation.NonNull
    @Override
    public Result doWork() {
        String ad = getInputData().getString("Address");
        try {
            URL address = new URL(ad);
            Log.d("Loader", "Данные готовы");
            Bitmap image = BitmapFactory.decodeStream(address.openStream());
            outputdata = new Data.Builder()
                    .putString("Image", ggson.ImageToGson(image)).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success(outputdata);
    }
}
