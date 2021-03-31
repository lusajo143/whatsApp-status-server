package com.techdealers.statussaver;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.techdealers.statussaver.saved.Saved;
import com.techdealers.statussaver.unsaved.Unsaved;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // banner     ca-app-pub-3229241212568197/2080003668
    // banner test ad    ca-app-pub-3940256099942544/6300978111


    private ViewPager viewPager;
    private adapter adapter;
    //private CheckBox automatic;

    private FloatingActionButton next, prev;

    private FirebaseAnalytics mFirebaseAnalytics;
    private AdView mAdView;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.pager);
        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);
//        automatic =findViewById(R.id.check);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        AdRequest rewarded = new AdRequest.Builder().build();

        RewardedAd rewardedAd = new RewardedAd(this,"ca-app-pub-3229241212568197/5916781679");

        //RewardedAd rewardedAd = new RewardedAd(this,"ca-app-pub-3940256099942544/5224354917");
        RewardedAdLoadCallback callback = new RewardedAdLoadCallback(){
            @Override
            public void onRewardedAdLoaded() {
                super.onRewardedAdLoaded();
            }

            @Override
            public void onRewardedAdFailedToLoad(int i) {
                super.onRewardedAdFailedToLoad(i);
            }
        };
        rewardedAd.loadAd(rewarded,callback);


//        new Thread(()->{
//            while (!rewardedAd.isLoaded()) {
//
//            }
//
//        }).start();



        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                +"/Status Saver");
        if (!file.exists()) {
            file.mkdirs();
        }

        List<Fragment> list = new ArrayList<>();
        list.add(new Unsaved());
        list.add(new Saved());

        adapter = new adapter(getSupportFragmentManager(),
                list);

        viewPager.setAdapter(adapter);

        if (viewPager.getCurrentItem() == 0) {
            prev.setVisibility(View.GONE);
            next.setVisibility(View.VISIBLE);
        } else {
            next.setVisibility(View.GONE);
            prev.setVisibility(View.VISIBLE);
        }

        next.setOnClickListener(v -> {
            next.setVisibility(View.GONE);
            prev.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(1,true);
            if (rewardedAd.isLoaded()) {
                rewardedAd.show(MainActivity.this, new RewardedAdCallback() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                    }
                });
            }
        });

        prev.setOnClickListener(v -> {
            prev.setVisibility(View.GONE);
            next.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(0,true);
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    prev.setVisibility(View.GONE);
                    next.setVisibility(View.VISIBLE);
                } else {
                    next.setVisibility(View.GONE);
                    prev.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


//        Intent intent = new Intent(this, SaveService.class);
//
//        if (new dbHelper(this).getState().equals("not checked")) {
//            automatic.setChecked(false);
//        } else {
//            automatic.setChecked(true);
//            startService(intent);
//        }
//
//
//
//        automatic.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                new dbHelper(this)
//                        .changeState("checked");
//                startService(intent);
//            } else {
//                new dbHelper(this)
//                        .changeState("not changed");
//                stopService(intent);
//            }
//        });


    }
}