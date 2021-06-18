package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.mixin.mixins.IEntityPlayerSP;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.util.TimerUtil;
import me.darki.konas.util.rotation.Rotation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class Class233
extends Module {
    public static Setting<Boolean> swiftness = new Setting<>("Swiftness", false);
    public static Setting<Boolean> strength = new Setting<>("Strength", false);
    public static Setting<Boolean> toggelable = new Setting<>("Toggelable", false);
    public static Setting<Boolean> autoSwitch = new Setting<>("AutoSwitch", false);
    public static Setting<Boolean> rearrange = new Setting<>("Rearrange", false);
    public static Setting<Boolean> noGapSwitch = new Setting<>("NoGapSwitch", false);
    public static Setting<Integer> minHealth = new Setting<>("MinHealth", 20, 36, 0, 1);
    public TimerUtil Field2480 = new TimerUtil();
    public boolean Field2481 = false;

    @Subscriber(priority=98)
    public void Method2131(UpdateEvent updateEvent) {
        block9: {
            block10: {
                if (Class233.mc.player == null || Class233.mc.world == null) {
                    return;
                }
                if (updateEvent.isCanceled() || !Rotation.Method1966()) {
                    return;
                }
                if (!this.Field2480.GetDifferenceTiming(2500.0)) {
                    return;
                }
                if (Class233.mc.player.getHealth() + Class233.mc.player.getAbsorptionAmount() < (float)((Integer)minHealth.getValue()).intValue()) {
                    return;
                }
                if (((Boolean)noGapSwitch.getValue()).booleanValue() && Class233.mc.player.getActiveItemStack().getItem() instanceof ItemFood) {
                    return;
                }
                if (((Boolean)strength.getValue()).booleanValue() && !Class233.mc.player.isPotionActive(MobEffects.STRENGTH)) {
                    if (this.Method1925("Arrow of Strength")) {
                        this.Method135(updateEvent);
                    } else if (((Boolean)toggelable.getValue()).booleanValue()) {
                        this.toggle();
                    }
                }
                if (!((Boolean)swiftness.getValue()).booleanValue() || Class233.mc.player.isPotionActive(MobEffects.SPEED)) break block9;
                if (!this.Method1925("Arrow of Swiftness")) break block10;
                this.Method135(updateEvent);
                break block9;
            }
            if (!((Boolean)toggelable.getValue()).booleanValue()) break block9;
            this.toggle();
        }
    }

    public void Method135(UpdateEvent updateEvent) {
        block3: {
            int n;
            block1: {
                block2: {
                    if (Class233.mc.player.inventory.getCurrentItem().getItem() != Items.BOW) break block1;
                    Class233.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, -90.0f, Class233.mc.player.onGround));
                    ((IEntityPlayerSP)Class233.mc.player).setLastReportedYaw(0.0f);
                    ((IEntityPlayerSP)Class233.mc.player).setLastReportedPitch(-90.0f);
                    if (Class233.mc.player.getItemInUseMaxCount() < 3) break block2;
                    this.Field2481 = false;
                    Class233.mc.playerController.onStoppedUsingItem((EntityPlayer)Class233.mc.player);
                    if (((Boolean)toggelable.getValue()).booleanValue()) {
                        this.toggle();
                    }
                    this.Field2480.UpdateCurrentTime();
                    break block3;
                }
                if (Class233.mc.player.getItemInUseMaxCount() != 0) break block3;
                Class233.mc.playerController.processRightClick((EntityPlayer)Class233.mc.player, (World)Class233.mc.world, EnumHand.MAIN_HAND);
                this.Field2481 = true;
                break block3;
            }
            if (!((Boolean)autoSwitch.getValue()).booleanValue() || (n = this.Method464()) == -1 || n == Class233.mc.player.inventory.currentItem) break block3;
            Class233.mc.player.inventory.currentItem = n;
            Class233.mc.playerController.updateController();
        }
    }

    public boolean Method2132(int n, String string) {
        for (int i = 0; i < 36; ++i) {
            ItemStack itemStack = Class233.mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() != Items.TIPPED_ARROW || !itemStack.getDisplayName().equalsIgnoreCase(string)) continue;
            Class233.mc.playerController.windowClick(0, n, 0, ClickType.PICKUP, (EntityPlayer)Class233.mc.player);
            Class233.mc.playerController.windowClick(0, i, 0, ClickType.PICKUP, (EntityPlayer)Class233.mc.player);
            Class233.mc.playerController.windowClick(0, n, 0, ClickType.PICKUP, (EntityPlayer)Class233.mc.player);
            return true;
        }
        return false;
    }

    public boolean Method1925(String string) {
        for (int i = 0; i < 36; ++i) {
            ItemStack itemStack = Class233.mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() != Items.TIPPED_ARROW) continue;
            boolean bl = itemStack.getDisplayName().equalsIgnoreCase(string);
            if (bl) {
                return true;
            }
            if (((Boolean)rearrange.getValue()).booleanValue()) {
                return this.Method2132(i, string);
            }
            return false;
        }
        return false;
    }

    @Subscriber
    public void Method1762(Class41 class41) {
        block0: {
            if (!this.Field2481) break block0;
            class41.setCanceled(true);
        }
    }

    @Override
    public void onEnable() {
        this.Field2481 = false;
    }

    public int Method464() {
        int n = -1;
        if (Class233.mc.player.getHeldItemMainhand().getItem() == Items.BOW) {
            n = Module.mc.player.inventory.currentItem;
        }
        if (n == -1) {
            for (int i = 0; i < 9; ++i) {
                if (Class233.mc.player.inventory.getStackInSlot(i).getItem() != Items.BOW) continue;
                n = i;
                break;
            }
        }
        return n;
    }

    public Class233() {
        super("SelfBow", "Shoots yourself", Category.COMBAT, new String[0]);
    }
}