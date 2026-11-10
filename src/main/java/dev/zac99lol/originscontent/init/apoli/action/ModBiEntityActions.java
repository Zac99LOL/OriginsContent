package dev.zac99lol.originscontent.init.apoli.action;

import com.google.gson.JsonSyntaxException;
import dev.zac99lol.originscontent.OriginsContent;
import dev.zac99lol.originscontent.apoli.ConditionalChoiceAction;
import dev.zac99lol.originscontent.init.ModPackets;
import dev.zac99lol.originscontent.init.apoli.ModDataTypes;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import team.lodestar.lodestone.network.screenshake.PositionedScreenshakePacket;
import team.lodestar.lodestone.systems.easing.Easing;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ModBiEntityActions {
    Map<ActionFactory<Pair<Entity, Entity>>, Identifier> BIENTITY_ACTIONS = new LinkedHashMap<>();

    ActionFactory<Pair<Entity, Entity>> CONDITIONAL_CHOICE = createAction("conditional_choice", ConditionalChoiceAction.getFactory(ApoliDataTypes.BIENTITY_ACTION, ApoliDataTypes.BIENTITY_CONDITION));

    ActionFactory<Pair<Entity, Entity>> SCREENSHAKE = createAction("screenshake", new ActionFactory<>(OriginsContent.id("screenshake"),
        new SerializableData()
            .add("duration", SerializableDataTypes.INT)
            .add("intensity", ModDataTypes.INTENSITY)
            .add("easing", ModDataTypes.EASING, null)
            .add("start_easing", ModDataTypes.EASING, null)
            .add("end_easing", ModDataTypes.EASING, null)
            .add("fall_off_easing", ModDataTypes.EASING, Easing.LINEAR)
            .add("position", SerializableDataTypes.VECTOR)
            .add("fall_off_distance", SerializableDataTypes.FLOAT)
            .add("max_distance", SerializableDataTypes.FLOAT, 0F),
        (data, pair) -> {
            Entity actor = pair.getLeft();
            Entity target = pair.getRight();
            if (!(target instanceof ServerPlayerEntity player)) return;
            PacketByteBuf buf = PacketByteBufs.create();
            PositionedScreenshakePacket packet = new PositionedScreenshakePacket(
                data.getInt("duration"),
                actor.getPos(),
                data.getFloat("fall_off_distance"),
                data.getFloat("max_distance") != 0 ? data.getFloat("max_distance") : player.distanceTo(actor) + 1,
                data.get("fall_off_easing")
            );

            float[] intensity = data.get("intensity");
            switch (intensity.length) {
                case 1: packet.setIntensity(intensity[0]);
                case 2: packet.setIntensity(intensity[0], intensity[1]);
                case 3: packet.setIntensity(intensity[0], intensity[1], intensity[2]);
            }

            Easing easing = data.get("easing");
            Easing startEasing = data.get("start_easing");
            Easing endEasing = data.get("end_easing");

            if (easing == null) {
                if (startEasing == null && endEasing == null);
                else if (startEasing != null && endEasing != null) packet.setEasing(startEasing, endEasing);
                else throw new JsonSyntaxException("Specify either no easings, one easing, or BOTH start and end easings.");
            } else if (startEasing != null || endEasing != null) throw new JsonSyntaxException("Specify either no easings, one easing, or BOTH start and end easings.");
            else packet.setEasing(easing);

            packet.encode(buf);
            ServerPlayNetworking.send(player, ModPackets.POSITIONED_SCREENSHAKE, buf);
        }));

    private static ActionFactory<Pair<Entity, Entity>> createAction(String id, ActionFactory<Pair<Entity, Entity>> actionFactory) {
        BIENTITY_ACTIONS.put(actionFactory, OriginsContent.id(id));
        return actionFactory;
    }

    static void initialize() {
        BIENTITY_ACTIONS.keySet().forEach(actionFactory -> Registry.register(ApoliRegistries.BIENTITY_ACTION, BIENTITY_ACTIONS.get(actionFactory), actionFactory));
    }
}
