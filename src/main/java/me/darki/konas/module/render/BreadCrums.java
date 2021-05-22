package me.darki.konas.module.render;

import cookiedragon.eventsystem.Subscriber;
import java.awt.Color;
import java.util.ArrayList;

import me.darki.konas.unremaped.Class77;
import me.darki.konas.unremaped.Render3DEvent;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.event.events.TickEvent;
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
    public void Method139(Render3DEvent render3DEvent) {
        if (BreadCrums.mc.player == null || BreadCrums.mc.world == null) {
            return;
        }
        GL11.glBlendFunc(770, 771);
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth(1.5f);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GL11.glEnable(2848);
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GL11.glHint(3154, 4354);
        GlStateManager.depthMask(false);
        GlStateManager.color((float) this.color.getValue().Method775().getRed() / 255.0f, (float) this.color.getValue().Method775().getGreen() / 255.0f, (float) this.color.getValue().Method775().getBlue() / 255.0f, (float) this.color.getValue().Method775().getAlpha() / 255.0f);
        double d = BreadCrums.mc.player.lastTickPosX + (BreadCrums.mc.player.posX - BreadCrums.mc.player.lastTickPosX) * (double) render3DEvent.Method436();
        double d2 = BreadCrums.mc.player.lastTickPosY + (BreadCrums.mc.player.posY - BreadCrums.mc.player.lastTickPosY) * (double) render3DEvent.Method436();
        double d3 = BreadCrums.mc.player.lastTickPosZ + (BreadCrums.mc.player.posZ - BreadCrums.mc.player.lastTickPosZ) * (double) render3DEvent.Method436();
        GL11.glBegin(3);
        for (Vec3d vec3d : arrayList) {
            Vec3d vec3d2 = vec3d.subtract(d, d2, d3);
            GL11.glVertex3d(vec3d2.x, vec3d2.y, vec3d2.z);
        }
        GL11.glEnd();
        GL11.glPopMatrix();
        GlStateManager.enableCull();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
    }

    public BreadCrums() {
        super("Breadcrumbs", Category.RENDER);
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
                if (onlyRender.getValue().booleanValue()) {
                    return;
                }
                if (BreadCrums.mc.player.posX != BreadCrums.mc.player.lastTickPosX) break block6;
                if (BreadCrums.mc.player.posY == BreadCrums.mc.player.lastTickPosY && BreadCrums.mc.player.posZ == BreadCrums.mc.player.lastTickPosZ) break block7;
            }
            arrayList.add(BreadCrums.mc.player.getPositionVector());
            if (arrayList.size() >= this.maxVertices.getValue() * 10000) {
                arrayList.remove(0);
                arrayList.remove(1);
            }
        }
    }
}