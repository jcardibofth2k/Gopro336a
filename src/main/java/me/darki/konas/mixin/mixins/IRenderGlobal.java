package me.darki.konas.mixin.mixins;

import java.util.Map;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.shader.ShaderGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={RenderGlobal.class})
public interface IRenderGlobal {
    @Accessor(value="entityOutlineShader")
    ShaderGroup Method73();

    @Accessor(value="damagedBlocks")
    Map<Integer, DestroyBlockProgress> Method74();
}
