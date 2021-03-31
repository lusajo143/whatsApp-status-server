package com.techdealers.statussaver.saved;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.techdealers.statussaver.R;

import java.io.File;

public class Saved extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private GridView layout;
    private TextView no;
    private ad_saved adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved, container, false);

        layout = view.findViewById(R.id.grid_saved);
        no = view.findViewById(R.id.noSaved);

        getSaved();

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                getSaved();
            }
        };
        localBroadcastManager.registerReceiver(receiver, new IntentFilter("change"));


        return view;
    }

    private void getSaved(){
        File file = new File(Environment.getExternalStorageDirectory()+
                "/Status Saver");

        if (!file.exists()) {
            file.mkdirs();
        } else {
            File[] files = file.listFiles();
            if (files.length != 0) {
                adapter = new ad_saved(files,getContext());
                layout.setAdapter(adapter);
                no.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
            } else {
                no.setVisibility(View.VISIBLE);
                layout.setVisibility(View.GONE);
            }
        }
    }

}