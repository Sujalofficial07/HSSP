package com.hssp.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class SkyblockItemHandler {

    // NBT Keys
    private static final String NBT_KEY_SKYBLOCK_ID = "SkyblockID";
    private static final String NBT_KEY_RARITY = "Rarity";
    private static final String NBT_KEY_STATS = "Stats";

    // Rarity Enum
    public enum Rarity {
        COMMON(Formatting.WHITE),
        UNCOMMON(Formatting.GREEN),
        RARE(Formatting.BLUE),
        EPIC(Formatting.DARK_PURPLE),
        LEGENDARY(Formatting.GOLD),
        MYTHIC(Formatting.LIGHT_PURPLE),
        DIVINE(Formatting.AQUA),
        SPECIAL(Formatting.RED);

        public final Formatting color;

        Rarity(Formatting color) {
            this.color = color;
        }
    }

    /**
     * Initializes a standard Skyblock item with base stats.
     * @param stack The ItemStack to modify.
     * @param id The internal Skyblock ID (e.g., "HYPERION").
     * @param rarity The rarity of the item.
     * @return The modified ItemStack.
     */
    public static ItemStack createSkyblockItem(ItemStack stack, String id, Rarity rarity) {
        NbtCompound nbt = stack.getOrCreateNbt();
        
        // Base Skyblock Data set kar rahe hain
        nbt.putString(NBT_KEY_SKYBLOCK_ID, id);
        nbt.putString(NBT_KEY_RARITY, rarity.name());
        
        // Empty stats compound initialize kar rahe hain
        if (!nbt.contains(NBT_KEY_STATS)) {
            nbt.put(NBT_KEY_STATS, new NbtCompound());
        }

        return stack;
    }

    /**
     * Sets a specific stat on the item (e.g., Strength, Crit Chance).
     */
    public static void setStat(ItemStack stack, String statName, double value) {
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound stats = nbt.getCompound(NBT_KEY_STATS);
        stats.putDouble(statName, value);
        nbt.put(NBT_KEY_STATS, stats);
    }

    /**
     * Generates the lore based on stats and rarity.
     * Call this whenever stats are updated.
     */
    public static void updateLore(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null || !nbt.contains(NBT_KEY_SKYBLOCK_ID)) return;

        List<Text> lore = new ArrayList<>();
        NbtCompound stats = nbt.getCompound(NBT_KEY_STATS);
        Rarity rarity = Rarity.valueOf(nbt.getString(NBT_KEY_RARITY));

        // Stats ko Lore mein add karna
        // Example: Strength: +50
        for (String key : stats.getKeys()) {
            double value = stats.getDouble(key);
            lore.add(Text.literal(key + ": +" + (int)value).formatted(Formatting.GRAY));
        }

        lore.add(Text.literal("")); // Empty line
        
        // Rarity line (e.g., "LEGENDARY SWORD")
        String type = "ITEM"; // Logic can be improved to detect Sword/Bow/Armor
        lore.add(Text.literal(rarity.name() + " " + type).formatted(rarity.color, Formatting.BOLD));

        // Lore set karna directly NBT par (Fabric way)
        stack.getOrCreateSubNbt("display").put("Lore", toNbtList(lore));
    }
    
    // Helper to convert Text list to NBT List (Mock implementation for brevity)
    private static net.minecraft.nbt.NbtList toNbtList(List<Text> lines) {
        net.minecraft.nbt.NbtList list = new net.minecraft.nbt.NbtList();
        for (Text text : lines) {
            list.add(net.minecraft.nbt.NbtString.of(Text.Serializer.toJson(text)));
        }
        return list;
    }
}
