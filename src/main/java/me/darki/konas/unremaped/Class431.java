package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import io.netty.util.internal.ConcurrentSet;
import java.awt.Color;
import java.util.Set;

import me.darki.konas.setting.ColorValue;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.ChunkPos;

public class Class431
extends Module {
    public ICamera Field799 = new Frustum();
    public Set<ChunkPos> Field800 = new ConcurrentSet();
    public Setting<ColorValue> color = new Setting<>("Color", new ColorValue(new Color(0.8392157f, 0.3372549f, 0.5764706f, 0.39215687f).hashCode(), false));

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        if (packetEvent.getPacket() instanceof SPacketChunkData) {
            SPacketChunkData sPacketChunkData = (SPacketChunkData) packetEvent.getPacket();
            if (sPacketChunkData.isFullChunk()) {
                return;
            }
            ChunkPos chunkPos = new ChunkPos(sPacketChunkData.getChunkX(), sPacketChunkData.getChunkZ());
            this.Field800.add(chunkPos);
        }
    }

    @Subscriber
    public void Method139(Render3DEvent render3DEvent) {
        if (mc.getRenderViewEntity() == null) {
            return;
        }
        this.Field799.setPosition(Class431.mc.getRenderViewEntity().posX, Class431.mc.getRenderViewEntity().posY, Class431.mc.getRenderViewEntity().posZ);
        GlStateManager.pushMatrix();
        Class516.Method1289();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
        GlStateManager.depthMask((boolean)false);
        GlStateManager.glLineWidth((float)2.0f);
        for (ChunkPos chunkPos : this.Field800) {
            AxisAlignedBB axisAlignedBB = new AxisAlignedBB((double)chunkPos.getXStart(), 0.0, (double)chunkPos.getZStart(), (double)chunkPos.getXEnd(), 0.0, (double)chunkPos.getZEnd());
            GlStateManager.pushMatrix();
            if (this.Field799.isBoundingBoxInFrustum(axisAlignedBB)) {
                double d = Class431.mc.player.lastTickPosX + (Class431.mc.player.posX - Class431.mc.player.lastTickPosX) * (double) render3DEvent.Method436();
                double d2 = Class431.mc.player.lastTickPosY + (Class431.mc.player.posY - Class431.mc.player.lastTickPosY) * (double) render3DEvent.Method436();
                double d3 = Class431.mc.player.lastTickPosZ + (Class431.mc.player.posZ - Class431.mc.player.lastTickPosZ) * (double) render3DEvent.Method436();
                RenderUtil3D.Method1414(axisAlignedBB.offset(-d, -d2, -d3), 3, ((ColorValue)this.color.getValue()).Method774());
            }
            GlStateManager.popMatrix();
        }
        GlStateManager.glLineWidth((float)1.0f);
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableAlpha();
        Class516.Method1261();
        GlStateManager.popMatrix();
    }

    @Override
    public void onEnable() {
        this.Field800.clear();
    }

    public Class431() {
        super("NewChunks", Category.RENDER, new String[0]);
    }
}