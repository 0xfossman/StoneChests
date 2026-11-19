# StoneChests

**Bigger storage for vanilla-style survival servers — no resource pack, client mod, or dependency required.**

StoneChests adds three craftable chest upgrades with persistent, multi-page storage. They look and behave like normal Minecraft chests while giving players much more room for building materials, farming loot, exploration supplies, and everything else a survival world accumulates.

## Features

- Three craftable storage tiers: **Stone Chest**, **Iron Chest**, and **Diamond Chest**.
- **162, 324, or 486 slots** of storage, split into familiar 54-slot chest pages.
- A floating `TextDisplay` hologram shows the chest type and total used slots.
- Simple in-inventory navigation: temporary compass in hotbar slot 9.
  - Left-click: next page
  - Right-click: previous page
- Persistent SQLite storage that keeps complete ItemStack metadata, including names, lore, enchantments, attributes, potion data, and custom model data.
- Safe storage flow: visible inventory changes are saved after Bukkit has applied them, and pages are saved before changing page, closing, disconnecting, or disabling the plugin.
- One player can edit a StoneChest at a time, preventing conflicting changes.
- Hoppers, hopper minecarts, and piston movement are blocked to keep storage manual and predictable.
- Breaking a chest drops its typed chest item and every stored item. Replacing it creates an empty chest of that tier.
- Automatic cleanup of stale StoneChests holograms in loaded chunks.
- Configurable chest tiers, page counts for new chests, recipes, holograms, automation blocking, cleanup interval, and debug logging.

## Chest tiers and crafting

| Chest | Pages | Storage | Recipe |
|---|---:|---:|---|
| Stone Chest | 3 | 162 slots | 8 Stone Bricks around a Chest |
| Iron Chest | 6 | 324 slots | 8 Iron Blocks around a Stone Chest |
| Diamond Chest | 9 | 486 slots | 8 Diamond Blocks around an Iron Chest |

## How it works

Place a crafted StoneChest exactly like a normal chest and right-click it to open the first storage page. While the chest is open, StoneChests temporarily places a protected navigation compass in hotbar slot 9. Your previous item in that slot is restored when you close the chest.

Every visible page is a normal 54-slot server-side inventory, so standard Minecraft actions work naturally: clicking, shift-clicking, splitting stacks, combining stacks, dragging, and hotbar swaps.

## Commands and permissions

| Command | Description | Permission |
|---|---|---|
| `/stonechests` | Shows a navigation hint. | `stonechests.use` |
| `/stonechests info` | Shows basic plugin and storage status. | `stonechests.use` |
| `/stonechests reload` | Reloads configuration and recipes. | `stonechests.reload` |

`stonechests.use` is granted to all players by default. Administrative permissions default to server operators.

## Requirements

- **Server software:** Paper 26.3
- **Java:** 25
- **Dependencies:** None

SQLite JDBC is included in the plugin JAR. No additional plugin is needed.

## Configuration

StoneChests creates `plugins/StoneChests/config.yml` on first startup. You can enable or disable individual chest types and recipes, change the page count for newly placed chests, configure holograms, choose the stale-hologram cleanup interval, disable automation blocking, and enable debug logging.

Existing chests always retain their persisted page count and contents. Changing the configuration does not silently remove stored items.

## Data and safety

Chest contents are stored in `plugins/StoneChests/stonechests.db`. Include this file in regular server backups.

StoneChests is designed for manual player storage. To prevent unexpected transfers or duplication paths, it blocks automation access and gives each open chest a single active editor. Please test every server-side plugin combination in a staging environment before using it on a production world.

## Version 1.0.5

This release fixes navigation-compass detection in open chest inventories and ensures page transitions are not interrupted by inventory-close handling. It also improves compass protection and cleans up orphaned StoneChests holograms in loaded chunks.

## Release upload details

| Field | Value |
|---|---|
| Version number | `1.0.5` |
| Version type | Release |
| Supported version | `26.3` |
| Platform | Paper |
| Java requirement | Java 25 |
| Main file | `StoneChests-1.0.5.jar` |

Upload file: `build/libs/StoneChests-1.0.5.jar`
