package net.susu609.sandevistan.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.susu609.sandevistan.SandevistanMod;

/**
 * Local-player feedback while Sandevistan is active: the view narrows and a
 * cyan tint fades in and out. Only the activating client runs this; other
 * players see the faster movement through the server.
 */
@Mod.EventBusSubscriber(modid = SandevistanMod.MOD_ID, value = Dist.CLIENT,
                        bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class SandevistanClientEvents {

    /** Multiplier on the FOV; below 1 zooms in. */
    private static final float FOV_SCALE = 0.75F;
    /** Peak tint alpha (0-255) at the start of the effect. */
    private static final int MAX_TINT_ALPHA = 0x40;
    /** Tint colour without alpha, cyan to match the particles. */
    private static final int TINT_RGB = 0x33CCFF;
    /** Ticks over which the tint fades out at the end of the effect. */
    private static final int FADE_TICKS = 10;

    private SandevistanClientEvents() {
    }

    @SubscribeEvent
    public static void onComputeFov(ComputeFovModifierEvent event) {
        if (activeEffect(event.getPlayer()) != null) {
            event.setNewFovModifier(event.getNewFovModifier() * FOV_SCALE);
        }
    }

    @SubscribeEvent
    public static void onRenderHotbar(RenderGuiOverlayEvent.Post event) {
        // Draw the tint once per frame, after the hotbar, not after every
        // overlay.
        if (event.getOverlay() != VanillaGuiOverlay.HOTBAR.type()) {
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        MobEffectInstance effect = activeEffect(minecraft.player);
        if (effect == null) {
            return;
        }

        int alpha = Math.min(MAX_TINT_ALPHA,
                effect.getDuration() * MAX_TINT_ALPHA / FADE_TICKS);
        if (alpha <= 0) {
            return;
        }
        int width = event.getWindow().getGuiScaledWidth();
        int height = event.getWindow().getGuiScaledHeight();
        event.getGuiGraphics().fill(0, 0, width, height,
                (alpha << 24) | TINT_RGB);
    }

    private static MobEffectInstance activeEffect(Player player) {
        if (player == null) {
            return null;
        }
        return player.getEffect(SandevistanMod.SANDEVISTAN_EFFECT.get());
    }
}
