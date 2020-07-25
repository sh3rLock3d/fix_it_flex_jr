package com.example.fixitflexjr;

public class LevelInformations {
    private static final int[][] level1 = {
            {4, 4, 4, 3},
            {3, 4, 4, 3},
            {4, 3, 2, 2},
            {1, 0, 4, 3},
            {2, 2, 2, 3}
    };

    private static final int[][] level2 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    };


    private static final int[][] level3 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    };


    private static final int[][] level4 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    };

    private static final int[][] level5 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    };


    private LevelInformations(){}

    public static int[][] getLevelInformation(int level) {
        switch (level) {
            case 1:
                return level1;
            case 2:
                return level2;
            case 3:
                return level3;
            case 4:
                return level4;
            default:
                return level5;
        }
    }
}
