package de.ungarnet.stonechests;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import java.util.Locale;

public enum ChestType {
    STONE("Steinkiste", 3, NamedTextColor.GRAY, Material.STONE_BRICKS),
    IRON("Eisenkiste", 6, NamedTextColor.WHITE, Material.IRON_BLOCK),
    DIAMOND("Diamantkiste", 9, NamedTextColor.AQUA, Material.DIAMOND_BLOCK);
    public final String title; public final int defaultPages; public final NamedTextColor color; public final Material recipeMaterial;
    ChestType(String title, int pages, NamedTextColor color, Material recipeMaterial) { this.title=title; this.defaultPages=pages; this.color=color; this.recipeMaterial=recipeMaterial; }
    public String key() { return name().toLowerCase(Locale.ROOT); }
}
