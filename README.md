# StoneChests

StoneChests is a lightweight Paper plugin that adds craftable, multi-page storage chests to vanilla-style survival servers. It requires no resource pack, client mod, or other plugin.

Each page is a standard 54-slot server-side chest inventory. Chests are persistent, protected from hopper automation, and display their storage usage in a floating hologram.

## Requirements

- Paper 26.3
- Java 25

## Installation

1. Download `StoneChests-1.0.5.jar` from the release assets.
2. Put it in your server's `plugins` folder.
3. Start or restart the server.
4. Configure the generated `plugins/StoneChests/config.yml` if needed.

StoneChests stores its data in `plugins/StoneChests/stonechests.db`. Back up this file before server upgrades or manual configuration changes.

## Chest types and recipes

| Chest | Pages | Slots | Recipe |
|---|---:|---:|---|
| Stone Chest | 3 | 162 | 8 Stone Bricks around a Chest |
| Iron Chest | 6 | 324 | 8 Iron Blocks around a Stone Chest |
| Diamond Chest | 9 | 486 | 8 Diamond Blocks around an Iron Chest |

Breaking a StoneChest drops its typed chest item and all stored contents. The placed replacement chest is empty.

## Navigation

When a StoneChest is open, a temporary navigation compass appears in hotbar slot 9.

- **Left-click:** next page
- **Right-click:** previous page

The compass only works during an active StoneChest session. It cannot be moved, dropped, traded, or placed into another inventory. The original item in hotbar slot 9 is restored when the chest is closed.

## Commands and permissions

| Command | Description | Permission |
|---|---|---|
| `/stonechests` | Shows the navigation hint. | `stonechests.use` |
| `/stonechests info` | Shows plugin and storage status. | `stonechests.use` |
| `/stonechests reload` | Reloads configuration and recipes. | `stonechests.reload` |

`stonechests.use` is granted to all players by default. `stonechests.reload` and `stonechests.admin` default to server operators.

## Configuration

`plugins/StoneChests/config.yml` is created on first start. The key options are:

| Setting | Default | Purpose |
|---|---:|---|
| `chests.<type>.enabled` | `true` | Enables a chest tier. |
| `chests.<type>.pages` | tier-specific | Page count for newly placed chests. |
| `recipes.<type>.enabled` | `true` | Enables the recipe for a chest tier. |
| `automation-blocking` | `true` | Blocks hopper transfers. |
| `holograms.enabled` | `true` | Shows floating chest information. |
| `holograms.cleanup.interval-seconds` | `300` | Interval for loaded-chunk hologram cleanup. |
| `debug` | `false` | Enables diagnostic navigation messages in the server log. |

Existing chests retain their persisted page count. Reducing a configured page count does not remove existing pages or contents.

## Holograms and protection

StoneChests uses Paper's `TextDisplay` entities for holograms. A cleanup pass runs every five minutes by default and only inspects loaded chunks. It removes only entities carrying the StoneChests marker, restores missing holograms for valid loaded chests, and never removes chest contents.

Hoppers, hopper minecarts, and piston movement are blocked for StoneChest blocks. A chest can only be edited by one player at a time.

## Data safety

After a supported inventory action has completed, StoneChests snapshots the visible Bukkit inventory and writes the chest state in an SQLite transaction. The current page is flushed before navigation, and again when the inventory is closed, the player disconnects, or the plugin is disabled.

## Verification status

The release JAR is built against `io.papermc.paper:paper-api:26.3.build.244-stable` and embeds SQLite JDBC. The project compiles successfully with Java 25.

Live Paper-server integration tests, including Inventory Profiles Next compatibility, must be performed by the server owner before using the plugin in production. In particular, test storage changes, rapid page switching, reconnects, restarts, breaking a full chest, and the navigation compass on your exact Paper build.

## Building from source

Run:

```bash
JAVA_HOME=/path/to/jdk-25 gradle build
```

The output is written to `build/libs/StoneChests-1.0.5.jar`.
