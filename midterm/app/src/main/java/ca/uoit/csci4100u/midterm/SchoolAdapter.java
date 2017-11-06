package ca.uoit.csci4100u.midterm;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ca.uoit.csci4100u.midterm.Model.School;

public class SchoolAdapter extends ArrayAdapter<School> {
    private Context context;
    private ArrayList<School> data;

    public SchoolAdapter(Context context, ArrayList<School> data) {
        super(context, R.layout.school_list_item, data);
        this.data = data;
        this.context = context;
    }

    public int getCount() {
        return data.size();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        School schoolToDisplay = data.get(position);

        Log.d("SchoolAdapter", "School:");
        Log.d("SchoolAdapter", "  Name:   " + schoolToDisplay.getName());
        Log.d("SchoolAdapter", "  Address:   " + schoolToDisplay.getAddress());
        Log.d("SchoolAdapter", "  City:  " + schoolToDisplay.getCity());
        Log.d("SchoolAdapter", "  Postal Code: " + schoolToDisplay.getPostalCode());
        Log.d("SchoolAdapter", "  Phone: " + schoolToDisplay.getPhone());

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.school_list_item, parent, false);
        }

        // populate the views with the data from story
        TextView lblName = (TextView)convertView.findViewById(R.id.lblName);
        lblName.setText(schoolToDisplay.getName());

        TextView lblAddress = (TextView)convertView.findViewById(R.id.lblAddress);
        lblAddress.setText(schoolToDisplay.getAddress());

        TextView lblCity = (TextView)convertView.findViewById(R.id.lblCity);
        lblCity.setText(schoolToDisplay.getCity());

        TextView lblPostalCode = (TextView)convertView.findViewById(R.id.lblPostalCode);
        lblPostalCode.setText(schoolToDisplay.getPostalCode());

        TextView lblPhone = (TextView)convertView.findViewById(R.id.lblPhone);
        lblPhone.setText(schoolToDisplay.getPhone());

        return convertView;
    }
}
