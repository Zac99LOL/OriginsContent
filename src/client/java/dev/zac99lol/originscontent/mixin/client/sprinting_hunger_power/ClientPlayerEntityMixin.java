package dev.zac99lol.originscontent.mixin.client.sprinting_hunger_power;

import dev.zac99lol.originscontent.apoli.power.SprintingHungerPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    @ModifyConstant(method = "canSprint", constant = @Constant)
    private float originscontent$modifyMinimumHungerLevel(float constant) {
        SprintingHungerPower power = PowerHolderComponent.getPowers((ClientPlayerEntity) (Object) this, SprintingHungerPower.class).stream().findFirst().orElse(null);
        if (power == null) return constant;

        return power.getMinimumSprintingHunger();
    }
}
