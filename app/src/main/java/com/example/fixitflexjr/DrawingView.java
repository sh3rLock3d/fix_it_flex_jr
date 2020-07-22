package com.example.fixitflexjr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DrawingView extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    private Thread thread;
    private SurfaceHolder holder;
    private boolean canDraw = true;

    private Paint paint;
    private Bitmap[] felixMovingRight, felixMovingLeft, felixNormalRight, felixNormalLeft, felixWin
            , felixFixingLeft, felixFixingRight, felixFalling;
    // todo enter all other bitmaps

    private int totalFrame = 4;
    private int currentFelixFrame = 0;
    private int currentRalphFrame = 0;
    private long frameTicker;
    private int xPosFelix;
    private int yPosFelix;
    private int xPosRalph;
    private int yPosRalph;

    private float x1, x2, y1, y2;

    private int nextDirection = 4;
    private int viewDirection = 2;

    private int screenWidth, screenHeight;
    private int blockSize;
    public static int LONG_PRESS_TIME=750;
    private int currentScore = 0;           //Current game score
    final Handler handler = new Handler();

    public DrawingView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        frameTicker = 1000/totalFrame;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        // todo initialize ...
        // blockSize

        loadBitmapImages();
        Log.i("info", "Constructor");
    }

    @Override
    public void run() {
        Log.i("info", "Run");
        while (canDraw) {
            if (!holder.getSurface().isValid()) {
                continue;
            }
            Canvas canvas = holder.lockCanvas();
            // Set background color to Transparent
            if (canvas != null) {
                canvas.drawColor(Color.BLACK);
                drawMap(canvas);
                drawArrowIndicators(canvas);

                updateFrame(System.currentTimeMillis());

                moveRalph(canvas);


                moveFlex(canvas);

                //Update current and high scores
                updateScores(canvas);
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void updateScores(Canvas canvas) {
        paint.setTextSize(blockSize);

        Globals g = Globals.getInstance();
        int highScore = g.getHighScore();
        if (currentScore > highScore) {
            g.setHighScore(currentScore);
        }

        String formattedHighScore = String.format("%05d", highScore);
        String hScore = "High Score : " + formattedHighScore;
        canvas.drawText(hScore, 0, 2*blockSize - 10, paint);

        String formattedScore = String.format("%05d", currentScore);
        String score = "Score : " + formattedScore;
        canvas.drawText(score, 11 * blockSize, 2 * blockSize - 10, paint);
    }

    public void moveRalph(Canvas canvas) {
        //canvas.drawBitmap(ghostBitmap, xPosRalph, yPosRalph, paint);
    }



    public void moveFlex(Canvas canvas) {
        drawFelix(canvas);
    }

    private void drawArrowIndicators(Canvas canvas) {
        switch(nextDirection) {
            case(0):
                // up
                break;
            case(1):
                //right
                break;
            case(2):
                //down
                break;
            case(3):
                //left
                break;
            default:
                break;
        }

    }


    public void drawFelix(Canvas canvas) {
        switch (viewDirection) {
            case (0):


                break;
            case (1):
                //canvas.drawBitmap(felixMovingRight[currentFelixFrame], xPosFelix, yPosFelix, paint);

                break;
            case (3):

                break;
            default:

                break;
        }
    }

    // Method to draw map layout
    public void drawMap(Canvas canvas) {
        //
    }

    Runnable longPressed = new Runnable() {
        public void run() {
            Log.i("info", "LongPress");
            Intent pauseIntent = new Intent(getContext(), PauseActivity.class);
            getContext().startActivity(pauseIntent);
        }
    };

    // Method to get touch events
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case (MotionEvent.ACTION_DOWN): {
                x1 = event.getX();
                y1 = event.getY();
                handler.postDelayed(longPressed, LONG_PRESS_TIME);
                break;
            }
            case (MotionEvent.ACTION_UP): {
                x2 = event.getX();
                y2 = event.getY();
                calculateSwipeDirection();
                handler.removeCallbacks(longPressed);
                break;
            }
        }
        return true;
    }

    // Calculates which direction the user swipes
    // based on calculating the differences in
    // initial position vs final position of the swipe
    private void calculateSwipeDirection() {
        float xDiff = (x2 - x1);
        float yDiff = (y2 - y1);

        // Directions
        // 0 means going up
        // 1 means going right
        // 2 means going down
        // 3 means going left
        // 4 means stop moving, look at move function

        // Checks which axis has the greater distance
        // in order to see which direction the swipe is
        // going to be (buffering of direction)
        if (Math.abs(yDiff) > Math.abs(xDiff)) {
            if (yDiff < 0) {
                nextDirection = 0;
            } else if (yDiff > 0) {
                nextDirection = 2;
            }
        } else {
            if (xDiff < 0) {
                nextDirection = 3;
            } else if (xDiff > 0) {
                nextDirection = 1;
            }
        }
    }

    // Check to see if we should update the current frame
    // based on time passed so the animation won't be too
    // quick and look bad
    private void updateFrame(long gameTime) {

        // If enough time has passed go to next frame
        if (gameTime > frameTicker + (totalFrame * 30)) {
            frameTicker = gameTime;

            // Increment the frame
            currentFelixFrame++;
            // Loop back the frame when you have gone through all the frames
            if (currentFelixFrame >= totalFrame) {
                currentFelixFrame = 0;
            }
        }
        if (gameTime > frameTicker + (50)) {
            currentRalphFrame++;
            if (currentRalphFrame >= 7) {
                currentRalphFrame = 0;
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("info", "Surface Created");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("info", "Surface Changed");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("info", "Surface Destroyed");
    }

    public void pause() {
        Log.i("info", "pause");
        canDraw = false;
        thread = null;
    }

    public void resume() {
        Log.i("info", "resume");
        if (thread != null) {
            thread.start();
        }
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
            Log.i("info", "resume thread");
        }
        canDraw = true;
    }

    private void loadBitmapImages() {

        int felixSize = screenWidth/17;
        felixSize = (felixSize / 5) * 5;

        felixMovingLeft = new Bitmap[4]; // 7 image frames for right direction
        felixMovingLeft[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixMovingLeft0), felixSize, felixSize, false);
        felixMovingLeft[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixMovingLeft1), felixSize, felixSize, false);
        felixMovingLeft[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixMovingLeft2), felixSize, felixSize, false);
        felixMovingLeft[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixMovingLeft3), felixSize, felixSize, false);

        felixMovingRight = new Bitmap[4];
        felixMovingRight[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixMovingRight0), felixSize, felixSize, false);
        felixMovingRight[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixMovingRight1), felixSize, felixSize, false);
        felixMovingRight[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixMovingRight2), felixSize, felixSize, false);
        felixMovingRight[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixMovingRight3), felixSize, felixSize, false);

        felixNormalLeft = new Bitmap[1];
        felixNormalRight[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixNormalLeft0), felixSize, felixSize, false);

        felixNormalRight = new Bitmap[1];
        felixNormalRight[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixNormalRight0), felixSize, felixSize, false);

        felixWin = new Bitmap[6];
        felixWin[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixWin0), felixSize, felixSize, false);
        felixWin[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixWin1), felixSize, felixSize, false);
        felixWin[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixWin2), felixSize, felixSize, false);
        felixWin[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixWin3), felixSize, felixSize, false);
        felixWin[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixWin4), felixSize, felixSize, false);
        felixWin[5] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixWin5), felixSize, felixSize, false);

        felixFixingLeft = new Bitmap[2];
        felixFixingLeft[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixFixingLeft0), felixSize, felixSize, false);
        felixFixingLeft[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixFixingLeft1), felixSize, felixSize, false);

        felixFixingRight = new Bitmap[2];
        felixFixingRight[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixFixingRight0), felixSize, felixSize, false);
        felixFixingRight[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixFixingRight1), felixSize, felixSize, false);

        felixFalling = new Bitmap[2];
        felixFalling[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixFalling0), felixSize, felixSize, false);
        felixFalling[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixFalling1), felixSize, felixSize, false);


    }

}

