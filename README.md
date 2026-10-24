# Sandevistan — Minecraft Forge 1.20.1

A small Forge mod based on SuForgeTemplate. Right-click the **Sandevistan** item
(Tools and Utilities tab) to activate it:

- for 6 seconds you move at double speed, and a white trail of particles follows you;
- hostile mobs within 10 blocks are slowed while it lasts;
- the item goes on a 20-second cooldown.

Mod ID: `sandevistan`. Package: `net.susu609.sandevistan`.

## Build and run

```
gradle build          # jar in build/libs/
gradle runClient      # test world in run-client1/
```

The project has no `gradlew`; use a Gradle 8.x install (`gradle-wrapper.properties`
points at 8.8). Java 17 is required.

Tunables are constants in `SandevistanItem` (duration, cooldown) and
`SandevistanEffect` (slow radius, speed bonus).
