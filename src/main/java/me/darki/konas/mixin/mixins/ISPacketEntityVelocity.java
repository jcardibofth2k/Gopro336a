package me.darki.konas.mixin.mixins;

import net.minecraft.network.play.server.SPacketEntityVelocity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={SPacketEntityVelocity.class})
public interface ISPacketEntityVelocity {
    @Accessor(value="motionX")
    public int Method75();

    @Accessor(value="motionX")
    public void Method76(int var1);

    @Accessor(value="motionY")
    public int Method77();

    @Accessor(value="motionY")
    public void Method78(int var1);

    @Accessor(value="motionZ")
    public int Method79();

    @Accessor(value="motionZ")
    public void Method80(int var1);
}
