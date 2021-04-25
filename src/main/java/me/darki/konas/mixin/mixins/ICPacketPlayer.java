package me.darki.konas.mixin.mixins;

import net.minecraft.network.play.client.CPacketPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={CPacketPlayer.class})
public interface ICPacketPlayer {
    @Accessor(value="yaw")
    float Method1694();

    @Accessor(value="yaw")
    void Method1695(float var1);

    @Accessor(value="pitch")
    float Method1696();

    @Accessor(value="pitch")
    void Method1697(float var1);

    @Accessor(value="y")
    void Method1698(double var1);

    @Accessor(value="y")
    double Method1699();

    @Accessor(value="onGround")
    void Method1700(boolean var1);

    @Accessor(value="rotating")
    boolean Method1701();

    @Accessor(value="moving")
    boolean Method1702();
}
