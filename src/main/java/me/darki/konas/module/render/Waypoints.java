package me.darki.konas.module.render;

import cookiedragon.eventsystem.Subscriber;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.darki.konas.event.events.OpenGuiEvent;
import me.darki.konas.mixin.mixins.IRenderManager;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.setting.ParentSetting;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.*;
import me.darki.konas.setting.ColorValue;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class Waypoints
extends Module {
    public Setting<ParentSetting> custom = new Setting<>("Custom", new ParentSetting(false));
    public Setting<Boolean> cTracers = new Setting<>("CTracers", false).setParentSetting(this.Field1895);
    public Setting<ColorValue> cTracersC = new Setting<>("CTracersC", new ColorValue(-16776961)).setParentSetting(this.Field1895);
    public Setting<Boolean> cFill = new Setting<>("CFill", true).setParentSetting(this.Field1895);
    public Setting<ColorValue> cFillC = new Setting<>("CFillC", new ColorValue(0x550000FF)).setParentSetting(this.Field1895);
    public Setting<Boolean> cOutline = new Setting<>("COutline", true).setParentSetting(this.Field1895);
    public Setting<ColorValue> cOutlineC = new Setting<>("COutlineC", new ColorValue(-16776961)).setParentSetting(this.Field1895);
    public Setting<ParentSetting> logouts = new Setting<>("Logouts", new ParentSetting(false));
    public Setting<Boolean> renderLogouts = new Setting<>("RenderLogouts", true).setParentSetting(this.Field1902);
    public Setting<Boolean> lTracers = new Setting<>("LTracers", false).setParentSetting(this.Field1902);
    public Setting<ColorValue> lTracersC = new Setting<>("LTracersC", new ColorValue(-16777216)).setParentSetting(this.Field1902);
    public Setting<Boolean> lFill = new Setting<>("LFill", true).setParentSetting(this.Field1902);
    public Setting<ColorValue> lFillC = new Setting<>("LFillC", new ColorValue(0x55FF00FF)).setParentSetting(this.Field1902);
    public Setting<Boolean> lOutline = new Setting<>("LOutline", true).setParentSetting(this.Field1902);
    public Setting<ColorValue> lOutlineC = new Setting<>("LOutlineC", new ColorValue(-65536)).setParentSetting(this.Field1902);
    public Setting<ParentSetting> deaths = new Setting<>("Deaths", new ParentSetting(false));
    public Setting<Boolean> renderDeaths = new Setting<>("RenderDeaths", true).setParentSetting(this.Field1910);
    public Setting<Boolean> onlyLast = new Setting<>("OnlyLast", false).setParentSetting(this.Field1910);
    public Setting<Boolean> dTracers = new Setting<>("DTracers", false).setParentSetting(this.Field1910);
    public Setting<ColorValue> dTracersC = new Setting<>("DTracersC", new ColorValue(-16711936)).setParentSetting(this.Field1910);
    public Setting<Boolean> dFill = new Setting<>("DFill", true).setParentSetting(this.Field1910);
    public Setting<ColorValue> dFillC = new Setting<>("DFillC", new ColorValue(0x5500FF00)).setParentSetting(this.Field1910);
    public Setting<Boolean> dOutline = new Setting<>("DOutline", true).setParentSetting(this.Field1910);
    public Setting<ColorValue> dOutlineC = new Setting<>("DOutlineC", new ColorValue(-16711936)).setParentSetting(this.Field1910);
    public ConcurrentHashMap<EntityPlayer, Long> Field1919 = new ConcurrentHashMap();
    public DecimalFormat Field1920 = new DecimalFormat("#.##");

    @Subscriber
    public void Method1796(uuidHelper uuidHelper) {
        if (Waypoints.mc.player == null || Waypoints.mc.world == null) {
            return;
        }
        if (!this.renderLogouts.getValue().booleanValue()) {
            return;
        }
        EntityPlayer entityPlayer = Waypoints.mc.world.getPlayerEntityByUUID(uuidHelper.Method209());
        if (entityPlayer != null) {
            this.Field1919.put(entityPlayer, System.currentTimeMillis());
        }
    }

    public void Method1797(AxisAlignedBB axisAlignedBB, Class197 class197) {
        block13: {
            ColorValue colorValue = null;
            ColorValue class4402 = null;
            ColorValue class4403 = null;
            switch (Class199.Field47[class197.ordinal()]) {
                case 1: {
                    if (this.cOutline.getValue().booleanValue()) {
                        colorValue = this.cOutlineC.getValue();
                    }
                    if (this.cFill.getValue().booleanValue()) {
                        class4402 = this.cFillC.getValue();
                    }
                    if (!this.cTracers.getValue().booleanValue()) break;
                    class4403 = this.cTracersC.getValue();
                    break;
                }
                case 2: {
                    if (this.lOutline.getValue().booleanValue()) {
                        colorValue = this.lOutlineC.getValue();
                    }
                    if (this.lFill.getValue().booleanValue()) {
                        class4402 = this.lFillC.getValue();
                    }
                    if (!this.lTracers.getValue().booleanValue()) break;
                    class4403 = this.lTracersC.getValue();
                    break;
                }
                case 3: {
                    if (this.dOutline.getValue().booleanValue()) {
                        colorValue = this.dOutlineC.getValue();
                    }
                    if (this.dFill.getValue().booleanValue()) {
                        class4402 = this.dFillC.getValue();
                    }
                    if (!this.dTracers.getValue().booleanValue()) break;
                    class4403 = this.dTracersC.getValue();
                }
            }
            if (class4402 != null) {
                EspRenderUtil.Method1386();
                EspRenderUtil.Method1379(axisAlignedBB, class4402);
                EspRenderUtil.Method1385();
            }
            if (colorValue != null) {
                EspRenderUtil.Method1386();
                EspRenderUtil.Method1374(axisAlignedBB, 1.5, colorValue);
                EspRenderUtil.Method1385();
            }
            if (class4403 == null) break block13;
            Vec3d vec3d = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-((float)Math.toRadians(Waypoints.mc.player.rotationPitch))).rotateYaw(-((float)Math.toRadians(Waypoints.mc.player.rotationYaw)));
            Vec3d vec3d2 = new Vec3d(axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) * 0.5, axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) * 0.5, axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) * 0.5);
            Search.Method732(vec3d.x, vec3d.y + (double) Waypoints.mc.player.getEyeHeight(), vec3d.z, vec3d2.x - ((IRenderManager)mc.getRenderManager()).getRenderPosX(), vec3d2.y - ((IRenderManager)mc.getRenderManager()).getRenderPosY(), vec3d2.z - ((IRenderManager)mc.getRenderManager()).getRenderPosZ(), class4403.Method774());
        }
    }

    @Subscriber
    public void Method139(Render3DEvent render3DEvent) {
        if (Waypoints.mc.world == null || Waypoints.mc.player == null) {
            return;
        }
        if (this.renderLogouts.getValue().booleanValue()) {
            for (Map.Entry object : this.Field1919.entrySet()) {
                EntityPlayer entityPlayer = (EntityPlayer)object.getKey();
                if (entityPlayer == Waypoints.mc.player) continue;
                this.Method1797(entityPlayer.getEntityBoundingBox(), Class197.LOGOUT);
            }
        }
        for (Class559 class559 : KonasGlobals.INSTANCE.Field1138.Method759()) {
            this.Method1797(new AxisAlignedBB(class559.Method821(), class559.Method820(), class559.Method818(), class559.Method821() + 1.0, class559.Method820() + 2.0, class559.Method818() + 1.0), class559.Method815() == Class561.DEATH ? Class197.DEATH : Class197.CUSTOM);
        }
    }

    @Subscriber
    public void Method1451(OpenGuiEvent openGuiEvent) {
        if (openGuiEvent.Method1161() instanceof GuiConnecting || openGuiEvent.Method1161() instanceof GuiDownloadTerrain || openGuiEvent.Method1161() instanceof GuiDisconnected || openGuiEvent.Method1161() instanceof GuiMultiplayer) {
            this.Field1919.clear();
        } else if (openGuiEvent.Method1161() instanceof GuiGameOver && this.renderDeaths.getValue().booleanValue()) {
            if (this.onlyLast.getValue().booleanValue()) {
                KonasGlobals.INSTANCE.Field1138.Method764();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
            Date date = new Date();
            KonasGlobals.INSTANCE.Field1138.Method760(new Class559("Death_" + simpleDateFormat.format(date), Double.parseDouble(this.Field1920.format(Waypoints.mc.player.posX)), Double.parseDouble(this.Field1920.format(Waypoints.mc.player.posY)), Double.parseDouble(this.Field1920.format(Waypoints.mc.player.posZ)), Waypoints.mc.player.dimension, Class561.DEATH));
        }
    }

    @Subscriber
    public void Method1798(Class16 class16) {
        if (Waypoints.mc.player == null || Waypoints.mc.world == null) {
            return;
        }
        if (!this.renderLogouts.getValue().booleanValue()) {
            return;
        }
        for (Map.Entry<EntityPlayer, Long> entry : this.Field1919.entrySet()) {
            if (!entry.getKey().getUniqueID().equals(class16.Method209())) continue;
            this.Field1919.remove(entry.getKey());
        }
    }

    public Waypoints() {
        super("Waypoints", "Shows waypoints", Category.CLIENT);
    }

    public ConcurrentHashMap<EntityPlayer, Long> Method1799() {
        return this.Field1919;
    }
}