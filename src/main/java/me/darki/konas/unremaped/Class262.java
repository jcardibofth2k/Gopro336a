package me.darki.konas.unremaped;

import java.io.IOException;

import me.darki.konas.command.commands.Party;
import me.darki.konas.util.RenderUtil2;
import me.darki.konas.command.commands.PartyCommand;
import me.darki.konas.module.client.KonasGlobals;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;

public class Class262
extends GuiScreen {
    public EntityPlayer Field2011;
    public Class566 Field2012 = new Class566();

    public void drawScreen(int n, int n2, float f) {
        block3: {
            super.drawScreen(n, n2, f);
            vector3DThing();
            Vec3d vec3d2 = KonasGlobals.INSTANCE.Field1137.Method2026(vec3d);
            boolean bl = Class492.Method1989(this.Field2011.getName());
            boolean bl2 = PartyCommand.Field2509.contains(this.Field2011.getName());
            boolean bl3 = false;
            if (this.mc.getCurrentServerData() != null && this.mc.getCurrentServerData().serverIP != null && this.mc.getCurrentServerData().serverIP.contains("pvp")) {
                bl3 = true;
            }
            float f2 = Math.max(Class557.Method800(bl ? "Unfriend" : "Friend"), Class557.Method800(bl2 ? "Unparty" : "Party"));
            float f3 = 8.0f + Class557.Method799(bl ? "Unfriend" : "Friend") + Class557.Method799(bl2 ? "Unparty" : "Party");
            if (bl3) {
                f3 += 4.0f + Class557.Method799("Duel");
            }
            RenderUtil2.Method1338((float)vec3d2.x - f2 / 2.0f - 2.0f, (float)vec3d2.y - 2.0f, f2 + 4.0f, f3, Integer.MIN_VALUE);
            RenderUtil2.Method1336((float)vec3d2.x - f2 / 2.0f - 2.0f, (float)vec3d2.y - 2.0f, f2 + 4.0f, f3, 1.0f, -805306368);
            Class557.Method798(bl ? "Unfriend" : "Friend", (float)vec3d2.x - Class557.Method800(bl ? "Unfriend" : "Friend") / 2.0f, (float)vec3d2.y, -1);
            Class557.Method798(bl2 ? "Unparty" : "Party", (float)vec3d2.x - Class557.Method800(bl2 ? "Unparty" : "Party") / 2.0f, (float)vec3d2.y + 4.0f + Class557.Method799(bl ? "Unfriend" : "Friend"), -1);
            if (bl3) {
                Class557.Method798("Duel", (float)vec3d2.x - Class557.Method800("Duel") / 2.0f, (float)vec3d2.y + 4.0f + Class557.Method799(bl ? "Unfriend" : "Friend") + 4.0f + Class557.Method799(bl2 ? "Unparty" : "Party"), -1);
            }
            if (!this.Field2012.Method737(5000.0)) break block3;
            this.mc.displayGuiScreen(null);
        }
    }

    public void mouseClicked(int n, int n2, int n3) throws IOException {
        block5: {
            boolean bl;
            boolean bl2;
            boolean bl3;
            Vec3d vec3d;
            block7: {
                block6: {
                    super.mouseClicked(n, n2, n3);
                    vector3DThing();
                    Vec3d vec3d2;
                    vec3d = KonasGlobals.INSTANCE.Field1137.Method2026(vec3d2);
                    bl3 = Class492.Method1989(this.Field2011.getName());
                    bl2 = PartyCommand.Field2509.contains(this.Field2011.getName());
                    bl = false;
                    if (this.mc.getCurrentServerData() != null && this.mc.getCurrentServerData().serverIP != null && this.mc.getCurrentServerData().serverIP.contains("pvp")) {
                        bl = true;
                    }
                    if (n3 != 0) break block5;
                    if (!Class262.Method1842(n, n2, (float)vec3d.x - Class557.Method800(bl3 ? "Unfriend" : "Friend") / 2.0f, (float)vec3d.y, Class557.Method800(bl3 ? "Unfriend" : "Friend"), Class557.Method799(bl3 ? "Unfriend" : "Friend"))) break block6;
                    if (bl3) {
                        Class492.Method1992(this.Field2011.getName());
                    } else {
                        Class492.Method1990(this.Field2011.getName(), this.Field2011.getUniqueID().toString().replace("-", ""));
                    }
                    this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
                    this.mc.displayGuiScreen(null);
                    break block5;
                }
                if (!Class262.Method1842(n, n2, (float)vec3d.x - Class557.Method800(bl2 ? "Unparty" : "Party") / 2.0f, (float)vec3d.y + 4.0f + Class557.Method799(bl3 ? "Unfriend" : "Friend"), Class557.Method800(bl2 ? "Unparty" : "Party"), Class557.Method799(bl2 ? "Unparty" : "Party"))) break block7;
                if (bl2) {
                    PartyCommand.Field2509.remove(this.Field2011.getName());
                } else {
                    PartyCommand.Field2509.add(this.Field2011.getName());
                }
                this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
                this.mc.displayGuiScreen(null);
                break block5;
            }
            if (!bl || !Class262.Method1842(n, n2, (float)vec3d.x - Class557.Method800(bl2 ? "Unparty" : "Party") / 2.0f, (float)vec3d.y + 4.0f + Class557.Method799(bl3 ? "Unfriend" : "Friend") + 4.0f + Class557.Method799(bl2 ? "Unparty" : "Party"), Class557.Method800("Duel"), Class557.Method799("Duel"))) break block5;
            this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
            this.mc.displayGuiScreen(null);
            this.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("/duel " + this.Field2011.getName()));
        }
    }

    public void drawScreen(final int n, final int n2, final float n3) {
        super.drawScreen(n, n2, n3);
        final Vec3d method2026 = KonasGlobals.INSTANCE.Field1137.Method2026(new Vec3d(this.Field2011.lastTickPosX + (this.Field2011.posX - this.Field2011.lastTickPosX) * this.mc.getRenderPartialTicks(), this.Field2011.lastTickPosY + (this.Field2011.posY - this.Field2011.lastTickPosY) * this.mc.getRenderPartialTicks(), this.Field2011.lastTickPosZ + (this.Field2011.posZ - this.Field2011.lastTickPosZ) * this.mc.getRenderPartialTicks()).add(0.0, (double)this.Field2011.height, 0.0));
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
        Class510.Method1338((float)method2026.x - max / 2.0f - 2.0f, (float)method2026.y - 2.0f, max + 4.0f, n4, Integer.MIN_VALUE);
        Class510.Method1336((float)method2026.x - max / 2.0f - 2.0f, (float)method2026.y - 2.0f, max + 4.0f, n4, 1.0f, -805306368);
        Class557.Method798(method2027 ? "Unfriend" : "Friend", (float)method2026.x - Class557.Method800(method2027 ? "Unfriend" : "Friend") / 2.0f, (float)method2026.y, -1);
        Class557.Method798(contains ? "Unparty" : "Party", (float)method2026.x - Class557.Method800(contains ? "Unparty" : "Party") / 2.0f, (float)method2026.y + 4.0f + Class557.Method799(method2027 ? "Unfriend" : "Friend"), -1);
        if (b) {
            Class557.Method798("Duel", (float)method2026.x - Class557.Method800("Duel") / 2.0f, (float)method2026.y + 4.0f + Class557.Method799(method2027 ? "Unfriend" : "Friend") + 4.0f + Class557.Method799(contains ? "Unparty" : "Party"), -1);
        }
        if (this.Field2012.Method737(5000.0)) {
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
        this.Field2012.Method739();
    }

    public static boolean Method1842(int n, int n2, double d, double d2, double d3, double d4) {
        return (double)n >= d && (double)n <= d + d3 && (double)n2 >= d2 && (double)n2 <= d2 + d4;
    }
}
