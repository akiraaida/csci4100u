package ca.uoit.csci4100u.lab03;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void handleLoginClick(View view) {
        Intent intent = new Intent(MainActivity.this, login.class);
        startActivityForResult(intent, 1);
    }

    public void handleAboutClick(View view) {
        Intent intent = new Intent(MainActivity.this, about.class);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "Login Success", 5).show();
        } else if (requestCode == 1 && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Login Failed", 5).show();
        }
    }
}
