package dev.zac99lol.originscontent.power;

import dev.zac99lol.originscontent.OriginsContent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class DisableOffhandPower extends Power {
    public DisableOffhandPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }

    @Override
    public void onGained() {
        if (!(entity instanceof ServerPlayerEntity player)) return;
        ItemStack offhand = player.getOffHandStack();
        if (offhand.isEmpty()) return;

        player.getInventory().offHand.set(0, ItemStack.EMPTY);

        if (!player.getInventory().insertStack(offhand)) {
            ItemEntity itemEntity = player.dropItem(offhand, false);
            if (itemEntity != null) {
                itemEntity.resetPickupDelay();
                itemEntity.setOwner(player.getUuid());
            }
        }
    }

    public static PowerFactory<?> getFactory() {
        return (PowerFactory<?>) Power.createSimpleFactory(
            DisableOffhandPower::new,
            OriginsContent.id("disable_offhand")
        );
    }
}
