package dev.zac99lol.originscontent.command;

import dev.zac99lol.originscontent.OriginsContent;
import dev.zac99lol.originscontent.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class WikiCommand {
    public static void init() {
        OriginsContent.LOGGER.info("Registering /wiki command...");

        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("wiki")
            .executes(context -> {
                ClickEvent event = new ClickEvent(ClickEvent.Action.OPEN_URL, config.wikiAddress);
                context.getSource().sendFeedback(() -> Text.literal("Click ").formatted(Formatting.AQUA)
                    .append(Text.literal("[").formatted(Formatting.AQUA)
                        .append(Text.literal("Here").formatted(Formatting.DARK_AQUA).styled(style -> style.withClickEvent(event))
                            .append(Text.literal("]").formatted(Formatting.AQUA)
                                .append(Text.literal(" To Open the SMP Wiki!").formatted(Formatting.AQUA))))), false);
                return 1;
            })));
    }
}
