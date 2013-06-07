package com.tsk.sucy;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

/**
 * A Kingdom - Largest of the divisions of land
 */
public class Kingdom {

    String capital;
    String prefix;
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
    Kingdom(String kingdomName, String capitalName, String king, String prefix, KCuboid area) {
        capital = capitalName;
        name = kingdomName;
        this.king = king;
        this.prefix = prefix;
        funds = 0;

        new Town(capitalName, king, area);
    }

    /**
     * Constructor from toString data
     */
    Kingdom(String data) {
        String[] pieces = data.split(",");
        name = pieces[0];
        king = pieces[1];
        prefix = pieces[2];
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
    public String prefix() {
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
     * Adds a player to the kingdom
     *
     * @param player name of the player
     * @return       true if successful
     */
    public boolean addResident(String player) {
        KData data = TSKKingdom.getPlayer(player);
        if (data == null) return false;
        if (data.kingdom != null) return false;
        else data.kingdom = name;

        // TODO give the player the prefix for the Kingdom

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
        return name + "," + king + "," + prefix + "," + capital + "," + funds;
    }
}
