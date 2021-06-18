package me.darki.konas.module.misc;

import cookiedragon.eventsystem.Subscriber;
import java.util.Comparator;

import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.Class477;
import me.darki.konas.util.rotation.Rotation;
import me.darki.konas.event.events.Event;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumHand;

public class AutoMend
extends Module {
    public Setting<Boolean> lookdown = new Setting<>("Lookdown", true);
    public Setting<Boolean> autoXP = new Setting<>("AutoXP", false);
    public Setting<Boolean> autoSwitch = new Setting<>("AutoSwitch", false);
    public Setting<Boolean> armor = new Setting<>("Armor", false);
    public Setting<Integer> threshold = new Setting<>("Threshold", 60, 100, 0, 1);
    public Setting<Boolean> attackCheck = new Setting<>("AttackCheck", true);
    public Setting<Float> crystalRange = new Setting<>("CrystalRange", 6.0f, 10.0f, 0.0f, 0.1f).visibleIf(this.attackCheck::getValue);
    public boolean Field1951 = false;
    public static boolean Field1952 = false;
    public float Field1953 = 0.0f;

    public static EntityEnderCrystal Method1805(Entity entity) {
        return (EntityEnderCrystal)entity;
    }

    @Override
    public void onDisable() {
        Field1952 = false;
    }

    public AutoMend() {
        super("AutoMend", Category.MISC);
    }

    @Override
    public void onEnable() {
        this.Field1953 = 0.0f;
    }

    public int Method464() {
        ItemStack itemStack = AutoMend.mc.player.getHeldItemMainhand();
        if (!itemStack.isEmpty() && itemStack.getItem() instanceof ItemExpBottle) {
            return AutoMend.mc.player.inventory.currentItem;
        }
        for (int i = 0; i < 9; ++i) {
            itemStack = AutoMend.mc.player.inventory.getStackInSlot(i);
            if (itemStack.isEmpty() || !(itemStack.getItem() instanceof ItemExpBottle)) continue;
            return i;
        }
        return -1;
    }

    @Subscriber(priority=10)
    public void Method948(Event event) {
        block11: {
            block12: {
                ItemStack itemStack;
                int n;
                if (AutoMend.mc.player == null || AutoMend.mc.world == null) {
                    return;
                }
                EntityEnderCrystal entityEnderCrystal = AutoMend.mc.world.loadedEntityList.stream().filter(this::Method513).map(AutoMend::Method1805).min(Comparator.comparing(AutoMend::Method1806)).orElse(null);
                if ((entityEnderCrystal != null || AutoMend.mc.player.getHealth() + AutoMend.mc.player.getAbsorptionAmount() < this.Field1953) && this.attackCheck.getValue().booleanValue()) {
                    Field1952 = false;
                    this.Field1951 = false;
                    this.toggle();
                    return;
                }
                this.Field1953 = AutoMend.mc.player.getHealth() + AutoMend.mc.player.getAbsorptionAmount();
                if (AutoMend.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() != Items.EXPERIENCE_BOTTLE && (!this.autoSwitch.getValue().booleanValue() || this.Method464() == -1)) break block11;
                if (!(event instanceof UpdateEvent)) break block12;
                if (event.isCanceled() || !Rotation.Method1966()) {
                    return;
                }
                this.Field1951 = false;
                if (this.lookdown.getValue().booleanValue()) {
                    KonasGlobals.INSTANCE.Field1139.Method1937(AutoMend.mc.player.rotationYaw, 90.0f);
                }
                if (!this.armor.getValue().booleanValue()) break block11;
                ItemStack[] itemStackArray = new ItemStack[]{AutoMend.mc.player.inventory.getStackInSlot(39), AutoMend.mc.player.inventory.getStackInSlot(38), AutoMend.mc.player.inventory.getStackInSlot(37), AutoMend.mc.player.inventory.getStackInSlot(36)};
                for (n = 0; n < 4; ++n) {
                    itemStack = itemStackArray[n];
                    if (!(itemStack.getItem() instanceof ItemArmor) || Class477.Method2169(itemStack) < (float) this.threshold.getValue().intValue()) continue;
                    for (int i = 0; i < 36; ++i) {
                        ItemStack itemStack2 = AutoMend.mc.player.inventory.getStackInSlot(i);
                        if (!itemStack2.isEmpty() || itemStack2.getItem() != Items.AIR) continue;
                        Field1952 = true;
                        AutoMend.mc.playerController.windowClick(AutoMend.mc.player.inventoryContainer.windowId, n + 5, 0, ClickType.PICKUP, AutoMend.mc.player);
                        AutoMend.mc.playerController.windowClick(AutoMend.mc.player.inventoryContainer.windowId, i < 9 ? i + 36 : i, 0, ClickType.PICKUP, AutoMend.mc.player);
                        AutoMend.mc.playerController.windowClick(AutoMend.mc.player.inventoryContainer.windowId, n + 5, 0, ClickType.PICKUP, AutoMend.mc.player);
                        AutoMend.mc.playerController.updateController();
                        return;
                    }
                }
                for (n = 0; n < 4; ++n) {
                    itemStack = itemStackArray[n];
                    if (!(itemStack.getItem() instanceof ItemArmor) || Class477.Method2169(itemStack) >= (float) this.threshold.getValue().intValue()) continue;
                    this.Field1951 = true;
                }
                if (!this.Field1951) {
                    Field1952 = false;
                    this.toggle();
                }
                break block11;
            }
            if (!this.autoXP.getValue().booleanValue() || this.armor.getValue().booleanValue() && !this.Field1951) break block11;
            int n = this.Method464();
            boolean bl = this.autoSwitch.getValue().booleanValue() && AutoMend.mc.player.inventory.currentItem != n && n != -1;
            int n2 = AutoMend.mc.player.inventory.currentItem;
            if (bl) {
                AutoMend.mc.player.inventory.currentItem = n;
                AutoMend.mc.player.connection.sendPacket(new CPacketHeldItemChange(n));
            }
            if (AutoMend.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemExpBottle) {
                AutoMend.mc.playerController.processRightClick(AutoMend.mc.player, AutoMend.mc.world, EnumHand.MAIN_HAND);
            }
            if (bl) {
                AutoMend.mc.player.inventory.currentItem = n2;
                AutoMend.mc.player.connection.sendPacket(new CPacketHeldItemChange(n2));
            }
        }
    }

    public boolean Method513(Entity entity) {
        return entity instanceof EntityEnderCrystal && AutoMend.mc.player.getDistance(entity) <= this.crystalRange.getValue().floatValue();
    }

    public static Float Method1806(EntityEnderCrystal entityEnderCrystal) {
        return Float.valueOf(AutoMend.mc.player.getDistance(entityEnderCrystal));
    }
}