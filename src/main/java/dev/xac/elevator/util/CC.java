package dev.xac.elevator.util;

import org.bukkit.ChatColor;

public class CC {

    public static String translate(final String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
