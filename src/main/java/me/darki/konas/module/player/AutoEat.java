package me.darki.konas.module.player;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumHand;

public class AutoEat
extends Module {
    public Setting<Float> health = new Setting<>("Health", Float.valueOf(10.0f), Float.valueOf(36.0f), Float.valueOf(0.0f), Float.valueOf(1.0f));
    public Setting<Float> hunger = new Setting<>("Hunger", Float.valueOf(15.0f), Float.valueOf(20.0f), Float.valueOf(0.0f), Float.valueOf(1.0f));
    public Setting<Boolean> autoSwitch = new Setting<>("AutoSwitch", true);
    public Setting<Boolean> preferGaps = new Setting<>("PreferGaps", false);
    public int Field2293 = -1;
    public boolean Field2294 = true;
    public boolean Field2295 = false;

    public int Method464() {
        int n = -1;
        float f = 0.0f;
        if (n == -1) {
            for (int i = 0; i < 9; ++i) {
                ItemStack itemStack = AutoEat.mc.player.inventory.getStackInSlot(i);
                if (!(itemStack.getItem() instanceof ItemFood)) continue;
                if (this.preferGaps.getValue().booleanValue() && itemStack.getItem() == Items.GOLDEN_APPLE) {
                    n = i;
                    break;
                }
                float f2 = ((ItemFood)itemStack.getItem()).getHealAmount(itemStack);
                if (!(f2 > f)) continue;
                f = f2;
                n = i;
            }
        }
        return n;
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (AutoEat.mc.player == null || AutoEat.mc.world == null) {
            return;
        }
        if (AutoEat.mc.player.isCreative()) {
            return;
        }
        if (AutoEat.mc.player.getHealth() + AutoEat.mc.player.getAbsorptionAmount() <= this.health.getValue().floatValue() || (float) AutoEat.mc.player.getFoodStats().getFoodLevel() <= this.hunger.getValue().floatValue()) {
            if (this.autoSwitch.getValue().booleanValue() && !(AutoEat.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemFood) && !(AutoEat.mc.player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemFood)) {
                int n = this.Method464();
                if (this.Field2294) {
                    this.Field2293 = AutoEat.mc.player.inventory.currentItem;
                    this.Field2294 = false;
                }
                if (n != -1) {
                    AutoEat.mc.player.inventory.currentItem = n;
                    AutoEat.mc.player.connection.sendPacket(new CPacketHeldItemChange(n));
                }
            }
            if (AutoEat.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemFood) {
                if (AutoEat.mc.currentScreen == null || AutoEat.mc.currentScreen instanceof GuiInventory) {
                    KeyBinding.setKeyBindState(AutoEat.mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                    this.Field2295 = true;
                } else {
                    AutoEat.mc.playerController.processRightClick(AutoEat.mc.player, AutoEat.mc.world, EnumHand.MAIN_HAND);
                }
            } else if (AutoEat.mc.player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemFood) {
                if (AutoEat.mc.currentScreen == null || AutoEat.mc.currentScreen instanceof GuiInventory) {
                    KeyBinding.setKeyBindState(AutoEat.mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                    this.Field2295 = true;
                } else {
                    AutoEat.mc.playerController.processRightClick(AutoEat.mc.player, AutoEat.mc.world, EnumHand.OFF_HAND);
                }
            } else if (AutoEat.mc.currentScreen == null || AutoEat.mc.currentScreen instanceof GuiInventory) {
                KeyBinding.setKeyBindState(AutoEat.mc.gameSettings.keyBindUseItem.getKeyCode(), GameSettings.isKeyDown(AutoEat.mc.gameSettings.keyBindUseItem));
            }
        } else {
            if (this.Field2295) {
                KeyBinding.setKeyBindState(AutoEat.mc.gameSettings.keyBindUseItem.getKeyCode(), GameSettings.isKeyDown(AutoEat.mc.gameSettings.keyBindUseItem));
                this.Field2295 = false;
            }
            if (this.Field2293 != -1) {
                AutoEat.mc.player.inventory.currentItem = this.Field2293;
                AutoEat.mc.player.connection.sendPacket(new CPacketHeldItemChange(this.Field2293));
                this.Field2293 = -1;
                this.Field2294 = true;
            }
        }
    }

    public AutoEat() {
        super("AutoEat", Category.MISC, "AutoFood");
    }
}