package com.sujal.hssp.items;

import com.sujal.hssp.items.impl.RogueSword;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemManager {
    public static final Map<String, SBItem> ITEMS = new HashMap<>();

    public static void registerItems() {
        register(new RogueSword());
    }

    private static void register(SBItem item) {
        // In a real scenario, we'd access the ID via a getter
        // Here we hack it for the example
        ITEMS.put(item.getClass().getSimpleName().toUpperCase(), item); 
    }

    public static ItemStack getItem(String id) {
        if (ITEMS.containsKey(id)) {
            return ITEMS.get(id).build();
        }
        return null;
    }
}
