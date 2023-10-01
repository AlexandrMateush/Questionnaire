package com.example.a15151.activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a15151.MainActivity;
import com.example.a15151.R;
import com.example.a15151.constant.Constants;
import com.example.a15151.db.ResultDataSource;
import com.example.a15151.entitys.Result;

import java.util.List;


public class LevelSelectActivity extends AppCompatActivity {
    private ResultDataSource resultDataSource;
    int[] buttonIds = Constants.buttonIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);
        resultDataSource = new ResultDataSource(this);
        resultDataSource.open();


        for (int i = 0; i < buttonIds.length; i++) {
            Button levelButton = findViewById(buttonIds[i]);
            final int selectedLevel = i;

            List<Result> results = resultDataSource.getResultsForLevel(selectedLevel);

            int lastScore = 0;
            int bestScore = 0;

            if (!results.isEmpty()) {
                Result lastResult = results.get(results.size() - 1);
                lastScore = lastResult.getLastScore();
                bestScore = lastResult.getBestScore();
            }


            levelButton.setText(getString(R.string.level, i + 1, bestScore, lastScore));

            levelButton.setOnClickListener(v -> {
                Intent gameIntent = new Intent(LevelSelectActivity.this, QuestionActivity.class);
                gameIntent.putExtra("level", selectedLevel);
                startActivity(gameIntent);
            });
        }

        Button buttonBack2 = findViewById(R.id.button_back);
        buttonBack2.setOnClickListener(v -> {
            Intent levelSelectIntent = new Intent(LevelSelectActivity.this, MainActivity.class);
            levelSelectIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(levelSelectIntent);
            finishAffinity();
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        resultDataSource.close();
    }
    @Override
    public void onBackPressed() {
        Intent levelSelectIntent = new Intent(LevelSelectActivity.this, MainActivity.class);
        levelSelectIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(levelSelectIntent);
        finishAffinity();
    }
}

