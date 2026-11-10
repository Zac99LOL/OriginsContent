package dev.zac99lol.originscontent.init.apoli.action;

import com.google.gson.JsonSyntaxException;
import dev.zac99lol.originscontent.OriginsContent;
import dev.zac99lol.originscontent.api.DefileApi;
import dev.zac99lol.originscontent.apoli.ConditionalChoiceAction;
import dev.zac99lol.originscontent.init.ModPackets;
import dev.zac99lol.originscontent.init.apoli.ModDataTypes;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Triple;
import team.lodestar.lodestone.network.screenshake.PositionedScreenshakePacket;
import team.lodestar.lodestone.systems.easing.Easing;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ModBlockActions {
    Map<ActionFactory<Triple<World, BlockPos, Direction>>, Identifier> BLOCK_ACTIONS = new LinkedHashMap<>();

    ActionFactory<Triple<World, BlockPos, Direction>> CONDITIONAL_CHOICE = createAction("conditional_choice", ConditionalChoiceAction.getFactory(ApoliDataTypes.BLOCK_ACTION, ApoliDataTypes.BLOCK_CONDITION, triple -> new CachedBlockPosition(triple.getLeft(), triple.getMiddle(), true)));

    ActionFactory<Triple<World, BlockPos, Direction>> TRIGGER_ECLIPSE = createAction("defile.trigger_eclipse", new ActionFactory<>(OriginsContent.id("defile.trigger_eclipse"),
        new SerializableData(),
        (data, block) -> DefileApi.startEclipse(block.getLeft(), block.getMiddle())));

    ActionFactory<Triple<World, BlockPos, Direction>> SCREENSHAKE_AROUND = createAction("screenshake_around", new ActionFactory<>(OriginsContent.id("screenshake_around"),
        new SerializableData()
            .add("radius", SerializableDataTypes.DOUBLE)
            .add("duration", SerializableDataTypes.INT)
            .add("intensity", ModDataTypes.INTENSITY)
            .add("easing", ModDataTypes.EASING, null)
            .add("start_easing", ModDataTypes.EASING, null)
            .add("end_easing", ModDataTypes.EASING, null)
            .add("fall_off_easing", ModDataTypes.EASING, Easing.LINEAR)
            .add("fall_off_distance", SerializableDataTypes.FLOAT)
            .add("max_distance", SerializableDataTypes.FLOAT),
        (data, triple) -> {
            if (!(triple.getLeft() instanceof ServerWorld serverWorld)) return;
            PacketByteBuf buf = PacketByteBufs.create();
            PositionedScreenshakePacket packet = new PositionedScreenshakePacket(
                data.getInt("duration"),
                triple.getMiddle().toCenterPos(),
                data.getFloat("fall_off_distance"),
                data.getFloat("max_distance"),
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
            PlayerLookup.around(serverWorld, triple.getMiddle().toCenterPos(), data.getDouble("radius")).forEach(player -> ServerPlayNetworking.send(player, ModPackets.POSITIONED_SCREENSHAKE, buf));
        }));

    private static ActionFactory<Triple<World, BlockPos, Direction>> createAction(String id, ActionFactory<Triple<World, BlockPos, Direction>> action) {
        BLOCK_ACTIONS.put(action, OriginsContent.id(id));
        return action;
    }

    static void initialize() {
        BLOCK_ACTIONS.keySet().forEach(action -> Registry.register(ApoliRegistries.BLOCK_ACTION, BLOCK_ACTIONS.get(action), action));
    }
}
