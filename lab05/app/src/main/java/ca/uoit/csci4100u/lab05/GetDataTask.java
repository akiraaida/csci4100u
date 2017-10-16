package ca.uoit.csci4100u.lab05;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by akira on 10/16/17.
 */

public class GetDataTask extends AsyncTask<String, Void, String> {

    private TaskListener taskListener;

    public void setTaskListener(TaskListener taskListener) {
        this.taskListener = taskListener;
    }

    @Override
    protected String doInBackground(String... strings) {
        String data = "";
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream inputRaw = conn.getInputStream();
            BufferedReader input = new BufferedReader(new InputStreamReader(inputRaw));
            String line;
            while ((line = input.readLine()) != null) {
                data += line;
            }
            inputRaw.close();
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    protected void onPostExecute(String data) {
        taskListener.taskUpdater(data);
    }
}