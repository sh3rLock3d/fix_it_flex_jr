package com.example.fixitflexjr;

import android.content.Context;
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
            , felixFixingLeft, felixFixingRight, felixFalling, birdLeft, birdRight, brick
            ,cake ,cloud ,niceLander, window, doubleDoor, glasses, bigWindow, door, flowerpot, roof,
            bush, config, initialMenu, life, spritesSinFondo;

    private Bitmap[] ralphClimbing, ralphDemolishing, ralphMoving;
    private Bitmap building;


    int xPosFlex, yPosFlex;


    private int currentFelixFrame = 0;
    private int currentRalphFrame = 0;
    private long frameTicker;


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
        frameTicker = 1000/4;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        // todo initialize ...

        blockSize = (int)(screenWidth/6.5);
        blockSize = (blockSize / 5) * 5;

        initializeLocations();
        xPosFlex = locations[3][0][0];
        yPosFlex = locations[3][0][1];

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
                //drawArrowIndicators(canvas);

                updateFrame(System.currentTimeMillis());

                //moveRalph(canvas);


                moveFlex(canvas);

                //Update current and high scores
                //updateScores(canvas);
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

    FLexAction fLexAction = FLexAction.normalLeft;


    private int flexMovingDistance = 50; // todo
    public void moveFlex(Canvas canvas) {
        // todo set fLexAction based on fLexNextAction
        // todo for example if he is in the 5th window of a row he catt go any righter
        // todo currentFelixFrame = 0;

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
        // todo initialize flexMovingDistance
        switch (fLexAction){
            case movingRight:
                switch (nextDirection){
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
                switch (nextDirection){
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
                switch (nextDirection){
                    case 0:
                        ///////
                        fLexAction = FLexAction.jumpingRight;
                        currentFelixFrame = 0;
                        nextDirection = 4;
                        flexMovingDistance = getFlexDistance(0);
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
                switch (nextDirection){
                    case 0:
                        ///////
                        fLexAction = FLexAction.jumpingLeft;
                        currentFelixFrame = 0;
                        nextDirection = 4;
                        flexMovingDistance = getFlexDistance(0);
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


        switch (fLexAction){
            case movingRight:
                xPosFlex += flexMovingDistance / 15;
                if (flexLocationIsValid()){
                    fLexAction = FLexAction.normalRight;
                    currentFelixFrame = 0;
                }
                break;
            case movingLeft:
                xPosFlex -= flexMovingDistance / 15;
                if (flexLocationIsValid()){
                    fLexAction = FLexAction.normalLeft;
                    currentFelixFrame = 0;
                }
                break;
            case normalRight:
                break;
            case normalLeft:
                break;
            case fixingLeft:
                if (currentFelixFrame == felixFixingLeft.length - 1){
                    fLexAction = FLexAction.normalLeft;
                    currentFelixFrame = 0;
                     // todo fix window
                }
                break;
            case fixingRight:
                if (currentFelixFrame == felixFixingRight.length - 1){
                    fLexAction = FLexAction.normalRight;
                    currentFelixFrame = 0;
                    // todo fix window
                }
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

        /*
        if (direction == 0) {
            yPosFlex += -blockSize/15;
        } else if (direction == 1) {
            xPosFlex += blockSize/15;
        } else if (direction == 2) {
            yPosFlex += blockSize/15;
        } else if (direction == 3) {
            xPosFlex += -blockSize/15;
        }
        */

    }

    int xflexDest, yFlexDest;
    private int getFlexDistance(int direction){
        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                int x = locations[i][j][0], y = locations[i][j][1];
                if (x == xPosFlex && y == yPosFlex){
                    switch (direction){
                        case 0:
                            if (j == 0){
                                return 0;
                            } else {
                                xflexDest = i;
                                yFlexDest = j-1;
                                return Math.abs(locations[i][j][1] - locations[i][j - 1][1]);
                            }
                        case 1:
                            if (i == 4){
                                return 0;
                            } else {
                                xflexDest = i + 1;
                                yFlexDest = j;
                                return Math.abs(locations[i][j][0] - locations[i+1][j][0]);
                            }
                        case 2:
                            if (j == 3){
                                return 0;
                            } else {
                                xflexDest = i;
                                yFlexDest = j+1;
                                return Math.abs(locations[i][j][1] - locations[i][j + 1][1]);
                            }
                        case 3:
                            if (i == 0){
                                return 0;
                            } else {
                                xflexDest = i-1;
                                yFlexDest = j;
                                return Math.abs(locations[i][j][0] - locations[i-1][j][0]);
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
        if(Math.abs(xPosFlex - locations[xflexDest][yFlexDest][0]) < 15 && Math.abs(yPosFlex - locations[xflexDest][yFlexDest][1]) < 15) {
            yPosFlex = locations[xflexDest][yFlexDest][1];
            xPosFlex = locations[xflexDest][yFlexDest][0];
            return true;
        }

        return false;
    }

    public void drawFelix(Canvas canvas) {
        Bitmap[] felixActionBitmap = getFelixActionBitmap();
        canvas.drawBitmap(felixActionBitmap[currentFelixFrame], xPosFlex, yPosFlex, paint);
    }

    private Bitmap[] getFelixActionBitmap() {
        switch (fLexAction){
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
            case jumpingLeft: // todo
                return null;
            case jumpingRight:
                return null;
            case downwardRight:
                return null;
            case downWardLeft:
                return null;
            default:
                return null;
        }

    }




    // Method to draw map layout
    public void drawMap(Canvas canvas) {
        canvas.drawBitmap(building, 0, 0, paint);
        // windows
        for(int i = 0; i < windowsLife.length; i++ ){
            for(int j = 0; j < windowsLife[0].length; j++ ){
                int thisWindowsLife = windowsLife[i][j];
                if (thisWindowsLife == 1){
                    canvas.drawBitmap(window[0], locations[i][j][0], locations[i][j][1], paint);
                } else {
                    canvas.drawBitmap(window[1], locations[i][j][0], locations[i][j][1], paint);
                }
            }
        }

    }

    Runnable longPressed = new Runnable() {
        public void run() {
            /*
            Log.i("info", "LongPress");
            Intent pauseIntent = new Intent(getContext(), PauseActivity.class);
            getContext().startActivity(pauseIntent);
             */
            windowsLife[0][0] = 5;
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


        //todo if not normal return

        if (Math.abs(yDiff) <  20 && Math.abs(xDiff) < 20) {
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

        int felixSize = screenWidth/7;
        felixSize = (felixSize / 5) * 5;

        felixMovingLeft = new Bitmap[4]; // 7 image frames for right direction
        felixMovingLeft[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixmovingleft0), felixSize, felixSize, false);
        felixMovingLeft[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixmovingleft1), felixSize, felixSize, false);
        felixMovingLeft[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixmovingleft2), felixSize, felixSize, false);
        felixMovingLeft[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixmovingleft3), felixSize, felixSize, false);

        felixMovingRight = new Bitmap[4];
        felixMovingRight[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixmovingright0), felixSize, felixSize, false);
        felixMovingRight[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixmovingright1), felixSize, felixSize, false);
        felixMovingRight[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixmovingright2), felixSize, felixSize, false);
        felixMovingRight[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixmovingright3), felixSize, felixSize, false);

        felixNormalLeft = new Bitmap[1];
        felixNormalLeft[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixnormalleft0), felixSize, felixSize, false);

        felixNormalRight = new Bitmap[1];
        felixNormalRight[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixnormalright0), felixSize, felixSize, false);

        felixWin = new Bitmap[6];
        felixWin[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixwin0), felixSize, felixSize, false);
        felixWin[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixwin1), felixSize, felixSize, false);
        felixWin[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixwin2), felixSize, felixSize, false);
        felixWin[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixwin3), felixSize, felixSize, false);
        felixWin[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixwin4), felixSize, felixSize, false);
        felixWin[5] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixwin5), felixSize, felixSize, false);

        felixFixingLeft = new Bitmap[2];
        felixFixingLeft[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixfixingleft0), felixSize, felixSize, false);
        felixFixingLeft[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixfixingleft1), felixSize, felixSize, false);

        felixFixingRight = new Bitmap[2];
        felixFixingRight[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixfixingright0), felixSize, felixSize, false);
        felixFixingRight[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixfixingright1), felixSize, felixSize, false);

        felixFalling = new Bitmap[2];
        felixFalling[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixfalling0), felixSize, felixSize, false);
        felixFalling[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.felixfalling1), felixSize, felixSize, false);


        int ralphSize = screenWidth/17;
        ralphSize = (ralphSize / 5) * 5;

        ralphClimbing = new Bitmap[2];
        ralphClimbing[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.ralphclimbing0), ralphSize, ralphSize, false);
        ralphClimbing[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.ralphclimbing1), ralphSize, ralphSize, false);

        ralphDemolishing = new Bitmap[2];
        ralphDemolishing[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.ralphdemolishing0), ralphSize, ralphSize, false);
        ralphDemolishing[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.ralphdemolishing1), ralphSize, ralphSize, false);

        ralphMoving = new Bitmap[2];
        ralphMoving[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.ralphmoving0), ralphSize, ralphSize, false);
        ralphMoving[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.ralphmoving1), ralphSize, ralphSize, false);


        building = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.building0), screenWidth, screenHeight, false);

        int birdSize = screenWidth/17;
        birdSize = (birdSize / 5) * 5;

        birdLeft = new Bitmap[2];
        birdLeft[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.birdleft0), birdSize, birdSize, false);
        birdLeft[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.birdleft1), birdSize, birdSize, false);

        birdRight = new Bitmap[2];
        birdRight[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.birdright0), birdSize, birdSize, false);
        birdRight[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.birdright1), birdSize, birdSize, false);

        int brickSize = screenWidth/17;
        brickSize = (brickSize / 5) * 5;

        brick = new Bitmap[2];
        brick[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.brick0), brickSize, brickSize, false);
        brick[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.brick1), brickSize, brickSize, false);

        int cakeSize = screenWidth/17;
        cakeSize = (cakeSize / 5) * 5;

        cake = new Bitmap[2];
        cake[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.cake0), cakeSize, cakeSize, false);
        brick[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.cake1), cakeSize, cakeSize, false);

        int cloudSize = screenWidth/17;
        cloudSize = (cloudSize / 5) * 5;

        cloud = new Bitmap[1];
        cloud[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.cloud0), cloudSize, cloudSize, false);

        int niceLanderSize = screenWidth/17;
        niceLanderSize = (niceLanderSize / 5) * 5;

        niceLander = new Bitmap[2];
        niceLander[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.nicelander0), niceLanderSize, niceLanderSize, false);
        niceLander[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.nicelander1), niceLanderSize, niceLanderSize, false);

        int windowSize = blockSize;
        windowSize = (windowSize / 5) * 5;

        window = new Bitmap[2];
        window[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.window0), windowSize, windowSize, false);
        window[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.window1), windowSize, windowSize, false);

        int doubleDoorSize = screenWidth/17;
        doubleDoorSize = (doubleDoorSize / 5) * 5;

        doubleDoor = new Bitmap[4];
        doubleDoor[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.doubledoor0), doubleDoorSize, doubleDoorSize, false);
        doubleDoor[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.doubledoor1), doubleDoorSize, doubleDoorSize, false);
        doubleDoor[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.doubledoor2), doubleDoorSize, doubleDoorSize, false);
        doubleDoor[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.doubledoor3), doubleDoorSize, doubleDoorSize, false);

        int glassesSize = screenWidth/17;
        glassesSize = (glassesSize / 5) * 5;

        glasses = new Bitmap[7];
        glasses[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.glasses0), glassesSize, glassesSize, false);
        glasses[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.glasses1), glassesSize, glassesSize, false);
        glasses[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.glasses2), glassesSize, glassesSize, false);
        glasses[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.glasses3), glassesSize, glassesSize, false);
        glasses[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.glasses4), glassesSize, glassesSize, false);
        glasses[5] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.glasses5), glassesSize, glassesSize, false);
        glasses[6] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.glasses6), glassesSize, glassesSize, false);

        int flowerpotSize = screenWidth/17;
        flowerpotSize = (flowerpotSize / 5) * 5;

        flowerpot = new Bitmap[1];
        flowerpot[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.flowerpot), flowerpotSize, flowerpotSize, false);

        int roofSize = screenWidth/17;
        roofSize = (roofSize / 5) * 5;

        roof = new Bitmap[1];
        roof[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.roof), roofSize, roofSize, false);

        int bigWindowSize = screenWidth/17;
        bigWindowSize = (bigWindowSize / 5) * 5;

        bigWindow = new Bitmap[5];
        bigWindow[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.bigwindow0), bigWindowSize, bigWindowSize, false);
        bigWindow[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.bigwindow1), bigWindowSize, bigWindowSize, false);
        bigWindow[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.bigwindow2), bigWindowSize, bigWindowSize, false);
        bigWindow[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.bigwindow3), bigWindowSize, bigWindowSize, false);
        bigWindow[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.bigwindow4), bigWindowSize, bigWindowSize, false);

        int doorSize = screenWidth/17;
        doorSize = (doorSize / 5) * 5;

        door = new Bitmap[5];
        door[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.door0), doorSize, doorSize, false);
        door[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.door1), doorSize, doorSize, false);
        door[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.door2), doorSize, doorSize, false);
        door[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.door3), doorSize, doorSize, false);
        door[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.door4), doorSize, doorSize, false);

        int bushSize = screenWidth/17;
        bushSize = (bushSize / 5) * 5;

        bush = new Bitmap[1];
        bush[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.bush), bushSize, bushSize, false);

        int configSize = screenWidth/17;
        configSize = (configSize / 5) * 5;

        config = new Bitmap[1];
        config[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.config), configSize, configSize, false);

        int initialMenuSize = screenWidth/17;
        initialMenuSize = (initialMenuSize / 5) * 5;

        initialMenu = new Bitmap[1];
        roof[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.initial_menu), initialMenuSize, initialMenuSize, false);

        int lifeSize = screenWidth/17;
        lifeSize = (lifeSize / 5) * 5;

        life = new Bitmap[1];
        life[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.life), lifeSize, lifeSize, false);

        int spritesSinFondoSize = screenWidth/17;
        spritesSinFondoSize = (spritesSinFondoSize / 5) * 5;

        spritesSinFondo = new Bitmap[1];
        spritesSinFondo[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.sprites_sin_fondo), spritesSinFondoSize, spritesSinFondoSize, false);

    }

    int[][] windowsLife = {
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1}
    };

    int[][][] locations;

    private void initializeLocations(){
        //TODO fix measures
        locations = new int[5][4][2];
        int xThreshold = (int)(screenWidth/ 7);
        int yThreshold = (int)(screenHeight/ 3);
        int xDist = (int)(screenWidth/6.5);
        int yDist = (int)(screenHeight/8.5);

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 4; j++){
                if(i == 0 || i == 1) {
                    int x = i * xDist + xThreshold, y = j * yDist + yThreshold;
                    int xThr = (int)(yThreshold / 11);
                    locations[i][j][0] = x - xThr;
                    locations[i][j][1] = y;
                } else if (i == 2) {
                    int x = i * xDist + xThreshold, y = j * yDist + yThreshold;
                    int xThr = (int)(yThreshold / 23);
                    locations[i][j][0] = x - xThr;
                    locations[i][j][1] = y;
                } else {
                    int x = i * xDist + xThreshold, y = j * yDist + yThreshold;
                    locations[i][j][0] = x;
                    locations[i][j][1] = y;
                }
            }
        }
    }



    public static void control(){

    }

}

