package com.tsk.sucy;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

/**
 * Handles various events tied to kingdom permissions
 */
public class KListener implements Listener {

    Plugin plugin;

    /**
     * Constructor
     *
     * @param plugin plugin reference
     */
    public KListener(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    /**
     * Prevents players from building where they shouldn't
     *
     * @param event event details
     */
    @EventHandler (priority = EventPriority.LOW)
    public void onBreak(BlockBreakEvent event) {
        if (!KRank.canBuild(event.getPlayer().getName(), event.getBlock().getLocation()))
            event.setCancelled(true);
    }

    /**
     * Prevents players from building where they shouldn't
     *
     * @param event plugin reference
     */
    @EventHandler (priority = EventPriority.LOW)
    public void onPlace(BlockPlaceEvent event) {
        if (!KRank.canBuild(event.getPlayer().getName(), event.getBlock().getLocation()))
            event.setCancelled(true);
    }

    /**
     * Prevents players from damaging people in the same kingdom
     */
    @EventHandler (priority = EventPriority.HIGH)
    public void onDamage(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity damaged = event.getEntity();
        if (damager instanceof Projectile) damager = ((Projectile) damager).getShooter();

        if (!(damager instanceof Player) || !(damaged instanceof Player))
            return;

        KData attacker = TSKKingdom.getPlayer(((Player) damager).getName());
        KData victim = TSKKingdom.getPlayer(((Player) damaged).getName());

        if (attacker.kingdom != null && victim.kingdom != null) {
            if (attacker.kingdom.equalsIgnoreCase(victim.kingdom))
                event.setCancelled(true);
        }
    }

    @EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {
        Town town = TSKKingdom.getTown(event.getPlayer().getLocation());
        Plot plot = TSKKingdom.getPlot(event.getPlayer().getLocation());
        KData data = TSKKingdom.getPlayer(event.getPlayer().getName());

        if (town == null) {
            if (data.currentTown != null) {
                event.getPlayer().sendMessage("-= Leaving " + data.currentTown + " =-");
                data.currentTown = null;
            }
        }
        else if (data.currentTown == null) {
            data.currentTown = town.name();
            event.getPlayer().sendMessage("-= Entering " + town.name() + " =-");
        }
        else if (!data.currentTown.equalsIgnoreCase(town.name())) {
            data.currentTown = town.name();
            event.getPlayer().sendMessage("-= Entering " + town.name() + " =-");
        }

        if (plot == null) {
            if (data.currentPlot != null) {
                event.getPlayer().sendMessage("-= Leaving " + plot.name + " =-");
                data.currentPlot = null;
            }
        }
        else if (data.currentPlot == null) {
            data.currentPlot = plot.name();
            event.getPlayer().sendMessage("-= Entering " + plot.name + " =-");
        }
        else if (!data.currentPlot.equalsIgnoreCase(plot.name)) {
            data.currentPlot = plot.name;
            event.getPlayer().sendMessage("-= Entering " + plot.name + " =-");
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onDrop(PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(KCommandExecutor.TOOL_NAME))
            event.setCancelled(true);
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getPlayer().getItemInHand().hasItemMeta()
                && event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(KCommandExecutor.TOOL_NAME)) {
            event.setCancelled(true);
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                TSKKingdom.getPlayer(event.getPlayer().getName()).p2 = event.getClickedBlock().getLocation();
                event.getPlayer().sendMessage("Second point set to (" + event.getClickedBlock().getLocation().getBlockX()
                        + ", " + event.getClickedBlock().getLocation().getBlockZ() + ")");
            }
            else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                TSKKingdom.getPlayer(event.getPlayer().getName()).p1 = event.getClickedBlock().getLocation();
                event.getPlayer().sendMessage("First point set to (" + event.getClickedBlock().getLocation().getBlockX()
                        + ", " + event.getClickedBlock().getLocation().getBlockZ() + ")");
            }
        }
    }
}
