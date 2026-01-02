package com.sujal.hssp.items.impl;

import com.sujal.hssp.items.SBItem;
import com.sujal.hssp.stats.StatType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class RogueSword extends SBItem {
    public RogueSword() {
        super("ROGUE_SWORD", Items.GOLDEN_SWORD, "Rogue Sword", Rarity.COMMON);
        this.setStat(StatType.DAMAGE, 20); // Note: Need to map DAMAGE in StatType
        this.setStat(StatType.SPEED, 10);
    }

    @Override
    public void onAbility(PlayerEntity player, World world) {
        if (!world.isClient) {
            player.sendMessage(Text.literal("§aYou used Speed Boost!"), true);
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20 * 30, 1));
        }
    }
}
