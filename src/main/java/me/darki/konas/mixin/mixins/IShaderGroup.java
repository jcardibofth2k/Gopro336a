package me.darki.konas.mixin.mixins;

import java.util.List;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={ShaderGroup.class})
public interface IShaderGroup {
    @Accessor(value="listShaders")
    List<Shader> Method1220();
}