package dev.zac99lol.originscontent;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class MapCommand {
    public static void init() {
        OriginsContent.LOGGER.info("Initialising /map command...");

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("map")
                .executes(context -> {
                    ClickEvent event = new ClickEvent(ClickEvent.Action.OPEN_URL, "http://37.10.104.9:9547/");
                    context.getSource().sendFeedback(() -> Text.literal("Click ").formatted(Formatting.AQUA).append(Text.literal("[").formatted(Formatting.AQUA).append(Text.literal("Here").formatted(Formatting.DARK_AQUA).styled(style -> style.withClickEvent(event)).append(Text.literal("]").formatted(Formatting.AQUA).append(Text.literal(" To Open the SMP Map!").formatted(Formatting.AQUA))))), false);
                    return 1;
                }));
        });
    }
}
