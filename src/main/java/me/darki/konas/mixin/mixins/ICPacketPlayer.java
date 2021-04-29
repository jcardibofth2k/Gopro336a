package me.darki.konas.mixin.mixins;

import net.minecraft.network.play.client.CPacketPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={CPacketPlayer.class})
public interface ICPacketPlayer {
    @Accessor(value="yaw")
    float getYaw();

    @Accessor(value="yaw")
    void setYaw(float var1);

    @Accessor(value="pitch")
    float getPitch();

    @Accessor(value="pitch")
    void setPitch(float var1);

    @Accessor(value="y")
    void setY(double var1);

    @Accessor(value="y")
    double getY();

    @Accessor(value="onGround")
    void setOnGround(boolean var1);

    @Accessor(value="rotating")
    boolean isRotating();

    @Accessor(value="moving")
    boolean isMoving();
}