package ca.uoit.csci4100u.assign01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends AppCompatActivity {

    private static int[] answers;
    private static int answered_questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        String[] questions = getResources().getStringArray(R.array.questions);
        answers = new int[questions.length];
        answered_questions = 0;
    }

    public void handleStartQuiz(View view) {
        String[] questions = getResources().getStringArray(R.array.questions);

        for (int i = 0; i < questions.length; ++i) {
            Intent intent = new Intent(MainMenu.this, AskQuestion.class);
            intent.putExtra(Intent.EXTRA_TEXT, questions[i]);
            startActivityForResult(intent, i);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        int answer = intent.getIntExtra(Intent.EXTRA_TEXT, -1);
        answers[requestCode] = answer;
        answered_questions += 1;

        if(answered_questions == answers.length) {
            Intent summaryIntent = new Intent(MainMenu.this, Summary.class);
            summaryIntent.putExtra(Intent.EXTRA_TEXT, answers);
            startActivity(summaryIntent);
        }
    }
}
