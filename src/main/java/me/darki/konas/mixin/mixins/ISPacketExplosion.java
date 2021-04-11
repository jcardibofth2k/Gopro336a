package me.darki.konas.mixin.mixins;

import net.minecraft.network.play.server.SPacketExplosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={SPacketExplosion.class})
public interface ISPacketExplosion {
    @Accessor(value="motionX")
    float getMotionX();

    @Accessor(value="motionX")
    void setMotionX(float var1);

    @Accessor(value="motionY")
    float getMotionY();

    @Accessor(value="motionY")
    void setMotionY(float var1);

    @Accessor(value="motionZ")
    float getMotionZ();

    @Accessor(value="motionZ")
    void setMotionZ(float var1);
}