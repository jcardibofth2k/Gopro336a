package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.mixin.mixins.IMinecraft;
import me.darki.konas.mixin.mixins.ITimer;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.util.math.Interpolation;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class Crosshair
extends Module {
    public Setting<Boolean> dot = new Setting<>("Dot", false);
    public Setting<Float> gap = new Setting<>("Gap", Float.valueOf(2.0f), Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(0.5f));
    public Setting<Float> motionGap = new Setting<>("MotionGap", Float.valueOf(0.0f), Float.valueOf(5.0f), Float.valueOf(0.0f), Float.valueOf(0.05f));
    public Setting<Float> width = new Setting<>("Width", Float.valueOf(1.0f), Float.valueOf(5.0f), Float.valueOf(0.1f), Float.valueOf(0.1f));
    public Setting<Float> motionWidth = new Setting<>("MotionWidth", Float.valueOf(0.0f), Float.valueOf(2.5f), Float.valueOf(0.0f), Float.valueOf(0.05f));
    public Setting<Float> size = new Setting<>("Size", Float.valueOf(2.0f), Float.valueOf(40.0f), Float.valueOf(1.0f), Float.valueOf(0.5f));
    public Setting<Float> motionSize = new Setting<>("MotionSize", Float.valueOf(0.0f), Float.valueOf(20.0f), Float.valueOf(0.0f), Float.valueOf(0.2f));
    public Setting<ColorValue> color = new Setting<>("Color", new ColorValue(-1));
    public long Field2376 = -1L;
    public float Field2377 = 0.0f;
    public float Field2378 = 0.0f;

    @Subscriber
    public void Method2074(Class108 class108) {
        class108.setCanceled(true);
    }

    @Subscriber
    public void Method135(UpdateEvent updateEvent) {
        this.Field2378 = this.Field2377;
        double d = Crosshair.mc.player.posX - Crosshair.mc.player.prevPosX;
        double d2 = Crosshair.mc.player.posZ - Crosshair.mc.player.prevPosZ;
        this.Field2377 = (float)Math.sqrt(d * d + d2 * d2);
        this.Field2376 = System.currentTimeMillis();
    }

    public Crosshair() {
        super("Crosshair", "Draws a custom crosshair", Category.RENDER, new String[0]);
    }

    @Subscriber
    public void Method466(Class91 class91) {
        block0: {
            ScaledResolution scaledResolution = new ScaledResolution(mc);
            float f = (float)(scaledResolution.getScaledWidth_double() / 2.0 + 0.5);
            float f2 = (float)(scaledResolution.getScaledHeight_double() / 2.0 + 0.5);
            float f3 = ((Float)this.gap.getValue()).floatValue();
            float f4 = Math.max(((Float)this.width.getValue()).floatValue(), 0.5f);
            float f5 = ((Float)this.size.getValue()).floatValue();
            float f6 = ((ITimer)((IMinecraft)mc).getTimer()).getTickLength();
            Crosshair.Method2075(f - (f3 += Interpolation.Method361(this.Field2378, this.Field2377, Math.min((float)(System.currentTimeMillis() - this.Field2376) / f6, 1.0f)) * ((Float)this.motionGap.getValue()).floatValue()) - (f5 += Interpolation.Method361(this.Field2378, this.Field2377, Math.min((float)(System.currentTimeMillis() - this.Field2376) / f6, 1.0f)) * ((Float)this.motionSize.getValue()).floatValue()), f2 - (f4 += Interpolation.Method361(this.Field2378, this.Field2377, Math.min((float)(System.currentTimeMillis() - this.Field2376) / f6, 1.0f)) * ((Float)this.motionWidth.getValue()).floatValue()) / 2.0f, f - f3, f2 + f4 / 2.0f, ((ColorValue)this.color.getValue()).Method774());
            Crosshair.Method2075(f + f3 + f5, f2 - f4 / 2.0f, f + f3, f2 + f4 / 2.0f, ((ColorValue)this.color.getValue()).Method774());
            Crosshair.Method2075(f - f4 / 2.0f, f2 + f3 + f5, f + f4 / 2.0f, f2 + f3, ((ColorValue)this.color.getValue()).Method774());
            Crosshair.Method2075(f - f4 / 2.0f, f2 - f3 - f5, f + f4 / 2.0f, f2 - f3, ((ColorValue)this.color.getValue()).Method774());
            if (!((Boolean)this.dot.getValue()).booleanValue()) break block0;
            Crosshair.Method2075(f - f4 / 2.0f, f2 - f4 / 2.0f, f + f4 / 2.0f, f2 + f4 / 2.0f, ((ColorValue)this.color.getValue()).Method774());
        }
    }

    public static void Method2075(float f, float f2, float f3, float f4, int n) {
        float f5;
        if (f < f3) {
            f5 = f;
            f = f3;
            f3 = f5;
        }
        if (f2 < f4) {
            f5 = f2;
            f2 = f4;
            f4 = f5;
        }
        f5 = (float)(n >> 24 & 0xFF) / 255.0f;
        float f6 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f7 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f8 = (float)(n & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.color((float)f6, (float)f7, (float)f8, (float)f5);
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferBuilder.pos((double)f, (double)f4, 0.0).endVertex();
        bufferBuilder.pos((double)f3, (double)f4, 0.0).endVertex();
        bufferBuilder.pos((double)f3, (double)f2, 0.0).endVertex();
        bufferBuilder.pos((double)f, (double)f2, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}