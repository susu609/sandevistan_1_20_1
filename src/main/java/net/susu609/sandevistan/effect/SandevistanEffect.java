package net.susu609.sandevistan.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.phys.AABB;

/**
 * Doubles the holder's movement speed and slows the hostile mobs near them.
 * The effect is ticked on the server; the trail is sent to the clients as
 * particles.
 */
public final class SandevistanEffect extends MobEffect {

    private static final double SLOW_RADIUS = 10.0D;

    public SandevistanEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x33CCFF);
        addAttributeModifier(Attributes.MOVEMENT_SPEED,
                "5a2b7c1e-9f3d-4e8a-b6c4-2d1f0e9a8b7c", 1.0D,
                AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!(entity.level() instanceof ServerLevel level)) {
            return;
        }

        AABB area = entity.getBoundingBox().inflate(SLOW_RADIUS);
        for (Monster mob : level.getEntitiesOfClass(Monster.class, area)) {
            // Short and refreshed every tick, so it ends right after the
            // effect does.
            mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
                    10, 3, false, false, false));
        }

        // Every other tick is enough for a trail and halves the packets.
        if (entity.tickCount % 2 == 0) {
            level.sendParticles(ParticleTypes.END_ROD, entity.getX(),
                    entity.getY(0.5D), entity.getZ(), 3, 0.2D, 0.4D, 0.2D,
                    0.01D);
        }
    }
}
