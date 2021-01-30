package me.darki.konas.mixin.mixins;

import net.minecraft.network.play.server.SPacketPlayerPosLook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={SPacketPlayerPosLook.class})
public interface ISPacketPlayerPosLook {
    @Accessor(value="yaw")
    public void Method40(float var1);

    @Accessor(value="pitch")
    public void Method41(float var1);
}
