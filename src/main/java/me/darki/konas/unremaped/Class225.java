package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.util.PlayerUtil;
import me.darki.konas.event.events.MoveEvent;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.mixin.mixins.ISPacketPlayerPosLook;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.util.TimerUtil;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public class Class225
extends Module {
    public Setting<Boolean> float = new Setting<>("Float", false);
    public boolean Field2641 = true;
    public TimerUtil Field2642 = new TimerUtil();

    /*
     * Enabled aggressive block sorting
     */
    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (Class225.mc.player == null) return;
        if (Class225.mc.world == null) {
            return;
        }
        if (PlayerUtil.Method1084() && Class225.mc.player.posY <= 1.0) {
            if (!this.Field2641) return;
            if (!this.Field2642.GetDifferenceTiming(1000.0)) return;
            this.Field2641 = false;
            Class225.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Class225.mc.player.posX, Class225.mc.player.posY + 0.1, Class225.mc.player.posZ, false));
            return;
        }
        this.Field2641 = true;
    }

    public Class225() {
        super("AntiVoid", Category.EXPLOIT, "NoVoid");
    }

    @Subscriber
    public void Method503(MoveEvent moveEvent) {
        block0: {
            if (!((Boolean)this.float.getValue()).booleanValue() || !PlayerUtil.Method1084() || !(Class225.mc.player.posY <= 1.0)) break block0;
            moveEvent.setY(-0.01);
        }
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        if (packetEvent.getPacket() instanceof SPacketPlayerPosLook && !(Class225.mc.currentScreen instanceof GuiDownloadTerrain)) {
            SPacketPlayerPosLook sPacketPlayerPosLook = (SPacketPlayerPosLook) packetEvent.getPacket();
            ((ISPacketPlayerPosLook)sPacketPlayerPosLook).setYaw(Class225.mc.player.rotationYaw);
            ((ISPacketPlayerPosLook)sPacketPlayerPosLook).setPitch(Class225.mc.player.rotationPitch);
            sPacketPlayerPosLook.getFlags().remove(SPacketPlayerPosLook.EnumFlags.X_ROT);
            sPacketPlayerPosLook.getFlags().remove(SPacketPlayerPosLook.EnumFlags.Y_ROT);
        }
    }
}