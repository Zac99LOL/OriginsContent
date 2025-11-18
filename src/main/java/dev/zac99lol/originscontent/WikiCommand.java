package dev.zac99lol.originscontent;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class WikiCommand {
    public static void init() {
        OriginsContent.LOGGER.info("Initialising /wiki command...");

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("wiki")
                .executes(context -> {
                    ClickEvent event = new ClickEvent(ClickEvent.Action.OPEN_URL, "https://origins.l3g85.club/");
                    context.getSource().sendFeedback(() -> Text.literal("Click ").formatted(Formatting.AQUA).append(Text.literal("[").formatted(Formatting.AQUA).append(Text.literal("Here").formatted(Formatting.DARK_AQUA).styled(style -> style.withClickEvent(event)).append(Text.literal("]").formatted(Formatting.AQUA).append(Text.literal(" To Open the SMP Wiki!").formatted(Formatting.AQUA))))), false);
                    return 1;
                }));
        });
    }
}
