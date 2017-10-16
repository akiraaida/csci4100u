package ca.uoit.csci4100u.lab06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);
    }

    public void handleAddNewContact(View view) {
        String firstName = ((EditText) findViewById(R.id.firstName)).getText().toString();
        String lastName = ((EditText) findViewById(R.id.lastName)).getText().toString();
        String phone = ((EditText) findViewById(R.id.phone)).getText().toString();

        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_TEXT, new String[] {firstName, lastName, phone});
        setResult(1, intent);
        finish();
    }
}
