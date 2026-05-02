package dev.zac99lol.originscontent.client;

import dev.zac99lol.originscontent.OriginsContent;
import net.fabricmc.api.ClientModInitializer;

public class OriginsContentClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        OriginsContent.LOGGER.info("Initialising OriginsContent on the client...");
    }
}
