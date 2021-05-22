package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import java.util.LinkedList;
import java.util.Queue;

import me.darki.konas.event.events.TickEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.setting.Setting;
import net.minecraft.block.BlockWeb;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Criticals
extends Module {
    public Setting<Class323> mode = new Setting<>("Mode", Class323.PACKET);
    public Setting<Boolean> webCrits = new Setting<>("WebCrits", false).visibleIf(this::Method396);
    public Setting<Boolean> vehicles = new Setting<>("Vehicles", false);
    public Setting<Integer> hits = new Setting<>("Hits", 3, 15, 0, 1).visibleIf(this.vehicles::getValue);
    public Setting<Integer> delay = new Setting<>("Delay", 1, 10, 1, 1);
    public Setting<Boolean> onlyWhenKA = new Setting<>("OnlyWhenKA", true);
    public Queue<CPacketUseEntity> Field718 = new LinkedList<CPacketUseEntity>();
    public CPacketUseEntity Field719 = null;
    public CPacketAnimation Field720 = null;
    public int Field721 = 0;
    public int Field722 = 0;

    @Override
    public String Method756() {
        return ((Class323)((Object)this.mode.getValue())).toString().charAt(0) + ((Class323)((Object)this.mode.getValue())).toString().substring(1).toLowerCase();
    }

    public Criticals() {
        super("Criticals", 0, Category.COMBAT, "Crits", "AlwaysCrit");
    }

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        if (Criticals.mc.player == null || Criticals.mc.world == null) {
            return;
        }
        if (!ModuleManager.Method1612("KillAura").isEnabled() && ((Boolean)this.onlyWhenKA.getValue()).booleanValue()) {
            return;
        }
        if ((this.mode.getValue() == Class323.JUMP || this.mode.getValue() == Class323.SMALLJUMP) && this.Field719 != null && this.Field720 != null) {
            return;
        }
        if (sendPacketEvent.getPacket() instanceof CPacketUseEntity && ((CPacketUseEntity) sendPacketEvent.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && Criticals.mc.player.onGround && Criticals.mc.player.collidedVertically && !Criticals.mc.player.isInLava() && !Criticals.mc.player.isInWater()) {
            Entity entity = ((CPacketUseEntity) sendPacketEvent.getPacket()).getEntityFromWorld((World) Criticals.mc.world);
            if (entity instanceof EntityEnderCrystal || entity == null) {
                return;
            }
            if (entity instanceof EntityMinecart || entity instanceof EntityBoat) {
                if (((Boolean)this.vehicles.getValue()).booleanValue()) {
                    if (this.Field721 > 0) {
                        --this.Field721;
                        return;
                    }
                    this.Field721 = (Integer)this.hits.getValue();
                    for (int i = 0; i < (Integer)this.hits.getValue(); ++i) {
                        this.Field718.add(new CPacketUseEntity(entity));
                    }
                    return;
                }
            }
            switch (Class314.Field761[((Class323)((Object)this.mode.getValue())).ordinal()]) {
                case 1: {
                    Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.0625101, Criticals.mc.player.posZ, false));
                    Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY, Criticals.mc.player.posZ, false));
                    Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.0125, Criticals.mc.player.posZ, false));
                    Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY, Criticals.mc.player.posZ, false));
                    break;
                }
                case 2: {
                    if (((Boolean)this.webCrits.getValue()).booleanValue() && Criticals.mc.world.getBlockState(new BlockPos((Entity) Criticals.mc.player)).getBlock() instanceof BlockWeb) {
                        Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.0625101, Criticals.mc.player.posZ, false));
                        Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY, Criticals.mc.player.posZ, false));
                        Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.0125, Criticals.mc.player.posZ, false));
                        Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY, Criticals.mc.player.posZ, false));
                        break;
                    }
                    Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.11, Criticals.mc.player.posZ, false));
                    Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 0.1100013579, Criticals.mc.player.posZ, false));
                    Criticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Criticals.mc.player.posX, Criticals.mc.player.posY + 1.3579E-6, Criticals.mc.player.posZ, false));
                    break;
                }
                case 3: {
                    if (this.Field719 != null) break;
                    Criticals.mc.player.jump();
                    this.Field719 = (CPacketUseEntity) sendPacketEvent.getPacket();
                    sendPacketEvent.setCanceled(true);
                    break;
                }
                case 4: {
                    if (this.Field719 != null) break;
                    Criticals.mc.player.jump();
                    Criticals.mc.player.motionY = 0.25;
                    this.Field719 = (CPacketUseEntity) sendPacketEvent.getPacket();
                    sendPacketEvent.setCanceled(true);
                    break;
                }
            }
        } else if (sendPacketEvent.getPacket() instanceof CPacketAnimation && Criticals.mc.player.onGround) {
            if (Criticals.mc.player.collidedVertically && !Criticals.mc.player.isInLava() && !Criticals.mc.player.isInWater() && this.Field719 != null && this.Field720 == null) {
                this.Field720 = (CPacketAnimation) sendPacketEvent.getPacket();
            }
        }
    }

    //@Override
    public boolean Method396() {
        return this.mode.getValue() == Class323.BYPASS;
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (Criticals.mc.player == null || Criticals.mc.world == null) {
            return;
        }
        if (tickEvent.Method324() == net.minecraftforge.fml.common.gameevent.TickEvent.Phase.START) {
            if (!this.Field718.isEmpty() && this.Field722 % (Integer)this.delay.getValue() == 0) {
                Criticals.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                Criticals.mc.player.connection.sendPacket((Packet)this.Field718.poll());
            }
            ++this.Field722;
        }
        if (Criticals.mc.player.motionY < 0.0 && this.Field719 != null && this.Field720 != null && (this.mode.getValue() == Class323.JUMP || this.mode.getValue() == Class323.SMALLJUMP)) {
            Criticals.mc.player.connection.sendPacket((Packet)this.Field719);
            Criticals.mc.player.connection.sendPacket((Packet)this.Field720);
            this.Field719 = null;
            this.Field720 = null;
        }
    }

    @Override
    public void onEnable() {
        this.Field719 = null;
        this.Field720 = null;
    }
}