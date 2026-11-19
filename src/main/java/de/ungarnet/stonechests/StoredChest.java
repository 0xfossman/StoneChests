package de.ungarnet.stonechests;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import java.util.*;

public final class StoredChest {
    public final UUID id; public final ChestType type; public final Location location; public final ItemStack[][] pages;
    public UUID hologram;
    public StoredChest(UUID id, ChestType type, Location location, int count) { this.id=id; this.type=type; this.location=location; this.pages=new ItemStack[count][54]; }
    public int usedSlots() { int used=0; for (ItemStack[] p: pages) for(ItemStack i:p) if(i!=null && !i.getType().isAir()) used++; return used; }
}
