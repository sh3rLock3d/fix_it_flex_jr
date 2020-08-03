package com.example.fixitflexjr.gameUtil;

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


    private LevelInformations() {
    }

    public static int[][] getLevelInformation(int level) {
        switch (level) {
            case 1:
                return copyOfArray(level1);
            case 2:
                return copyOfArray(level2);
            case 3:
                return copyOfArray(level3);
            case 4:
                return copyOfArray(level4);
            default:
                return copyOfArray(level5);
        }
    }


    private static int[][] copyOfArray(int[][] original) {
        int[][] copy = new int[5][4];
        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, original[0].length);
        }
        return copy;
    }
}
