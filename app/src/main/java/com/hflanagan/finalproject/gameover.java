package com.hflanagan.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class gameover extends AppCompatActivity {
TextView finalScore, finalRound;
EditText playerName;
int finalScoreNum, finalRoundNum;

public DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

       db = new DatabaseHandler(this);
       db.emptyHiScores();
       InsertingData();

      finalScoreNum = getIntent().getIntExtra("score", -1);
      finalRoundNum = getIntent().getIntExtra("round", -1);

      finalScore = findViewById(R.id.tvFinalScore);
      finalScore.setText(String.valueOf(finalScoreNum));

      finalRound = findViewById(R.id.tvFinalRound);
      finalRound.setText(String.valueOf(finalRoundNum));

      playerName = findViewById(R.id.etSubmitName);

    }

    public void startHiScore(View view) {
        Intent HiScoreIntent = new Intent(gameover.this, hiScores.class);
        startActivity(HiScoreIntent);
    }

    public void startPlayAgain(View view) {
        Intent MainIntent = new Intent(gameover.this, MainActivity.class);
        MainIntent.putExtra("score", 0);
        startActivity(MainIntent);
    }
    public void doSubmit(View view) {
        List<HiScore> top5HiScores = db.getTopFiveScores();
        HiScore lastScore = top5HiScores.get(top5HiScores.size() - 1);

        if(finalScoreNum > lastScore.score && playerName.getText().toString() != ""){

            String userName = playerName.getText().toString();

            String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

            db.addHiScore(new HiScore(date, userName, finalScoreNum));

            top5HiScores = db.getTopFiveScores();

            for (HiScore hs : top5HiScores) {
                String log =
                        "Id: " + hs.getScore_id() +
                                " , Player: " + hs.getPlayer_name() +
                                " , Score: " + hs.getScore();

                // Writing HiScore to log
                Log.i("Score: ", log);
            }
        }


       Intent hiScoreIntent = new Intent(view.getContext(), hiScores.class);
        startActivity(hiScoreIntent);

    }
    public void InsertingData(){

        db.addHiScore(new HiScore("01/01/2020", "Harry", 10));
        db.addHiScore(new HiScore("02/02/2020", "Ruth", 4));
        db.addHiScore(new HiScore("03/03/2020", "Max", 3));
        db.addHiScore(new HiScore("04/04/2020", "Daniel", 2));
        db.addHiScore(new HiScore("05/05/2020", "James", 1));

    }

}