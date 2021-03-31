package com.techdealers.statussaver.unsaved;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.techdealers.statussaver.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public class ad_unsaved extends BaseAdapter {

    private File[] files;
    private Context context;

    public ad_unsaved(File[] files, Context context) {
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

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    +"/Status Saver");
            if (!file.exists()) {
                file.mkdirs();
            }

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

                save.setOnClickListener(v1 -> {


                    File source = files[position];
                    File dest = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            +"/Status Saver/",source.getName());

                    if (dest.exists()) {
                        Toast.makeText(context, "Already Saved", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
                            localBroadcastManager.sendBroadcast(new Intent("change"));

                        } catch (FileNotFoundException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                });

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
                save.setOnClickListener(v -> {
                    File source = files[position];
                    File dest = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            +"/Status Saver/",source.getName());

                    if (dest.exists()) {
                        Toast.makeText(context, "Already Saved", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
                            localBroadcastManager.sendBroadcast(new Intent("change"));

                        } catch (FileNotFoundException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            });

            img.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.GONE);
        }



        return view;    }
}
