package dev.zac99lol.originscontent.power;

import dev.zac99lol.originscontent.OriginsContent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OverrideHotbarPower extends Power {
    private final HotbarSlotConfig[] overrides = new HotbarSlotConfig[10];
    private int oldSelectedSlot = 0;

    public OverrideHotbarPower(PowerType<?> type, LivingEntity entity, HotbarSlotConfig[] overrides) {
        super(type, entity);
        System.arraycopy(overrides, 0, this.overrides, 0, 10);
    }

    @Nullable
    public ItemStack getOverride(int i) {
        HotbarSlotConfig config = overrides[i];
        if (config == null) return null;
        HotbarOverride active = config.resolveActive(entity);
        return active != null ? active.getStack() : null;
    }

    @Nullable
    public ItemStack getOffhandOverride() {
        return getOverride(10);
    }

    /*
    public void setOverride(int i, @Nullable ItemStack stack) {
        PowerHolderComponent.KEY.sync(entity);
        overrides[i].stack = stack;
    }

    public void setOffhandOverride(@Nullable ItemStack stack) {
        overrides[9].stack = stack;
    }
    */

    @Override
    public boolean shouldTick() {
        return true;
    }

    @Override
    public void tick() {
        if (!(entity instanceof PlayerEntity player)) return;
        for (HotbarSlotConfig override : overrides) {
            override.tick(entity);
        }

        if (!player.getWorld().isClient) {
            int selectedSlot = player.getInventory().selectedSlot;
            if (oldSelectedSlot != selectedSlot) {
                oldSelectedSlot = selectedSlot;
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeUuid(player.getUuid());
                buf.writeVarInt(selectedSlot);
                for (ServerPlayerEntity tracking : PlayerLookup.tracking(player)) {
                    ServerPlayNetworking.send(tracking, OriginsContent.id("sync_selected_slot_packet"), buf);
                }
            }
        }
    }

    public static class HotbarOverride {
        private final ItemStack stack;
        private final ConditionFactory<LivingEntity>.Instance condition;

        HotbarOverride(ItemStack stack, ConditionFactory<LivingEntity>.Instance condition) {
            this.stack = stack;
            this.condition = condition;
        }

        public ItemStack getStack() {
            return condition == null ? stack : (condition.test((LivingEntity) stack.getHolder()) ? stack : null);
        }

        public ItemStack getStackNoMatterWhat() {
            return stack;
        }

        public ConditionFactory<LivingEntity>.Instance getCondition() {
            return condition;
        }

        public void tick(LivingEntity entity) {
            if (stack != null && !stack.isEmpty()) {
                stack.inventoryTick(entity.getWorld(), entity, -1, false);
            }
        }
    }

    public static class HotbarSlotConfig {
        private final List<HotbarOverride> variants;

        public HotbarSlotConfig(List<HotbarOverride> variants) {
            this.variants = variants;
        }

        @Nullable
        public HotbarOverride resolveActive(LivingEntity entity) {
            for (HotbarOverride variant : variants) {
                if (variant.getCondition() == null || variant.getCondition().test(entity)) {
                    return variant;
                }
            }
            return null;
        }

        public List<HotbarOverride> getVariants() {
            return variants;
        }

        public void tick(LivingEntity entity) {
            for (HotbarOverride variant : variants) {
                variant.tick(entity);
            }
        }
    }

    private static final SerializableDataType<HotbarOverride> HOTBAR_OVERRIDE = SerializableDataType.compound(
        HotbarOverride.class,
        new SerializableData()
            .add("stack", SerializableDataTypes.ITEM_STACK, null)
            .add("condition", ApoliDataTypes.ENTITY_CONDITION, null),
        data -> new HotbarOverride(data.get("stack"), data.get("condition")),
        (data, inst) -> {
            SerializableData.Instance dataInst = data.new Instance();
            dataInst.set("stack", inst.getStackNoMatterWhat());
            dataInst.set("condition", inst.getCondition());
            return dataInst;
        }
    );

    private static final SerializableDataType<HotbarSlotConfig> HOTBAR_SLOT_CONFIG = SerializableDataType.compound(
        HotbarSlotConfig.class,
        new SerializableData()
            .add("variants", SerializableDataType.list(HOTBAR_OVERRIDE)),
        data -> new HotbarSlotConfig(data.get("variants")),
        (data, inst) -> {
            SerializableData.Instance dataInst = data.new Instance();
            dataInst.set("variants", inst.getVariants());
            return dataInst;
        }
    );

    public static PowerFactory<?> getFactory() {
        SerializableData data = new SerializableData();
        String[] names = {
            "hotbar_1", "hotbar_2", "hotbar_3", "hotbar_4", "hotbar_5",
            "hotbar_6", "hotbar_7", "hotbar_8", "hotbar_9", "offhand"
        };
        for (String name : names) {
            data.add(name, HOTBAR_SLOT_CONFIG, null);
        }

        return new PowerFactory<OverrideHotbarPower>(
            OriginsContent.id("override_hotbar"),
            data,
            dataInst -> (type, entity) -> {
                HotbarSlotConfig[] configs = new HotbarSlotConfig[10];
                for (int i = 0; i < names.length; i++) {
                    configs[i] = new HotbarSlotConfig(dataInst.get("variants"));
                }
                return new OverrideHotbarPower(type, entity, configs);
            }
        ).allowCondition();
    }
}
