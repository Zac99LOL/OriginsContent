package dev.zac99lol.originscontent.mixin.neverend_advancements;

import com.llamalad7.mixinextras.sugar.Local;
import dev.zac99lol.neverend.entity.PoolfishEntity;
import dev.zac99lol.neverend.item.PoolfishItem;
import dev.zac99lol.originscontent.init.ModAdvancements;
import dev.zac99lol.originscontent.util.PoolfishPlacer;
import net.minecraft.advancement.Advancement;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(PoolfishItem.class)
public abstract class PoolfishItemMixin extends Item {
    @Shadow
    public static @Nullable NbtCompound getPoolfishTag(ItemStack stack) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    public PoolfishItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
    private void originscontent$savePlacer(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir, @Local(name = "poolfish") PoolfishEntity poolfish) {
        ((PoolfishPlacer) poolfish).originscontent$setPlacer(user);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof ServerPlayerEntity player) {
            if (getPoolfishTag(stack).getInt("Age") > 0) {
                Advancement advancement = Objects.requireNonNull(player.getServer()).getAdvancementLoader().get(ModAdvancements.EAT_BABY);
                player.getAdvancementTracker().grantCriterion(advancement, "nohappen");
            }
        }
        return super.finishUsing(stack, world, user);
    }
}
