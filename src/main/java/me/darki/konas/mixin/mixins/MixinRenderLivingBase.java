package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.unremaped.Class122;
import me.darki.konas.unremaped.RenderLivingBaseEvent;
import me.darki.konas.unremaped.Class139;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.module.render.Chams;
import me.darki.konas.module.render.ESP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={RenderLivingBase.class}, priority=0x7FFFFFFF)
public abstract class MixinRenderLivingBase
extends Render {
    @Shadow
    protected ModelBase Field16;

    protected MixinRenderLivingBase(RenderManager renderManager) {
        super(renderManager);
    }

    @Shadow
    protected abstract void Method34(T var1, float var2, float var3, float var4, float var5, float var6, float var7);

    @Shadow
    protected abstract boolean Method35(T var1);

    @Redirect(method={"doRender"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/renderer/entity/RenderLivingBase;renderModel(Lnet/minecraft/entity/EntityLivingBase;FFFFFF)V"))
    public void Method36(RenderLivingBase renderLivingBase, T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        RenderLivingBaseEvent pre = new RenderLivingBaseEvent(renderLivingBase.getMainModel(), (Entity)entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        if (!ESP.Field1342) {
            EventDispatcher.Companion.dispatch(pre);
        }
        if (!pre.isCanceled()) {
            if (ModuleManager.getModuleByClass(Chams.class).isEnabled() || ModuleManager.getModuleByClass(ESP.class).isEnabled()) {
                boolean flag1;
                boolean flag = this.Method35(entity);
                boolean bl = flag1 = !flag && !entity.isInvisibleToPlayer((EntityPlayer)Minecraft.getMinecraft().player);
                if (flag || flag1) {
                    if (!this.bindEntityTexture((Entity)entity)) {
                        if (!ESP.Field1342) {
                            Class122 post = new Class122(renderLivingBase.getMainModel(), (Entity)entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                            EventDispatcher.Companion.dispatch(post);
                        }
                        return;
                    }
                    if (flag1) {
                        GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
                    }
                    if ((ModuleManager.getModuleByClass(Chams.class).isEnabled() || Chams.Field2147) && !ESP.Field1342 && Chams.Method1952(this.Field16, entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor)) {
                        if (!ESP.Field1342) {
                            Class122 post = new Class122(renderLivingBase.getMainModel(), (Entity)entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                            EventDispatcher.Companion.dispatch(post);
                        }
                        return;
                    }
                    this.Field16.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                    if (flag1) {
                        GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
                    }
                }
            } else {
                this.Method34(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            }
        }
        if (!ESP.Field1342) {
            Class122 post = new Class122(renderLivingBase.getMainModel(), (Entity)entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            EventDispatcher.Companion.dispatch(post);
        }
    }

    @Inject(method={"doRender"}, at={@At(value="RETURN")})
    public void Method37(T entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (entity instanceof EntityPlayer) {
            ESP.Method1326((EntityPlayer)entity, (ModelPlayer)this.Field16, partialTicks);
        }
    }

    @Inject(method={"renderName"}, at={@At(value="HEAD")}, cancellable=true)
    private void Method38(EntityLivingBase entity, double x, double y, double z, CallbackInfo ci) {
        if (ESP.Field1342) {
            ci.cancel();
            return;
        }
        Class139 event = new Class139(entity);
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
}