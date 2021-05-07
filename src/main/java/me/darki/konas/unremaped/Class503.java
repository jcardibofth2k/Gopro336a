package me.darki.konas.unremaped;

import me.darki.konas.*;
import cookiedragon.eventsystem.Subscriber;
import java.util.Arrays;

import me.darki.konas.setting.ListenableSettingDecorator;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.potion.PotionEffect;

public class Class503
extends Module {
    public float Field1371;
    public ListenableSettingDecorator<Class505> Field1372 = new ListenableSettingDecorator("Mode", Class505.NORMAL, this::Method1442);
    public Setting<Boolean> sine = new Setting<>("Sine", false).visibleIf(this::Method394);
    public Setting<Boolean> cancel = new Setting<>("Cancel", false).visibleIf(this::Method388);
    public long Field1375;

    public void Method1442(Class505 class505) {
        block5: {
            if (Class503.mc.world == null || Class503.mc.player == null) break block5;
            if (class505 != Class505.NORMAL) {
                if (Class503.mc.player.dimension == -1) {
                    Class503.Method124();
                } else {
                    Class503.Method1443();
                }
            }
            if (class505 != Class505.GAMMA) {
                Class503.mc.gameSettings.gammaSetting = this.Field1371;
            }
            if (class505 != Class505.POTION) {
                Class503.mc.player.removePotionEffect(MobEffects.NIGHT_VISION);
            }
        }
    }

    public boolean Method388() {
        return this.Field1372.getValue() == Class505.POTION;
    }

    @Override
    public void onDisable() {
        if (Class503.mc.player == null || Class503.mc.world == null) {
            return;
        }
        if (this.Field1372.getValue() == Class505.GAMMA) {
            Class503.mc.gameSettings.gammaSetting = this.Field1371;
        } else if (this.Field1372.getValue() == Class505.NORMAL) {
            if (Class503.mc.player.dimension == -1) {
                Class503.Method124();
            } else {
                Class503.Method1443();
            }
        } else {
            Class503.mc.player.removePotionEffect(MobEffects.NIGHT_VISION);
        }
    }

    public boolean Method394() {
        return this.Field1372.getValue() == Class505.GAMMA;
    }

    public static void Method124() {
        float f = 0.1f;
        for (int i = 0; i <= 15; ++i) {
            float f2 = 1.0f - (float)i / 15.0f;
            Class503.mc.world.provider.getLightBrightnessTable()[i] = (1.0f - f2) / (f2 * 3.0f + 1.0f) * 0.9f + 0.1f;
        }
    }

    public static void Method1443() {
        float f = 0.0f;
        for (int i = 0; i <= 15; ++i) {
            float f2 = 1.0f - (float)i / 15.0f;
            Class503.mc.world.provider.getLightBrightnessTable()[i] = (1.0f - f2) / (f2 * 3.0f + 1.0f) * 1.0f + 0.0f;
        }
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (Class503.mc.world == null || Class503.mc.player == null) {
            return;
        }
        if (this.Field1372.getValue() == Class505.GAMMA) {
            Class503.mc.gameSettings.gammaSetting = (Boolean)this.sine.getValue() != false ? this.Field1371 + 20.0f * Math.min(1.0f, (float)(System.currentTimeMillis() - this.Field1375) / 1000.0f) : this.Field1371 + 20.0f;
        } else if (this.Field1372.getValue() == Class505.NORMAL) {
            Arrays.fill(Class503.mc.world.provider.getLightBrightnessTable(), 1.0f);
        } else {
            Class503.mc.player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 5210));
        }
    }

    public Class503() {
        super("FullBright", "Makes everything bright", 0, Category.RENDER, new String[0]);
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        block1: {
            if (!(packetEvent.getPacket() instanceof SPacketEntityEffect) || !((Boolean)this.cancel.getValue()).booleanValue()) break block1;
            SPacketEntityEffect sPacketEntityEffect = (SPacketEntityEffect) packetEvent.getPacket();
            if (Class503.mc.player != null && sPacketEntityEffect.getEntityId() == Class503.mc.player.getEntityId() && (sPacketEntityEffect.getEffectId() == 9 || sPacketEntityEffect.getEffectId() == 15)) {
                packetEvent.setCanceled(true);
            }
        }
    }

    @Override
    public void onEnable() {
        this.Field1371 = Class503.mc.gameSettings.gammaSetting;
        this.Field1375 = System.currentTimeMillis();
    }
}