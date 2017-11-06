package ca.uoit.csci4100u.samplemidterm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowFeedActivity extends AppCompatActivity implements StoryDataListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_feed);

        Intent intent = getIntent();
        String url = intent.getStringExtra(Intent.EXTRA_TEXT);
        DownloadFeedTask task = new DownloadFeedTask();
        task.setStoryDataListener(this);
        task.execute(new String[] {url});
    }

    @Override
    public void showStories(ArrayList<Story> data) {
        StoryAdapter storyAdapter = new StoryAdapter(this, data);
        ListView feed = (ListView)findViewById(R.id.feed);
        feed.setAdapter(storyAdapter);
    }
}
