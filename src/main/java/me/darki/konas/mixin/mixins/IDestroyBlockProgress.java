package me.darki.konas.mixin.mixins;

import net.minecraft.client.renderer.DestroyBlockProgress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={DestroyBlockProgress.class})
public interface IDestroyBlockProgress {
    @Accessor(value="miningPlayerEntId")
    int getMiningPlayerEntId();
}