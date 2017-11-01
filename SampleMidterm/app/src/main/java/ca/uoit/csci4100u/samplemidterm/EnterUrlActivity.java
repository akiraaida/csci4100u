package ca.uoit.csci4100u.samplemidterm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EnterUrlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_url);
    }

    public void handleShowFeed(View view) {
        String url = ((EditText)findViewById(R.id.url)).getText().toString();
        if (!url.isEmpty()) {
            Intent intent = new Intent(EnterUrlActivity.this, ShowFeedActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, url);
            startActivity(intent);
        }
    }
}
