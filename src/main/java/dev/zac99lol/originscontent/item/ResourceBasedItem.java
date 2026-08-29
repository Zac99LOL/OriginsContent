package dev.zac99lol.originscontent.item;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import io.github.apace100.apoli.power.ResourcePower;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ResourceBasedItem extends Item {
    public ResourceBasedItem(Settings settings) {
        super(settings);
    }

    private float cachedResourceFill = 0;

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return;

        Identifier resourceId = new Identifier(nbt.getString("BarResource"));
        PowerHolderComponent component = PowerHolderComponent.KEY.get(entity);
        PowerType<?> powerType = new PowerTypeReference<>(resourceId);
        if (!(component.getPower(powerType) instanceof ResourcePower power)) return;

        cachedResourceFill = power.getFill();
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        return nbt != null && nbt.contains("BarResource");
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return 0xFFFFFF;
        String hex = nbt.getString("BarColor");
        try {
            return Integer.parseInt(hex.replace("#", ""), 16);
        } catch (NumberFormatException e) {
            return 0xFFFFFF;
        }
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.round(cachedResourceFill * 13);
        /* OH MY GOD PLEASE WORK I HAVE BEEN TRYING TO GET THIS TO WORK FOR 12 HOURS NOW JUST MAKE THE FUCKING RESOURCE SHOW UP WHAT DO YOU MEAN THERES NO HOLDER I AM ACTIVELY SETTING THE HOLDER???? IT DISAPPEARS FOR NO REASON?????????????? ITS ALWAYS SET TO THE RIGHT ENTITY BUT THEN IT ISNT?????? oh well it works now with my inventoryTick thingy :sob:
        if (stack.getHolder() == null) return 0;
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return 0;

        Identifier resourceId = new Identifier(nbt.getString("BarResource"));
        PowerHolderComponent component = PowerHolderComponent.KEY.get(stack.getHolder());
        PowerType<?> powerType = new PowerTypeReference<>(resourceId);
        if (!(component.getPower(powerType) instanceof ResourcePower power)) return 0;

        return Math.round(power.getFill() * 13);
         */
    }
}
