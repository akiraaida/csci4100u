package ca.uoit.csci4100u.assign02;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The async class which will convert the Canadian dollar to bit coin by getting the value from the
 * specified website
 */
public class GetBitCoinTask extends AsyncTask<String, Void, String> {

    /**
     * Member variables
     */
    private TaskListener mTaskListener;

    /**
     * Setter for the task listener so classes that implement the observer can subscribe to it
     * @param taskListener
     */
    public void setTaskListener(TaskListener taskListener) {
        this.mTaskListener = taskListener;
    }

    /**
     * The main work that the async task does. This will take the given URL and get the file
     * associated with the URL. The only line of this file is the value in bit coin that based off
     * of the value supplied in the URL.
     * @param strings The string representation of the URL that will be retrieved
     * @return The converted value from the Canadian dollar in bit coin
     */
    @Override
    protected String doInBackground(String... strings) {
        String bitCoin = "";
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream inputRaw = conn.getInputStream();
            BufferedReader input = new BufferedReader(new InputStreamReader(inputRaw));
            String line;
            while ((line = input.readLine()) != null) {
                bitCoin = line;
            }
            inputRaw.close();
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitCoin;
    }

    /**
     * The onPostExecute method which is called after doInBackground completes. This will be called on
     * all of the subscribers of the observer.
     * @param data The bit coin value of the product
     */
    protected void onPostExecute(String data) {
        mTaskListener.taskUpdater(Float.parseFloat(data));
    }

}
