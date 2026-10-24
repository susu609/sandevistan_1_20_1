package net.susu609.sandevistan.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.susu609.sandevistan.SandevistanMod;

/**
 * Slows the world for the player who has Sandevistan on their own client.
 * Other entities only get their client-side tick on every {@link #FACTOR}th
 * client tick; the local player always ticks, so they move at full speed.
 * Other clients are not affected.
 */
@Mod.EventBusSubscriber(modid = SandevistanMod.MOD_ID, value = Dist.CLIENT,
                        bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ClientTimeDilation {

    /** The world runs at 1 / FACTOR of its normal speed for the activator. */
    public static final int FACTOR = 4;

    private static int clientTicks;

    private ClientTimeDilation() {
    }

    /**
     * Whether the entity's tick is skipped this client tick. Called from the
     * client level's entity loop.
     */
    public static boolean skipsTick(Entity entity) {
        Player local = Minecraft.getInstance().player;
        if (local == null || entity == local
                || !local.hasEffect(SandevistanMod.SANDEVISTAN_EFFECT.get())) {
            return false;
        }
        return clientTicks % FACTOR != 0;
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            clientTicks++;
        }
    }
}
