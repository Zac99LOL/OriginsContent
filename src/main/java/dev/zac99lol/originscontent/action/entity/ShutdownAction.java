package dev.zac99lol.originscontent.action.entity;

import dev.zac99lol.originscontent.OriginsContent;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class ShutdownAction {
    public static ActionFactory<Entity> getFactory() {
        return new ActionFactory<>(OriginsContent.id("shutdown"), new SerializableData(), (data, entity) -> {
            if (entity instanceof ServerPlayerEntity player) {
                PacketByteBuf buf = PacketByteBufs.create();

                ServerPlayNetworking.send(player, OriginsContent.id("shutdown_packet"), buf);
            }
        });
    }
}
