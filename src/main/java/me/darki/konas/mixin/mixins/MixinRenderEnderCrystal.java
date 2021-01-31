package me.darki.konas.mixin.mixins;

import me.darki.konas.Class167;
import me.darki.konas.ColorValue;
import me.darki.konas.Chams;
import me.darki.konas.ChamsGlintMode;
import me.darki.konas.module.render.ESP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={RenderEnderCrystal.class}, priority=1069)
public abstract class MixinRenderEnderCrystal
extends Render {
    @Shadow
    public ModelBase Field33;
    @Shadow
    public ModelBase Field34;
    @Final
    @Shadow
    private static ResourceLocation Field35;

    protected MixinRenderEnderCrystal(RenderManager renderManager) {
        super(renderManager);
    }

    @Shadow
    public abstract void Method61(EntityEnderCrystal var1, double var2, double var4, double var6, float var8, float var9);

    @Redirect(method={"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void Method62(ModelBase modelBase, Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (Class167.Method1610(Chams.class).isEnabled() && !ESP.Field1342 && ((Boolean) Chams.crystals.getValue()).booleanValue()) {
            if (((Boolean) Chams.crystals.getValue()).booleanValue() && ((Boolean) Chams.cRender.getValue()).booleanValue()) {
                GL11.glScalef((float)((Float) Chams.scale.getValue()).floatValue(), (float)((Float) Chams.scale.getValue()).floatValue(), (float)((Float) Chams.scale.getValue()).floatValue());
                if (!((Boolean) Chams.cRenderDepth.getValue()).booleanValue()) {
                    GL11.glPushAttrib((int)1048575);
                    GL11.glDepthMask((boolean)false);
                    GL11.glDisable((int)2929);
                }
                modelBase.render(entityIn, limbSwing, limbSwingAmount * ((Float) Chams.spinSpeed.getValue()).floatValue(), ageInTicks * ((Float) Chams.bounciness.getValue()).floatValue(), netHeadYaw, headPitch, scale);
                if (!((Boolean) Chams.cRenderDepth.getValue()).booleanValue()) {
                    GL11.glEnable((int)2929);
                    GL11.glDepthMask((boolean)true);
                    GL11.glPopAttrib();
                }
                GL11.glScalef((float)(1.0f / ((Float) Chams.scale.getValue()).floatValue()), (float)(1.0f / ((Float) Chams.scale.getValue()).floatValue()), (float)(1.0f / ((Float) Chams.scale.getValue()).floatValue()));
            } else if (!((Boolean) Chams.crystals.getValue()).booleanValue()) {
                modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            }
        } else if (Class167.Method1610(Chams.class).isEnabled() && ESP.Field1342 && ((Boolean) Chams.crystals.getValue()).booleanValue()) {
            GL11.glScalef((float)((Float) Chams.scale.getValue()).floatValue(), (float)((Float) Chams.scale.getValue()).floatValue(), (float)((Float) Chams.scale.getValue()).floatValue());
            modelBase.render(entityIn, limbSwing, limbSwingAmount * ((Float) Chams.spinSpeed.getValue()).floatValue(), ageInTicks * ((Float) Chams.bounciness.getValue()).floatValue(), netHeadYaw, headPitch, scale);
            GL11.glScalef((float)(1.0f / ((Float) Chams.scale.getValue()).floatValue()), (float)(1.0f / ((Float) Chams.scale.getValue()).floatValue()), (float)(1.0f / ((Float) Chams.scale.getValue()).floatValue()));
        } else {
            modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    @Inject(method={"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"}, at={@At(value="RETURN")}, cancellable=true)
    public void Method63(EntityEnderCrystal entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (Class167.Method1610(Chams.class).isEnabled() && !ESP.Field1342 && ((Boolean) Chams.crystals.getValue()).booleanValue()) {
            float f4;
            float f3;
            if (((Boolean) Chams.cFill.getValue()).booleanValue()) {
                f3 = (float)entity.innerRotation + partialTicks;
                GlStateManager.pushMatrix();
                GlStateManager.translate((double)x, (double)y, (double)z);
                Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(Field35);
                f4 = MathHelper.sin((float)(f3 * 0.2f)) / 2.0f + 0.5f;
                f4 += f4 * f4;
                GL11.glPushAttrib((int)1048575);
                GL11.glPolygonMode((int)1032, (int)6914);
                GL11.glDisable((int)3553);
                if (((Boolean) Chams.cLighting.getValue()).booleanValue()) {
                    GL11.glEnable((int)2896);
                } else {
                    GL11.glDisable((int)2896);
                }
                GL11.glDisable((int)2929);
                GL11.glEnable((int)2848);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glColor4f((float)((float)((ColorValue) Chams.cFillColor.getValue()).Method769() / 255.0f), (float)((float)((ColorValue) Chams.cFillColor.getValue()).Method770() / 255.0f), (float)((float)((ColorValue) Chams.cFillColor.getValue()).Method779() / 255.0f), (float)((float)((ColorValue) Chams.cFillColor.getValue()).Method782() / 255.0f));
                GL11.glScalef((float)((Float) Chams.scale.getValue()).floatValue(), (float)((Float) Chams.scale.getValue()).floatValue(), (float)((Float) Chams.scale.getValue()).floatValue());
                if (((Boolean) Chams.cFillDepth.getValue()).booleanValue()) {
                    GL11.glDepthMask((boolean)true);
                    GL11.glEnable((int)2929);
                }
                if (entity.shouldShowBottom()) {
                    this.Field33.render((Entity)entity, 0.0f, f3 * 3.0f * ((Float) Chams.spinSpeed.getValue()).floatValue(), f4 * 0.2f * ((Float) Chams.bounciness.getValue()).floatValue(), 0.0f, 0.0f, 0.0625f);
                } else {
                    this.Field34.render((Entity)entity, 0.0f, f3 * 3.0f * ((Float) Chams.spinSpeed.getValue()).floatValue(), f4 * 0.2f * ((Float) Chams.bounciness.getValue()).floatValue(), 0.0f, 0.0f, 0.0625f);
                }
                if (((Boolean) Chams.cFillDepth.getValue()).booleanValue()) {
                    GL11.glDisable((int)2929);
                    GL11.glDepthMask((boolean)false);
                }
                GL11.glScalef((float)(1.0f / ((Float) Chams.scale.getValue()).floatValue()), (float)(1.0f / ((Float) Chams.scale.getValue()).floatValue()), (float)(1.0f / ((Float) Chams.scale.getValue()).floatValue()));
                GL11.glPopAttrib();
                GL11.glPopMatrix();
            }
            if (((Boolean) Chams.cOutline.getValue()).booleanValue()) {
                f3 = (float)entity.innerRotation + partialTicks;
                GlStateManager.pushMatrix();
                GlStateManager.translate((double)x, (double)y, (double)z);
                Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(Field35);
                f4 = MathHelper.sin((float)(f3 * 0.2f)) / 2.0f + 0.5f;
                f4 += f4 * f4;
                GL11.glPushAttrib((int)1048575);
                GL11.glPolygonMode((int)1032, (int)6913);
                GL11.glDisable((int)3553);
                GL11.glDisable((int)2896);
                GL11.glDisable((int)2929);
                GL11.glEnable((int)2848);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glLineWidth((float)((Float) Chams.cWidth.getValue()).floatValue());
                GL11.glColor4f((float)((float)((ColorValue) Chams.cgOutlineColor.getValue()).Method769() / 255.0f), (float)((float)((ColorValue) Chams.cgOutlineColor.getValue()).Method770() / 255.0f), (float)((float)((ColorValue) Chams.cgOutlineColor.getValue()).Method779() / 255.0f), (float)((float)((ColorValue) Chams.cgOutlineColor.getValue()).Method782() / 255.0f));
                GL11.glScalef((float)((Float) Chams.scale.getValue()).floatValue(), (float)((Float) Chams.scale.getValue()).floatValue(), (float)((Float) Chams.scale.getValue()).floatValue());
                if (((Boolean) Chams.cOutlineDepth.getValue()).booleanValue()) {
                    GL11.glDepthMask((boolean)true);
                    GL11.glEnable((int)2929);
                }
                if (entity.shouldShowBottom()) {
                    this.Field33.render((Entity)entity, 0.0f, f3 * 3.0f * ((Float) Chams.spinSpeed.getValue()).floatValue(), f4 * 0.2f * ((Float) Chams.bounciness.getValue()).floatValue(), 0.0f, 0.0f, 0.0625f);
                } else {
                    this.Field34.render((Entity)entity, 0.0f, f3 * 3.0f * ((Float) Chams.spinSpeed.getValue()).floatValue(), f4 * 0.2f * ((Float) Chams.bounciness.getValue()).floatValue(), 0.0f, 0.0f, 0.0625f);
                }
                if (((Boolean) Chams.cOutlineDepth.getValue()).booleanValue()) {
                    GL11.glDisable((int)2929);
                    GL11.glDepthMask((boolean)false);
                }
                GL11.glScalef((float)(1.0f / ((Float) Chams.scale.getValue()).floatValue()), (float)(1.0f / ((Float) Chams.scale.getValue()).floatValue()), (float)(1.0f / ((Float) Chams.scale.getValue()).floatValue()));
                GL11.glPopAttrib();
                GL11.glPopMatrix();
            }
            if (Chams.cGlint.getValue() != ChamsGlintMode.NONE) {
                f3 = (float)entity.innerRotation + partialTicks;
                GlStateManager.pushMatrix();
                GlStateManager.translate((double)x, (double)y, (double)z);
                Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(Chams.cGlint.getValue() == ChamsGlintMode.CUSTOM ? Chams.location1 : Chams.location);
                f4 = MathHelper.sin((float)(f3 * 0.2f)) / 2.0f + 0.5f;
                f4 += f4 * f4;
                GL11.glPushAttrib((int)1048575);
                GL11.glPolygonMode((int)1032, (int)6914);
                GL11.glDisable((int)2896);
                GL11.glDisable((int)2929);
                GL11.glEnable((int)3042);
                GL11.glColor4f((float)((float)((ColorValue) Chams.cGlintColor.getValue()).Method769() / 255.0f), (float)((float)((ColorValue) Chams.cGlintColor.getValue()).Method770() / 255.0f), (float)((float)((ColorValue) Chams.cGlintColor.getValue()).Method779() / 255.0f), (float)((float)((ColorValue) Chams.cGlintColor.getValue()).Method782() / 255.0f));
                GL11.glScalef((float)((Float) Chams.scale.getValue()).floatValue(), (float)((Float) Chams.scale.getValue()).floatValue(), (float)((Float) Chams.scale.getValue()).floatValue());
                GlStateManager.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_COLOR, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE);
                for (int i = 0; i < 2; ++i) {
                    GlStateManager.matrixMode((int)5890);
                    GlStateManager.loadIdentity();
                    float tScale = 0.33333334f * ((Float) Chams.cGlintScale.getValue()).floatValue();
                    GlStateManager.scale((float)tScale, (float)tScale, (float)tScale);
                    GlStateManager.rotate((float)(30.0f - (float)i * 60.0f), (float)0.0f, (float)0.0f, (float)1.0f);
                    GlStateManager.translate((float)0.0f, (float)(((float)entity.ticksExisted + partialTicks) * (0.001f + (float)i * 0.003f) * ((Float) Chams.cGlintSpeed.getValue()).floatValue()), (float)0.0f);
                    GlStateManager.matrixMode((int)5888);
                    if (((Boolean) Chams.cGlintDepth.getValue()).booleanValue()) {
                        GL11.glDepthMask((boolean)true);
                        GL11.glEnable((int)2929);
                    }
                    if (entity.shouldShowBottom()) {
                        this.Field33.render((Entity)entity, 0.0f, f3 * 3.0f * ((Float) Chams.spinSpeed.getValue()).floatValue(), f4 * 0.2f * ((Float) Chams.bounciness.getValue()).floatValue(), 0.0f, 0.0f, 0.0625f);
                    } else {
                        this.Field34.render((Entity)entity, 0.0f, f3 * 3.0f * ((Float) Chams.spinSpeed.getValue()).floatValue(), f4 * 0.2f * ((Float) Chams.bounciness.getValue()).floatValue(), 0.0f, 0.0f, 0.0625f);
                    }
                    if (!((Boolean) Chams.cGlintDepth.getValue()).booleanValue()) continue;
                    GL11.glDisable((int)2929);
                    GL11.glDepthMask((boolean)false);
                }
                GlStateManager.matrixMode((int)5890);
                GlStateManager.loadIdentity();
                GlStateManager.matrixMode((int)5888);
                GlStateManager.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
                GL11.glScalef((float)(1.0f / ((Float) Chams.scale.getValue()).floatValue()), (float)(1.0f / ((Float) Chams.scale.getValue()).floatValue()), (float)(1.0f / ((Float) Chams.scale.getValue()).floatValue()));
                GL11.glPopAttrib();
                GL11.glPopMatrix();
            }
        }
    }
}
