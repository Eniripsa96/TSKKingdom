package com.tsk.sucy;

import com.rit.sucy.ChatAPI;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Hashtable;

/**
 * Main class
 */
public class TSKKingdom extends JavaPlugin {

    static final String[] COMMANDS = new String[] {
            "info", "kingdom", "plot", "town", "tskkingdom" };

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
        instance = this;
    }

    /**
     * onEnable
     */
    @Override
    public void onEnable() {

        // Commands
        KCommandExecutor executor = new KCommandExecutor(this);
        for (String command : COMMANDS) getCommand(command).setExecutor(executor);

        // Listeners
        new KListener(this);

        // Config
        dataConfig = new KConfig(this);
        dataConfig.reloadConfig();
        try{
            for (String player : dataConfig.getConfig().getConfigurationSection("Players").getKeys(false)) {
                players.put(player, new KData(dataConfig.getConfig().getString("Players." + player)));
            }
            for (String kingdom : dataConfig.getConfig().getConfigurationSection("Kingdoms").getKeys(false)) {
                kingdoms.put(kingdom, new Kingdom(dataConfig.getConfig().getString("Kingdoms." + kingdom)));
            }
            for (String town : dataConfig.getConfig().getConfigurationSection("Towns").getKeys(false)) {
                towns.put(town, new Town(dataConfig.getConfig().getString("Towns." + town)));
            }
            for (String plot : dataConfig.getConfig().getConfigurationSection("Plots").getKeys(false)) {
                plots.put(plot, new Plot(dataConfig.getConfig().getString("Plots." + plot)));
            }
        }
        catch (NullPointerException e) {
            // No values for a section yet
        }
    }

    /**
     * onDisable
     */
    @Override
    public void onDisable() {
        for (Kingdom kingdom : kingdoms.values()) {
            dataConfig.getConfig().set("Kingdoms." + kingdom.name.toLowerCase(), kingdom.toString());
        }
        for (Town town : towns.values()) {
            dataConfig.getConfig().set("Towns." + town.name.toLowerCase(), town.toString());
        }
        for (Plot plot : plots.values()) {
            dataConfig.getConfig().set("Plots." + plot.name.toLowerCase(), plot.toString());
        }
        for (KData player : players.values()) {
            dataConfig.getConfig().set("Players." + player.playerName.toLowerCase(), player.toString());
        }
        dataConfig.saveConfig();
    }

    public void addTown(Town town) {
        towns.put(town.name.toLowerCase(), town);
    }

    public void addKingdom(Kingdom kingdom) {
        kingdoms.put(kingdom.name.toLowerCase(), kingdom);
    }

    public void addPlot(Plot plot) {
        plots.put(plot.name.toLowerCase(), plot);
    }

    public static void deleteKingdom(String kingdomName) {
        Kingdom kingdom = getKingdom(kingdomName);
        for (Town town : kingdom.towns()) deleteTown(town.name);
        for (KData player : TSKKingdom.instance.players.values()) {
            if (player.kingdom == null) continue;
            if (player.kingdom.equalsIgnoreCase(kingdomName)) {
                ChatAPI.clearPluginPrefixes("TSKKingdom");
                kingdom.removeResident(player.name());
            }
        }
        instance.kingdoms.remove(kingdom.name);
        instance.dataConfig.getConfig().set("Kingdoms." + kingdom.name, null);
    }

    public static void deleteTown(String townName) {
        for (KData player : TSKKingdom.instance.players.values()) {
            if (player.town == null) continue;
            if (player.town.equalsIgnoreCase(townName)) {
                ChatAPI.clearPluginPrefixes("TSKKingdom");
                for (Plot plot : player.plots())
                    deletePlot(plot.name);
                player.town().removeResident(player.name());
            }
        }
        instance.towns.remove(townName);
        instance.dataConfig.getConfig().set("Towns." + townName, null);
    }

    public static void deletePlot(String plotName) {
        instance.plots.remove(plotName.toLowerCase());
        instance.dataConfig.getConfig().set("Plots." + plotName, null);
    }

    /**
     * Retrieves the kingdom with the given name
     *
     * @param name name of the kingdom
     * @return     the kingdom or null if not found
     */
    public static Kingdom getKingdom(String name) {
        return instance.kingdoms.get(name.toLowerCase());
    }

    /**
     * Retrieves the town with the given name
     *
     * @param name name of the town
     * @return     the town or null if not found
     */
    public static Town getTown(String name) {
        return instance.towns.get(name.toLowerCase());
    }

    /**
     * Retrieves a town containing the given point
     *
     * @param location point in a town
     * @return         town at the location or null if not found
     */
    public static Town getTown(Location location) {
        for (Town town : instance.towns.values()) {
            if (town.cuboid.contains(location)) return town;
        }
        return null;
    }

    /**
     * Retrieves the plot with the given name
     *
     * @param name name of the plot
     * @return     the plot or null if not found
     */
    public static Plot getPlot(String name) {
        return instance.plots.get(name.toLowerCase());
    }

    /**
     * Retrieves a plot containing the given point
     *
     * @param location point in a plot
     * @return         plot at the location or null if not found
     */
    public static Plot getPlot(Location location) {
        for (Plot plot : instance.plots.values()) {
            if (plot.cuboid.contains(location)) return plot;
        }
        return null;
    }

    /**
     * Retrieves the data for the player with the given name
     *
     * @param name player name
     * @return     player data
     */
    public static KData getPlayer(String name) {
        name = name.toLowerCase();
        // If the player isn't loaded, load their data
        if (!instance.players.containsKey(name)) {
            if (instance.dataConfig.getConfig().contains("players." + name))
                instance.players.put(name, new KData(instance.dataConfig.getConfig().getString("players." + name.toLowerCase())));
            else if (instance.getServer().getPlayer(name) != null) {
                instance.players.put(name, new KData(name));
            }
            else return null;
        }

        return instance.players.get(name);
    }

    /**
     * Does nothing when run as .jar
     */
    public static void main(String[] args) {}
}
