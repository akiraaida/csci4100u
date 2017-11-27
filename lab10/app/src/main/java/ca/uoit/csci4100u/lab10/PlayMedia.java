package ca.uoit.csci4100u.lab10;

import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

public class PlayMedia extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private VideoView mVideoView;
    private int[] mRawIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_media_activity);

        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.jingle));
        titles.add(getString(R.string.silent));
        titles.add(getString(R.string.toy));

        final TypedArray rawArray = getResources().obtainTypedArray(R.array.raw_array);
        mRawIds = new int[rawArray.length()];
        for (int i = 0; i < rawArray.length(); i++) {
            mRawIds[i] = rawArray.getResourceId(i, -1);
        }

        Spinner spinner = (Spinner) findViewById(R.id.rawList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, titles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void handlePlay(View view) {
        Spinner spinner = (Spinner) findViewById(R.id.rawList);
        int pos = spinner.getSelectedItemPosition();
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
        }
        if (mVideoView != null) {
            if (mVideoView.isPlaying()) {
                mVideoView.stopPlayback();
            }
        }

        mVideoView = (VideoView) findViewById(R.id.videoView);
        if (pos == 2) {
            mVideoView.setVisibility(View.VISIBLE);
            String path = "android.resource://" + getPackageName() + "/" + mRawIds[pos];
            mVideoView.setVideoURI(Uri.parse(path));
            mVideoView.start();
        } else {
            mVideoView.setVisibility(View.GONE);
            mMediaPlayer = MediaPlayer.create(this, mRawIds[pos]);
            mMediaPlayer.start();
        }
    }

    public void handlePause(View view) {
        if (mMediaPlayer != null) {
            if(mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            }
        }
        if (mVideoView != null) {
            if (mVideoView.isPlaying()) {
                mVideoView.pause();
            }
        }
    }
}
