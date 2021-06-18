package me.darki.konas.module.player;

import cookiedragon.eventsystem.Subscriber;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import me.darki.konas.event.events.TickEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.misc.AutoMend;
import me.darki.konas.module.movement.ElytraFly;
import me.darki.konas.setting.Setting;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.unremaped.SendPacketEvent;
import me.darki.konas.unremaped.Class482;
import me.darki.konas.util.TimerUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class AutoArmor
extends Module {
    public static Setting<Boolean> elytraPrio = new Setting<>("ElytraPrio", false);
    public static Setting<Boolean> smart = new Setting<>("Smart", false).visibleIf(elytraPrio::getValue);
    public static Setting<Integer> delay = new Setting<>("Delay", 0, 10, 0, 1);
    public static Setting<Boolean> strict = new Setting<>("Strict", false);
    public static Setting<Boolean> armorSaver = new Setting<>("ArmorSaver", false);
    public static Setting<Boolean> pauseWhenSafe = new Setting<>("PauseWhenSafe", false);
    public static Setting<Float> depletion = new Setting<>("Depletion", Float.valueOf(0.75f), Float.valueOf(0.95f), Float.valueOf(0.5f), Float.valueOf(0.05f)).visibleIf(armorSaver::getValue);
    public static Setting<Boolean> allowMend = new Setting<>("AllowMend", false);
    public TimerUtil Field2041 = new TimerUtil();
    public boolean Field2042;
    public TimerUtil Field2043 = new TimerUtil();

    public static boolean Method1855(Class482 class482) {
        return ((ItemArmor)class482.Field2350.getItem()).armorType.equals(EntityEquipmentSlot.LEGS);
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        block21: {
            boolean bl;
            List object;
            if (tickEvent.Method324() == net.minecraftforge.fml.common.gameevent.TickEvent.Phase.END) {
                return;
            }
            if (AutoArmor.mc.world == null || AutoArmor.mc.player == null) {
                return;
            }
            if (!this.Field2043.GetDifferenceTiming(350.0)) {
                return;
            }
            if (AutoArmor.mc.player.ticksExisted % (delay.getValue() + 1) != 0) {
                return;
            }
            if (strict.getValue().booleanValue() && (AutoArmor.mc.player.motionX != 0.0 || AutoArmor.mc.player.motionZ != 0.0)) {
                return;
            }
            if (pauseWhenSafe.getValue().booleanValue() && (object = AutoArmor.mc.world.loadedEntityList.stream().filter(AutoArmor::Method386).collect(Collectors.toList())).isEmpty()) {
                return;
            }
            if (AutoMend.Field1952 && ModuleManager.Method1612("AutoMend").isEnabled()) {
                return;
            }
            if (allowMend.getValue().booleanValue() && !this.Field2041.GetDifferenceTiming(500.0)) {
                for (int i = 0; i < AutoArmor.mc.player.inventory.armorInventory.size(); ++i) {
                    ItemStack itemStack = AutoArmor.mc.player.inventory.armorInventory.get(i);
                    if (itemStack.getEnchantmentTagList() != null) {
                        boolean bl2 = false;
                        for (Map.Entry entry : EnchantmentHelper.getEnchantments(itemStack).entrySet()) {
                            if (!((Enchantment)entry.getKey()).getName().contains("mending")) continue;
                            bl2 = true;
                            break;
                        }
                        if (!bl2) continue;
                    }
                    if (itemStack.isEmpty()) continue;
                    long l = AutoArmor.mc.player.inventory.mainInventory.stream().filter(AutoArmor::Method938).map(AutoArmor::Method1860).count();
                    if (l <= 0L) {
                        return;
                    }
                    if (itemStack.getItemDamage() == 0) continue;
                    this.Method562(8 - i);
                    return;
                }
                return;
            }
            if (AutoArmor.mc.currentScreen instanceof GuiContainer) {
                return;
            }
            AtomicBoolean b = new AtomicBoolean(false);
            if (this.Field2042) {
                this.Field2042 = false;
                return;
            }
            boolean bl3 = elytraPrio.getValue();
            if (smart.getValue().booleanValue() && !ModuleManager.getModuleByClass(ElytraFly.class).isEnabled()) {
                bl3 = false;
            }
            HashSet<Class482> hashSet = new HashSet<Class482>();
            for (int i = 0; i < 36; ++i) {
                Class482 class482 = new Class482(i, AutoArmor.mc.player.inventory.getStackInSlot(i));
                if (!(class482.Field2350.getItem() instanceof ItemArmor) && !(class482.Field2350.getItem() instanceof ItemElytra)) continue;
                hashSet.add(class482);
            }
            List list = hashSet.stream().filter(AutoArmor::Method1870).filter(AutoArmor::Method1873).sorted(Comparator.comparingInt(AutoArmor::Method1872)).sorted(Comparator.comparingInt(AutoArmor::Method1878)).collect(Collectors.toList());
            boolean bl4 = list.isEmpty();
            if (bl4) {
                list = hashSet.stream().filter(AutoArmor::Method1877).sorted(Comparator.comparingInt(AutoArmor::Method1875)).sorted(Comparator.comparingInt(AutoArmor::Method1866)).collect(Collectors.toList());
            }
            List list2 = hashSet.stream().filter(AutoArmor::Method1869).sorted(Comparator.comparingInt(AutoArmor::Method1867)).collect(Collectors.toList());
            Item item = AutoArmor.mc.player.inventory.getStackInSlot(39).getItem();
            Item item2 = AutoArmor.mc.player.inventory.getStackInSlot(38).getItem();
            Item item3 = AutoArmor.mc.player.inventory.getStackInSlot(37).getItem();
            Item item4 = AutoArmor.mc.player.inventory.getStackInSlot(36).getItem();
            boolean bl5 = item.equals(Items.AIR) || !bl4 && armorSaver.getValue() != false && AutoArmor.mc.player.inventory.getStackInSlot(39).getItem().getDurabilityForDisplay(AutoArmor.mc.player.inventory.getStackInSlot(39)) >= (double) depletion.getValue().floatValue();
            boolean bl6 = item2.equals(Items.AIR) || !bl4 && armorSaver.getValue() != false && AutoArmor.mc.player.inventory.getStackInSlot(38).getItem().getDurabilityForDisplay(AutoArmor.mc.player.inventory.getStackInSlot(38)) >= (double) depletion.getValue().floatValue();
            boolean bl7 = item3.equals(Items.AIR) || !bl4 && armorSaver.getValue() != false && AutoArmor.mc.player.inventory.getStackInSlot(37).getItem().getDurabilityForDisplay(AutoArmor.mc.player.inventory.getStackInSlot(37)) >= (double) depletion.getValue().floatValue();
            boolean bl8 = bl = item4.equals(Items.AIR) || !bl4 && armorSaver.getValue() != false && AutoArmor.mc.player.inventory.getStackInSlot(36).getItem().getDurabilityForDisplay(AutoArmor.mc.player.inventory.getStackInSlot(36)) >= (double) depletion.getValue().floatValue();
            if (bl5 && !((AtomicBoolean)b).get()) {
                list.stream().filter(AutoArmor::Method1864).filter(AutoArmor::Method1871).findFirst().ifPresent(arg_0 -> this.Method1874((AtomicBoolean)b, arg_0));
            }
            if (bl3 && !(item2 instanceof ItemElytra) && list2.size() > 0 && !((AtomicBoolean)b).get()) {
                list2.stream().findFirst().ifPresent(arg_0 -> this.Method1859((AtomicBoolean)b, arg_0));
            }
            if (bl6 || !bl3 && item2.equals(Items.ELYTRA) && !((AtomicBoolean)object).get()) {
                list.stream().filter(AutoArmor::Method1857).filter(AutoArmor::Method1863).findFirst().ifPresent(arg_0 -> this.Method1856((AtomicBoolean)b, arg_0));
            }
            if (bl7 && !((AtomicBoolean)object).get()) {
                list.stream().filter(AutoArmor::Method1876).filter(AutoArmor::Method1855).findFirst().ifPresent(arg_0 -> this.Method1868((AtomicBoolean)b, arg_0));
            }
            if (!bl || ((AtomicBoolean)object).get()) break block21;
            list.stream().filter(AutoArmor::Method1858).filter(AutoArmor::Method1862).findFirst().ifPresent(arg_0 -> this.Method1865((AtomicBoolean)b, arg_0));
        }
    }

    public void Method1856(AtomicBoolean atomicBoolean, Class482 class482) {
        this.Method1248(class482.Field2349, 6);
        atomicBoolean.set(true);
    }

    public static boolean Method1857(Class482 class482) {
        return class482.Field2350.getItem() instanceof ItemArmor;
    }

    public static boolean Method1858(Class482 class482) {
        return class482.Field2350.getItem() instanceof ItemArmor;
    }

    public void Method1859(AtomicBoolean atomicBoolean, Class482 class482) {
        this.Method1248(class482.Field2349, 6);
        atomicBoolean.set(true);
    }

    public static Integer Method1860(ItemStack itemStack) {
        return AutoArmor.mc.player.inventory.getSlotFor(itemStack);
    }

    public static boolean Method938(ItemStack itemStack) {
        return itemStack.isEmpty() || itemStack.getItem() == Items.AIR;
    }

    @Subscriber
    public void Method1861(PlayerInteractEvent.RightClickItem rightClickItem) {
        if (rightClickItem.getEntityPlayer() != AutoArmor.mc.player) {
            return;
        }
        if (rightClickItem.getItemStack().getItem() != Items.EXPERIENCE_BOTTLE) {
            return;
        }
        this.Field2041.UpdateCurrentTime();
    }

    public static boolean Method1862(Class482 class482) {
        return ((ItemArmor)class482.Field2350.getItem()).armorType.equals(EntityEquipmentSlot.FEET);
    }

    public static boolean Method1863(Class482 class482) {
        return ((ItemArmor)class482.Field2350.getItem()).armorType.equals(EntityEquipmentSlot.CHEST);
    }

    public static boolean Method1864(Class482 class482) {
        return class482.Field2350.getItem() instanceof ItemArmor;
    }

    public void Method1865(AtomicBoolean atomicBoolean, Class482 class482) {
        this.Method1248(class482.Field2349, 8);
        atomicBoolean.set(true);
    }

    public static int Method1866(Class482 class482) {
        return ((ItemArmor)class482.Field2350.getItem()).damageReduceAmount;
    }

    public static int Method1867(Class482 class482) {
        return class482.Field2349;
    }

    public static boolean Method386(Entity entity) {
        return entity instanceof EntityPlayer && !entity.equals(AutoArmor.mc.player) && AutoArmor.mc.player.getDistance(entity) <= 6.0f || entity instanceof EntityEnderCrystal && AutoArmor.mc.player.getDistance(entity) <= 12.0f;
    }

    public void Method1868(AtomicBoolean atomicBoolean, Class482 class482) {
        this.Method1248(class482.Field2349, 7);
        atomicBoolean.set(true);
    }

    public static boolean Method1869(Class482 class482) {
        return class482.Field2350.getItem() instanceof ItemElytra;
    }

    public static boolean Method1870(Class482 class482) {
        return class482.Field2350.getItem() instanceof ItemArmor;
    }

    public AutoArmor() {
        super("AutoArmor", "Automatically equips armor", Category.PLAYER);
    }

    public static boolean Method1871(Class482 class482) {
        return ((ItemArmor)class482.Field2350.getItem()).armorType.equals(EntityEquipmentSlot.HEAD);
    }

    public static int Method1872(Class482 class482) {
        return class482.Field2349;
    }

    public static boolean Method1873(Class482 class482) {
        return armorSaver.getValue() == false || class482.Field2350.getItem().getDurabilityForDisplay(class482.Field2350) < (double) depletion.getValue().floatValue();
    }

    public void Method1874(AtomicBoolean atomicBoolean, Class482 class482) {
        this.Method1248(class482.Field2349, 5);
        atomicBoolean.set(true);
    }

    public void Method1248(int n, int n2) {
        AutoArmor.mc.playerController.windowClick(AutoArmor.mc.player.inventoryContainer.windowId, n < 9 ? n + 36 : n, 0, ClickType.PICKUP, AutoArmor.mc.player);
        AutoArmor.mc.playerController.windowClick(AutoArmor.mc.player.inventoryContainer.windowId, n2, 0, ClickType.PICKUP, AutoArmor.mc.player);
        AutoArmor.mc.playerController.windowClick(AutoArmor.mc.player.inventoryContainer.windowId, n < 9 ? n + 36 : n, 0, ClickType.PICKUP, AutoArmor.mc.player);
        this.Field2042 = true;
    }

    public static int Method1875(Class482 class482) {
        return class482.Field2349;
    }

    public static boolean Method1876(Class482 class482) {
        return class482.Field2350.getItem() instanceof ItemArmor;
    }

    public static boolean Method1877(Class482 class482) {
        return class482.Field2350.getItem() instanceof ItemArmor;
    }

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        block0: {
            if (!(sendPacketEvent.getPacket() instanceof CPacketClickWindow)) break block0;
            this.Field2043.UpdateCurrentTime();
        }
    }

    public void Method562(int n) {
        AutoArmor.mc.playerController.windowClick(AutoArmor.mc.player.inventoryContainer.windowId, n, 0, ClickType.QUICK_MOVE, AutoArmor.mc.player);
    }

    public static int Method1878(Class482 class482) {
        return ((ItemArmor)class482.Field2350.getItem()).damageReduceAmount;
    }
}