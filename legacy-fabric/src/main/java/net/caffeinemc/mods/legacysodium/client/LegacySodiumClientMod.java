package net.caffeinemc.mods.legacysodium.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Shared client bootstrap for Legacy Sodium. Rendering logic will be backported here from upstream Sodium.
 */
public class LegacySodiumClientMod {
    public static final Logger LOGGER = LogManager.getLogger("Legacy Sodium");

    private static String version;

    public static void onInitialization(String modVersion) {
        version = modVersion;
        LOGGER.info("Legacy Sodium {} is loading", modVersion);
    }

    public static String getVersion() {
        return version;
    }
}