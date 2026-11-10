package dev.zac99lol.originscontent.mixin.mace_enchantable_at_etable;

import com.pvpranked.item.MaceItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MaceItem.class)
public abstract class MaceItemMixin extends Item {
    public MaceItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public int getEnchantability() {
        return 14;
    }
}
