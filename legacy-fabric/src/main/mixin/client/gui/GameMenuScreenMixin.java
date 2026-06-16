package net.caffeinemc.mods.legacysodium.mixin.client.gui;

import net.caffeinemc.mods.legacysodium.client.gui.LegacySodiumOptionsScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Intercepts the Options button in the 1.8.9 pause menu (GuiIngameMenu → GameMenuScreen in Legacy Yarn)
 * and opens the Legacy Sodium options screen instead of vanilla GuiOptions.
 *
 * In vanilla 1.8.9 GuiIngameMenu, the Options button has id=0.
 */
@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin {

    @Inject(method = "buttonClicked", at = @At("HEAD"), cancellable = true)
    private void legacySodium$interceptOptionsButton(ButtonWidget button, CallbackInfo ci) {
        // id 0 = Options button in vanilla 1.8.9 GuiIngameMenu
        if (button.id == 0) {
            this.client.openScreen(new LegacySodiumOptionsScreen((net.minecraft.client.gui.screen.Screen)(Object)this));
            ci.cancel();
        }
    }

    // Fabric injects the `client` field via the Screen superclass
    @org.spongepowered.asm.mixin.Shadow
    protected net.minecraft.client.MinecraftClient client;
}