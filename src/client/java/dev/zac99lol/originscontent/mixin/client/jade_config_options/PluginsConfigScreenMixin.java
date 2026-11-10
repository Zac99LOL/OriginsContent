package dev.zac99lol.originscontent.mixin.client.jade_config_options;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import snownee.jade.api.Identifiers;
import snownee.jade.gui.PluginsConfigScreen;
import snownee.jade.gui.config.OptionsList;
import snownee.jade.gui.config.value.OptionValue;

import java.util.List;

@Mixin(PluginsConfigScreen.class)
public abstract class PluginsConfigScreenMixin {
    @Unique
    private static final List<Identifier> TO_DISABLE = List.of(Identifiers.MC_ENTITY_HEALTH, Identifiers.MC_ENTITY_ARMOR, Identifiers.MC_POTION_EFFECTS, Identifiers.MC_ANIMAL_OWNER);

    @SuppressWarnings("unchecked")
    @Inject(method = "lambda$createOptions$0", at = @At(value = "INVOKE", target = "Lsnownee/jade/impl/config/entry/ConfigEntry;isSynced()Z"))
    private static void originscontent$disableOptions(OptionsList options, String translationKey, boolean noteServerFeature, MutableObject<OptionValue<?>> lastPrimary, Identifier i, CallbackInfo ci, @Local(name = "entry") OptionValue<?> entry) {
        if (TO_DISABLE.contains(i)) {
            entry.setDisabled(true);
            ((OptionValue<Boolean>) entry).setValue(false);
            entry.appendDescription(I18n.translate("gui.jade.disabled_by_mods"));
            entry.appendDescription("OriginsContent");
            if (entry.getFirstWidget() != null && entry.getDescription() != null) {
                entry.getFirstWidget().setTooltip(Tooltip.of(Text.literal(entry.getDescription())));
            }
        }
    }
}
