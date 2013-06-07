package com.tsk.sucy;

import org.bukkit.plugin.Plugin;

/**
 * A plot - belongs to a town and owned by a resident of the town
 */
public class Plot {

    KCuboid cuboid;
    String town;
    String owner;
    String name;
    double value;

    /**
     * Constructor for the mayor creating a new plot
     *
     * @param name name of the plot
     * @param town name of the town the plot is in
     * @param area location of the plot
     */
    Plot(String name, String town, KCuboid area) {
        this.name = name;
        owner = TSKKingdom.getTown(town).mayor;
        this.value = -1;
        cuboid = area;

        TSKKingdom.instance.plots.put(name, this);
    }

    /**
     * Constructor from toString data
     *
     * @param plugin plugin to get the world through
     * @param data   toString data
     */
    Plot(Plugin plugin, String data) {
        String[] pieces = data.split(",");
        name = pieces[0];
        town = pieces[1];
        owner = pieces[2];
        cuboid = new KCuboid(pieces[3], pieces[4], pieces[5], pieces[6], pieces[7]);
    }

    /**
     * @return plot owner
     */
    public KData owner() {
        return TSKKingdom.getPlayer(owner);
    }

    /**
     * @return plot owner's name
     */
    public String ownerName() {
        return owner;
    }

    /**
     * @return name of the plot
     */
    public String name() {
        return name;
    }

    /**
     * @return town the plot belongs to
     */
    public Town town() {
        return TSKKingdom.getTown(town);
    }

    /**
     * @return name of the town the plot belongs to
     */
    public String townName() {
        return town;
    }

    /**
     * @return plot data as a string
     */
    @Override
    public String toString() {
        return name + "," + town + "," + owner + "," + cuboid.toString();
    }
}
