package me.darki.konas.unremaped;

import cookiedragon.eventsystem.EventDispatcher;
import cookiedragon.eventsystem.Subscriber;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.util.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.world.World;

public class Class87 {
    public Map<Entity, Long> Field261 = new ConcurrentHashMap<Entity, Long>();
    public static HashMap<String, Integer> Field262;
    public TimerUtil Field263 = new TimerUtil();
    public static boolean Field264;

    public void Method420(Entity entity, Long l) {
        if (System.currentTimeMillis() - l > TimeUnit.SECONDS.toMillis(30L)) {
            this.Field261.remove(entity);
        }
    }

    static {
        Field264 = !Class87.class.desiredAssertionStatus();
        Field262 = new HashMap();
    }

    public Entity Method421() {
        if (!this.Field261.isEmpty()) {
            return (Entity)Collections.max(this.Field261.entrySet(), Map.Entry.comparingByValue()).getKey();
        }
        return null;
    }

    @Subscriber
    public void Method422(TickEvent tickEvent) {
        if (Minecraft.getMinecraft().world == null || Minecraft.getMinecraft().player == null || tickEvent.Method324() == net.minecraftforge.fml.common.gameevent.TickEvent.Phase.START) {
            return;
        }
        for (EntityPlayer entityPlayer : Minecraft.getMinecraft().world.playerEntities) {
            if (Class546.Method963((Entity)entityPlayer) || !(entityPlayer.getHealth() <= 0.0f) || !Field262.containsKey(entityPlayer.getName())) continue;
            Field262.remove(entityPlayer.getName(), Field262.get(entityPlayer.getName()));
        }
        this.Method429().forEach(this::Method427);
    }

    public boolean Method423(Entity entity) {
        return this.Field261.containsKey(entity);
    }

    @Subscriber
    public void Method424(SendPacketEvent sendPacketEvent) {
        block2: {
            CPacketUseEntity cPacketUseEntity;
            if (Minecraft.getMinecraft().world == null || Minecraft.getMinecraft().player == null) {
                return;
            }
            if (!(sendPacketEvent.getPacket() instanceof CPacketUseEntity) || !(cPacketUseEntity = (CPacketUseEntity) sendPacketEvent.getPacket()).getAction().equals((Object)CPacketUseEntity.Action.ATTACK) || !(cPacketUseEntity.getEntityFromWorld((World)Minecraft.getMinecraft().world) instanceof EntityPlayer)) break block2;
            EntityPlayer entityPlayer = (EntityPlayer)cPacketUseEntity.getEntityFromWorld((World)Minecraft.getMinecraft().world);
            if (!Field264 && entityPlayer == null) {
                throw new AssertionError();
            }
            this.Method430((Entity)entityPlayer);
        }
    }

    public void Method425() {
        this.Field261.forEach(this::Method420);
    }

    public void Method426(Entity entity) {
        this.Field261.remove(entity);
    }

    public void Method427(Entity entity) {
        block0: {
            EntityPlayer entityPlayer;
            if (!(entity instanceof EntityPlayer) || !((entityPlayer = (EntityPlayer)entity).getHealth() <= 0.0f)) break block0;
            Class45 class45 = new Class45(entityPlayer);
            EventDispatcher.Companion.dispatch(class45);
            this.Method426((Entity)entityPlayer);
        }
    }

    public int Method428(Entity entity) {
        int n;
        block11: {
            Entity entity2;
            Map<Entity, Long> map;
            try {
                map = this.Field261;
                entity2 = entity;
            }
            catch (NullPointerException nullPointerException) {
                return 255;
            }
            boolean bl = map.containsKey(entity2);
            if (bl) break block11;
            return 255;
        }
        long l = System.currentTimeMillis();
        Map<Entity, Long> map = this.Field261;
        Entity entity3 = entity;
        Long l2 = map.get(entity3);
        Long l3 = l2;
        long l4 = l3;
        int n2 = n = (int)(l - l4) / 118;
        int n3 = 255;
        return Math.min(n2, n3);
    }

    public Set<Entity> Method429() {
        return this.Field261.keySet();
    }

    public void Method430(Entity entity) {
        this.Field261.put(entity, System.currentTimeMillis());
    }

    @Subscriber
    public void Method431(Class19 class19) {
        block0: {
            if (!this.Field263.GetDifferenceTiming(10000.0)) break block0;
            this.Method425();
            this.Field263.UpdateCurrentTime();
        }
    }

    @Subscriber
    public void Method432(PacketEvent packetEvent) {
        SPacketEntityStatus sPacketEntityStatus;
        if (Minecraft.getMinecraft().world == null || Minecraft.getMinecraft().player == null) {
            return;
        }
        if (packetEvent.getPacket() instanceof SPacketEntityStatus && (sPacketEntityStatus = (SPacketEntityStatus) packetEvent.getPacket()).getOpCode() == 35) {
            Entity entity = sPacketEntityStatus.getEntity((World)Minecraft.getMinecraft().world);
            if (Field262 == null) {
                Field262 = new HashMap();
            }
            if (Field262.get(entity.getName()) == null) {
                Field262.put(entity.getName(), 1);
            } else if (Field262.get(entity.getName()) != null) {
                Field262.put(entity.getName(), Field262.get(entity.getName()) + 1);
            }
            Class43 class43 = new Class43(entity, Field262.get(entity.getName()));
            EventDispatcher.Companion.dispatch(class43);
        }
    }

    public void Method433() {
        this.Field261.clear();
    }
}