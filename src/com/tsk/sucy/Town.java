package com.tsk.sucy;

import com.rit.sucy.CPrefix;
import com.rit.sucy.ChatAPI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

/**
 * A town - belongs to a kingdom and owned by a mayor
 */
public class Town {

    static final int START_SIZE = 50;
    static final int SIZE_PER_SLOT = 250;
    static final int OFFSET = START_SIZE * START_SIZE - SIZE_PER_SLOT * 5;

    ArrayList<String> applicants = new ArrayList<String>();
    KCuboid cuboid;
    String kingdom;
    String mayor;
    String name;
    double funds;

    /**
     * Constructor for claiming a town
     *
     * @param name  town name
     * @param mayor name of the person claiming the town
     * @param area  town location
     */
    Town (String name, String mayor, KCuboid area) {
        this.name = name;
        this.mayor = mayor;
        this.kingdom = TSKKingdom.getPlayer(mayor).kingdom;
        cuboid = area;
        KData owner = TSKKingdom.getPlayer(mayor);
        owner.town = name;
        addResident(mayor);
        if (!owner.rank.equalsIgnoreCase("Mayor") && !owner.rank.equalsIgnoreCase("King"))
            owner.setRank("Mayor");

        TSKKingdom.instance.towns.put(name.toLowerCase(), this);
    }

    /**
     * Constructor from toString data
     *
     * @param data   toString data
     */
    Town(String data) {
        String[] pieces = data.split(",");
        name = pieces[0];
        kingdom = pieces[1];
        mayor = pieces[2];
        funds = Double.parseDouble(pieces[3]);
        cuboid = new KCuboid(pieces[4], pieces[5], pieces[6], pieces[7], pieces[8]);
        for (int i = 9; i < pieces.length; i++) applicants.add(pieces[i]);
    }

    /**
     * @return the town's name
     */
    public String name() {
        return name;
    }

    /**
     * @return the town's mayor's name
     */
    public String mayorName() {
        return mayor;
    }

    /**
     * @return the town's mayor
     */
    public KData mayor() {
        return TSKKingdom.getPlayer(mayor);
    }

    /**
     * @return town's kingdom's name
     */
    public String kingdomName() {
        return kingdom;
    }

    /**
     * @return town's kingdom
     */
    public Kingdom kingdom() {
        return TSKKingdom.getKingdom(kingdom);
    }

    /**
     * @return size of the town in square meters
     */
    public int size() {
        return cuboid.area();
    }

    public int capacity() {
        return (size() - OFFSET) / SIZE_PER_SLOT;
    }

    /**
     * @return the value of the town
     */
    public double value() {
        return KFunction.townValue(cuboid.width, cuboid.depth);
    }

    /**
     * @return the names of all plots in this town
     */
    public ArrayList<String> plotNames() {
        ArrayList<String> list = new ArrayList<String>();
        for (Plot plot : TSKKingdom.instance.plots.values()) {
            if (plot.town.equalsIgnoreCase(name)) list.add(plot.name);
        }
        return list;
    }

    /**
     * @return all plots in this town
     */
    public ArrayList<Plot> plots() {
        ArrayList<Plot> list = new ArrayList<Plot>();
        for (Plot plot : TSKKingdom.instance.plots.values()) {
            if (plot.town.equalsIgnoreCase(name)) list.add(plot);
        }
        return list;
    }

    /**
     * @return the names of all players trying to get into the town
     */
    public ArrayList<String> applicants() {
        return applicants;
    }

    /**
     * Adds money to the kingdom's funds
     *
     * @param amount amount to deposit
     * @return       new balance
     */
    public double depositMoney(double amount) {
        return funds += amount;
    }

    /**
     * Removes money from the kingdom's funds
     *
     * @param amount amount to withdraw
     * @return       new balance
     */
    public double withdrawMoney(double amount) {
        return funds -= amount;
    }

    /**
     * Adds a player to the kingdom
     *
     * @param player name of the player
     * @return       true if successful, false otherwise
     */
    public boolean addResident(String player) {
        KData data = TSKKingdom.getPlayer(player);
        if (data == null) return false;
        if (data.town != null) return false;

        data.town = name;
        ChatAPI.getPlayerData(data.name()).setPluginPrefix(new CPrefix("TSKKingdom", data.kingdom().prefix + name(), data.kingdom().prefix));

        return true;
    }

    /**
     * Removes a resident from the kingdom
     * @param player player to remove
     * @return       true if successful, false otherwise
     */
    public boolean removeResident(String player) {
        KData data = TSKKingdom.getPlayer(player);
        if (data == null) return false;
        if (data.kingdom == null) return false;
        if (!data.kingdom.equalsIgnoreCase(name)) return false;

        for (Plot plot : data.plots())
            plot.owner = mayor;
        data.town = null;
        if (!data.rank.equalsIgnoreCase("Resident"))
            data.setRank("Resident");
        ChatAPI.getPlayerData(player).setPluginPrefix(new CPrefix("TSKKingdom", data.kingdom().prefix + data.kingdom().name, data.kingdom().prefix));

        return true;
    }

    /**
     * @return town data as a string
     */
    @Override
    public String toString() {
        String data = name + "," + kingdom + "," + mayor + "," + funds + "," + cuboid.toString();
        for (String applicant : applicants) data += "," + applicant;
        return data;
    }
}
