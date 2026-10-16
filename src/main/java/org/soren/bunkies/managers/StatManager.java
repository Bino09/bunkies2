package org.soren.bunkies.managers;

import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class StatManager {
  static HashMap<Player, Integer> deathTracker = new HashMap<>();
  static HashMap<Player, Integer> killTracker = new HashMap<>();

  public static void startGame(List<Player> playerList) {

    for (int i = 0; i < playerList.size(); i++) {
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
    List<Player> playerList = new ArrayList<>(Bukkit.getOnlinePlayers().stream().toList());
    HashMap<String, Float> leaderboard = new HashMap<>();
    Player player = playerList.getFirst();
    String entry = "";
    int length = playerList.size();
    float KD = 0;
    for (int i = 0; i < 4 || i < length; i++) {
      if (deathTracker.get(player) <= 0) {
        updateDeaths(player);
      }
      player = playerList.get(i);
      System.out.println("works1");
      if(!(player.toString().equals(playerList.get(i).getName()))) {
        for (int k = 0; k < (length); k++) {
          System.out.println("works");
          System.out.println(leaderboard);
          System.out.println((playerList.size()));
          System.out.println(i);

          System.out.println(k);


          if ((float) (killTracker.get(player) / deathTracker.get(player)) < (float) (killTracker.get(playerList.get(k)) / deathTracker.get(playerList.get(k)))) {
            entry = playerList.get(k).getName();
            KD = (float) (killTracker.get(playerList.get(k)) / deathTracker.get(playerList.get(k)));
            leaderboard.put(entry, KD);
            if(k < playerList.size()){
              playerList.remove(playerList.get((k-1)));
            }
            else {
              playerList.remove(k);
            }

          }
          if ((float) (killTracker.get(player) / deathTracker.get(player)) == (float) (killTracker.get(playerList.get(k)) / deathTracker.get(playerList.get(k)))) {
            entry = player.getName() + " " + playerList.get(k).getName();
            leaderboard.put(entry, KD);
            if(k < playerList.size()){
              playerList.remove(playerList.get((k-1)));
            }

          }
          if(playerList.size() < i) {
            break;
          }
        }
      }
    }


    return leaderboard;


  }
                                    
}
