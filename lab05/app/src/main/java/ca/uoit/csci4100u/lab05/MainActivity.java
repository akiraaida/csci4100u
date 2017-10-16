package ca.uoit.csci4100u.lab05;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements TaskListener {

    private static final String GNU_URL = "https://www.gnu.org/licenses/gpl.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleLicense(View view) {
        GetDataTask task = new GetDataTask();
        task.setTaskListener(this);
        task.execute(new String[] {GNU_URL});
    }

    public void taskUpdater(String data) {
        Intent intent = new Intent(MainActivity.this, ShowLicense.class);
        intent.putExtra(Intent.EXTRA_TEXT, data);
        startActivity(intent);
    }

}
