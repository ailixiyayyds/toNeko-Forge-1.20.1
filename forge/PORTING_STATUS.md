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
* The complete Forge source set now compiles and the reobfuscated Forge JAR is
  produced successfully by `:forge:build`.
* Player state calls in the migrated GUI, items, commands, events, networking,
  AI goals and respawn-copy path now go through `NekoAccess` instead of relying
  on compile-time interface injection.

## Current runnable boundary

The generated JAR is a development milestone, not yet a release candidate.
It still uses Forgified Fabric API for unported callbacks and requires a Forge
client launch test. NekoAI and JLayer also need to be embedded or otherwise
packaged with the release artifact before distribution.

## Next implementation order

1. Launch the Forge client with the generated JAR and fix loader, Mixin and
   registry failures before gameplay testing.
2. Add a Forge-owned player data API (capability/attachment wrapper), then port
   player persistence, sync and scale/energy attributes to Forge
   events and networking. Keep the public `INeko` contract as the compatibility
   surface for JustARod.
3. Replace Fabric command, lifecycle, interaction and resource callbacks with
   Forge event-bus registrations; remove the corresponding bridge use as each
   area is ported.
4. Embed the non-mod runtime libraries, run gameplay smoke tests, then link
   JustARod Forge only to the produced Forge JAR. JustARod must never depend on
   the Fabric backport JAR.

Each stage should compile before moving to the next one. This keeps native
Forge behaviour testable and avoids silently shipping a Connector-dependent
artifact.
