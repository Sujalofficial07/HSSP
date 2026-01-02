package com.sujal.hssp.items;

import com.sujal.hssp.stats.StatType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;

public abstract class SBItem {
    private final String id;
    private final Item vanillaItem;
    private final String displayName;
    private final Rarity rarity;
    private final Map<StatType, Double> baseStats = new HashMap<>();

    public SBItem(String id, Item vanillaItem, String displayName, Rarity rarity) {
        this.id = id;
        this.vanillaItem = vanillaItem;
        this.displayName = displayName;
        this.rarity = rarity;
    }

    public void setStat(StatType type, double value) {
        baseStats.put(type, value);
    }

    public ItemStack build() {
        ItemStack stack = new ItemStack(vanillaItem);
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound sbData = new NbtCompound();
        
        sbData.putString("id", id);
        
        // Save stats to NBT
        NbtCompound statsNbt = new NbtCompound();
        baseStats.forEach((k, v) -> statsNbt.putDouble(k.name(), v));
        sbData.put("stats", statsNbt);
        sbData.putString("rarity", rarity.name());
        
        nbt.put("HSSP_DATA", sbData);
        
        // formatting
        stack.setCustomName(Text.literal(displayName).formatted(rarity.color));
        return stack;
    }

    // Abstract method for Right-Click abilities
    public abstract void onAbility(PlayerEntity player, World world);

    public enum Rarity {
        COMMON(Formatting.WHITE),
        UNCOMMON(Formatting.GREEN),
        RARE(Formatting.BLUE),
        EPIC(Formatting.DARK_PURPLE),
        LEGENDARY(Formatting.GOLD),
        MYTHIC(Formatting.LIGHT_PURPLE);

        public final Formatting color;
        Rarity(Formatting color) { this.color = color; }
    }
}
