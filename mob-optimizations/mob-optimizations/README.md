# Mob Optimizations — Fabric 1.21.1

A client-side Fabric mod with three utilities:

| Feature    | Key     | Description                                      |
|------------|---------|--------------------------------------------------|
| **Freecam**| `F4`    | Detaches your camera — fly through walls freely  |
| **X-Ray**  | `X`     | Hides all non-ore blocks; shows ores through terrain |
| **NoClip** | `V`     | Player walks through solid blocks                |

Hold **Ctrl** (sprint) during Freecam for 3× speed.  
All three show a HUD indicator and action-bar feedback when toggled.

## X-Ray Highlights

Coal · Iron · Copper · Gold · Redstone · Emerald · Lapis · Diamond  
(deepslate variants) · Nether Gold · Quartz · Ancient Debris  
Chests · Spawners · Trial Spawners

---

## Building

### Requirements
- **JDK 21** (e.g. [Adoptium](https://adoptium.net/))
- Internet connection (Gradle downloads Minecraft + Fabric)

### Steps

```bash
# 1. Clone / unzip the project
cd mob-optimizations

# 2. Build (first run downloads dependencies – may take a few minutes)
./gradlew build        # macOS / Linux
gradlew.bat build      # Windows

# 3. Install
#    Copy build/libs/mob-optimizations-1.0.0.jar  →  .minecraft/mods/
#    Also install Fabric API (same version) if not already present.
```

### Required mods
- **Fabric Loader** ≥ 0.15
- **Fabric API** for 1.21.1

---

## Notes
- X-Ray triggers a full chunk reload on toggle (brief freeze).
- Freecam keeps your player body frozen at the original spot.
- NoClip works on the player entity; server-side anti-cheat may kick you.
