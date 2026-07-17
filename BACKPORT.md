# toNeko Minecraft 1.20.1 Backport

This repository is a public, community-maintained backport of the original
[CSneko/toNeko](https://github.com/CSneko/toNeko) project.

## Project status

- Target: Minecraft 1.20.1 with Fabric
- Upstream baseline: `V1.9.0` (`21790e31c6073bb941e6af1f0f2ecfa3c06ead2e`)
- Development branch: `1.20.1-backport`
- Status: the common and Fabric source sets compile on Java 17, the remapped
  Fabric JAR builds successfully, a dedicated-server development launch passes
  Loader, Mixin, registry, and mod initialization before the expected first-run
  EULA stop, and the core client reaches the main menu. Gameplay verification
  is the next milestone.
- API reference: upstream tag `V1.3.9-1.20` is an actual Minecraft 1.20.1
  codebase and is used as a compatibility reference; it is not treated as the
  1.9.0 feature baseline.
- Relationship to upstream: unofficial; issues specific to this backport belong in this fork

The initial build, dedicated-server startup, and core client startup milestones
are complete. The next milestone is gameplay verification on Java 17. NeoForge
support is outside the first milestone.

Ported API groups include network payloads, synced entity data, recipes,
advancements, attributes, item NBT, GeckoLib rendering, screens, Fabric entity
registration, data generation, and optional EMI/Trinkets integration.

Current compatibility notes:

- The 1.21-only fall-damage, burning-time, and gravity attributes have no
  vanilla 1.20.1 equivalents; their allele entries remain registered but their
  attribute modifiers are temporarily inactive.
- Player leash state is implemented through a 1.20.1 compatibility interface;
  movement/visual parity still requires in-game verification.
- Trinkets items register and render, while their 1.21-era extra attribute and
  slot modifiers are temporarily omitted pending runtime testing against the
  1.20.1 Trinkets API. Trinkets is kept compile-only in the Mojang-mappings
  development environment because its Yarn refmap fails there; production
  interoperability remains a separate test item.

## License and attribution

The original project and this backport are licensed under GPL-3.0. Copyright
and attribution notices from upstream are retained. When distributing modified
binaries, distribute the corresponding source and GPL-3.0 license as required.
