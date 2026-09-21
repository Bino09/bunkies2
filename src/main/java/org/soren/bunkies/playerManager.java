package org.soren.bunkies;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

enum ePriority {
    RANDOM,
    DEF,
    ATK;
}

public class playerManager {


    public record Teams(List<Player> def, List<Player> atk) {
    }
    private static Teams team = new Teams(new ArrayList<>(), new ArrayList<>());

    public static Teams genTeams(int numberOfDefenders) {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

        players.removeIf(Player::isOp);

        Random r = new Random();
        List<Player> atk = new ArrayList<>();
        List<Player> def = new ArrayList<>();
        System.out.println(players);
        for (int i = 0; i < numberOfDefenders; i++) {
            int index = r.nextInt(players.size());
            def.add(players.get(index));
            players.remove(index);
            System.out.println(def);

        }
        atk.addAll(players);

        for (Player player : def) {
            player.sendMessage(Component.text("You are on def"));
        }
        for (Player player : atk) {
            player.sendMessage(Component.text("You are on atk"));
        }
        team = new Teams(def, atk);
        return team;

    }
    public static List<Player> getAtk() {
        return team.atk;
    }
    public static List<Player> getDef() {
        return team.def;
    }


    public static void listTeams(Player player){
        String atk_team = "";
        List<String> atk = new ArrayList<>();
        atk.addAll(getAtk().stream().map(Player::getName).toList());
        for(int i=0; i < atk.size(); i++ ) {
            atk_team = atk_team + atk.get(i) + ", ";
        }
        String def_team = "";
                List<String> def = new ArrayList<>();
                def.addAll(getDef().stream().map(Player::getName).toList());
                for(int i=0; i < def.size(); i++ ) {
                    def_team = def_team + def.get(i) + ", ";
                }



        player.sendMessage(Component.text("Atk: " + atk_team + "Def: " + def_team));

    }
}

