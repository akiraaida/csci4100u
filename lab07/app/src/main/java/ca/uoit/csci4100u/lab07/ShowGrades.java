package ca.uoit.csci4100u.lab07;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import ca.uoit.csci4100u.lab07.model.Grade;
import ca.uoit.csci4100u.lab07.model.GradesDbHelper;

public class ShowGrades extends AppCompatActivity {

    private static final int ADD_REQUEST = 0;
    private static final int DELETE_REQUEST = 1;
    private static GradesDbHelper mGradesDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_grades);

        mGradesDbHelper = new GradesDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        List<Grade> grades = mGradesDbHelper.getAllGrades();
        ListView listView = (ListView) findViewById(R.id.grades);
        ArrayAdapter<Grade> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, grades);
        listView.setAdapter(listAdapter);
    }

    public void handleAdd(View view) {
        Intent intent = new Intent(ShowGrades.this, AddGrade.class);
        startActivityForResult(intent, ADD_REQUEST);
    }

    public void handleDelete(View view) {
        Intent intent = new Intent(ShowGrades.this, DeleteGrade.class);
        startActivityForResult(intent, DELETE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == ADD_REQUEST) {
            String[] data = intent.getStringArrayExtra(Intent.EXTRA_TEXT);
            mGradesDbHelper.createGrade(data[0], data[1], data[2]);
        } else if (requestCode == DELETE_REQUEST) {
            String studentId = intent.getStringExtra(Intent.EXTRA_TEXT);
            mGradesDbHelper.deleteGrade(studentId);
        }
    }
}
