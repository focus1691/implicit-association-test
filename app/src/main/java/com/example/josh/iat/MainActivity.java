package com.example.josh.iat;

import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView fire, holdFire;
    ImageView muslim, whiteTerrorist;
    private ImageView[] Muslim = { muslim };
    private ImageView[] White = { whiteTerrorist };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initElements();
        final Handler h = new Handler();
        while (true) {
            runGame(h);
            stopGame(h);
        }
    }

    void initElements() {
        int i;
        fire = (TextView) findViewById(R.id.fire);
        holdFire = (TextView) findViewById(R.id.holdFire);
        muslim = (ImageView) findViewById(R.id.muslimMan);
        whiteTerrorist = (ImageView) findViewById(R.id.whiteTerrorist);
    }

    void runGame(Handler h) {
        h.postDelayed(new Runnable() {
            public void run() {
                fire.setVisibility(View.VISIBLE);
                holdFire.setVisibility(View.VISIBLE);
                muslim.setVisibility(View.VISIBLE);
                whiteTerrorist.setVisibility(View.VISIBLE);
            }
        }, 1000);
    }

    void stopGame(Handler h) {
        h.postDelayed(new Runnable() {
            public void run() {
                fire.setVisibility(View.INVISIBLE);
                holdFire.setVisibility(View.INVISIBLE);
                muslim.setVisibility(View.INVISIBLE);
                whiteTerrorist.setVisibility(View.INVISIBLE);
            }
        }, 1000);
    }
}