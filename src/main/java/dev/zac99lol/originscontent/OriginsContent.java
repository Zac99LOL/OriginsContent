package dev.zac99lol.originscontent;

import dev.zac99lol.originscontent.command.MapCommand;
import dev.zac99lol.originscontent.command.WikiCommand;
import dev.zac99lol.originscontent.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OriginsContent implements ModInitializer {
    public static final String MOD_ID = "originscontent";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initialising OriginsContent...");

        LOGGER.info("Registering configs...");
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);

        LOGGER.info("Checking configs...");
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        if (config.wikiAddress.equals("CHANGEME") && config.wikiCommandEnabled) {
            throw new RuntimeException("[OriginsContent] Please set 'wikiAddress' in the config file before starting.");
        }
        if (config.mapAddress.equals("CHANGEMETOO") && config.mapCommandEnabled) {
            throw new RuntimeException("[OriginsContent] Please set 'mapAddress' in the config file before starting.");
        }

        ModItems.initialize();

        if (config.wikiCommandEnabled) { WikiCommand.init(); }
        if (config.mapCommandEnabled) { MapCommand.init(); }

        LOGGER.info("OriginsContent initialised!");
    }
}
