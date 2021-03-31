package com.techdealers.statussaver.saved;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;

import com.techdealers.statussaver.R;

import java.io.File;

public class ad_saved extends BaseAdapter {
    private File[] files;
    private Context context;

    public ad_saved(File[] files, Context context) {
        this.files = files;
        this.context = context;
    }

    @Override
    public int getCount() {
        return files.length;
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return files.length;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.layout_grid,null);

        ImageView img= view.findViewById(R.id.grid_image);
        VideoView videoView = view.findViewById(R.id.grid_video);

        if (files[position].getAbsolutePath().substring(files[position].getAbsolutePath().lastIndexOf("."))
                .equals(".mp4")) {

            videoView.setVideoURI(Uri.parse(files[position].getAbsolutePath()));
            videoView.setOnPreparedListener(mp -> {
                mp.setVolume(0,0);
                mp.setLooping(true);
                mp.start();
            });
            img.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            videoView.setOnClickListener(v -> {
                Toast.makeText(context, "Opening...", Toast.LENGTH_SHORT).show();
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setView(R.layout.layout_alert_video)
                        .create();
                alertDialog.show();

                VideoView videoAlert = alertDialog.findViewById(R.id.alertVideo);
                Button save = alertDialog.findViewById(R.id.alertSave);
                videoAlert.setVideoURI(Uri.parse(files[position].getAbsolutePath()));
                videoAlert.setOnPreparedListener(mp -> {
                    mp.setVolume(1,1);
                    mp.setLooping(true);
                    mp.start();
                });

                save.setVisibility(View.GONE);

            });

        } else {
            img.setImageURI(Uri.parse(files[position].getAbsolutePath()));

            img.setOnClickListener(v1 -> {
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setView(R.layout.layout_alert_image)
                        .create();

                alertDialog.show();
                ImageView imageView = alertDialog.findViewById(R.id.alertImage);
                imageView.setImageURI(Uri.parse(files[position].getAbsolutePath()));

                Button save = alertDialog.findViewById(R.id.alertSaveImage);
                save.setVisibility(View.GONE);
            });

            img.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.GONE);
        }



        return view;    }
}
