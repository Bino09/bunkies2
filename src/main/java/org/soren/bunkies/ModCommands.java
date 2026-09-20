package org.soren.bunkies;


import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

public class ModCommands {

    public static void register(Commands commands) {
        commands.register(
                Commands.literal("soren")
                        .then(Commands.literal("genTeams")
                                .requires(src -> src.getSender().hasPermission("bunkies.genteams"))
                                .then(Commands.argument("x", IntegerArgumentType.integer(0))
                                        .executes(ModCommands::genTeamsCommand)))
                        .build()
        );
    }

    public static int genTeamsCommand(CommandContext<CommandSourceStack> ctx) {
        int x = IntegerArgumentType.getInteger(ctx, "x");
        playerManager.genTeams(x);
        return Command.SINGLE_SUCCESS;
    }
}

