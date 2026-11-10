package dev.zac99lol.originscontent.duels.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class SuicideCommand {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("suicide")
            .requires(ServerCommandSource::isExecutedByPlayer)
            .executes(context -> {
                if (context.getSource().getPlayer() == null) return 0;
                context.getSource().getPlayer().kill();
                return 1;
            })));
    }
}
