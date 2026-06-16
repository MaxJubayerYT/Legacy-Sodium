<img src="legacy-fabric/src/main/resources/legacy-sodium-icon.png" width="128">

# Legacy Sodium

Legacy Sodium is a backport of [Sodium](https://github.com/CaffeineMC/sodium) for older Minecraft versions using [Legacy Fabric](https://legacyfabric.net). It aims to bring Sodium's rendering optimizations and chunk rendering pipeline to Minecraft 1.7.10, 1.8.9, and 1.13.2.

**Mod ID:** `legacy-sodium`  
**Author:** MaxJubayerYT  
**Repository:** https://github.com/MaxJubayerYT/Legacy-Sodium

---

## Supported Versions

| Minecraft | Loader        | Java | Status        |
|-----------|---------------|------|---------------|
| 1.7.10    | Legacy Fabric | 8    | Scaffolding   |
| 1.8.9     | Legacy Fabric | 8    | Scaffolding   |
| 1.13.2    | Legacy Fabric | 8    | Scaffolding   |

Each target version uses its own Gradle profile under `versions/`. Long-term, version-specific work may live on dedicated branches.

## Requirements

- **Legacy Fabric Loader** — not standard Fabric Loader for modern Minecraft
- **Legacy Fabric API** — replaces `fabric-api` for these versions
- **Java 8** for 1.7.10 and 1.8.9; Java 8 or 11 for 1.13.2

Do **not** install this mod alongside OptiFabric.

## Building

Legacy Sodium uses Gradle with **Legacy Looming** (`legacy-looming` + `fabric-loom-remap`) for obfuscated legacy versions.

```sh
# Build for the default profile (1.8.9)
./gradlew build

# Build for a specific Minecraft version
./gradlew build -PversionProfile=1.7.10
./gradlew build -PversionProfile=1.13.2

# Run the client
./gradlew :legacy-fabric:runClient

# Generate Minecraft sources
./gradlew :legacy-fabric:genSources
```

Build artifacts are written to `build/mods/`.

### Project Layout

```
common/           Version-agnostic rendering logic (backport in progress)
legacy-fabric/    Legacy Fabric loader entrypoints and mixins
versions/         Per-Minecraft-version Gradle property profiles
fabric/           Upstream modern Fabric module (excluded from default legacy build)
frapi/            Upstream FRAPI module (not used on legacy versions)
neoforge/         Upstream NeoForge module (excluded from default legacy build)
```

The default build only compiles `legacy-fabric`. To include upstream modern subprojects for reference:

```sh
./gradlew build -Plegacy.mode=false
```

## Development Notes

- 1.7.10 and 1.8.9 use the tessellator/vertex rendering pipeline — the modern Sodium chunk builder must be adapted for these versions.
- 1.13.2 is closer to modern Minecraft but still predates many current Sodium APIs.
- FRAPI (Fabric Rendering API) is **not** available on Legacy Fabric and is not a dependency.
- Mappings: Legacy Yarn via `net.legacyfabric:yarn` for all supported versions.

## License

Except where otherwise stated (see [third-party license notices](thirdparty/NOTICE.txt)), the content of this repository is provided under the [Polyform Shield 1.0.0](LICENSE.md) license by [JellySquid](https://jellysquid.me).

Legacy Sodium is a community fork maintained by MaxJubayerYT and is not affiliated with CaffeineMC.
