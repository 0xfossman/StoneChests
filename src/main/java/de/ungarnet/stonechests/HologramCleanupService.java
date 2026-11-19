package de.ungarnet.stonechests;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TextDisplay;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;
import java.util.*;

/** Only scans entities in already-loaded chunks; it never loads worlds or chunks. */
final class HologramCleanupService {
  private final StoneChests plugin;
  private BukkitTask task;
  HologramCleanupService(StoneChests plugin) { this.plugin=plugin; }
  void start() { stop(); if(!plugin.getConfig().getBoolean("holograms.cleanup.enabled",true))return; long ticks=Math.max(20L,plugin.getConfig().getLong("holograms.cleanup.interval-seconds",300)*20L); task=Bukkit.getScheduler().runTaskTimer(plugin,this::run,ticks,ticks); }
  void stop() { if(task!=null){task.cancel();task=null;} }
  void run() {
    int checked=0, removed=0, restored=0, inconsistent=0;
    Map<UUID,TextDisplay> marked=new HashMap<>();
    for(World world:Bukkit.getWorlds()) for(Entity entity:world.getEntities()) {
      if(!(entity instanceof TextDisplay display)) continue;
      String raw=display.getPersistentDataContainer().get(plugin.hologramKey(),PersistentDataType.STRING);
      if(raw==null) continue;
      checked++;
      try { UUID chestId=UUID.fromString(raw); StoredChest chest=plugin.chest(chestId); if(chest==null || chest.location.getWorld()==null || !chest.location.getWorld().equals(world) || !plugin.validChestBlock(chest)) { display.remove(); removed++; } else if(marked.putIfAbsent(chestId,display)!=null) { display.remove(); removed++; } else plugin.adoptHologram(chest,display); }
      catch(IllegalArgumentException ex) { display.remove(); removed++; }
    }
    for(StoredChest chest:plugin.allChests()) {
      World world=chest.location.getWorld();
      if(world==null || !world.isChunkLoaded(chest.location.getBlockX()>>4,chest.location.getBlockZ()>>4)) continue;
      if(!plugin.validChestBlock(chest)) { inconsistent++; continue; }
      TextDisplay display=marked.get(chest.id);
      if(display==null && plugin.getConfig().getBoolean("holograms.cleanup.restore-missing",true)) { plugin.ensureHologramForCleanup(chest); restored++; }
    }
    plugin.getLogger().info("Hologram cleanup: checked="+checked+", removed="+removed+", restored="+restored+", inconsistencies="+inconsistent);
  }
}
