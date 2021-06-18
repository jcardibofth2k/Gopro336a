package me.darki.konas.module.player;

import cookiedragon.eventsystem.Subscriber;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.darki.konas.module.Category;
import me.darki.konas.unremaped.SendPacketEvent;
import me.darki.konas.util.TimerUtil;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.util.EnumHand;

public class HotbarRefil
extends Module {
    public static Setting<Boolean> itemSaver = new Setting<>("ItemSaver", false);
    public static Setting<Boolean> handOnly = new Setting<>("HandOnly", false);
    public Setting<Integer> refillThreshold = new Setting<>("RefillThreshold", 36, 64, 1, 1);
    public Setting<Integer> delay = new Setting<>("Delay", 1, 20, 1, 1);
    public static Setting<Boolean> crystals = new Setting<>("Crystals", true);
    public static Setting<Boolean> eXp = new Setting<>("EXp", true);
    public static Setting<Boolean> food = new Setting<>("Food", true);
    public static Setting<Boolean> others = new Setting<>("Others", false);
    public ConcurrentHashMap<ItemStack, Integer> Field1962 = new ConcurrentHashMap();
    public int Field1963 = 0;
    public TimerUtil Field1964 = new TimerUtil();

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        int n;
        if (HotbarRefil.mc.player == null || HotbarRefil.mc.world == null || HotbarRefil.mc.currentScreen instanceof GuiContainer || tickEvent.Method324() == net.minecraftforge.fml.common.gameevent.TickEvent.Phase.START) {
            return;
        }
        if (!this.Field1964.GetDifferenceTiming(350.0)) {
            return;
        }
        if (itemSaver.getValue().booleanValue()) {
            n = 0;
            EnumHand[] enumHandArray = EnumHand.values();
            block4: for (int i = 0; i < enumHandArray.length; ++i) {
                EnumHand enumHand = enumHandArray[i];
                ItemStack itemStack = HotbarRefil.mc.player.getHeldItem(enumHand);
                if (itemStack == null || itemStack.getItem() == null) continue;
                Item item = itemStack.getItem();
                if (!itemStack.isItemStackDamageable() || itemStack.getItemDamage() != item.getMaxDamage(itemStack)) continue;
                switch (enumHand) {
                    case MAIN_HAND: {
                        HotbarRefil.mc.playerController.windowClick(HotbarRefil.mc.player.inventoryContainer.windowId, HotbarRefil.mc.player.inventory.currentItem + 36, 0, ClickType.QUICK_MOVE, HotbarRefil.mc.player);
                        n = 1;
                        continue block4;
                    }
                    case OFF_HAND: {
                        HotbarRefil.mc.playerController.windowClick(HotbarRefil.mc.player.inventoryContainer.windowId, 45, 1, ClickType.QUICK_MOVE, HotbarRefil.mc.player);
                        n = 1;
                    }
                }
            }
            if (n != 0) {
                this.Field1963 = 0;
                return;
            }
        }
        if (this.Field1963 > this.delay.getValue() * 2) {
            if (!HotbarRefil.mc.player.inventory.getItemStack().isEmpty()) {
                for (n = 44; n >= 9; --n) {
                    if (!HotbarRefil.mc.player.inventoryContainer.getSlot(n).getStack().isEmpty()) continue;
                    HotbarRefil.mc.playerController.windowClick(0, n, 0, ClickType.PICKUP, HotbarRefil.mc.player);
                    return;
                }
            }
            this.Method124();
            this.Method134();
            this.Field1963 = 0;
        } else {
            ++this.Field1963;
        }
    }

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        block0: {
            if (!(sendPacketEvent.getPacket() instanceof CPacketClickWindow)) break block0;
            this.Field1964.UpdateCurrentTime();
        }
    }

    public boolean Method394() {
        for (int i = 0; i < 36; ++i) {
            if (HotbarRefil.mc.player.inventoryContainer.getSlot(i).getHasStack()) continue;
            return true;
        }
        return false;
    }

    public void Method134() {
        for (Map.Entry<ItemStack, Integer> entry : this.Field1962.entrySet()) {
            ItemStack itemStack = entry.getKey();
            int n = entry.getValue();
            if (HotbarRefil.mc.player.inventory.getSlotFor(itemStack) == -1) continue;
            int n2 = -1;
            for (int i = 9; i <= 35; ++i) {
                ItemStack itemStack2 = HotbarRefil.mc.player.inventory.getStackInSlot(i);
                if (!itemStack2.getItem().equals(itemStack.getItem())) continue;
                if (!itemStack2.getDisplayName().equals(itemStack.getDisplayName()) || itemStack2.getItemDamage() != itemStack.getItemDamage()) continue;
                n2 = i;
                break;
            }
            if (n2 == -1) continue;
            HotbarRefil.mc.playerController.windowClick(0, n2, 0, ClickType.PICKUP, HotbarRefil.mc.player);
            HotbarRefil.mc.playerController.windowClick(0, n < 9 ? n + 36 : n, 0, ClickType.PICKUP, HotbarRefil.mc.player);
            HotbarRefil.mc.playerController.windowClick(0, n2, 0, ClickType.PICKUP, HotbarRefil.mc.player);
            this.Field1962.remove(itemStack);
        }
    }

    public void Method124() {
        for (int i = 0; i <= 9; ++i) {
            ItemStack itemStack;
            if (handOnly.getValue().booleanValue() && i != HotbarRefil.mc.player.inventory.currentItem || (itemStack = HotbarRefil.mc.player.inventory.getStackInSlot(i)).isEmpty() || itemStack.getItem() == Items.AIR || !itemStack.isStackable() || itemStack.getCount() >= itemStack.getMaxStackSize() || itemStack.getCount() >= this.refillThreshold.getValue() || !(others.getValue() != false || itemStack.getItem() instanceof ItemEndCrystal && crystals.getValue() != false || itemStack.getItem() instanceof ItemFood && food.getValue() != false) && (!(itemStack.getItem() instanceof ItemExpBottle) || !eXp.getValue().booleanValue())) continue;
            this.Field1962.put(itemStack, i);
        }
    }

    public HotbarRefil() {
        super("HotbarRefill", "Automatically refills your hotbar", Category.PLAYER);
    }
}