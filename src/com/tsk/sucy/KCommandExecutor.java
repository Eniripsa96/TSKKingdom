package com.tsk.sucy;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

/**
 * Performs commands for the plugin
 */
public class KCommandExecutor implements CommandExecutor {

    static final String TOOL_NAME = ChatColor.RED + "P" + ChatColor.DARK_GREEN + "lot "
            + ChatColor.RED + "T" + ChatColor.DARK_GREEN + "ool";
    static ItemStack plotTool;

    TSKKingdom plugin;

    /**
     * General constructor
     *
     * @param plugin TSKKingdom reference
     */
    public KCommandExecutor(TSKKingdom plugin) {
        this.plugin = plugin;

        plotTool = new ItemStack(Material.STICK);
        ItemMeta meta = plotTool.getItemMeta();
        meta.setDisplayName(TOOL_NAME);
        plotTool.setItemMeta(meta);
    }

    /**
     * Handles incoming commands
     *
     * @param sender  person who sent the command
     * @param command command that was sent
     * @param label   command label
     * @param args    command arguments
     * @return        true
     */
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName();

        if (commandName.equalsIgnoreCase("kingdom")) {
            if (args.length == 0) kingdomCommandUses(sender);
            else if (args[0].equalsIgnoreCase("donate")) kingdomDonate(sender, args);
            else if (args[0].equalsIgnoreCase("info")) kingdomInfo(sender, args);
            else if (args[0].equalsIgnoreCase("join")) kingdomJoin(sender, args);
            else if (args[0].equalsIgnoreCase("leave")) kingdomLeave(sender, args);
            else if (args[0].equalsIgnoreCase("list")) kingdomList(sender, args);
            else if (args[0].equalsIgnoreCase("resign")) kingdomResign(sender, args);
            else if (args[0].equalsIgnoreCase("withdraw")) kingdomWithdraw(sender, args);
            else kingdomCommandUses(sender);
        }
        else if (commandName.equalsIgnoreCase("plot")) {
            if (args.length == 0) plotCommandUses(sender);
            else if (args[0].equalsIgnoreCase("buy")) plotBuy(sender, args);
            else if (args[0].equalsIgnoreCase("create")) plotCreate(sender, args);
            else if (args[0].equalsIgnoreCase("delete")) plotDelete(sender, args);
            else if (args[0].equalsIgnoreCase("info")) plotInfo(sender, args);
            else if (args[0].equalsIgnoreCase("listings")) plotListings(sender, args);
            else if (args[0].equalsIgnoreCase("sell")) plotSell(sender, args);
            else plotCommandUses(sender);
        }
        else if (commandName.equalsIgnoreCase("town")) {
            if (args.length == 0) townCommandUses(sender);
            else if (args[0].equalsIgnoreCase("accept")) townAccept(sender, args);
            else if (args[0].equalsIgnoreCase("applicants")) townApplicants(sender, args);
            else if (args[0].equalsIgnoreCase("create")) townCreate(sender, args);
            else if (args[0].equalsIgnoreCase("donate")) townDonate(sender, args);
            else if (args[0].equalsIgnoreCase("expand")) townExpand(sender, args);
            else if (args[0].equalsIgnoreCase("info")) townInfo(sender, args);
            else if (args[0].equalsIgnoreCase("join")) townJoin(sender, args);
            else if (args[0].equalsIgnoreCase("leave")) townLeave(sender, args);
            else if (args[0].equalsIgnoreCase("list")) townList(sender, args);
            else if (args[0].equalsIgnoreCase("promote")) townPromote(sender, args);
            else if (args[0].equalsIgnoreCase("rank")) townRank(sender, args);
            else if (args[0].equalsIgnoreCase("reject")) townReject(sender, args);
            else if (args[0].equalsIgnoreCase("resign"))  townReject(sender, args);
            else if (args[0].equalsIgnoreCase("withdraw")) townWithdraw(sender, args);
            else townCommandUses(sender);
        }
        else if (commandName.equalsIgnoreCase("tskKingdom")) {
            if (args.length == 0) tskCommandUses(sender);
            else if (args[0].equalsIgnoreCase("appoint")) tskAppoint(sender, args);
            else if (args[0].equalsIgnoreCase("create")) tskCreate(sender, args);
            else if (args[0].equalsIgnoreCase("delete")) tskDelete(sender, args);
            else if (args[0].equalsIgnoreCase("giveMoney")) tskMoney(sender, args);
            else tskCommandUses(sender);
        }
        else if (commandName.equalsIgnoreCase("info")) info(sender, args);

        return true;
    }

    /**
     * Displays uses of the info command
     *
     * @param sender sender of the command
     */
    void infoUses(CommandSender sender) {
        sender.sendMessage(ChatColor.AQUA + "Info Command - Uses");
        if (sender instanceof Player) sender.sendMessage("   /info [playerName]");
        else sender.sendMessage("   /info <playerName>");
    }

    /**
     * Displays the info of a player
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void info(CommandSender sender, String[] args) {
        if (args.length == 0 && sender instanceof Player) {
            KData data = TSKKingdom.getPlayer(sender.getName());
            infoDisplayPlayer(sender, data);
        }
        else if (args.length == 1) {
            KData data = TSKKingdom.getPlayer(args[0]);
            if (data == null)
                sender.sendMessage("No player exists with the name " + args[1]);
            else infoDisplayPlayer(sender, data);
        }
        else infoUses(sender);
    }

    /**
     * Displays the info of a player
     *
     * @param sender sender of the command
     * @param player player data
     */
    void infoDisplayPlayer(CommandSender sender, KData player) {
        sender.sendMessage("Player info for: " + ChatColor.AQUA + player.name());
        sender.sendMessage(ChatColor.GRAY + "   Money: " + ChatColor.GREEN + player.moneyString());
        sender.sendMessage(ChatColor.GRAY + "   Kingdom: " + ChatColor.GREEN + (player.kingdom == null ? "None" : player.kingdom));
        sender.sendMessage(ChatColor.GRAY + "   Town: " + ChatColor.GREEN + (player.town == null ? "None" : player.town));
        sender.sendMessage(ChatColor.GRAY + "   Rank: " + ChatColor.GREEN + (player.rank == null ? "Unranked" : player.rank));
    }

    /**
     * Displays the uses of the kingdom command to the sender
     *
     * @param sender sender of the command
     */
    void kingdomCommandUses(CommandSender sender) {
        sender.sendMessage(ChatColor.AQUA + "Kingdom Command - uses");
        if (sender instanceof Player) {
            sender.sendMessage("   /kingdom donate <amount>");
            sender.sendMessage("   /kingdom info [kingdomName]");
            sender.sendMessage("   /kingdom join <kingdomName>");
            sender.sendMessage("   /kingdom leave");
            sender.sendMessage("   /kingdom list");
            sender.sendMessage("   /kingdom resign <playerName>");
            sender.sendMessage("   /kingdom withdraw <amount>");
        }
        else {
            sender.sendMessage("   /kingdom info <kingdomName>");
            sender.sendMessage("   /kingdom list");
        }

    }

    /**
     * Displays the information for a kingdom to the sender
     *
     * @param sender  sender of the command
     * @param kingdom kingdom to display the info of
     */
    void kingdomDisplayInfo(CommandSender sender, Kingdom kingdom) {
        sender.sendMessage("Kingdom info for: " + ChatColor.AQUA + kingdom.name);
        sender.sendMessage(ChatColor.GRAY + "      King : " + ChatColor.GREEN + kingdom.king);
        sender.sendMessage(ChatColor.GRAY + "   Capital : " + ChatColor.GREEN + kingdom.capital);
        sender.sendMessage(ChatColor.GRAY + "    Towns : " + ChatColor.GREEN + kingdom.townCount());
        sender.sendMessage(ChatColor.GRAY + "    Funds : " + ChatColor.GREEN + KFunction.moneyString(kingdom.funds));
    }

    /**
     * Donates money to the kingdom
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void kingdomDonate(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 2) {
            try{
                double amount = Double.parseDouble(args[1]);
                KData data = TSKKingdom.getPlayer(sender.getName());
                if (data.kingdom == null)
                    sender.sendMessage("You are not part of a kingdom!");
                else if (data.money < amount)
                    sender.sendMessage("You do not have that much!");
                else {
                    data.kingdom().depositMoney(amount);
                    data.takeMoney(amount);
                    sender.sendMessage("You donated " + KFunction.moneyString(amount));
                }
            }
            catch (Exception e) {
                sender.sendMessage(args[1] + " is an invalid amount of money");
            }
        }
        else kingdomCommandUses(sender);
    }

    /**
     * Displays the info of a kingdom
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void kingdomInfo(CommandSender sender, String[] args) {

        // Displays info for a player's current kingdom
        if (args.length == 1) {
            if (sender instanceof Player) {
                KData data = TSKKingdom.getPlayer(sender.getName());
                if (data.kingdom != null) {
                    kingdomDisplayInfo(sender, data.kingdom());
                }
                else sender.sendMessage("You are not in a kingdom!");
            }
            else kingdomCommandUses(sender);
        }

        // Displays info for the designated kingdom
        else if (args.length == 2) {
            Kingdom kingdom = TSKKingdom.getKingdom(args[1]);
            if (kingdom == null) sender.sendMessage("There is no kingdom with the name " + args[1]);
            else kingdomDisplayInfo(sender, kingdom);
        }

        // Invalid number of arguments
        else kingdomCommandUses(sender);
    }

    /**
     * Adds a player into a kingdom
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void kingdomJoin(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 2) {
            Kingdom kingdom = TSKKingdom.getKingdom(args[1]);
            KData data = TSKKingdom.getPlayer(sender.getName());
            if (kingdom == null)
                sender.sendMessage("There is no kingdom with the name " + args[1]);
            else if (data.kingdom != null)
                sender.sendMessage("You are already in a kingdom!");
            else {
                if (data.kingdom != null) data.kingdom().removeResident(sender.getName());
                kingdom.addResident(sender.getName());
                sender.sendMessage("You have successfully joined " + args[1]);
            }
        }
        else kingdomCommandUses(sender);
    }

    /**
     * Removes a player from a kingdom
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void kingdomLeave(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 1) {
            KData data = TSKKingdom.getPlayer(sender.getName());
            if (data.kingdom == null)
                sender.sendMessage("You aren't part of a kingdom!");
            else if (data.rank.equalsIgnoreCase("King"))
                sender.sendMessage("You can't leave as the king!");
            else if (data.rank.equalsIgnoreCase("Mayor"))
                sender.sendMessage("You can't leave as a mayor!");
            else {
                for (Plot plot : data.plots())
                    plot.owner = data.town().mayor;
                data.kingdom().removeResident(data.name());
                sender.sendMessage("You have left your kingdom!");
            }
        }
        else kingdomCommandUses(sender);
    }

    /**
     * Displays a list of all kingdoms
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void kingdomList(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sender.sendMessage("Kingdoms:");
            for (String kingdom : TSKKingdom.instance.kingdoms.keySet()) {
                sender.sendMessage("   - " + kingdom);
            }
        }
        else kingdomCommandUses(sender);
    }

    /**
     * Resigns the king from his throne
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void kingdomResign(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 2) {
            KData data = TSKKingdom.getPlayer(sender.getName());
            KData target = TSKKingdom.getPlayer(args[1]);
            if (data.rank == null)
                sender.sendMessage("You are not a king!");
            else if (!data.rank.equalsIgnoreCase("King"))
                sender.sendMessage("You are not a king!");
            else if (target == null)
                sender.sendMessage("No player exists with the name " + args[1]);
            else if (target.town == null)
                sender.sendMessage("You must appoint someone in the capital!");
            else if (!target.town.equalsIgnoreCase(data.town))
                sender.sendMessage("You must appoint someone in the capital!");
            else {
                data.setRank("Resident");
                data.town().mayor = target.name();
                target.setRank("King");
                sender.sendMessage("You have resigned as king!");
                Player player = plugin.getServer().getPlayer(target.name());
                if (player != null) player.sendMessage("You have been made the king of " + target.kingdom + "!");
            }
        }
        else kingdomCommandUses(sender);
    }

    /**
     * Withdraws money from a kingdom
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void kingdomWithdraw(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 2) {
            try{
                double amount = Double.parseDouble(args[1]);
                KData data = TSKKingdom.getPlayer(sender.getName());
                if (data.rank == null)
                    sender.sendMessage("You are not a king!");
                else if (!data.rank.equalsIgnoreCase("King"))
                    sender.sendMessage("You are not a king!");
                else if (data.kingdom().funds < amount)
                    sender.sendMessage("The kingdom does not have that much funds!");
                else {
                    data.kingdom().withdrawMoney(amount);
                    data.giveMoney(amount);
                    sender.sendMessage("You withdrew " + KFunction.moneyString(amount));
                }
            }
            catch (Exception e) {
                sender.sendMessage(args[1] + " is an invalid amount of money");
            }
        }
        else kingdomCommandUses(sender);
    }

    /**
     * Buys the plot the player is standing in
     *
     * @param sender sender of the command
     * @param args   command arugments
     */
    void plotBuy(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length < 3) {
            KData data = TSKKingdom.getPlayer(sender.getName());
            if (args.length == 1) {
                Plot plot = TSKKingdom.getPlot(((Player) sender).getLocation());
                if (plot == null)
                    sender.sendMessage("You are not in a plot!");
                else if (plot.owner.equalsIgnoreCase(sender.getName()))
                    sender.sendMessage("You cannot buy your own plot!");
                else if (data.town == null)
                    sender.sendMessage("You are not part of this town!");
                else if (!plot.town.equalsIgnoreCase(data.town))
                    sender.sendMessage("You are not part of this town!");
                else if (plot.forSale()) {
                    sender.sendMessage("You can buy this plot for $" + plot.value);
                    sender.sendMessage("   to do so, use /plot buy confirm");
                }
                else sender.sendMessage("This plot isn't for sale!");
            }
            else if (args.length == 2 && args[1].equalsIgnoreCase("confirm")) {
                Plot plot = TSKKingdom.getPlot(((Player) sender).getLocation());
                if (plot == null)
                    sender.sendMessage("You are not in a plot!");
                else if (plot.owner.equalsIgnoreCase(sender.getName()))
                    sender.sendMessage("You cannot buy your own plot!");
                else if (data.town == null)
                    sender.sendMessage("You are not part of this town!");
                else if (!plot.town.equalsIgnoreCase(data.town))
                    sender.sendMessage("You are not part of this town!");
                else if (data.money < plot.value)
                    sender.sendMessage("You do not have enough money!");
                else if (plot.forSale()) {
                    data.takeMoney(plot.value);
                    plot.owner().giveMoney(plot.value);
                    Player player = plugin.getServer().getPlayer(plot.owner);
                    if (player != null) player.sendMessage("Your plot was bought for " + KFunction.moneyString(plot.value));
                    sender.sendMessage("You bought the plot for " + KFunction.moneyString(plot.value));
                    plot.owner = sender.getName();
                    plot.value = -1;
                }
                else sender.sendMessage("This plot isn't for sale!");
            }
            else plotCommandUses(sender);
        }
        else plotCommandUses(sender);
    }

    /**
     * Displays usage for the plot command
     *
     * @param sender sender of the command
     */
    void plotCommandUses(CommandSender sender) {
        sender.sendMessage(ChatColor.AQUA + "Plot Command - Uses");
        if (sender instanceof Player) {
            sender.sendMessage("   /plot buy");
            sender.sendMessage("   /plot create <plotName>");
            sender.sendMessage("   /plot delete");
            sender.sendMessage("   /plot info [plotName]");
            sender.sendMessage("   /plot listings [townName]");
            sender.sendMessage("   /plot sell <amount>");
            sender.sendMessage("   /plot sell cancel");
        }
        else {
            sender.sendMessage("   /plot info <plotName>");
            sender.sendMessage("   /plot listings <townName>");
        }
    }

    /**
     * Creates a new plot
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void plotCreate(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 2) {
            KData data = TSKKingdom.getPlayer(sender.getName());

            if (data.p1 == null || data.p2 == null) {
                sender.sendMessage("You have not designated an area yet!");
                return;
            }

            KCuboid cuboid = new KCuboid(data.p1, data.p2);

            if (KFunction.conflictsPlot(cuboid))
                sender.sendMessage("You cannot make a plot there, it conflicts with another!");
            else if (KFunction.plotNameTaken(args[1]))
                sender.sendMessage("The name " + args[1] + " is already being used!");
            else if (data.town == null)
                sender.sendMessage("What?");
            else if (!data.town().cuboid.contains(cuboid))
                sender.sendMessage("You must make a plot inside your town!");
            else {
                new Plot(args[1], data.town, cuboid);
                sender.sendMessage("You have created the plot " + args[1]);
            }
        }
        else plotCommandUses(sender);
    }

    /**
     * Deletes the players plot that they are in
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void plotDelete(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 1) {
            Plot plot = TSKKingdom.getPlot(((Player) sender).getLocation());
            KData data = TSKKingdom.getPlayer(sender.getName());
            if (plot == null)
                sender.sendMessage("You are not in a plot!");
            else if (((data.rank.equalsIgnoreCase("Mayor") || data.rank.equalsIgnoreCase("King"))
                    && data.town.equalsIgnoreCase(plot.town)) || plot.owner.equalsIgnoreCase(sender.getName())) {
                TSKKingdom.instance.plots.remove(plot.name);
                sender.sendMessage("The plot has been deleted!");
            }
            else sender.sendMessage("You do not own this plot!");
        }
        else plotCommandUses(sender);
    }

    /**
     * Displays information for a plot
     *
     * @param sender sender of the command
     */
    void plotDisplayInfo(CommandSender sender, Plot plot) {
        sender.sendMessage("Plot info for: " + ChatColor.AQUA + plot.name);
        sender.sendMessage(ChatColor.GRAY + "      Owner : " + ChatColor.GREEN + plot.owner);
        sender.sendMessage(ChatColor.GRAY + "       Town : " + ChatColor.GREEN + plot.town);
        sender.sendMessage(ChatColor.GRAY + "        Size : " + ChatColor.GREEN + plot.size());
        sender.sendMessage(ChatColor.GRAY + "       Price : " + ChatColor.GREEN + (plot.forSale() ? "$" + plot.value : "Not for sale"));
        sender.sendMessage(ChatColor.GRAY + "   Location : " + ChatColor.GREEN + plot.cuboid.pairString());
    }

    /**
     * Displays information for a plot
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void plotInfo(CommandSender sender, String[] args) {
        if (args.length == 1 && sender instanceof Player) {
            Plot plot = TSKKingdom.getPlot(((Player) sender).getLocation());
            if (plot == null)
                sender.sendMessage("You are not in a plot!");
            else plotDisplayInfo(sender, plot);
        }
        else if (args.length == 2) {
            Plot plot = TSKKingdom.getPlot(args[1]);
            if (plot == null)
                sender.sendMessage("No plot exists with the name " + args[1]);
            else plotDisplayInfo(sender, plot);
        }
        else plotCommandUses(sender);
    }

    /**
     * Displays the plots available for sale
     * @param sender sender of the command
     * @param args   command arguments
     */
    void plotListings(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 1) {
            KData data = TSKKingdom.getPlayer(sender.getName());
            if (data.town == null)
                sender.sendMessage("You aren't part of a town!");
            else {
                sender.sendMessage("Plots for sale:");
                for (Plot plot : data.town().plots()) {
                    if (plot.forSale()) sender.sendMessage("   " + plot.name + ": $" + plot.value + " for " + plot.size() + " sq. blocks");
                }
            }
        }
        else if (args.length == 2) {
            Town town = TSKKingdom.getTown(args[1]);
            if (town == null)
                sender.sendMessage("No town exists with the name " + args[1]);
            else {
                sender.sendMessage("Plots for sale:");
                for (Plot plot : town.plots()) {
                    if (plot.forSale()) sender.sendMessage("   " + plot.name + ": $" + plot.value + " for " + plot.size() + " sq. blocks");
                }
            }
        }
        else plotCommandUses(sender);
    }

    /**
     * Puts a plot up for sale or cancels a sale
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void plotSell(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 2) {
            Plot plot = TSKKingdom.getPlot(((Player) sender).getLocation());
            if (plot == null)
                sender.sendMessage("You are not in a plot!");
            else if (!plot.owner.equalsIgnoreCase(sender.getName()))
                sender.sendMessage("You do not own this plot!");
            else if (args[1].equalsIgnoreCase("cancel")) {
                if (plot.forSale()) {
                    plot.value = -1;
                    sender.sendMessage("Your plot is no longer for sale!");
                }
                else sender.sendMessage("Your plot is not up for sale!");
            }
            else {
                try {
                    double price = Double.parseDouble(args[1]);
                    plot.value = price;
                    sender.sendMessage("Your plot is now listed at $" + price);
                }
                catch (Exception e) {
                    sender.sendMessage(args[1] + " is not a valid price!");
                }
            }
        }
        else plotCommandUses(sender);
    }

    /**
     * Accepts a player into the town
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void townAccept(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 2) {
            KData data = TSKKingdom.getPlayer(sender.getName());
            KData target = TSKKingdom.getPlayer(args[1]);
            if (data.town == null)
                sender.sendMessage("You are not part of a town!");
            else if (!data.rank.equalsIgnoreCase("Mayor") && !data.rank.equalsIgnoreCase("King"))
                sender.sendMessage("You do not own a town!");
            else if (target == null)
                sender.sendMessage("No player exists with the name " + args[1]);
            else if (target.kingdom == null) {
                sender.sendMessage("That player doesn't belong to your kingdom!");
            }
            else if (!target.kingdom.equalsIgnoreCase(data.kingdom))
                sender.sendMessage("That player doesn't belong to your kingdom!");
            else if (target.town != null)
                sender.sendMessage("That player already belongs to a town!");
            else if (data.town().applicants().contains(target.name())) {
                data.town().addResident(target.name());
                sender.sendMessage(args[1] + " has joined your town!");
            }
            else
                sender.sendMessage("That player has not applied yet!");
        }
        else townCommandUses(sender);
    }

    /**
     * Displays the town applicants
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void townApplicants(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 1) {
            KData data = TSKKingdom.getPlayer(sender.getName());
            if (data.town == null)
                sender.sendMessage("You are not part of a town!");
            else {
                String message = "Applicants: ";
                for (String applicant : data.town().applicants) message += applicant + ", ";
                if (message.charAt(message.length() - 2) == ',') message = message.substring(0, message.length() - 2);
                sender.sendMessage(message);
            }
        }
        else if (args.length == 2) {
            Town town = TSKKingdom.getTown(args[1]);
            if (town == null)
                sender.sendMessage("No town exists with the name " + args[1]);
            else {
                String message = "Applicants: ";
                for (String applicant : town.applicants) message += applicant + ", ";
                if (message.charAt(message.length() - 2) == ',') message = message.substring(0, message.length() - 2);
                sender.sendMessage(message);
            }
        }
        else townCommandUses(sender);
    }

    /**
     * Displays uses for the town command
     *
     * @param sender sender of the command
     */
    void townCommandUses(CommandSender sender) {
        sender.sendMessage(ChatColor.AQUA + "Town Command - Uses");
        if (sender instanceof Player) {
            sender.sendMessage("   /town accept <playerName>");
            sender.sendMessage("   /town applicants [townName]");
            sender.sendMessage("   /town create <townName>");
            sender.sendMessage("   /town donate <amount>");
            sender.sendMessage("   /town info [townName]");
            sender.sendMessage("   /town join <townName>");
            sender.sendMessage("   /town leave");
            sender.sendMessage("   /town list [kingdomName]");
            sender.sendMessage("   /town promote <playerName> <rank>");
            sender.sendMessage("   /town rank [playerName]");
            sender.sendMessage("   /town reject <all|playerName>");
            sender.sendMessage("   /town resign <playerName>");
            sender.sendMessage("   /town withdraw <amount>");
        }
        else {
            sender.sendMessage("/town applicants <townName>");
            sender.sendMessage("/town info <townName>");
            sender.sendMessage("/town list <kingdomName>");
        }
    }

    /**
     * Creates a town
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void townCreate(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 2) {
            KData data = TSKKingdom.getPlayer(sender.getName());
            double cost = KFunction.townCost(Town.START_SIZE);
            KCuboid cuboid = new KCuboid(((Player) sender).getLocation(), Town.START_SIZE, Town.START_SIZE);
            if (data.kingdom == null)
                sender.sendMessage("You are not part of a kingdom!");
            else if (data.town != null)
                sender.sendMessage("You are already a part of a town!");
            else if (KFunction.conflictsTown(cuboid))
                sender.sendMessage("You are too close to another town!");
            else if (KFunction.townNameTaken(args[1]))
                sender.sendMessage("That name is already taken!");
            else if (data.money < cost)
                sender.sendMessage("You do not have enough money!");
            else {
                new Town(args[1], sender.getName(), cuboid);
                data.takeMoney(cost);
                sender.sendMessage("You created the town " + args[1] + " for " + KFunction.moneyString(cost));
            }
        }
        else townCommandUses(sender);
    }

    /**
     * Displays info for a town
     *
     * @param sender sender of the command
     * @param town   town to show info of
     */
    void townDisplayInfo(CommandSender sender, Town town) {
        sender.sendMessage("Town info for: " + ChatColor.AQUA + town.name);
        sender.sendMessage(ChatColor.GRAY + "      Mayor : " + ChatColor.GREEN + town.mayor);
        sender.sendMessage(ChatColor.GRAY + "    Kingdom : " + ChatColor.GREEN + town.kingdom);
        sender.sendMessage(ChatColor.GRAY + "      Funds : " + ChatColor.GREEN + KFunction.moneyString(town.funds));
        sender.sendMessage(ChatColor.GRAY + "       Area : " + ChatColor.GREEN + town.size());
        sender.sendMessage(ChatColor.GRAY + "   Location : " + ChatColor.GREEN + town.cuboid.pairString());
    }

    /**
     * Donates money to a town
     *
     * @param sender sender of a command
     * @param args   command arguments
     */
    void townDonate(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 2) {
            try{
                double amount = Double.parseDouble(args[1]);
                KData data = TSKKingdom.getPlayer(sender.getName());
                if (data.town == null)
                    sender.sendMessage("You are not part of a town!");
                else if (data.money < amount)
                    sender.sendMessage("You do not have enough money!");
                else {
                    data.town().depositMoney(amount);
                    data.takeMoney(amount);
                    sender.sendMessage("You donated " + KFunction.moneyString(amount));
                }
            }
            catch (Exception e) {
                sender.sendMessage(args[1] + " is an invalid amount of money");
            }
        }
        else townCommandUses(sender);
    }

    /**
     * Expands a town
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void townExpand(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 2) {
            try{
                int amount = Integer.parseInt(args[1]);
                KData data = TSKKingdom.getPlayer(sender.getName());
                Town t = TSKKingdom.getTown(((Player) sender).getLocation());

                if (t == null) {
                    sender.sendMessage("You are not in a town!");
                    return;
                }

                Vector direction = ((Player) sender).getLocation().getDirection();
                int xChange = Math.abs(direction.getX()) > Math.abs(direction.getZ()) ? (direction.getX() < 0 ? -amount : amount) : 0;
                int zChange = Math.abs(direction.getZ()) > Math.abs(direction.getX()) ? (direction.getZ() < 0 ? -amount : amount) : 0;
                double cost = KFunction.expansionCost(t.cuboid.width, t.cuboid.depth,
                        t.cuboid.width() + xChange, t.cuboid.depth + zChange);
                if (data.town == null)
                    sender.sendMessage("You are not part of a town!");
                else if (!data.rank.equalsIgnoreCase("Mayor") && !data.rank.equalsIgnoreCase("King"))
                    sender.sendMessage("You do not own a town!");
                else if (!t.name.equalsIgnoreCase(data.town))
                    sender.sendMessage("You are not in your town!");
                else if (data.money < cost)
                    sender.sendMessage("You do not have enough money!");
                else {
                    data.takeMoney(cost);
                    if (xChange < 0) t.cuboid.corner.setX(t.cuboid.corner.getX() + xChange);
                    t.cuboid.width += Math.abs(xChange);
                    if (zChange < 0) t.cuboid.corner.setZ(t.cuboid.corner.getZ() + zChange);
                    t.cuboid.depth += Math.abs(zChange);

                    sender.sendMessage("The town has been expanded " + args[1] + " blocks for " + KFunction.moneyString(cost));
                }
            }
            catch (Exception e) {
                sender.sendMessage(args[1] + " is not a valid amount of money!");
            }
        }
        else townCommandUses(sender);
    }

    /**
     * Displays info for a town
     *
     * @param sender sender of a command
     * @param args   command arguments
     */
    void townInfo(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 1) {
            KData data = TSKKingdom.getPlayer(sender.getName());
            if (data.town == null)
                sender.sendMessage("You are not part of a town!");
            else townDisplayInfo(sender, data.town());
        }
        else if (args.length == 2) {
            Town town = TSKKingdom.getTown(args[1]);
            if (town == null)
                sender.sendMessage("No town exists with the name " + args[1]);
            else townDisplayInfo(sender, town);
        }
        else townCommandUses(sender);
    }

    /**
     * Adds a request for the player to join the town
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void townJoin(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 2) {
            KData data = TSKKingdom.getPlayer(sender.getName());
            Town town = TSKKingdom.getTown(args[1]);
            if (town == null)
                sender.sendMessage("No town exists with the name " + args[1]);
            else if (data.kingdom == null)
                sender.sendMessage("You are not part of a kingdom!");
            else if (!data.kingdom.equalsIgnoreCase(town.kingdom))
                sender.sendMessage("That town is not part of your kingdom!");
            else if (data.town != null)
                sender.sendMessage("You are already part of a town!");
            else if (data.town().applicants().contains(data.name()))
                sender.sendMessage("You are already applied to " + args[1]);
            else {
                town.applicants().add(data.name());
                sender.sendMessage("You have applied to join the town " + args[1]);
            }
        }
        else townCommandUses(sender);
    }

    /**
     * Removes a player from their town
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void townLeave(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 1) {
            KData data = TSKKingdom.getPlayer(sender.getName());
            if (data.town == null)
                sender.sendMessage("You are not part of a town!");
            else if (data.rank.equalsIgnoreCase("Mayor"))
                sender.sendMessage("You can not leave a town while you are Mayor!");
            else if (data.rank.equalsIgnoreCase("King"))
                sender.sendMessage("You can not leave a town while you are King!");
            else {
                data.town().removeResident(data.name());
                sender.sendMessage("You have left your town!");
            }
        }
        else townCommandUses(sender);
    }

    /**
     * Lists all towns in a kingdom
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void townList(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 1) {
            KData data = TSKKingdom.getPlayer(sender.getName());
            if (data.kingdom == null)
                sender.sendMessage("You are not part of a kingdom!");
            else {
                String message = "Towns of " + data.kingdom + ": ";
                for (String town : data.kingdom().townNames()) message += town + ", ";
                if (message.charAt(message.length() - 2) == ',') message = message.substring(0, message.length() - 2);
                sender.sendMessage(message);
            }
        }
        else if (args.length == 2) {
            Kingdom kingdom = TSKKingdom.getKingdom(args[1]);
            if (kingdom == null)
                sender.sendMessage("No kingdom exists with the name " + args[1]);
            else {
                String message = "Towns of " + kingdom.name + ": ";
                for (String town : kingdom.townNames()) message += town + ", ";
                if (message.charAt(message.length() - 2) == ',') message = message.substring(0, message.length() - 2);
                sender.sendMessage(message);
            }
        }
        else townCommandUses(sender);
    }

    /**
     * Promotes a player in the kingdom
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void townPromote(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 3) {
            KData data = TSKKingdom.getPlayer(args[1]);
            KData user = TSKKingdom.getPlayer(sender.getName());
            if (!user.rank.equalsIgnoreCase("Mayor") && !user.rank.equalsIgnoreCase("King"))
                sender.sendMessage("You do not own a town!");
            if (data == null)
                sender.sendMessage("No player exists with the name " + args[1]);
            else if (!KRank.isRank(args[2]))
                sender.sendMessage("No rank exists with the name " + args[2]);
            else if (!KRank.isBasicRank(args[2]))
                sender.sendMessage("You cannot assign someone to " + args[2]);
            else if (data.rank.equalsIgnoreCase("Mayor"))
                sender.sendMessage("You cannot demote a mayor!");
            else if (data.rank.equalsIgnoreCase("King"))
                sender.sendMessage("You cannot demote a king!");
            else if (data.rank.equalsIgnoreCase(args[2]))
                sender.sendMessage("That player is already has the rank " + args[2]);
            else {
                data.setRank(args[2]);
                sender.sendMessage("The rank of " + args[1] + " has been set to " + args[2]);
                plugin.getServer().getPlayer(data.name()).sendMessage("Your rank has been set to " + args[2]);
            }
        }
        else kingdomCommandUses(sender);
    }

    /**
     * Displays a player's rank
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void townRank(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 1) {
            KData data = TSKKingdom.getPlayer(sender.getName());
            if (data.town == null)
                sender.sendMessage("You are not part of a town!");
            else {
                sender.sendMessage(sender.getName());
                sender.sendMessage("   Town: " + data.town);
                sender.sendMessage("   Rank: " + data.rank);
            }
        }
        else if (args.length == 2) {
            KData data = TSKKingdom.getPlayer(args[1]);
            if (data == null)
                sender.sendMessage("No player exists with the name " + args[1]);
            else if (data.town == null)
                sender.sendMessage("That player is not part of a town!");
            else {
                sender.sendMessage(data.name());
                sender.sendMessage("   Town: " + data.town);
                sender.sendMessage("   Rank: " + data.rank);
            }
        }
        else townCommandUses(sender);
    }

    /**
     * Rejects a player from joining a town
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void townReject(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 2) {
            KData data = TSKKingdom.getPlayer(sender.getName());
            if (data.town == null)
                sender.sendMessage("You are not part of a town!");
            else if (!data.rank.equalsIgnoreCase("Mayor") && !data.rank.equalsIgnoreCase("King"))
                sender.sendMessage("You do not own a town!");
            else if (args[1].equalsIgnoreCase("all")) {
                data.town().applicants().clear();
                sender.sendMessage("All applicants have been rejected!");
            }
            else if (!data.town().applicants().contains(args[1]))
                sender.sendMessage(args[1] + " has not applied!");
            else {
                data.town().applicants().remove(args[1]);
                sender.sendMessage(args[1] + " has been rejected!");
            }
        }
        else townCommandUses(sender);
    }

    /**
     * Resigns as mayor and makes a new player the mayor
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void townResign(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 2) {
            KData data = TSKKingdom.getPlayer(sender.getName());
            KData target = TSKKingdom.getPlayer(args[1]);
            if (data.town == null)
                sender.sendMessage("You are not part of a town!");
            else if (data.rank.equalsIgnoreCase("King"))
                sender.sendMessage("You cannot resign as a mayor when you are king!");
            else if (!data.rank.equalsIgnoreCase("Mayor"))
                sender.sendMessage("You are not a mayor!");
            else if (target == null)
                sender.sendMessage("No player exists with the name " + args[1]);
            else if (target.town == null)
                sender.sendMessage(args[1] + " does not belong to your town!");
            else if (!target.town.equalsIgnoreCase(data.town))
                sender.sendMessage(args[1] + " belongs to a different town!");
            else {
                data.setRank("Resident");
                target.setRank("Mayor");
                data.town().mayor = target.name();
                sender.sendMessage("You have resigned and " + args[1] + " has taken over");
                Player player = plugin.getServer().getPlayer(target.name());
                if (player != null) player.sendMessage("You have been made the mayor of " + target.town + "!");
            }
        }
        else townCommandUses(sender);
    }

    /**
     * Withdraws money from a town
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void townWithdraw(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 2) {
            try{
                double amount = Double.parseDouble(args[1]);
                KData data = TSKKingdom.getPlayer(sender.getName());
                if (data.rank == null)
                    sender.sendMessage("You are not a mayor!");
                else if (data.rank.equalsIgnoreCase("King"))
                    sender.sendMessage("You must withdraw through the kingdom as king!");
                else if (!data.rank.equalsIgnoreCase("Mayor"))
                    sender.sendMessage("You are not a mayor!");
                else if (data.town().funds < amount)
                    sender.sendMessage("The town does not have that much funds!");
                else {
                    data.town().withdrawMoney(amount);
                    data.giveMoney(amount);
                    sender.sendMessage("You withdrew " + KFunction.moneyString(amount));
                }
            }
            catch (Exception e) {
                sender.sendMessage(args[1] + " is an invalid amount of money");
            }
        }
        else kingdomCommandUses(sender);
    }

    /**
     * Appoints a new leader for the town
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void tskAppoint(CommandSender sender, String[] args) {
        if (args.length == 3) {
            KData data = TSKKingdom.getPlayer(args[1]);
            Town town = TSKKingdom.getTown(args[2]);
            if (data == null)
                sender.sendMessage("No player exists with the name " + args[1]);
            else if (town == null)
                sender.sendMessage("No town exists with the name " + args[2]);
            else if (data.kingdom == null)
                sender.sendMessage("That player is not part of a kingdom!");
            else if (!data.kingdom.equalsIgnoreCase(town.kingdom))
                sender.sendMessage("That player is not from the same kingdom!");
            else if (data.town == null)
                sender.sendMessage("That player is not part of that town!");
            else if (!data.town.equalsIgnoreCase(town.name()))
                sender.sendMessage("That player belongs to a different town!");
            else {
                for (Plot plot : town.mayor().plots())
                    plot.owner = args[1];
                town.mayor().setRank("Resident");
                town.mayor = args[1];
                if (town.kingdom().capital.equalsIgnoreCase(town.name()))
                    data.setRank("King");
                else data.setRank("Mayor");
                sender.sendMessage(args[1] + " has been appointed to the owner of " + args[2]);
            }
        }
        else tskCommandUses(sender);
    }

    /**
     * Displays the uses of the tskKingdom command
     *
     * @param sender sender of the command
     */
    void tskCommandUses(CommandSender sender){
        sender.sendMessage(ChatColor.AQUA + "TSK Command - Uses");
        if (sender instanceof Player) {
            sender.sendMessage("   /tskKingdom appoint <playerName> <townName>");
            sender.sendMessage("   /tskKingdom create <kingdomName> <capitalName> <prefixColor>");
            sender.sendMessage("   /tskKingdom delete <kingdomName>");
            sender.sendMessage("   /tskKingdom giveMoney <playerName> <amount>");
        }
        else {
            sender.sendMessage("   /tskKingdom appoint <playerName> <townName>");
            sender.sendMessage("   /tskKingdom delete <kingdomName>");
            sender.sendMessage("   /tskKingdom giveMoney <playerName> <amount>");
        }
    }

    /**
     * Creates a new kingdom
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void tskCreate(CommandSender sender, String[] args) {
        if (sender instanceof Player && args.length == 4) {
            KCuboid cuboid = new KCuboid(((Player) sender).getLocation(), Town.START_SIZE, Town.START_SIZE);
            KData data = TSKKingdom.getPlayer(sender.getName());
            ChatColor color = ChatColor.valueOf(args[3].toUpperCase());
            if (data.kingdom != null)
                sender.sendMessage("You cannot create a kingdom when you are in a kingdom!");
            else if (KFunction.conflictsTown(cuboid))
                sender.sendMessage("You cannot create a kingdom so close to another town!");
            else if (KFunction.kingdomNameTaken(args[1]))
                sender.sendMessage("That kingdom name is already taken!");
            else if (KFunction.townNameTaken(args[2]))
                sender.sendMessage("That town name is already taken!");
            else if (color == null)
                sender.sendMessage(args[3] + " is not a valid color!");
            else {
                Kingdom kingdom = new Kingdom(args[1], args[2], data.name(), color, cuboid);
                TSKKingdom.instance.kingdoms.put(args[1].toLowerCase(), kingdom);
                sender.sendMessage("You have created " + args[1] + " and have been assigned the king!");
                sender.sendMessage("You can assign the king by using /kingdom resign <playerName>");
            }
        }
        else tskCommandUses(sender);
    }

    /**
     * Deletes a kingdom
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void tskDelete(CommandSender sender, String[] args) {
        if (args.length == 2) {
            Kingdom kingdom = TSKKingdom.getKingdom(args[1]);
            if (kingdom == null)
                sender.sendMessage("No kingdom exists with the name " + args[1]);
            else {
                TSKKingdom.deleteKingdom(args[1]);
                sender.sendMessage(args[1] + " has been deleted!");
            }
        }
        else tskCommandUses(sender);
    }

    /**
     * Gives money to a player
     *
     * @param sender sender of the command
     * @param args   command arguments
     */
    void tskMoney(CommandSender sender, String[] args) {
        if (args.length == 3) {
            try{
                double amount = Double.parseDouble(args[2]);
                KData data = TSKKingdom.getPlayer(args[1]);

                if (data == null)
                    sender.sendMessage("No player exists with the name " + args[1]);
                else if (amount == 0)
                    sender.sendMessage("Giving $0 will do nothing!");
                else {
                    data.giveMoney(amount);
                    sender.sendMessage(args[1] + " was given " + KFunction.moneyString(amount));
                }
            }
            catch (Exception e) {
                sender.sendMessage(args[2] + " is not a valid number!");
            }
        }
        else tskCommandUses(sender);
    }
}