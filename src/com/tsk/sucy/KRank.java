package com.tsk.sucy;

import org.bukkit.ChatColor;
import org.bukkit.Location;

/**
 * Handles rank data
 */
class KRank {

    static final String[] RANKS = new String[] { "Resident", "Architect", "Mayor", "King" };
    static final String[] PREFIXES = new String[] {
            ChatColor.GRAY + "Resident",
            ChatColor.AQUA + "Architect",
            ChatColor.GREEN + "Mayor",
            ChatColor.RED + "King"};

    /**
     * Gets the prefix of the designated rank
     *
     * @param rank rank name
     * @return     rank prefix
     */
    static String getPrefix(String rank) {
        for (int i = 0; i < RANKS.length; i++) {
            if (RANKS[i].equalsIgnoreCase(rank)) return PREFIXES[i];
        }
        return null;
    }

    /**
     * Checks if the given string is an applicable rank
     *
     * @param rank rank name
     * @return     true if a rank, false otherwise
     */
    static boolean isRank(String rank) {
        for (String r : RANKS) if (r.equalsIgnoreCase(rank)) return true;
        return false;
    }

    /**
     * Checks if the given string is a basic rank (not king or mayor)
     *
     * @param rank rank name
     * @return     true if a rank, false otherwise
     */
    static boolean isBasicRank(String rank) {
        for (String r : RANKS) {
            if (r.equalsIgnoreCase("Mayor")) return false;
            if (r.equalsIgnoreCase(rank)) return true;
        }
        return false;
    }

    /**
     * Checks if a player can build at the given location
     *
     * @param playerName name of the player trying to build
     * @param location   location of the block being modified
     * @return           true if can build, false otherwise
     */
    static boolean canBuild(String playerName, Location location) {
        KData data = TSKKingdom.getPlayer(playerName);
        if (data == null) return false;

        Town town = TSKKingdom.getTown(location);

        // Can build outside of towns
        if (town == null) return true;

        // People not in a kingdom cannot build in any kingdom
        // People not in any town cannot build in any towns
        if (data.kingdom == null || data.town == null) return false;

        // People cannot build in other kingdoms
        if (!data.kingdom.equalsIgnoreCase(town.kingdom)) return false;

        // A king can build anywhere inside his own kingdom
        if (data.rank.equalsIgnoreCase(RANKS[3])) return true;

        // Anyone else cannot build inside another town
        if (!data.town.equalsIgnoreCase(town.name())) return false;

        // Architects and mayors can build anywhere inside their own towns
        if (data.rank.equalsIgnoreCase(RANKS[1]) || data.rank.equalsIgnoreCase(RANKS[2])) return true;

        // Residents can build in their own plots
        for (Plot plot : data.plots()) if (plot.cuboid.contains(location)) return true;

        // Residents cannot build outside their own plots
        return false;
    }
}
