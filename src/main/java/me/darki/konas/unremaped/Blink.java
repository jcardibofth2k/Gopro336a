package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

import me.darki.konas.setting.ColorValue;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.mixin.mixins.IRenderManager;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketTabComplete;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.lwjgl.opengl.GL11;

public class Blink
extends Module {
    public Setting<Boolean> pulse = new Setting<>("Pulse", false);
    public Setting<Boolean> strict = new Setting<>("Strict", false);
    public Setting<Float> factor = new Setting<>("Factor", 1.0f, 10.0f, 0.1f, 0.1f);
    public static Setting<Boolean> render = new Setting<>("Render", true);
    public static Setting<Boolean> fill = new Setting<>("Fill", true).visibleIf(render::getValue);
    public static Setting<Float> width = new Setting<>("Width", 2.5f, 5.0f, 0.1f, 0.1f).visibleIf(render::getValue);
    public static Setting<ColorValue> color = new Setting<>("Color", new ColorValue(869950564, true)).visibleIf(render::getValue);
    public Queue<Packet> Field2620 = new LinkedList<Packet>();
    public Vec3d Field2621 = new Vec3d((Vec3i)BlockPos.ORIGIN);
    public AtomicBoolean Field2622 = new AtomicBoolean(false);

    @Override
    public void onEnable() {
        if (Blink.mc.player == null || Blink.mc.world == null || mc.isIntegratedServerRunning()) {
            this.toggle();
            return;
        }
        this.Field2621 = Blink.mc.player.getPositionVector();
        this.Field2622.set(false);
        this.Field2620.clear();
        this.Method124();
    }

    public Blink() {
        super("Blink", Category.EXPLOIT, "FakeLag", "InstantMove");
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        block1: {
            if (!((Boolean)this.pulse.getValue()).booleanValue() || tickEvent.Method324() != net.minecraftforge.fml.common.gameevent.TickEvent.Phase.START || Blink.mc.player == null || Blink.mc.world == null || !((float)this.Field2620.size() >= ((Float)this.factor.getValue()).floatValue() * 10.0f)) break block1;
            this.Field2622.set(true);
            while (!this.Field2620.isEmpty()) {
                Packet packet = this.Field2620.poll();
                Blink.mc.player.connection.sendPacket(packet);
                if (!(packet instanceof CPacketPlayer)) continue;
                this.Field2621 = new Vec3d(((CPacketPlayer)packet).getX(Blink.mc.player.posX), ((CPacketPlayer)packet).getY(Blink.mc.player.posY), ((CPacketPlayer)packet).getZ(Blink.mc.player.posZ));
            }
            this.Field2622.set(false);
            this.Field2620.clear();
            this.Method124();
        }
    }

    @Subscriber
    public void Method139(Class89 class89) {
        block9: {
            int n;
            int n2;
            int n3;
            float f;
            if (Blink.mc.player == null || Blink.mc.world == null) {
                return;
            }
            if (!((Boolean)render.getValue()).booleanValue() || this.Field2621 == null) break block9;
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            GL11.glEnable((int)3008);
            GL11.glBlendFunc((int)770, (int)771);
            GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
            IRenderManager iRenderManager = (IRenderManager)mc.getRenderManager();
            float[] fArray = Color.RGBtoHSB(((ColorValue)color.getValue()).Method769(), ((ColorValue)color.getValue()).Method770(), ((ColorValue)color.getValue()).Method779(), null);
            float f2 = f = (float)(System.currentTimeMillis() % 7200L) / 7200.0f;
            int n4 = Color.getHSBColor(f2, fArray[1], fArray[2]).getRGB();
            ArrayList<Vec3d> arrayList = new ArrayList<Vec3d>();
            double d = this.Field2621.x - iRenderManager.getRenderPosX();
            double d2 = this.Field2621.y - iRenderManager.getRenderPosY();
            double d3 = this.Field2621.z - iRenderManager.getRenderPosZ();
            GL11.glShadeModel((int)7425);
            GlStateManager.disableCull();
            GL11.glLineWidth((float)((Float)width.getValue()).floatValue());
            GL11.glBegin((int)1);
            for (n3 = 0; n3 <= 360; ++n3) {
                Vec3d vec3d = new Vec3d(d + Math.sin((double)n3 * Math.PI / 180.0) * 0.5, d2 + 0.01, d3 + Math.cos((double)n3 * Math.PI / 180.0) * 0.5);
                arrayList.add(vec3d);
            }
            for (n3 = 0; n3 < arrayList.size() - 1; ++n3) {
                int n5 = n4 >> 16 & 0xFF;
                n2 = n4 >> 8 & 0xFF;
                n = n4 & 0xFF;
                if (((ColorValue)color.getValue()).Method783()) {
                    GL11.glColor4f((float)((float)n5 / 255.0f), (float)((float)n2 / 255.0f), (float)((float)n / 255.0f), (float)((Boolean)fill.getValue() != false ? 1.0f : (float)((ColorValue)color.getValue()).Method782() / 255.0f));
                } else {
                    GL11.glColor4f((float)((float)((ColorValue)color.getValue()).Method769() / 255.0f), (float)((float)((ColorValue)color.getValue()).Method770() / 255.0f), (float)((float)((ColorValue)color.getValue()).Method779() / 255.0f), (float)((Boolean)fill.getValue() != false ? 1.0f : (float)((ColorValue)color.getValue()).Method782() / 255.0f));
                }
                GL11.glVertex3d((double)((Vec3d)arrayList.get((int)n3)).x, (double)((Vec3d)arrayList.get((int)n3)).y, (double)((Vec3d)arrayList.get((int)n3)).z);
                GL11.glVertex3d((double)((Vec3d)arrayList.get((int)(n3 + 1))).x, (double)((Vec3d)arrayList.get((int)(n3 + 1))).y, (double)((Vec3d)arrayList.get((int)(n3 + 1))).z);
                n4 = Color.getHSBColor(f2 += 0.0027777778f, fArray[1], fArray[2]).getRGB();
            }
            GL11.glEnd();
            if (((Boolean)fill.getValue()).booleanValue()) {
                f2 = f;
                GL11.glBegin((int)9);
                for (n3 = 0; n3 < arrayList.size() - 1; ++n3) {
                    int n6 = n4 >> 16 & 0xFF;
                    n2 = n4 >> 8 & 0xFF;
                    n = n4 & 0xFF;
                    if (((ColorValue)color.getValue()).Method783()) {
                        GL11.glColor4f((float)((float)n6 / 255.0f), (float)((float)n2 / 255.0f), (float)((float)n / 255.0f), (float)((float)((ColorValue)color.getValue()).Method782() / 255.0f));
                    } else {
                        GL11.glColor4f((float)((float)((ColorValue)color.getValue()).Method769() / 255.0f), (float)((float)((ColorValue)color.getValue()).Method770() / 255.0f), (float)((float)((ColorValue)color.getValue()).Method779() / 255.0f), (float)((float)((ColorValue)color.getValue()).Method782() / 255.0f));
                    }
                    GL11.glVertex3d((double)((Vec3d)arrayList.get((int)n3)).x, (double)((Vec3d)arrayList.get((int)n3)).y, (double)((Vec3d)arrayList.get((int)n3)).z);
                    GL11.glVertex3d((double)((Vec3d)arrayList.get((int)(n3 + 1))).x, (double)((Vec3d)arrayList.get((int)(n3 + 1))).y, (double)((Vec3d)arrayList.get((int)(n3 + 1))).z);
                    n4 = Color.getHSBColor(f2 += 0.0027777778f, fArray[1], fArray[2]).getRGB();
                }
                GL11.glEnd();
            }
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glDisable((int)3008);
            GlStateManager.enableCull();
            GL11.glShadeModel((int)7424);
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }

    public void Method124() {
        this.Method1645(this.Field2620.size() + "");
    }

    @Subscriber
    public void Method536(Class24 class24) {
        block5: {
            Packet packet;
            block4: {
                packet = class24.getPacket();
                if (this.Field2622.get()) {
                    return;
                }
                if (!((Boolean)this.pulse.getValue()).booleanValue()) break block4;
                if (!(class24.getPacket() instanceof CPacketPlayer)) break block5;
                if (((Boolean)this.strict.getValue()).booleanValue() && !((CPacketPlayer)class24.getPacket()).isOnGround()) {
                    this.Field2622.set(true);
                    while (!this.Field2620.isEmpty()) {
                        Packet packet2 = this.Field2620.poll();
                        Blink.mc.player.connection.sendPacket(packet2);
                        if (!(packet2 instanceof CPacketPlayer)) continue;
                        this.Field2621 = new Vec3d(((CPacketPlayer)packet2).getX(Blink.mc.player.posX), ((CPacketPlayer)packet2).getY(Blink.mc.player.posY), ((CPacketPlayer)packet2).getZ(Blink.mc.player.posZ));
                    }
                    this.Field2622.set(false);
                    this.Field2620.clear();
                    this.Method124();
                } else {
                    class24.Cancel();
                    this.Field2620.add(class24.getPacket());
                    this.Method124();
                }
                break block5;
            }
            if (packet instanceof CPacketChatMessage || packet instanceof CPacketConfirmTeleport || packet instanceof CPacketKeepAlive || packet instanceof CPacketTabComplete || packet instanceof CPacketClientStatus) break block5;
            class24.Cancel();
            this.Field2620.add(class24.getPacket());
            this.Method124();
        }
    }

    @Override
    public void onDisable() {
        if (Blink.mc.world == null || Blink.mc.player == null) {
            return;
        }
        while (!this.Field2620.isEmpty()) {
            Blink.mc.player.connection.sendPacket(this.Field2620.poll());
        }
    }
}