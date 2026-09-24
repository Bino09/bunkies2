package org.soren.bunkies;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class armourListener implements Listener {


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
        String boots = player.getEquipment().getBoots().getItemMeta().getAsComponentString();
        String pants = player.getEquipment().getLeggings().getItemMeta().getAsComponentString();
        String chestplate = player.getEquipment().getChestplate().getItemMeta().getAsComponentString();
        String helmet = player.getEquipment().getHelmet().getItemMeta().getAsComponentString();


        if(!boots.equals("[minecraft:trim={material: \"minecraft:lapis\", pattern: \"minecraft:wayfinder\"}]")) {

            if (defEntries.contains(player.getName())) {

                ArmorMeta meta = (ArmorMeta) newBoots.getItemMeta();
                meta.setTrim(new ArmorTrim(TrimMaterial.LAPIS, TrimPattern.WAYFINDER));
                newBoots.setItemMeta(meta);
                player.getEquipment().setBoots(newBoots);
            }
            else if (atkEntries.contains(player.getName())) {
                ArmorMeta meta = (ArmorMeta) newBoots.getItemMeta();
                meta.setTrim(new ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.WAYFINDER));
                newBoots.setItemMeta(meta);
                player.getEquipment().setBoots(newBoots);

            }
        }
            else if (!pants.equals("[minecraft:trim={material: \"minecraft:lapis\", pattern: \"minecraft:wayfinder\"}]")) {

            if (defEntries.contains(player.getName())) {
                ArmorMeta meta = (ArmorMeta) newPants.getItemMeta();
                meta.setTrim(new ArmorTrim(TrimMaterial.LAPIS, TrimPattern.WAYFINDER));
                newPants.setItemMeta(meta);
                player.getEquipment().setLeggings(newPants);

            } else if (atkEntries.contains(player.getName())) {
                ArmorMeta meta = (ArmorMeta) newPants.getItemMeta();
                meta.setTrim(new ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.WAYFINDER));
                newPants.setItemMeta(meta);
                player.getEquipment().setLeggings(newPants);

            }
        }
        if (!chestplate.equals("[minecraft:trim={material: \"minecraft:lapis\", pattern: \"minecraft:wayfinder\"}]")) {

            if (defEntries.contains(player.getName())) {
                        ArmorMeta meta = (ArmorMeta) newChest.getItemMeta();
                        meta.setTrim(new ArmorTrim(TrimMaterial.LAPIS, TrimPattern.WAYFINDER));
                        newChest.setItemMeta(meta);
                        player.getEquipment().setChestplate(newChest);
                    }
            else if (atkEntries.contains(player.getName())) {
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
            else if (atkEntries.contains(player.getName())) {
                ArmorMeta meta = (ArmorMeta) newHelmet.getItemMeta();
                meta.setTrim(new ArmorTrim(TrimMaterial.REDSTONE, TrimPattern.WAYFINDER));
                newHelmet.setItemMeta(meta);
                player.getEquipment().setHelmet(newHelmet);

            }

                }
            }
        }





