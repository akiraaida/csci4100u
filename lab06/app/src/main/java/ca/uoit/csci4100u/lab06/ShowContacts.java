package ca.uoit.csci4100u.lab06;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ShowContacts extends AppCompatActivity {

    private static final int ADD_ID = 1;
    private static final int DELETE_ID = 2;
    private static int m_idCounter;
    private static List<Contact> m_contacts;
    private static List<String> m_contactsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_contacts);

        // TODO: Initialize from file later
        m_contacts = new ArrayList<>();
        m_contactsData = new ArrayList<>();
        for (Contact contactData : m_contacts) {
            m_contactsData.add(contactData.toString());
        }
        m_idCounter = 0;
    }

    public void handleAdd(View view) {
        Intent intent = new Intent(ShowContacts.this, AddContact.class);
        startActivityForResult(intent, ADD_ID);
    }

    public void handleDelete(View view) {
        Intent intent = new Intent(ShowContacts.this, DeleteContact.class);
        String[] temp = new String[m_contactsData.size()];

        intent.putStringArrayListExtra(Intent.EXTRA_TEXT, (ArrayList<String>)m_contactsData);
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
        }
    }

}
