package me.darki.konas.mixin.mixins;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={World.class})
public interface IWorld {
    @Accessor(value="rainingStrength")
    float Method220();

    @Accessor(value="thunderingStrength")
    float Method221();
}
