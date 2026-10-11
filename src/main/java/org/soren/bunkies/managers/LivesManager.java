package org.soren.bunkies.managers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class LivesManager {
    static HashMap<UUID, Integer> lives = new HashMap<>();
    public static void startGame(List<Player> playerList) {
        int defaultLives = 2;
        for(int i = 0; i < playerList.size(); i++) {
            lives.put(playerList.get(i).getUniqueId(), defaultLives);
        }


    }
    public static void validDeath(UUID uuid) {

        int life = lives.get(uuid);
        life -= 1;
        lives.remove(uuid);
        lives.put(uuid, life);


    }
    public static int getLives(UUID uuid) {
       int numberLives = lives.get(uuid);

        return numberLives;
    }
    public static void setLives(UUID uuid, int amount, Player player) {
        int life = lives.get(uuid);
        if(life < 0) {
            life = amount;
            lives.remove(uuid);
            lives.put(uuid,life);
            player.setGameMode(GameMode.SURVIVAL);
        }
        if(amount == 0) {
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(Component.text("You have been killed", TextColor.fromHexString("#f0bf3a")));
        }
        life = amount;
        lives.remove(uuid);
        lives.put(uuid,life);

        player.setGameMode(GameMode.SURVIVAL);

    }
}
