package com.example.fixitflexjr;

public class Globals{
    private static Globals instance;
    private static int level = 1;
    public boolean wonLastGame;
    // Global variable
    private int highScore;

    // Restrict the constructor from being instantiated
    private Globals(){}

    public int getLevel() {
        return level;
    }

    public void incrementLevel() {
        Globals.level += 1;
    }

    public void setHighScore(int newScore){
        this.highScore =newScore;
    }
    public int getHighScore(){
        return this.highScore;
    }

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }
}