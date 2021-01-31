package me.darki.konas.module.render;

import cookiedragon.eventsystem.Subscriber;
import java.awt.Color;
import java.util.ArrayList;

import me.darki.konas.Class77;
import me.darki.konas.Class89;
import me.darki.konas.ColorValue;
import me.darki.konas.TickEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class BreadCrums
extends Module {
    public static ArrayList<Vec3d> arrayList = new ArrayList();
    public static Setting<Boolean> onlyRender = new Setting<>("OnlyRender", false);
    public Setting<Integer> maxVertices = new Setting<>("MaxVertices", 50, 250, 25, 25);
    public Setting<ColorValue> color = new Setting<>("Color", new ColorValue(Color.WHITE.hashCode()));

    @Subscriber
    public void Method139(Class89 class89) {
        if (BreadCrums.mc.player == null || BreadCrums.mc.world == null) {
            return;
        }
        GL11.glBlendFunc((int)770, (int)771);
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth((float)1.5f);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GL11.glEnable((int)2848);
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GL11.glHint((int)3154, (int)4354);
        GlStateManager.depthMask((boolean)false);
        GlStateManager.color((float)((float)((ColorValue)this.color.getValue()).Method775().getRed() / 255.0f), (float)((float)((ColorValue)this.color.getValue()).Method775().getGreen() / 255.0f), (float)((float)((ColorValue)this.color.getValue()).Method775().getBlue() / 255.0f), (float)((float)((ColorValue)this.color.getValue()).Method775().getAlpha() / 255.0f));
        double d = BreadCrums.mc.player.lastTickPosX + (BreadCrums.mc.player.posX - BreadCrums.mc.player.lastTickPosX) * (double)class89.Method436();
        double d2 = BreadCrums.mc.player.lastTickPosY + (BreadCrums.mc.player.posY - BreadCrums.mc.player.lastTickPosY) * (double)class89.Method436();
        double d3 = BreadCrums.mc.player.lastTickPosZ + (BreadCrums.mc.player.posZ - BreadCrums.mc.player.lastTickPosZ) * (double)class89.Method436();
        GL11.glBegin((int)3);
        for (Vec3d vec3d : arrayList) {
            Vec3d vec3d2 = vec3d.subtract(d, d2, d3);
            GL11.glVertex3d((double)vec3d2.x, (double)vec3d2.y, (double)vec3d2.z);
        }
        GL11.glEnd();
        GL11.glPopMatrix();
        GlStateManager.enableCull();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
    }

    public BreadCrums() {
        super("Breadcrumbs", Category.RENDER, new String[0]);
    }

    @Subscriber
    public void Method1256(Class77 class77) {
        arrayList.clear();
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        block7: {
            block6: {
                if (BreadCrums.mc.player == null || BreadCrums.mc.world == null) {
                    return;
                }
                if (((Boolean) onlyRender.getValue()).booleanValue()) {
                    return;
                }
                if (BreadCrums.mc.player.posX != BreadCrums.mc.player.lastTickPosX) break block6;
                if (BreadCrums.mc.player.posY == BreadCrums.mc.player.lastTickPosY && BreadCrums.mc.player.posZ == BreadCrums.mc.player.lastTickPosZ) break block7;
            }
            arrayList.add(BreadCrums.mc.player.getPositionVector());
            if (arrayList.size() >= (Integer)this.maxVertices.getValue() * 10000) {
                arrayList.remove(0);
                arrayList.remove(1);
            }
        }
    }
}
