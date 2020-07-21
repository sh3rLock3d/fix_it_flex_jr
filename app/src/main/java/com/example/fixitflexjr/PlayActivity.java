package com.example.fixitflexjr;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class PlayActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_layout);
        MainActivity.getPlayer().start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.getPlayer().pause();
    }}
