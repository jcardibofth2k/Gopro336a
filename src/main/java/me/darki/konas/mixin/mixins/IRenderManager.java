package me.darki.konas.mixin.mixins;

import net.minecraft.client.renderer.entity.RenderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={RenderManager.class})
public interface IRenderManager {
    @Accessor(value="renderPosX")
    double Method69();

    @Accessor(value="renderPosY")
    double Method70();

    @Accessor(value="renderPosZ")
    double Method71();
}