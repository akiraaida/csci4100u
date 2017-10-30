package ca.uoit.csci4100u.lab07;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddGrade extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_grade);
    }

    public void handleAdd(View view) {
        String studentId = ((EditText) findViewById(R.id.studentId)).getText().toString();
        String courseComponent = ((EditText) findViewById(R.id.courseComponent)).getText().toString();
        String grade = ((EditText) findViewById(R.id.grade)).getText().toString();

        if (!studentId.isEmpty() && !courseComponent.isEmpty() && !grade.isEmpty()) {
            Intent intent = new Intent();
            intent.putExtra(Intent.EXTRA_TEXT, new String[]{studentId, courseComponent, grade});
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
