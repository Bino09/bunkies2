package org.soren.bunkies.managers;

import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
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
    int deaths = deathTracker.get(player);
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
  public static HashMap<String, Float> Leaderboard() {

    HashMap<String, Float> leaderboard = new HashMap<>();
    List<String> playerPart = new ArrayList<>();
    List<Float> KDPart = new ArrayList<>();
    killTracker.forEach((player,integer) -> {
      playerPart.add(player.getName());
      if (deathTracker.get(player) <= 0) {
        updateDeaths(player);
      }
      KDPart.add((float) (killTracker.get(player)/deathTracker.get(player)));



    });
    int playerLength = playerPart.size();
    if(playerPart.size() == KDPart.size()) {
      for(int i=0; i < 4 || i < playerPart.size(); i++ ){
        System.out.println((playerPart.get(i) + KDPart.get(i)));
        String highest = playerPart.getFirst();
        int highestIndex = 0;
        for(int k=0; i < playerLength; i++ ){
          if(highestIndex < KDPart.get(k)) {
            highestIndex = k;
            highest = playerPart.get(k);

          }
          if (highestIndex == KDPart.get(k)) {
            highest = highest + "," + playerPart.get(k);

          }
        }
        leaderboard.put(highest, KDPart.get(highestIndex));
        System.out.println(("leaderboard "+ leaderboard));
      }
    }

    else {
      System.out.println("error, list is desynced");
    }
    System.out.println(("leaderboard2 "+ leaderboard));

  return leaderboard;


  }
                                    
}
