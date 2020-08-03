package com.example.fixitflexjr;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.fixitflexjr.gameUtil.Globals;
import com.example.fixitflexjr.gameUtil.WinOrLoseActivity;

public class PlayActivity extends Activity {
    static PlayActivity activity;
    private SharedPreferences sharedPreferences;
    private DrawingView drawingView;
    private Globals globals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globals = Globals.getInstance();
        int level = globals.getLevel();
        drawingView = new DrawingView(this, level);
        setContentView(drawingView);
        activity = this;
        sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
        int temp = sharedPreferences.getInt("high_score", 0);
        globals.setHighScore(temp);
    }

    @Override
    protected void onPause() {
        Log.i("info", "onPause");
        super.onPause();
        drawingView.pause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("high_score", globals.getHighScore());
        editor.apply();
        MainActivity.getGameSong().pause();
    }

    @Override
    protected void onResume() {
        Log.i("info", "onResume");
        super.onResume();
        drawingView.resume();
        if (MainActivity.isCheckMusic()) {
            MainActivity.getGameSong().start();
        }

    }

    public static PlayActivity getInstance() {
        return activity;
    }

    public void win() {
        finish();
        globals.wonLastGame = true;
        Intent playIntent = new Intent(this, WinOrLoseActivity.class);
        startActivity(playIntent);
    }

    public void lose() {
        finish();
        globals.wonLastGame = false;
        Intent playIntent = new Intent(this, WinOrLoseActivity.class);
        startActivity(playIntent);
    }

}
