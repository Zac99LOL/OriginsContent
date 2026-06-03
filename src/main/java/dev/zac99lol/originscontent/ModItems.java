package dev.zac99lol.originscontent;

import dev.zac99lol.originscontent.item.EmotionalSupportPerkeoItem;
import dev.zac99lol.originscontent.item.EnchantedBookBundleItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import static dev.zac99lol.originscontent.OriginsContent.LOGGER;

public class ModItems {
    public static final Item OBSCURER_ITEM = new Item(new FabricItemSettings().rarity(Rarity.RARE));
    public static final Item SHATTERED_ORB_ITEM = new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON));
    public static final EmotionalSupportPerkeoItem EMOTIONAL_SUPPORT_PERKEO_ITEM = new EmotionalSupportPerkeoItem(new FabricItemSettings().rarity(Rarity.EPIC).maxCount(1).fireproof().equipmentSlot(stack -> EquipmentSlot.HEAD));
    public static final Item PLACEHOLDER_ITEM_1 = new Item(new FabricItemSettings());
    public static final Item PLACEHOLDER_ITEM_2 = new Item(new FabricItemSettings());
    public static final Item PLACEHOLDER_ITEM_3 = new Item(new FabricItemSettings());
    public static final Item PLACEHOLDER_ITEM_4 = new Item(new FabricItemSettings());
    public static final Item PLACEHOLDER_ITEM_5 = new Item(new FabricItemSettings());
    public static final Item PLACEHOLDER_ITEM_6 = new Item(new FabricItemSettings().equipmentSlot(stack -> EquipmentSlot.HEAD));
    public static final Item PLACEHOLDER_ITEM_7 = new Item(new FabricItemSettings().equipmentSlot(stack -> EquipmentSlot.CHEST));
    public static final Item PLACEHOLDER_ITEM_8 = new Item(new FabricItemSettings().equipmentSlot(stack -> EquipmentSlot.LEGS));
    public static final Item PLACEHOLDER_ITEM_9 = new Item(new FabricItemSettings().equipmentSlot(stack -> EquipmentSlot.FEET));
    public static final EnchantedBookBundleItem ENCHANTED_BOOK_BUNDLE_ITEM = new EnchantedBookBundleItem(new FabricItemSettings().maxCount(16).rarity(Rarity.UNCOMMON));
    public static final Item ACHIEVEMENT_ITEM = new Item(new FabricItemSettings().rarity(Rarity.RARE).fireproof());
    public static final Item ACHIEVEMENT_USE_ITEM = new Item(new FabricItemSettings().rarity(Rarity.RARE).fireproof());

    public static void initialize() {
        LOGGER.info("Initialising item groups...");
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(t -> {
            t.add(ENCHANTED_BOOK_BUNDLE_ITEM);
            t.add(OBSCURER_ITEM);
            t.add(SHATTERED_ORB_ITEM);
        });

        LOGGER.info("Initialising items...");
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "obscurer"), OBSCURER_ITEM);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "shattered_orb_of_origin"), SHATTERED_ORB_ITEM);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "perkeo"), EMOTIONAL_SUPPORT_PERKEO_ITEM);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "placeholder_item_1"), PLACEHOLDER_ITEM_1);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "placeholder_item_2"), PLACEHOLDER_ITEM_2);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "placeholder_item_3"), PLACEHOLDER_ITEM_3);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "placeholder_item_4"), PLACEHOLDER_ITEM_4);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "placeholder_item_5"), PLACEHOLDER_ITEM_5);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "placeholder_item_6"), PLACEHOLDER_ITEM_6);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "placeholder_item_7"), PLACEHOLDER_ITEM_7);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "placeholder_item_8"), PLACEHOLDER_ITEM_8);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "placeholder_item_9"), PLACEHOLDER_ITEM_9);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "enchanted_book_bundle"), ENCHANTED_BOOK_BUNDLE_ITEM);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "achievement_item"), ACHIEVEMENT_ITEM);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "achievement_item_use"), ACHIEVEMENT_USE_ITEM);
    }
}
