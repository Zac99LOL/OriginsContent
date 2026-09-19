package dev.zac99lol.originscontent.command;

import dev.zac99lol.originscontent.OriginsContent;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class RainbowCommand {
    public static void init() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("rainbow")
            .requires(source -> source.getPlayer() != null && source.isExecutedByPlayer() && source.getPlayer().getGameProfile().getId().equals(OriginsContent.UUIDs.Zac99LOL))
            .executes(context -> {
                ServerPlayerEntity player = context.getSource().getPlayer();

                if (player == null) return 0;

                ItemStack stack = player.getStackInHand(player.getActiveHand());
                if (stack == null) {
                    context.getSource().sendError(Text.literal("You must be holding an item to run this command."));
                    return 0;
                }

                stack.getOrCreateNbt().putString("BarColor", "rainbow");
                return 1;
            }))));
    }
}
