package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.unremaped.Class119;
import me.darki.konas.unremaped.Class31;
import me.darki.konas.unremaped.Class46;
import me.darki.konas.unremaped.Class49;
import me.darki.konas.unremaped.Class65;
import me.darki.konas.settingEnums.ViewModelMode;
import me.darki.konas.unremaped.Class659;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ItemRenderer.class})
public abstract class MixinItemRenderer {
    @Shadow
    protected abstract void Method1896(EnumHandSide var1, float var2);

    @Shadow
    protected abstract void Method1897(EnumHandSide var1, float var2);

    @Inject(method={"transformSideFirstPerson"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method1898(EnumHandSide hand, float y, CallbackInfo ci) {
        int i = hand == EnumHandSide.RIGHT ? 1 : -1;
        Class659 event = new Class659((float)i * 0.56f, -0.52f + y * -0.6f, -0.72f, 1.0f, 1.0f, 1.0f, hand == EnumHandSide.LEFT ? ViewModelMode.OFFHAND : ViewModelMode.MAINHAND);
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            ci.cancel();
            GlStateManager.translate(event.Method1109(), event.Method258(), event.Method213());
            GlStateManager.scale(event.Method1108(), event.Method215(), event.Method1110());
            GlStateManager.rotate(event.Method1107(), 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(event.Method260(), 0.0f, 1.0f, 0.0f);
            GlStateManager.rotate(event.Method340(), 0.0f, 0.0f, 1.0f);
        }
    }

    @Inject(method={"updateEquippedItem"}, at={@At(value="HEAD")}, cancellable=true)
    private void Method1899(CallbackInfo ci) {
        Class65 event = new Class65();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(method={"transformEatFirstPerson"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method1900(float p_187454_1_, EnumHandSide hand, ItemStack stack, CallbackInfo ci) {
        Class31 event = new Class31(p_187454_1_, hand, stack);
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(method={"renderItemInFirstPerson(F)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void Method1901(float partialTicks, CallbackInfo ci) {
        Class119 event = new Class119();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Redirect(method={"setLightmap"}, at=@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;player:Lnet/minecraft/client/entity/EntityPlayerSP;"))
    private EntityPlayerSP Method1902(Minecraft mc) {
        Class49 event = new Class49(mc.player);
        EventDispatcher.Companion.dispatch(event);
        return (EntityPlayerSP)event.Method275();
    }

    @Redirect(method={"rotateArm"}, at=@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;player:Lnet/minecraft/client/entity/EntityPlayerSP;"))
    private EntityPlayerSP Method1903(Minecraft mc) {
        Class49 event = new Class49(mc.player);
        EventDispatcher.Companion.dispatch(event);
        return (EntityPlayerSP)event.Method275();
    }

    @Redirect(method={"renderItemInFirstPerson(F)V"}, at=@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;player:Lnet/minecraft/client/entity/EntityPlayerSP;"))
    private EntityPlayerSP Method1904(Minecraft mc) {
        Class49 event = new Class49(mc.player);
        EventDispatcher.Companion.dispatch(event);
        return (EntityPlayerSP)event.Method275();
    }

    @Inject(method={"renderOverlays"}, at={@At(value="HEAD")}, cancellable=true)
    private void Method1905(float partialTicks, CallbackInfo ci) {
        Class46 event = new Class46();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Redirect(method={"renderOverlays"}, at=@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;player:Lnet/minecraft/client/entity/EntityPlayerSP;"))
    private EntityPlayerSP Method1906(Minecraft mc) {
        Class49 event = new Class49(mc.player);
        EventDispatcher.Companion.dispatch(event);
        return (EntityPlayerSP)event.Method275();
    }
}