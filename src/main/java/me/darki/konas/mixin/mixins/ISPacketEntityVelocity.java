package me.darki.konas.mixin.mixins;

import net.minecraft.network.play.server.SPacketEntityVelocity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={SPacketEntityVelocity.class})
public interface ISPacketEntityVelocity {
    @Accessor(value="motionX")
    int Method75();

    @Accessor(value="motionX")
    void Method76(int var1);

    @Accessor(value="motionY")
    int Method77();

    @Accessor(value="motionY")
    void Method78(int var1);

    @Accessor(value="motionZ")
    int Method79();

    @Accessor(value="motionZ")
    void Method80(int var1);
}
