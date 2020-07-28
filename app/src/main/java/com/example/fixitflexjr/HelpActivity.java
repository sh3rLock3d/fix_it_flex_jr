package com.example.fixitflexjr;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HelpActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_layout);
        MainActivity.getGameSong().start();
        TextView textView = findViewById(R.id.textView2);
        String text;
        text = "Hi Guys \n" +
                "Welcome to fix it flex jr game \n" +
                "In this game you have to play against the saboteur Ralph and repair his sabotage" +
                " so that the inhabitants of the city will be happy and proud of you.\n" +
                "How to play now?\n" +
                "To move in the direction you want to pull your finger in the desired direction," +
                " Also, to repair the windows of the building, go to the desired window and click on it several times.\n" +
                "Finally, you should always be careful that the bricks do not hit you, and if you fix all the windows, you will go to the next level.";
        textView.setText(text);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.getGameSong().pause();
    }

}
