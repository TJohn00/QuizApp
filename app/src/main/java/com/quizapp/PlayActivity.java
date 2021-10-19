package com.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.appbar_background));
        getSupportActionBar().setTitle("Quiz App");

        Button btPlay = findViewById(R.id.playButton);
        btPlay.setOnClickListener((v) -> {
                Intent intent = new Intent(PlayActivity.this,QuizApp.class);
                finish();
                startActivity(intent);
        });
    }
}