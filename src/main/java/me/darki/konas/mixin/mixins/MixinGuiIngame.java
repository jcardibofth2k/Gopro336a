package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.unremaped.Class108;
import me.darki.konas.unremaped.Class12;
import me.darki.konas.unremaped.Class49;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GuiIngame.class})
public class MixinGuiIngame {
    @Inject(method={"renderGameOverlay"}, at={@At(value="TAIL")}, cancellable=true)
    public void Method1889(float partialTicks, CallbackInfo info) {
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Konas", 3.0f, 5.0f, 0xFFFFFF);
    }

    @Inject(method={"renderPotionEffects"}, at={@At(value="HEAD")}, cancellable=true)
    protected void Method1890(ScaledResolution scaledRes, CallbackInfo info) {
        Class12 event = new Class12();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            info.cancel();
        }
    }

    @Inject(method={"renderAttackIndicator"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method1891(float partialTicks, ScaledResolution p_184045_2_, CallbackInfo ci) {
        Class108 event = new Class108();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }

    @Redirect(method={"renderGameOverlay"}, at=@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;player:Lnet/minecraft/client/entity/EntityPlayerSP;"))
    private EntityPlayerSP Method1892(Minecraft mc) {
        Class49 event = new Class49(mc.player);
        EventDispatcher.Companion.dispatch(event);
        if (event.Method275() instanceof EntityPlayerSP) {
            return (EntityPlayerSP)event.Method275();
        }
        return mc.player;
    }

    @Redirect(method={"renderPotionEffects"}, at=@At(value="FIELD", target="Lnet/minecraft/client/Minecraft;player:Lnet/minecraft/client/entity/EntityPlayerSP;"))
    private EntityPlayerSP Method1893(Minecraft mc) {
        Class49 event = new Class49(mc.player);
        EventDispatcher.Companion.dispatch(event);
        if (event.Method275() instanceof EntityPlayerSP) {
            return (EntityPlayerSP)event.Method275();
        }
        return mc.player;
    }
}