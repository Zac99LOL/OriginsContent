package dev.zac99lol.originscontent;

import dev.zac99lol.originscontent.command.MapCommand;
import dev.zac99lol.originscontent.command.WikiCommand;
import dev.zac99lol.originscontent.condition.InBlackRainCondition;
import dev.zac99lol.originscontent.config.ModConfig;
import dev.zac99lol.originscontent.power.*;
import io.github.apace100.apoli.registry.ApoliRegistries;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

public class OriginsContent implements ModInitializer {
    public static final String MOD_ID = "originscontent";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initialising OriginsContent...");

        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);

        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        if (config.wikiAddress.equals("CHANGEME") && config.wikiCommandEnabled) {
            throw new IllegalStateException("[OriginsContent] Please set 'wikiAddress' in the config file before starting.");
        }
        if (config.mapAddress.equals("CHANGEMETOO") && config.mapCommandEnabled) {
            throw new IllegalStateException("[OriginsContent] Please set 'mapAddress' in the config file before starting.");
        }

        // vanilla registering
        ModItems.initialize();

        // origins / apoli shit
        Registry.register(ApoliRegistries.ENTITY_CONDITION, new Identifier(MOD_ID, "in_black_rain"), InBlackRainCondition.getFactory());
        ModPowers.init();

        // other stuff
        CancelInventoryActionsPower.init(); // cancel_inventory_actions power
        BackWeaponInteractionGuard.init(); // back slot stuff

        // commands
        if (config.wikiCommandEnabled) { WikiCommand.init(); }
        if (config.mapCommandEnabled) { MapCommand.init(); }
        suppressOriginsMathLogging();
    }

    public static Identifier id(String id) {
        return Identifier.of(MOD_ID, id);
    }

    private void suppressOriginsMathLogging() {
        final String loggerName = "origins-math"; // matches OriginsMath.MOD_ID exactly

        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();

        LoggerConfig loggerConfig = config.getLoggerConfig(loggerName);

        if (!loggerConfig.getName().equals(loggerName)) {
            loggerConfig = new LoggerConfig(loggerName, Level.WARN, true);
            config.addLogger(loggerName, loggerConfig);
        } else {
            loggerConfig.setLevel(Level.WARN);
        }

        ctx.updateLoggers();
    }
}
