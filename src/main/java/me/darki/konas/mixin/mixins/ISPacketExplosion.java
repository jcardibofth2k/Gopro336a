package me.darki.konas.mixin.mixins;

import net.minecraft.network.play.server.SPacketExplosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={SPacketExplosion.class})
public interface ISPacketExplosion {
    @Accessor(value="motionX")
    float Method470();

    @Accessor(value="motionX")
    void Method471(float var1);

    @Accessor(value="motionY")
    float Method472();

    @Accessor(value="motionY")
    void Method473(float var1);

    @Accessor(value="motionZ")
    float Method474();

    @Accessor(value="motionZ")
    void Method475(float var1);
}