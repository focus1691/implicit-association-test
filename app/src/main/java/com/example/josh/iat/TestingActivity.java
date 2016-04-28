package com.example.josh.iat;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class TestingActivity extends Activity implements View.OnClickListener {

    private Statistics stats;
    private Record record;
    private Realm realm;
    private ImageView img;
    private ImageAsync[] imageList;
    private boolean shootSelected = false;
    private boolean button_clicked = false;
    private ImageButton shoot_btn;
    private ImageView wait_screen;
    private ImageView cracked_screen;
    private ImageView shoot_btn_pressed;
    private double startTime, elapsedTime;
    private final Handler handler = new Handler();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        createDatabase();
        stats = new Statistics(4);
        initButtons();
        initImages();
        shuffleImages(imageList);
        runTrials(0);
    }

    protected void computeTTest() {
        stats.computeDifferences();
        stats.computeMean();
        stats.computeSD();
        stats.computeSE();
        stats.computeT();
        stats.computeSignificance();
    }

    protected void updateRecord() {
        record.setN(stats.N);
        record.setMean(stats.mean);
        record.setSD(stats.SD);
        record.setSE(stats.SE);
        record.setT(stats.T);
        record.setSignificance(stats.significant);
    }

    protected void createDatabase() {
        RealmConfiguration configuration = new RealmConfiguration.Builder(getApplicationContext()).build();
        Realm.setDefaultConfiguration(configuration);
        realm = Realm.getDefaultInstance();
        record = new Record();
    }

    protected void saveData() {
        realm.beginTransaction();
        realm.copyToRealm(record);
        realm.commitTransaction();
        realm.close();
    }

    public void onClick(View view) {
        button_clicked = true;
        switch (view.getId()) {
            case R.id.shoot_btn:
                shoot_btn.setEnabled(false);
                elapsedTime = System.currentTimeMillis();
                shootSelected = true;
                Glide.clear(shoot_btn);
                Glide.with(this).load(R.drawable.red_btn_clicked).into(shoot_btn_pressed);
        }
    }

    protected void initButtons() {

        // Initialise the shoot button animation when pressed
        shoot_btn_pressed = (ImageView) findViewById(R.id.shoot_clicked);

        // Initialise the shoot button
        shoot_btn = (ImageButton) findViewById(R.id.shoot_btn);
        shoot_btn.setOnClickListener(this);
    }

    protected void initImages() {

        Glide.with(getApplicationContext()).load(R.drawable.red_btn).into(shoot_btn);

        img = (ImageView) findViewById(R.id.white_gun_1);
        wait_screen = (ImageView) findViewById(R.id.wait_screen);
        cracked_screen = (ImageView) findViewById(R.id.broken_glass);

        imageList = new ImageAsync[6];

        int[] imgResArray = { R.drawable.white_gun1_p1, R.drawable.white_gun1_p2,
                              R.drawable.white_gun1_p3, R.drawable.white_gun1_p4,
                              R.drawable.white_gun1_p5, R.drawable.white_gun1_p6 };

        int[] imgResArray2 = { R.drawable.animationtwo_00000, R.drawable.animationtwo_00001,
                              R.drawable.animationtwo_00002, R.drawable.animationtwo_00003,
                              R.drawable.animationtwo_00004, R.drawable.animationtwo_00005,
                              R.drawable.animationtwo_00006, R.drawable.animationtwo_00007 };

        int[] imgResArray3 = { R.drawable.animationthree_00000, R.drawable.animationthree_00001,
                               R.drawable.animationthree_00002, R.drawable.animationthree_00003,
                               R.drawable.animationthree_00004, R.drawable.animationthree_00005,
                               R.drawable.animationthree_00006 };

        int[] imgResArray4 = { R.drawable.animationfour_00000, R.drawable.animationfour_00001,
                              R.drawable.animationfour_00002, R.drawable.animationfour_00003,
                              R.drawable.animationfour_00004, R.drawable.animationfour_00005,
                              R.drawable.animationfour_00006, R.drawable.animationfour_00007 };

        int[] imgResArray5 = { R.drawable.animationfive_00000, R.drawable.animationfive_00001,
                               R.drawable.animationfive_00002, R.drawable.animationfive_00003,
                               R.drawable.animationfive_00004, R.drawable.animationfive_00005,
                               R.drawable.animationfive_00006, R.drawable.animationfive_00007,
                               R.drawable.animationfive_00008, R.drawable.animationfive_00009 };

        int[] imgResArray6 = { R.drawable.animationsix_00000, R.drawable.animationsix_00001,
                               R.drawable.animationsix_00002, R.drawable.animationsix_00003,
                               R.drawable.animationsix_00004, R.drawable.animationsix_00005,
                               R.drawable.animationsix_00006 };

        imageList[0] = new ImageAsync(this, imgResArray, img, false);
        imageList[1] = new ImageAsync(this, imgResArray2, img, true);
        imageList[2] = new ImageAsync(this, imgResArray3, img, true);
        imageList[3] = new ImageAsync(this, imgResArray4, img, true);
        imageList[4] = new ImageAsync(this, imgResArray5, img, true);
        imageList[5] = new ImageAsync(this, imgResArray6, img, false);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void shuffleImages(ImageAsync[] imageList) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = imageList.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            ImageAsync temp = imageList[index];
            imageList[index] = imageList[i];
            imageList[i] = temp;
        }
    }

    protected void runTrials(final int trial) {

        /* Minimum delay of 2 seconds and a maximum delay of 4 seconds, for the next trial */
        int delay = 2000 + (new Random().nextInt(2000));

        Glide.with(getApplicationContext()).load(R.drawable.fav).into(wait_screen);
        showTarget(trial, delay);
        checkShoot(delay, trial);
        removeMedia(trial, delay);
    }

    void showTarget(final int trial, int delay) {
        handler.postDelayed(new Runnable() {
            public void run() {
                button_clicked = false;
                Glide.clear(wait_screen);
                loadTarget(trial);
                shoot_btn.setEnabled(true);
                startTime = System.currentTimeMillis();
            }
        }, delay);
    }

    void checkShoot(int delay, final int trial) {
        handler.postDelayed(new Runnable() {
            public void run() {
                shoot_btn.setEnabled(false);
                if (button_clicked == false) {
                    //Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    //v.vibrate(500);
                    imageList[trial].showAnimation();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(getApplicationContext()).load(R.drawable.brokenglass).into(cracked_screen);
                        }
                    }, 100);
                }
            }
        }, delay + 2000);
    }

    void removeMedia(final int trial, int delay) {
        handler.postDelayed(new Runnable() {
            public void run() {
                refreshTrial(trial);
                Log.d("TAG", "Time Taken: " + Double.toString(elapsedTime - startTime));
                if (imageList[trial].isBlackMan()) {
                    stats.addScore(elapsedTime - startTime, "Black");
                } else {
                    stats.addScore(elapsedTime - startTime, "White");
                }
                if (trial + 1 < imageList.length) {
                    runTrials(trial + 1);
                } else {
                    computeTTest();
                    Log.d("Josh", "v:" + Double.toString(stats.T));
                    //updateRecord();
                    //saveData();
                }
            }
        }, delay + 2000 + 4000);
    }

    protected void loadTarget(int trialNumber) {
        Glide.with(getApplicationContext()).load(R.drawable.red_btn).into(shoot_btn);
        imageList[trialNumber].showFirstFrame();
    }

    protected void refreshTrial(int trialNumber) {
        imageList[trialNumber].clear();
        if (shootSelected == true) {
            Glide.clear(shoot_btn_pressed);
            shootSelected = false;
            return;
        }
        Glide.clear(shoot_btn);
        Glide.clear(cracked_screen);
        Glide.clear(wait_screen);
        img.setImageDrawable(null);
        Glide.clear(img);
    }
}