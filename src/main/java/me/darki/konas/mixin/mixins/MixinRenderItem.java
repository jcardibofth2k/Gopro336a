package me.darki.konas.mixin.mixins;

import me.darki.konas.module.ModuleManager;
import me.darki.konas.unremaped.EnchantColor;
import me.darki.konas.module.render.ESP;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={RenderItem.class})
public class MixinRenderItem {
    @Inject(method={"renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method1885(ItemStack stack, ItemCameraTransforms.TransformType cameraTransformType, CallbackInfo ci) {
        if (ESP.Field1342) {
            ci.cancel();
        }
    }

    @Inject(method={"renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;Z)V"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method1886(ItemStack stack, EntityLivingBase entitylivingbaseIn, ItemCameraTransforms.TransformType transform, boolean leftHanded, CallbackInfo ci) {
        if (ESP.Field1342) {
            ci.cancel();
        }
    }

    @ModifyArg(method={"renderEffect"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/RenderItem;renderModel(Lnet/minecraft/client/renderer/block/model/IBakedModel;I)V"), index=1)
    public int Method1887(int in) {
        if (ModuleManager.getModuleByClass(EnchantColor.class).isEnabled()) {
            return EnchantColor.enchantColor.getValue().Method774();
        }
        return in;
    }
}