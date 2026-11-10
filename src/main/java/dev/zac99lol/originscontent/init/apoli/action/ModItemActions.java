package dev.zac99lol.originscontent.init.apoli.action;

import dev.zac99lol.originscontent.OriginsContent;
import dev.zac99lol.originscontent.apoli.ConditionalChoiceAction;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.world.World;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ModItemActions {
    Map<ActionFactory<Pair<World, ItemStack>>, Identifier> ITEM_ACTIONS = new LinkedHashMap<>();

    ActionFactory<Pair<World, ItemStack>> CONDITIONAL_CHOICE = createAction("conditional_choice", ConditionalChoiceAction.getFactory(ApoliDataTypes.ITEM_ACTION, ApoliDataTypes.ITEM_CONDITION, Pair::getRight));

    private static ActionFactory<Pair<World, ItemStack>> createAction(String id, ActionFactory<Pair<World, ItemStack>> actionFactory) {
        ITEM_ACTIONS.put(actionFactory, OriginsContent.id(id));
        return actionFactory;
    }

    static void initialize() {
        ITEM_ACTIONS.keySet().forEach(actionFactory -> Registry.register(ApoliRegistries.ITEM_ACTION, ITEM_ACTIONS.get(actionFactory), actionFactory));
    }
}
