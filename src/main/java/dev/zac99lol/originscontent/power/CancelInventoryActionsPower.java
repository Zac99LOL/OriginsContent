package dev.zac99lol.originscontent.power;

import dev.zac99lol.originscontent.OriginsContent;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.fabricmc.fabric.api.event.client.player.ClientPickBlockApplyCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class CancelInventoryActionsPower extends Power {
    private final boolean changeHotbarSlot;
    private final boolean moveItemInGui;
    private final boolean dropItems;
    private final boolean pickupItems;
    private final boolean pickBlock;

    public CancelInventoryActionsPower(PowerType<?> type, LivingEntity entity,
                                       boolean changeHotbarSlot, boolean moveItemInGui, boolean dropItems, boolean pickupItems, boolean pickBlock) {
        super(type, entity);
        this.changeHotbarSlot = changeHotbarSlot;
        this.moveItemInGui = moveItemInGui;
        this.dropItems = dropItems;
        this.pickupItems = pickupItems;
        this.pickBlock = pickBlock;
    }

    public boolean blocksHotbarSlot() { return changeHotbarSlot; }
    public boolean blocksGuiMove() { return moveItemInGui; }
    public boolean blocksDrop() { return dropItems; }
    public boolean blocksPickup() { return pickupItems; }
    public boolean blocksPickBlock() { return pickBlock; }

    public static void init() {
        ClientPickBlockApplyCallback.EVENT.register((player, result, stack) -> {
            boolean blocked = PowerHolderComponent.getPowers(player, CancelInventoryActionsPower.class)
                .stream()
                .anyMatch(CancelInventoryActionsPower::blocksPickBlock);

            if (blocked) {
                return ItemStack.EMPTY;
            }

            return stack;
        });
    }

    public static PowerFactory<CancelInventoryActionsPower> getFactory() {
        return new PowerFactory<CancelInventoryActionsPower>(
            Identifier.of(OriginsContent.MOD_ID, "cancel_inventory_actions"),
            new SerializableData()
                .add("change_hotbar_slot", SerializableDataTypes.BOOLEAN, true)
                .add("move_item_in_gui", SerializableDataTypes.BOOLEAN, true)
                .add("drop_items", SerializableDataTypes.BOOLEAN, true)
                .add("pickup_items", SerializableDataTypes.BOOLEAN, true)
                .add("pick_block", SerializableDataTypes.BOOLEAN, true),
            data -> (type, entity) -> new CancelInventoryActionsPower(
                type, entity,
                data.getBoolean("change_hotbar_slot"),
                data.getBoolean("move_item_in_gui"),
                data.getBoolean("drop_items"),
                data.getBoolean("pickup_items"),
                data.getBoolean("pick_block")
            )
        ).allowCondition();
    }
}
