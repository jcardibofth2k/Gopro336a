package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.unremaped.Class100;
import me.darki.konas.unremaped.Class166;
import me.darki.konas.unremaped.Class509;
import me.darki.konas.unremaped.Class98;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={RenderManager.class})
public class MixinRenderManager {
    @Redirect(method={"getEntityRenderObject"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/entity/AbstractClientPlayer;getSkinType()Ljava/lang/String;"))
    public String Method1290(AbstractClientPlayer abstractClientPlayer) {
        if (Minecraft.getMinecraft().currentScreen instanceof Class166) {
            return Class509.Method1357(Minecraft.getMinecraft().getSession().getProfile().getId().toString()) ? "slim" : "default";
        }
        return abstractClientPlayer.getSkinType();
    }

    @Inject(method={"renderEntity"}, at={@At(value="INVOKE")}, cancellable=true)
    public void Method1291(Entity entityIn, double x, double y, double z, float yaw, float partialTicks, boolean p_188391_10_, CallbackInfo ci) {
        Class98 pre = new Class98(entityIn, x, y, z, yaw, partialTicks);
        EventDispatcher.Companion.dispatch(pre);
        if (pre.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(method={"renderEntity"}, at={@At(value="TAIL")})
    public void Method1292(Entity entityIn, double x, double y, double z, float yaw, float partialTicks, boolean p_188391_10_, CallbackInfo ci) {
        Class100 post = new Class100(entityIn, x, y, z, yaw, partialTicks);
        EventDispatcher.Companion.dispatch(post);
    }
}