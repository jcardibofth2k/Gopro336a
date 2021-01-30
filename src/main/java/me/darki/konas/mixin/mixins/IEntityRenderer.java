package me.darki.konas.mixin.mixins;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={EntityRenderer.class})
public interface IEntityRenderer {
    @Invoker(value="setupCameraTransform")
    public void Method1908(float var1, int var2);

    @Invoker(value="orientCamera")
    public void Method1909(float var1);

    @Invoker(value="getFOVModifier")
    public float Method1910(float var1, boolean var2);

    @Invoker(value="updateFogColor")
    public void Method1911(float var1);

    @Accessor(value="lightmapColors")
    public int[] Method1912();

    @Accessor(value="lightmapTexture")
    public DynamicTexture Method1913();

    @Accessor(value="torchFlickerX")
    public float Method1914();

    @Accessor(value="renderEndNanoTime")
    public long Method1915();

    @Accessor(value="cameraZoom")
    public double Method1916();

    @Accessor(value="cameraZoom")
    public void Method1917(double var1);
}
