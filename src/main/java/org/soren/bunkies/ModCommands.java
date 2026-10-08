package org.soren.bunkies;


import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.BlockPositionResolver;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.soren.bunkies.managers.*;

import java.util.List;
import java.util.Set;

public class ModCommands {


    public static void register(Commands commands, Bunkies2 plugin) {
                FileConfiguration config = JavaPlugin.getPlugin(Bunkies2.class).getConfig();

                commands.register(
                Commands.literal("soren")
                        .then(Commands.literal("genTeams")
                                .requires(src -> src.getSender().isOp())
                                .then(Commands.argument("x", IntegerArgumentType.integer(0))
                                        .executes(context -> {
                                            int x = context.getArgument("x", int.class);
                                            playerManager.genTeams(x);
                                            return  Command.SINGLE_SUCCESS;
                                        })))
                        .then(Commands.literal("stats")
                                .executes(ctx -> {
                                    Player player = (Player) ctx.getSource().getSender();
                                    StatManager.getStats(player);
                                return 1;
                              }))
                        .then(Commands.literal("StartGame")
                                .requires(src-> src.getSender().isOp())
                                .executes(ctx-> {
                                    if(GameManager.getGameStatus() == 0) {
                                        GameManager.startGame();
                                        
                                    }
                                    else {
                                        Player player = (Player)ctx.getSource().getSender();
                                        player.sendMessage(Component.text("Game Already Started!"));
                                    }
                                    
                                    return 1;
                                }))
                        .then(Commands.literal("EndGame")
                                .requires(src-> src.getSender().isOp())
                                .executes(ctx-> {
                                    if(GameManager.getGameStatus() == 1) {
                                        GameManager.endGame();
                                    }
                                    else {
                                        Player player = (Player)ctx.getSource().getSender();
                                        player.sendMessage(Component.text("Game Hasn't Started!"));
                                    }
                                    
                                    return 1;
                                }))
                        .then(Commands.literal("lives")
                                .executes(ctx -> {
                                    ctx.getSource().getSender().sendMessage(Component.text("You have: " + LivesManager.getLives(ctx.getSource().getSender().getServer().getPlayerUniqueId(ctx.getSource().getSender().getName())) + " Lives", TextColor.fromHexString("#f0bf3a")));
                                return 1;
                                }))
                        .then(Commands.literal("setLives")
                                .requires(src->src.getSender().isOp())
                                .then(Commands.argument("x", IntegerArgumentType.integer(0))
                                        .then(Commands.argument("player", ArgumentTypes.player())
                                                .executes(ctx->{

                                                    int x = ctx.getArgument("x", int.class);
                                                    PlayerSelectorArgumentResolver resolver = ctx.getArgument("player", PlayerSelectorArgumentResolver.class);
                                                    Player player = resolver.resolve(ctx.getSource()).getFirst();
                                                    LivesManager.setLives(player.getUniqueId(), x, player);
                                                    player.sendMessage(Component.text("Your lives have been set to: " + LivesManager.getLives(player.getUniqueId()), TextColor.fromHexString("#f0bf3a")));
                                                    return 1;

                                                }))))
                        .then(Commands.literal("setWarp")
                                .then(Commands.literal("def")
                                        .requires(src -> src.getSender().isOp())
                                        .executes(ctx -> {
                                            Player player = (Player) ctx.getSource().getSender();
                                            config.set("warps.def", player.getLocation());
                                            plugin.saveConfig();
                                            player.sendMessage(Component.text("Warp saved successfully"));
                                            return 1;
                                        }))
                                .then(Commands.literal("atk")
                                        .requires(src -> src.getSender().isOp())
                                        .executes(ctx -> {
                                            Player player = (Player) ctx.getSource().getSender();
                                            config.set("warps.atk", player.getLocation());
                                            plugin.saveConfig();
                                            player.sendMessage(Component.text("Warp saved successfully"));
                                            return 1;
                                        }))
                                .then(Commands.literal("Idef")
                                        .requires(src -> src.getSender().isOp())
                                        .executes(ctx -> {
                                            Player player = (Player) ctx.getSource().getSender();
                                            config.set("warps.def1", player.getLocation());
                                            plugin.saveConfig();
                                            player.sendMessage(Component.text("Warp saved successfully"));
                                            return 1;
                                        }))
                                .then(Commands.literal("Iatk")
                                        .requires(src -> src.getSender().isOp())
                                        .executes(ctx -> {
                                            Player player = (Player) ctx.getSource().getSender();
                                            config.set("warps.atk1", player.getLocation());
                                            plugin.saveConfig();
                                            player.sendMessage(Component.text("Warp saved successfully"));
                                            return 1;
                                        })))


                        .then(Commands.literal("teamlist")
                                .executes(ctx -> {
                                    playerManager.listTeams((Player) ctx.getSource().getSender());
                                    return 1;
                                }))
                        .then(Commands.literal("swapPlayer")
                                .requires(src -> src.getSender().isOp())
                                .then(Commands.argument("player", ArgumentTypes.player())
                                        .executes(ctx -> {
                                            PlayerSelectorArgumentResolver resolver = ctx.getArgument("player", PlayerSelectorArgumentResolver.class);
                                            Player player = resolver.resolve(ctx.getSource()).getFirst();
                                            TeamManager.swapPlayer(player);

                                            return 1;
                                        })))
                                .then(Commands.literal("AddPlayer")
                                        .requires(src -> src.getSender().isOp())
                                        .then(Commands.literal("atk")
                                                .then(Commands.argument("player", ArgumentTypes.player())

                                                    .executes(ctx -> {
                                                        PlayerSelectorArgumentResolver resolver = ctx.getArgument("player", PlayerSelectorArgumentResolver.class);
                                                        Player player = resolver.resolve((ctx.getSource())).getFirst();
                                                        TeamManager.addAtk(player);
                                                        playerManager.addAtk(player);

                                                        return 1;
                                                    })))
                                        .then(Commands.literal("def")
                                                .then(Commands.argument("player", ArgumentTypes.player())
                                                        .executes(ctx -> {
                                                            PlayerSelectorArgumentResolver resolver = ctx.getArgument("player", PlayerSelectorArgumentResolver.class);
                                                            Player player = resolver.resolve((ctx.getSource())).getFirst();
                                                            TeamManager.addDef(player);
                                                            playerManager.addDef(player);
                                                                    
                                                            return 1;
                                                        }))))
                                .then(Commands.literal("kit")
                                        .then(Commands.literal("save")
                                                .then(Commands.argument("kit name", StringArgumentType.word())
                                                        .executes(ctx -> {
                                                                if (!(ctx.getSource().getSender() instanceof Player)) {
                                                                    return 0;
                                                                }
                                                                Player player = (Player) ctx.getSource().getSender();
                                                                String name = ctx.getArgument("kit name", String.class);
                                                                Component message = KitsManager.saveKit(player, name, plugin);
                                                                ctx.getSource().getSender().sendMessage(message);
                                                                return 1;
                                                        })))
                                        .then(Commands.literal("load")
                                                .then(Commands.argument("kit name", StringArgumentType.word())
                                                        .suggests((ctx, builder) -> {
                                                            List<String> kits = plugin.getConfig().getStringList("kits.kitsList");

                                                            for (String kit : kits) {
                                                                builder.suggest(kit);
                                                            }
                                                            return builder.buildFuture();
                                                        })
                                                        .executes(ctx -> {
                                                                if (!(ctx.getSource().getSender() instanceof Player)) {
                                                                return 0;
                                                                }
                                                            Location player = ctx.getSource().getLocation();
                                                            Location atk1 = config.getLocation("zones.atk1");
                                                            Location atk2 = config.getLocation("zones.atk2");
                                                            Location def1 = config.getLocation("zones.def1");
                                                            Location def2 = config.getLocation("zones.def2");
                                                            Player player1 = ((Player) ctx.getSource().getSender()).getPlayer();
                                                            if (playerManager.getAtk().contains(player1)) {
                                                                if((player.getBlockX() <= atk1.blockX() && player.getBlockX() >= atk2.blockX()) || (player.getBlockX() >= atk1.blockX() && player.getBlockX() <= atk2.blockX())) {
                                                                    if((player.getBlockY() <= atk1.blockY() && player.getBlockY() >= atk2.blockY()) || (player.getBlockY() >= atk1.blockY() && player.getBlockY() <= atk2.blockY())) {
                                                                        if((player.getBlockX() <= atk1.blockX() && player.getBlockX() >= atk2.blockX()) || (player.getBlockX() >= atk1.blockX() && player.getBlockX() <= atk2.blockX())) {
                                                                            String name = ctx.getArgument("kit name", String.class);
                                                                            Component message = KitsManager.loadKit(player1, name, plugin);
                                                                            try {
                                                                                modListener.checkArmour(player1);
                                                                            } catch (Exception e) {
                                                                                throw new RuntimeException(e);

                                                                            }
                                                                            ctx.getSource().getSender().sendMessage(message);
                                                                        }
                                                                        else {
                                                                            player1.sendMessage(Component.text("Get in the Rekit Zone Chud"));

                                                                    }
                                                                    else {
                                                                        player1.sendMessage(Component.text("Get in the Rekit Zone Chud"));

                                                                }
                                                                else {
                                                                    player1.sendMessage(Component.text("Get in the Rekit Zone Chud"));
                                                                }
                                                            }
                                                            if (playerManager.getDef().contains(player1)) {
                                                                if((player.getBlockX() <= def1.blockX() && player.getBlockX() >= def2.blockX()) || (player.getBlockX() >= def1.blockX() && player.getBlockX() <= def2.blockX())) {
                                                                    if((player.getBlockY() <= def1.blockY() && player.getBlockY() >= def2.blockY()) || (player.getBlockY() >= def1.blockY() && player.getBlockY() <= def2.blockY())) {
                                                                        if((player.getBlockX() <= def1.blockX() && player.getBlockX() >= def2.blockX()) || (player.getBlockX() >= def1.blockX() && player.getBlockX() <= def2.blockX())) {
                                                                            String name = ctx.getArgument("kit name", String.class);
                                                                            Component message = KitsManager.loadKit(player1, name, plugin);
                                                                            ctx.getSource().getSender().sendMessage(message);

                                                                        }
                                                                        else {
                                                                            player1.sendMessage(Component.text("Get in the Rekit Zone Chud"));

                                                                    }
                                                                    else {
                                                                            player1.sendMessage(Component.text("Get in the Rekit Zone Chud"));

                                                                    }

                                                                }
                                                                else {
                                                                    player1.sendMessage(Component.text("Get in the Rekit Zone Chud"));
                                                                }
                                                            }
                                                            else {
                                                                            player1.sendMessage(Component.text("Get in the Rekit Zone Chud"));

                                                                    }



                                                                return 1;
                                                        })))
                                        .then(Commands.literal("list")
                                                .executes(ctx -> {
                                                        if (!(ctx.getSource().getSender() instanceof Player)) {
                                                        return 0;
                                                        }
                                                        List<String> kitsList = KitsManager.listKits(plugin);
                                                        String len = Integer.toString(kitsList.size());
                                                        if (len.equals("0")) {
                                                            Component message = Component.text("There are no kits saved").color(NamedTextColor.RED);
                                                            ctx.getSource().getSender().sendMessage(message);
                                                            return 1;
                                                        } else if (len.equals("1")) {
                                                            Component message = Component.text("There is 1 kit saved").color(NamedTextColor.GREEN);
                                                            ctx.getSource().getSender().sendMessage(message);
                                                        } else {
                                                            Component message = Component.text("There are " + len + " saved kits:").color(NamedTextColor.GREEN);
                                                            ctx.getSource().getSender().sendMessage(message);
                                                        }
                                                        for (int i = 0; i< kitsList.size(); i++) {
                                                                int index = i+1;
                                                                Component message = Component.text("[" + index + "] " + kitsList.get(i)).color(NamedTextColor.GREEN);
                                                                ctx.getSource().getSender().sendMessage(message);
                                                        }
                                                        return 1;
                                                }))
                                        .then(Commands.literal("delete")
                                                .then(Commands.argument("kitName", StringArgumentType.word())
                                                        .suggests((ctx, builder) -> {
                                                                Player player = (Player) ctx.getSource().getSender();
                                                                String uuid = player.getUniqueId().toString();
                                                                List<String> kits = plugin.getConfig().getStringList("kits.kitsList");
                                                                for (String kit : kits) {
                                                                        if (ctx.getSource().getSender().isOp()) {
                                                                                builder.suggest(kit);
                                                                        } else {
                                                                                if (plugin.getConfig().getString("kits.kits."+kit+".player").equals(uuid)) {
                                                                                        builder.suggest(kit);
                                                                                }
                                                                        }
                                                                }
                                                                return builder.buildFuture();
                                                        })
                                                        .executes(ctx -> {
                                                                if (!(ctx.getSource().getSender() instanceof Player)) {
                                                                return 0;
                                                                }
                                                                Player player = (Player) ctx.getSource().getSender();
                                                                String name = ctx.getArgument("kitName", String.class);
                                                                Boolean op = ctx.getSource().getSender().isOp();
                                                                Component message = KitsManager.deleteKit(player, name, op, plugin);
                                                                ctx.getSource().getSender().sendMessage(message);
                                                                return 1;
                                                                })))
                                        .then(Commands.literal("setZone")
                                                .requires(src->src.getSender().isOp())
                                                .then(Commands.literal("atk1")
                                                        .executes(ctx->{

                                                            Player player = (Player) ctx.getSource().getSender();
                                                            Location atk1  = player.getLocation();
                                                            config.set("zones.atk1", atk1);
                                                            plugin.saveConfig();
                                                            player.sendMessage(Component.text("Point saved successfully"));
                                                            return 1;
                                                        }))
                                                .then(Commands.literal("atk2")
                                                        .executes(ctx->{

                                                            Player player = (Player) ctx.getSource().getSender();
                                                            Location atk2  = player.getLocation();
                                                            config.set("zones.atk2", atk2);
                                                            plugin.saveConfig();
                                                            player.sendMessage(Component.text("Point saved successfully"));


                                                            return 1;
                                                        }))

                                                .then(Commands.literal("def1")
                                                        .executes(ctx->{

                                                            Player player = (Player) ctx.getSource().getSender();
                                                            Location def1  = player.getLocation();
                                                            config.set("zones.def1", def1);
                                                            plugin.saveConfig();
                                                            player.sendMessage(Component.text("Point saved successfully"));
                                                            return 1;
                                                        }))
                                                .then(Commands.literal("def2")
                                                        .executes(ctx->{

                                                            Player player = (Player) ctx.getSource().getSender();
                                                            Location def2  = player.getLocation();
                                                            config.set("zones.def2", def2);
                                                            plugin.saveConfig();
                                                            player.sendMessage(Component.text("Point saved successfully"));


                                                            return 1;
                                                        })))
                                        )

                        .build()

         );

    }



}

