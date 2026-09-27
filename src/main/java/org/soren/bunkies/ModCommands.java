package org.soren.bunkies;


import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.soren.bunkies.managers.LivesManager;
import org.soren.bunkies.managers.TeamManager;
import org.soren.bunkies.managers.playerManager;

public class ModCommands {

    public static void register(Commands commands) {
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
                        .then(Commands.literal("lives")
                                .executes(ctx -> {
                                    ctx.getSource().getSender().sendMessage(Component.text("You have: " + LivesManager.getLives(ctx.getSource().getSender().getServer().getPlayerUniqueId(ctx.getSource().getSender().getName())) + " Lives", TextColor.fromHexString("#f0bf3a")));
                                return 1;
                                }))
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

