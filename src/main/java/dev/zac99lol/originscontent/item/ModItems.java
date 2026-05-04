// dev/zac99lol/originscontent/item/ModItems.java
package dev.zac99lol.originscontent.item;

import dev.zac99lol.originscontent.OriginsContent;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {

    public static final Item OBSCURER = new Item(new FabricItemSettings().rarity(Rarity.RARE));
    public static final Item SHATTERED_ORB = new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON));
    public static final EmotionalSupportPerkeoItem PERKEO = new EmotionalSupportPerkeoItem(new FabricItemSettings().rarity(Rarity.EPIC).maxCount(1).fireproof().equipmentSlot(stack -> EquipmentSlot.HEAD));
    public static final Item PLACEHOLDER_1 = new Item(new FabricItemSettings());
    public static final Item PLACEHOLDER_2 = new Item(new FabricItemSettings());
    public static final Item PLACEHOLDER_3 = new Item(new FabricItemSettings());
    public static final Item PLACEHOLDER_4 = new Item(new FabricItemSettings());
    public static final Item PLACEHOLDER_5 = new Item(new FabricItemSettings());
    public static final Item PLACEHOLDER_6 = new Item(new FabricItemSettings().equipmentSlot(stack -> EquipmentSlot.HEAD));
    public static final Item PLACEHOLDER_7 = new Item(new FabricItemSettings().equipmentSlot(stack -> EquipmentSlot.CHEST));
    public static final Item PLACEHOLDER_8 = new Item(new FabricItemSettings().equipmentSlot(stack -> EquipmentSlot.LEGS));
    public static final Item PLACEHOLDER_9 = new Item(new FabricItemSettings().equipmentSlot(stack -> EquipmentSlot.FEET));
    public static final EnchantedBookBundleItem ENCHANTED_BOOK_BUNDLE = new EnchantedBookBundleItem(new FabricItemSettings().maxCount(16).rarity(Rarity.UNCOMMON));
    public static final Item ACHIEVEMENT = new Item(new FabricItemSettings().rarity(Rarity.RARE).fireproof());
    public static final Item ACHIEVEMENT_USE = new Item(new FabricItemSettings().rarity(Rarity.RARE).fireproof());

    // Perkeo Chain Items
    public static final Item SCULK_DUST = new Item(new FabricItemSettings());
    public static final Item TAINTED_AMETHYST_CLUSTER = new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON));
    public static final Item TAINTED_AMETHYST_SHARD = new Item(new FabricItemSettings());
    public static final Item SMOLDERING_SHARD = new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON));
    public static final Item CURED_SHARD = new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON));
    public static final Item SEARED_SHARD = new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON));
    public static final Item VOID_KISSED_SHARD = new Item(new FabricItemSettings().rarity(Rarity.RARE));
    public static final Item ANCIENT_SHARD = new Item(new FabricItemSettings().rarity(Rarity.RARE));
    public static final Item RESONANT_SHARD = new Item(new FabricItemSettings().rarity(Rarity.RARE));
    public static final Item HARMONISED_SHARD = new Item(new FabricItemSettings().rarity(Rarity.EPIC));

    public static void initialize() {
        // Existing items — IDs unchanged
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "obscurer"), OBSCURER);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "shattered_orb_of_origin"), SHATTERED_ORB);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "perkeo"), PERKEO);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "placeholder_item_1"), PLACEHOLDER_1);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "placeholder_item_2"), PLACEHOLDER_2);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "placeholder_item_3"), PLACEHOLDER_3);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "placeholder_item_4"), PLACEHOLDER_4);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "placeholder_item_5"), PLACEHOLDER_5);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "placeholder_item_6"), PLACEHOLDER_6);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "placeholder_item_7"), PLACEHOLDER_7);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "placeholder_item_8"), PLACEHOLDER_8);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "placeholder_item_9"), PLACEHOLDER_9);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "enchanted_book_bundle"), ENCHANTED_BOOK_BUNDLE);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "achievement_item"), ACHIEVEMENT);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "achievement_item_use"), ACHIEVEMENT_USE);

        // Perkeo Chain Items
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "sculk_dust"), SCULK_DUST);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "tainted_amethyst_cluster"), TAINTED_AMETHYST_CLUSTER);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "tainted_amethyst_shard"), TAINTED_AMETHYST_SHARD);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "smoldering_shard"), SMOLDERING_SHARD);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "cured_shard"), CURED_SHARD);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "seared_shard"), SEARED_SHARD);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "void_kissed_shard"), VOID_KISSED_SHARD);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "ancient_shard"), ANCIENT_SHARD);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "resonant_shard"), RESONANT_SHARD);
        Registry.register(Registries.ITEM, new Identifier(OriginsContent.MOD_ID, "harmonised_shard"), HARMONISED_SHARD);

        // Item groups
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(t -> {
            t.add(ENCHANTED_BOOK_BUNDLE);
            t.add(OBSCURER);
            t.add(SHATTERED_ORB);
        });
    }
}