# Forge 1.20.1 porting status

This directory is the native Forge 1.20.1 build target. It produces a Forge
mod, uses ForgeGradle and does not use Connector.

## Current state

* ForgeGradle resolves the Minecraft 1.20.1 / Forge 47.4.21 development
  classpath and the Forge-specific GeckoLib and Architectury libraries.
* Forgified Fabric API is a temporary Forge runtime bridge for existing public
  API calls. It is not a Fabric loader bridge and it is not Connector.
* Fabric-only EMI entrypoints and the development-only `testgal` command are
  excluded from the Forge source set.
* The armor renderer uses Forge's `IClientItemExtensions` API for GeckoLib
  4.4.9.

## Blocking migration boundary

The original common source relies on Architectury Loom interface injection:
`Player` and other Minecraft classes are compiled as though they directly
implement `INeko` and related interfaces. ForgeGradle does not alter the
compile-time Minecraft classes this way, so calls such as `player.isNeko()`
must be moved behind an explicit Forge API before a complete Forge JAR can be
compiled.

## Next implementation order

1. Add a Forge-owned player data API (capability/attachment wrapper) and
   replace direct `Player` extension calls in common gameplay code.
2. Port the player persistence, sync and scale/energy attributes to Forge
   events and networking. Keep the public `INeko` contract as the compatibility
   surface for JustARod.
3. Replace Fabric command, lifecycle, interaction and resource callbacks with
   Forge event-bus registrations; remove the corresponding bridge use as each
   area is ported.
4. Build and run toNeko Forge, then link JustARod Forge only to that produced
   JAR. JustARod must never depend on the Fabric backport JAR.

Each stage should compile before moving to the next one. This keeps native
Forge behaviour testable and avoids silently shipping a Connector-dependent
artifact.
