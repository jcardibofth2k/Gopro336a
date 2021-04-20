package me.darki.konas.module.movement;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.Class19;
import me.darki.konas.unremaped.Class269;
import me.darki.konas.unremaped.Class570;
import me.darki.konas.event.events.PushOutOfBlocksEvent;
import me.darki.konas.util.PlayerUtil;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.network.play.client.CPacketPlayer;

public class Phase
extends Module {
    public static Setting<Class269> mode = new Setting<>("Mode", Class269.SAND);
    public static Setting<Boolean> noVoid = new Setting<>("NoVoid", true);
    public static Setting<Boolean> autoClip = new Setting<>("AutoClip", false);
    public static Setting<Double> distance = new Setting<>("Distance", 6.0, 10.0, 0.1, 0.1);

    @Subscriber
    public void Method135(UpdateEvent updateEvent) {
        block3: {
            this.Method1645(mode.getValue().name());
            if (mode.getValue() != Class269.NOCLIP) break block3;
            Phase.mc.player.setVelocity(0.0, 0.0, 0.0);
            if (Phase.mc.gameSettings.keyBindForward.isKeyDown() || Phase.mc.gameSettings.keyBindBack.isKeyDown() || Phase.mc.gameSettings.keyBindLeft.isKeyDown() || Phase.mc.gameSettings.keyBindRight.isKeyDown()) {
                double[] dArray = PlayerUtil.Method1086(0.06f);
                Phase.mc.player.connection.sendPacket(new CPacketPlayer.Position(Phase.mc.player.posX + dArray[0], Phase.mc.player.posY, Phase.mc.player.posZ + dArray[1], Phase.mc.player.onGround));
                Phase.mc.player.connection.sendPacket(new CPacketPlayer.Position(Phase.mc.player.posX, 0.0, Phase.mc.player.posZ, Phase.mc.player.onGround));
            }
            if (Phase.mc.gameSettings.keyBindSneak.isKeyDown()) {
                Phase.mc.player.connection.sendPacket(new CPacketPlayer.Position(Phase.mc.player.posX, Phase.mc.player.posY - distance.getValue() / 1000.0, Phase.mc.player.posZ, Phase.mc.player.onGround));
                Phase.mc.player.connection.sendPacket(new CPacketPlayer.Position(Phase.mc.player.posX, 0.0, Phase.mc.player.posZ, Phase.mc.player.onGround));
            }
            if (Phase.mc.gameSettings.keyBindJump.isKeyDown()) {
                Phase.mc.player.connection.sendPacket(new CPacketPlayer.Position(Phase.mc.player.posX, Phase.mc.player.posY + distance.getValue() / 1000.0, Phase.mc.player.posZ, Phase.mc.player.onGround));
                Phase.mc.player.connection.sendPacket(new CPacketPlayer.Position(Phase.mc.player.posX, 0.0, Phase.mc.player.posZ, Phase.mc.player.onGround));
            }
        }
    }

    @Subscriber
    public void Method130(Class19 class19) {
        if (mode.getValue() == Class269.SAND && Phase.mc.gameSettings.keyBindJump.isKeyDown() && Phase.mc.player.getRidingEntity() != null && Phase.mc.player.getRidingEntity() instanceof EntityBoat) {
            EntityBoat entityBoat = (EntityBoat) Phase.mc.player.getRidingEntity();
            if (entityBoat.onGround) {
                entityBoat.motionY = 0.42f;
            }
        }
    }

    public Phase() {
        super("Phase", "Non Full-Block Phase", Category.EXPLOIT, "DoorClip", "NoClip");
    }

    @Subscriber
    public void Method1824(Class570 class570) {
        block3: {
            block2: {
                block4: {
                    if (Phase.mc.player == null || Phase.mc.world == null) {
                        return;
                    }
                    if (mode.getValue() != Class269.SAND) break block2;
                    if (Phase.mc.player.getRidingEntity() == null || class570.Method579() != Phase.mc.player.getRidingEntity()) break block3;
                    if (!Phase.mc.gameSettings.keyBindSprint.isKeyDown() || !noVoid.getValue().booleanValue()) break block4;
                    class570.setCanceled(true);
                    break block3;
                }
                if (Phase.mc.gameSettings.keyBindJump.isKeyDown() && (double)class570.Method251().getY() >= Phase.mc.player.getRidingEntity().posY) {
                    class570.setCanceled(true);
                }
                if (!((double)class570.Method251().getY() >= Phase.mc.player.getRidingEntity().posY)) break block3;
                class570.setCanceled(true);
                break block3;
            }
            if (class570.Method579() != Phase.mc.player && (Phase.mc.player.getRidingEntity() == null || class570.Method579() != Phase.mc.player.getRidingEntity())) break block3;
            class570.setCanceled(true);
        }
    }

    @Override
    public void onDisable() {
        Phase.mc.player.noClip = false;
    }

    @Override
    public void onEnable() {
        block0: {
            if (!autoClip.getValue().booleanValue() || Phase.mc.player == null || Phase.mc.world == null) break block0;
            double d = Math.cos(Math.toRadians(Phase.mc.player.rotationYaw + 90.0f));
            double d2 = Math.sin(Math.toRadians(Phase.mc.player.rotationYaw + 90.0f));
            Phase.mc.player.setPosition(Phase.mc.player.posX + (1.0 * (distance.getValue() / 1000.0) * d + 0.0 * (distance.getValue() / 1000.0) * d2), Phase.mc.player.posY, Phase.mc.player.posZ + (1.0 * (distance.getValue() / 1000.0) * d2 - 0.0 * (distance.getValue() / 1000.0) * d));
        }
    }

    @Subscriber
    public void Method461(PushOutOfBlocksEvent pushOutOfBlocksEvent) {
        pushOutOfBlocksEvent.setCanceled(true);
    }
}