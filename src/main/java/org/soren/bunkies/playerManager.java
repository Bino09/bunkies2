package org.soren.bunkies;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

enum ePriority {
    RANDOM,
    DEF,
    ATK;
}

public class playerManager {

    /** The result of a team draw. */
    public record Teams(List<Player> defenders, List<Player> attackers) {}

    public static Teams genTeams(int numberOfDefenders) {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        Collections.shuffle(players);


        int defCount = Math.max(0, Math.min(numberOfDefenders, players.size()));

        List<Player> defenders = new ArrayList<>(players.subList(0, defCount));
        List<Player> attackers = new ArrayList<>(players.subList(defCount, players.size()));

        for (Player p : defenders) {
            p.sendMessage(Component.text("You are on def"));
        }
        for (Player p : attackers) {
            p.sendMessage(Component.text("You are on atk"));
        }

        return new Teams(defenders, attackers);
    }
}

