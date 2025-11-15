package dev.zac99lol.originscontent;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;

public class WikiCommand {
    public static void init() {
        OriginsContent.LOGGER.info("Initialising /wiki command...");

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("wiki")
                .executes(context -> {
                    context.getSource().sendFeedback(() -> Text.literal("Click me to open the wiki.").styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.youtube.com/watch?v=dQw4w9WgXcQ")).withUnderline(true)), false);
                    return 1;
                }));
        });
    }
}
