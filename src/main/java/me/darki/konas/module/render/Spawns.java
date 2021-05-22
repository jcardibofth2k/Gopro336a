package me.darki.konas.module.render;

import cookiedragon.eventsystem.Subscriber;
import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import me.darki.konas.setting.ColorValue;
import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.mixin.mixins.IRenderManager;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.SendPacketEvent;
import me.darki.konas.unremaped.Class442;
import me.darki.konas.unremaped.Render3DEvent;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.network.play.server.SPacketSpawnPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class Spawns
extends Module {
    public static Setting<Boolean> crystals = new Setting<>("Crystals", true);
    public static Setting<Boolean> onlyOwn = new Setting<>("OnlyOwn", false).visibleIf(crystals::getValue);
    public static Setting<Boolean> players = new Setting<>("Players", false);
    public static Setting<Boolean> mobs = new Setting<>("Mobs", false);
    public static Setting<Boolean> boats = new Setting<>("Boats", false);
    public static Setting<Float> duration = new Setting<>("Duration", Float.valueOf(1.0f), Float.valueOf(5.0f), Float.valueOf(0.1f), Float.valueOf(0.1f));
    public static Setting<ColorValue> color = new Setting<>("Color", new ColorValue(-65536, true));
    public static Setting<Float> width = new Setting<>("Width", Float.valueOf(2.5f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f));
    public CopyOnWriteArrayList<Class442> Field755 = new CopyOnWriteArrayList();
    public ConcurrentHashMap<BlockPos, Long> Field756 = new ConcurrentHashMap();

    @Subscriber
    public void Method135(UpdateEvent updateEvent) {
        block0: {
            if (Spawns.mc.player.ticksExisted % 20 != 0 || !onlyOwn.getValue().booleanValue()) break block0;
            this.Field756.forEach(this::Method792);
        }
    }

    public void Method792(BlockPos blockPos, Long l) {
        if (System.currentTimeMillis() - l > 2500L) {
            this.Field756.remove(blockPos);
        }
    }

    public Spawns() {
        super("Spawns", "Renders entity spawning", Category.RENDER);
    }

    @Subscriber
    public void Method139(Render3DEvent render3DEvent) {
        for (Class442 class442 : this.Field755) {
            int n;
            if ((float)(System.currentTimeMillis() - Class442.Method722(class442)) > 1000.0f * duration.getValue().floatValue()) {
                this.Field755.remove(class442);
                continue;
            }
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            IRenderManager iRenderManager = (IRenderManager)mc.getRenderManager();
            float[] fArray = Color.RGBtoHSB(color.getValue().Method769(), color.getValue().Method770(), color.getValue().Method779(), null);
            float f = (float)(System.currentTimeMillis() % 7200L) / 7200.0f;
            int n2 = Color.getHSBColor(f, fArray[1], fArray[2]).getRGB();
            ArrayList<Vec3d> arrayList = new ArrayList<Vec3d>();
            double d = Class442.Method719(class442).x - iRenderManager.getRenderPosX();
            double d2 = Class442.Method719(class442).y - iRenderManager.getRenderPosY();
            double d3 = Class442.Method719(class442).z - iRenderManager.getRenderPosZ();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glLineWidth(width.getValue().floatValue());
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glBegin(1);
            for (n = 0; n <= 360; ++n) {
                Vec3d vec3d = new Vec3d(d + Math.sin((double)n * Math.PI / 180.0) * (double)Class442.Method720(class442), d2 + (double)(Class442.Method721(class442) * ((float)(System.currentTimeMillis() - Class442.Method722(class442)) / (1000.0f * duration.getValue().floatValue()))), d3 + Math.cos((double)n * Math.PI / 180.0) * (double)Class442.Method720(class442));
                arrayList.add(vec3d);
            }
            for (n = 0; n < arrayList.size() - 1; ++n) {
                int n3 = n2 >> 24 & 0xFF;
                int n4 = n2 >> 16 & 0xFF;
                int n5 = n2 >> 8 & 0xFF;
                int n6 = n2 & 0xFF;
                if (color.getValue().Method783()) {
                    GL11.glColor4f((float)n4 / 255.0f, (float)n5 / 255.0f, (float)n6 / 255.0f, (float)n3 / 255.0f);
                } else {
                    GL11.glColor4f((float) color.getValue().Method769() / 255.0f, (float) color.getValue().Method770() / 255.0f, (float) color.getValue().Method779() / 255.0f, (float) color.getValue().Method782() / 255.0f);
                }
                GL11.glVertex3d(arrayList.get(n).x, arrayList.get(n).y, arrayList.get(n).z);
                GL11.glVertex3d(arrayList.get(n + 1).x, arrayList.get(n + 1).y, arrayList.get(n + 1).z);
                n2 = Color.getHSBColor(f += 0.0027777778f, fArray[1], fArray[2]).getRGB();
            }
            GL11.glEnd();
            GL11.glDisable(2848);
            GlStateManager.enableLighting();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        EntityLivingBase entityLivingBase;
        if (packetEvent.getPacket() instanceof SPacketSpawnObject) {
            if (((SPacketSpawnObject) packetEvent.getPacket()).getType() == 51 && crystals.getValue().booleanValue()) {
                if (onlyOwn.getValue().booleanValue()) {
                    BlockPos blockPos = new BlockPos(((SPacketSpawnObject) packetEvent.getPacket()).getX(), ((SPacketSpawnObject) packetEvent.getPacket()).getY(), ((SPacketSpawnObject) packetEvent.getPacket()).getZ()).down();
                    if (this.Field756.containsKey(blockPos)) {
                        this.Field756.remove(blockPos);
                        this.Field755.add(new Class442(new Vec3d(((SPacketSpawnObject) packetEvent.getPacket()).getX(), ((SPacketSpawnObject) packetEvent.getPacket()).getY(), ((SPacketSpawnObject) packetEvent.getPacket()).getZ()), 1.5f, 0.5f));
                    }
                } else {
                    this.Field755.add(new Class442(new Vec3d(((SPacketSpawnObject) packetEvent.getPacket()).getX(), ((SPacketSpawnObject) packetEvent.getPacket()).getY(), ((SPacketSpawnObject) packetEvent.getPacket()).getZ()), 1.5f, 0.5f));
                }
            } else if (((SPacketSpawnObject) packetEvent.getPacket()).getType() == 1 && boats.getValue().booleanValue()) {
                this.Field755.add(new Class442(new Vec3d(((SPacketSpawnObject) packetEvent.getPacket()).getX(), ((SPacketSpawnObject) packetEvent.getPacket()).getY(), ((SPacketSpawnObject) packetEvent.getPacket()).getZ()), 1.0f, 0.75f));
            }
        } else if (packetEvent.getPacket() instanceof SPacketSpawnPlayer && players.getValue().booleanValue()) {
            this.Field755.add(new Class442(new Vec3d(((SPacketSpawnPlayer) packetEvent.getPacket()).getX(), ((SPacketSpawnPlayer) packetEvent.getPacket()).getY(), ((SPacketSpawnPlayer) packetEvent.getPacket()).getZ()), 1.8f, 0.5f));
        } else if (packetEvent.getPacket() instanceof SPacketSpawnMob && mobs.getValue().booleanValue() && (entityLivingBase = (EntityLivingBase)EntityList.createEntityByID(((SPacketSpawnMob) packetEvent.getPacket()).getEntityType(), Spawns.mc.world)) != null) {
            this.Field755.add(new Class442(new Vec3d(((SPacketSpawnMob) packetEvent.getPacket()).getX(), ((SPacketSpawnMob) packetEvent.getPacket()).getY(), ((SPacketSpawnMob) packetEvent.getPacket()).getZ()), entityLivingBase.height, entityLivingBase.width / 2.0f));
        }
    }

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        if (sendPacketEvent.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && onlyOwn.getValue().booleanValue() && Spawns.mc.player.getHeldItem(((CPacketPlayerTryUseItemOnBlock) sendPacketEvent.getPacket()).getHand()).getItem() instanceof ItemEndCrystal) {
            this.Field756.put(((CPacketPlayerTryUseItemOnBlock) sendPacketEvent.getPacket()).getPos(), System.currentTimeMillis());
        }
    }
}