package me.darki.konas.module.misc;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.mixin.mixins.IMinecraft;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.Class19;
import me.darki.konas.unremaped.Class376;
import me.darki.konas.util.TimerUtil;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Items;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.math.BlockPos;

public class AutoFish
extends Module {
    public static Setting<Class376> mode = new Setting<>("Mode", Class376.BOUNCE);
    public static Setting<Boolean> cast = new Setting<>("Cast", true);
    public boolean Field2086 = false;
    public boolean Field2087 = false;
    public TimerUtil Field2088 = new TimerUtil();

    public AutoFish() {
        super("AutoFish", Category.MISC, "AutoCaster");
    }

    @Subscriber
    public void Method130(Class19 class19) {
        if (AutoFish.mc.player.getHeldItemMainhand().getItem() != Items.FISHING_ROD) {
            this.Field2088.UpdateCurrentTime();
            this.Field2086 = false;
            this.Field2087 = false;
            return;
        }
        if (AutoFish.mc.player.fishEntity == null) {
            if (this.Field2087) {
                if (this.Field2088.GetDifferenceTiming(450.0)) {
                    ((IMinecraft)mc).rightClickMouse();
                    this.Field2088.UpdateCurrentTime();
                    this.Field2086 = false;
                    this.Field2087 = false;
                }
            } else if (cast.getValue().booleanValue() && this.Field2088.GetDifferenceTiming(4500.0)) {
                ((IMinecraft)mc).rightClickMouse();
                this.Field2088.UpdateCurrentTime();
                this.Field2086 = false;
                this.Field2087 = false;
            }
        } else if (this.Method394() && this.Method388()) {
            if (this.Field2086) {
                if (this.Field2088.GetDifferenceTiming(350.0)) {
                    ((IMinecraft)mc).rightClickMouse();
                    this.Field2088.UpdateCurrentTime();
                    this.Field2086 = false;
                    this.Field2087 = true;
                }
            } else if (mode.getValue() != Class376.SPLASH && this.Method393()) {
                this.Field2088.UpdateCurrentTime();
                this.Field2086 = true;
                this.Field2087 = false;
            }
        } else if (this.Method394()) {
            ((IMinecraft)mc).rightClickMouse();
            this.Field2088.UpdateCurrentTime();
            this.Field2086 = false;
            this.Field2087 = false;
        }
    }

    @Override
    public String Method756() {
        return mode.getValue().toString().charAt(0) + mode.getValue().toString().substring(1).toLowerCase();
    }

    public boolean Method394() {
        if (AutoFish.mc.player.fishEntity == null || AutoFish.mc.player.fishEntity.isAirBorne || this.Field2087) {
            return false;
        }
        return Math.abs(AutoFish.mc.player.fishEntity.motionX) + Math.abs(AutoFish.mc.player.fishEntity.motionZ) < 0.01;
    }

    public boolean Method388() {
        if (AutoFish.mc.player.fishEntity == null || AutoFish.mc.player.fishEntity.isAirBorne) {
            return false;
        }
        BlockPos blockPos = AutoFish.mc.player.fishEntity.getPosition();
        return AutoFish.mc.world.getBlockState(blockPos).getBlock() instanceof BlockLiquid || AutoFish.mc.world.getBlockState(blockPos.down()).getBlock() instanceof BlockLiquid;
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        block0: {
            if (!(packetEvent.getPacket() instanceof SPacketSoundEffect) || !((SPacketSoundEffect) packetEvent.getPacket()).getSound().getSoundName().toString().toLowerCase().contains("entity.bobber.splash") || this.Field2087 || !this.Method394() || mode.getValue() == Class376.BOUNCE) break block0;
            this.Field2086 = true;
            this.Field2088.UpdateCurrentTime();
        }
    }

    public boolean Method393() {
        if (AutoFish.mc.player.fishEntity == null || !this.Method388()) {
            return false;
        }
        return Math.abs(AutoFish.mc.player.fishEntity.motionY) > 0.05;
    }
}