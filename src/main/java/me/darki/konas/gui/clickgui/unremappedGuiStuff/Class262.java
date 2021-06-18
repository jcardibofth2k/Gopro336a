package me.darki.konas.gui.clickgui.unremappedGuiStuff;

import java.io.IOException;

import me.darki.konas.command.commands.Party;
import me.darki.konas.unremaped.Class492;
import me.darki.konas.unremaped.Class557;
import me.darki.konas.util.TimerUtil;
import me.darki.konas.util.RenderUtil2;
import me.darki.konas.module.client.KonasGlobals;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.util.math.Vec3d;

public class Class262
extends GuiScreen {
    public EntityPlayer Field2011;
    public TimerUtil Field2012 = new TimerUtil();

    public void drawScreen(final int n, final int n2, final float n3) {
        super.drawScreen(n, n2, n3);
        final Vec3d method2026 = KonasGlobals.INSTANCE.Field1137.Method2026(new Vec3d(this.Field2011.lastTickPosX + (this.Field2011.posX - this.Field2011.lastTickPosX) * this.mc.getRenderPartialTicks(), this.Field2011.lastTickPosY + (this.Field2011.posY - this.Field2011.lastTickPosY) * this.mc.getRenderPartialTicks(), this.Field2011.lastTickPosZ + (this.Field2011.posZ - this.Field2011.lastTickPosZ) * this.mc.getRenderPartialTicks()).addVector(0.0, (double)this.Field2011.height, 0.0));
        final boolean method2027 = Class492.Method1989(this.Field2011.getName());
        final boolean contains = Party.Field2509.contains(this.Field2011.getName());
        boolean b = false;
        if (this.mc.getCurrentServerData() != null && this.mc.getCurrentServerData().serverIP != null && this.mc.getCurrentServerData().serverIP.contains("pvp")) {
            b = true;
        }
        final float max = Math.max(Class557.Method800(method2027 ? "Unfriend" : "Friend"), Class557.Method800(contains ? "Unparty" : "Party"));
        float n4 = 8.0f + Class557.Method799(method2027 ? "Unfriend" : "Friend") + Class557.Method799(contains ? "Unparty" : "Party");
        if (b) {
            n4 += 4.0f + Class557.Method799("Duel");
        }
        RenderUtil2.Method1338((float)method2026.x - max / 2.0f - 2.0f, (float)method2026.y - 2.0f, max + 4.0f, n4, Integer.MIN_VALUE);
        RenderUtil2.Method1336((float)method2026.x - max / 2.0f - 2.0f, (float)method2026.y - 2.0f, max + 4.0f, n4, 1.0f, -805306368);
        Class557.Method798(method2027 ? "Unfriend" : "Friend", (float)method2026.x - Class557.Method800(method2027 ? "Unfriend" : "Friend") / 2.0f, (float)method2026.y, -1);
        Class557.Method798(contains ? "Unparty" : "Party", (float)method2026.x - Class557.Method800(contains ? "Unparty" : "Party") / 2.0f, (float)method2026.y + 4.0f + Class557.Method799(method2027 ? "Unfriend" : "Friend"), -1);
        if (b) {
            Class557.Method798("Duel", (float)method2026.x - Class557.Method800("Duel") / 2.0f, (float)method2026.y + 4.0f + Class557.Method799(method2027 ? "Unfriend" : "Friend") + 4.0f + Class557.Method799(contains ? "Unparty" : "Party"), -1);
        }
        if (this.Field2012.GetDifferenceTiming(5000.0)) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
    }

    public void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        final Vec3d method2026 = KonasGlobals.INSTANCE.Field1137.Method2026(new Vec3d(this.Field2011.lastTickPosX + (this.Field2011.posX - this.Field2011.lastTickPosX) * this.mc.getRenderPartialTicks(), this.Field2011.lastTickPosY + (this.Field2011.posY - this.Field2011.lastTickPosY) * this.mc.getRenderPartialTicks(), this.Field2011.lastTickPosZ + (this.Field2011.posZ - this.Field2011.lastTickPosZ) * this.mc.getRenderPartialTicks()).addVector(0.0, (double) this.Field2011.height, 0.0));
        final boolean method2027 = Class492.Method1989(this.Field2011.getName());
        final boolean contains = Party.Field2509.contains(this.Field2011.getName());
        boolean b = false;
        if (this.mc.getCurrentServerData() != null && this.mc.getCurrentServerData().serverIP != null && this.mc.getCurrentServerData().serverIP.contains("pvp")) {
            b = true;
        }
        if (n3 == 0) {
            if (Method1842(n, n2, (float) method2026.x - Class557.Method800(method2027 ? "Unfriend" : "Friend") / 2.0f, (float) method2026.y, Class557.Method800(method2027 ? "Unfriend" : "Friend"), Class557.Method799(method2027 ? "Unfriend" : "Friend"))) {
                if (method2027) {
                    Class492.Method1992(this.Field2011.getName());
                } else {
                    Class492.Method1990(this.Field2011.getName(), this.Field2011.getUniqueID().toString().replace("-", ""));
                }
                this.mc.getSoundHandler().playSound((ISound) PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
                this.mc.displayGuiScreen((GuiScreen) null);
            } else if (Method1842(n, n2, (float) method2026.x - Class557.Method800(contains ? "Unparty" : "Party") / 2.0f, (float) method2026.y + 4.0f + Class557.Method799(method2027 ? "Unfriend" : "Friend"), Class557.Method800(contains ? "Unparty" : "Party"), Class557.Method799(contains ? "Unparty" : "Party"))) {
                if (contains) {
                    Party.Field2509.remove(this.Field2011.getName());
                } else {
                    Party.Field2509.add(this.Field2011.getName());
                }
                this.mc.getSoundHandler().playSound((ISound) PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
                this.mc.displayGuiScreen((GuiScreen) null);
            } else if (b && Method1842(n, n2, (float) method2026.x - Class557.Method800(contains ? "Unparty" : "Party") / 2.0f, (float) method2026.y + 4.0f + Class557.Method799(method2027 ? "Unfriend" : "Friend") + 4.0f + Class557.Method799(contains ? "Unparty" : "Party"), Class557.Method800("Duel"), Class557.Method799("Duel"))) {
                this.mc.getSoundHandler().playSound((ISound) PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
                this.mc.displayGuiScreen((GuiScreen) null);
                this.mc.player.connection.sendPacket((Packet) new CPacketChatMessage("/duel " + this.Field2011.getName()));
            }
        }

    }

    public Class262(EntityPlayer entityPlayer) {
        this.Field2011 = entityPlayer;
        this.Field2012.UpdateCurrentTime();
    }

    public static boolean Method1842(int n, int n2, double d, double d2, double d3, double d4) {
        return (double)n >= d && (double)n <= d + d3 && (double)n2 >= d2 && (double)n2 <= d2 + d4;
    }
}
