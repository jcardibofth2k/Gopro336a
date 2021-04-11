package me.darki.konas.mixin.mixins;

import net.minecraft.network.play.server.SPacketEntityVelocity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={SPacketEntityVelocity.class})
public interface ISPacketEntityVelocity {
    @Accessor(value="motionX")
    int getMotionX();

    @Accessor(value="motionX")
    void setMotionX(int var1);

    @Accessor(value="motionY")
    int getMotionY();

    @Accessor(value="motionY")
    void setMotionY(int var1);

    @Accessor(value="motionZ")
    int getMotionZ();

    @Accessor(value="motionZ")
    void setMotionZ(int var1);
}