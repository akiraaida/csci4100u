package ca.uoit.csci4100u.assign01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);

        int score = 0;

        String[] questions = getResources().getStringArray(R.array.questions);
        int[] correctAnswers = getResources().getIntArray(R.array.answers);

        Intent intent = getIntent();
        int[] answers = intent.getIntArrayExtra(Intent.EXTRA_TEXT);
        TextView textView = (TextView) findViewById(R.id.summary);

        String quest = getResources().getString(R.string.quest);
        String correctAnswer = getResources().getString(R.string.correct_answer);
        String userAnswer = getResources().getString(R.string.user_answer);

        String summary = quest + "\t" + correctAnswer + "\t" + userAnswer + "\n";
        for (int i = 0; i < questions.length; ++i) {
            summary += questions[i] + "\t" + answers[i] + "\t" + correctAnswers[i] + "\n";
            if (answers[i] == correctAnswers[i]) {
                score += 1;
            }
        }
        String finalScore = getResources().getString(R.string.final_score);
        summary += "\n" + finalScore + " " + score;

        textView.setText(summary);
    }
}
