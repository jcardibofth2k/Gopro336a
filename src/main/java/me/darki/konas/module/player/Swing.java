package me.darki.konas.module.player;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.unremaped.Class24;
import me.darki.konas.unremaped.Class395;
import me.darki.konas.mixin.mixins.ICPacketAnimation;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumHand;

public class Swing
extends Module {
    public Setting<Class395> mode = new Setting<>("Mode", Class395.CANCEL);
    public Setting<Boolean> strict = new Setting<>("Strict", false);
    public Setting<Boolean> render = new Setting<>("Render", false);

    public Swing() {
        super("Swing", "Modifies swinging behavior", Category.PLAYER);
    }

    public int Method464() {
        if (Swing.mc.player.isPotionActive(MobEffects.HASTE)) {
            return 6 - (1 + Swing.mc.player.getActivePotionEffect(MobEffects.HASTE).getAmplifier());
        }
        return Swing.mc.player.isPotionActive(MobEffects.MINING_FATIGUE) ? 6 + (1 + Swing.mc.player.getActivePotionEffect(MobEffects.MINING_FATIGUE).getAmplifier()) * 2 : 6;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Subscriber
    public void Method536(Class24 class24) {
        CPacketAnimation cPacketAnimation;
        if (Swing.mc.player == null) return;
        if (Swing.mc.world == null) {
            return;
        }
        if (!(class24.getPacket() instanceof CPacketAnimation)) return;
        if (this.mode.getValue() == Class395.CANCEL) {
            if (!this.strict.getValue().booleanValue() || Swing.mc.playerController.getIsHittingBlock()) {
                class24.Cancel();
            }
        } else if (this.mode.getValue() == Class395.OFFHAND) {
            cPacketAnimation = (CPacketAnimation)class24.getPacket();
            ((ICPacketAnimation)cPacketAnimation).Method1602(EnumHand.OFF_HAND);
        } else if (this.mode.getValue() == Class395.MAINHAND) {
            cPacketAnimation = (CPacketAnimation)class24.getPacket();
            ((ICPacketAnimation)cPacketAnimation).Method1602(EnumHand.MAIN_HAND);
        } else if (this.mode.getValue() == Class395.OPPOSITE) {
            cPacketAnimation = (CPacketAnimation)class24.getPacket();
            ((ICPacketAnimation)cPacketAnimation).Method1602(cPacketAnimation.getHand() == EnumHand.MAIN_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
        }
        if (this.render.getValue() == false) return;
        cPacketAnimation = ((CPacketAnimation)class24.getPacket()).getHand();
        try {
            if (Swing.mc.player.isSwingInProgress && Swing.mc.player.swingProgressInt < this.Method464() / 2) {
                if (Swing.mc.player.swingProgressInt >= 0) return;
            }
            Swing.mc.player.swingProgressInt = -1;
            Swing.mc.player.isSwingInProgress = true;
            Swing.mc.player.swingingHand = cPacketAnimation;
            return;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}
