package ca.uoit.csci4100u.assign01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * The Summary activity. This activity is launched after
 * all of the questions are answered displaying the game summary
 * and score that the player received.
 */
public class Summary extends AppCompatActivity {

    /**
     * The onCreate method for the Summary activity which sets
     * the display views with the questions, user answers, and
     * the correct answers
     * @param savedInstanceState The instances state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);

        int score = 0;

        String[] questions = getResources().getStringArray(R.array.questions);
        String[] correctAnswers = getResources().getStringArray(R.array.answers);

        Intent summaryIntent = getIntent();
        String[] userAnswers = summaryIntent.getStringArrayExtra(Intent.EXTRA_TEXT);

        TextView summaryTextView = (TextView) findViewById(R.id.summary);
        TextView questionTextView = (TextView) findViewById(R.id.question);
        TextView userAnswerTextView = (TextView) findViewById(R.id.userAnswer);
        TextView correctAnswerTextView = (TextView) findViewById(R.id.correctAnswer);

        String questionString = "";
        String userAnswerString = "";
        String correctAnswerString = "";
        for (int i = 0; i < questions.length; ++i) {
            questionString += questions[i] + "\n";
            userAnswerString += userAnswers[i] + "\n";
            correctAnswerString += correctAnswers[i] + "\n";

            if (userAnswers[i].compareTo(correctAnswers[i]) == 0) {
                ++score;
            }
        }

        summaryTextView.setText(summaryTextView.getText().toString() + " " + score);
        questionTextView.setText(questionString);
        userAnswerTextView.setText(userAnswerString);
        correctAnswerTextView.setText(correctAnswerString);
    }
}
