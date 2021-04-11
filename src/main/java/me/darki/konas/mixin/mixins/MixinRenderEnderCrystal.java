package me.darki.konas.mixin.mixins;

import me.darki.konas.module.ModuleManager;
import me.darki.konas.module.render.Chams;
import me.darki.konas.settingEnums.ChamsGlintMode;
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
        if (ModuleManager.getModuleByClass(Chams.class).isEnabled() && !ESP.Field1342 && Chams.crystals.getValue().booleanValue()) {
            if (Chams.crystals.getValue().booleanValue() && Chams.cRender.getValue().booleanValue()) {
                GL11.glScalef(Chams.scale.getValue().floatValue(), Chams.scale.getValue().floatValue(), Chams.scale.getValue().floatValue());
                if (!Chams.cRenderDepth.getValue().booleanValue()) {
                    GL11.glPushAttrib(1048575);
                    GL11.glDepthMask(false);
                    GL11.glDisable(2929);
                }
                modelBase.render(entityIn, limbSwing, limbSwingAmount * Chams.spinSpeed.getValue().floatValue(), ageInTicks * Chams.bounciness.getValue().floatValue(), netHeadYaw, headPitch, scale);
                if (!Chams.cRenderDepth.getValue().booleanValue()) {
                    GL11.glEnable(2929);
                    GL11.glDepthMask(true);
                    GL11.glPopAttrib();
                }
                GL11.glScalef(1.0f / Chams.scale.getValue().floatValue(), 1.0f / Chams.scale.getValue().floatValue(), 1.0f / Chams.scale.getValue().floatValue());
            } else if (!Chams.crystals.getValue().booleanValue()) {
                modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            }
        } else if (ModuleManager.getModuleByClass(Chams.class).isEnabled() && ESP.Field1342 && Chams.crystals.getValue().booleanValue()) {
            GL11.glScalef(Chams.scale.getValue().floatValue(), Chams.scale.getValue().floatValue(), Chams.scale.getValue().floatValue());
            modelBase.render(entityIn, limbSwing, limbSwingAmount * Chams.spinSpeed.getValue().floatValue(), ageInTicks * Chams.bounciness.getValue().floatValue(), netHeadYaw, headPitch, scale);
            GL11.glScalef(1.0f / Chams.scale.getValue().floatValue(), 1.0f / Chams.scale.getValue().floatValue(), 1.0f / Chams.scale.getValue().floatValue());
        } else {
            modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    @Inject(method={"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"}, at={@At(value="RETURN")}, cancellable=true)
    public void Method63(EntityEnderCrystal entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (ModuleManager.getModuleByClass(Chams.class).isEnabled() && !ESP.Field1342 && Chams.crystals.getValue().booleanValue()) {
            float f4;
            float f3;
            if (Chams.cFill.getValue().booleanValue()) {
                f3 = (float)entity.innerRotation + partialTicks;
                GlStateManager.pushMatrix();
                GlStateManager.translate(x, y, z);
                Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(Field35);
                f4 = MathHelper.sin(f3 * 0.2f) / 2.0f + 0.5f;
                f4 += f4 * f4;
                GL11.glPushAttrib(1048575);
                GL11.glPolygonMode(1032, 6914);
                GL11.glDisable(3553);
                if (Chams.cLighting.getValue().booleanValue()) {
                    GL11.glEnable(2896);
                } else {
                    GL11.glDisable(2896);
                }
                GL11.glDisable(2929);
                GL11.glEnable(2848);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glColor4f((float) Chams.cFillColor.getValue().Method769() / 255.0f, (float) Chams.cFillColor.getValue().Method770() / 255.0f, (float) Chams.cFillColor.getValue().Method779() / 255.0f, (float) Chams.cFillColor.getValue().Method782() / 255.0f);
                GL11.glScalef(Chams.scale.getValue().floatValue(), Chams.scale.getValue().floatValue(), Chams.scale.getValue().floatValue());
                if (Chams.cFillDepth.getValue().booleanValue()) {
                    GL11.glDepthMask(true);
                    GL11.glEnable(2929);
                }
                if (entity.shouldShowBottom()) {
                    this.Field33.render(entity, 0.0f, f3 * 3.0f * Chams.spinSpeed.getValue().floatValue(), f4 * 0.2f * Chams.bounciness.getValue().floatValue(), 0.0f, 0.0f, 0.0625f);
                } else {
                    this.Field34.render(entity, 0.0f, f3 * 3.0f * Chams.spinSpeed.getValue().floatValue(), f4 * 0.2f * Chams.bounciness.getValue().floatValue(), 0.0f, 0.0f, 0.0625f);
                }
                if (Chams.cFillDepth.getValue().booleanValue()) {
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                }
                GL11.glScalef(1.0f / Chams.scale.getValue().floatValue(), 1.0f / Chams.scale.getValue().floatValue(), 1.0f / Chams.scale.getValue().floatValue());
                GL11.glPopAttrib();
                GL11.glPopMatrix();
            }
            if (Chams.cOutline.getValue().booleanValue()) {
                f3 = (float)entity.innerRotation + partialTicks;
                GlStateManager.pushMatrix();
                GlStateManager.translate(x, y, z);
                Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(Field35);
                f4 = MathHelper.sin(f3 * 0.2f) / 2.0f + 0.5f;
                f4 += f4 * f4;
                GL11.glPushAttrib(1048575);
                GL11.glPolygonMode(1032, 6913);
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glDisable(2929);
                GL11.glEnable(2848);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glLineWidth(Chams.cWidth.getValue().floatValue());
                GL11.glColor4f((float) Chams.cgOutlineColor.getValue().Method769() / 255.0f, (float) Chams.cgOutlineColor.getValue().Method770() / 255.0f, (float) Chams.cgOutlineColor.getValue().Method779() / 255.0f, (float) Chams.cgOutlineColor.getValue().Method782() / 255.0f);
                GL11.glScalef(Chams.scale.getValue().floatValue(), Chams.scale.getValue().floatValue(), Chams.scale.getValue().floatValue());
                if (Chams.cOutlineDepth.getValue().booleanValue()) {
                    GL11.glDepthMask(true);
                    GL11.glEnable(2929);
                }
                if (entity.shouldShowBottom()) {
                    this.Field33.render(entity, 0.0f, f3 * 3.0f * Chams.spinSpeed.getValue().floatValue(), f4 * 0.2f * Chams.bounciness.getValue().floatValue(), 0.0f, 0.0f, 0.0625f);
                } else {
                    this.Field34.render(entity, 0.0f, f3 * 3.0f * Chams.spinSpeed.getValue().floatValue(), f4 * 0.2f * Chams.bounciness.getValue().floatValue(), 0.0f, 0.0f, 0.0625f);
                }
                if (Chams.cOutlineDepth.getValue().booleanValue()) {
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                }
                GL11.glScalef(1.0f / Chams.scale.getValue().floatValue(), 1.0f / Chams.scale.getValue().floatValue(), 1.0f / Chams.scale.getValue().floatValue());
                GL11.glPopAttrib();
                GL11.glPopMatrix();
            }
            if (Chams.cGlint.getValue() != ChamsGlintMode.NONE) {
                f3 = (float)entity.innerRotation + partialTicks;
                GlStateManager.pushMatrix();
                GlStateManager.translate(x, y, z);
                Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(Chams.cGlint.getValue() == ChamsGlintMode.CUSTOM ? Chams.location1 : Chams.location);
                f4 = MathHelper.sin(f3 * 0.2f) / 2.0f + 0.5f;
                f4 += f4 * f4;
                GL11.glPushAttrib(1048575);
                GL11.glPolygonMode(1032, 6914);
                GL11.glDisable(2896);
                GL11.glDisable(2929);
                GL11.glEnable(3042);
                GL11.glColor4f((float) Chams.cGlintColor.getValue().Method769() / 255.0f, (float) Chams.cGlintColor.getValue().Method770() / 255.0f, (float) Chams.cGlintColor.getValue().Method779() / 255.0f, (float) Chams.cGlintColor.getValue().Method782() / 255.0f);
                GL11.glScalef(Chams.scale.getValue().floatValue(), Chams.scale.getValue().floatValue(), Chams.scale.getValue().floatValue());
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
                for (int i = 0; i < 2; ++i) {
                    GlStateManager.matrixMode(5890);
                    GlStateManager.loadIdentity();
                    float tScale = 0.33333334f * Chams.cGlintScale.getValue().floatValue();
                    GlStateManager.scale(tScale, tScale, tScale);
                    GlStateManager.rotate(30.0f - (float)i * 60.0f, 0.0f, 0.0f, 1.0f);
                    GlStateManager.translate(0.0f, ((float)entity.ticksExisted + partialTicks) * (0.001f + (float)i * 0.003f) * Chams.cGlintSpeed.getValue().floatValue(), 0.0f);
                    GlStateManager.matrixMode(5888);
                    if (Chams.cGlintDepth.getValue().booleanValue()) {
                        GL11.glDepthMask(true);
                        GL11.glEnable(2929);
                    }
                    if (entity.shouldShowBottom()) {
                        this.Field33.render(entity, 0.0f, f3 * 3.0f * Chams.spinSpeed.getValue().floatValue(), f4 * 0.2f * Chams.bounciness.getValue().floatValue(), 0.0f, 0.0f, 0.0625f);
                    } else {
                        this.Field34.render(entity, 0.0f, f3 * 3.0f * Chams.spinSpeed.getValue().floatValue(), f4 * 0.2f * Chams.bounciness.getValue().floatValue(), 0.0f, 0.0f, 0.0625f);
                    }
                    if (!Chams.cGlintDepth.getValue().booleanValue()) continue;
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                }
                GlStateManager.matrixMode(5890);
                GlStateManager.loadIdentity();
                GlStateManager.matrixMode(5888);
                GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GL11.glScalef(1.0f / Chams.scale.getValue().floatValue(), 1.0f / Chams.scale.getValue().floatValue(), 1.0f / Chams.scale.getValue().floatValue());
                GL11.glPopAttrib();
                GL11.glPopMatrix();
            }
        }
    }
}