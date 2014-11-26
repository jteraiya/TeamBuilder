package com.nibr;

import java.util.*;

public class TeamBuilder {

    public static final int INFINITY = 99999;
    public static final int PATH_NO_OF_ELEMENT_LOWER_LIMIT = 2;
    public static final int PATH_NO_OF_ELEMENT_UPPER_LIMIT = 50;
    public static String ILLEGAL_PATH_ARG_MESSAGE;

    /**
     * @param paths: String Array that describes the way the locations have been connected
     * @return an int[] with exactly two elements,
     * the first one is the number of locations that can reach all other locations,
     * and the second one is the number of locations that are reachable by all other locations
     * @throws IllegalArgumentException
     */
    public int[] specialLocations(String[] paths) throws IllegalArgumentException {

        if (isPathValid(paths)) {
            int length = paths.length;
            int[] result = new int[2];
            int[][] solMatrx = new int[length][length];

            // Fill solution matrix with infinity (some high num)
            for (int i = 0; i < length; i++) {
                Arrays.fill(solMatrx[i], INFINITY);
            }

            //First Round: Populate the solution matrix with incoming paths
            solMatrx = populateMatrixWithPaths(paths, length, solMatrx);

            //Process with intermediate node, this to figure out any via routes.
            solMatrx = processMatrixWithIntermediateNode(length, solMatrx);

            //Number of locations that can reach all other locations
            result[0] = findNumOfLocsReachesAllLocs(length, solMatrx);

            //Number of locations that are reachable by all other location
            result[1] = findNumOfLocsReachableByAllLocs(length, solMatrx);

            return result;
        } else {
            throw new IllegalArgumentException(ILLEGAL_PATH_ARG_MESSAGE);
        }
    }

    private int findNumOfLocsReachableByAllLocs(int length, int[][] solMatrx) {
        int numOfLocsReachableByAllLoc = 0;
        for (int i = 0; i < length; i++) {
            int num = 0;
            for (int j = 0; j < length; j++) {
                if (i != j) {
                    if (solMatrx[j][i] < INFINITY) {
                        num++;
                    }
                }
            }
            if (num == length - 1)
                numOfLocsReachableByAllLoc++;
        }
        return numOfLocsReachableByAllLoc;
    }

    private int findNumOfLocsReachesAllLocs(int length, int[][] solMatrx) {
        int numOfLocReachesAllLoc = 0;
        for (int i = 0; i < length; i++) {
            int num = 0;
            for (int j = 0; j < length; j++) {
                if (i != j) {
                    if (solMatrx[i][j] < INFINITY) {
                        num++;
                    }
                }
            }
            if (num == length - 1) {
                numOfLocReachesAllLoc++;
            }
        }
        return numOfLocReachesAllLoc;
    }

    private int[][] processMatrixWithIntermediateNode(int length, int[][] solMatrx) {
        //k is an intermediate node in path from i to j.
        for (int k = 0; k < length; k++) {
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {
                    if ((solMatrx[i][k] + solMatrx[k][j]) < solMatrx[i][j]) {
                        solMatrx[i][j] = solMatrx[i][k] + solMatrx[k][j];
                    }
                }
            }
        }
        return solMatrx;
    }

    private int[][] populateMatrixWithPaths(String[] paths, int length, int[][] solMatrx) {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (paths[i].charAt(j) == '1') {
                    solMatrx[i][j] = 1;
                }
            }
        }
        return solMatrx;
    }

    private boolean isPathValid(String[] paths) {
        int length = paths.length;
        boolean isPathValid = true;
        /**
         * paths will contain between 2 and 50 elements, inclusive.
         */
        if (length < PATH_NO_OF_ELEMENT_LOWER_LIMIT || length > PATH_NO_OF_ELEMENT_UPPER_LIMIT) {
            isPathValid = false;
            ILLEGAL_PATH_ARG_MESSAGE = "Paths can contain between 2 and 50 elements, inclusive";
        } else {
            for (int i = 0; i < length; i++) {
                /**
                 * Each element of paths will contain N characters, where N is the number of elements of paths.
                 */
                if (paths[i].length() != length) {
                    isPathValid = false;
                    ILLEGAL_PATH_ARG_MESSAGE = "Each element of paths can contain exactly N characters, where N is the number of elements of paths";
                    /**
                     * Each element of paths will contain only the characters '0' and '1'.
                     */
                } else {
                    for (int j = 0; j < length; j++) {
                        if (paths[i].charAt(j) != '1' && paths[i].charAt(j) != '0') {
                            isPathValid = false;
                        }
                        /**
                         * The i-th element of paths will contain a zero in the i-th position.  So {"01","00"} is valid.
                         */
                        if (i == j && paths[i].charAt(j) != '0') {
                            isPathValid = false;
                        }
                    }
                }
            }
        }

        return isPathValid;

    }
}
