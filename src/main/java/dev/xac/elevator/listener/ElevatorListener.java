package dev.xac.elevator.listener;

import com.sun.javafx.scene.traversal.Direction;
import dev.xac.elevator.Elevator;
import dev.xac.elevator.util.CC;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class ElevatorListener implements Listener {
    private List<String> directions = Arrays.asList("up","down");


    private Elevator plugin;

    public ElevatorListener(final Elevator plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this.plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block block = e.getClickedBlock();
        if(!e.getAction().name().contains("RIGHT")) return;

        if(block == null) return;

        if (!(block.getState() instanceof Sign))
            return;

        Sign sign = (Sign) e.getClickedBlock().getState();
        String[] lines = sign.getLines();
        if (lines[0].equalsIgnoreCase(CC.translate("&c[Elevator]")) && lines[1].equalsIgnoreCase("Up")) {
            Location loc = getLocation(sign.getLocation(), Direction.UP);
            if (loc != null) {
                p.teleport(loc);
            } else {
                p.sendMessage(CC.translate("&cCouldint locate a valid location to teleport you to."));
            }
        } else if (lines[0].equalsIgnoreCase(CC.translate("&c[Elevator]")) && lines[1].equalsIgnoreCase("Down")) {
            Location loc = getLocation(sign.getLocation(), Direction.DOWN);
            if (loc != null) {
                p.teleport(loc);
            } else {
                p.sendMessage(CC.translate("&cCouldint locate a valid location to teleport you to."));
            }
        }
    }

    public Location getLocation(Location location, Direction direction) {
        if (direction == Direction.UP) {
            for (int i = location.getBlockY(); i < 255; i++) {
                Material firstBlock = location.getWorld().getBlockAt(location.getBlockX(), i, location.getBlockZ()).getType();
                Material secondBlock = location.getWorld().getBlockAt(location.getBlockX(), i + 1, location.getBlockZ()).getType();
                if (evaluateMaterials(firstBlock, secondBlock)) {
                    return new Location(location.getWorld(), location.getBlockX(), i, location.getBlockZ());
                }
            }
            return null;

        } else if (direction == Direction.DOWN) {
            for (int i = location.getBlockY(); i > 1; i--) {
                Material firstBlock = location.getWorld().getBlockAt(location.getBlockX(), i, location.getBlockZ()).getType();
                Material secondBlock = location.getWorld().getBlockAt(location.getBlockX(), i - 1, location.getBlockZ()).getType();
                if (evaluateMaterials(firstBlock, secondBlock)) {
                    return new Location(location.getWorld(), location.getBlockX(), i - 1, location.getBlockZ());
                }
            }
            return null;
        }
        return null;
    }

    public boolean evaluateMaterials(Material firstBlock, Material secondBlock) {
        return firstBlock.equals(Material.AIR) && secondBlock.equals(Material.AIR);
    }

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        if (!e.getLine(0).equalsIgnoreCase("[Elevator]")) {
            return;
        }

        if(e.getLine(1) == null || !directions.contains(e.getLine(1).toLowerCase())){
            return;
        }

        e.setLine(0, CC.translate("&c[Elevator]"));
        e.setLine(1, e.getLine(1));

    }
}

