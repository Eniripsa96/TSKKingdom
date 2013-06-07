package com.tsk.sucy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KCommandExecutor implements CommandExecutor {

    TSKKingdom plugin;

    public KCommandExecutor(TSKKingdom plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName();

        // TODO add command functions

        return true;
    }
}
