package me.darki.konas.module.movement;

import cookiedragon.eventsystem.Subscriber;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import me.darki.konas.event.events.MoveEvent;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.event.events.PushOutOfBlocksEvent;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.mixin.mixins.ISPacketPlayerPosLook;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.setting.Setting;
import me.darki.konas.settingEnums.*;
import me.darki.konas.unremaped.*;
import me.darki.konas.util.PlayerUtil;
import me.darki.konas.util.TimerUtil;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class PacketFly
extends Module {
    public static Setting<PacketFlyType> type = new Setting<>("Type", PacketFlyType.FACTOR);
    public static Setting<PacketFlyMode> packetMode = new Setting<>("PacketMode", PacketFlyMode.LIMITJITTER);
    public static Setting<PacketFlyBounds> bounds = new Setting<>("Bounds", PacketFlyBounds.STRICT);
    public static Setting<Float> factor = new Setting<>("Factor", Float.valueOf(1.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).visibleIf(PacketFly::Method388);
    public static Setting<Float> speed = new Setting<>("Speed", Float.valueOf(1.0f), Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(0.05f));
    public static Setting<PacketFlyPhase> phase = new Setting<>("Phase", PacketFlyPhase.FAST);
    public static Setting<PacketFlyAntiKick> antiKick = new Setting<>("AntiKick", PacketFlyAntiKick.NORMAL);
    public static Setting<PacketFlylimit> limit = new Setting<>("Limit", PacketFlylimit.NONE);
    public static Setting<Boolean> constrict = new Setting<>("Constrict", false);
    public static Setting<Boolean> boost = new Setting<>("Boost", false);
    public int Field2330;
    public CPacketConfirmTeleport Field2331;
    public CPacketPlayer.Position Field2332;
    public ArrayList<CPacketPlayer> Field2333 = new ArrayList();
    public Map<Integer, Class471> Field2334 = new ConcurrentHashMap<Integer, Class471>();
    public int Field2335 = 0;
    public int Field2336 = 0;
    public int Field2337 = 0;
    public boolean Field2338 = false;
    public int Field2339 = 0;
    public int Field2340 = 0;
    public boolean Field2341 = false;
    public double Field2342 = 0.0;
    public double Field2343 = 0.0;
    public double Field2344 = 0.0;
    public int Field2345 = 0;
    public TimerUtil Field2346 = new TimerUtil();
    public static Random Field2347 = new Random();

    public void Method2053(double d, double d2, double d3, PacketFlyMode packetFlyMode, boolean bl) {
        if (this.Field2331 != null) {
            PacketFly.mc.player.connection.sendPacket(this.Field2331);
            this.Field2331 = null;
        }
        Vec3d vec3d = new Vec3d(PacketFly.mc.player.posX + d, PacketFly.mc.player.posY + d2, PacketFly.mc.player.posZ + d3);
        Vec3d vec3d2 = this.Method2057(d, d2, d3, packetFlyMode);
        CPacketPlayer.Position position = new CPacketPlayer.Position(vec3d.x, vec3d.y, vec3d.z, PacketFly.mc.player.onGround);
        this.Field2333.add(position);
        PacketFly.mc.player.connection.sendPacket(position);
        if (limit.getValue() != PacketFlylimit.NONE && this.Field2339 == 0) {
            return;
        }
        CPacketPlayer.Position position2 = new CPacketPlayer.Position(vec3d2.x, vec3d2.y, vec3d2.z, PacketFly.mc.player.onGround);
        this.Field2333.add(position2);
        PacketFly.mc.player.connection.sendPacket(position2);
        if (constrict.getValue().booleanValue()) {
            for (int i = 0; i < 7; ++i) {
                position = new CPacketPlayer.Position(vec3d.x, vec3d.y, vec3d.z, PacketFly.mc.player.onGround);
                this.Field2333.add(position);
                PacketFly.mc.player.connection.sendPacket(position);
            }
        }
        if (bl) {
            ++this.Field2330;
            this.Field2334.put(this.Field2330, new Class471(vec3d.x, vec3d.y, vec3d.z, System.currentTimeMillis()));
            this.Field2331 = new CPacketConfirmTeleport(this.Field2330);
        }
    }

    @Subscriber
    public void Method130(Class19 class19) {
        if (PacketFly.mc.player == null || PacketFly.mc.world == null) {
            this.toggle();
            return;
        }
        if (PacketFly.mc.player.ticksExisted % 20 == 0) {
            this.Method124();
        }
        this.Method1645(type.getValue().name());
        PacketFly.mc.player.setVelocity(0.0, 0.0, 0.0);
        if (this.Field2330 <= 0 && type.getValue() != PacketFlyType.SETBACK) {
            this.Field2332 = new CPacketPlayer.Position(PacketFly.Method2055(), 1.0, PacketFly.Method2055(), PacketFly.mc.player.onGround);
            this.Field2333.add(this.Field2332);
            PacketFly.mc.player.connection.sendPacket(this.Field2332);
            return;
        }
        boolean bl = this.Method519();
        this.Field2342 = 0.0;
        this.Field2343 = 0.0;
        this.Field2344 = 0.0;
        if (PacketFly.mc.gameSettings.keyBindJump.isKeyDown() && (this.Field2337 < 1 || bl)) {
            this.Field2343 = PacketFly.mc.player.ticksExisted % (type.getValue() == PacketFlyType.SETBACK || type.getValue() == PacketFlyType.SLOW || limit.getValue() == PacketFlylimit.STRICT ? 10 : 20) == 0 ? (antiKick.getValue() != PacketFlyAntiKick.NONE ? -0.032 : 0.062) : 0.062;
            this.Field2335 = 0;
            this.Field2336 = 5;
        } else if (PacketFly.mc.gameSettings.keyBindSneak.isKeyDown() && (this.Field2337 < 1 || bl)) {
            this.Field2343 = -0.062;
            this.Field2335 = 0;
            this.Field2336 = 5;
        }
        if (bl || !PacketFly.mc.gameSettings.keyBindSneak.isKeyDown() || !PacketFly.mc.gameSettings.keyBindJump.isKeyDown()) {
            if (PlayerUtil.Method1080()) {
                double[] dArray = PlayerUtil.Method1086((bl && phase.getValue() != PacketFlyPhase.NONE ? (phase.getValue() == PacketFlyPhase.FAST ? (this.Field2343 != 0.0 ? 0.0465 : 0.062) : 0.031) : 0.26) * (double) speed.getValue().floatValue());
                if (dArray[0] != 0.0 || dArray[1] != 0.0) {
                    if (this.Field2336 < 1 || bl) {
                        this.Field2342 = dArray[0];
                        this.Field2344 = dArray[1];
                        this.Field2337 = 5;
                    }
                }
            }
            if (antiKick.getValue() != PacketFlyAntiKick.NONE && (limit.getValue() == PacketFlylimit.NONE || this.Field2339 != 0)) {
                if (this.Field2335 < (packetMode.getValue() == PacketFlyMode.BYPASS && bounds.getValue() == PacketFlyBounds.NONE ? 1 : 3)) {
                    ++this.Field2335;
                } else {
                    this.Field2335 = 0;
                    if (antiKick.getValue() != PacketFlyAntiKick.LIMITED || !bl) {
                        double d = this.Field2343 = antiKick.getValue() == PacketFlyAntiKick.STRICT ? -0.08 : -0.04;
                    }
                }
            }
        }
        if (bl && (phase.getValue() != PacketFlyPhase.NONE && (double) PacketFly.mc.player.moveForward != 0.0 || (double) PacketFly.mc.player.moveStrafing != 0.0 && this.Field2343 != 0.0)) {
            this.Field2343 /= 2.5;
        }
        if (limit.getValue() != PacketFlylimit.NONE && this.Field2339 == 0) {
            this.Field2342 = 0.0;
            this.Field2343 = 0.0;
            this.Field2344 = 0.0;
        }
        switch (Class271.Field1984[type.getValue().ordinal()]) {
            case 1: {
                PacketFly.mc.player.setVelocity(this.Field2342, this.Field2343, this.Field2344);
                this.Method2053(this.Field2342, this.Field2343, this.Field2344, packetMode.getValue(), true);
                break;
            }
            case 2: {
                this.Method2053(this.Field2342, this.Field2343, this.Field2344, packetMode.getValue(), true);
                break;
            }
            case 3: {
                PacketFly.mc.player.setVelocity(this.Field2342, this.Field2343, this.Field2344);
                this.Method2053(this.Field2342, this.Field2343, this.Field2344, packetMode.getValue(), false);
                break;
            }
            case 4: 
            case 5: {
                float f = factor.getValue().floatValue();
                int n = (int)Math.floor(f);
                float f2 = f - (float)n;
                if (Math.random() <= (double)f2) {
                    ++n;
                }
                for (int i = 1; i <= n; ++i) {
                    PacketFly.mc.player.setVelocity(this.Field2342 * (double)i, this.Field2343 * (double)i, this.Field2344 * (double)i);
                    this.Method2053(this.Field2342 * (double)i, this.Field2343 * (double)i, this.Field2344 * (double)i, packetMode.getValue(), true);
                }
                this.Field2342 = PacketFly.mc.player.motionX;
                this.Field2343 = PacketFly.mc.player.motionY;
                this.Field2344 = PacketFly.mc.player.motionZ;
            }
        }
        --this.Field2336;
        --this.Field2337;
        ++this.Field2339;
        ++this.Field2340;
        if (this.Field2339 > (limit.getValue() == PacketFlylimit.STRICT ? (this.Field2338 ? 1 : 2) : 3)) {
            this.Field2339 = 0;
            boolean bl2 = this.Field2338 = !this.Field2338;
        }
        if (this.Field2340 > 7) {
            this.Field2340 = 0;
        }
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        if (packetEvent.getPacket() instanceof SPacketPlayerPosLook) {
            if (!(PacketFly.mc.currentScreen instanceof GuiDownloadTerrain)) {
                SPacketPlayerPosLook sPacketPlayerPosLook = (SPacketPlayerPosLook) packetEvent.getPacket();
                if (PacketFly.mc.player.isEntityAlive()) {
                    if (this.Field2330 <= 0) {
                        this.Field2330 = ((SPacketPlayerPosLook) packetEvent.getPacket()).getTeleportId();
                    } else if (PacketFly.mc.world.isBlockLoaded(new BlockPos(PacketFly.mc.player.posX, PacketFly.mc.player.posY, PacketFly.mc.player.posZ), false) && type.getValue() != PacketFlyType.SETBACK) {
                        if (type.getValue() == PacketFlyType.DESYNC) {
                            this.Field2334.remove(sPacketPlayerPosLook.getTeleportId());
                            packetEvent.setCanceled(true);
                            if (type.getValue() == PacketFlyType.SLOW) {
                                PacketFly.mc.player.setPosition(sPacketPlayerPosLook.getX(), sPacketPlayerPosLook.getY(), sPacketPlayerPosLook.getZ());
                            }
                            return;
                        }
                        if (this.Field2334.containsKey(sPacketPlayerPosLook.getTeleportId())) {
                            Class471 class471 = this.Field2334.get(sPacketPlayerPosLook.getTeleportId());
                            if (class471.x == sPacketPlayerPosLook.getX() && class471.y == sPacketPlayerPosLook.getY() && class471.z == sPacketPlayerPosLook.getZ()) {
                                this.Field2334.remove(sPacketPlayerPosLook.getTeleportId());
                                packetEvent.setCanceled(true);
                                if (type.getValue() == PacketFlyType.SLOW) {
                                    PacketFly.mc.player.setPosition(sPacketPlayerPosLook.getX(), sPacketPlayerPosLook.getY(), sPacketPlayerPosLook.getZ());
                                }
                                return;
                            }
                        }
                    }
                }
                ((ISPacketPlayerPosLook)sPacketPlayerPosLook).setYaw(PacketFly.mc.player.rotationYaw);
                ((ISPacketPlayerPosLook)sPacketPlayerPosLook).setPitch(PacketFly.mc.player.rotationPitch);
                sPacketPlayerPosLook.getFlags().remove(SPacketPlayerPosLook.EnumFlags.X_ROT);
                sPacketPlayerPosLook.getFlags().remove(SPacketPlayerPosLook.EnumFlags.Y_ROT);
                this.Field2330 = sPacketPlayerPosLook.getTeleportId();
            } else {
                this.Field2330 = 0;
            }
        }
    }

    public static double Method2054() {
        int n = Field2347.nextInt(22);
        n += 70;
        if (Field2347.nextBoolean()) {
            return n;
        }
        return -n;
    }

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        block2: {
            if (sendPacketEvent.getPacket() instanceof CPacketPlayer && !(sendPacketEvent.getPacket() instanceof CPacketPlayer.Position)) {
                sendPacketEvent.Cancel();
            }
            if (!(sendPacketEvent.getPacket() instanceof CPacketPlayer)) break block2;
            CPacketPlayer cPacketPlayer = (CPacketPlayer) sendPacketEvent.getPacket();
            if (this.Field2333.contains(cPacketPlayer)) {
                this.Field2333.remove(cPacketPlayer);
                return;
            }
            sendPacketEvent.Cancel();
        }
    }

    @Override
    public void onEnable() {
        if (PacketFly.mc.player == null || PacketFly.mc.world == null) {
            this.toggle();
            return;
        }
        this.Field2333.clear();
        this.Field2334.clear();
        this.Field2330 = 0;
        this.Field2336 = 0;
        this.Field2337 = 0;
        this.Field2335 = 0;
        this.Field2339 = 0;
        this.Field2340 = 0;
        this.Field2342 = 0.0;
        this.Field2343 = 0.0;
        this.Field2344 = 0.0;
        this.Field2341 = false;
        this.Field2332 = null;
        this.Field2332 = new CPacketPlayer.Position(PacketFly.Method2055(), 1.0, PacketFly.Method2055(), PacketFly.mc.player.onGround);
        this.Field2333.add(this.Field2332);
        PacketFly.mc.player.connection.sendPacket(this.Field2332);
    }

    @Subscriber
    public void Method503(MoveEvent moveEvent) {
        if (type.getValue() != PacketFlyType.SETBACK && this.Field2330 <= 0) {
            return;
        }
        if (type.getValue() != PacketFlyType.SLOW) {
            moveEvent.setX(this.Field2342);
            moveEvent.setY(this.Field2343);
            moveEvent.setZ(this.Field2344);
        }
        if (phase.getValue() != PacketFlyPhase.NONE && this.Method519()) {
            PacketFly.mc.player.noClip = true;
        }
    }

    @Subscriber
    public void Method461(PushOutOfBlocksEvent pushOutOfBlocksEvent) {
        pushOutOfBlocksEvent.setCanceled(true);
    }

    public static double Method2055() {
        int n = Field2347.nextInt(bounds.getValue() != PacketFlyBounds.NONE ? 80 : (packetMode.getValue() == PacketFlyMode.OBSCURE ? (PacketFly.mc.player.ticksExisted % 2 == 0 ? 480 : 100) : 29000000)) + (bounds.getValue() != PacketFlyBounds.NONE ? 5 : 500);
        if (Field2347.nextBoolean()) {
            return n;
        }
        return -n;
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (PacketFly.mc.currentScreen instanceof GuiDisconnected || PacketFly.mc.currentScreen instanceof GuiMainMenu || PacketFly.mc.currentScreen instanceof GuiMultiplayer || PacketFly.mc.currentScreen instanceof GuiDownloadTerrain) {
            this.toggle();
        }
        if (boost.getValue().booleanValue()) {
            KonasGlobals.INSTANCE.Field1134.Method746(this, 9, 1.088f);
        } else {
            KonasGlobals.INSTANCE.Field1134.Method749(this);
        }
    }

    public void Method2056(Integer n, Class471 class471) {
        if (System.currentTimeMillis() - class471.Method2177() > TimeUnit.SECONDS.toMillis(30L)) {
            this.Field2334.remove(n);
        }
    }

    public PacketFly() {
        super("PacketFly", Category.EXPLOIT);
    }

    public Vec3d Method2057(double d, double d2, double d3, PacketFlyMode packetFlyMode) {
        switch (Class271.Field1985[packetFlyMode.ordinal()]) {
            case 1: {
                return new Vec3d(PacketFly.mc.player.posX + d, bounds.getValue() != PacketFlyBounds.NONE ? (double)(bounds.getValue() == PacketFlyBounds.STRICT ? 255 : 256) : PacketFly.mc.player.posY + 420.0, PacketFly.mc.player.posZ + d3);
            }
            case 2: {
                return new Vec3d(bounds.getValue() != PacketFlyBounds.NONE ? PacketFly.mc.player.posX + PacketFly.Method2055() : PacketFly.Method2055(), bounds.getValue() == PacketFlyBounds.STRICT ? Math.max(PacketFly.mc.player.posY, 2.0) : PacketFly.mc.player.posY, bounds.getValue() != PacketFlyBounds.NONE ? PacketFly.mc.player.posZ + PacketFly.Method2055() : PacketFly.Method2055());
            }
            case 3: {
                return new Vec3d(PacketFly.mc.player.posX + (bounds.getValue() == PacketFlyBounds.STRICT ? d : PacketFly.Method2058()), PacketFly.mc.player.posY + PacketFly.Method2054(), PacketFly.mc.player.posZ + (bounds.getValue() == PacketFlyBounds.STRICT ? d3 : PacketFly.Method2058()));
            }
            case 4: {
                if (bounds.getValue() != PacketFlyBounds.NONE) {
                    double d4 = d2 * 510.0;
                    return new Vec3d(PacketFly.mc.player.posX + d, PacketFly.mc.player.posY + (d4 > (double)(PacketFly.mc.player.dimension == -1 ? 127 : 255) ? -d4 : (d4 < 1.0 ? -d4 : d4)), PacketFly.mc.player.posZ + d3);
                }
                return new Vec3d(PacketFly.mc.player.posX + (d == 0.0 ? (double)(Field2347.nextBoolean() ? -10 : 10) : d * 38.0), PacketFly.mc.player.posY + d2, PacketFly.mc.player.posX + (d3 == 0.0 ? (double)(Field2347.nextBoolean() ? -10 : 10) : d3 * 38.0));
            }
            case 5: {
                return new Vec3d(PacketFly.mc.player.posX + PacketFly.Method2055(), Math.max(1.5, Math.min(PacketFly.mc.player.posY + d2, 253.5)), PacketFly.mc.player.posZ + PacketFly.Method2055());
            }
        }
        return new Vec3d(PacketFly.mc.player.posX + d, bounds.getValue() != PacketFlyBounds.NONE ? (double)(bounds.getValue() == PacketFlyBounds.STRICT ? 1 : 0) : PacketFly.mc.player.posY - 1337.0, PacketFly.mc.player.posZ + d3);
    }

    public boolean Method519() {
        if (!PacketFly.mc.world.getCollisionBoxes(PacketFly.mc.player, PacketFly.mc.player.getEntityBoundingBox().expand(0.0, 0.0, 0.0)).isEmpty()) {
            return true;
        }
        return !PacketFly.mc.world.getCollisionBoxes(PacketFly.mc.player, PacketFly.mc.player.getEntityBoundingBox().offset(0.0, 2.0, 0.0).contract(0.0, 1.99, 0.0)).isEmpty();
    }

    public static double Method2058() {
        int n = Field2347.nextInt(10);
        if (Field2347.nextBoolean()) {
            return n;
        }
        return -n;
    }

    @Override
    public void onDisable() {
        if (PacketFly.mc.player != null) {
            PacketFly.mc.player.setVelocity(0.0, 0.0, 0.0);
        }
        KonasGlobals.INSTANCE.Field1134.Method749(this);
    }

    public void Method124() {
        this.Field2334.forEach(this::Method2056);
    }

    public static boolean Method388() {
        return type.getValue() == PacketFlyType.FACTOR || type.getValue() == PacketFlyType.DESYNC;
    }
}