package com.tsk.sucy;

import java.util.ArrayList;

/**
 * Player data and helper methods
 */
public class KData {

    /**
     * Name of the player
     */
    String playerName;

    /**
     * The kingdom the player is aligned to
     */
    String kingdom;

    /**
     * The town the player is residing in
     */
    String town;

    /**
     * Their rank
     */
    String rank;

    /**
     * Constructor
     *
     * @param name player name
     * @param rank kingdom rank
     */
    public KData(String name, String rank) {
        this.playerName = name;
        this.rank = rank;
    }

    /**
     * Constructor from toString data
     *
     * @param data toString data
     */
    KData(String data) {
        if (data.contains(" ")) {
            String[] pieces = data.split(" ");
            playerName = pieces[0];
            if (pieces.length > 1) kingdom = pieces[1];
            if (pieces.length > 2) town = pieces[2];
        }
        else {
            playerName = data;
        }
    }

    /**
     * @return player's name
     */
    public String name() {
        return playerName;
    }

    /**
     * @return player's kingdom's name
     */
    public String kingdomName() {
        return kingdom;
    }

    /**
     * @return player's kingdom
     */
    public Kingdom kingdom() {
        return TSKKingdom.getKingdom(kingdom);
    }

    /**
     * @return player's town's name
     */
    public String townName() {
        return town;
    }

    /**
     * @return player's town
     */
    public Town town() {
        return TSKKingdom.getTown(town);
    }

    /**
     * @return the names of all plots owned by the player
     */
    public ArrayList<String> plotNames() {
        ArrayList<String> list = new ArrayList<String>();
        for (Plot plot : TSKKingdom.instance.plots.values()) {
            if (plot.owner.equalsIgnoreCase(playerName)) list.add(plot.name);
        }
        return list;
    }

    /**
     * @return all plots owned by the player
     */
    public ArrayList<Plot> plots() {
        ArrayList<Plot> list = new ArrayList<Plot>();
        for (Plot plot : TSKKingdom.instance.plots.values()) {
            if (plot.owner.equalsIgnoreCase(playerName)) list.add(plot);
        }
        return list;
    }

    /**
     * @return player data as a string
     */
    @Override
    public String toString() {
        String data = playerName;
        if (kingdom != null) data += "," + kingdom;
        if (town != null) data += "," + town;
        return data;
    }
}
