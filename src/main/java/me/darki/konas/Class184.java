package me.darki.konas;

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

import me.darki.konas.command.Logger;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
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

public class Class184
extends Module {
    public TrayIcon Field1408;
    public SystemTray Field1409;
    public int Field1410 = 0;
    public Setting<Boolean> Field1411 = new Setting<>("Modules", true);
    public Setting<Boolean> Field1412 = new Setting<>("2b2tKick", false);
    public Class530<Boolean> Field1413 = new Class530("KillStreak", false, this::Method145);
    public Setting<Boolean> Field1414 = new Setting<>("Donkeys", false);
    public Setting<Boolean> Field1415 = new Setting<>("Llamas", false);
    public Setting<Boolean> Field1416 = new Setting<>("Slimes", false);
    public Setting<Boolean> Field1417 = new Setting<>("Ghasts", false);
    public Setting<Boolean> Field1418 = new Setting<>("Sound", true);
    public Class530<Boolean> Field1419 = new Class530("SystemTray", false, this::Method1469);
    public ArrayList<Entity> Field1420 = new ArrayList();
    public Class566 Field1421 = new Class566();
    public HashMap<Long, Boolean> Field1422 = new Class175(this);

    @Subscriber
    public void Method1466(TickEvent tickEvent) {
        if (Class184.mc.player == null || Class184.mc.world == null) {
            return;
        }
        if (Class184.mc.player.getHealth() <= 0.0f || Class184.mc.player.isDead) {
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
    public void Method1451(Class654 class654) {
        if (class654.Method1161() instanceof GuiMultiplayer || class654.Method1161() instanceof GuiMainMenu) {
            this.Method124();
            this.Field1410 = 0;
        }
    }

    public Class184() {
        super("Notify", "Notifies you of various things", Category.CLIENT, new String[0]);
    }

    public void Method145(Boolean bl) {
        this.Field1410 = 0;
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        block1: {
            if (!(packetEvent.getPacket() instanceof SPacketChat) || !Class184.mc.getCurrentServerData().serverIP.toLowerCase().contains("2b2t.org")) break block1;
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
            InputStream inputStream = Class184.class.getResourceAsStream("assets/minecraft/konas/textures/konas.png");
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
        if (!((Boolean)this.Field1417.getValue()).booleanValue() && !((Boolean)this.Field1414.getValue()).booleanValue() && !((Boolean)this.Field1415.getValue()).booleanValue() && !((Boolean)this.Field1416.getValue()).booleanValue() || Class184.mc.player == null || Class184.mc.world == null || tickEvent.Method324() == net.minecraftforge.fml.common.gameevent.TickEvent.Phase.START) {
            return;
        }
        for (Entity entity : Class184.mc.world.loadedEntityList) {
            if (entity instanceof EntityDonkey && ((Boolean)this.Field1414.getValue()).booleanValue() && !this.Field1420.contains(entity)) {
                Logger.Method1118("Found &bDonkey &fat &bx=" + entity.getPosition().getX() + ",y=" + entity.getPosition().getY() + ",z=" + entity.getPosition().getZ());
                this.Field1420.add(entity);
                if (!((Boolean)this.Field1418.getValue()).booleanValue()) continue;
                mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.ENTITY_PLAYER_LEVELUP, (float)1.0f));
                continue;
            }
            if (entity instanceof EntityLlama && ((Boolean)this.Field1415.getValue()).booleanValue() && !this.Field1420.contains(entity)) {
                Logger.Method1118("Found &bLlama &fat &bx=" + entity.getPosition().getX() + ",y=" + entity.getPosition().getY() + ",z=" + entity.getPosition().getZ());
                this.Field1420.add(entity);
                if (!((Boolean)this.Field1418.getValue()).booleanValue()) continue;
                mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.ENTITY_PLAYER_LEVELUP, (float)1.0f));
                continue;
            }
            if (entity instanceof EntitySlime && ((Boolean)this.Field1416.getValue()).booleanValue() && !this.Field1420.contains(entity)) {
                Logger.Method1118("Found &bSlime &fat &bx=" + entity.getPosition().getX() + ",y=" + entity.getPosition().getY() + ",z=" + entity.getPosition().getZ());
                this.Field1420.add(entity);
                if (!((Boolean)this.Field1418.getValue()).booleanValue()) continue;
                mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.ENTITY_PLAYER_LEVELUP, (float)1.0f));
                continue;
            }
            if (!(entity instanceof EntityGhast) || !((Boolean)this.Field1417.getValue()).booleanValue() || this.Field1420.contains(entity)) continue;
            Logger.Method1118("Found &bGhast &fat &bx=" + entity.getPosition().getX() + ",y=" + entity.getPosition().getY() + ",z=" + entity.getPosition().getZ());
            this.Field1420.add(entity);
            if (!((Boolean)this.Field1418.getValue()).booleanValue()) continue;
            mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.ENTITY_PLAYER_LEVELUP, (float)1.0f));
        }
    }

    public void Method124() {
        this.Field1421.Method739();
        for (Map.Entry<Long, Boolean> entry : this.Field1422.entrySet()) {
            this.Field1422.put(entry.getKey(), false);
        }
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (Class184.mc.player == null || Class184.mc.world == null || tickEvent.Method324() == net.minecraftforge.fml.common.gameevent.TickEvent.Phase.START || !((Boolean)this.Field1412.getValue()).booleanValue()) {
            return;
        }
        if (this.Field1421.Method737(2.07E7) && !this.Field1422.get(20700000L).booleanValue()) {
            Logger.Method1118("You will get kicked in 15 Minutes!");
            this.Field1422.put(20700000L, true);
        }
        if (this.Field1421.Method737(2.1E7) && !this.Field1422.get(21000000L).booleanValue()) {
            Logger.Method1118("You will get kicked in 10 Minutes!");
            this.Field1422.put(21000000L, true);
        }
        if (this.Field1421.Method737(2.13E7) && !this.Field1422.get(21300000L).booleanValue()) {
            Logger.Method1118("You will get kicked in 5 Minutes!");
            this.Field1422.put(21300000L, true);
        }
        if (this.Field1421.Method737(2.154E7) && !this.Field1422.get(21540000L).booleanValue()) {
            Logger.Method1118("You will get kicked in 1 Minute!");
            this.Field1422.put(21540000L, true);
        }
        if (this.Field1421.Method737(2.157E7) && !this.Field1422.get(21570000L).booleanValue()) {
            Logger.Method1118("You will get kicked in 30 Seconds!");
            this.Field1422.put(21570000L, true);
        }
    }
}
