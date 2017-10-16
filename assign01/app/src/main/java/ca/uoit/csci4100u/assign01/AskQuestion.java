package ca.uoit.csci4100u.assign01;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AskQuestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask_question);

        Intent intent = getIntent();
        String question = intent.getStringExtra(Intent.EXTRA_TEXT);
        TextView textView = (TextView) findViewById(R.id.question);
        textView.setText(question);
    }

    public void handleYes(View view) {
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_TEXT, 1);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void handleNo(View view) {
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_TEXT, 0);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
