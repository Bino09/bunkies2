package org.soren.bunkies.managers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.soren.bunkies.Bunkies2;

import java.util.HashMap;
import java.util.Objects;

public class GameManager {

    static int gameStatus = 0;
    static FileConfiguration config = JavaPlugin.getPlugin(Bunkies2.class).getConfig();

    public static void startGame() {
        gameStatus = 1;
        Bukkit.getWorld("world").setDifficulty(Difficulty.HARD);
        Location atk = config.getLocation("warps.atk");
        Location def = config.getLocation("warps.def");
        for(int i = 0; i < playerManager.getAtk().size(); i++) {
            Player player = playerManager.getAtk().get(i);
            player.teleport(atk);
            player.setGameMode(GameMode.SURVIVAL);
        }
        for(int i = 0; i < playerManager.getDef().size(); i++) {
            Player player = playerManager.getDef().get(i);
            player.teleport(def);
            player.setGameMode(GameMode.SURVIVAL);
        }
    }
    public static void endGame() {
        gameStatus = 0;
        Location atk1 = config.getLocation("warps.atk1");
        Location def1 = config.getLocation("warps.def1");
        Server server = Bukkit.getServer();

        HashMap<String, Float> leaderboard = StatManager.Leaderboard();
        for(int i = 0; i < leaderboard.size(); i++) {


        }

    }



    public static int getGameStatus() {
        return gameStatus;
    }
}
