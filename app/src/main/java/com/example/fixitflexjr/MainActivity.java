package com.example.fixitflexjr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static MediaPlayer gameSong;

    // Method to start activity for Help button
    public void showHelpScreen(View view) {
        Intent helpIntent = new Intent(this, HelpActivity.class);
        startActivity(helpIntent);
    }

    // Method to start activity for Play button
    public void showPlayScreen(View view) {
        Intent playIntent = new Intent(this, PlayActivity.class);
        startActivity(playIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameSong = MediaPlayer.create(this, R.raw.fix_it_felix_jr_music);
        gameSong.setVolume(100, 100);
        gameSong.setLooping(true);
        gameSong.start();
    }

    public static MediaPlayer getGameSong() {
        return gameSong;
    }

    @Override
    public void onPause() {
        super.onPause();
        gameSong.pause();
    }

    @Override
    public void onResume() {
        Log.i("info", "MainActivity onResume");
        super.onResume();
        gameSong.start();
    }

    public void showSettingScreen(View view) {

    }

    public void Exit(View view) {
        finish();
    }
}
