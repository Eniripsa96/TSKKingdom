package com.tsk.sucy;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Hashtable;

/**
 * Main class
 */
public class TSKKingdom extends JavaPlugin {

    static final String[] COMMANDS = new String[] {
            "claim", "kingdom", "town", "plot" };

    static TSKKingdom instance;

    Hashtable<String, Kingdom> kingdoms = new Hashtable<String, Kingdom>();
    Hashtable<String, Town> towns = new Hashtable<String, Town>();
    Hashtable<String, Plot> plots = new Hashtable<String, Plot>();
    Hashtable<String, KData> players = new Hashtable<String, KData>();

    KConfig dataConfig;

    /**
     * Constructor
     */
    public TSKKingdom() {

        // Setup
        instance = this;

        // Commands
        KCommandExecutor executor = new KCommandExecutor(this);
        for (String command : COMMANDS) getCommand(command).setExecutor(executor);

        // Listeners
        new KListener(this);
    }

    /**
     * onEnable
     */
    @Override
    public void onEnable() {
    }

    /**
     * onDisable
     */
    @Override
    public void onDisable() {

    }

    /**
     * Retrieves the kingdom with the given name
     *
     * @param name name of the kingdom
     * @return     the kingdom or null if not found
     */
    public static Kingdom getKingdom(String name) {
        return instance.kingdoms.get(name);
    }

    /**
     * Retrieves the town with the given name
     *
     * @param name name of the town
     * @return     the town or null if not found
     */
    public static Town getTown(String name) {
        return instance.towns.get(name);
    }

    /**
     * Retrieves the plot with the given name
     *
     * @param name name of the plot
     * @return     the plot or null if not found
     */
    public static Plot getPlot(String name) {
        return instance.plots.get(name);
    }

    /**
     * Retrieves the data for the player with the given name
     *
     * @param name player name
     * @return     player data
     */
    public static KData getPlayer(String name) {

        // If the player isn't loaded, load their data
        if (!instance.players.containsKey(name)) {
            if (instance.dataConfig.getConfig().contains("players." + name))
                instance.players.put(name, new KData(instance.dataConfig.getConfig().getString("players." + name)));
            else if (instance.getServer().getPlayer(name) != null) {
                instance.players.put(name, new KData(name, "Resident"));
            }
            else return null;
        }

        return instance.players.get(name);
    }
}
