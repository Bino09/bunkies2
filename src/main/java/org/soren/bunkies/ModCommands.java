package org.soren.bunkies;


import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
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
                        .build()
        );
    }



}

