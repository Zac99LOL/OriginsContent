package dev.zac99lol.originscontent.init;

import doctor4t.defile.index.DefileStatusEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public interface ModFoodComponents {
    FoodComponent TSUNDERE = new FoodComponent.Builder().alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 4, 99), 1.0F).build();
    FoodComponent BULLSHIT = new FoodComponent.Builder().alwaysEdible().statusEffect(new StatusEffectInstance(DefileStatusEffects.INKMORPHOSIS, 600, 0), 0.5F).build();
}
