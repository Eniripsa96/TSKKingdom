package com.tsk.sucy;

import java.text.DecimalFormat;

/**
 * Provides various functions for kingdoms
 */
public class KFunction {

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

    /**
     * Checks if the cuboid conflicts with any town
     *
     * @param cuboid cuboid to check
     * @return       true if conflicting, false otherwise
     */
    static boolean conflictsTown(KCuboid cuboid) {
        for (Town town : TSKKingdom.instance.towns.values()) {
            if (cuboid.overlaps(town.cuboid)) return true;
        }

        return false;
    }

    /**
     * Checks if the cuboid conflicts with any plot
     *
     * @param cuboid cuboid to check
     * @return       true if conflicting, false otherwise
     */
    static boolean conflictsPlot(KCuboid cuboid) {
        for (Plot plot : TSKKingdom.instance.plots.values()) {
            if (cuboid.overlaps(plot.cuboid)) return true;
        }

        return false;
    }

    /**
     * Checks if the name is already taken
     *
     * @param name desired name
     * @return     true if taken, false otherwise
     */
    static boolean kingdomNameTaken(String name) {
        for (Kingdom kingdom : TSKKingdom.instance.kingdoms.values()) {
            if (kingdom.name.equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    /**
     * Checks if the name is already taken
     *
     * @param name desired name
     * @return     true if taken, false otherwise
     */
    static boolean plotNameTaken(String name) {
        for (Plot plot : TSKKingdom.instance.plots.values()) {
            if (plot.name.equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    /**
     * Checks if the name is already taken
     *
     * @param name desired name
     * @return     true if taken, false otherwise
     */
    static boolean townNameTaken(String name) {
        for (Town town : TSKKingdom.instance.towns.values()) {
            if (town.name.equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    /**
     * Converts a monetary value to a money string ($XXX.XX)
     *
     * @param money money amount
     * @return      money string
     */
    static String moneyString(double money) {
        String moneyString = "$";
        DecimalFormat format = new DecimalFormat("#,###,###,##0.00");
        moneyString += format.format(money);
        return moneyString;
    }
}
