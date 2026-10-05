package org.soren.bunkies.managers;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;


public class playerManager {



    public record Teams(List<Player> def, List<Player> atk) {
    }
    private static Teams team = new Teams(new ArrayList<>(), new ArrayList<>());

    public static Teams genTeams(int numberOfDefenders) {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        LivesManager.startGame(players);
        StatManager.startGame(players);

        players.removeIf(Player::isOp);

        Random r = new Random();
        List<Player> atk = new ArrayList<>();
        List<Player> def = new ArrayList<>();
        for (int i = 0; i < numberOfDefenders; i++) {
            int index = r.nextInt(players.size());
            def.add(players.get(index));
            players.remove(index);



        }
        atk.addAll(players);

        for (Player player : def) {

            TeamManager.addDef(player);




        }
        for (Player player : atk) {

            TeamManager.addAtk(player);

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
    public static void swapAtk(Player player) { //remove atk add def
        List<Player> atk = getAtk();
        List<Player> def = getDef();
        atk.remove(player);
        def.add(player);

    }
    public static void swapDef(Player player) { //remove def add atk
        List<Player> atk = getAtk();
        List<Player> def = getDef();
        def.remove(player);
        atk.add(player);

    }
    public static void addDef(Player player) { //remove def add atk
        List<Player> def = getDef();
        def.add(player);


    }
    public static void addAtk(Player player) { //remove def add atk
        List<Player> atk = getAtk();
        atk.add(player);


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

