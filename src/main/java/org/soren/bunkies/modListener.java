package org.soren.bunkies;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;
import org.soren.bunkies.managers.*;


public class modListener implements Listener {

    @EventHandler
    public void onEquipArmour(PlayerArmorChangeEvent event) {
        checkArmour(event.getPlayer());
    }

    @EventHandler
    public void playerDamage(EntityDamageByEntityEvent event) {
        if (GameManager.getGameStatus() == 0) {
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


    public static void checkArmour(Player player) {
        Team atk = TeamManager.board.getTeam("atk");
        Team def = TeamManager.board.getTeam("def");
        String name = player.getName();

        TrimMaterial trimMat;
        if (def != null && def.hasEntry(name)) trimMat = TrimMaterial.DIAMOND;
        else if (atk != null && atk.hasEntry(name)) trimMat = TrimMaterial.REDSTONE;
        else return;

        PlayerInventory inv = player.getInventory();

        ItemStack boots = inv.getBoots();
        if (!isEmpty(boots) && !hasTrim(boots, trimMat))
            inv.setBoots(build(Material.DIAMOND_BOOTS, trimMat, true));

        ItemStack legs = inv.getLeggings();
        if (!isEmpty(legs) && !hasTrim(legs, trimMat))
            inv.setLeggings(build(Material.DIAMOND_LEGGINGS, trimMat, false));

        ItemStack chest = inv.getChestplate();
        if (!isEmpty(chest) && !hasTrim(chest, trimMat))
            inv.setChestplate(build(Material.DIAMOND_CHESTPLATE, trimMat, false));

        ItemStack helmet = inv.getHelmet();
        if (!isEmpty(helmet) && !hasTrim(helmet, trimMat))
            inv.setHelmet(build(Material.DIAMOND_HELMET, trimMat, false));
    }

    private static boolean hasTrim(ItemStack item, TrimMaterial expected) {
        if (item == null || !(item.getItemMeta() instanceof ArmorMeta meta)) return false;
        ArmorTrim trim = meta.getTrim();
        return trim != null
                && trim.getMaterial() == expected
                && trim.getPattern() == TrimPattern.RIB;
    }

    private static ItemStack build(Material type, TrimMaterial trimMat, boolean depthStrider) {
        ItemStack item = new ItemStack(type);
        ArmorMeta meta = (ArmorMeta) item.getItemMeta();
        meta.setTrim(new ArmorTrim(trimMat, TrimPattern.RIB));
        meta.addEnchant(Enchantment.PROTECTION, 4, false);
        meta.addEnchant(Enchantment.UNBREAKING, 3, false);
        if (depthStrider) meta.addEnchant(Enchantment.DEPTH_STRIDER, 3, false);
        item.setItemMeta(meta);
        return item;
    }
    private static boolean isEmpty(ItemStack item) {
        return item == null || item.getType().isAir();
    }
}