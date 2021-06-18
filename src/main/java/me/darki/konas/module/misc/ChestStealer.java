package me.darki.konas.module.misc;

import cookiedragon.eventsystem.Subscriber;
import java.util.Random;
import me.darki.konas.module.Category;
import me.darki.konas.util.TimerUtil;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;

public class ChestStealer
extends Module {
    public Setting<Integer> delay = new Setting<>("Delay", 100, 1000, 1, 1);
    public Setting<Boolean> random = new Setting<>("Random", false);
    public TimerUtil Field2177 = new TimerUtil();

    public boolean Method1975(Container container) {
        int n;
        boolean bl = true;
        int n2 = n = container.inventorySlots.size() == 90 ? 54 : 27;
        for (int i = 0; i < n; ++i) {
            if (!container.getSlot(i).getHasStack()) continue;
            bl = false;
        }
        return bl;
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        block2: {
            if (!(ChestStealer.mc.currentScreen instanceof GuiChest)) break block2;
            GuiChest guiChest = (GuiChest)ChestStealer.mc.currentScreen;
            ContainerChest containerChest = (ContainerChest)ChestStealer.mc.player.openContainer;
            for (int i = 0; i < containerChest.getLowerChestInventory().getSizeInventory(); ++i) {
                Slot slot = guiChest.inventorySlots.getSlot(i);
                if (!slot.getHasStack()) continue;
                Random random = new Random();
                if (!this.Field2177.GetDifferenceTiming(this.delay.getValue() + (this.random.getValue() != false ? random.nextInt(this.delay.getValue()) : 0))) continue;
                ChestStealer.mc.playerController.windowClick(containerChest.windowId, i, 0, ClickType.QUICK_MOVE, ChestStealer.mc.player);
                this.Field2177.UpdateCurrentTime();
            }
            if (this.Method1975(containerChest)) {
                ChestStealer.mc.player.closeScreen();
            }
        }
    }

    public ChestStealer() {
        super("ChestStealer", "Automatically takes items out of chests", Category.PLAYER, "Looter", "ChestLooter");
    }
}