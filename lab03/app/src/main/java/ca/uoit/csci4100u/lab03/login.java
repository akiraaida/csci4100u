package ca.uoit.csci4100u.lab03;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class login extends AppCompatActivity {

    private static final String USERNAME = "100526064";
    private static final String PASSWORD = "100526064";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void handleVerificationClick(View view) {
        String username = ((EditText)findViewById(R.id.username)).getText().toString();
        String password = ((EditText)findViewById(R.id.pass)).getText().toString();
        if (username.compareTo(this.USERNAME) == 0 && password.compareTo(this.PASSWORD) == 0) {
            setResult(Activity.RESULT_OK, new Intent());
        } else {
            setResult(Activity.RESULT_CANCELED, new Intent());
        }
        finish();
    }
}
