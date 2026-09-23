package org.soren.bunkies;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TeamManager {
    static Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
    public static Team registerTeam(String team_name) {


        Team team = board.registerNewTeam(team_name);

        return team;


    }
    public static void unregisterTeams() {
        Team team = board.getTeam("atk");
        team.unregister();
        team = board.getTeam("def");
        team.unregister();

    }
    public static void addDef(Player player) {
        Team def = board.getTeam("def");
        def.color(NamedTextColor.BLUE);
        def.prefix(Component.text("[Def] "));

        def.addPlayer(player);



    }
    public static void addAtk(Player player) {
        Team atk = board.getTeam("atk");
        atk.color(NamedTextColor.RED);
        atk.prefix(Component.text("[Atk] "));

        atk.addPlayer(player);



    }

}




