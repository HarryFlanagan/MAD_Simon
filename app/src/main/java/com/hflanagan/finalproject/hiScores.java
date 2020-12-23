package com.hflanagan.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class hiScores extends AppCompatActivity {

    ListView listView;
    private DatabaseHandler db2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hi_scores);
        listView = findViewById(R.id.lvHiScores);


        db2 = new DatabaseHandler(this);

       // db.emptyHiScores();     // empty table if required

        // Inserting hi scores
       // Log.i("Insert: ", "Inserting ..");
       // db.addHiScore(new HiScore("20 OCT 2020", "Elisee", 1));
       // db.addHiScore(new HiScore("28 OCT 2020", "Daniel", 2));
       // db.addHiScore(new HiScore("20 NOV 2020", "Christine", 3));
       // db.addHiScore(new HiScore("20 NOV 2020", "James", 4));
       // db.addHiScore(new HiScore("22 NOV 2020", "Ruth", 5));
       // db.addHiScore(new HiScore("30 NOV 2020", "Max", 6));
       //db.addHiScore(new HiScore("01 DEC 2020", "Tom", 7));
       // db.addHiScore(new HiScore("02 DEC 2020", "Harry", 110));


        // Reading all scores
        Log.i("Reading: ", "Reading all scores..");
        List<HiScore> hiScores = db2.getAllHiScores();


        for (HiScore hs : hiScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

            // Writing HiScore to log
            Log.i("Score: ", log);
        }

        Log.i("divider", "====================");

        HiScore singleScore = db2.getHiScore(5);
        Log.i("High Score 5 is by ", singleScore.getPlayer_name() + " with a score of " +
                singleScore.getScore());

        Log.i("divider", "====================");

        // Calling SQL statement
        List<HiScore> top5HiScores = db2.getTopFiveScores();
        for (HiScore hs : top5HiScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

            // Writing HiScore to log
            Log.i("Score: ", log);
        }
        Log.i("divider", "====================");

        HiScore hiScore = top5HiScores.get(top5HiScores.size() - 1);
        // hiScore contains the 5th highest score
        Log.i("fifth Highest score: ", String.valueOf(hiScore.getScore()) );


        Log.i("divider", "====================");

        // Calling SQL statement
        top5HiScores = db2.getTopFiveScores();
        List<String> scoresStr;
        scoresStr = new ArrayList<>();

        int j = 1;

        for (HiScore hs : top5HiScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

            // store score in string array
            scoresStr.add(j++ + " : " +
                    hs.getPlayer_name() + "\t" +
                    hs.getScore());

            // Writing HiScore to log
            Log.i("Score: ", log);
            Log.i("divider", "Scores in list <>");
            for (String ss : scoresStr){
                Log.i("Score", ss);
            }
            ArrayAdapter<String> itemsAdapter =
                    new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scoresStr);
            listView.setAdapter(itemsAdapter);
        }
    }


}