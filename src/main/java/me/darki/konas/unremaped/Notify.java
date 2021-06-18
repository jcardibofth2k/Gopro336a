package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

import me.darki.konas.event.events.OpenGuiEvent;
import me.darki.konas.setting.ListenableSettingDecorator;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.command.Logger;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.util.TimerUtil;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.SoundEvent;

public class Notify
extends Module {
    public TrayIcon Field1408;
    public SystemTray Field1409;
    public int Field1410 = 0;
    public Setting<Boolean> modules = new Setting<>("Modules", true);
    public Setting<Boolean> 2b2tKick = new Setting<>("2b2tKick", false);
    public ListenableSettingDecorator<Boolean> Field1413 = new ListenableSettingDecorator("KillStreak", false, this::Method145);
    public Setting<Boolean> donkeys = new Setting<>("Donkeys", false);
    public Setting<Boolean> llamas = new Setting<>("Llamas", false);
    public Setting<Boolean> slimes = new Setting<>("Slimes", false);
    public Setting<Boolean> ghasts = new Setting<>("Ghasts", false);
    public Setting<Boolean> sound = new Setting<>("Sound", true);
    public ListenableSettingDecorator<Boolean> Field1419 = new ListenableSettingDecorator("SystemTray", false, this::Method1469);
    public ArrayList<Entity> Field1420 = new ArrayList();
    public TimerUtil Field1421 = new TimerUtil();
    public HashMap<Long, Boolean> Field1422 = new Class175(this);

    @Subscriber
    public void Method1466(TickEvent tickEvent) {
        if (Notify.mc.player == null || Notify.mc.world == null) {
            return;
        }
        if (Notify.mc.player.getHealth() <= 0.0f || Notify.mc.player.isDead) {
            this.Field1410 = 0;
        }
    }

    @Subscriber
    public void Method1467(Class45 class45) {
        block0: {
            ++this.Field1410;
            if (this.Field1410 <= 1) break block0;
            Logger.Method1118("You are on a " + this.Field1410 + " Kill Streak!");
        }
    }

    @Subscriber
    public void Method1256(Class77 class77) {
        this.Field1420.clear();
    }

    @Override
    public void onEnable() {
        this.Field1420.clear();
    }

    public void Method1468(String string, String string2, TrayIcon.MessageType messageType) {
        this.Field1408.displayMessage(string, string2, messageType);
    }

    @Subscriber
    public void Method1451(OpenGuiEvent openGuiEvent) {
        if (openGuiEvent.Method1161() instanceof GuiMultiplayer || openGuiEvent.Method1161() instanceof GuiMainMenu) {
            this.Method124();
            this.Field1410 = 0;
        }
    }

    public Notify() {
        super("Notify", "Notifies you of various things", Category.CLIENT, new String[0]);
    }

    public void Method145(Boolean bl) {
        this.Field1410 = 0;
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        block1: {
            if (!(packetEvent.getPacket() instanceof SPacketChat) || !Notify.mc.getCurrentServerData().serverIP.toLowerCase().contains("2b2t.org")) break block1;
            if (((SPacketChat) packetEvent.getPacket()).getChatComponent().getFormattedText().contains("Position in queue:")) {
                this.Method124();
            }
        }
    }

    public void Method1469(Boolean bl) {
        if (bl.booleanValue()) {
            TrayIcon trayIcon;
            SystemTray systemTray;
            if (!SystemTray.isSupported()) {
                Logger.Method1119("Your computer does not support system tray notifications.");
                this.toggle();
                return;
            }
            InputStream inputStream = Notify.class.getResourceAsStream("assets/minecraft/konas/textures/konas.png");
            BufferedImage bufferedImage = null;
            InputStream inputStream2 = inputStream;
            BufferedImage bufferedImage2 = ImageIO.read(inputStream2);
            try {
                bufferedImage = bufferedImage2;
            }
            catch (IOException iOException) {
                // empty catch block
            }
            if (bufferedImage == null) {
                Logger.Method1119("Failed to load assets.");
                this.toggle();
                return;
            }
            this.Field1408 = new TrayIcon(bufferedImage, "Konas");
            this.Field1409 = SystemTray.getSystemTray();
            try {
                systemTray = this.Field1409;
                trayIcon = this.Field1408;
            }
            catch (AWTException aWTException) {
                Logger.Method1119("Failed to initialize tray icon.");
                this.toggle();
                return;
            }
            systemTray.add(trayIcon);
        }
        this.Field1409.remove(this.Field1408);
    }

    @Subscriber
    public void Method974(TickEvent tickEvent) {
        if (!((Boolean)this.ghasts.getValue()).booleanValue() && !((Boolean)this.donkeys.getValue()).booleanValue() && !((Boolean)this.llamas.getValue()).booleanValue() && !((Boolean)this.slimes.getValue()).booleanValue() || Notify.mc.player == null || Notify.mc.world == null || tickEvent.Method324() == net.minecraftforge.fml.common.gameevent.TickEvent.Phase.START) {
            return;
        }
        for (Entity entity : Notify.mc.world.loadedEntityList) {
            if (entity instanceof EntityDonkey && ((Boolean)this.donkeys.getValue()).booleanValue() && !this.Field1420.contains(entity)) {
                Logger.Method1118("Found &bDonkey &fat &bx=" + entity.getPosition().getX() + ",y=" + entity.getPosition().getY() + ",z=" + entity.getPosition().getZ());
                this.Field1420.add(entity);
                if (!((Boolean)this.sound.getValue()).booleanValue()) continue;
                mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.ENTITY_PLAYER_LEVELUP, (float)1.0f));
                continue;
            }
            if (entity instanceof EntityLlama && ((Boolean)this.llamas.getValue()).booleanValue() && !this.Field1420.contains(entity)) {
                Logger.Method1118("Found &bLlama &fat &bx=" + entity.getPosition().getX() + ",y=" + entity.getPosition().getY() + ",z=" + entity.getPosition().getZ());
                this.Field1420.add(entity);
                if (!((Boolean)this.sound.getValue()).booleanValue()) continue;
                mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.ENTITY_PLAYER_LEVELUP, (float)1.0f));
                continue;
            }
            if (entity instanceof EntitySlime && ((Boolean)this.slimes.getValue()).booleanValue() && !this.Field1420.contains(entity)) {
                Logger.Method1118("Found &bSlime &fat &bx=" + entity.getPosition().getX() + ",y=" + entity.getPosition().getY() + ",z=" + entity.getPosition().getZ());
                this.Field1420.add(entity);
                if (!((Boolean)this.sound.getValue()).booleanValue()) continue;
                mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.ENTITY_PLAYER_LEVELUP, (float)1.0f));
                continue;
            }
            if (!(entity instanceof EntityGhast) || !((Boolean)this.ghasts.getValue()).booleanValue() || this.Field1420.contains(entity)) continue;
            Logger.Method1118("Found &bGhast &fat &bx=" + entity.getPosition().getX() + ",y=" + entity.getPosition().getY() + ",z=" + entity.getPosition().getZ());
            this.Field1420.add(entity);
            if (!((Boolean)this.sound.getValue()).booleanValue()) continue;
            mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.ENTITY_PLAYER_LEVELUP, (float)1.0f));
        }
    }

    public void Method124() {
        this.Field1421.UpdateCurrentTime();
        for (Map.Entry<Long, Boolean> entry : this.Field1422.entrySet()) {
            this.Field1422.put(entry.getKey(), false);
        }
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (Notify.mc.player == null || Notify.mc.world == null || tickEvent.Method324() == net.minecraftforge.fml.common.gameevent.TickEvent.Phase.START || !((Boolean)this.2b2tKick.getValue()).booleanValue()) {
            return;
        }
        if (this.Field1421.GetDifferenceTiming(2.07E7) && !this.Field1422.get(20700000L).booleanValue()) {
            Logger.Method1118("You will get kicked in 15 Minutes!");
            this.Field1422.put(20700000L, true);
        }
        if (this.Field1421.GetDifferenceTiming(2.1E7) && !this.Field1422.get(21000000L).booleanValue()) {
            Logger.Method1118("You will get kicked in 10 Minutes!");
            this.Field1422.put(21000000L, true);
        }
        if (this.Field1421.GetDifferenceTiming(2.13E7) && !this.Field1422.get(21300000L).booleanValue()) {
            Logger.Method1118("You will get kicked in 5 Minutes!");
            this.Field1422.put(21300000L, true);
        }
        if (this.Field1421.GetDifferenceTiming(2.154E7) && !this.Field1422.get(21540000L).booleanValue()) {
            Logger.Method1118("You will get kicked in 1 Minute!");
            this.Field1422.put(21540000L, true);
        }
        if (this.Field1421.GetDifferenceTiming(2.157E7) && !this.Field1422.get(21570000L).booleanValue()) {
            Logger.Method1118("You will get kicked in 30 Seconds!");
            this.Field1422.put(21570000L, true);
        }
    }
}