package me.darki.konas.mixin.mixins;

import net.minecraft.network.play.client.CPacketPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={CPacketPlayer.class})
public interface ICPacketPlayer {
    @Accessor(value="yaw")
    public float Method1694();

    @Accessor(value="yaw")
    public void Method1695(float var1);

    @Accessor(value="pitch")
    public float Method1696();

    @Accessor(value="pitch")
    public void Method1697(float var1);

    @Accessor(value="y")
    public void Method1698(double var1);

    @Accessor(value="y")
    public double Method1699();

    @Accessor(value="onGround")
    public void Method1700(boolean var1);

    @Accessor(value="rotating")
    public boolean Method1701();

    @Accessor(value="moving")
    public boolean Method1702();
}
