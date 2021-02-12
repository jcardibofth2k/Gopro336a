package me.darki.konas.module.misc;

import cookiedragon.eventsystem.Subscriber;
import java.util.Random;
import me.darki.konas.module.Category;
import me.darki.konas.unremaped.Class566;
import me.darki.konas.TickEvent;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;

public class ChestStealer
extends Module {
    public Setting<Integer> Field2175 = new Setting<>("Delay", 100, 1000, 1, 1);
    public Setting<Boolean> Field2176 = new Setting<>("Random", false);
    public Class566 Field2177 = new Class566();

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
                if (!this.Field2177.Method737(this.Field2175.getValue() + (this.Field2176.getValue() != false ? random.nextInt(this.Field2175.getValue()) : 0))) continue;
                ChestStealer.mc.playerController.windowClick(containerChest.windowId, i, 0, ClickType.QUICK_MOVE, ChestStealer.mc.player);
                this.Field2177.Method739();
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
