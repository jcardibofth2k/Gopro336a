package me.darki.konas.mixin.mixins;

import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={CPacketAnimation.class})
public interface ICPacketAnimation {
    @Accessor(value="hand")
    void Method1602(EnumHand var1);
}