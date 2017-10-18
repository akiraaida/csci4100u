package ca.uoit.csci4100u.assign01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * The MainMenu activity. This activity is launched first
 * and initializes the game.
 */
public class MainMenu extends AppCompatActivity {

    private static String[] m_questions;
    private static String[] m_answers;
    private static int m_answeredQuestions;

    /**
     * The onCreate method which initializes the member variables
     * @param savedInstanceState The instances state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        m_questions = getResources().getStringArray(R.array.questions);
        m_answers = new String[m_questions.length];
        m_answeredQuestions = 0;
    }

    /**
     * The onclick function for the main menu's start quiz button. This method
     * creates an intent to ask the first question (if there are any questions).
     * @param view The view that was clicked
     */
    public void handleStartQuiz(View view) {
        if (m_questions.length > 0) {
            Intent questionIntent = new Intent(MainMenu.this, AskQuestion.class);
            questionIntent.putExtra(Intent.EXTRA_TEXT, m_questions[m_answeredQuestions]);
            startActivityForResult(questionIntent, m_answeredQuestions);
        }
    }

    /**
     * Overriden onActivityResult. Handles the answer intent and asks another
     * question if there is another question to ask. If not, displays the summary
     * @param requestCode The integer specified by the requested startActivityResult
     * @param resultCode The resultCode of the answerIntent if it's good/bad
     * @param answerIntent The answer intent which contains the answer to the asked question
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent answerIntent) {
        String answer = answerIntent.getStringExtra(Intent.EXTRA_TEXT);
        m_answers[requestCode] = answer;
        m_answeredQuestions += 1;

        if (m_answeredQuestions == m_answers.length) {
            Intent summaryIntent = new Intent(MainMenu.this, Summary.class);
            summaryIntent.putExtra(Intent.EXTRA_TEXT, m_answers);
            startActivity(summaryIntent);
            m_answeredQuestions = 0;
            m_answers = new String[m_questions.length];
        } else {
            Intent questionIntent = new Intent(MainMenu.this, AskQuestion.class);
            questionIntent.putExtra(Intent.EXTRA_TEXT, m_questions[m_answeredQuestions]);
            startActivityForResult(questionIntent, m_answeredQuestions);
        }
    }
}
