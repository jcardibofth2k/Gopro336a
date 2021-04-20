package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.unremaped.Class131;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Render.class})
public class MixinRender {
    @Inject(method={"renderShadow"}, at={@At(value="HEAD")}, cancellable=true)
    public void Method148(Entity entityIn, double x, double y, double z, float shadowAlpha, float partialTicks, CallbackInfo ci) {
        if (Minecraft.getMinecraft().player != null && entityIn.equals(Minecraft.getMinecraft().player)) {
            Class131 event = new Class131();
            EventDispatcher.Companion.dispatch(event);
            if (event.isCanceled()) {
                ci.cancel();
            }
        }
    }
}