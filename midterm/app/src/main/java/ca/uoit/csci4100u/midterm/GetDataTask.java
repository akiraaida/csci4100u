package ca.uoit.csci4100u.midterm;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import ca.uoit.csci4100u.midterm.Model.School;

public class GetDataTask extends AsyncTask<String, Void, ArrayList<School>> {

    private TaskListener taskListener;

    public void setTaskListener(TaskListener taskListener) {
        this.taskListener = taskListener;
    }

    @Override
    protected ArrayList<School> doInBackground(String... strings) {
        ArrayList<School> data = new ArrayList<>();
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream inputRaw = conn.getInputStream();
            BufferedReader input = new BufferedReader(new InputStreamReader(inputRaw));
            String line;
            while ((line = input.readLine()) != null) {
                String[] schoolInfo = line.split(",");
                School newSchool = new School(schoolInfo[0], schoolInfo[1], schoolInfo[2],schoolInfo[3], schoolInfo[4]);
                data.add(newSchool);
            }
            inputRaw.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    protected void onPostExecute(ArrayList<School> data) {
        taskListener.taskUpdater(data);
    }
}
