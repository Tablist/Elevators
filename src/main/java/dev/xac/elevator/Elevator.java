package dev.xac.elevator;

import dev.xac.elevator.listener.ElevatorListener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class Elevator extends JavaPlugin {

    @Getter
    private static Elevator instance;

    @Override
    public void onEnable() {
        instance = this;

        new ElevatorListener(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
