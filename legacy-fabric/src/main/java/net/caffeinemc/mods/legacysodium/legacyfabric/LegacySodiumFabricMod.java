package net.caffeinemc.mods.legacysodium.legacyfabric;

import net.caffeinemc.mods.legacysodium.client.LegacySodiumClientMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LegacySodiumFabricMod implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("Legacy Sodium");

    @Override
    public void onInitializeClient() {
        ModContainer mod = FabricLoader.getInstance()
                .getModContainer("legacy-sodium")
                .orElseThrow(() -> new IllegalStateException("Legacy Sodium mod container missing"));

        String version = mod.getMetadata().getVersion().getFriendlyString();
        LegacySodiumClientMod.onInitialization(version);

        LOGGER.info("Legacy Sodium {} initialized for Minecraft {}", version, getMinecraftVersion());
    }

    private static String getMinecraftVersion() {
        return FabricLoader.getInstance().getModContainer("minecraft")
                .map(container -> container.getMetadata().getVersion().getFriendlyString())
                .orElse("unknown");
    }
}
