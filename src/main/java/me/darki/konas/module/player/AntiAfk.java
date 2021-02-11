package me.darki.konas.module.player;

import cookiedragon.eventsystem.Subscriber;
import java.text.DecimalFormat;
import java.util.Random;

import me.darki.konas.*;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.ChatType;

public class AntiAfk
extends Module {
    public Setting<Integer> seconds = new Setting<>("Seconds", 30, 120, 0, 1);
    public Setting<Boolean> jump = new Setting<>("Jump", false);
    public Setting<Boolean> rotations = new Setting<>("Rotations", true);
    public Setting<Boolean> move = new Setting<>("Move", true);
    public Setting<Boolean> autoReply = new Setting<>("AutoReply", false);
    public Setting<Boolean> friendCoords = new Setting<>("FriendCoords", false).Method1191(this::Method396);
    public Setting<Float> delay = new Setting<>("Delay", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(1.0f), Float.valueOf(1.0f));
    public static Setting<Boolean> safe = new Setting<>("Safe", true);
    public Class566 Field2231 = new Class566();
    public Class566 Field2232 = new Class566();
    public Class566 Field2233 = new Class566();
    public Class566 Field2234 = new Class566();
    public boolean Field2235 = false;
    public boolean Field2236 = true;

    @Subscriber
    public void Method1709(Class2 class2) {
        block2: {
            if (!this.Field2235) break block2;
            if (this.move.getValue().booleanValue() && this.Field2233.Method737(this.delay.getValue().floatValue() * 100.0f)) {
                class2.Method81().moveForward = new Random().nextFloat() * 2.0f - 1.0f;
                class2.Method81().moveStrafe = new Random().nextFloat() * 2.0f - 1.0f;
                this.Field2233.Method739();
            }
            if (this.jump.getValue().booleanValue() && AntiAfk.mc.player.onGround && this.Field2234.Method737(this.delay.getValue().floatValue() * 100.0f)) {
                class2.Method81().jump = new Random().nextBoolean();
                this.Field2234.Method739();
            }
        }
    }

    public AntiAfk() {
        super("AntiAFK", "Prevents you from getting kicked while AFK", Category.PLAYER, "NoAFK");
    }

    @Subscriber
    public void Method503(MoveEvent moveEvent) {
        double d = moveEvent.getX();
        double d2 = moveEvent.getZ();
        if (safe.getValue().booleanValue()) {
            double d3 = 0.05;
            while (d != 0.0 && AntiAfk.mc.world.getCollisionBoxes(AntiAfk.mc.player, AntiAfk.mc.player.getEntityBoundingBox().offset(d, -1.0, 0.0)).isEmpty()) {
                if (d < d3 && d >= -d3) {
                    d = 0.0;
                    continue;
                }
                if (d > 0.0) {
                    d -= d3;
                    continue;
                }
                d += d3;
            }
            while (d2 != 0.0 && AntiAfk.mc.world.getCollisionBoxes(AntiAfk.mc.player, AntiAfk.mc.player.getEntityBoundingBox().offset(0.0, -1.0, d2)).isEmpty()) {
                if (d2 < d3 && d2 >= -d3) {
                    d2 = 0.0;
                    continue;
                }
                if (d2 > 0.0) {
                    d2 -= d3;
                    continue;
                }
                d2 += d3;
            }
            while (d != 0.0 && d2 != 0.0 && AntiAfk.mc.world.getCollisionBoxes(AntiAfk.mc.player, AntiAfk.mc.player.getEntityBoundingBox().offset(d, -1.0, d2)).isEmpty()) {
                d = d < d3 && d >= -d3 ? 0.0 : (d > 0.0 ? (d -= d3) : (d += d3));
                if (d2 < d3 && d2 >= -d3) {
                    d2 = 0.0;
                    continue;
                }
                if (d2 > 0.0) {
                    d2 -= d3;
                    continue;
                }
                d2 += d3;
            }
        }
        moveEvent.setX(d);
        moveEvent.setZ(d2);
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        block1: {
            if (!(packetEvent.getPacket() instanceof SPacketChat) || !this.autoReply.getValue().booleanValue() || !this.Field2235) break block1;
            String[] stringArray = ((SPacketChat) packetEvent.getPacket()).getChatComponent().getUnformattedText().split(" ");
            if (((SPacketChat) packetEvent.getPacket()).getType() == ChatType.SYSTEM && stringArray[1].startsWith("whispers:")) {
                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                double d = Double.parseDouble(decimalFormat.format(AntiAfk.mc.player.posX));
                double d2 = Double.parseDouble(decimalFormat.format(AntiAfk.mc.player.posY));
                double d3 = Double.parseDouble(decimalFormat.format(AntiAfk.mc.player.posZ));
                AntiAfk.mc.player.sendChatMessage("/r I'm currently afk " + (this.friendCoords.getValue() != false && Class492.Method1989(stringArray[0]) ? " at " + d + ", " + d2 + ", " + d3 : ""));
            }
        }
    }

    @Subscriber
    public void Method135(UpdateEvent updateEvent) {
        if (updateEvent.isCanceled() || !Class496.Method1966()) {
            return;
        }
        if (AntiAfk.mc.player == null || AntiAfk.mc.world == null) {
            this.Field2231.Method739();
            return;
        }
        if (this.Field2236) {
            this.Field2231.Method739();
            this.Field2236 = false;
        }
        if (MathUtil.Method1080()) {
            this.Field2231.Method739();
        }
        if (this.Field2231.Method737(this.seconds.getValue() * 1000)) {
            this.Field2235 = true;
            if (this.rotations.getValue().booleanValue() && this.Field2232.Method737(this.delay.getValue().floatValue() * 100.0f)) {
                float f = -5.0f;
                float f2 = 5.0f;
                float f3 = (float)(Math.random() * (double)(f2 - f + 1.0f) + (double)f);
                float f4 = (float)(Math.random() * (double)(f2 - f + 1.0f) + (double)f);
                NewGui.INSTANCE.Field1139.Method1937(AntiAfk.mc.player.rotationYaw + f4, AntiAfk.mc.player.rotationPitch + f3);
                this.Field2232.Method739();
            }
        } else {
            this.Field2235 = false;
        }
    }

    @Override
    public boolean Method396() {
        return this.autoReply.getValue();
    }

    @Subscriber
    public void Method971(Class653 class653) {
        this.Field2231.Method739();
    }
}
