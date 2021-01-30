package me.darki.konas.mixin.mixins;

import me.darki.konas.Class167;
import me.darki.konas.ColorValue;
import me.darki.konas.Class493;
import me.darki.konas.Class495;
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
        if (Class167.Method1610(Class493.class).Method1651() && !ESP.Field1342 && ((Boolean)Class493.Field2119.getValue()).booleanValue()) {
            if (((Boolean)Class493.Field2119.getValue()).booleanValue() && ((Boolean)Class493.Field2120.getValue()).booleanValue()) {
                GL11.glScalef((float)((Float)Class493.Field2135.getValue()).floatValue(), (float)((Float)Class493.Field2135.getValue()).floatValue(), (float)((Float)Class493.Field2135.getValue()).floatValue());
                if (!((Boolean)Class493.Field2121.getValue()).booleanValue()) {
                    GL11.glPushAttrib((int)1048575);
                    GL11.glDepthMask((boolean)false);
                    GL11.glDisable((int)2929);
                }
                modelBase.render(entityIn, limbSwing, limbSwingAmount * ((Float)Class493.Field2136.getValue()).floatValue(), ageInTicks * ((Float)Class493.Field2137.getValue()).floatValue(), netHeadYaw, headPitch, scale);
                if (!((Boolean)Class493.Field2121.getValue()).booleanValue()) {
                    GL11.glEnable((int)2929);
                    GL11.glDepthMask((boolean)true);
                    GL11.glPopAttrib();
                }
                GL11.glScalef((float)(1.0f / ((Float)Class493.Field2135.getValue()).floatValue()), (float)(1.0f / ((Float)Class493.Field2135.getValue()).floatValue()), (float)(1.0f / ((Float)Class493.Field2135.getValue()).floatValue()));
            } else if (!((Boolean)Class493.Field2119.getValue()).booleanValue()) {
                modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            }
        } else if (Class167.Method1610(Class493.class).Method1651() && ESP.Field1342 && ((Boolean)Class493.Field2119.getValue()).booleanValue()) {
            GL11.glScalef((float)((Float)Class493.Field2135.getValue()).floatValue(), (float)((Float)Class493.Field2135.getValue()).floatValue(), (float)((Float)Class493.Field2135.getValue()).floatValue());
            modelBase.render(entityIn, limbSwing, limbSwingAmount * ((Float)Class493.Field2136.getValue()).floatValue(), ageInTicks * ((Float)Class493.Field2137.getValue()).floatValue(), netHeadYaw, headPitch, scale);
            GL11.glScalef((float)(1.0f / ((Float)Class493.Field2135.getValue()).floatValue()), (float)(1.0f / ((Float)Class493.Field2135.getValue()).floatValue()), (float)(1.0f / ((Float)Class493.Field2135.getValue()).floatValue()));
        } else {
            modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    @Inject(method={"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"}, at={@At(value="RETURN")}, cancellable=true)
    public void Method63(EntityEnderCrystal entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (Class167.Method1610(Class493.class).Method1651() && !ESP.Field1342 && ((Boolean)Class493.Field2119.getValue()).booleanValue()) {
            float f4;
            float f3;
            if (((Boolean)Class493.Field2127.getValue()).booleanValue()) {
                f3 = (float)entity.innerRotation + partialTicks;
                GlStateManager.pushMatrix();
                GlStateManager.translate((double)x, (double)y, (double)z);
                Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(Field35);
                f4 = MathHelper.sin((float)(f3 * 0.2f)) / 2.0f + 0.5f;
                f4 += f4 * f4;
                GL11.glPushAttrib((int)1048575);
                GL11.glPolygonMode((int)1032, (int)6914);
                GL11.glDisable((int)3553);
                if (((Boolean)Class493.Field2129.getValue()).booleanValue()) {
                    GL11.glEnable((int)2896);
                } else {
                    GL11.glDisable((int)2896);
                }
                GL11.glDisable((int)2929);
                GL11.glEnable((int)2848);
                GL11.glEnable((int)3042);
                GL11.glBlendFunc((int)770, (int)771);
                GL11.glColor4f((float)((float)((ColorValue)Class493.Field2130.getValue()).Method769() / 255.0f), (float)((float)((ColorValue)Class493.Field2130.getValue()).Method770() / 255.0f), (float)((float)((ColorValue)Class493.Field2130.getValue()).Method779() / 255.0f), (float)((float)((ColorValue)Class493.Field2130.getValue()).Method782() / 255.0f));
                GL11.glScalef((float)((Float)Class493.Field2135.getValue()).floatValue(), (float)((Float)Class493.Field2135.getValue()).floatValue(), (float)((Float)Class493.Field2135.getValue()).floatValue());
                if (((Boolean)Class493.Field2128.getValue()).booleanValue()) {
                    GL11.glDepthMask((boolean)true);
                    GL11.glEnable((int)2929);
                }
                if (entity.shouldShowBottom()) {
                    this.Field33.render((Entity)entity, 0.0f, f3 * 3.0f * ((Float)Class493.Field2136.getValue()).floatValue(), f4 * 0.2f * ((Float)Class493.Field2137.getValue()).floatValue(), 0.0f, 0.0f, 0.0625f);
                } else {
                    this.Field34.render((Entity)entity, 0.0f, f3 * 3.0f * ((Float)Class493.Field2136.getValue()).floatValue(), f4 * 0.2f * ((Float)Class493.Field2137.getValue()).floatValue(), 0.0f, 0.0f, 0.0625f);
                }
                if (((Boolean)Class493.Field2128.getValue()).booleanValue()) {
                    GL11.glDisable((int)2929);
                    GL11.glDepthMask((boolean)false);
                }
                GL11.glScalef((float)(1.0f / ((Float)Class493.Field2135.getValue()).floatValue()), (float)(1.0f / ((Float)Class493.Field2135.getValue()).floatValue()), (float)(1.0f / ((Float)Class493.Field2135.getValue()).floatValue()));
                GL11.glPopAttrib();
                GL11.glPopMatrix();
            }
            if (((Boolean)Class493.Field2131.getValue()).booleanValue()) {
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
                GL11.glLineWidth((float)((Float)Class493.Field2133.getValue()).floatValue());
                GL11.glColor4f((float)((float)((ColorValue)Class493.Field2134.getValue()).Method769() / 255.0f), (float)((float)((ColorValue)Class493.Field2134.getValue()).Method770() / 255.0f), (float)((float)((ColorValue)Class493.Field2134.getValue()).Method779() / 255.0f), (float)((float)((ColorValue)Class493.Field2134.getValue()).Method782() / 255.0f));
                GL11.glScalef((float)((Float)Class493.Field2135.getValue()).floatValue(), (float)((Float)Class493.Field2135.getValue()).floatValue(), (float)((Float)Class493.Field2135.getValue()).floatValue());
                if (((Boolean)Class493.Field2132.getValue()).booleanValue()) {
                    GL11.glDepthMask((boolean)true);
                    GL11.glEnable((int)2929);
                }
                if (entity.shouldShowBottom()) {
                    this.Field33.render((Entity)entity, 0.0f, f3 * 3.0f * ((Float)Class493.Field2136.getValue()).floatValue(), f4 * 0.2f * ((Float)Class493.Field2137.getValue()).floatValue(), 0.0f, 0.0f, 0.0625f);
                } else {
                    this.Field34.render((Entity)entity, 0.0f, f3 * 3.0f * ((Float)Class493.Field2136.getValue()).floatValue(), f4 * 0.2f * ((Float)Class493.Field2137.getValue()).floatValue(), 0.0f, 0.0f, 0.0625f);
                }
                if (((Boolean)Class493.Field2132.getValue()).booleanValue()) {
                    GL11.glDisable((int)2929);
                    GL11.glDepthMask((boolean)false);
                }
                GL11.glScalef((float)(1.0f / ((Float)Class493.Field2135.getValue()).floatValue()), (float)(1.0f / ((Float)Class493.Field2135.getValue()).floatValue()), (float)(1.0f / ((Float)Class493.Field2135.getValue()).floatValue()));
                GL11.glPopAttrib();
                GL11.glPopMatrix();
            }
            if (Class493.Field2122.getValue() != Class495.NONE) {
                f3 = (float)entity.innerRotation + partialTicks;
                GlStateManager.pushMatrix();
                GlStateManager.translate((double)x, (double)y, (double)z);
                Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(Class493.Field2122.getValue() == Class495.CUSTOM ? Class493.Field2146 : Class493.Field2145);
                f4 = MathHelper.sin((float)(f3 * 0.2f)) / 2.0f + 0.5f;
                f4 += f4 * f4;
                GL11.glPushAttrib((int)1048575);
                GL11.glPolygonMode((int)1032, (int)6914);
                GL11.glDisable((int)2896);
                GL11.glDisable((int)2929);
                GL11.glEnable((int)3042);
                GL11.glColor4f((float)((float)((ColorValue)Class493.Field2126.getValue()).Method769() / 255.0f), (float)((float)((ColorValue)Class493.Field2126.getValue()).Method770() / 255.0f), (float)((float)((ColorValue)Class493.Field2126.getValue()).Method779() / 255.0f), (float)((float)((ColorValue)Class493.Field2126.getValue()).Method782() / 255.0f));
                GL11.glScalef((float)((Float)Class493.Field2135.getValue()).floatValue(), (float)((Float)Class493.Field2135.getValue()).floatValue(), (float)((Float)Class493.Field2135.getValue()).floatValue());
                GlStateManager.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_COLOR, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE);
                for (int i = 0; i < 2; ++i) {
                    GlStateManager.matrixMode((int)5890);
                    GlStateManager.loadIdentity();
                    float tScale = 0.33333334f * ((Float)Class493.Field2125.getValue()).floatValue();
                    GlStateManager.scale((float)tScale, (float)tScale, (float)tScale);
                    GlStateManager.rotate((float)(30.0f - (float)i * 60.0f), (float)0.0f, (float)0.0f, (float)1.0f);
                    GlStateManager.translate((float)0.0f, (float)(((float)entity.ticksExisted + partialTicks) * (0.001f + (float)i * 0.003f) * ((Float)Class493.Field2124.getValue()).floatValue()), (float)0.0f);
                    GlStateManager.matrixMode((int)5888);
                    if (((Boolean)Class493.Field2123.getValue()).booleanValue()) {
                        GL11.glDepthMask((boolean)true);
                        GL11.glEnable((int)2929);
                    }
                    if (entity.shouldShowBottom()) {
                        this.Field33.render((Entity)entity, 0.0f, f3 * 3.0f * ((Float)Class493.Field2136.getValue()).floatValue(), f4 * 0.2f * ((Float)Class493.Field2137.getValue()).floatValue(), 0.0f, 0.0f, 0.0625f);
                    } else {
                        this.Field34.render((Entity)entity, 0.0f, f3 * 3.0f * ((Float)Class493.Field2136.getValue()).floatValue(), f4 * 0.2f * ((Float)Class493.Field2137.getValue()).floatValue(), 0.0f, 0.0f, 0.0625f);
                    }
                    if (!((Boolean)Class493.Field2123.getValue()).booleanValue()) continue;
                    GL11.glDisable((int)2929);
                    GL11.glDepthMask((boolean)false);
                }
                GlStateManager.matrixMode((int)5890);
                GlStateManager.loadIdentity();
                GlStateManager.matrixMode((int)5888);
                GlStateManager.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
                GL11.glScalef((float)(1.0f / ((Float)Class493.Field2135.getValue()).floatValue()), (float)(1.0f / ((Float)Class493.Field2135.getValue()).floatValue()), (float)(1.0f / ((Float)Class493.Field2135.getValue()).floatValue()));
                GL11.glPopAttrib();
                GL11.glPopMatrix();
            }
        }
    }
}
