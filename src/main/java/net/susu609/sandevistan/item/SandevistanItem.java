package net.susu609.sandevistan.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.susu609.sandevistan.SandevistanMod;

public final class SandevistanItem extends Item {

    /** How long the effect lasts, in ticks (6 seconds). */
    private static final int DURATION = 20 * 6;
    /** Time before the item can be used again, in ticks (20 seconds). */
    private static final int COOLDOWN = 20 * 20;

    public SandevistanItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player,
                                                  InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        if (!level.isClientSide) {
            player.addEffect(new MobEffectInstance(
                    SandevistanMod.SANDEVISTAN_EFFECT.get(), DURATION, 0,
                    false, true, true));
            player.getCooldowns().addCooldown(this, COOLDOWN);
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.0F,
                    1.5F);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
