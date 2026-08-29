package dev.zac99lol.originscontent.power;

import dev.zac99lol.originscontent.OriginsContent;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
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

public class OverrideHotbarPower extends Power {
    private final HotbarOverride[] overrides = new HotbarOverride[10];
    private int oldSelectedSlot = 0;

    public OverrideHotbarPower(PowerType<?> type, LivingEntity entity, HotbarOverride[] overrides) {
        super(type, entity);
        System.arraycopy(overrides, 0, this.overrides, 0, 10);
    }

    @Nullable
    public ItemStack getOverride(int i) {
        HotbarOverride override = overrides[i];
        return override.condition.test(entity) ? override.stack : null;
    }

    @Nullable
    public ItemStack getOffhandOverride() {
        HotbarOverride override = overrides[9];
        return override.condition.test(entity) ? override.stack : null;
    }

    public void setOverride(int i, @Nullable ItemStack stack) {
        PowerHolderComponent.KEY.sync(entity);
        overrides[i].stack = stack;
    }

    public void setOffhandOverride(@Nullable ItemStack stack) {
        overrides[9].stack = stack;
    }

    @Override
    public boolean shouldTick() {
        return true;
    }

    @Override
    public void tick() {
        if (!(entity instanceof PlayerEntity player)) return;
        for (HotbarOverride override : overrides) {
            if (override.stack != null && !override.stack.isEmpty()) {
                override.stack.setHolder(entity);
                override.stack.inventoryTick(player.getEntityWorld(), player, -1, false);
            }
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
        public ItemStack stack;
        public ConditionFactory<LivingEntity>.Instance condition;

        HotbarOverride(ItemStack stack, ConditionFactory<LivingEntity>.Instance condition) {
            this.stack = stack;
            this.condition = condition;
        }
    }

    public static PowerFactory<?> getFactory() {
        SerializableData data = new SerializableData();
        String[] names = {
            "hotbar_1", "hotbar_2", "hotbar_3", "hotbar_4", "hotbar_5",
            "hotbar_6", "hotbar_7", "hotbar_8", "hotbar_9", "offhand"
        };
        for (String name : names) {
            data.add(name, SerializableDataTypes.ITEM_STACK, null);
            data.add(name + "_condition", ApoliDataTypes.ENTITY_CONDITION);
        }

        return new PowerFactory<OverrideHotbarPower>(
            OriginsContent.id("override_hotbar"),
            data,
            dataInst -> (type, entity) -> {
                HotbarOverride[] overrides = new HotbarOverride[10];
                for (int i = 0; i < names.length; i++) {
                    overrides[i].stack = dataInst.get(names[i]);
                    overrides[i].condition = dataInst.get(names[i] + "_condition");
                }
                return new OverrideHotbarPower(type, entity, overrides);
            }
        ).allowCondition();
    }
}
