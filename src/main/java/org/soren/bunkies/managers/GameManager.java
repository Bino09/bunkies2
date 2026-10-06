package org.soren.bunkies.managers;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.soren.bunkies.Bunkies2;

public class GameManager {
    static int gameStatus = 0;
    static FileConfiguration config = JavaPlugin.getPlugin(Bunkies2.class).getConfig();
    public static void startGame() {
        gameStatus = 1;

        Location atk = config.getLocation("warps.atk");
        Location def = config.getLocation("warps.def");
        for(int i = 0; i < playerManager.getAtk().size(); i++) {
            Player player = playerManager.getAtk().get(i);
            player.teleport(atk);
        }
        for(int i = 0; i < playerManager.getDef().size(); i++) {
            Player player = playerManager.getDef().get(i);
            player.teleport(def);
        }
    }



    public static int getGameStatus() {
        return gameStatus;
    }
}
