package com.hflanagan.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity{

    private final int BLUE = 1, RED = 2, YELLOW = 3, GREEN = 4;
    Button bRed, bBlue, bYellow, bGreen, fb;

    int sequenceCount = 4, n = 0, score, round, counter;
    private Object mutex = new Object();
    int[] gameSequence = new int[120];
    int arrayIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Score and Round
        score = getIntent().getIntExtra("score", 0);
        round = getIntent().getIntExtra("round", 1);
        counter = getIntent().getIntExtra("counter", 3);


        bRed = findViewById(R.id.btnNorth);
        bBlue = findViewById(R.id.btnWest);
        bYellow = findViewById(R.id.btnSouth);
        bGreen = findViewById(R.id.btnEast);
    }


    CountDownTimer ctRound1 = new CountDownTimer(6000,  1500) {

        public void onTick(long millisUntilFinished) {
            oneButton();
        }

        public void onFinish() {

            //add to sequence
            for (int i = 0; i< arrayIndex; i++) Log.d("game sequence", String.valueOf(gameSequence[i]));

            //Set Counter
            counter = 3;
            //Send Data via Intent
            Intent playIntent = new Intent(MainActivity.this, play.class);
            playIntent.putExtra("score", score);
            playIntent.putExtra("round", round);
            playIntent.putExtra("counter", counter);
            playIntent.putExtra("sequence", gameSequence);

            //Start the next activity
            startActivity(playIntent);

        }
    };
    CountDownTimer ctRound2 = new CountDownTimer(9000,  1500) {

        public void onTick(long millisUntilFinished) {
            oneButton();
        }

        public void onFinish() {

            //add to sequence
            for (int i = 0; i< arrayIndex; i++) Log.d("game sequence", String.valueOf(gameSequence[i]));

            counter = 8;

            //Send Data via Intent
            Intent playIntent = new Intent(MainActivity.this, play.class);
            playIntent.putExtra("score", score);
            playIntent.putExtra("round", round);
            playIntent.putExtra("counter", counter);
            playIntent.putExtra("sequence", gameSequence);


            //Start the next activity
            startActivity(playIntent);

        }
    };
    CountDownTimer ctRound3 = new CountDownTimer(12000, 1500 ) {

        public void onTick(long millisUntilFinished) {
            oneButton();
        }

        public void onFinish() {

            //add to sequence
            for (int i = 0; i< arrayIndex; i++) Log.d("game sequence", String.valueOf(gameSequence[i]));

            counter = 15;

            //Send Data via Intent
            Intent playIntent = new Intent(MainActivity.this, play.class);
            playIntent.putExtra("score", score);
            playIntent.putExtra("round", round);
            playIntent.putExtra("counter", counter);
            playIntent.putExtra("sequence", gameSequence);


            //Start the next activity
            startActivity(playIntent);

        }
    };
    CountDownTimer ctRound4 = new CountDownTimer(15000,  1500) {

        public void onTick(long millisUntilFinished) {
            oneButton();
        }

        public void onFinish() {

            //add to sequence
            for (int i = 0; i< arrayIndex; i++) Log.d("game sequence", String.valueOf(gameSequence[i]));

            counter = 15;

            //Send Data via Intent
            Intent playIntent = new Intent(MainActivity.this, play.class);
            playIntent.putExtra("score", score);
            playIntent.putExtra("round", round);
            playIntent.putExtra("counter", counter);
            playIntent.putExtra("sequence", gameSequence);


            //Start the next activity
            startActivity(playIntent);

        }
    };
    CountDownTimer ctRound5 = new CountDownTimer(18000,  1500) {

        public void onTick(long millisUntilFinished) {
            oneButton();
        }

        public void onFinish() {

            //add to sequence
            for (int i = 0; i< arrayIndex; i++) Log.d("game sequence", String.valueOf(gameSequence[i]));

            counter = 24;
            //Send Data via Intent
            Intent playIntent = new Intent(MainActivity.this, play.class);
            playIntent.putExtra("score", score);
            playIntent.putExtra("round", round);
            playIntent.putExtra("counter", counter);
            playIntent.putExtra("sequence", gameSequence);


            //Start the next activity
            startActivity(playIntent);

        }
    };
    public void doPlay(View view) {

        switch (round){
            case 1:
                ctRound1.start();
                break;
            case 2:
                ctRound2.start();
                break;
            case 3:
                ctRound3.start();
                break;
            case 4:
                ctRound4.start();
                break;
            case 5:
                ctRound5.start();
                break;
        }

    }
    private void oneButton() {
        n = getRandom(sequenceCount);

        switch (n) {
            case 1:
                flashButton(bBlue);
                gameSequence[arrayIndex++] = BLUE;
                break;
            case 2:
                flashButton(bRed);
                gameSequence[arrayIndex++] = RED;
                break;
            case 3:
                flashButton(bYellow);
                gameSequence[arrayIndex++] = YELLOW;
                break;
            case 4:
                flashButton(bGreen);
                gameSequence[arrayIndex++] = GREEN;
                break;
            default:
                break;
        }   // end switch
    }

    // return a number between 1 and maxValue
    private int getRandom(int maxValue) {
        return ((int) ((Math.random() * maxValue) + 1));
    }

    private void flashButton(Button button) {
        fb = button;
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {

                fb.setPressed(true);
                fb.invalidate();
                fb.performClick();
                Handler handler1 = new Handler();
                Runnable r1 = new Runnable() {
                    public void run() {
                        fb.setPressed(false);
                        fb.invalidate();
                    }
                };
                handler1.postDelayed(r1, 600);

            } // end runnable
        };
        handler.postDelayed(r, 600);
    }

}
