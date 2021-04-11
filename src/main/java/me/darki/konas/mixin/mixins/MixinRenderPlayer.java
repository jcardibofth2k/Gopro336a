package me.darki.konas.mixin.mixins;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.unremaped.Class102;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.unremaped.Class419;
import me.darki.konas.unremaped.Class46;
import me.darki.konas.module.render.Chams;
import me.darki.konas.settingEnums.ChamsGlintMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={RenderPlayer.class})
public abstract class MixinRenderPlayer {
    private float Field0;
    private float Field1;
    private float Field2;
    private float Field3;
    private float Field4 = 0.0f;
    private float Field5;
    private float Field6 = 0.0f;
    private boolean Field7 = false;

    @Shadow
    public abstract ModelPlayer Method1();

    @Shadow
    protected abstract void Method2(AbstractClientPlayer var1);

    @Inject(method={"doRender"}, at={@At(value="HEAD")})
    private void Method3(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (ModuleManager.getModuleByClass(Class419.class).isEnabled() && entity == Minecraft.getMinecraft().player) {
            this.Field3 = entity.prevRotationYawHead;
            this.Field5 = entity.prevRotationPitch;
            this.Field0 = entity.rotationPitch;
            this.Field1 = entity.rotationYaw;
            this.Field2 = entity.rotationYawHead;
            entity.rotationPitch = Class419.Method1115();
            entity.prevRotationPitch = this.Field6;
            entity.rotationYaw = Class419.Method1114();
            entity.rotationYawHead = Class419.Method1114();
            entity.prevRotationYawHead = this.Field4;
        }
    }

    @Inject(method={"doRender"}, at={@At(value="RETURN")})
    private void Method4(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (ModuleManager.getModuleByClass(Class419.class).isEnabled() && entity == Minecraft.getMinecraft().player) {
            this.Field4 = entity.rotationYawHead;
            this.Field6 = entity.rotationPitch;
            entity.rotationPitch = this.Field0;
            entity.rotationYaw = this.Field1;
            entity.rotationYawHead = this.Field2;
            entity.prevRotationYawHead = this.Field3;
            entity.prevRotationPitch = this.Field5;
        }
    }

    @Inject(method={"renderRightArm"}, at={@At(value="FIELD", target="Lnet/minecraft/client/model/ModelPlayer;swingProgress:F", opcode=181)}, cancellable=true)
    public void Method5(AbstractClientPlayer clientPlayer, CallbackInfo ci) {
        if (clientPlayer == Minecraft.getMinecraft().player) {
            Class102 event = new Class102();
            EventDispatcher.Companion.dispatch(event);
            if (event.isCanceled()) {
                GL11.glPushAttrib(1048575);
                GL11.glDisable(3008);
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glLineWidth(1.5f);
                GL11.glEnable(2960);
                GL11.glEnable(10754);
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
                GL11.glColor4f(event.Method1108() / 255.0f, event.Method215() / 255.0f, event.Method214() / 255.0f, event.Method213() / 255.0f);
                this.Field7 = true;
            }
        }
    }

    @Inject(method={"renderRightArm"}, at={@At(value="RETURN")}, cancellable=true)
    public void Method6(AbstractClientPlayer clientPlayer, CallbackInfo ci) {
        if (clientPlayer == Minecraft.getMinecraft().player && this.Field7) {
            GL11.glEnable(3042);
            GL11.glEnable(2896);
            GL11.glEnable(3553);
            GL11.glEnable(3008);
            GL11.glPopAttrib();
            this.Field7 = false;
        }
        if (Chams.hGlint.getValue() != ChamsGlintMode.NONE && ModuleManager.getModuleByClass(Chams.class).isEnabled()) {
            ModelPlayer modelplayer = this.Method1();
            this.Method2(clientPlayer);
            GlStateManager.enableBlend();
            modelplayer.swingProgress = 0.0f;
            modelplayer.isSneak = false;
            modelplayer.setRotationAngles(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f, clientPlayer);
            GlStateManager.pushMatrix();
            Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(Chams.hGlint.getValue() == ChamsGlintMode.CUSTOM ? Chams.location1 : Chams.location);
            GL11.glPushAttrib(1048575);
            GL11.glPolygonMode(1032, 6914);
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glEnable(3042);
            GL11.glColor4f((float) Chams.hGlintColor.getValue().Method769() / 255.0f, (float) Chams.hGlintColor.getValue().Method770() / 255.0f, (float) Chams.hGlintColor.getValue().Method779() / 255.0f, (float) Chams.hGlintColor.getValue().Method782() / 255.0f);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
            for (int i = 0; i < 2; ++i) {
                GlStateManager.matrixMode(5890);
                GlStateManager.loadIdentity();
                float tScale = 0.33333334f * Chams.hGlintScale.getValue().floatValue();
                GlStateManager.scale(tScale, tScale, tScale);
                GlStateManager.rotate(30.0f - (float)i * 60.0f, 0.0f, 0.0f, 1.0f);
                GlStateManager.translate(0.0f, ((float)clientPlayer.ticksExisted + Minecraft.getMinecraft().getRenderPartialTicks()) * (0.001f + (float)i * 0.003f) * Chams.hGlintSpeed.getValue().floatValue(), 0.0f);
                GlStateManager.matrixMode(5888);
                modelplayer.bipedRightArm.rotateAngleX = 0.0f;
                modelplayer.bipedRightArm.render(0.0625f);
                modelplayer.bipedRightArmwear.rotateAngleX = 0.0f;
                modelplayer.bipedRightArmwear.render(0.0625f);
            }
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GL11.glScalef(1.0f / Chams.scale.getValue().floatValue(), 1.0f / Chams.scale.getValue().floatValue(), 1.0f / Chams.scale.getValue().floatValue());
            GL11.glPopAttrib();
            GL11.glPopMatrix();
            GlStateManager.disableBlend();
        }
    }

    @Inject(method={"renderLeftArm"}, at={@At(value="FIELD", target="Lnet/minecraft/client/model/ModelPlayer;swingProgress:F", opcode=181)}, cancellable=true)
    public void Method7(AbstractClientPlayer clientPlayer, CallbackInfo ci) {
        if (clientPlayer == Minecraft.getMinecraft().player) {
            Class102 event = new Class102();
            EventDispatcher.Companion.dispatch(event);
            if (event.isCanceled()) {
                GL11.glPushAttrib(1048575);
                GL11.glDisable(3008);
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glLineWidth(1.5f);
                GL11.glEnable(2960);
                GL11.glEnable(10754);
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
                GL11.glColor4f(event.Method1108() / 255.0f, event.Method215() / 255.0f, event.Method214() / 255.0f, event.Method213() / 255.0f);
                this.Field7 = true;
            }
        }
    }

    @Inject(method={"renderLeftArm"}, at={@At(value="RETURN")}, cancellable=true)
    public void Method8(AbstractClientPlayer clientPlayer, CallbackInfo ci) {
        if (clientPlayer == Minecraft.getMinecraft().player && this.Field7) {
            GL11.glEnable(3042);
            GL11.glEnable(2896);
            GL11.glEnable(3553);
            GL11.glEnable(3008);
            GL11.glPopAttrib();
            this.Field7 = false;
        }
    }

    @Redirect(method={"doRender"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/entity/AbstractClientPlayer;isUser()Z"))
    private boolean Method9(AbstractClientPlayer abstractClientPlayer) {
        Minecraft mc = Minecraft.getMinecraft();
        Class46 event = new Class46();
        EventDispatcher.Companion.dispatch(event);
        if (event.isCanceled()) {
            return abstractClientPlayer.isUser() && abstractClientPlayer == mc.getRenderViewEntity();
        }
        return abstractClientPlayer.isUser();
    }
}