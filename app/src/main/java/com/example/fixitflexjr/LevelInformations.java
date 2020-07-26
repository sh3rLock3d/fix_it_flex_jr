package com.example.fixitflexjr;

public class LevelInformations {
    private static final int[][] level1 = {
            {4, 3, 4, 3},
            {3, 4, 3, 2},
            {4, 3, 2, 4},
            {4, 1, 0, 3},
            {2, 3, 2, 4}
    };

    private static final int[][] level2 = {
            {2, 3, 4, 1},
            {3, 4, 0, 2},
            {4, 2, 3, 0},
            {1, 4, 2, 3},
            {4, 2, 3, 4}
    };


    private static final int[][] level3 = {
            {3, 1, 4, 3},
            {0, 2, 1, 0},
            {3, 4, 0, 2},
            {4, 0, 3, 1},
            {1, 3, 2, 4}
    };


    private static final int[][] level4 = {
            {3, 0, 2, 0},
            {0, 1, 3, 2},
            {4, 2, 0, 0},
            {1, 0, 2, 0},
            {2, 3, 1, 2}
    };

    private static final int[][] level5 = {
            {1, 0, 0, 2},
            {0, 3, 1, 0},
            {1, 2, 0, 1},
            {0, 0, 1, 0},
            {0, 2, 0, 0}
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
