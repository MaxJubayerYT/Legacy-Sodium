package net.caffeinemc.mods.legacysodium.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.caffeinemc.mods.legacysodium.client.LegacySodiumClientMod;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LegacySodiumConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static LegacySodiumConfig instance;

    // General
    public boolean animateOnlyVisibleTextures = true;
    public boolean useEntityCulling = true;
    public boolean useParticleCulling = true;
    public boolean useFogOcclusion = true;

    // Chunk rendering
    public boolean useChunkFaceCulling = true;
    public boolean sortTranslucentChunks = true;

    public static LegacySodiumConfig getInstance() {
        if (instance == null) {
            instance = load();
        }
        return instance;
    }

    private static File getConfigFile() {
        return new File(MinecraftClient.getInstance().runDirectory, "config/legacy-sodium.json");
    }

    private static LegacySodiumConfig load() {
        File file = getConfigFile();
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                return GSON.fromJson(reader, LegacySodiumConfig.class);
            } catch (IOException e) {
                LegacySodiumClientMod.LOGGER.error("Failed to load Legacy Sodium config, using defaults", e);
            }
        }
        LegacySodiumConfig config = new LegacySodiumConfig();
        config.save();
        return config;
    }

    public void save() {
        File file = getConfigFile();
        file.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            LegacySodiumClientMod.LOGGER.error("Failed to save Legacy Sodium config", e);
        }
    }
}