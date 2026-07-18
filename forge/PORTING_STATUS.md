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
* Forge `DeferredRegister` now owns attributes, entities, entity attributes,
  blocks, items, effects, sounds, menus, recipes and the creative tab.
* The client reaches and runs an integrated test world with the toNeko Mixins,
  HUD, player state, entity renderers and tick callbacks active.
* Entity scale changes now affect both collision dimensions and the GeckoLib
  model transform; `0.5` was verified in the development world.
* Armed Neko held items now use GeckoLib's 1.20.1 bone render layer instead of
  multiplying a 1.21 model-space matrix, which was the source of the stretched
  triangle/body corruption. Vanilla and convention-tagged melee weapons share
  the same AI selection path.
* NekoAI and JLayer are embedded into the release artifact.
* Player state calls in the migrated GUI, items, commands, events, networking,
  AI goals and respawn-copy path now go through `NekoAccess` instead of relying
  on compile-time interface injection.

## Current runnable boundary

The generated JAR is a gameplay-test milestone, not yet a release candidate.
It still uses Forgified Fabric API for unported callbacks. Fighting/armed Neko
rendering, AI chat configuration, persistence across death/reconnect and a
clean external-modpack launch still require focused gameplay tests.
The dedicated-server launch reaches Forge's server environment and selects the
toNeko Mixins without a client-class crash, then stops at the untouched Mojang
EULA gate. A complete dedicated-world test still requires the server owner to
accept that EULA.

## Next implementation order

1. Add a Forge-owned player data API (capability/attachment wrapper), then port
   player persistence, sync and scale/energy attributes to Forge
   events and networking. Keep the public `INeko` contract as the compatibility
   surface for JustARod.
2. Replace Fabric command, lifecycle, interaction and resource callbacks with
   Forge event-bus registrations; remove the corresponding bridge use as each
   area is ported.
3. Run focused armed-Neko, AI-chat, death/reconnect and dedicated-server smoke
   tests, then link
   JustARod Forge only to the produced Forge JAR. JustARod must never depend on
   the Fabric backport JAR.

Each stage should compile before moving to the next one. This keeps native
Forge behaviour testable and avoids silently shipping a Connector-dependent
artifact.
