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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

import java.util.UUID;

public class OriginsContent implements ModInitializer {
    public static final class UUIDs {
        public static final UUID SlightlyShorter = UUID.fromString("bd6f0aa4-dc8b-4a44-b1e5-36b7b68cc255");
        public static final UUID Zac99LOL = UUID.fromString("20aaa00a-d216-4ed8-9a3b-bd8a5638210a");
        public static final UUID Astrulux = UUID.fromString("c90f16a6-60d2-4813-a6b4-15fc81971a03");
        public static final UUID Olified = UUID.fromString("6c09692f-aa94-4233-8328-a9ba62b8ff43");
        public static final UUID Lendhy0 = UUID.fromString("59e45737-8f6b-4ae4-9609-597665b9f77d");
    }

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
        Registry.register(ApoliRegistries.ENTITY_CONDITION, id("in_black_rain"), InBlackRainCondition.getFactory());
        ModPowers.init();
        ModActions.init();

        // other stuff
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

    public static boolean heldByUUID(ItemStack stack, UUID uuid) {
        if (!(stack.getHolder() instanceof PlayerEntity player)) return false;
        return player.getGameProfile().getId().equals(uuid);
    }
}
