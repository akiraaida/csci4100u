package ca.uoit.csci4100u.assign01;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * The AskQuestion activity. This activity is launched when
 * a question is asked, displaying the question and a yes or
 * no button.
 */
public class AskQuestion extends AppCompatActivity {

    /**
     * The onCreate method for the AskQuestion activity. This method
     * sets the TextView that will contain the question from the MainMenu's
     * intent
     * @param savedInstanceState The instances state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask_question);

        Intent questionIntent = getIntent();
        String question = questionIntent.getStringExtra(Intent.EXTRA_TEXT);
        TextView textView = (TextView) findViewById(R.id.question);
        textView.setText(question);
    }

    /**
     * Handles the onClick method of the yes button which creates an intent
     * containing the the answer 'yes' then finishes the sub-activity
     * @param view The view that was clicked
     */
    public void handleYes(View view) {
        Intent answerIntent = new Intent();
        answerIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.yes));
        setResult(Activity.RESULT_OK, answerIntent);
        finish();
    }

    /**
     * Handles the onClick method of the no button which creates an intent
     * containing the the answer 'no' then finishes the sub-activity
     * @param view The view that was clicked
     */
    public void handleNo(View view) {
        Intent answerIntent = new Intent();
        answerIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.no));
        setResult(Activity.RESULT_OK, answerIntent);
        finish();
    }
}
