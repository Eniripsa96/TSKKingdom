package com.tsk.sucy;

import org.bukkit.plugin.Plugin;

/**
 * A town - belongs to a kingdom and owned by a mayor
 */
public class Town {

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
     * @param area  the location for the town
     */
    Town (String name, String mayor, KCuboid area) {
        this.name = name;
        this.mayor = mayor;
        this.kingdom = TSKKingdom.getPlayer(mayor).kingdom;
        cuboid = area;

        TSKKingdom.instance.towns.put(name, this);
    }

    /**
     * Constructor from toString data
     *
     * @param plugin plugin to get the world through
     * @param data   toString data
     */
    Town(Plugin plugin, String data) {
        String[] pieces = data.split(",");
        name = pieces[0];
        kingdom = pieces[1];
        mayor = pieces[2];
        funds = Double.parseDouble(pieces[3]);
        cuboid = new KCuboid(pieces[4], pieces[5], pieces[6], pieces[7], pieces[8]);
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

    /**
     * @return the value of the town
     */
    public double value() {
        return KFunction.townValue(cuboid.width, cuboid.depth);
    }

    /**
     * @return town data as a string
     */
    @Override
    public String toString() {
        return name + "," + kingdom + "," + mayor + "," + funds + "," + cuboid.toString();
    }
}
