package ca.uoit.csci4100u.lab10;

import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class PlayMedia extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private String[] mSongs;
    private int[] mSongIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_media_activity);

        mSongs = getResources().getStringArray(R.array.songs_array);
        final TypedArray songsArray = getResources().obtainTypedArray(R.array.songs_array);
        mSongIds = new int[songsArray.length()];
        for (int i = 0; i < songsArray.length(); i++) {
            mSongIds[i] = songsArray.getResourceId(i, -1);
        }

        Spinner spinner = (Spinner) findViewById(R.id.songList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, mSongs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void handlePlay(View view) {
        Spinner spinner = (Spinner) findViewById(R.id.songList);
        int pos = spinner.getSelectedItemPosition();
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
        }
        mMediaPlayer = MediaPlayer.create(this, mSongIds[pos]);
        mMediaPlayer.start();
    }

    public void handlePause(View view) {
        if(mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }
}
