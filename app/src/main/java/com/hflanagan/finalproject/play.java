package com.hflanagan.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class play extends AppCompatActivity implements SensorEventListener{

    int[] gameSequence = new int[120];

    int num = 0, use_sequence = -1, score, counter = 3, round, time;

    TextView tvScore, tvRound, tvX, tvY, tvZ;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    // experimental values for hi and lo magnitude limits
    private final double NORTH_MOVE_FORWARD = 6;     // upper mag limit
    private final double NORTH_MOVE_BACKWARD = 9;      // lower mag limit

    private final double SOUTH_MOVE_FORWARD = 2;     // upper mag limit
    private final double SOUTH_MOVE_BACKWARD = 5;      // lower mag limit

    private final double EAST_MOVE_FORWARD = 1;     // upper mag limit
    private final double EAST_MOVE_BACKWARD = 0;      // lower mag limit

    private final double WEST_MOVE_FORWARD = 1;     // upper mag limit
    private final double WEST_MOVE_BACKWARD = 0;      // lower mag limit

    boolean highLimitNorth = false;      // detect high limit
    boolean highLimitSouth = false;      // detect high limit
    boolean highLimitEast = false;      // detect high limit
    boolean highLimitWest = false;      // detect high limit

    Button btnNorth, btnWest, btnEast, btnSouth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // we are going to use the sensor service
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        gameSequence = getIntent().getIntArrayExtra("sequence");

        score = getIntent().getIntExtra("score", 1);
        round = getIntent().getIntExtra("round", 1);
        counter = getIntent().getIntExtra("counter", 3);

        tvScore = findViewById(R.id.tvplayScoreNum);
        tvScore.setText(String.valueOf(score));

        tvRound = findViewById(R.id.tvplayRoundNum);
        tvRound.setText(String.valueOf(round));

        tvX = findViewById(R.id.tvX);
        tvY = findViewById(R.id.tvY);
        tvZ = findViewById(R.id.tvZ);



    }

    public void doClick(View view){

        use_sequence++;
        score++;

        tvScore = findViewById(R.id.tvplayScoreNum);
        tvScore.setText(String.valueOf(score));

        switch (view.getId())
        {
            case(R.id.btnWestPlay) :
                num = 1;
                break;
            case(R.id.btnNorthPlay) :
                num = 2;
                break;
            case(R.id.btnSouthPlay):
                num = 3;
                break;
            case(R.id.btnEastPlay) :
                num = 4;
                break;
        }

        for(int i : gameSequence)
        {
            if(num == gameSequence[use_sequence])
            {


                if(counter == score)
                {
                    round++;

                    //Get Score and return to main to start new round
                    Intent mainIntent = new Intent(play.this, MainActivity.class);
                    mainIntent.putExtra("score", score);
                    mainIntent.putExtra("round", round);
                    mainIntent.putExtra("counter", counter);
                    startActivity(mainIntent);

                }
                return;

            }
            else if(num != gameSequence[use_sequence])
            {
                //Minus the point to score point gained
                score--;
                //Stop the game and start the Game Over Screen
                Intent gameOverIntent = new Intent(view.getContext(), gameover.class);
                gameOverIntent.putExtra("score", score);
                gameOverIntent.putExtra("round", round);

                startActivity(gameOverIntent);
                return;
            }
        }
    }

    /*
     * When the app is brought to the foreground - using app on screen
     */
    protected void onResume() {
        super.onResume();
        // turn on the sensor
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    /*
     * App running but not on screen - in the background
     */
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);    // turn off listener to save power
    }
    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        tvX.setText(String.valueOf(x));
        tvY.setText(String.valueOf(y));
        tvZ.setText(String.valueOf(z));

        //Buttons for Aceleometer
        final Button btnNorth = findViewById(R.id.btnNorthPlay);
        final Button btnSouth = findViewById(R.id.btnSouthPlay);
        final Button btnEast = findViewById(R.id.btnEastPlay);
        final Button btnWest = findViewById(R.id.btnWestPlay);

        // North Movement
        if ((x > NORTH_MOVE_FORWARD && z > 0) && (highLimitNorth == false)) {
            highLimitNorth = true;

        }
        if ((x < NORTH_MOVE_BACKWARD && z > 0) && (highLimitNorth == true)) {
            // we have a tilt to the NORTH
            highLimitNorth = false;
            Handler handler = new Handler();
            Runnable r = new Runnable() {
                public void run() {
                    btnNorth.setPressed(true);
                    btnNorth.invalidate();
                    btnNorth.performClick();
                    Handler handler1 = new Handler();
                    Runnable r1 = new Runnable() {
                        public void run() {
                            btnNorth.setPressed(false);
                            btnNorth.invalidate();
                        }
                    };
                    handler1.postDelayed(r1, 600);
                } // end runnable
            };
            handler.postDelayed(r, 600);
        }

        // South Movement
        if ((x < SOUTH_MOVE_FORWARD && z < 0) && (highLimitSouth == false)) {
            highLimitSouth = true;

        }
        if ((x > SOUTH_MOVE_BACKWARD && z < 0) && (highLimitSouth == true)) {
            // we have a tilt to the SOUTH
            highLimitSouth = false;
            Handler handler = new Handler();
            Runnable r = new Runnable() {
                public void run() {

                    btnSouth.setPressed(true);
                    btnSouth.invalidate();
                    btnSouth.performClick();
                    Handler handler1 = new Handler();
                    Runnable r1 = new Runnable() {
                        public void run() {
                            btnSouth.setPressed(false);
                            btnSouth.invalidate();
                        }
                    };
                    handler1.postDelayed(r1, 600);

                } // end runnable
            };
            handler.postDelayed(r, 600);

        }

        // East Movement
        if (y > EAST_MOVE_FORWARD && highLimitEast == false) {
            highLimitEast = true;

        }
        if (y < EAST_MOVE_BACKWARD && highLimitEast == true) {
            // we have a tilt to the EAST
            highLimitEast = false;
            Handler handler = new Handler();
            Runnable r = new Runnable() {
                public void run() {

                    btnEast.setPressed(true);
                    btnEast.invalidate();
                    btnEast.performClick();
                    Handler handler1 = new Handler();
                    Runnable r1 = new Runnable() {
                        public void run() {
                            btnEast.setPressed(false);
                            btnEast.invalidate();
                        }
                    };
                    handler1.postDelayed(r1, 600);

                } // end runnable
            };
            handler.postDelayed(r, 600);


        }

        // West Movement
        if (y < WEST_MOVE_FORWARD && highLimitWest == false) {
            highLimitWest = true;

        }
        if (y > WEST_MOVE_BACKWARD && highLimitWest == true) {
            // we have a tilt to the WEST
            highLimitWest = false;
            Handler handler = new Handler();
            Runnable r = new Runnable() {
                public void run() {

                    btnWest.setPressed(true);
                    btnWest.invalidate();
                    btnWest.performClick();
                    Handler handler1 = new Handler();
                    Runnable r1 = new Runnable() {
                        public void run() {
                            btnWest.setPressed(false);
                            btnWest.invalidate();
                        }
                    };
                    handler1.postDelayed(r1, 600);

                } // end runnable
            };
            handler.postDelayed(r, 600);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}