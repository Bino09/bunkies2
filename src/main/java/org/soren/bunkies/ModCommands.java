package org.soren.bunkies;


import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.soren.bunkies.managers.*;

public class ModCommands {


    public static void register(Commands commands) {
                FileConfiguration config = JavaPlugin.getPlugin(Bunkies2.class).getConfig();
                Bunkies2 plugin = JavaPlugin.getPlugin(Bunkies2.class);
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
                                                    LivesManager.setLives(player.getUniqueId(), x);
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
                        .build()

         );

    }



}

