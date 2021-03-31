package com.techdealers.statussaver;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class SaveService extends Service {

    private Handler handler = new Handler();

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    +"/Status Saver");
            if (!file.exists()) {
                file.mkdirs();
            }

            File[] files = file.listFiles();

            for (File File1 :
                    files) {

                File source = File1;
                File dest = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                        +"/Status Saver/",source.getName());

                if (dest.exists()) {

                } else {
                    try {
                        FileInputStream inputStream = new FileInputStream(source);
                        FileOutputStream outputStream  = new FileOutputStream(dest);

                        byte[] buffer = new byte[1024];
                        int read;
                        while ((read = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer,0,read);
                        }

                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(SaveService.this);
                        localBroadcastManager.sendBroadcast(new Intent("change"));

                    } catch (FileNotFoundException e) {

                    } catch (IOException e) {

                    }
                }


            }

            handler.postDelayed(runnable,5000);

        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        handler.postDelayed(runnable,5000);

        return super.onStartCommand(intent, flags, startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
