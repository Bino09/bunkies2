package org.soren.bunkies;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Team;

import java.util.List;
import java.util.Set;


public class armourListener implements Listener {


    @EventHandler
    public void onEquipArmour(PlayerArmorChangeEvent event) {
        Player player = event.getPlayer();
        Team atk = TeamManager.board.getTeam("atk");
        Set<String> team = atk.getEntries();

    }
}
