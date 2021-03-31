package com.techdealers.statussaver;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class Splash extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE},100);
        } else {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    +"/Status Saver");
            if (!file.exists()) {
                file.mkdirs();
            }
            new Handler().postDelayed(()-> {
                startActivity(new Intent(this,MainActivity.class));
            },2000);
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    +"/Status Saver");
            if (!file.exists()) {
                file.mkdirs();
            }
            new Handler().postDelayed(()-> {
                startActivity(new Intent(this,MainActivity.class));
            },2000);
        }

    }
}