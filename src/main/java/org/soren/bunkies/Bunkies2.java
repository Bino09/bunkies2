package org.soren.bunkies;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;

public class Bunkies2 extends JavaPlugin {

    @Override
    public void onEnable() {
         getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> ModCommands.register(event.registrar()));
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> ModCommands.register(event.registrar()));
    }
}