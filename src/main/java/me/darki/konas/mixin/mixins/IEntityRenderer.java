package me.darki.konas.mixin.mixins;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={EntityRenderer.class})
public interface IEntityRenderer {
    @Invoker(value="setupCameraTransform")
    void Method1908(float var1, int var2);

    @Invoker(value="orientCamera")
    void Method1909(float var1);

    @Invoker(value="getFOVModifier")
    float Method1910(float var1, boolean var2);

    @Invoker(value="updateFogColor")
    void Method1911(float var1);

    @Accessor(value="lightmapColors")
    int[] Method1912();

    @Accessor(value="lightmapTexture")
    DynamicTexture Method1913();

    @Accessor(value="torchFlickerX")
    float Method1914();

    @Accessor(value="renderEndNanoTime")
    long Method1915();

    @Accessor(value="cameraZoom")
    double Method1916();

    @Accessor(value="cameraZoom")
    void Method1917(double var1);
}