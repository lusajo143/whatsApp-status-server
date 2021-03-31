package com.techdealers.statussaver.unsaved;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

import com.techdealers.statussaver.R;

import java.io.File;


public class Unsaved extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private GridView layout;
    private ad_unsaved adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_unsaved, container, false);


        layout = view.findViewById(R.id.grid_unsaved);

        File file = new File(Environment.getExternalStorageDirectory()+"/WhatsApp/Media/.Statuses");

        File[] statuses = file.listFiles();

        adapter = new ad_unsaved(statuses,getContext());
        layout.setAdapter(adapter);



        return view;
    }

}