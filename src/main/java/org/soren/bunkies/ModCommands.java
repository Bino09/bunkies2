package org.soren.bunkies;


import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

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
                        .then(Commands.literal("teamList")
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
                        .build()
        );
    }



}

