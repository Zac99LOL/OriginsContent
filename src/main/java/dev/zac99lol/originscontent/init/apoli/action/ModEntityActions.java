package dev.zac99lol.originscontent.init.apoli.action;

import com.google.gson.JsonSyntaxException;
import dev.zac99lol.originscontent.OriginsContent;
import dev.zac99lol.originscontent.api.DefileApi;
import dev.zac99lol.originscontent.apoli.ConditionalChoiceAction;
import dev.zac99lol.originscontent.init.ModPackets;
import dev.zac99lol.originscontent.init.apoli.ModDataTypes;
import doctor4t.defile.cca.DefileComponents;
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
import net.minecraft.world.World;
import team.lodestar.lodestone.network.screenshake.PositionedScreenshakePacket;
import team.lodestar.lodestone.network.screenshake.ScreenshakePacket;
import team.lodestar.lodestone.systems.easing.Easing;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public interface ModEntityActions {
    Map<ActionFactory<Entity>, Identifier> ENTITY_ACTIONS = new LinkedHashMap<>();

    ActionFactory<Entity> OPEN_STEAM_GAME = createAction("open_steam_game", new ActionFactory<>(OriginsContent.id("open_steam_game"),
        new SerializableData().add("id", SerializableDataTypes.INT),
        (data, entity) -> {
            if (entity instanceof ServerPlayerEntity player) {
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeVarInt(data.getInt("id"));

                ServerPlayNetworking.send(player, ModPackets.OPEN_STEAM_GAME, buf);
            }
        }));

    ActionFactory<Entity> DEFILE_DIVE = createAction("defile.dive", new ActionFactory<>(OriginsContent.id("defile.dive"),
        new SerializableData(),
        (data, entity) -> DefileComponents.INKLING.get(entity).dive()));

    ActionFactory<Entity> DEFILE_EMERGE = createAction("defile.emerge", new ActionFactory<>(OriginsContent.id("defile.emerge"),
        new SerializableData(),
        (data, entity) -> DefileComponents.INKLING.get(entity).rise()));

    ActionFactory<Entity> CRASH = createAction("crash", new ActionFactory<>(OriginsContent.id("crash"),
        new SerializableData().add("error", SerializableDataTypes.STRING, "Direct all complaints to Harry LLC."),
        (data, entity) -> {
            if (!(entity instanceof ServerPlayerEntity player)) return;
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeString(data.getString("error"));

            ServerPlayNetworking.send(player, ModPackets.CRASH_GAME, buf);
        }));

    ActionFactory<Entity> SET_BLACK_RAIN_DURATION = createAction("defile.set_black_rain_duration", new ActionFactory<>(OriginsContent.id("defile.set_black_rain_duration"),
        new SerializableData().add("ticks", SerializableDataTypes.INT),
        (data, entity) -> DefileApi.setBlackRain(entity.getWorld(), data.getInt("ticks"))));

    ActionFactory<Entity> TRIGGER_THUNDER = createAction("defile.trigger_thunder", new ActionFactory<>(OriginsContent.id("defile.trigger_thunder"),
        new SerializableData(),
        (data, entity) -> DefileApi.triggerThunder(entity.getWorld())));

    @SuppressWarnings("unchecked")
    ActionFactory<Entity> ACTION_AS_NEAREST = createAction("action_as_nearest", new ActionFactory<>(OriginsContent.id("action_as_nearest"),
        new SerializableData().add("action", ApoliDataTypes.ENTITY_ACTION),
        (data, entity) -> {
            World world = entity.getWorld();

            Entity nearest = null;
            double nearestSq = 64 * 64;

            for (Entity e : world.getOtherEntities(entity, entity.getBoundingBox().expand(64))) {
                double distSq = entity.squaredDistanceTo(e.getPos());
                if (distSq < nearestSq) {
                    nearestSq = distSq;
                    nearest = entity;
                }
            }

            if (nearest != null) ((Consumer<Entity>) data.get("action")).accept(nearest);
        })); // as of writing this i have just realised it already exists! fuck my life

    ActionFactory<Entity> CONDITIONAL_CHOICE = createAction("conditional_choice", ConditionalChoiceAction.getFactory(ApoliDataTypes.ENTITY_ACTION, ApoliDataTypes.ENTITY_CONDITION));

    ActionFactory<Entity> DISPLAY_TOAST = createAction("display_toast", new ActionFactory<>(OriginsContent.id("display_toast"),
        new SerializableData().add("title", SerializableDataTypes.STRING).add("description", SerializableDataTypes.STRING, null),
        (data, entity) -> {
            if (!(entity instanceof ServerPlayerEntity player)) return;
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeString(data.getString("title"));
            buf.writeString(data.isPresent("description") ? data.getString("description") : data.getString("title"));
            ServerPlayNetworking.send(player, ModPackets.DISPLAY_TOAST, buf);
        }));

    ActionFactory<Entity> SCREENSHAKE = createAction("screenshake", new ActionFactory<>(OriginsContent.id("screenshake"),
        new SerializableData()
            .add("duration", SerializableDataTypes.INT)
            .add("intensity", ModDataTypes.INTENSITY)
            .add("easing", ModDataTypes.EASING, null)
            .add("start_easing", ModDataTypes.EASING, null)
            .add("end_easing", ModDataTypes.EASING, null),
        (data, entity) -> {
            if (!(entity instanceof ServerPlayerEntity player)) return;
            PacketByteBuf buf = PacketByteBufs.create();
            ScreenshakePacket packet = new ScreenshakePacket(data.getInt("duration"));

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
            ServerPlayNetworking.send(player, ModPackets.SCREENSHAKE, buf);
        }));

    ActionFactory<Entity> POSITIONED_SCREENSHAKE = createAction("positioned_screenshake", new ActionFactory<>(OriginsContent.id("positioned_screenshake"),
        new SerializableData()
            .add("duration", SerializableDataTypes.INT)
            .add("intensity", ModDataTypes.INTENSITY)
            .add("easing", ModDataTypes.EASING, null)
            .add("start_easing", ModDataTypes.EASING, null)
            .add("end_easing", ModDataTypes.EASING, null)
            .add("fall_off_easing", ModDataTypes.EASING, Easing.LINEAR)
            .add("position", SerializableDataTypes.VECTOR)
            .add("fall_off_distance", SerializableDataTypes.FLOAT)
            .add("max_distance", SerializableDataTypes.FLOAT),
        (data, entity) -> {
            if (!(entity instanceof ServerPlayerEntity player)) return;
            PacketByteBuf buf = PacketByteBufs.create();
            PositionedScreenshakePacket packet = new PositionedScreenshakePacket(
                data.getInt("duration"),
                data.get("position"),
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
            ServerPlayNetworking.send(player, ModPackets.POSITIONED_SCREENSHAKE, buf);
        }));

    private static ActionFactory<Entity> createAction(String id, ActionFactory<Entity> action) {
        ENTITY_ACTIONS.put(action, OriginsContent.id(id));
        return action;
    }

    static void initialize() {
        ENTITY_ACTIONS.keySet().forEach(action -> Registry.register(ApoliRegistries.ENTITY_ACTION, ENTITY_ACTIONS.get(action), action));
    }
}
