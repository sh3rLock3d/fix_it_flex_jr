package com.example.fixitflexjr;

import android.app.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class WinOrLoseActivity extends Activity {

    private Bitmap[] felixWin;
    private int blockWSize;
    private int blockHSize;
    private int screenWidth, screenHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_or_lose_layout);
        boolean win = Globals.getInstance().wonLastGame;
        int level = Globals.getInstance().getLevel();

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        blockWSize = (int) (screenWidth / 6.5);
        blockWSize = (blockWSize / 5) * 5;

        blockHSize = (int) (screenHeight / 11);
        blockHSize = (blockHSize / 5) * 5;
        int felixWSize = (int) (blockWSize);
        felixWSize = (felixWSize / 5) * 5;

        int felixHSize = (int) (blockHSize * 0.8);
        felixHSize = (felixHSize / 5) * 5;

        felixWin = new Bitmap[6];
        felixWin[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixwin0), felixWSize, felixHSize, false);
        felixWin[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixwin1), felixWSize, felixHSize, false);
        felixWin[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixwin2), felixWSize, felixHSize, false);
        felixWin[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixwin3), felixWSize, felixHSize, false);
        felixWin[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixwin4), felixWSize, felixHSize, false);
        felixWin[5] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixwin5), felixWSize, felixHSize, false);
        if (win){
            Globals.getInstance().incrementLevel();
        }

        TextView textView = findViewById(R.id.winMessage);
        String s;
        if (win) {
            s = "you win";
            ImageView tv1;
            tv1= (ImageView) findViewById(R.id.imageView);
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

    public void goToGame(){
        finish();
        Intent playIntent = new Intent(this, PlayActivity.class);
        startActivity(playIntent);
    }
}
