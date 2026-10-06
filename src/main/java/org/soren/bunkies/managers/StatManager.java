package org.soren.bunkies.managers;

import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;


public class StatManager {
  static HashMap<Player, Integer> deathTracker = new HashMap<>();
  static HashMap<Player, Integer> killTracker = new HashMap<>();
  public static void startGame(List<Player> playerList) {
    
    for(int i = 0; i < playerList.size(); i++) {
      deathTracker.put(playerList.get(i), 0);
      killTracker.put(playerList.get(i), 0);
      
    }
  
  }
  public static void getStats(Player player) {
    int deaths = (2 - deathTracker.get(player));
    int kills = killTracker.get(player);
    float kd = ((float) kills / deaths);
    player.sendMessage(Component.text("You have died: " + deaths + " times", TextColor.fromHexString("#1DF251")));
    player.sendMessage(Component.text("You have: " + kills + " kills", TextColor.fromHexString("#1DF251")));
    player.sendMessage(Component.text("Your K/D is " + kd, TextColor.fromHexString("#1DF251")));
    
  }
  public static void updateKills(Player player) {
    int kills = killTracker.get(player);
    kills += 1;
    killTracker.remove(player);
    killTracker.put(player, kills);
      
  }
  public static void updateDeaths(Player player) {
    int deaths = deathTracker.get(player);
    deaths += 1;
    deathTracker.remove(player);
    deathTracker.put(player, deaths);
  }
                                    
}
