package com.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView txtScore,txtCorrectQues,txtWrongQues;

    Button btStartQuiz;
    Button btMainMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.appbar_background));
        getSupportActionBar().setTitle("Quiz App");

        btMainMenu = findViewById(R.id.exitButton);
        btStartQuiz = findViewById(R.id.retryButton);
        txtCorrectQues = findViewById(R.id.txtRightNum);
        txtWrongQues = findViewById(R.id.txtWrongNum);
        txtScore = findViewById(R.id.txtScoreNum);

        btMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this,PlayActivity.class);
                finish();
                startActivity(intent);
            }
        });

        btStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this,QuizApp.class);
                finish();
                startActivity(intent);
            }
        });

        loadScore();

    }

    private void loadScore() {
        Intent intent = getIntent();
        int score = intent.getIntExtra("Score",0);
        int correct = intent.getIntExtra("Correct",0);
        int wrong = intent.getIntExtra("Wrong",0);

        txtScore.setText(String.valueOf(score));
        txtCorrectQues.setText(String.valueOf(correct));
        txtWrongQues.setText(String.valueOf(wrong));
    }
}