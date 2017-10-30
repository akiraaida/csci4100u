package ca.uoit.csci4100u.lab07;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class DeleteGrade extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_grade);
    }

    public void handleDelete(View view) {
        String studentId = ((EditText) findViewById(R.id.delStudentId)).getText().toString();

        if (!studentId.isEmpty()) {
            Intent intent = new Intent();
            intent.putExtra(Intent.EXTRA_TEXT, studentId);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
