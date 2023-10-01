package com.example.a15151.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a15151.R;
import com.example.a15151.constant.Constants;
import com.example.a15151.db.ResultDataSource;
import com.example.a15151.entitys.Question;
import com.example.a15151.entitys.Result;
import com.example.a15151.processing.LevelLoader;

import java.util.List;


public class QuestionActivity extends AppCompatActivity {
    private TextView questionText;
    private RadioGroup answerOptions;
    private Button submitButton;
    private int currentQuestionIndex = 0;
    private List<Question> questions;
    public int correctAnswersCount = 0;
    private CountDownTimer timer;
    private boolean gameFinished = false;
    private int lastScore;
    private int bestScore;

    private int selectedLevel;

    private ResultDataSource resultDataSource;

    public int[] levelResourceIds = Constants.levelResourceIds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        resultDataSource = new ResultDataSource(this);
        resultDataSource.open();

        selectedLevel = getIntent().getIntExtra("level", 0);

        lastScore = getLastScoreForLevel(selectedLevel);
        bestScore = getBestScoreForLevel(selectedLevel);

        int levelResourceId;

        if (selectedLevel >= 0 && selectedLevel < levelResourceIds.length) {
            levelResourceId = levelResourceIds[selectedLevel];
        } else {
            levelResourceId = levelResourceIds[0];
        }

        questions = LevelLoader.loadLevel(this, levelResourceId);

        questionText = findViewById(R.id.questionText);
        answerOptions = findViewById(R.id.answerOptions);
        submitButton = findViewById(R.id.submitButton);

        if (currentQuestionIndex >= questions.size()) {
            finishLevel();
        } else {
            startGame();
        }
    }



    private int getLastScoreForLevel(int level) {
        List<Result> results = resultDataSource.getResultsForLevel(level);
        if (!results.isEmpty()) {
            return results.get(0).getLastScore();
        }
        return 0;
    }

    private int getBestScoreForLevel(int level) {
        List<Result> results = resultDataSource.getResultsForLevel(level);
        if (!results.isEmpty()) {
            return results.get(0).getBestScore();
        }
        return 0;
    }

    private void startGame() {
        if (gameFinished) {
            return;
        }
        if (currentQuestionIndex < questions.size()) {
            displayQuestion(currentQuestionIndex);
        } else {
            finishLevel();
        }

        timer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                if (!gameFinished) {
                    submitButton.setText("Submit (" + millisUntilFinished / 1000 + "s)");
                }
            }

            public void onFinish() {
                if (!gameFinished) {
                    currentQuestionIndex++;
                    submitAnswer();
                }
            }
        }.start();

        submitButton.setOnClickListener(v -> submitAnswer());
    }

    private void displayQuestion(int questionIndex) {
        if (questionIndex >= 0 && questionIndex < questions.size()) {
            Question currentQuestion = questions.get(questionIndex);
            questionText.setText(currentQuestion.getQuestionText());

            answerOptions.removeAllViews();

            for (int i = 0; i < currentQuestion.getAnswerOptions().size(); i++) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(currentQuestion.getAnswerOptions().get(i));
                answerOptions.addView(radioButton);
            }
        }
    }

    private void submitAnswer() {
        if (gameFinished) {
            return;
        }
        int selectedRadioButtonId = answerOptions.getCheckedRadioButtonId();

        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            int selectedAnswerIndex = answerOptions.indexOfChild(selectedRadioButton);

            Question currentQuestion = questions.get(currentQuestionIndex);
            boolean isCorrect = (selectedAnswerIndex == currentQuestion.getCorrectAnswerIndex());

            if (selectedAnswerIndex == currentQuestion.getCorrectAnswerIndex()) {
                correctAnswersCount++;
            }

            String answerMessage = isCorrect ? getString(R.string.correct) : getString(R.string.incorrect);

            Toast.makeText(this, answerMessage, Toast.LENGTH_SHORT).show();

            currentQuestionIndex++;

            if (currentQuestionIndex < questions.size()) {
                displayQuestion(currentQuestionIndex);
            } else {
                timer.cancel();
                finishLevel();
            }
            answerOptions.clearCheck();
            submitButton.setText("Submit");
            timer.cancel();
            startGame();

        } else {
            Toast.makeText(this, R.string.answer, Toast.LENGTH_SHORT).show();
            startGame();
        }
    }

    private void finishLevel() {
        if (!gameFinished) {
            gameFinished = true;

            updateGameResult(selectedLevel, correctAnswersCount);

            lastScore = getLastScoreForLevel(selectedLevel);
            bestScore = getBestScoreForLevel(selectedLevel);

        }
        Intent levelSelectIntent = new Intent(QuestionActivity.this, LevelSelectActivity.class);
        levelSelectIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(levelSelectIntent);
        finishAffinity();

    }


    private void updateGameResult(int level, int lastScore) {
        List<Result> results = resultDataSource.getResultsForLevel(level);

        if (!results.isEmpty()) {
            Result result = results.get(0);
            result.setLastScore(lastScore);

            if (lastScore > result.getBestScore()) {
                result.setBestScore(lastScore);
            }

            resultDataSource.updateResult(result);
        } else {
            Result newResult = new Result(level, lastScore, lastScore);
            resultDataSource.addResult(newResult);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        resultDataSource.close();
    }
    @Override
    public void onBackPressed() {
        Intent levelSelectIntent = new Intent(QuestionActivity.this, LevelSelectActivity.class);
        levelSelectIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(levelSelectIntent);
        finishAffinity();
    }
}

