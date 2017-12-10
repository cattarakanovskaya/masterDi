package com.example.kate.myapplication;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button listen;
    private  boolean isPower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        writeFileSD();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listen = (Button) findViewById(R.id.button);
        isPower = false;

        listen.setOnClickListener(onClickListener);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isPower == false){
                listen.setText("Stop");
                isPower = true;
            }
            else{
                listen.setText("Start");
                isPower = false;
            }

        }
    };

    final String LOG_TAG = "myLogs";
    final String DIR_SD = "MyFiles2";
    final String FILENAME_SD = "fileSD.txt";

    private void writeFileSD() {
        // проверяем доступность SD
        Log.d(LOG_TAG, "Start!!");
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        Log.d(LOG_TAG, String.valueOf(sdPath));
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
        Log.d(LOG_TAG, String.valueOf(sdPath));
        // создаем каталог
        if (sdPath.mkdirs() == true ) Log.d(LOG_TAG, "It's ok");
        else Log.d(LOG_TAG, "It's not ok");
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, FILENAME_SD);
        try {
            // открываем поток для записи
            Log.d(LOG_TAG, "Block try1");
            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
            Log.d(LOG_TAG, "Block try2");
            // пишем данные
            bw.write("Содержимое файла на SD");
            // закрываем поток
            bw.close();
            Log.d(LOG_TAG, "Файл записан на SD: " + sdFile.getAbsolutePath());
        } catch (IOException e) {
            Log.d(LOG_TAG, "Block exception");
            e.printStackTrace();
        }
    }
}
