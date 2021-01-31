package me.darki.konas.module.render;

import cookiedragon.eventsystem.Subscriber;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.darki.konas.*;
import me.darki.konas.mixin.mixins.IRenderManager;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.NewGui;
import me.darki.konas.setting.Setting;
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
    public Setting<ParentSetting> Field1895 = new Setting<>("Custom", new ParentSetting(false));
    public Setting<Boolean> Field1896 = new Setting<>("CTracers", false).setParentSetting(this.Field1895);
    public Setting<ColorValue> Field1897 = new Setting<>("CTracersC", new ColorValue(-16776961)).setParentSetting(this.Field1895);
    public Setting<Boolean> Field1898 = new Setting<>("CFill", true).setParentSetting(this.Field1895);
    public Setting<ColorValue> Field1899 = new Setting<>("CFillC", new ColorValue(0x550000FF)).setParentSetting(this.Field1895);
    public Setting<Boolean> Field1900 = new Setting<>("COutline", true).setParentSetting(this.Field1895);
    public Setting<ColorValue> Field1901 = new Setting<>("COutlineC", new ColorValue(-16776961)).setParentSetting(this.Field1895);
    public Setting<ParentSetting> Field1902 = new Setting<>("Logouts", new ParentSetting(false));
    public Setting<Boolean> Field1903 = new Setting<>("RenderLogouts", true).setParentSetting(this.Field1902);
    public Setting<Boolean> Field1904 = new Setting<>("LTracers", false).setParentSetting(this.Field1902);
    public Setting<ColorValue> Field1905 = new Setting<>("LTracersC", new ColorValue(-16777216)).setParentSetting(this.Field1902);
    public Setting<Boolean> Field1906 = new Setting<>("LFill", true).setParentSetting(this.Field1902);
    public Setting<ColorValue> Field1907 = new Setting<>("LFillC", new ColorValue(0x55FF00FF)).setParentSetting(this.Field1902);
    public Setting<Boolean> Field1908 = new Setting<>("LOutline", true).setParentSetting(this.Field1902);
    public Setting<ColorValue> Field1909 = new Setting<>("LOutlineC", new ColorValue(-65536)).setParentSetting(this.Field1902);
    public Setting<ParentSetting> Field1910 = new Setting<>("Deaths", new ParentSetting(false));
    public Setting<Boolean> Field1911 = new Setting<>("RenderDeaths", true).setParentSetting(this.Field1910);
    public Setting<Boolean> Field1912 = new Setting<>("OnlyLast", false).setParentSetting(this.Field1910);
    public Setting<Boolean> Field1913 = new Setting<>("DTracers", false).setParentSetting(this.Field1910);
    public Setting<ColorValue> Field1914 = new Setting<>("DTracersC", new ColorValue(-16711936)).setParentSetting(this.Field1910);
    public Setting<Boolean> Field1915 = new Setting<>("DFill", true).setParentSetting(this.Field1910);
    public Setting<ColorValue> Field1916 = new Setting<>("DFillC", new ColorValue(0x5500FF00)).setParentSetting(this.Field1910);
    public Setting<Boolean> Field1917 = new Setting<>("DOutline", true).setParentSetting(this.Field1910);
    public Setting<ColorValue> Field1918 = new Setting<>("DOutlineC", new ColorValue(-16711936)).setParentSetting(this.Field1910);
    public ConcurrentHashMap<EntityPlayer, Long> Field1919 = new ConcurrentHashMap();
    public DecimalFormat Field1920 = new DecimalFormat("#.##");

    @Subscriber
    public void Method1796(Class15 class15) {
        if (Waypoints.mc.player == null || Waypoints.mc.world == null) {
            return;
        }
        if (!((Boolean)this.Field1903.getValue()).booleanValue()) {
            return;
        }
        EntityPlayer entityPlayer = Waypoints.mc.world.getPlayerEntityByUUID(class15.Method209());
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
                    if (((Boolean)this.Field1900.getValue()).booleanValue()) {
                        colorValue = (ColorValue)this.Field1901.getValue();
                    }
                    if (((Boolean)this.Field1898.getValue()).booleanValue()) {
                        class4402 = (ColorValue)this.Field1899.getValue();
                    }
                    if (!((Boolean)this.Field1896.getValue()).booleanValue()) break;
                    class4403 = (ColorValue)this.Field1897.getValue();
                    break;
                }
                case 2: {
                    if (((Boolean)this.Field1908.getValue()).booleanValue()) {
                        colorValue = (ColorValue)this.Field1909.getValue();
                    }
                    if (((Boolean)this.Field1906.getValue()).booleanValue()) {
                        class4402 = (ColorValue)this.Field1907.getValue();
                    }
                    if (!((Boolean)this.Field1904.getValue()).booleanValue()) break;
                    class4403 = (ColorValue)this.Field1905.getValue();
                    break;
                }
                case 3: {
                    if (((Boolean)this.Field1917.getValue()).booleanValue()) {
                        colorValue = (ColorValue)this.Field1918.getValue();
                    }
                    if (((Boolean)this.Field1915.getValue()).booleanValue()) {
                        class4402 = (ColorValue)this.Field1916.getValue();
                    }
                    if (!((Boolean)this.Field1913.getValue()).booleanValue()) break;
                    class4403 = (ColorValue)this.Field1914.getValue();
                }
            }
            if (class4402 != null) {
                Class507.Method1386();
                Class507.Method1379(axisAlignedBB, class4402);
                Class507.Method1385();
            }
            if (colorValue != null) {
                Class507.Method1386();
                Class507.Method1374(axisAlignedBB, 1.5, colorValue);
                Class507.Method1385();
            }
            if (class4403 == null) break block13;
            Vec3d vec3d = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-((float)Math.toRadians(Waypoints.mc.player.rotationPitch))).rotateYaw(-((float)Math.toRadians(Waypoints.mc.player.rotationYaw)));
            Vec3d vec3d2 = new Vec3d(axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) * 0.5, axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) * 0.5, axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) * 0.5);
            Search.Method732(vec3d.x, vec3d.y + (double) Waypoints.mc.player.getEyeHeight(), vec3d.z, vec3d2.x - ((IRenderManager)mc.getRenderManager()).Method69(), vec3d2.y - ((IRenderManager)mc.getRenderManager()).Method70(), vec3d2.z - ((IRenderManager)mc.getRenderManager()).Method71(), class4403.Method774());
        }
    }

    @Subscriber
    public void Method139(Class89 class89) {
        if (Waypoints.mc.world == null || Waypoints.mc.player == null) {
            return;
        }
        if (((Boolean)this.Field1903.getValue()).booleanValue()) {
            for (Map.Entry object : this.Field1919.entrySet()) {
                EntityPlayer entityPlayer = (EntityPlayer)object.getKey();
                if (entityPlayer == Waypoints.mc.player) continue;
                this.Method1797(entityPlayer.getEntityBoundingBox(), Class197.LOGOUT);
            }
        }
        for (Class559 class559 : NewGui.INSTANCE.Field1138.Method759()) {
            this.Method1797(new AxisAlignedBB(class559.Method821(), class559.Method820(), class559.Method818(), class559.Method821() + 1.0, class559.Method820() + 2.0, class559.Method818() + 1.0), class559.Method815() == Class561.DEATH ? Class197.DEATH : Class197.CUSTOM);
        }
    }

    @Subscriber
    public void Method1451(Class654 class654) {
        if (class654.Method1161() instanceof GuiConnecting || class654.Method1161() instanceof GuiDownloadTerrain || class654.Method1161() instanceof GuiDisconnected || class654.Method1161() instanceof GuiMultiplayer) {
            this.Field1919.clear();
        } else if (class654.Method1161() instanceof GuiGameOver && ((Boolean)this.Field1911.getValue()).booleanValue()) {
            if (((Boolean)this.Field1912.getValue()).booleanValue()) {
                NewGui.INSTANCE.Field1138.Method764();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
            Date date = new Date();
            NewGui.INSTANCE.Field1138.Method760(new Class559("Death_" + simpleDateFormat.format(date), Double.parseDouble(this.Field1920.format(Waypoints.mc.player.posX)), Double.parseDouble(this.Field1920.format(Waypoints.mc.player.posY)), Double.parseDouble(this.Field1920.format(Waypoints.mc.player.posZ)), Waypoints.mc.player.dimension, Class561.DEATH));
        }
    }

    @Subscriber
    public void Method1798(Class16 class16) {
        if (Waypoints.mc.player == null || Waypoints.mc.world == null) {
            return;
        }
        if (!((Boolean)this.Field1903.getValue()).booleanValue()) {
            return;
        }
        for (Map.Entry<EntityPlayer, Long> entry : this.Field1919.entrySet()) {
            if (!entry.getKey().getUniqueID().equals(class16.Method209())) continue;
            this.Field1919.remove(entry.getKey());
        }
    }

    public Waypoints() {
        super("Waypoints", "Shows waypoints", Category.CLIENT, new String[0]);
    }

    public ConcurrentHashMap<EntityPlayer, Long> Method1799() {
        return this.Field1919;
    }
}
