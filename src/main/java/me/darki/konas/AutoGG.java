package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import java.util.ArrayList;
import java.util.Random;

import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.world.World;

public class AutoGG
extends Module {
    public static Setting<Boolean> gg = new Setting<>("GG", true);
    public static Setting<Boolean> ez = new Setting<>("EZ", false);
    public static Setting<Boolean> log = new Setting<>("Log", false);
    public static Setting<Boolean> excuses = new Setting<>("Excuses", false);
    public static Setting<Boolean> pops = new Setting<>("Pops", false);
    public static ArrayList<String> Field1976 = new ArrayList();
    public static ArrayList<String> Field1977 = new ArrayList();
    public static ArrayList<String> Field1978 = new ArrayList();
    public static ArrayList<String> Field1979 = new ArrayList();
    public static ArrayList<String> Field1980 = new ArrayList();
    public Random Field1981 = new Random();
    public Class566 Field1982 = new Class566();

    public AutoGG() {
        super("AutoGG", Category.MISC, new String[0]);
        Field1976.add("Good fight! Konas owns me and all \u2022\u1d17\u2022");
        Field1977.add("you just got nae nae'd by konas <player>!");
        Field1977.add("<player> tango down");
        Field1977.add("<player> you just felt the wrath of konas client");
        Field1977.add("I guess konas ca is too fast for you <player>!");
        Field1977.add("<player> konas ca is too fast!");
        Field1977.add("you just got ez'd by konas client <player>");
        Field1978.add("keep popping <player>");
        Field1978.add("ez pop <player>");
        Field1979.add("ez log <player>");
        Field1979.add("I just made <player> log with the power of Konas!");
        Field1980.add("You're such a ping player!");
        Field1980.add("Photoshop! Konas users can never die.");
        Field1980.add("I was AFK!");
        Field1980.add("I bet you wet yourself while killing me! Stupid bedwetter...");
    }

    public void Method1807(EntityPlayer entityPlayer) {
        AutoGG.mc.player.sendChatMessage(Field1977.get(this.Field1981.nextInt(Field1977.size())).replaceAll("<player>", entityPlayer.getName()));
    }

    @Subscriber
    public void Method1451(Class654 class654) {
        block0: {
            if (!(class654.Method1161() instanceof GuiGameOver) || !((Boolean) excuses.getValue()).booleanValue() || !this.Field1982.Method737(2000.0)) break block0;
            this.Method124();
            this.Field1982.Method739();
        }
    }

    public void Method1808(EntityPlayer entityPlayer) {
        AutoGG.mc.player.sendChatMessage(Field1976.get(this.Field1981.nextInt(Field1976.size())).replaceAll("<player>", entityPlayer.getName()));
    }

    public void Method1809(EntityPlayer entityPlayer) {
        AutoGG.mc.player.sendChatMessage(Field1979.get(this.Field1981.nextInt(Field1979.size())).replaceAll("<player>", entityPlayer.getName()));
    }

    @Subscriber
    public void Method531(PacketEvent packetEvent) {
        SPacketPlayerListItem sPacketPlayerListItem;
        if (AutoGG.mc.player == null || AutoGG.mc.world == null) {
            return;
        }
        if (packetEvent.getPacket() instanceof SPacketPlayerListItem && (sPacketPlayerListItem = (SPacketPlayerListItem) packetEvent.getPacket()).getAction() == SPacketPlayerListItem.Action.REMOVE_PLAYER) {
            for (SPacketPlayerListItem.AddPlayerData addPlayerData : sPacketPlayerListItem.getEntries()) {
                EntityPlayer entityPlayer = AutoGG.mc.world.getPlayerEntityByUUID(addPlayerData.getProfile().getId());
                if (entityPlayer == null || !((Boolean) log.getValue()).booleanValue() || AutoGG.mc.player.ticksExisted <= 100 || !NewGui.INSTANCE.Field1133.Method423((Entity)entityPlayer)) continue;
                this.Method1809(entityPlayer);
            }
        }
    }

    @Subscriber
    public void Method1467(Class45 class45) {
        block1: {
            if (((Boolean) gg.getValue()).booleanValue()) {
                this.Method1808(class45.Method270());
                return;
            }
            if (!((Boolean) ez.getValue()).booleanValue()) break block1;
            this.Method1807(class45.Method270());
        }
    }

    public void Method124() {
        AutoGG.mc.player.sendChatMessage(Field1980.get(this.Field1981.nextInt(Field1980.size())));
    }

    public void Method1810(EntityPlayer entityPlayer) {
        AutoGG.mc.player.sendChatMessage(Field1978.get(this.Field1981.nextInt(Field1978.size())).replaceAll("<player>", entityPlayer.getName()));
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        block0: {
            EntityPlayer entityPlayer;
            Entity entity;
            SPacketEntityStatus sPacketEntityStatus;
            if (!(packetEvent.getPacket() instanceof SPacketEntityStatus) || (sPacketEntityStatus = (SPacketEntityStatus) packetEvent.getPacket()).getOpCode() != 35 || !((entity = sPacketEntityStatus.getEntity((World) AutoGG.mc.world)) instanceof EntityPlayer) || !NewGui.INSTANCE.Field1133.Method423((Entity)(entityPlayer = (EntityPlayer)entity)) || !((Boolean) pops.getValue()).booleanValue()) break block0;
            this.Method1810(entityPlayer);
        }
    }
}
