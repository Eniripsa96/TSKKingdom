package com.tsk.sucy;

/**
 * Provides various functions for kingdoms
 */
class KFunction {

    /**
     * Calculates the initial cost of a town based on its size
     *
     * @param size x and z-coordinate size
     * @return     cost of the town
     */
    static double townCost(int size) {
        return townValue(size, size);
    }

    /**
     * Calculates the value of a town based off its dimensions
     *
     * @param width x-coordinate size
     * @param depth z-coordinate size
     * @return      value of the town
     */
    static double townValue(int width, int depth) {
        int area = width * depth;
        return (area + 1.0) * (area / 200.0);
    }

    /**
     * Calculates the cost of expanding a town
     *
     * @param initialWidth x-coordinate size before expanding
     * @param initialDepth z-coordinate size before expanding
     * @param newWidth     x-coordinate size after expanding
     * @param newDepth     z-coordinate size after expanding
     * @return             cost of the expansion
     */
    static double expansionCost(int initialWidth, int initialDepth, int newWidth, int newDepth) {
        return townValue(newWidth, newDepth) - townValue(initialWidth, initialDepth);
    }
}
