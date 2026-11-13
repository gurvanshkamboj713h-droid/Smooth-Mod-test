# Smooth360 (Fabric 1.21.11)

A minimal client-side Fabric mod source project.

## Behavior
- Tap **Mouse Button 4** (the fourth physical mouse button / common back button) to start one smooth 360-degree view rotation.
- The spin takes about 0.4 seconds (8 game ticks at 20 TPS).
- Pitch is held constant and yaw is returned to its starting value.
- No HUD, keybind screen, configuration screen, or Mod Menu API integration is included.

## Important limitations
- This project has **not been compiled or tested here**. Build it locally before using it.
- The mod is client-side, but rotation updates may still be observable by a multiplayer server. Server rules and anti-cheat behavior vary; no undetectability or universal multiplayer compatibility is promised.
- Not integrating with Mod Menu means there is no custom settings page. It does **not** hide the mod from Fabric Loader's installed-mod list or from server-side inspection where applicable.
- Some mice/drivers expose side buttons differently. If Mouse Button 4 does not trigger, verify the button index with your mouse software.

## Build
Requirements: JDK 21 and Gradle 8.14 or compatible.

From this folder, run:

```bash
gradle build
```

The remapped mod jar should be created in `build/libs/`.

## Install
Copy the resulting `smooth360-1.0.0.jar` into the Minecraft instance's `mods` folder, together with Fabric API for Minecraft 1.21.11.

## Change spin duration
Edit `SPIN_TICKS` in `Smooth360Client.java`. Larger values make the spin slower and smoother.
