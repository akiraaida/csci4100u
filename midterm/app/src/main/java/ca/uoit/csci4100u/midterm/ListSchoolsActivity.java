package ca.uoit.csci4100u.midterm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ca.uoit.csci4100u.midterm.Model.School;

public class ListSchoolsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, TaskListener {

    public static final String URL = "http://csundergrad.science.uoit.ca/courses/csci4100u/assessments/school_data.csv";
    private SchoolAdapter schoolAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_schools);

        GetDataTask task = new GetDataTask();
        task.setTaskListener(this);
        task.execute(new String[] {URL});
    }

    @Override
    public void onItemClick(AdapterView aView, View source,
                            int position, long id) {
        School school = (School)schoolAdapter.getItem(position);
        String name = school.getName();
        String address = school.getAddress();
        String city = school.getCity();
        String postalCode = school.getPostalCode();
        String phone = school.getPhone();

        Intent intent = new Intent(ListSchoolsActivity.this, ShowSchoolDetailsActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("address", address);
        intent.putExtra("city", city);
        intent.putExtra("postalCode", postalCode);
        intent.putExtra("phone", phone);
        startActivity(intent);
    }

    @Override
    public void taskUpdater(ArrayList<School> data) {
        schoolAdapter = new SchoolAdapter(this , data);
        ListView feed = (ListView)findViewById(R.id.schoolList);
        feed.setAdapter(schoolAdapter);
        feed.setOnItemClickListener(this);
    }
}

