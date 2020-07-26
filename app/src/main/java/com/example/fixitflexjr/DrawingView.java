package com.example.fixitflexjr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class DrawingView extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    private Thread thread;
    private SurfaceHolder holder;
    private boolean canDraw = true;

    private Paint paint;
    private Bitmap[] felixMovingRight, felixMovingLeft, felixNormalRight, felixNormalLeft, felixWin, felixFixingLeft,
            felixFixingRight, felixFalling, birdLeft, birdRight, brick,
            cake, cloud, niceLander, window, doubleDoor, glasses, bigWindow, door, flowerpot, roof,
            bush, config, initialMenu, life, spritesSinFondo;

    private Bitmap[] ralphClimbing, ralphDemolishing, ralphMoving;
    private Bitmap building, flower;


    int xPosFlex, yPosFlex;
    int xPosRalph, yPosRalph, ralphDest;

    private int currentFelixFrame = 0;
    private int currentRalphFrame = 0;
    private long frameTicker;

    private float x1, x2, y1, y2;

    private int nextDirection = 4;

    private int screenWidth, screenHeight;
    private int blockWSize;
    private int blockHSize;
    public static int LONG_PRESS_TIME = 750;
    private int currentScore = 0;           //Current game score
    final Handler handler = new Handler();
    int lifeOfFlex;

    public DrawingView(Context context, int level) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        frameTicker = 1000 / 4;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        windowsLife = LevelInformations.getLevelInformation(level).clone();

        // initialize
        bricks = new ArrayList<>();
        lifeOfFlex = 3;
        blockWSize = (int) (screenWidth / 6.5);
        blockWSize = (blockWSize / 5) * 5;

        blockHSize = (int) (screenHeight / 11);
        blockHSize = (blockHSize / 5) * 5;

        initializeLocations();
        loadBitmapImages();

        xflexDest = 3;
        yFlexDest = 3;
        xPosFlex = locations[3][3][0];
        yPosFlex = locations[3][3][1];

        //
        xPosRalph = screenWidth / 2;
        yPosRalph = screenHeight / 6;
        ralphAction = RalphAction.moving;
        ralphDest = (new Random()).nextInt(screenWidth - ralphMoving[0].getWidth());


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

                updateFrame(System.currentTimeMillis());

                moveRalph(canvas);

                moveFlex(canvas);

                moveBricks(canvas);

                checkWinAndCheckCollisionOfFlexAndBricks();

                updateScores(canvas);

                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void drawMap(Canvas canvas) {
        // todo add some graphics
        canvas.drawBitmap(building, 0, 0, paint);
        // windows
        for (int i = 0; i < windowsLife.length; i++) {
            for (int j = 0; j < windowsLife[0].length; j++) {
                int thisWindowsLife = windowsLife[i][j];
                canvas.drawBitmap(window[thisWindowsLife], locations[i][j][0], locations[i][j][1], paint);

            }
        }
        // sprites
        canvas.drawBitmap(spritesSinFondo[0], (int) (-1 * screenWidth / 12.6), (int) (screenHeight / 40), paint);
        // door
        canvas.drawBitmap(door[0], (int) (screenWidth / 2.37), (int) (screenHeight - screenHeight / 6.2), paint);
        // flower
        canvas.drawBitmap(flower, (int) (screenWidth / 1.73), (int) (screenHeight - screenHeight / 6.05), paint);
        canvas.drawBitmap(flower, (int) (screenWidth / 10.5), (int) (screenHeight - screenHeight / 6.05), paint);
        for (int i = 0; i < 14; i++) {
            canvas.drawBitmap(flowerpot[0], (int) (screenWidth / 10.4 + i * 0.6 * screenWidth / 10.4 ), (int) (screenHeight - screenHeight / 5.35), paint);
        }

    }

    // flex
    FLexAction fLexAction = FLexAction.normalLeft;
    private int flexMovingDistance;

    public void moveFlex(Canvas canvas) {
        /*
        switch (nextDirection){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
         */
        // todo there is duplicate code clean it later
        MediaPlayer actionSong;
        switch (fLexAction) {
            case movingRight:
                switch (nextDirection) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        fLexAction = FLexAction.movingLeft;
                        currentFelixFrame = 0;
                        nextDirection = 4;
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                }
                break;
            case movingLeft:
                switch (nextDirection) {
                    case 0:
                        break;
                    case 1:
                        fLexAction = FLexAction.movingRight;
                        currentFelixFrame = 0;
                        nextDirection = 4;
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                }
                break;
            case normalRight:
                switch (nextDirection) {
                    case 0:
                        ///////
                        fLexAction = FLexAction.jumpingRight;
                        currentFelixFrame = 0;
                        nextDirection = 4;
                        flexMovingDistance = getFlexDistance(0);
                        actionSong = MediaPlayer.create(PlayActivity.getInstance(), R.raw.jumpsound);
                        actionSong.setVolume(100, 100);
                        actionSong.setLooping(false);
                        actionSong.start();
                        break;
                    case 1:
                        fLexAction = FLexAction.movingRight;
                        currentFelixFrame = 0;
                        nextDirection = 4;
                        flexMovingDistance = getFlexDistance(1);
                        break;
                    case 2:
                        fLexAction = FLexAction.downwardRight;
                        currentFelixFrame = 0;
                        nextDirection = 4;
                        flexMovingDistance = getFlexDistance(2);
                        actionSong = MediaPlayer.create(PlayActivity.getInstance(), R.raw.jumpsound);
                        actionSong.setVolume(100, 100);
                        actionSong.setLooping(false);
                        actionSong.start();
                        break;
                    case 3:
                        fLexAction = FLexAction.normalLeft;
                        currentFelixFrame = 0;
                        nextDirection = 4;
                        break;
                    case 4:
                        break;
                    case 5:
                        fLexAction = FLexAction.fixingRight;
                        currentFelixFrame = 0;
                        nextDirection = 4;
                        break;
                }
                break;
            case normalLeft:
                switch (nextDirection) {
                    case 0:
                        ///////
                        fLexAction = FLexAction.jumpingLeft;
                        currentFelixFrame = 0;
                        nextDirection = 4;
                        flexMovingDistance = getFlexDistance(0);
                        actionSong = MediaPlayer.create(PlayActivity.getInstance(), R.raw.jumpsound);
                        actionSong.setVolume(100, 100);
                        actionSong.setLooping(false);
                        actionSong.start();
                        break;
                    case 1:
                        fLexAction = FLexAction.normalRight;
                        currentFelixFrame = 0;
                        nextDirection = 4;
                        break;
                    case 2:
                        fLexAction = FLexAction.downWardLeft;
                        currentFelixFrame = 0;
                        nextDirection = 4;
                        flexMovingDistance = getFlexDistance(2);
                        actionSong = MediaPlayer.create(PlayActivity.getInstance(), R.raw.jumpsound);
                        actionSong.setVolume(100, 100);
                        actionSong.setLooping(false);
                        actionSong.start();
                        break;
                    case 3:
                        fLexAction = FLexAction.movingLeft;
                        currentFelixFrame = 0;
                        nextDirection = 4;
                        flexMovingDistance = getFlexDistance(3);
                        break;
                    case 4:
                        break;
                    case 5:
                        fLexAction = FLexAction.fixingLeft;
                        currentFelixFrame = 0;
                        nextDirection = 4;
                        break;
                }
                break;
            case fixingLeft:
                break;
            case fixingRight:
                break;
            case falling:
                break;
            case jumpingLeft:
                break;
            case jumpingRight:
                break;
            case downwardRight:
                break;
            case downWardLeft:
                break;
        }

        drawFelix(canvas);

        switch (fLexAction) {
            case movingRight:
                xPosFlex += flexMovingDistance / 15;
                if (flexLocationIsValid()) {
                    fLexAction = FLexAction.normalRight;
                    currentFelixFrame = 0;
                }
                break;
            case movingLeft:
                xPosFlex -= flexMovingDistance / 15;
                if (flexLocationIsValid()) {
                    fLexAction = FLexAction.normalLeft;
                    currentFelixFrame = 0;
                }
                break;
            case normalRight:
                break;
            case normalLeft:
                break;
            case fixingLeft:
                if (currentFelixFrame == 1) {
                    fLexAction = FLexAction.normalLeft;
                    currentFelixFrame = 0;
                    fixWindow(xflexDest, yFlexDest);
                }
                break;
            case fixingRight:
                if (currentFelixFrame == 1) {
                    fLexAction = FLexAction.normalRight;
                    currentFelixFrame = 0;
                    fixWindow(xflexDest, yFlexDest);
                }
                break;
            case falling:
                yPosFlex += 5;
                if (yPosFlex > screenHeight * 0.80) {
                    currentFelixFrame = 0;
                    fLexAction = FLexAction.normalRight;
                    xflexDest = 3;
                    yFlexDest = 3;
                    xPosFlex = locations[3][3][0];
                    yPosFlex = locations[3][3][1];
                }
                break;
            case jumpingLeft:
                yPosFlex -= flexMovingDistance / 15;
                if (flexLocationIsValid()) {
                    fLexAction = FLexAction.normalLeft;
                    currentFelixFrame = 0;
                }
                break;
            case jumpingRight:
                yPosFlex -= flexMovingDistance / 15;
                if (flexLocationIsValid()) {
                    fLexAction = FLexAction.normalRight;
                    currentFelixFrame = 0;
                }
                break;
            case downwardRight:
                yPosFlex += flexMovingDistance / 15;
                if (flexLocationIsValid()) {
                    fLexAction = FLexAction.normalRight;
                    currentFelixFrame = 0;
                }
                break;
            case downWardLeft:
                yPosFlex += flexMovingDistance / 15;
                if (flexLocationIsValid()) {
                    fLexAction = FLexAction.normalLeft;
                    currentFelixFrame = 0;
                }
                break;
        }

    }

    public void drawFelix(Canvas canvas) {
        Bitmap[] felixActionBitmap = getFelixActionBitmap();
        canvas.drawBitmap(felixActionBitmap[currentFelixFrame], xPosFlex, yPosFlex, paint);
    }

    private void fixWindow(int x, int y) {
        windowsLife[x][y] += 1;
        if (windowsLife[x][y] == window.length) {
            windowsLife[x][y] -= 1;
        }
        MediaPlayer gameSong = MediaPlayer.create(PlayActivity.getInstance(), R.raw.fixsound);
        gameSong.setVolume(100, 100);
        gameSong.setLooping(false);
        gameSong.start();
    }

    int xflexDest, yFlexDest;

    private int getFlexDistance(int direction) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                int x = locations[i][j][0], y = locations[i][j][1];
                if (x == xPosFlex && y == yPosFlex) {
                    switch (direction) {
                        case 0:
                            if (j == 0) {
                                return 0;
                            } else {
                                xflexDest = i;
                                yFlexDest = j - 1;
                                return Math.abs(locations[i][j][1] - locations[i][j - 1][1]);
                            }
                        case 1:
                            if (i == 4) {
                                return 0;
                            } else {
                                xflexDest = i + 1;
                                yFlexDest = j;
                                return Math.abs(locations[i][j][0] - locations[i + 1][j][0]);
                            }
                        case 2:
                            if (j == 3) {
                                return 0;
                            } else {
                                xflexDest = i;
                                yFlexDest = j + 1;
                                return Math.abs(locations[i][j][1] - locations[i][j + 1][1]);
                            }
                        case 3:
                            if (i == 0) {
                                return 0;
                            } else {
                                xflexDest = i - 1;
                                yFlexDest = j;
                                return Math.abs(locations[i][j][0] - locations[i - 1][j][0]);
                            }
                        default:
                            return 0;
                    }
                }
            }
        }
        return 0;
    }

    private boolean flexLocationIsValid() {
        if (Math.abs(xPosFlex - locations[xflexDest][yFlexDest][0]) < 15 && Math.abs(yPosFlex - locations[xflexDest][yFlexDest][1]) < 15) {
            yPosFlex = locations[xflexDest][yFlexDest][1];
            xPosFlex = locations[xflexDest][yFlexDest][0];
            return true;
        }

        return false;
    }

    private Bitmap[] getFelixActionBitmap() {
        switch (fLexAction) {
            case movingRight:
                return felixMovingRight;
            case movingLeft:
                return felixMovingLeft;
            case normalRight:
                return felixNormalRight;
            case normalLeft:
                return felixNormalLeft;
            case fixingLeft:
                return felixFixingLeft;
            case fixingRight:
                return felixFixingRight;
            case falling:
                return felixFalling;
            case jumpingLeft:
                return new Bitmap[]{felixMovingLeft[0]};
            case jumpingRight:
                return new Bitmap[]{felixMovingRight[0]};
            case downwardRight:
                return new Bitmap[]{felixMovingRight[0]};
            case downWardLeft:
                return new Bitmap[]{felixMovingLeft[0]};
            default:
                return null;
        }

    }

    // ralph
    RalphAction ralphAction;

    public void moveRalph(Canvas canvas) {

        drawRalph(canvas);

        switch (ralphAction) {
            case moving:
                int dif = (int) Math.signum(ralphDest - xPosRalph) * 5;
                xPosRalph += dif;
                if (Math.abs(ralphDest - xPosRalph) < 10) {
                    ralphAction = RalphAction.demolishing;
                    currentRalphFrame = 0;

                    MediaPlayer actionSong = MediaPlayer.create(PlayActivity.getInstance(), R.raw.smashsound);
                    actionSong.setVolume(100, 100);
                    actionSong.setLooping(false);
                    actionSong.start();
                }
                break;
            case climbing:
                break;
            case demolishing:
                if (currentRalphFrame == ralphDemolishing.length - 1) {
                    ralphDest = (new Random()).nextInt(screenWidth - ralphMoving[0].getWidth());
                    ralphAction = RalphAction.moving;
                    currentRalphFrame = 0;
                }
        }
    }

    private void drawRalph(Canvas canvas) {
        Bitmap[] ralphActionBitmap = getRalphActionBitmap();
        canvas.drawBitmap(ralphActionBitmap[currentRalphFrame], xPosRalph, yPosRalph, paint);
    }

    private Bitmap[] getRalphActionBitmap() {
        switch (ralphAction) {
            case moving:
                return ralphMoving;
            case climbing:
                return ralphClimbing;
            case demolishing:
                return ralphDemolishing;
        }
        return new Bitmap[0];
    }

    ArrayList<Integer[]> bricks;

    private void demolishBrick(int xPosRalph, int currentRalphFrame) {
        Integer[] result = new Integer[2];
        result[1] = yPosRalph + ralphDemolishing[0].getHeight();
        result[0] = xPosRalph + ralphDemolishing[0].getWidth();
        if (currentRalphFrame == 0 || currentRalphFrame == 2) {
            result[0] -= ralphDemolishing[0].getWidth();
        }
        if (currentRalphFrame == 2 || currentRalphFrame == 3) {
            return;
        }
        bricks.add(result);
    }

    private void checkWinAndCheckCollisionOfFlexAndBricks() {
        boolean collision = false;
        for (Integer[] thisBrick : bricks) {
            int x = thisBrick[0];
            int y = thisBrick[1];
            if (doCollide(xPosFlex, yPosFlex, x, y)) {
                collision = true;
                break;
            }

        }
        boolean isWin = true;
        for (int i = 0; i < windowsLife.length; i++) {
            for (int j = 0; j < windowsLife[0].length; j++) {
                if (windowsLife[i][j] != window.length - 1) {
                    isWin = false;
                }
            }
        }
        if (isWin) {
            win();
        } else if (collision) {
            bricks.clear();
            fLexAction = FLexAction.falling;
            currentFelixFrame = 0;
            lifeOfFlex--;
            if (lifeOfFlex == 0) {
                lost();
            } else {
                MediaPlayer actionSong = MediaPlayer.create(PlayActivity.getInstance(), R.raw.loselifesound);
                actionSong.setVolume(100, 100);
                actionSong.setLooping(false);
                actionSong.start();
            }
        }
    }

    private boolean doCollide(int xFlex, int yFlex, int x, int y) {
        int brickWidth = brick[0].getWidth(), brickHeight = brick[0].getHeight();

        boolean resultX = false;
        if (xFlex < x && x < (xFlex + felixNormalLeft[0].getWidth())) {
            resultX = true;
        }
        if (xFlex < x + brickWidth && x + brickWidth < (xFlex + felixNormalLeft[0].getWidth())) {
            resultX = true;
        }
        boolean resultY = false;
        if (yFlex < y && y < (yFlex + felixNormalLeft[0].getHeight())) {
            resultY = true;
        }
        if (yFlex < y + brickHeight && y + brickHeight < (yFlex + felixNormalLeft[0].getHeight())) {
            resultY = true;
        }

        return resultX && resultY;
    }

    private void moveBricks(Canvas canvas) {
        ArrayList<Integer[]> removed = new ArrayList<>();
        for (Integer[] thisBrick : bricks) {
            int x = thisBrick[0];
            int y = thisBrick[1];
            thisBrick[1] += 5;
            canvas.drawBitmap(brick[0], x, y, paint);
            if (y > screenHeight * (0.75)) {
                removed.add(thisBrick);
            }
        }
        bricks.removeAll(removed);
    }

    public void updateScores(Canvas canvas) {
        paint.setTextSize(blockWSize);

        Globals g = Globals.getInstance();
        int highScore = g.getHighScore();
        if (currentScore > highScore) {
            g.setHighScore(currentScore);
        }


        // todo draw life bitmap
        for (int i = 0; i < lifeOfFlex; i++) {
            int x = screenWidth - (blockWSize * i / 3 + 110) - life[0].getWidth();
            canvas.drawBitmap(life[0], x, blockHSize / 4, paint);
        }

    }


    Runnable longPressed = new Runnable() {
        public void run() {
            /*
            Log.i("info", "LongPress");
            Intent pauseIntent = new Intent(getContext(), PauseActivity.class);
            getContext().startActivity(pauseIntent);
             */
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
        // 5 means fixing

        // Checks which axis has the greater distance
        // in order to see which direction the swipe is
        // going to be (buffering of direction)

        if (fLexAction == FLexAction.normalRight || fLexAction == FLexAction.normalLeft) {

        } else {
            return;
        }

        if (Math.abs(yDiff) < 20 && Math.abs(xDiff) < 20) {
            //pressed
            nextDirection = 5;
            return;
        }
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

    long ralphFrameTicker = 100 / 4;

    private void updateFrame(long gameTime) {
        int totalFrame = getFelixActionBitmap().length;
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
        if (gameTime > ralphFrameTicker + 4 * 30) {
            ralphFrameTicker = gameTime;
            if (ralphAction == RalphAction.demolishing) {
                demolishBrick(xPosRalph, currentRalphFrame);
            }
            currentRalphFrame++;
            if (currentRalphFrame >= getRalphActionBitmap().length) {
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

        int felixWSize = (int) (blockWSize);
        felixWSize = (felixWSize / 5) * 5;

        int felixHSize = (int) (blockHSize * 0.8);
        felixHSize = (felixHSize / 5) * 5;

        felixMovingLeft = new Bitmap[4];
        felixMovingLeft[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixmovingleft0), felixWSize, felixHSize, false);
        felixMovingLeft[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixmovingleft1), felixWSize, felixHSize, false);
        felixMovingLeft[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixmovingleft2), felixWSize, felixHSize, false);
        felixMovingLeft[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixmovingleft3), felixWSize, felixHSize, false);

        felixMovingRight = new Bitmap[4];
        felixMovingRight[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixmovingright0), felixWSize, felixHSize, false);
        felixMovingRight[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixmovingright1), felixWSize, felixHSize, false);
        felixMovingRight[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixmovingright2), felixWSize, felixHSize, false);
        felixMovingRight[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixmovingright3), felixWSize, felixHSize, false);

        felixNormalLeft = new Bitmap[1];
        felixNormalLeft[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixnormalleft0), felixWSize, felixHSize, false);

        felixNormalRight = new Bitmap[1];
        felixNormalRight[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixnormalright0), felixWSize, felixHSize, false);

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

        felixFixingLeft = new Bitmap[4];
        felixFixingLeft[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixfixingleft0), felixWSize, felixHSize, false);
        felixFixingLeft[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixfixingleft1), felixWSize, felixHSize, false);
        felixFixingLeft[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixfixingleft0), felixWSize, felixHSize, false);
        felixFixingLeft[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixfixingleft1), felixWSize, felixHSize, false);

        felixFixingRight = new Bitmap[4];
        felixFixingRight[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixfixingright0), felixWSize, felixHSize, false);
        felixFixingRight[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixfixingright1), felixWSize, felixHSize, false);
        felixFixingRight[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixfixingright0), felixWSize, felixHSize, false);
        felixFixingRight[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixfixingright1), felixWSize, felixHSize, false);

        felixFalling = new Bitmap[2];
        felixFalling[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixfalling0), felixWSize, felixHSize, false);
        felixFalling[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixfalling1), felixWSize, felixHSize, false);




        int ralphWSize = (int) (screenWidth / 4);
        ralphWSize = (ralphWSize / 5) * 5;

        int ralphHSize = screenHeight / 7;
        ralphHSize = (ralphHSize / 5) * 5;

        ralphClimbing = new Bitmap[4];
        ralphClimbing[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.ralphclimbing0), ralphWSize, ralphHSize, false);
        ralphClimbing[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.ralphclimbing1), ralphWSize, ralphHSize, false);
        ralphClimbing[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.ralphclimbing0), ralphWSize, ralphHSize, false);
        ralphClimbing[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.ralphclimbing1), ralphWSize, ralphHSize, false);

        ralphDemolishing = new Bitmap[4];
        ralphDemolishing[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.ralphdemolishing0), ralphWSize, ralphHSize, false);
        ralphDemolishing[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.ralphdemolishing1), ralphWSize, ralphHSize, false);
        ralphDemolishing[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.ralphdemolishing0), ralphWSize, ralphHSize, false);
        ralphDemolishing[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.ralphdemolishing1), ralphWSize, ralphHSize, false);

        ralphMoving = new Bitmap[4];
        ralphMoving[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.ralphmoving0), ralphWSize, ralphHSize, false);
        ralphMoving[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.ralphmoving1), ralphWSize, ralphHSize, false);
        ralphMoving[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.ralphmoving0), ralphWSize, ralphHSize, false);
        ralphMoving[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.ralphmoving1), ralphWSize, ralphHSize, false);

        building = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.building0), screenWidth, screenHeight, false);

        int flowerWSize = (int) (screenWidth) / 3;
        flowerWSize = (flowerWSize / 5) * 5;

        int flowerHSize = (int) (screenHeight / 7);
        flowerHSize = (flowerHSize / 5) * 5;

        flower = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.flower), flowerWSize, flowerHSize, false);

        int birdWSize = screenWidth / 17;
        birdWSize = (birdWSize / 5) * 5;

        int birdHSize = screenHeight / 17;
        birdHSize = (birdHSize / 5) * 5;


        birdLeft = new Bitmap[2];
        birdLeft[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.birdleft0), birdWSize, birdHSize, false);
        birdLeft[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.birdleft1), birdWSize, birdHSize, false);

        birdRight = new Bitmap[2];
        birdRight[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.birdright0), birdWSize, birdHSize, false);
        birdRight[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.birdright1), birdWSize, birdHSize, false);

        int brickWSize = screenWidth / 17;
        brickWSize = (brickWSize / 5) * 5;

        int brickHSize = brickWSize / 2;
        brickHSize = (brickHSize / 5) * 5;

        brick = new Bitmap[2];
        brick[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.brick0), brickWSize, brickHSize, false);
        brick[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.brick1), brickWSize, brickHSize, false);

        int cakeWSize = screenWidth / 17;
        cakeWSize = (cakeWSize / 5) * 5;

        int cakeHSize = screenHeight / 17;
        cakeHSize = (cakeHSize / 5) * 5;

        cake = new Bitmap[2];
        cake[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.cake0), cakeWSize, cakeHSize, false);
        brick[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.cake1), cakeWSize, cakeHSize, false);

        int cloudWSize = screenWidth / 17;
        cloudWSize = (cloudWSize / 5) * 5;

        int cloudHSize = screenHeight / 17;
        cloudHSize = (cloudHSize / 5) * 5;

        cloud = new Bitmap[1];
        cloud[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.cloud0), cloudWSize, cloudHSize, false);

        int niceLanderWSize = screenWidth / 17;
        niceLanderWSize = (niceLanderWSize / 5) * 5;

        int niceLanderHSize = screenHeight / 17;
        niceLanderHSize = (niceLanderHSize / 5) * 5;

        niceLander = new Bitmap[2];
        niceLander[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.nicelander0), niceLanderWSize, niceLanderHSize, false);
        niceLander[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.nicelander1), niceLanderWSize, niceLanderHSize, false);

        int windowWSize = blockWSize;
        windowWSize = (windowWSize / 5) * 5;

        int windowHSize = (int) (blockHSize * 0.9);
        windowHSize = (windowHSize / 5) * 5;

        window = new Bitmap[5];
        window[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.window0), windowWSize, windowHSize, false);
        window[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.window2), windowWSize, windowHSize, false);
        window[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.window6), windowWSize, windowHSize, false);
        window[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.window7), windowWSize, windowHSize, false);
        window[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.window9), windowWSize, windowHSize, false);

        int doubleDoorWSize = screenWidth / 17;
        doubleDoorWSize = (doubleDoorWSize / 5) * 5;

        int doubleDoorHSize = screenHeight / 17;
        doubleDoorHSize = (doubleDoorHSize / 5) * 5;

        doubleDoor = new Bitmap[4];
        doubleDoor[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.doubledoor0), doubleDoorWSize, doubleDoorHSize, false);
        doubleDoor[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.doubledoor1), doubleDoorWSize, doubleDoorHSize, false);
        doubleDoor[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.doubledoor2), doubleDoorWSize, doubleDoorHSize, false);
        doubleDoor[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.doubledoor3), doubleDoorWSize, doubleDoorHSize, false);

        int glassesWSize = screenWidth / 17;
        glassesWSize = (glassesWSize / 5) * 5;

        int glassesHSize = screenHeight / 17;
        glassesHSize = (glassesHSize / 5) * 5;

        glasses = new Bitmap[7];
        glasses[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.glasses0), glassesWSize, glassesHSize, false);
        glasses[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.glasses1), glassesWSize, glassesHSize, false);
        glasses[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.glasses2), glassesWSize, glassesHSize, false);
        glasses[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.glasses3), glassesWSize, glassesHSize, false);
        glasses[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.glasses4), glassesWSize, glassesHSize, false);
        glasses[5] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.glasses5), glassesWSize, glassesHSize, false);
        glasses[6] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.glasses6), glassesWSize, glassesHSize, false);

        int flowerpotWSize = screenWidth / 17;
        flowerpotWSize = (flowerpotWSize / 5) * 5;

        int flowerpotHSize = screenHeight / 36;
        flowerpotHSize = (flowerpotHSize / 5) * 5;

        flowerpot = new Bitmap[1];
        flowerpot[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.flowerpot), flowerpotWSize, flowerpotHSize, false);

        int roofWSize = screenWidth / 17;
        roofWSize = (roofWSize / 5) * 5;

        int roofHSize = screenHeight / 17;
        roofHSize = (roofHSize / 5) * 5;

        roof = new Bitmap[1];
        roof[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.roof), roofWSize, roofHSize, false);

        int bigWindowWSize = screenWidth / 17;
        bigWindowWSize = (bigWindowWSize / 5) * 5;

        int bigWindowHSize = screenHeight / 17;
        bigWindowHSize = (bigWindowHSize / 5) * 5;

        bigWindow = new Bitmap[5];
        bigWindow[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.bigwindow0), bigWindowWSize, bigWindowHSize, false);
        bigWindow[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.bigwindow1), bigWindowWSize, bigWindowHSize, false);
        bigWindow[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.bigwindow2), bigWindowWSize, bigWindowHSize, false);
        bigWindow[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.bigwindow3), bigWindowWSize, bigWindowHSize, false);
        bigWindow[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.bigwindow4), bigWindowWSize, bigWindowHSize, false);

        int doorWSize = (int) (screenWidth / 6);
        doorWSize = (doorWSize / 5) * 5;

        int doorHSize = (int) (screenHeight / 8);
        doorHSize = (doorHSize / 5) * 5;

        door = new Bitmap[5];
        door[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.door0), doorWSize, doorHSize, false);
        door[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.door1), doorWSize, doorHSize, false);
        door[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.door2), doorWSize, doorHSize, false);
        door[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.door3), doorWSize, doorHSize, false);
        door[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.door4), doorWSize, doorHSize, false);

        int bushWSize = screenWidth / 17;
        bushWSize = (bushWSize / 5) * 5;

        int bushHSize = screenHeight / 17;
        bushHSize = (bushHSize / 5) * 5;

        bush = new Bitmap[1];
        bush[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.bush), bushWSize, bushHSize, false);

        int configWSize = screenWidth / 17;
        configWSize = (configWSize / 5) * 5;

        int configHSize = screenHeight / 17;
        configHSize = (configHSize / 5) * 5;

        config = new Bitmap[1];
        config[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.config), configWSize, configHSize, false);

        int initialMenuWSize = screenWidth / 17;
        initialMenuWSize = (initialMenuWSize / 5) * 5;

        int initialMenuHSize = screenHeight / 17;
        initialMenuHSize = (initialMenuHSize / 5) * 5;


        initialMenu = new Bitmap[1];
        roof[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.initial_menu), initialMenuWSize, initialMenuHSize, false);

        int lifeWSize = (int) (screenWidth / 17 * 1.3 / 1.5);
        lifeWSize = (lifeWSize / 5) * 5;

        int lifeHSize = (int) (screenHeight / 17 / 1.5);
        lifeHSize = (lifeHSize / 5) * 5;

        life = new Bitmap[1];
        life[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.life), lifeWSize, lifeHSize, false);

        int spritesSinFondoWSize =(int) (screenWidth * 1.175);
        spritesSinFondoWSize = (spritesSinFondoWSize / 5) * 5;

        int spritesSinFondoHSize = (int) (screenHeight / 2.9);
        spritesSinFondoHSize = (spritesSinFondoHSize / 5) * 5;

        spritesSinFondo = new Bitmap[1];
        spritesSinFondo[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.sprites_sin_fondo), spritesSinFondoWSize, spritesSinFondoHSize, false);

    }

    int[][] windowsLife;

    int[][][] locations;

    private void initializeLocations() {
        locations = new int[5][4][2];
        int xThreshold = (int) (screenWidth / 7);
        int yThreshold = (int) (screenHeight / 3.1);
        int xDist = (int) (screenWidth / 6.5);
        int yDist = (int) (screenHeight / 8.5);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 0 || i == 1) {
                    int x = i * xDist + xThreshold, y = j * yDist + yThreshold;
                    int xThr = (int) (yThreshold / 11);
                    locations[i][j][0] = x - xThr;
                    locations[i][j][1] = y;
                } else if (i == 2) {
                    int x = i * xDist + xThreshold, y = j * yDist + yThreshold;
                    int xThr = (int) (yThreshold / 23);
                    locations[i][j][0] = x - xThr;
                    locations[i][j][1] = y;
                } else {
                    int x = i * xDist + xThreshold, y = j * yDist + yThreshold;
                    locations[i][j][0] = x;
                    locations[i][j][1] = y;
                }
                if (j == 0) {
                    locations[i][j][1] += (int) (screenHeight / 45);
                }
                if (j == 3) {
                    locations[i][j][1] -= (int) (screenHeight / 45);
                }
            }
        }
    }

    private void win() {
        PlayActivity.getInstance().win();
    }

    private void lost() {
        lifeOfFlex = 3;
        windowsLife = LevelInformations.getLevelInformation(Globals.getInstance().getLevel()).clone();
        PlayActivity.getInstance().lose();
    }
}