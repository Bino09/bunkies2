package org.soren.bunkies;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.scoreboard.Team;
import org.soren.bunkies.managers.LivesManager;
import org.soren.bunkies.managers.TeamManager;
import org.soren.bunkies.managers.playerManager;


import java.util.Set;


public class modListener implements Listener {


    @EventHandler
    public void onEquipArmour(PlayerArmorChangeEvent event) {
        ItemStack newBoots = new ItemStack(Material.DIAMOND_BOOTS);
        ItemStack newPants = new ItemStack(Material.DIAMOND_LEGGINGS);
        ItemStack newChest = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemStack newHelmet = new ItemStack(Material.DIAMOND_HELMET);





        Player player = event.getPlayer();
        Team atk = TeamManager.board.getTeam("atk");
        Team def = TeamManager.board.getTeam("def");
        assert atk != null;
        Set<String> atkEntries = atk.getEntries();
        assert def != null;
        Set<String> defEntries = def.getEntries();
        String boots = "";
        String pants = "";
        String chestplate = "";
        String helmet = "";
        try {
            boots = player.getEquipment().getBoots().getItemMeta().getAsComponentString();
            pants = player.getEquipment().getLeggings().getItemMeta().getAsComponentString();
            chestplate = player.getEquipment().getChestplate().getItemMeta().getAsComponentString();
            helmet = player.getEquipment().getHelmet().getItemMeta().getAsComponentString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(!boots.equals(!boots.equals("[minecraft:trim={material: \"minecraft:lapis\", pattern: \"minecraft:wayfinder\"}]") || !boots.equals("[minecraft:trim={material: \"minecraft:redstone\", pattern: \"minecraft:wayfinder\"}]"))) {

            if (defEntries.contains(player.getName())) {

                ArmorMeta meta = (ArmorMeta) newBoots.getItemMeta();
                meta.setTrim(new ArmorTrim(TrimMaterial.LAPIS, TrimPattern.WAYFINDER));
                newBoots.setItemMeta(meta);
                player.getEquipment().setBoots(newBoots);
            }
            if (atkEntries.contains(player.getName())) {
                ArmorMeta meta = (ArmorMeta) newBoots.getItemMeta();
                meta.setTrim(new ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.WAYFINDER));
                newBoots.setItemMeta(meta);
                player.getEquipment().setBoots(newBoots);

            }
        }
            if (!pants.equals("[minecraft:trim={material: \"minecraft:lapis\", pattern: \"minecraft:wayfinder\"}]") || !pants.equals("[minecraft:trim={material: \"minecraft:redstone\", pattern: \"minecraft:wayfinder\"}]")) {

            if (defEntries.contains(player.getName())) {
                ArmorMeta meta = (ArmorMeta) newPants.getItemMeta();
                meta.setTrim(new ArmorTrim(TrimMaterial.LAPIS, TrimPattern.WAYFINDER));
                newPants.setItemMeta(meta);

                player.getEquipment().setLeggings(newPants);

            }
            if (atkEntries.contains(player.getName())) {
                ArmorMeta meta = (ArmorMeta) newPants.getItemMeta();
                meta.setTrim(new ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.WAYFINDER));
                newPants.setItemMeta(meta);

                player.getEquipment().setLeggings(newPants);

            }
        }
        if (!chestplate.equals(!helmet.equals("[minecraft:trim={material: \"minecraft:lapis\", pattern: \"minecraft:wayfinder\"}]") || !chestplate.equals("[minecraft:trim={material: \"minecraft:redstone\", pattern: \"minecraft:wayfinder\"}]"))) {

            if (defEntries.contains(player.getName())) {
                        ArmorMeta meta = (ArmorMeta) newChest.getItemMeta();
                        meta.setTrim(new ArmorTrim(TrimMaterial.LAPIS, TrimPattern.WAYFINDER));
                        newChest.setItemMeta(meta);
                        player.getEquipment().setChestplate(newChest);
                    }
            if (atkEntries.contains(player.getName())) {
                ArmorMeta meta = (ArmorMeta) newChest.getItemMeta();
                meta.setTrim(new ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.WAYFINDER));
                newChest.setItemMeta(meta);
                player.getEquipment().setChestplate(newChest);

            }
                }
        if (!helmet.equals("[minecraft:trim={material: \"minecraft:lapis\", pattern: \"minecraft:wayfinder\"}]") || !helmet.equals("[minecraft:trim={material: \"minecraft:redstone\", pattern: \"minecraft:wayfinder\"}]")) {
            if(defEntries.contains(player.getName())) {
                        ArmorMeta meta = (ArmorMeta) newHelmet.getItemMeta();
                        meta.setTrim(new ArmorTrim(TrimMaterial.LAPIS, TrimPattern.WAYFINDER));
                        newHelmet.setItemMeta(meta);
                        player.getEquipment().setHelmet(newHelmet);
                    }
            if (atkEntries.contains(player.getName())) {
                ArmorMeta meta = (ArmorMeta) newHelmet.getItemMeta();
                meta.setTrim(new ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.WAYFINDER));
                newHelmet.setItemMeta(meta);
                player.getEquipment().setHelmet(newHelmet);

            }

                }
            }
            @EventHandler

    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        Entity killer = event.getDamageSource().getCausingEntity();
        if(playerManager.getAtk().contains(player)) {


            if(playerManager.getDef().contains(killer)) {
                killer.sendMessage(Component.text("Successful kill on " + playerName, TextColor.fromHexString("#5555FF")));
                LivesManager.validDeath(player.getUniqueId());
                player.sendMessage(Component.text(LivesManager.getLives(player.getUniqueId())));
            }
        }
        if(playerManager.getDef().contains(player)) {


            if(playerManager.getAtk().contains(killer)) {
                killer.sendMessage(Component.text("Successful kill on " + playerName, TextColor.fromHexString("#FF5555")));
                LivesManager.validDeath(player.getUniqueId());
                player.sendMessage(Component.text(LivesManager.getLives(player.getUniqueId())));
            }
        }
        if(LivesManager.getLives(player.getUniqueId()) <= 0) {
            player.setGameMode(GameMode.SPECTATOR);
        }



    }
        }





