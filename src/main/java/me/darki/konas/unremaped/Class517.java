package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import me.darki.konas.setting.ColorValue;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class Class517
extends Module {
    public ConcurrentHashMap<Integer, Class508> Field1280 = new ConcurrentHashMap();
    public Setting<ColorValue> color = new Setting<>("Color", new ColorValue(Color.WHITE.hashCode()));
    public Setting<Boolean> timeout = new Setting<>("Timeout", true);
    public Setting<Integer> seconds = new Setting<>("Seconds", 10, 100, 0, 1).visibleIf(this.Field1282::getValue);

    @Override
    public void onEnable() {
        this.Field1280.clear();
    }

    public void Method1253(Entity entity) {
        block2: {
            if (entity.ticksExisted <= 1 || !this.Method384(entity)) break block2;
            if (!this.Field1280.containsKey(entity.getEntityId())) {
                ArrayList<Vec3d> arrayList = new ArrayList<Vec3d>();
                arrayList.add(new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ));
                this.Field1280.put(entity.getEntityId(), new Class508(System.currentTimeMillis(), arrayList));
            } else {
                this.Field1280.get(entity.getEntityId()).Method1364().add(new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ));
                this.Field1280.get(entity.getEntityId()).Method1362(System.currentTimeMillis());
            }
        }
    }

    public static boolean Method386(Entity entity) {
        return Class517.mc.player != entity;
    }

    public boolean Method384(Entity entity) {
        if (entity instanceof EntitySnowball) {
            return true;
        }
        if (entity instanceof EntityArrow) {
            return true;
        }
        if (entity instanceof EntityEnderPearl) {
            return true;
        }
        return entity instanceof EntityEgg;
    }

    public static void Method1254(double d, double d2, double d3, Integer n, Class508 class508) {
        GL11.glBegin((int)3);
        for (Vec3d vec3d : class508.Method1364()) {
            Vec3d vec3d2 = vec3d.subtract(d, d2, d3);
            GL11.glVertex3d((double)vec3d2.x, (double)vec3d2.y, (double)vec3d2.z);
        }
        GL11.glEnd();
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        block1: {
            if (Class517.mc.player == null || Class517.mc.world == null) {
                return;
            }
            Class517.mc.world.loadedEntityList.stream().filter(Class517::Method386).forEach(this::Method1253);
            if (!((Boolean)this.timeout.getValue()).booleanValue()) break block1;
            this.Field1280.forEach(this::Method1255);
        }
    }

    @Subscriber
    public void Method139(Render3DEvent render3DEvent) {
        if (Class517.mc.player == null || Class517.mc.world == null) {
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
        double d = Class517.mc.player.lastTickPosX + (Class517.mc.player.posX - Class517.mc.player.lastTickPosX) * (double) render3DEvent.Method436();
        double d2 = Class517.mc.player.lastTickPosY + (Class517.mc.player.posY - Class517.mc.player.lastTickPosY) * (double) render3DEvent.Method436();
        double d3 = Class517.mc.player.lastTickPosZ + (Class517.mc.player.posZ - Class517.mc.player.lastTickPosZ) * (double) render3DEvent.Method436();
        this.Field1280.forEach((arg_0, arg_1) -> Class517.Method1254(d, d2, d3, arg_0, arg_1));
        GL11.glPopMatrix();
        GlStateManager.enableCull();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
    }

    public void Method1255(Integer n, Class508 class508) {
        if (System.currentTimeMillis() - class508.Method1363() > 1000L * (long)((Integer)this.seconds.getValue()).intValue()) {
            this.Field1280.remove(n);
        }
    }

    public Class517() {
        super("EntityTrails", Category.RENDER, new String[0]);
    }

    @Subscriber
    public void Method1256(Class77 class77) {
        this.Field1280.clear();
    }
}