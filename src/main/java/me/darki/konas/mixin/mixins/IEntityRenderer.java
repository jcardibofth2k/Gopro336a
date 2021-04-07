package me.darki.konas.mixin.mixins;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={EntityRenderer.class})
public interface IEntityRenderer {
    @Invoker(value="setupCameraTransform")
    void setupCameraTransform(float var1, int var2);

    @Invoker(value="orientCamera")
    void orientCamera(float var1);

    @Invoker(value="getFOVModifier")
    float getFOVModifier(float var1, boolean var2);

    @Invoker(value="updateFogColor")
    void updateFogColor(float var1);

    @Accessor(value="lightmapColors")
    int[] getLightmapColors();

    @Accessor(value="lightmapTexture")
    DynamicTexture getLightmapTexture();

    @Accessor(value="torchFlickerX")
    float getTorchFlickerX();

    @Accessor(value="renderEndNanoTime")
    long getRenderEndNanoTime();

    @Accessor(value="cameraZoom")
    double getCameraZoom();

    @Accessor(value="cameraZoom")
    void setCameraZoom(double var1);
}