package me.darki.konas.module.render;

import com.google.common.collect.Sets;
import cookiedragon.eventsystem.Subscriber;
import java.util.Set;
import me.darki.konas.Category;
import me.darki.konas.Class106;
import me.darki.konas.Class113;
import me.darki.konas.Class13;
import me.darki.konas.Class131;
import me.darki.konas.Class137;
import me.darki.konas.Class142;
import me.darki.konas.Class148;
import me.darki.konas.PacketEvent;
import me.darki.konas.Class417;
import me.darki.konas.Class530;
import me.darki.konas.Class569;
import me.darki.konas.TickEvent;
import me.darki.konas.Class642;
import me.darki.konas.Class79;
import me.darki.konas.Class93;
import me.darki.konas.Class94;
import me.darki.konas.mixin.mixins.IWorld;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.ParticleFirework;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketMaps;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.util.SoundEvent;

public class NoRender
extends Module {
    public static Set<SoundEvent> Field1100 = Sets.newHashSet(SoundEvents.ENTITY_BAT_AMBIENT, SoundEvents.ENTITY_BAT_DEATH, SoundEvents.ENTITY_BAT_HURT, SoundEvents.ENTITY_BAT_LOOP, SoundEvents.ENTITY_BAT_TAKEOFF);
    public Setting<Boolean> Field1101 = new Setting<>("NoHurtCam", true);
    public Setting<Boolean> Field1102 = new Setting<>("NoWeather", true);
    public Setting<Boolean> Field1103 = new Setting<>("NoLightning", true);
    public Setting<Boolean> Field1104 = new Setting<>("NoFire", true);
    public Setting<Boolean> Field1105 = new Setting<>("NoBossBar", false);
    public Setting<Boolean> Field1106 = new Class530("NoBats", true, this::Method145);
    public Setting<Class417> Field1107 = new Setting<>("Armor", Class417.NONE);
    public Setting<Boolean> Field1108 = new Setting<>("Head", true).Method1191(this::Method394);
    public Setting<Boolean> Field1109 = new Setting<>("Chestplate", false).Method1191(this::Method539);
    public Setting<Boolean> Field1110 = new Setting<>("Leggings", false).Method1191(this::Method393);
    public Setting<Boolean> Field1111 = new Setting<>("Boots", false).Method1191(this::Method519);
    public Setting<Boolean> Field1112 = new Setting<>("OwnShadow", true);
    public Setting<Boolean> Field1113 = new Setting<>("Mob", false);
    public Setting<Boolean> Field1114 = new Setting<>("Object", false);
    public Setting<Boolean> Field1115 = new Setting<>("XP", true);
    public Setting<Boolean> Field1116 = new Setting<>("Explosions", true);
    public Setting<Boolean> Field1117 = new Setting<>("Fireworks", false);
    public Setting<Boolean> Field1118 = new Setting<>("Item", false);
    public Setting<Boolean> Field1119 = new Setting<>("Water", true);
    public Setting<Boolean> Field1120 = new Setting<>("Lava", true);
    public Setting<Boolean> Field1121 = new Setting<>("Blocks", true);
    public Setting<Boolean> Field1122 = new Setting<>("EnchantmentTable", false);
    public Setting<Boolean> Field1123 = new Setting<>("Signs", false);
    public Setting<Boolean> Field1124 = new Setting<>("Maps", false);
    public Setting<Boolean> Field1125 = new Setting<>("Beacon", false);
    public static Setting<Boolean> Field1126 = new Setting<>("Toasts", true);
    public Setting<Boolean> Field1127 = new Setting<>("Chat", false);
    public EntityPlayer.EnumChatVisibility Field1128 = null;

    public boolean Method394() {
        return this.Field1107.getValue() == Class417.SELECT;
    }

    public NoRender() {
        super("NoRender", Category.RENDER, new String[0]);
    }

    @Subscriber
    public void Method1126(Class142 class142) {
        block0: {
            if (!((Boolean)this.Field1124.getValue()).booleanValue()) break block0;
            class142.setCanceled(true);
        }
    }

    @Subscriber
    public void Method1127(Class569 class569) {
        block1: {
            block4: {
                block3: {
                    block2: {
                        block0: {
                            if (this.Field1107.getValue() != Class417.ALL) break block0;
                            class569.Cancel();
                            break block1;
                        }
                        if (this.Field1107.getValue() != Class417.SELECT) break block1;
                        if (class569.Method663() != EntityEquipmentSlot.HEAD || !((Boolean)this.Field1108.getValue()).booleanValue()) break block2;
                        class569.Cancel();
                        break block1;
                    }
                    if (class569.Method663() != EntityEquipmentSlot.CHEST || !((Boolean)this.Field1109.getValue()).booleanValue()) break block3;
                    class569.Cancel();
                    break block1;
                }
                if (class569.Method663() != EntityEquipmentSlot.LEGS || !((Boolean)this.Field1110.getValue()).booleanValue()) break block4;
                class569.Cancel();
                break block1;
            }
            if (class569.Method663() != EntityEquipmentSlot.FEET || !((Boolean)this.Field1111.getValue()).booleanValue()) break block1;
            class569.Cancel();
        }
    }

    public static boolean Method513(Entity entity) {
        return entity instanceof EntityBat;
    }

    public void Method145(Boolean bl) {
        block0: {
            if (!bl.booleanValue()) break block0;
            this.Method134();
        }
    }

    @Subscriber
    public void Method1128(Class137 class137) {
        block0: {
            if (!((Boolean)this.Field1123.getValue()).booleanValue()) break block0;
            class137.Cancel();
        }
    }

    @Override
    public void onEnable() {
        this.Method134();
    }

    @Subscriber
    public void Method1129(Class106 class106) {
        block0: {
            if (!((Boolean)this.Field1125.getValue()).booleanValue()) break block0;
            class106.Cancel();
        }
    }

    public boolean Method519() {
        return this.Field1107.getValue() == Class417.SELECT;
    }

    @Subscriber
    public void Method1130(Class93 class93) {
        block0: {
            if (!((Boolean)this.Field1101.getValue()).booleanValue()) break block0;
            class93.Cancel();
        }
    }

    @Subscriber
    public void Method1131(Class94 class94) {
        block0: {
            if (!((Boolean)this.Field1122.getValue()).booleanValue()) break block0;
            class94.Cancel();
        }
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (NoRender.mc.world == null || NoRender.mc.player == null) {
            return;
        }
        if (((Boolean)this.Field1127.getValue()).booleanValue()) {
            if (this.Field1128 == null) {
                this.Field1128 = NoRender.mc.gameSettings.chatVisibility;
            }
            NoRender.mc.gameSettings.chatVisibility = EntityPlayer.EnumChatVisibility.HIDDEN;
        } else if (this.Field1128 != null) {
            NoRender.mc.gameSettings.chatVisibility = this.Field1128;
            this.Field1128 = null;
        }
        if (((Boolean)this.Field1102.getValue()).booleanValue()) {
            if ((double)((IWorld)NoRender.mc.world).Method220() > 0.9) {
                this.Method1645("Thunder");
            } else if ((double)((IWorld)NoRender.mc.world).Method220() > 0.2) {
                this.Method1645("Rain");
            } else {
                this.Method1645("Clear");
            }
        } else {
            this.Method1645("");
        }
    }

    @Subscriber
    public void Method1132(Class79 class79) {
        block0: {
            if (!((Boolean)this.Field1102.getValue()).booleanValue()) break block0;
            class79.Cancel();
        }
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        block3: {
            if (packetEvent.getPacket() instanceof SPacketSpawnGlobalEntity && ((Boolean)this.Field1103.getValue()).booleanValue() && ((SPacketSpawnGlobalEntity) packetEvent.getPacket()).getType() == 1) {
                packetEvent.setCanceled(true);
            }
            if (packetEvent.getPacket() instanceof SPacketMaps && ((Boolean)this.Field1124.getValue()).booleanValue()) {
                packetEvent.setCanceled(true);
            }
            if (((Boolean)this.Field1106.getValue()).booleanValue() && (packetEvent.getPacket() instanceof SPacketSpawnMob && ((SPacketSpawnMob) packetEvent.getPacket()).getEntityType() == 65 || packetEvent.getPacket() instanceof SPacketSoundEffect && Field1100.contains(((SPacketSoundEffect) packetEvent.getPacket()).getSound()))) {
                packetEvent.Cancel();
            }
            if (!(packetEvent.getPacket() instanceof SPacketSpawnMob && (Boolean)this.Field1113.getValue() != false || packetEvent.getPacket() instanceof SPacketSpawnObject && (Boolean)this.Field1114.getValue() != false || packetEvent.getPacket() instanceof SPacketSpawnExperienceOrb && (Boolean)this.Field1115.getValue() != false || packetEvent.getPacket() instanceof SPacketExplosion && (Boolean)this.Field1116.getValue() != false || packetEvent.getPacket() instanceof SPacketSpawnObject && (Boolean)this.Field1118.getValue() != false && ((SPacketSpawnObject) packetEvent.getPacket()).getType() == 2) && (!(packetEvent.getPacket() instanceof SPacketSpawnObject) || !((Boolean)this.Field1117.getValue()).booleanValue() || ((SPacketSpawnObject) packetEvent.getPacket()).getType() != 76)) break block3;
            packetEvent.Cancel();
        }
    }

    @Subscriber
    public void Method1133(Class148 class148) {
        block5: {
            boolean bl = false;
            switch (class148.Method1825()) {
                case FIRE: {
                    if (!((Boolean)this.Field1104.getValue()).booleanValue()) break;
                    bl = true;
                    break;
                }
                case WATER: {
                    if (!((Boolean)this.Field1119.getValue()).booleanValue()) break;
                    bl = true;
                    break;
                }
                case BLOCK: {
                    if (!((Boolean)this.Field1121.getValue()).booleanValue()) break;
                    bl = true;
                }
            }
            if (!bl) break block5;
            class148.Cancel();
        }
    }

    public void Method134() {
        block0: {
            if (!((Boolean)this.Field1106.getValue()).booleanValue() || NoRender.mc.player == null || NoRender.mc.world == null) break block0;
            NoRender.mc.world.getLoadedEntityList().stream().filter(NoRender::Method513).forEach(NoRender::Method561);
        }
    }

    @Subscriber
    public void Method1134(Class131 class131) {
        block0: {
            if (!((Boolean)this.Field1112.getValue()).booleanValue()) break block0;
            class131.setCanceled(true);
        }
    }

    @Subscriber
    public void Method1135(Class13 class13) {
        block0: {
            if (!((Boolean)this.Field1117.getValue()).booleanValue() || !(class13.Method261() instanceof ParticleFirework.Overlay) && !(class13.Method261() instanceof ParticleFirework.Spark) && !(class13.Method261() instanceof ParticleFirework.Starter)) break block0;
            class13.setCanceled(true);
        }
    }

    @Subscriber
    public void Method1136(Class113 class113) {
        block0: {
            if (!((Boolean)this.Field1120.getValue()).booleanValue() || !class113.Method348().getMaterial().equals(Material.LAVA)) break block0;
            class113.Cancel();
        }
    }

    @Subscriber
    public void Method1137(Class642 class642) {
        block0: {
            if (!((Boolean)this.Field1105.getValue()).booleanValue()) break block0;
            class642.Cancel();
        }
    }

    public boolean Method539() {
        return this.Field1107.getValue() == Class417.SELECT;
    }

    public boolean Method393() {
        return this.Field1107.getValue() == Class417.SELECT;
    }

    public static void Method561(Entity entity) {
        NoRender.mc.world.removeEntity(entity);
    }
}
