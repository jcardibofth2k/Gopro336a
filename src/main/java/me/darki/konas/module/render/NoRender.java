package me.darki.konas.module.render;

import com.google.common.collect.Sets;
import cookiedragon.eventsystem.Subscriber;
import java.util.Set;
import me.darki.konas.module.Category;
import me.darki.konas.unremaped.Class106;
import me.darki.konas.event.events.EventIdkPlsRename;
import me.darki.konas.unremaped.ParticalEffectAddEvent;
import me.darki.konas.unremaped.Class131;
import me.darki.konas.unremaped.Class137;
import me.darki.konas.unremaped.Class142;
import me.darki.konas.event.events.RenderBlockOverlayEvent;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.unremaped.Class417;
import me.darki.konas.setting.ListenableSettingDecorator;
import me.darki.konas.unremaped.RenderArmorEvent;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.unremaped.Class642;
import me.darki.konas.unremaped.Class79;
import me.darki.konas.unremaped.Class93;
import me.darki.konas.unremaped.Class94;
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
    public Setting<Boolean> noHurtCam = new Setting<>("NoHurtCam", true);
    public Setting<Boolean> noWeather = new Setting<>("NoWeather", true);
    public Setting<Boolean> noLightning = new Setting<>("NoLightning", true);
    public Setting<Boolean> noFire = new Setting<>("NoFire", true);
    public Setting<Boolean> noBossBar = new Setting<>("NoBossBar", false);
    public Setting<Boolean> Field1106 = new ListenableSettingDecorator("NoBats", true, this::Method145);
    public Setting<Class417> armor = new Setting<>("Armor", Class417.NONE);
    public Setting<Boolean> head = new Setting<>("Head", true).visibleIf(this::Method394);
    public Setting<Boolean> chestplate = new Setting<>("Chestplate", false).visibleIf(this::Method539);
    public Setting<Boolean> leggings = new Setting<>("Leggings", false).visibleIf(this::Method393);
    public Setting<Boolean> boots = new Setting<>("Boots", false).visibleIf(this::Method519);
    public Setting<Boolean> ownShadow = new Setting<>("OwnShadow", true);
    public Setting<Boolean> mob = new Setting<>("Mob", false);
    public Setting<Boolean> object = new Setting<>("Object", false);
    public Setting<Boolean> xP = new Setting<>("XP", true);
    public Setting<Boolean> explosions = new Setting<>("Explosions", true);
    public Setting<Boolean> fireworks = new Setting<>("Fireworks", false);
    public Setting<Boolean> item = new Setting<>("Item", false);
    public Setting<Boolean> water = new Setting<>("Water", true);
    public Setting<Boolean> lava = new Setting<>("Lava", true);
    public Setting<Boolean> blocks = new Setting<>("Blocks", true);
    public Setting<Boolean> enchantmentTable = new Setting<>("EnchantmentTable", false);
    public Setting<Boolean> signs = new Setting<>("Signs", false);
    public Setting<Boolean> maps = new Setting<>("Maps", false);
    public Setting<Boolean> beacon = new Setting<>("Beacon", false);
    public static Setting<Boolean> toasts = new Setting<>("Toasts", true);
    public Setting<Boolean> chat = new Setting<>("Chat", false);
    public EntityPlayer.EnumChatVisibility Field1128 = null;

    public boolean Method394() {
        return this.armor.getValue() == Class417.SELECT;
    }

    public NoRender() {
        super("NoRender", Category.RENDER);
    }

    @Subscriber
    public void Method1126(Class142 class142) {
        block0: {
            if (!this.maps.getValue().booleanValue()) break block0;
            class142.setCanceled(true);
        }
    }

    @Subscriber
    public void Method1127(RenderArmorEvent renderArmorEvent) {
        block1: {
            block4: {
                block3: {
                    block2: {
                        block0: {
                            if (this.armor.getValue() != Class417.ALL) break block0;
                            renderArmorEvent.Cancel();
                            break block1;
                        }
                        if (this.armor.getValue() != Class417.SELECT) break block1;
                        if (renderArmorEvent.Method663() != EntityEquipmentSlot.HEAD || !this.head.getValue().booleanValue()) break block2;
                        renderArmorEvent.Cancel();
                        break block1;
                    }
                    if (renderArmorEvent.Method663() != EntityEquipmentSlot.CHEST || !this.chestplate.getValue().booleanValue()) break block3;
                    renderArmorEvent.Cancel();
                    break block1;
                }
                if (renderArmorEvent.Method663() != EntityEquipmentSlot.LEGS || !this.leggings.getValue().booleanValue()) break block4;
                renderArmorEvent.Cancel();
                break block1;
            }
            if (renderArmorEvent.Method663() != EntityEquipmentSlot.FEET || !this.boots.getValue().booleanValue()) break block1;
            renderArmorEvent.Cancel();
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
            if (!this.signs.getValue().booleanValue()) break block0;
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
            if (!this.beacon.getValue().booleanValue()) break block0;
            class106.Cancel();
        }
    }

    public boolean Method519() {
        return this.armor.getValue() == Class417.SELECT;
    }

    @Subscriber
    public void Method1130(Class93 class93) {
        block0: {
            if (!this.noHurtCam.getValue().booleanValue()) break block0;
            class93.Cancel();
        }
    }

    @Subscriber
    public void Method1131(Class94 class94) {
        block0: {
            if (!this.enchantmentTable.getValue().booleanValue()) break block0;
            class94.Cancel();
        }
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (NoRender.mc.world == null || NoRender.mc.player == null) {
            return;
        }
        if (this.chat.getValue().booleanValue()) {
            if (this.Field1128 == null) {
                this.Field1128 = NoRender.mc.gameSettings.chatVisibility;
            }
            NoRender.mc.gameSettings.chatVisibility = EntityPlayer.EnumChatVisibility.HIDDEN;
        } else if (this.Field1128 != null) {
            NoRender.mc.gameSettings.chatVisibility = this.Field1128;
            this.Field1128 = null;
        }
        if (this.noWeather.getValue().booleanValue()) {
            if ((double)((IWorld)NoRender.mc.world).getRainingStrength() > 0.9) {
                this.Method1645("Thunder");
            } else if ((double)((IWorld)NoRender.mc.world).getRainingStrength() > 0.2) {
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
            if (!this.noWeather.getValue().booleanValue()) break block0;
            class79.Cancel();
        }
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        block3: {
            if (packetEvent.getPacket() instanceof SPacketSpawnGlobalEntity && this.noLightning.getValue().booleanValue() && ((SPacketSpawnGlobalEntity) packetEvent.getPacket()).getType() == 1) {
                packetEvent.setCanceled(true);
            }
            if (packetEvent.getPacket() instanceof SPacketMaps && this.maps.getValue().booleanValue()) {
                packetEvent.setCanceled(true);
            }
            if (this.Field1106.getValue().booleanValue() && (packetEvent.getPacket() instanceof SPacketSpawnMob && ((SPacketSpawnMob) packetEvent.getPacket()).getEntityType() == 65 || packetEvent.getPacket() instanceof SPacketSoundEffect && Field1100.contains(((SPacketSoundEffect) packetEvent.getPacket()).getSound()))) {
                packetEvent.Cancel();
            }
            if (!(packetEvent.getPacket() instanceof SPacketSpawnMob && this.mob.getValue() != false || packetEvent.getPacket() instanceof SPacketSpawnObject && this.object.getValue() != false || packetEvent.getPacket() instanceof SPacketSpawnExperienceOrb && this.xP.getValue() != false || packetEvent.getPacket() instanceof SPacketExplosion && this.explosions.getValue() != false || packetEvent.getPacket() instanceof SPacketSpawnObject && this.item.getValue() != false && ((SPacketSpawnObject) packetEvent.getPacket()).getType() == 2) && (!(packetEvent.getPacket() instanceof SPacketSpawnObject) || !this.fireworks.getValue().booleanValue() || ((SPacketSpawnObject) packetEvent.getPacket()).getType() != 76)) break block3;
            packetEvent.Cancel();
        }
    }

    @Subscriber
    public void Method1133(RenderBlockOverlayEvent renderBlockOverlayEvent) {
        block5: {
            boolean bl = false;
            switch (renderBlockOverlayEvent.Method1825()) {
                case FIRE: {
                    if (!this.noFire.getValue().booleanValue()) break;
                    bl = true;
                    break;
                }
                case WATER: {
                    if (!this.water.getValue().booleanValue()) break;
                    bl = true;
                    break;
                }
                case BLOCK: {
                    if (!this.blocks.getValue().booleanValue()) break;
                    bl = true;
                }
            }
            if (!bl) break block5;
            renderBlockOverlayEvent.Cancel();
        }
    }

    public void Method134() {
        block0: {
            if (!this.Field1106.getValue().booleanValue() || NoRender.mc.player == null || NoRender.mc.world == null) break block0;
            NoRender.mc.world.getLoadedEntityList().stream().filter(NoRender::Method513).forEach(NoRender::Method561);
        }
    }

    @Subscriber
    public void Method1134(Class131 class131) {
        block0: {
            if (!this.ownShadow.getValue().booleanValue()) break block0;
            class131.setCanceled(true);
        }
    }

    @Subscriber
    public void Method1135(ParticalEffectAddEvent particalEffectAddEvent) {
        block0: {
            if (!this.fireworks.getValue().booleanValue() || !(particalEffectAddEvent.Method261() instanceof ParticleFirework.Overlay) && !(particalEffectAddEvent.Method261() instanceof ParticleFirework.Spark) && !(particalEffectAddEvent.Method261() instanceof ParticleFirework.Starter)) break block0;
            particalEffectAddEvent.setCanceled(true);
        }
    }

    @Subscriber
    public void Method1136(EventIdkPlsRename eventIdkPlsRename) {
        block0: {
            if (!this.lava.getValue().booleanValue() || !eventIdkPlsRename.Method348().getMaterial().equals(Material.LAVA)) break block0;
            eventIdkPlsRename.Cancel();
        }
    }

    @Subscriber
    public void Method1137(Class642 class642) {
        block0: {
            if (!this.noBossBar.getValue().booleanValue()) break block0;
            class642.Cancel();
        }
    }

    public boolean Method539() {
        return this.armor.getValue() == Class417.SELECT;
    }

    public boolean Method393() {
        return this.armor.getValue() == Class417.SELECT;
    }

    public static void Method561(Entity entity) {
        NoRender.mc.world.removeEntity(entity);
    }
}