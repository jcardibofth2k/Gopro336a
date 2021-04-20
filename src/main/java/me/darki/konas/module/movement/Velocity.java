package me.darki.konas.module.movement;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.event.events.MoveEvent;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.mixin.mixins.ISPacketEntityVelocity;
import me.darki.konas.mixin.mixins.ISPacketExplosion;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.Class34;
import me.darki.konas.unremaped.Class36;
import me.darki.konas.unremaped.Class4;
import me.darki.konas.event.events.PushOutOfBlocksEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

public class Velocity
extends Module {
    public static Setting<Float> horizontal = new Setting<>("Horizontal", Float.valueOf(0.0f), Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(1.0f));
    public static Setting<Float> vertical = new Setting<>("Vertical", Float.valueOf(0.0f), Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(1.0f));
    public static Setting<Boolean> noPush = new Setting<>("NoPush", true);
    public static Setting<Boolean> noHook = new Setting<>("NoHook", true);
    public static Setting<Boolean> noPiston = new Setting<>("NoPiston", false);

    @Subscriber
    public void Method503(MoveEvent moveEvent) {
        block0: {
            if (moveEvent.getMoverType() != MoverType.PISTON && moveEvent.getMoverType() != MoverType.SHULKER_BOX || !noPiston.getValue().booleanValue()) break block0;
            moveEvent.setCanceled(true);
        }
    }

    @Subscriber
    public void Method461(PushOutOfBlocksEvent pushOutOfBlocksEvent) {
        block0: {
            if (!noPush.getValue().booleanValue()) break block0;
            pushOutOfBlocksEvent.setCanceled(true);
        }
    }

    @Subscriber
    public void Method2072(Class34 class34) {
        block1: {
            block2: {
                if (Velocity.mc.world == null || Velocity.mc.player == null) {
                    return;
                }
                if (!noPush.getValue().booleanValue() || class34.Method286() != Velocity.mc.player) break block1;
                if (class34.Method285() != Class36.HORIZONTAL) break block2;
                class34.Method89(class34.Method88() * (double) horizontal.getValue().floatValue());
                break block1;
            }
            if (class34.Method285() != Class36.VERTICAL) break block1;
            class34.Method89(class34.Method88() * (double) vertical.getValue().floatValue());
        }
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        block5: {
            Entity entity;
            SPacketEntityStatus sPacketEntityStatus;
            block6: {
                block4: {
                    if (Velocity.mc.world == null || Velocity.mc.player == null) {
                        return;
                    }
                    if (!(packetEvent.getPacket() instanceof SPacketEntityVelocity)) break block4;
                    SPacketEntityVelocity sPacketEntityVelocity = (SPacketEntityVelocity) packetEvent.getPacket();
                    if (sPacketEntityVelocity.getEntityID() != Velocity.mc.player.getEntityId()) break block5;
                    if (horizontal.getValue().floatValue() == 0.0f && vertical.getValue().floatValue() == 0.0f) {
                        packetEvent.setCanceled(true);
                    } else {
                        ((ISPacketEntityVelocity)sPacketEntityVelocity).setMotionX((int)((float)sPacketEntityVelocity.getMotionX() * horizontal.getValue().floatValue()));
                        ((ISPacketEntityVelocity)sPacketEntityVelocity).setMotionY((int)((float)sPacketEntityVelocity.getMotionY() * vertical.getValue().floatValue()));
                        ((ISPacketEntityVelocity)sPacketEntityVelocity).setMotionZ((int)((float)sPacketEntityVelocity.getMotionZ() * horizontal.getValue().floatValue()));
                    }
                    break block5;
                }
                if (!(packetEvent.getPacket() instanceof SPacketExplosion)) break block6;
                SPacketExplosion sPacketExplosion = (SPacketExplosion) packetEvent.getPacket();
                ((ISPacketExplosion)sPacketExplosion).setMotionX((int)(sPacketExplosion.getMotionX() * horizontal.getValue().floatValue()));
                ((ISPacketExplosion)sPacketExplosion).setMotionY((int)(sPacketExplosion.getMotionY() * vertical.getValue().floatValue()));
                ((ISPacketExplosion)sPacketExplosion).setMotionZ((int)(sPacketExplosion.getMotionZ() * horizontal.getValue().floatValue()));
                break block5;
            }
            if (!(packetEvent.getPacket() instanceof SPacketEntityStatus) || !noHook.getValue().booleanValue() || (sPacketEntityStatus = (SPacketEntityStatus) packetEvent.getPacket()).getOpCode() != 31 || (entity = sPacketEntityStatus.getEntity(Velocity.mc.world)) == null || !(entity instanceof EntityFishHook)) break block5;
            EntityFishHook entityFishHook = (EntityFishHook)entity;
            if (entityFishHook.caughtEntity == Velocity.mc.player) {
                packetEvent.Cancel();
            }
        }
    }

    @Subscriber
    public void Method2073(Class4 class4) {
        block0: {
            if (!noPush.getValue().booleanValue()) break block0;
            class4.setCanceled(true);
        }
    }

    public Velocity() {
        super("Velocity", Category.MOVEMENT, "AntiKnockback");
    }
}