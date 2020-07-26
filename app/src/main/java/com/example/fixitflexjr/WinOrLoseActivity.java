package com.example.fixitflexjr;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class WinOrLoseActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_or_lose_layout);
        boolean win = Globals.getInstance().wonLastGame;
        int level = Globals.getInstance().getLevel();
        if (win){
            Globals.getInstance().incrementLevel();
        }

        TextView textView = findViewById(R.id.winMessage);
        String s;
        if (win) {
            s = "you win";
        } else {
            s = "you lose";
        }
        s += "\nlevel : " + level;
        textView.setText(s);

        Button button = findViewById(R.id.continueButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGame();
            }
        });
    }

    public void goToGame(){
        Intent playIntent = new Intent(this, PlayActivity.class);
        startActivity(playIntent);
    }
}
