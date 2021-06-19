package me.darki.konas.module.combat;

import cookiedragon.eventsystem.Subscriber;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.Class28;
import me.darki.konas.unremaped.Class326;
import me.darki.konas.unremaped.Class333;
import me.darki.konas.util.CrystalUtils;
import me.darki.konas.util.TimerUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class Offhand
extends Module {
    public Setting<Boolean> totem = new Setting<>("Totem", true).Method1177(315, 1000);
    public Setting<Boolean> gapple = new Setting<>("Gapple", true);
    public Setting<Boolean> crystal = new Setting<>("Crystal", true);
    public Setting<Float> delay = new Setting<>("Delay", 0.0f, 5.0f, 0.0f, 0.05f);
    public Setting<Boolean> hotbarTotem = new Setting<>("HotbarTotem", false);
    public Setting<Float> totemHealth = new Setting<>("TotemHealth", 5.0f, 36.0f, 0.0f, 0.5f);
    public Setting<Boolean> rightClickGap = new Setting<>("RightClickGap", true).visibleIf(this::Method396);
    public Setting<Class333> crystalCheck = new Setting<>("CrystalCheck", Class333.DAMAGE);
    public Setting<Float> crystalRange = new Setting<>("CrystalRange", 10.0f, 15.0f, 1.0f, Float.valueOf(1.0f)).visibleIf(this::Method388);
    public Setting<Boolean> fallCheck = new Setting<>("FallCheck", true);
    public Setting<Float> fallDist = new Setting<>("FallDist", 15.0f, 50.0f, 0.0f, 1.0f).visibleIf(this::Method393);
    public Setting<Boolean> totemOnElytra = new Setting<>("TotemOnElytra", true);
    public Setting<Boolean> extraSafe = new Setting<>("ExtraSafe", false);
    public Setting<Boolean> clearAfter = new Setting<>("ClearAfter", true);
    public Setting<Boolean> hard = new Setting<>("Hard", false);
    public Setting<Boolean> notFromHotbar = new Setting<>("NotFromHotbar", true);
    public Setting<Class326> defaultItem = new Setting<>("DefaultItem", Class326.TOTEM);
    public Queue<Integer> Field249 = new LinkedList<Integer>();
    public TimerUtil Field250 = new TimerUtil();

    public boolean Method383(Item item) {
        return item == Items.DIAMOND_SWORD || item == Items.IRON_SWORD || item == Items.GOLDEN_SWORD || item == Items.STONE_SWORD || item == Items.WOODEN_SWORD;
    }

    public static boolean Method384(Entity entity) {
        return entity instanceof EntityEnderCrystal;
    }

    public float Method385(BlockPos blockPos) {
        List<Entity> list = Offhand.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(blockPos)).stream().filter(Offhand::Method384).collect(Collectors.toList());
        float f = 0.0f;
        for (Entity entity : list) {
            f += CrystalUtils.Method2150(entity.posX, entity.posY, entity.posZ, (Entity) Offhand.mc.player);
        }
        return f;
    }

    public boolean Method386(Entity entity) {
        return entity instanceof EntityEnderCrystal && Offhand.mc.player.getDistance(entity) <= ((Float)this.crystalRange.getValue()).floatValue();
    }

    public void Method387(Item item) {
        if (Offhand.mc.player.getHeldItemOffhand().getItem() == item) {
            return;
        }
        int n = this.Method391(item);
        if (((Boolean)this.hotbarTotem.getValue()).booleanValue() && item == Items.TOTEM_OF_UNDYING) {
            for (int i = 0; i < 9; ++i) {
                ItemStack itemStack = (ItemStack) Offhand.mc.player.inventory.mainInventory.get(i);
                if (itemStack.getItem() != Items.TOTEM_OF_UNDYING) continue;
                if (Offhand.mc.player.inventory.currentItem != i) {
                    Offhand.mc.player.inventory.currentItem = i;
                }
                return;
            }
        }
        if (n != -1) {
            if (((Float)this.delay.getValue()).floatValue() > 0.0f) {
                if (this.Field250.GetDifferenceTiming(((Float)this.delay.getValue()).floatValue() * 100.0f)) {
                    Offhand.mc.playerController.windowClick(Offhand.mc.player.inventoryContainer.windowId, n < 9 ? n + 36 : n, 0, ClickType.PICKUP, (EntityPlayer) Offhand.mc.player);
                    this.Field250.UpdateCurrentTime();
                } else {
                    this.Field249.add(n < 9 ? n + 36 : n);
                }
                this.Field249.add(45);
                this.Field249.add(n < 9 ? n + 36 : n);
            } else {
                this.Field250.UpdateCurrentTime();
                Offhand.mc.playerController.windowClick(Offhand.mc.player.inventoryContainer.windowId, n < 9 ? n + 36 : n, 0, ClickType.PICKUP, (EntityPlayer) Offhand.mc.player);
                PlayerControllerMP playerControllerMP = Offhand.mc.playerController;
                int n2 = Offhand.mc.player.inventoryContainer.windowId;
                int n3 = 45;
                int n4 = 0;
                ClickType clickType = ClickType.PICKUP;
                EntityPlayerSP entityPlayerSP = Offhand.mc.player;
                playerControllerMP.windowClick(n2, n3, n4, clickType, (EntityPlayer)entityPlayerSP);
                try {
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                Offhand.mc.playerController.windowClick(Offhand.mc.player.inventoryContainer.windowId, n < 9 ? n + 36 : n, 0, ClickType.PICKUP, (EntityPlayer) Offhand.mc.player);
            }
        }
    }

    public boolean Method388() {
        return this.crystalCheck.getValue() != Class333.NONE;
    }

    public Offhand() {
        super("Offhand", Category.COMBAT, "OffhandCrystal", "AutoTotem", "OffhandGapple", "AutoOffhand", "SmartOffhand");
    }

    public static Float Method389(Entity entity) {
        return Float.valueOf(Offhand.mc.player.getDistance(entity));
    }

    @Subscriber
    public void Method390(Class28 class28) {
        block25: {
            block26: {
                if (Offhand.mc.player == null || Offhand.mc.world == null) {
                    return;
                }
                if (Offhand.mc.currentScreen instanceof GuiContainer || Offhand.mc.currentScreen instanceof GuiInventory) break block25;
                if (this.Field249.isEmpty()) break block26;
                if (!this.Field250.GetDifferenceTiming(((Float)this.delay.getValue()).floatValue() * 100.0f)) {
                    return;
                }
                int n = this.Field249.poll();
                TimerUtil timerUtil = this.Field250;
                timerUtil.UpdateCurrentTime();
                PlayerControllerMP playerControllerMP = Offhand.mc.playerController;
                int n2 = Offhand.mc.player.inventoryContainer.windowId;
                int n3 = n;
                int n4 = 0;
                ClickType clickType = ClickType.PICKUP;
                EntityPlayerSP entityPlayerSP = Offhand.mc.player;
                playerControllerMP.windowClick(n2, n3, n4, clickType, (EntityPlayer)entityPlayerSP);
                try {
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break block25;
            }
            if (!Offhand.mc.player.inventory.getItemStack().isEmpty()) {
                for (int i = 44; i >= 9; --i) {
                    if (!Offhand.mc.player.inventoryContainer.getSlot(i).getStack().isEmpty()) continue;
                    Offhand.mc.playerController.windowClick(0, i, 0, ClickType.PICKUP, (EntityPlayer) Offhand.mc.player);
                    return;
                }
            }
            if (((Boolean)this.totem.getValue()).booleanValue()) {
                if (Offhand.mc.player.getHealth() + Offhand.mc.player.getAbsorptionAmount() <= ((Float)this.totemHealth.getValue()).floatValue() || (Boolean)this.totemOnElytra.getValue() != false && Offhand.mc.player.isElytraFlying() || ((Boolean)this.fallCheck.getValue()).booleanValue() && Offhand.mc.player.fallDistance >= ((Float)this.fallDist.getValue()).floatValue() && !Offhand.mc.player.isElytraFlying()) {
                    this.Method387(Items.TOTEM_OF_UNDYING);
                    return;
                }
                if (this.crystalCheck.getValue() == Class333.RANGE) {
                    EntityEnderCrystal entityEnderCrystal = (EntityEnderCrystal) Offhand.mc.world.loadedEntityList.stream().filter(this::Method386).min(Comparator.comparing(Offhand::Method389)).orElse(null);
                    if (entityEnderCrystal != null) {
                        this.Method387(Items.TOTEM_OF_UNDYING);
                        return;
                    }
                } else if (this.crystalCheck.getValue() == Class333.DAMAGE) {
                    float f = 0.0f;
                    List<Entity> list = Offhand.mc.world.loadedEntityList.stream().filter(Offhand::Method392).filter(this::Method395).collect(Collectors.toList());
                    for (Object entity : list) {
                        f += CrystalUtils.CalculateDamageEndCrystal((EntityEnderCrystal)entity, (Entity) Offhand.mc.player);
                    }
                    if (Offhand.mc.player.getHealth() + Offhand.mc.player.getAbsorptionAmount() - f <= ((Float)this.totemHealth.getValue()).floatValue()) {
                        this.Method387(Items.TOTEM_OF_UNDYING);
                        return;
                    }
                }
                if (((Boolean)this.extraSafe.getValue()).booleanValue() && this.Method394()) {
                    this.Method387(Items.TOTEM_OF_UNDYING);
                    return;
                }
            }
            if (((Boolean)this.gapple.getValue()).booleanValue() && this.Method383(Offhand.mc.player.getHeldItemMainhand().getItem())) {
                if (((Boolean)this.rightClickGap.getValue()).booleanValue() && !Offhand.mc.gameSettings.keyBindUseItem.isKeyDown()) {
                    if (((Boolean)this.clearAfter.getValue()).booleanValue()) {
                        this.Method387(((Class326)this.defaultItem.getValue()).Field559);
                    }
                    return;
                }
                this.Method387(Items.GOLDEN_APPLE);
                return;
            }
            if (((Boolean)this.crystal.getValue()).booleanValue()) {
                if (ModuleManager.getModuleByClass(AutoCrystal.class).isEnabled()) {
                    this.Method387(Items.END_CRYSTAL);
                    return;
                }
                if (((Boolean)this.clearAfter.getValue()).booleanValue()) {
                    this.Method387(((Class326)this.defaultItem.getValue()).Field559);
                    return;
                }
            }
            if (!((Boolean)this.hard.getValue()).booleanValue()) break block25;
            this.Method387(((Class326)this.defaultItem.getValue()).Field559);
        }
    }

    public int Method391(Item item) {
        int n;
        int n2 = -1;
        int n3 = n = (Boolean)this.notFromHotbar.getValue() != false ? 9 : 0;
        while (n < 36) {
            ItemStack itemStack = Offhand.mc.player.inventory.getStackInSlot(n);
            if (itemStack != null && itemStack.getItem() == item) {
                n2 = n;
                break;
            }
            ++n;
        }
        return n2;
    }

    public static boolean Method392(Entity entity) {
        return entity instanceof EntityEnderCrystal;
    }

    public boolean Method393() {
        return (Boolean)this.fallCheck.getValue();
    }

    public boolean Method394() {
        float f = 0.0f;
        ArrayList<Float> arrayList = new ArrayList<Float>();
        arrayList.add(Float.valueOf(this.Method385(Offhand.mc.player.getPosition().add(1, 0, 0))));
        arrayList.add(Float.valueOf(this.Method385(Offhand.mc.player.getPosition().add(-1, 0, 0))));
        arrayList.add(Float.valueOf(this.Method385(Offhand.mc.player.getPosition().add(0, 0, 1))));
        arrayList.add(Float.valueOf(this.Method385(Offhand.mc.player.getPosition().add(0, 0, -1))));
        arrayList.add(Float.valueOf(this.Method385(Offhand.mc.player.getPosition())));
        Iterator iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            float f2 = ((Float)iterator.next()).floatValue();
            f += f2;
            if (!(Offhand.mc.player.getHealth() + Offhand.mc.player.getAbsorptionAmount() - f2 <= ((Float)this.totemHealth.getValue()).floatValue())) continue;
            return true;
        }
        return Offhand.mc.player.getHealth() + Offhand.mc.player.getAbsorptionAmount() - f <= ((Float)this.totemHealth.getValue()).floatValue();
    }

    public boolean Method395(Entity entity) {
        return Offhand.mc.player.getDistance(entity) <= ((Float)this.crystalRange.getValue()).floatValue();
    }

    //@Override
    public boolean Method396() {
        return (Boolean)this.gapple.getValue();
    }
}