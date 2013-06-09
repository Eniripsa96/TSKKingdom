package com.tsk.sucy;

import com.rit.sucy.CPrefix;
import com.rit.sucy.ChatAPI;
import org.bukkit.Location;

import java.util.ArrayList;

/**
 * Player data and helper methods
 */
public class KData {

    String playerName;
    String kingdom;
    String town;
    String rank;

    double money;

    String currentPlot;
    String currentTown;

    Location p1;
    Location p2;

    /**
     * Constructor
     *
     * @param data name of player
     */
    KData(String data) {
        if (data.contains(",")) {
            String[] pieces = data.split(",");
            playerName = pieces[0];
            money = Double.parseDouble(pieces[1]);
            if (pieces.length > 2) rank = pieces[2];
            if (pieces.length > 3) kingdom = pieces[3];
            if (pieces.length > 4) town = pieces[4];
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
     * @return player's money
     */
    public double money() {
        return money;
    }

    /**
     * @return player's money as a string ($XXX.XX)
     */
    public String moneyString() {
        return KFunction.moneyString(money);
    }

    /**
     * Gives money to the player
     *
     * @param amount amount to give
     * @return       remaining balance
     */
    public double giveMoney(double amount) {
        return money += amount;
    }

    /**
     * Takes money from the player
     *
     * @param amount amount to take
     * @return       remaining balance
     */
    public double takeMoney(double amount) {
        return money -= amount;
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
     * Sets the rank of the player
     *
     * @param rank new rank
     */
    void setRank(String rank) {

        if (this.rank != null && (this.rank.equalsIgnoreCase("Mayor") || this.rank.equalsIgnoreCase("King")))
            TSKKingdom.instance.getServer().getPlayer(playerName).getInventory().remove(KCommandExecutor.plotTool);
        if (rank != null && (rank.equalsIgnoreCase("Mayor") || rank.equalsIgnoreCase("King")))
            TSKKingdom.instance.getServer().getPlayer(playerName).getInventory().addItem(KCommandExecutor.plotTool);

        if (this.rank != null)
            ChatAPI.getPlayerData(playerName).removePrefix("TSKKingdom", KRank.getPrefix(this.rank));
        if (rank != null)
            ChatAPI.getPlayerData(playerName).unlockPrefix(new CPrefix("TSKKingdom", KRank.getPrefix(rank)), false);
        this.rank = rank;
    }

    /**
     * @return player data as a string
     */
    @Override
    public String toString() {
        String data = playerName + "," + money;
        if (rank != null) data += "," + rank;
        if (kingdom != null) data += "," + kingdom;
        if (town != null) data += "," + town;
        return data;
    }
}
