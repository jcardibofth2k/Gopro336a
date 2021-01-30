package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.Class167;
import me.darki.konas.ColorValue;
import me.darki.konas.Class478;
import me.darki.konas.module.render.ESP;
import me.darki.konas.Class569;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={LayerArmorBase.class})
public class MixinLayerArmorBase {
    @Inject(method={"renderArmorLayer"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method892(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, EntityEquipmentSlot slotIn, CallbackInfo ci) {
        if (ESP.Field1342) {
            ci.cancel();
            return;
        }
        Class569 event = Class569.Method664(slotIn);
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Redirect(method={"renderEnchantedGlint"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V"))
    private static void Method893(float colorRed, float colorGreen, float colorBlue, float colorAlpha) {
        if (colorBlue == 0.608f && Class167.Method1610(Class478.class).Method1651()) {
            GlStateManager.color((float)((float)((ColorValue)Class478.Field2508.getValue()).Method769() / 255.0f), (float)((float)((ColorValue)Class478.Field2508.getValue()).Method770() / 255.0f), (float)((float)((ColorValue)Class478.Field2508.getValue()).Method779() / 255.0f), (float)((float)((ColorValue)Class478.Field2508.getValue()).Method782() / 255.0f));
        } else {
            GlStateManager.color((float)colorRed, (float)colorGreen, (float)colorBlue, (float)colorAlpha);
        }
    }
}
