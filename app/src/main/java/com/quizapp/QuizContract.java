package com.quizapp;

import android.provider.BaseColumns;

public final class QuizContract {
    public QuizContract(){}

    public static class QuestionTable implements BaseColumns{

        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "op1";
        public static final String COLUMN_OPTION2 = "op2";
        public static final String COLUMN_OPTION3 = "op3";
        public static final String COLUMN_OPTION4 = "op4";
        public static final String COLUMN_ANSWER = "correctAnswer";
    }
}
