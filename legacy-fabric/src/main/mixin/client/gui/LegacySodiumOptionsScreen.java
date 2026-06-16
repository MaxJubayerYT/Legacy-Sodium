package net.caffeinemc.mods.legacysodium.client.gui;

import net.caffeinemc.mods.legacysodium.client.LegacySodiumClientMod;
import net.caffeinemc.mods.legacysodium.client.config.LegacySodiumConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;

/**
 * Legacy Sodium options screen for Minecraft 1.8.9.
 *
 * Uses the Legacy Yarn 1.8.9 API:
 *   Screen         = net.minecraft.client.gui.GuiScreen
 *   ButtonWidget   = net.minecraft.client.gui.GuiButton
 */
public class LegacySodiumOptionsScreen extends Screen {

    private final Screen parent;
    private LegacySodiumConfig config;

    // Button IDs
    private static final int BTN_DONE             = 0;
    private static final int BTN_ENTITY_CULLING   = 1;
    private static final int BTN_PARTICLE_CULLING = 2;
    private static final int BTN_FOG_OCCLUSION    = 3;
    private static final int BTN_FACE_CULLING     = 4;
    private static final int BTN_ANIM_TEXTURES    = 5;
    private static final int BTN_SORT_TRANSLUCENT = 6;

    public LegacySodiumOptionsScreen(Screen parent) {
        this.parent = parent;
    }

    @Override
    public void init() {
        this.config = LegacySodiumConfig.getInstance();

        int col1 = this.width / 2 - 210;
        int col2 = this.width / 2 + 10;
        int startY = this.height / 4 - 8;
        int rowH = 26;
        int btnW = 200;
        int btnH = 20;

        // Column 1
        this.buttons.add(new ButtonWidget(BTN_ENTITY_CULLING,   col1, startY,            btnW, btnH, toggleLabel("Entity Culling",          config.useEntityCulling)));
        this.buttons.add(new ButtonWidget(BTN_PARTICLE_CULLING, col1, startY + rowH,     btnW, btnH, toggleLabel("Particle Culling",         config.useParticleCulling)));
        this.buttons.add(new ButtonWidget(BTN_FOG_OCCLUSION,    col1, startY + rowH * 2, btnW, btnH, toggleLabel("Fog Occlusion",            config.useFogOcclusion)));

        // Column 2
        this.buttons.add(new ButtonWidget(BTN_FACE_CULLING,     col2, startY,            btnW, btnH, toggleLabel("Chunk Face Culling",       config.useChunkFaceCulling)));
        this.buttons.add(new ButtonWidget(BTN_ANIM_TEXTURES,    col2, startY + rowH,     btnW, btnH, toggleLabel("Animate Visible Only",     config.animateOnlyVisibleTextures)));
        this.buttons.add(new ButtonWidget(BTN_SORT_TRANSLUCENT, col2, startY + rowH * 2, btnW, btnH, toggleLabel("Sort Translucent Chunks",  config.sortTranslucentChunks)));

        // Done button centered at bottom
        this.buttons.add(new ButtonWidget(BTN_DONE, this.width / 2 - 100, this.height - 40, 200, btnH, "Done"));
    }

    @Override
    protected void buttonClicked(ButtonWidget button) {
        switch (button.id) {
            case BTN_DONE:
                config.save();
                this.client.openScreen(this.parent);
                break;
            case BTN_ENTITY_CULLING:
                config.useEntityCulling = !config.useEntityCulling;
                button.message = toggleLabel("Entity Culling", config.useEntityCulling);
                break;
            case BTN_PARTICLE_CULLING:
                config.useParticleCulling = !config.useParticleCulling;
                button.message = toggleLabel("Particle Culling", config.useParticleCulling);
                break;
            case BTN_FOG_OCCLUSION:
                config.useFogOcclusion = !config.useFogOcclusion;
                button.message = toggleLabel("Fog Occlusion", config.useFogOcclusion);
                break;
            case BTN_FACE_CULLING:
                config.useChunkFaceCulling = !config.useChunkFaceCulling;
                button.message = toggleLabel("Chunk Face Culling", config.useChunkFaceCulling);
                break;
            case BTN_ANIM_TEXTURES:
                config.animateOnlyVisibleTextures = !config.animateOnlyVisibleTextures;
                button.message = toggleLabel("Animate Visible Only", config.animateOnlyVisibleTextures);
                break;
            case BTN_SORT_TRANSLUCENT:
                config.sortTranslucentChunks = !config.sortTranslucentChunks;
                button.message = toggleLabel("Sort Translucent Chunks", config.sortTranslucentChunks);
                break;
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        this.drawCenteredString(this.textRenderer, "Legacy Sodium \u00a7aOptions", this.width / 2, 15, 0xFFFFFF);
        this.drawCenteredString(this.textRenderer, "v" + LegacySodiumClientMod.getVersion(), this.width / 2, 28, 0x888888);
        super.render(mouseX, mouseY, delta);
    }

    private static String toggleLabel(String name, boolean value) {
        return name + ": " + (value ? "\u00a7aON\u00a7r" : "\u00a7cOFF\u00a7r");
    }
}