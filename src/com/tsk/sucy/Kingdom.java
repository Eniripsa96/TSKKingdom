package com.tsk.sucy;

import com.rit.sucy.CPrefix;
import com.rit.sucy.ChatAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;

/**
 * A Kingdom - Largest of the divisions of land
 */
public class Kingdom {

    String capital;
    ChatColor prefix;
    String name;
    String king;
    double funds;

    /**
     * Constructor for a new kingdom
     *
     * @param kingdomName name of the kingdom
     * @param capitalName name of the capital
     * @param king        name of the king
     * @param prefix      text for the prefix of residents
     * @param area        capital location
     */
    Kingdom(String kingdomName, String capitalName, String king, ChatColor prefix, KCuboid area) {

        this.capital = capitalName;
        name = kingdomName;
        this.king = king;
        TSKKingdom.getPlayer(king).setRank("King");
        TSKKingdom.getPlayer(king).kingdom = kingdomName;
        this.prefix = prefix;
        funds = 0;

        Town capital = new Town(capitalName, king, area);
        capital.kingdom = name;
        ChatAPI.getPlayerData(king).setPluginPrefix(new CPrefix("TSKKingdom", prefix + capitalName, prefix));
    }

    /**
     * Constructor from toString data
     */
    Kingdom(String data) {
        String[] pieces = data.split(",");
        name = pieces[0];
        king = pieces[1];
        prefix = ChatColor.valueOf(pieces[2]);
        capital = pieces[3];
        funds = Double.parseDouble(pieces[4]);
    }

    /**
     * @return kingdom's name
     */
    public String name() {
        return name;
    }

    /**
     * @return capital town name
     */
    public String capitalName() {
        return capital;
    }

    /**
     * @return the capital town
     */
    public Town capital() {
        return TSKKingdom.getTown(capital);
    }

    /**
     * @return kingdom prefix
     */
    public ChatColor prefixColor() {
        return prefix;
    }

    /**
     * @return kingdom's king's name
     */
    public String kingName() {
        return king;
    }

    /**
     * @return kingdom's king
     */
    public KData king() {
        return TSKKingdom.getPlayer(king);
    }

    /**
     * @return total value of the kingdom
     */
    public double value() {
        double total = 0;
        for (Town town : TSKKingdom.instance.towns.values()) {
            if (town.kingdom.equalsIgnoreCase(name)) total += town.value();
        }
        return total;
    }

    /**
     * Available funds for the kingdom
     *
     * @return available funds
     */
    public double funds() {
        return funds;
    }

    /**
     * @return number of towns in the kingdom
     */
    public int townCount() {
        int count = 0;
        for (Town town : TSKKingdom.instance.towns.values()) {
            if (town.kingdom.equalsIgnoreCase(name)) count++;
        }
        return count;
    }

    /**
     * @return all towns in the kingdom
     */
    public ArrayList<String> townNames() {
        ArrayList<String> list = new ArrayList<String>();
        for (Town town : TSKKingdom.instance.towns.values()) {
            if (town.kingdom.equalsIgnoreCase(name)) list.add(town.name());
        }
        return list;
    }

    /**
     * @return all towns in the kingdom
     */
    public ArrayList<Town> towns() {
        ArrayList<Town> list = new ArrayList<Town>();
        for (Town town : TSKKingdom.instance.towns.values()) {
            if (town.kingdom.equalsIgnoreCase(name)) list.add(town);
        }
        return list;
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
        if (data.kingdom != null) return false;

        data.kingdom = name;
        if (data.rank == null)
            data.setRank("Resident");
        ChatAPI.getPlayerData(data.name()).setPluginPrefix(new CPrefix("TSKKingdom", prefix + name(), prefix));

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

        data.kingdom = null;
        data.town = null;
        data.setRank(null);
        ChatAPI.getPlayerData(data.name()).clearPluginPrefix("TSKKingdom");

        return true;
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
     * @return kingdom data as a string
     */
    @Override
    public String toString() {
        return name + "," + king + "," + prefix.name() + "," + capital + "," + funds;
    }
}
