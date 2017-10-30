package ca.uoit.csci4100u.lab06;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Output;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShowContacts extends AppCompatActivity {

    private static final int ADD_ID = 1;
    private static final int DELETE_ID = 2;
    private static int m_idCounter;
    private static List<Contact> m_contacts;
    private static List<String> m_contactsData;
    private static final String mFileName = "contactData.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_contacts);

        m_contacts = new ArrayList<>();
        m_contactsData = new ArrayList<>();
        m_idCounter = 0;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (m_contacts.isEmpty()) {
            try {
                FileInputStream fileInputStream = openFileInput(mFileName);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] data = line.split(",");
                    Contact newContact = new Contact(Integer.parseInt(data[0]), data[1], data[2], data[3]);
                    m_idCounter = Integer.parseInt(data[0]) + 1;
                    m_contacts.add(newContact);
                    m_contactsData.add(newContact.toString());
                }
                ListView listView = (ListView) findViewById(R.id.contacts);
                ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, m_contactsData);
                listView.setAdapter(listAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        deleteFile(mFileName);
        try {
            OutputStream outputStream = openFileOutput(mFileName, Context.MODE_PRIVATE);
            for (Contact contact : m_contacts) {
                outputStream.write(contact.toString().getBytes());
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleAdd(View view) {
        Intent intent = new Intent(ShowContacts.this, AddContact.class);
        startActivityForResult(intent, ADD_ID);
    }

    public void handleDelete(View view) {
        Intent intent = new Intent(ShowContacts.this, DeleteContact.class);

        intent.putStringArrayListExtra(Intent.EXTRA_TEXT, (ArrayList<String>) ShowContacts.m_contactsData);
        startActivityForResult(intent, DELETE_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == ADD_ID) {
            String[] data = intent.getStringArrayExtra(Intent.EXTRA_TEXT);
            Contact contact = new Contact(m_idCounter, data[0], data[1], data[2]);
            m_contacts.add(contact);
            m_contactsData.add(contact.toString());
            ListView listView = (ListView) findViewById(R.id.contacts);
            ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, m_contactsData);
            listView.setAdapter(listAdapter);
            m_idCounter += 1;
        } else if (requestCode == DELETE_ID) {
            int id = intent.getIntExtra(Intent.EXTRA_TEXT, -1);
            for (int i = 0; i < m_contacts.size(); i++) {
                if (m_contacts.get(i).getId() == id) {
                    m_contacts.remove(i);
                    m_contactsData.remove(i);
                    ListView listView = (ListView) findViewById(R.id.contacts);
                    ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, m_contactsData);
                    listView.setAdapter(listAdapter);
                }
            }
            deleteFile(mFileName);
            try {
                OutputStream outputStream = openFileOutput(mFileName, Context.MODE_PRIVATE);
                for (Contact contact : m_contacts) {
                    outputStream.write(contact.toString().getBytes());
                }
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
