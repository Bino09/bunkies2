package org.soren.bunkies.managers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.soren.bunkies.Bunkies2;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class KitsManager {

    public static String ItemStackToBase64 (ItemStack item) {
        if (item == null || item.isEmpty()) {
            return null;
        }
        byte[] Bytes = item.serializeAsBytes();
        String base64 = Base64.getEncoder().encodeToString(Bytes);
        return base64;
    }

    public static ItemStack Base64ToItemStack (String base64) {
        if (base64 == null || base64.isEmpty()) {
            return null;
        }
        byte[] Bytes = Base64.getDecoder().decode(base64);
        ItemStack item = ItemStack.deserializeBytes(Bytes);
        return item;
    }

    public static Component saveKit(Player player, String name, Bunkies2 plugin) {
        UUID uuidObj = player.getUniqueId();
        String uuid = uuidObj.toString();
        if (plugin.getConfig().getInt("kits.users."+uuid, 0)>=plugin.getConfig().getInt("kits.settings.max-kits-per-player")) {
            Component message = Component.text("You cannot create any more kits: maximum quantity allowed per player is "+plugin.getConfig().getInt("kits.settings.max-kits-per-player")).color(NamedTextColor.RED);
            return message;
        }
        if (plugin.getConfig().getStringList("kits.kitsList").contains(name)) {
            Component message = Component.text("Kit "+name+" has already been saved, try another name").color(NamedTextColor.RED);
            return message;
        }
        PlayerInventory inv = player.getInventory();

        ItemStack helmet = inv.getHelmet();
        ItemStack chestplate = inv.getChestplate();
        ItemStack leggins = inv.getLeggings();
        ItemStack boots = inv.getBoots();
        ItemStack offHand = inv.getItemInOffHand();

        String helmet64 = ItemStackToBase64(helmet);
        String chestplate64 = ItemStackToBase64(chestplate);
        String leggins64 = ItemStackToBase64(leggins);
        String boots64 = ItemStackToBase64(boots);
        String offHand64 = ItemStackToBase64(offHand);

        plugin.getConfig().set("kits.kits."+name+".helmet", helmet64);
        plugin.getConfig().set("kits.kits."+name+".chestplate", chestplate64);
        plugin.getConfig().set("kits.kits."+name+".leggins", leggins64);
        plugin.getConfig().set("kits.kits."+name+".boots", boots64);
        plugin.getConfig().set("kits.kits."+name+".offHand", offHand64);

        for (int i = 0; i<36; i++) {
            ItemStack item = inv.getItem(i);
            String item64 = ItemStackToBase64(item);
            plugin.getConfig().set("kits.kits."+name+"."+i, item64);
        }

        plugin.getConfig().set("kits.kits."+name+".player", uuid);
        int num = plugin.getConfig().getInt("kits.users."+uuid, 0);
        plugin.getConfig().set("kits.users."+uuid, num+1);
        List<String> kitsList = plugin.getConfig().getStringList("kits.kitsList");
        kitsList.add(name);
        plugin.getConfig().set("kits.kitsList", kitsList);
        plugin.saveConfig();
        return(Component.text("Kit "+name+" saved correctly").color(NamedTextColor.GREEN));
    }

    public static Component loadKit(Player player, String name, Bunkies2 plugin) {
        UUID uuidObj = player.getUniqueId();
        String uuid = uuidObj.toString();
        if (plugin.getConfig().getString("kits.kits."+name+".player", "noUUID").equals("noUUID")) {
            Component message = Component.text("Kit "+name+" does not exist").color(NamedTextColor.RED);
            return message;
        }
        PlayerInventory inv = player.getInventory();

        String helmet64 = plugin.getConfig().getString("kits.kits."+name+".helmet");
        String chestplate64 = plugin.getConfig().getString("kits.kits."+name+".chestplate");
        String leggins64 = plugin.getConfig().getString("kits.kits."+name+".leggins");
        String boots64 = plugin.getConfig().getString("kits.kits."+name+".boots");
        String offHand64 = plugin.getConfig().getString("kits.kits."+name+".offHand");

        ItemStack helmet = Base64ToItemStack(helmet64);
        ItemStack chestplate = Base64ToItemStack(chestplate64);
        ItemStack leggins = Base64ToItemStack(leggins64);
        ItemStack boots = Base64ToItemStack(boots64);
        ItemStack offHand = Base64ToItemStack(offHand64);

        inv.setHelmet(helmet);
        inv.setChestplate(chestplate);
        inv.setLeggings(leggins);
        inv.setBoots(boots);
        inv.setItemInOffHand(offHand);

        for (int i = 0; i<36; i++) {
            String item64 = plugin.getConfig().getString("kits.kits."+name+"."+i);
            ItemStack item = Base64ToItemStack(item64);
            inv.setItem(i, item);
        }
        return(Component.text("Kit "+name+" loaded successfully").color(NamedTextColor.GREEN));
    }

    public static List<String> listKits(Bunkies2 plugin) {
        List<String> kitsList = plugin.getConfig().getStringList("kits.kitsList");
        return kitsList;
    }

    public static Component deleteKit(Player player, String name, Boolean op, Bunkies2 plugin) {
        if (!op) {
            if (!plugin.getConfig().getString("kits.kits."+name+".player").equals(player.getUniqueId().toString())) {
                Component message = Component.text("You do not own kit "+name+", you cannot delete it").color(NamedTextColor.RED);
                return message;
            }
        }
        if (plugin.getConfig().getString("kits.kits."+name+".player", "noUUID").equals("noUUID")) {
            Component message = Component.text("Kit "+name+" does not exist").color(NamedTextColor.RED);
            return message;
        }
        String uuid = plugin.getConfig().getString("kits.kits."+name+".player");
        int num = plugin.getConfig().getInt("kits.users."+uuid);
        plugin.getConfig().set("kits.users."+uuid, num-1);
        List<String> kitsList = plugin.getConfig().getStringList("kits.kitsList");
        kitsList.remove(name);
        plugin.getConfig().set("kits.kitsList", kitsList);
        plugin.getConfig().set("kits.kits."+name, null);
        plugin.saveConfig();
        return (Component.text("Kit "+name+" deleted successfully").color(NamedTextColor.GREEN));
    }
}