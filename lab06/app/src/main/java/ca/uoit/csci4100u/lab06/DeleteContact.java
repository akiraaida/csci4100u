package ca.uoit.csci4100u.lab06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

public class DeleteContact extends AppCompatActivity {

    List<String> m_contactsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_contact);

        Intent intent = getIntent();
        m_contactsData = intent.getStringArrayListExtra(Intent.EXTRA_TEXT);
        Spinner spinner = (Spinner) findViewById(R.id.contactsDelete);
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, m_contactsData);
        spinner.setAdapter(spinnerAdapter);
    }

    public void handleDeleteContact(View view) {
        Spinner spinner = (Spinner) findViewById(R.id.contactsDelete);
        String line = spinner.getSelectedItem().toString();
        String[] data = line.split(" ");
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_TEXT, Integer.parseInt(data[0]));
        setResult(RESULT_OK, intent);
        finish();
    }
}