package com.example.josh.iat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageView logo, background;
    private Button raceBtn, religionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        initBackground();
        initLogo();
        initButtons();
    }

    private void initBackground() {
        if ((background = (ImageView) findViewById(R.id.background)) != null) {
            Glide.with(this).load(R.drawable.background).centerCrop().into(background);
        }
    }

    private void initLogo() {
        if ((logo = (ImageView) findViewById(R.id.logo)) != null) {
            Glide.with(this).load(R.drawable.logo).centerCrop().into(logo);
        }
    }

    private void initButtons() {
        raceBtn = (Button) findViewById(R.id.raceBtn);
        religionBtn = (Button) findViewById(R.id.religionBtn);
    }

    public void runRaceTest(View view) {
        // Remove the images from the start screen
        Intent intent = new Intent(getApplicationContext(), TestingActivity.class);
        startActivity(intent);
    }
}