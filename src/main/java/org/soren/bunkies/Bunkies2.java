package org.soren.bunkies;

import org.bukkit.plugin.java.JavaPlugin;

public final class Bunkies2 extends JavaPlugin {

    @Override
    public void onEnable() {
         getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> ModCommands.register(event.registrar()));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
