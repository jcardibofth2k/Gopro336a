package me.darki.konas.module;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.Class28;
import me.darki.konas.util.TimerUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.util.EnumHand;

public class StrictTotem
extends Module {
    public static Setting<Integer> delay = new Setting<>("Delay", 0, 20, 0, 1);
    public static Setting<Boolean> cancelMotion = new Setting<>("CancelMotion", false);
    public TimerUtil Field2555 = new TimerUtil();
    public boolean Field2556 = false;

    @Subscriber
    public void Method390(Class28 class28) {
        if (!this.Field2556) {
            this.Field2555.UpdateCurrentTime();
        }
        if (StrictTotem.mc.player == null || StrictTotem.mc.world == null) {
            return;
        }
        if (!(StrictTotem.mc.currentScreen instanceof GuiContainer && !(StrictTotem.mc.currentScreen instanceof GuiInventory) || StrictTotem.mc.player.getHeldItem(EnumHand.OFF_HAND).getItem() == Items.TOTEM_OF_UNDYING || StrictTotem.mc.player.isCreative())) {
            for (int i = 44; i >= 9; --i) {
                if (StrictTotem.mc.player.inventoryContainer.getSlot(i).getStack().getItem() != Items.TOTEM_OF_UNDYING) continue;
                this.Field2556 = true;
                if (this.Field2555.GetDifferenceTiming((float) delay.getValue().intValue() * 100.0f) && StrictTotem.mc.player.inventory.getItemStack().getItem() != Items.TOTEM_OF_UNDYING) {
                    if (cancelMotion.getValue().booleanValue() && StrictTotem.mc.player.motionX * StrictTotem.mc.player.motionX + StrictTotem.mc.player.motionY * StrictTotem.mc.player.motionY + StrictTotem.mc.player.motionZ * StrictTotem.mc.player.motionZ >= 9.0E-4) {
                        StrictTotem.mc.player.motionX = 0.0;
                        StrictTotem.mc.player.motionY = 0.0;
                        StrictTotem.mc.player.motionZ = 0.0;
                        return;
                    }
                    StrictTotem.mc.playerController.windowClick(0, i, 0, ClickType.PICKUP, StrictTotem.mc.player);
                }
                if (this.Field2555.GetDifferenceTiming((float) delay.getValue().intValue() * 200.0f) && StrictTotem.mc.player.inventory.getItemStack().getItem() == Items.TOTEM_OF_UNDYING) {
                    if (cancelMotion.getValue().booleanValue() && StrictTotem.mc.player.motionX * StrictTotem.mc.player.motionX + StrictTotem.mc.player.motionY * StrictTotem.mc.player.motionY + StrictTotem.mc.player.motionZ * StrictTotem.mc.player.motionZ >= 9.0E-4) {
                        StrictTotem.mc.player.motionX = 0.0;
                        StrictTotem.mc.player.motionY = 0.0;
                        StrictTotem.mc.player.motionZ = 0.0;
                        return;
                    }
                    StrictTotem.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, StrictTotem.mc.player);
                    if (StrictTotem.mc.player.inventory.getItemStack().isEmpty()) {
                        this.Field2556 = false;
                        return;
                    }
                }
                if (!this.Field2555.GetDifferenceTiming((float) delay.getValue().intValue() * 300.0f) || StrictTotem.mc.player.inventory.getItemStack().isEmpty() || StrictTotem.mc.player.getHeldItem(EnumHand.OFF_HAND).getItem() != Items.TOTEM_OF_UNDYING) continue;
                if (cancelMotion.getValue().booleanValue() && StrictTotem.mc.player.motionX * StrictTotem.mc.player.motionX + StrictTotem.mc.player.motionY * StrictTotem.mc.player.motionY + StrictTotem.mc.player.motionZ * StrictTotem.mc.player.motionZ >= 9.0E-4) {
                    StrictTotem.mc.player.motionX = 0.0;
                    StrictTotem.mc.player.motionY = 0.0;
                    StrictTotem.mc.player.motionZ = 0.0;
                    return;
                }
                StrictTotem.mc.playerController.windowClick(0, i, 0, ClickType.PICKUP, StrictTotem.mc.player);
                this.Field2556 = false;
                return;
            }
        }
    }

    public StrictTotem() {
        super("StrictTotem", "Forces totem into offhand", Category.COMBAT);
        this.Method1633(315, 1000);
    }

    @Override
    public void onEnable() {
        this.Field2556 = false;
    }
}