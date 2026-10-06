package org.soren.bunkies;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;
import org.soren.bunkies.managers.*;


import java.util.Set;


public class modListener implements Listener {



    @EventHandler
    public void onEquipArmour(PlayerArmorChangeEvent event) throws Exception {
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

        String boots = player.getEquipment().getBoots().getItemMeta().getAsComponentString();
        String pants = player.getEquipment().getLeggings().getItemMeta().getAsComponentString();
        String chestplate = player.getEquipment().getChestplate().getItemMeta().getAsComponentString();
        String helmet = player.getEquipment().getHelmet().getItemMeta().getAsComponentString();

        if (!boots.equals("[minecraft:trim={material: \"minecraft:lapis\", pattern: \"minecraft:wayfinder\"}]") || !boots.equals("[minecraft:trim={material: \"minecraft:redstone\", pattern: \"minecraft:wayfinder\"}]")) {

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
            if (defEntries.contains(player.getName())) {
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
    public void playerDamage(EntityDamageByEntityEvent event) {
        if(GameManager.getGameStatus() == 0){
            event.setCancelled(true);
        }
    }

    @EventHandler

    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        Entity killer = event.getDamageSource().getCausingEntity();
        if (playerManager.getAtk().contains(player)) {

            if (playerManager.getDef().contains(killer)) {
                killer.sendMessage(Component.text("Successful kill on " + playerName, TextColor.fromHexString("#5555FF")));
                StatManager.updateDeaths(player);
                StatManager.updateKills((Player) killer);
                LivesManager.validDeath(player.getUniqueId());

            }
        }
        if (playerManager.getDef().contains(player)) {


            if (playerManager.getAtk().contains(killer)) {
                killer.sendMessage(Component.text("Successful kill on " + playerName, TextColor.fromHexString("#FF5555")));
                LivesManager.validDeath(player.getUniqueId());
                StatManager.updateDeaths(player);
                StatManager.updateKills((Player) killer);

            }
        }
        if (LivesManager.getLives(player.getUniqueId()) <= 0) {
            player.setGameMode(GameMode.SPECTATOR);
        }


    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        FileConfiguration config = JavaPlugin.getPlugin(Bunkies2.class).getConfig();
        Player player = event.getPlayer();
        if (playerManager.getAtk().contains(player)) {
            Location location = config.getLocation("warps.atk");

            event.setRespawnLocation(location);
        }
        if (playerManager.getDef().contains(player)) {
            Location location = config.getLocation("warps.def");

            event.setRespawnLocation(location);
        }

    }
    public static void checkArmour(Player player) throws Exception {
        ItemStack newBoots = new ItemStack(Material.DIAMOND_BOOTS);
        ItemStack newPants = new ItemStack(Material.DIAMOND_LEGGINGS);
        ItemStack newChest = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemStack newHelmet = new ItemStack(Material.DIAMOND_HELMET);



        Team atk = TeamManager.board.getTeam("atk");
        Team def = TeamManager.board.getTeam("def");
        assert atk != null;
        Set<String> atkEntries = atk.getEntries();
        assert def != null;
        Set<String> defEntries = def.getEntries();

        String boots = player.getEquipment().getBoots().getItemMeta().getAsComponentString();
        String pants = player.getEquipment().getLeggings().getItemMeta().getAsComponentString();
        String chestplate = player.getEquipment().getChestplate().getItemMeta().getAsComponentString();
        String helmet = player.getEquipment().getHelmet().getItemMeta().getAsComponentString();

        if (!boots.equals("[minecraft:trim={material: \"minecraft:lapis\", pattern: \"minecraft:wayfinder\"}]") || !boots.equals("[minecraft:trim={material: \"minecraft:redstone\", pattern: \"minecraft:wayfinder\"}]")) {

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
            if (defEntries.contains(player.getName())) {
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


}





