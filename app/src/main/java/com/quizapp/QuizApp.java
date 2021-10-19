package com.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class QuizApp extends AppCompatActivity {

    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button bConfirm;

    private TextView tvQuestions;
    private TextView tvScore;
    private TextView tvQCount;
    private TextView tvCD;

    private ArrayList<Questions> questionList;
    private int questionCounter;
    private int questionTotalCount;
    private Questions currentQuestion;
    private boolean answered;

    private int correctAns = 0, wrongAns = 0,score = 0;
    private static final long COUNTDOWN_IN_MILIS = 30000;
    private CountDownTimer countDownTimer;
    private long timeleftinMilis;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.appbar_background));
        getSupportActionBar().setTitle("Quiz App");

        setupUI();
        fetchDB();
    }

    private void setupUI() {
        tvCD = findViewById(R.id.txtTimer);
        tvQCount = findViewById(R.id.txtTotalQuestion);
        tvScore = findViewById(R.id.txtTotalScore);
        tvQuestions = findViewById(R.id.txtQuestion);

        bConfirm =  findViewById(R.id.button);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);
    }

    private void fetchDB(){
        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionList = dbHelper.getAllQuestions();
        startQuiz();
    }

    private void startQuiz() {
        questionTotalCount = questionList.size();
        Collections.shuffle(questionList);
        showQuestions();

        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_button1:
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_option_selected));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        break;
                    case R.id.radio_button2:
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_option_selected));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        break;
                    case R.id.radio_button3:
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_option_selected));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        break;
                    case R.id.radio_button4:
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_option_selected));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        break;

                }
            }
        });

        bConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!answered){
                    if(rb1.isChecked()||rb2.isChecked()||rb3.isChecked()||rb4.isChecked()){
                        quizOperations();
                    }else{
                        Toast.makeText(QuizApp.this,"Please select any one option",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void quizOperations() {
        answered=true;
        countDownTimer.cancel();
        RadioButton rbselected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answrNr = rbGroup.indexOfChild(rbselected)+1;
        checkSolution(answrNr,rbselected);

    }

    private void checkSolution(int answrNr, RadioButton rbselected) {
        switch (currentQuestion.getCorrectAnswer()){
            case 1:
                if(currentQuestion.getCorrectAnswer()==answrNr){
                    rb1.setBackground(ContextCompat.getDrawable(this,R.drawable.when_option_correct));
                    correctLoop();
                    answerHighlightDelay();
                }else{
                    wrongLoop();
                    changeToIncorrectColor(rbselected);
                }
                break;
            case 2:
                if(currentQuestion.getCorrectAnswer()==answrNr){
                    rb2.setBackground(ContextCompat.getDrawable(this,R.drawable.when_option_correct));
                    correctLoop();
                    answerHighlightDelay();
                }else{
                    wrongLoop();
                    changeToIncorrectColor(rbselected);
                }
                break;
            case 3:
                if(currentQuestion.getCorrectAnswer()==answrNr){
                    rb3.setBackground(ContextCompat.getDrawable(this,R.drawable.when_option_correct));
                    correctLoop();
                    answerHighlightDelay();
                }else{
                    wrongLoop();
                    changeToIncorrectColor(rbselected);
                }
                break;
            case 4:
                if(currentQuestion.getCorrectAnswer()==answrNr){
                    rb4.setBackground(ContextCompat.getDrawable(this,R.drawable.when_option_correct));
                    correctLoop();
                    answerHighlightDelay();
                }else{
                    wrongLoop();
                    changeToIncorrectColor(rbselected);
                }
                break;
        }

    }

    private void wrongLoop() {
        wrongAns++;
    }

    private void correctLoop() {
        score+=10;
        tvScore.setText("Score: "+score);
        correctAns++;
    }

    private void answerHighlightDelay() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showQuestions();
            }
        },1000);
    }

    private void changeToIncorrectColor(RadioButton rbselected) {
        rbselected.setBackground(ContextCompat.getDrawable(this,R.drawable.when_option_incorrect));
        answerHighlightDelay();
    }

    public void showQuestions() {
        rbGroup.clearCheck();

        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));

        if(questionCounter<questionTotalCount){

            currentQuestion = questionList.get(questionCounter);
            tvQuestions.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOp1());
            rb2.setText(currentQuestion.getOp2());
            rb3.setText(currentQuestion.getOp3());
            rb4.setText(currentQuestion.getOp4());

            questionCounter++;
            answered = false;

            if(questionCounter==questionTotalCount)
                bConfirm.setText("Finalise");
            else
                bConfirm.setText("Confirm");

            tvQCount.setText("Questions: "+questionCounter+"/"+questionTotalCount);

            timeleftinMilis = COUNTDOWN_IN_MILIS;
            startCD();
        }else{
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finalResult();
                }
            },2000);
        }
    }

    //timer

    private void startCD(){
        countDownTimer=new CountDownTimer(timeleftinMilis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeleftinMilis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeleftinMilis=0;
                updateCountDownText();;
            }
        }.start();

    }

    private void updateCountDownText(){
        int minutes = (int) (timeleftinMilis/1000)/60;
        int seconds = (int) (timeleftinMilis/1000)%60;

        String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        tvCD.setText(timeFormatted);

        if(timeleftinMilis<10000){
            tvCD.setTextColor(Color.RED);
        }else {
            tvCD.setTextColor(Color.WHITE);
        }
        if(timeleftinMilis==0){
            Toast.makeText(QuizApp.this,"Times Up",Toast.LENGTH_SHORT).show();
            handler.postDelayed(() -> {
                    Intent intent = new Intent(getApplicationContext(),QuizApp.class);
                    startActivity(intent);
            },2000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
    }

    private void finalResult(){
        Intent resultData = new Intent(QuizApp.this,ResultActivity.class);
        resultData.putExtra("Score",score);
        resultData.putExtra("Correct",correctAns);
        resultData.putExtra("Wrong",wrongAns);
        finish();
        startActivity(resultData);
    }
}