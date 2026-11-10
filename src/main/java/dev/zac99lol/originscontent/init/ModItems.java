package dev.zac99lol.originscontent.init;

import dev.zac99lol.originscontent.OriginsContent;
import dev.zac99lol.originscontent.item.EmotionalSupportPerkeoItem;
import dev.zac99lol.originscontent.item.EnchantedBookBundleItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ModItems {
    Map<Item, Identifier> ITEMS = new LinkedHashMap<>();
    Map<Item, RegistryKey<ItemGroup>> ITEM_GROUPS = new LinkedHashMap<>();
    Item OBSCURER = createItem("obscurer", new Item(new FabricItemSettings().rarity(Rarity.RARE)), ItemGroups.TOOLS);
    Item SHATTERED_ORB_OF_ORIGIN = createItem("shattered_orb_of_origin", new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON)), ItemGroups.TOOLS);
    Item EMOTIONAL_SUPPORT_PERKEO = createItem("perkeo", new EmotionalSupportPerkeoItem(new FabricItemSettings().rarity(Rarity.EPIC).maxCount(1).fireproof().equipmentSlot(stack -> {
        if (stack.getHolder() instanceof PlayerEntity player) {
            return player.getGameProfile().getId().equals(OriginsContent.UUIDs.Zac99LOL) ? EquipmentSlot.HEAD : null;
        }
        return null;
    })));
    Item PLACEHOLDER_ITEM_1 = createItem("placeholder_item_1", new Item(new FabricItemSettings()));
    Item PLACEHOLDER_ITEM_2 = createItem("placeholder_item_2", new Item(new FabricItemSettings()));
    Item PLACEHOLDER_ITEM_3 = createItem("placeholder_item_3", new Item(new FabricItemSettings()));
    Item PLACEHOLDER_ITEM_4 = createItem("placeholder_item_4", new Item(new FabricItemSettings()));
    Item PLACEHOLDER_ITEM_5 = createItem("placeholder_item_5", new Item(new FabricItemSettings()));
    Item PLACEHOLDER_ITEM_6 = createItem("placeholder_item_6", new Item(new FabricItemSettings().equipmentSlot(stack -> EquipmentSlot.HEAD)));
    Item PLACEHOLDER_ITEM_7 = createItem("placeholder_item_7", new Item(new FabricItemSettings().equipmentSlot(stack -> EquipmentSlot.CHEST)));
    Item PLACEHOLDER_ITEM_8 = createItem("placeholder_item_8", new Item(new FabricItemSettings().equipmentSlot(stack -> EquipmentSlot.LEGS)));
    Item PLACEHOLDER_ITEM_9 = createItem("placeholder_item_9", new Item(new FabricItemSettings().equipmentSlot(stack -> EquipmentSlot.FEET)));
    Item ENCHANTED_BOOK_BUNDLE = createItem("enchanted_book_bundle", new EnchantedBookBundleItem(new FabricItemSettings().maxCount(16).rarity(Rarity.UNCOMMON)), ItemGroups.TOOLS);
    Item ACHIEVEMENT_ITEM = createItem("achievement_item", new Item(new FabricItemSettings().rarity(Rarity.RARE).fireproof()));
    Item ACHIEVEMENT_USE_ITEM = createItem("achievement_item_use", new Item(new FabricItemSettings().rarity(Rarity.RARE).fireproof()));
    Item BLUEPRINT_ITEM = createItem("blueprint", new Item(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE)));
    Item SOUL_LOCKET = createItem("soul_locket", new Item(new FabricItemSettings().maxCount(1).fireproof()), ItemGroups.TOOLS);
    Item POOLFISH_ON_A_STICK = createItem("poolfish_on_a_stick", new Item(new FabricItemSettings().maxCount(1)));
    Item OLLIE_TSUNDERE = createItem("music_disc_tsundere", new MusicDiscItem(13, ModSoundEvents.MUSIC_DISC_TSUNDERE, new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC), 206), ItemGroups.SEARCH);
    Item MODEL_ITEM = createItem("model", new Item(new FabricItemSettings()));

    private static <T extends Item> T createItem(String id, T item) {
        ITEMS.put(item, OriginsContent.id(id));
        return item;
    }

    private static <T extends Item> T createItem(String id, T item, RegistryKey<ItemGroup> itemGroup) {
        ITEM_GROUPS.put(item, itemGroup);
        return createItem(id, item);
    }

    static void initialize() {
        ITEMS.keySet().forEach(item -> Registry.register(Registries.ITEM, ITEMS.get(item), item));
        ITEM_GROUPS.keySet().forEach(item ->
            ItemGroupEvents.modifyEntriesEvent(ITEM_GROUPS.get(item)).register(entries -> entries.add(item)));
    }
}
