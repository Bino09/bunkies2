package org.soren.bunkies.managers;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.soren.bunkies.Bunkies2;
import org.soren.bunkies.modListener;
import vg.civcraft.mc.namelayer.GroupManager;
import vg.civcraft.mc.namelayer.group.Group;
import vg.civcraft.mc.namelayer.permission.*;





public class TeamManager {
    public static Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
    static FileConfiguration config = JavaPlugin.getPlugin(Bunkies2.class).getConfig();

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
        Group defnl = GroupManager.getGroup("def");
        Group atknl = GroupManager.getGroup("atk");
        try {
            atknl.removeMember(player.getUniqueId());
        }
        catch (Exception ignore){}

        defnl.addMember(player.getUniqueId(), GroupManager.PlayerType.ADMINS);


        Location def1 = config.getLocation("warps.def1");
        Location defwarp = config.getLocation("warps.def");
        Team def = board.getTeam("def");
        def.color(NamedTextColor.BLUE);
        def.prefix(Component.text("[Def] "));
        player.sendMessage(Component.text("You are on the Defenders", TextColor.fromHexString("#5555FF")));
        if (GameManager.getGameStatus() == 1) {
            player.teleport(defwarp);

        } else {
            player.teleport(def1);

        }
        player.setGameMode(GameMode.SURVIVAL);


        def.addPlayer(player);


    }

    public static void addAtk(Player player) {
        Group defnl = GroupManager.getGroup("def");
        try {
            defnl.removeMember(player.getUniqueId());
        }
        catch (Exception ignore){}
        Group atknl = GroupManager.getGroup("atk");
        atknl.addMember(player.getUniqueId(), GroupManager.PlayerType.ADMINS);

        Location atk1 = config.getLocation("warps.atk1");
        Location atkwarp = config.getLocation("warps.atk");

        Team atk = board.getTeam("atk");
        atk.color(NamedTextColor.RED);
        atk.prefix(Component.text("[Atk] "));
        player.sendMessage(Component.text("You are on the Attackers", TextColor.fromHexString("#FF5555")));
        player.setGameMode(GameMode.SURVIVAL);
        if (GameManager.getGameStatus() == 1) {
            player.teleport(atkwarp);
            player.setGameMode(GameMode.SURVIVAL);

        } else {
            player.teleport(atk1);

        }
        atk.addPlayer(player);



    }

    public static void swapPlayer(Player player) {
        Team def = board.getTeam("def");
        Team atk = board.getTeam("atk");
        Group defnl = GroupManager.getGroup("def");
        Group atknl = GroupManager.getGroup("atk");

        if (def.hasPlayer(player)) {
            def.removePlayer(player);
            defnl.removeMember(player.getUniqueId());


            addAtk(player);
            atknl.addMember(player.getUniqueId(), GroupManager.PlayerType.ADMINS);

            playerManager.swapDef(player);


        } else if (atk.hasPlayer(player)) {
            atk.removePlayer(player);
            atknl.removeMember(player.getUniqueId());


            addDef(player);
            defnl.addMember(player.getUniqueId(), GroupManager.PlayerType.ADMINS);

            playerManager.swapAtk(player);


        }
        try {
            modListener.checkArmour(player);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}




