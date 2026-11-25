package dev.zac99lol.originscontent.apoli.power;

import dev.zac99lol.originscontent.OriginsContent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class OverrideHudTexturePower extends Power {
    public final Identifier crosshair;
    public final Identifier health;
    public final Identifier armor;
    public final Identifier hunger;
    public final Identifier air;
    public final Identifier experience;
    public final Identifier hotbar;
    public final Identifier bossBar;
    public final Identifier mountJumpBar;
    public final Identifier attackIndicator;
    public final Identifier carvedPumpkinOverlay;
    public final Identifier spyglassOverlay;
    public final Identifier powderSnowOutline;

    public OverrideHudTexturePower(PowerType<?> type, LivingEntity entity, Identifier crosshair, Identifier health, Identifier armor, Identifier hunger, Identifier air, Identifier experience, Identifier hotbar, Identifier bossBar, Identifier mountJumpBar, Identifier attackIndicator, Identifier carvedPumpkinOverlay, Identifier spyglassOverlay, Identifier powderSnowOutline) {
        super(type, entity);
        this.crosshair = crosshair;
        this.health = health;
        this.armor = armor;
        this.hunger = hunger;
        this.air = air;
        this.experience = experience;
        this.hotbar = hotbar;
        this.bossBar = bossBar;
        this.mountJumpBar = mountJumpBar;
        this.attackIndicator = attackIndicator;
        this.carvedPumpkinOverlay = carvedPumpkinOverlay;
        this.spyglassOverlay = spyglassOverlay;
        this.powderSnowOutline = powderSnowOutline;
    }

    public boolean crosshairActive() {
        return crosshair != null;
    }

    public boolean healthActive() {
        return health != null;
    }

    public boolean armorActive() {
        return armor != null;
    }

    public boolean hungerActive() {
        return hunger != null;
    }

    public boolean airActive() {
        return air != null;
    }

    public boolean experienceActive() {
        return experience != null;
    }

    public boolean hotbarActive() {
        return hotbar != null;
    }

    public boolean bossBarActive() {
        return bossBar != null;
    }

    public boolean mountJumpBarActive() {
        return mountJumpBar != null;
    }

    public boolean attackIndicatorActive() {
        return attackIndicator != null;
    }

    public boolean carvedPumpkinOverlayActive() {
        return carvedPumpkinOverlay != null;
    }

    public boolean spyglassOverlayActive() {
        return spyglassOverlay != null;
    }

    public boolean powderSnowOutlineActive() {
        return powderSnowOutline != null;
    }

    public static PowerFactory getFactory() {
        return new PowerFactory<OverrideHudTexturePower>(OriginsContent.id("override_hud_texture"),
            new SerializableData()
                .add("crosshair", SerializableDataTypes.IDENTIFIER, null)
                .add("health", SerializableDataTypes.IDENTIFIER, null)
                .add("armor", SerializableDataTypes.IDENTIFIER, null)
                .add("hunger", SerializableDataTypes.IDENTIFIER, null)
                .add("air", SerializableDataTypes.IDENTIFIER, null)
                .add("experience", SerializableDataTypes.IDENTIFIER, null)
                .add("hotbar", SerializableDataTypes.IDENTIFIER, null)
                .add("boss_bar", SerializableDataTypes.IDENTIFIER, null)
                .add("mount_jump_bar", SerializableDataTypes.IDENTIFIER, null)
                .add("attack_indicator", SerializableDataTypes.IDENTIFIER, null)
                .add("carved_pumpkin_overlay", SerializableDataTypes.IDENTIFIER, null)
                .add("spyglass_overlay", SerializableDataTypes.IDENTIFIER, null)
                .add("powder_snow_outline", SerializableDataTypes.IDENTIFIER, null),
            data -> (type, entity) -> new OverrideHudTexturePower(type, entity,
                data.getId("crosshair"),
                data.getId("health"),
                data.getId("armor"),
                data.getId("hunger"),
                data.getId("air"),
                data.getId("experience"),
                data.getId("hotbar"),
                data.getId("boss_bar"),
                data.getId("mount_jump_bar"),
                data.getId("attack_indicator"),
                data.getId("carved_pumpkin_overlay"),
                data.getId("spyglass_overlay"),
                data.getId("powder_snow_outline")));
    }
}
