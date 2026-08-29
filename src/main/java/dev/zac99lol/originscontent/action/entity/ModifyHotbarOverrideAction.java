package dev.zac99lol.originscontent.action.entity;

import dev.zac99lol.originscontent.OriginsContent;
import dev.zac99lol.originscontent.power.OverrideHotbarPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ModifyHotbarOverrideAction {
    public static void action(SerializableData.Instance data, Entity entity) {
        String slotName = data.getString("slot");
        ItemStack item = data.get("item");

        PowerHolderComponent.getPowers(entity, OverrideHotbarPower.class)
            .forEach(power -> {
                if (slotName.equals("mainhand") && entity instanceof PlayerEntity player) {
                    power.setOverride(player.getInventory().selectedSlot, item);
                } else if (slotName.equals("offhand")) {
                    power.setOffhandOverride(item);
                } else {
                    int index = Integer.parseInt(slotName.substring("hotbar_".length())) - 1;
                    power.setOverride(index, item);
                }
            });
    }

    public static ActionFactory<Entity> getFactory() {
        return new ActionFactory<>(
            OriginsContent.id("modify_hotbar_override"),
            new SerializableData()
                .add("slot", SerializableDataTypes.STRING)
                .add("item", SerializableDataTypes.ITEM_STACK, null),
            ModifyHotbarOverrideAction::action
        );
    }
}
