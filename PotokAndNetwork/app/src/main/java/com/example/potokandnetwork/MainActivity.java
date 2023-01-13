package com.example.potokandnetwork;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    //класс воркер, что-то по типу thread
    //делаем выход в инет(запрашиваем доступ в манифесте)
    //и в gradle module добавляем воркер
    //и скачиваем картинку

    TableRow tr;
    Button bt;
    Data data;
    String [] picture = {"https://i.pinimg.com/564x/ab/b8/f3/abb8f3da0211276485c30151723f7cc2.jpg",
    "https://i.pinimg.com/564x/a8/b3/68/a8b3687e9dde9d86074c3501b9fbc3d5.jpg",
    "https://i.pinimg.com/564x/22/1f/87/221f87131f4bfc05cdc58ed1040d9319.jpg"};
    Context c = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tr = findViewById(R.id.row);
        bt = findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = new Data.Builder().putString("Address", picture[0]).build();
                //создание экземпляра потока
                OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(Loader.class)
                        .setInputData(data).build();
                //создание менеджера
                WorkManager wk = WorkManager.getInstance(c);
                wk.enqueue(otwr);

                wk.getWorkInfoByIdLiveData(otwr.getId())
                        .observe((LifecycleOwner) MainActivity.this, new Observer<WorkInfo>() {
                            @Override
                            public void onChanged(WorkInfo workInfo) {
                                String imageText = workInfo.getOutputData().getString("Image");
                                Bitmap image = ggson.jsonToImage(imageText);
                                ((ImageView)tr.getChildAt(0)).setImageBitmap(image);
                            }
                        });
            }
        });
    }
}