package com.sujal.hssp.stats;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public enum StatType {
    HEALTH("Health", Formatting.RED, "❤"),
    DEFENSE("Defense", Formatting.GREEN, "❈"),
    STRENGTH("Strength", Formatting.RED, "❁"),
    SPEED("Speed", Formatting.WHITE, "✦"),
    CRIT_CHANCE("Crit Chance", Formatting.BLUE, "☣"),
    CRIT_DAMAGE("Crit Damage", Formatting.BLUE, "☠"),
    INTELLIGENCE("Intelligence", Formatting.AQUA, "✎"),
    DAMAGE("Damage", Formatting.RED, "❁"); // Added missing DAMAGE stat

    public final String name;
    public final Formatting color;
    public final String icon;

    StatType(String name, Formatting color, String icon) {
        this.name = name;
        this.color = color;
        this.icon = icon;
    }

    public Text getFormatted(double value) {
        // Formats as: "20❁ Damage" or similar
        return Text.literal((int)value + icon + " " + name).formatted(color);
    }
}
