package com.example.fixitflexjr.gameUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.fixitflexjr.PlayActivity;
import com.example.fixitflexjr.R;

public class WinOrLoseActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_or_lose_layout);
        boolean win = Globals.getInstance().wonLastGame;
        int level = Globals.getInstance().getLevel();

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        int blockWSize = (int) (screenWidth / 6.5);
        blockWSize = (blockWSize / 5) * 5;

        int blockHSize = screenHeight / 11;
        blockHSize = (blockHSize / 5) * 5;
        int felixWSize = blockWSize;
        felixWSize = (felixWSize / 5) * 5;

        int felixHSize = (int) (blockHSize * 0.8);
        felixHSize = (felixHSize / 5) * 5;

        if (win) {
            Globals.getInstance().incrementLevel();
        }

        TextView textView = findViewById(R.id.winMessage);
        String s;
        if (win) {
            s = "you win";
            ImageView tv1;
            tv1 = findViewById(R.id.imageView);
            Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                    getResources(), R.drawable.felixwin), felixWSize, felixHSize, false);
            tv1.setImageBitmap(bitmap);
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

    public void goToGame() {
        finish();
        Intent playIntent = new Intent(this, PlayActivity.class);
        startActivity(playIntent);
    }
}
