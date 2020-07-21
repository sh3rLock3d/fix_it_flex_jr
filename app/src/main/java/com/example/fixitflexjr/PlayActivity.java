package com.example.fixitflexjr;

import android.app.Activity;
import android.os.Bundle;

public class PlayActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_layout);
        MainActivity.getGameSong().start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.getGameSong().pause();
    }}
