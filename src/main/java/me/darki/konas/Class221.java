package me.darki.konas;

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
import me.darki.konas.module.combat.AutoCrystal;
import me.darki.konas.setting.Setting;
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

public class Class221
extends Module {
    public Setting<Boolean> Field232 = new Setting<>("Totem", true).Method1177(315, 1000);
    public Setting<Boolean> Field233 = new Setting<>("Gapple", true);
    public Setting<Boolean> Field234 = new Setting<>("Crystal", true);
    public Setting<Float> Field235 = new Setting<>("Delay", Float.valueOf(0.0f), Float.valueOf(5.0f), Float.valueOf(0.0f), Float.valueOf(0.05f));
    public Setting<Boolean> Field236 = new Setting<>("HotbarTotem", false);
    public Setting<Float> Field237 = new Setting<>("TotemHealth", Float.valueOf(5.0f), Float.valueOf(36.0f), Float.valueOf(0.0f), Float.valueOf(0.5f));
    public Setting<Boolean> Field238 = new Setting<>("RightClickGap", true).Method1191(this::Method396);
    public Setting<Class333> Field239 = new Setting<>("CrystalCheck", Class333.DAMAGE);
    public Setting<Float> Field240 = new Setting<>("CrystalRange", Float.valueOf(10.0f), Float.valueOf(15.0f), Float.valueOf(1.0f), Float.valueOf(1.0f)).Method1191(this::Method388);
    public Setting<Boolean> Field241 = new Setting<>("FallCheck", true);
    public Setting<Float> Field242 = new Setting<>("FallDist", Float.valueOf(15.0f), Float.valueOf(50.0f), Float.valueOf(0.0f), Float.valueOf(1.0f)).Method1191(this::Method393);
    public Setting<Boolean> Field243 = new Setting<>("TotemOnElytra", true);
    public Setting<Boolean> Field244 = new Setting<>("ExtraSafe", false);
    public Setting<Boolean> Field245 = new Setting<>("ClearAfter", true);
    public Setting<Boolean> Field246 = new Setting<>("Hard", false);
    public Setting<Boolean> Field247 = new Setting<>("NotFromHotbar", true);
    public Setting<Class326> Field248 = new Setting<>("DefaultItem", Class326.TOTEM);
    public Queue<Integer> Field249 = new LinkedList<Integer>();
    public Class566 Field250 = new Class566();

    public boolean Method383(Item item) {
        return item == Items.DIAMOND_SWORD || item == Items.IRON_SWORD || item == Items.GOLDEN_SWORD || item == Items.STONE_SWORD || item == Items.WOODEN_SWORD;
    }

    public static boolean Method384(Entity entity) {
        return entity instanceof EntityEnderCrystal;
    }

    public float Method385(BlockPos blockPos) {
        List list = Class221.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(blockPos)).stream().filter(Class221::Method384).collect(Collectors.toList());
        float f = 0.0f;
        for (Entity entity : list) {
            f += Class475.Method2150(entity.posX, entity.posY, entity.posZ, Class221.mc.player);
        }
        return f;
    }

    public boolean Method386(Entity entity) {
        return entity instanceof EntityEnderCrystal && Class221.mc.player.getDistance(entity) <= this.Field240.getValue().floatValue();
    }

    public void Method387(Item item) {
        if (Class221.mc.player.getHeldItemOffhand().getItem() == item) {
            return;
        }
        int n = this.Method391(item);
        if (this.Field236.getValue().booleanValue() && item == Items.TOTEM_OF_UNDYING) {
            for (int i = 0; i < 9; ++i) {
                ItemStack itemStack = Class221.mc.player.inventory.mainInventory.get(i);
                if (itemStack.getItem() != Items.TOTEM_OF_UNDYING) continue;
                if (Class221.mc.player.inventory.currentItem != i) {
                    Class221.mc.player.inventory.currentItem = i;
                }
                return;
            }
        }
        if (n != -1) {
            if (this.Field235.getValue().floatValue() > 0.0f) {
                if (this.Field250.Method737(this.Field235.getValue().floatValue() * 100.0f)) {
                    Class221.mc.playerController.windowClick(Class221.mc.player.inventoryContainer.windowId, n < 9 ? n + 36 : n, 0, ClickType.PICKUP, Class221.mc.player);
                    this.Field250.Method739();
                } else {
                    this.Field249.add(n < 9 ? n + 36 : n);
                }
                this.Field249.add(45);
                this.Field249.add(n < 9 ? n + 36 : n);
            } else {
                this.Field250.Method739();
                Class221.mc.playerController.windowClick(Class221.mc.player.inventoryContainer.windowId, n < 9 ? n + 36 : n, 0, ClickType.PICKUP, Class221.mc.player);
                PlayerControllerMP playerControllerMP = Class221.mc.playerController;
                int n2 = Class221.mc.player.inventoryContainer.windowId;
                int n3 = 45;
                int n4 = 0;
                ClickType clickType = ClickType.PICKUP;
                EntityPlayerSP entityPlayerSP = Class221.mc.player;
                playerControllerMP.windowClick(n2, n3, n4, clickType, entityPlayerSP);
                try {
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                Class221.mc.playerController.windowClick(Class221.mc.player.inventoryContainer.windowId, n < 9 ? n + 36 : n, 0, ClickType.PICKUP, Class221.mc.player);
            }
        }
    }

    public boolean Method388() {
        return this.Field239.getValue() != Class333.NONE;
    }

    public Class221() {
        super("Offhand", Category.COMBAT, "OffhandCrystal", "AutoTotem", "OffhandGapple", "AutoOffhand", "SmartOffhand");
    }

    public static Float Method389(Entity entity) {
        return Float.valueOf(Class221.mc.player.getDistance(entity));
    }

    @Subscriber
    public void Method390(Class28 class28) {
        block25: {
            block26: {
                if (Class221.mc.player == null || Class221.mc.world == null) {
                    return;
                }
                if (Class221.mc.currentScreen instanceof GuiContainer || Class221.mc.currentScreen instanceof GuiInventory) break block25;
                if (this.Field249.isEmpty()) break block26;
                if (!this.Field250.Method737(this.Field235.getValue().floatValue() * 100.0f)) {
                    return;
                }
                int n = this.Field249.poll();
                Class566 class566 = this.Field250;
                class566.Method739();
                PlayerControllerMP playerControllerMP = Class221.mc.playerController;
                int n2 = Class221.mc.player.inventoryContainer.windowId;
                int n3 = n;
                int n4 = 0;
                ClickType clickType = ClickType.PICKUP;
                EntityPlayerSP entityPlayerSP = Class221.mc.player;
                playerControllerMP.windowClick(n2, n3, n4, clickType, entityPlayerSP);
                try {
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break block25;
            }
            if (!Class221.mc.player.inventory.getItemStack().isEmpty()) {
                for (int i = 44; i >= 9; --i) {
                    if (!Class221.mc.player.inventoryContainer.getSlot(i).getStack().isEmpty()) continue;
                    Class221.mc.playerController.windowClick(0, i, 0, ClickType.PICKUP, Class221.mc.player);
                    return;
                }
            }
            if (this.Field232.getValue().booleanValue()) {
                if (Class221.mc.player.getHealth() + Class221.mc.player.getAbsorptionAmount() <= this.Field237.getValue().floatValue() || this.Field243.getValue() != false && Class221.mc.player.isElytraFlying() || this.Field241.getValue().booleanValue() && Class221.mc.player.fallDistance >= this.Field242.getValue().floatValue() && !Class221.mc.player.isElytraFlying()) {
                    this.Method387(Items.TOTEM_OF_UNDYING);
                    return;
                }
                if (this.Field239.getValue() == Class333.RANGE) {
                    EntityEnderCrystal entityEnderCrystal = Class221.mc.world.loadedEntityList.stream().filter(this::Method386).min(Comparator.comparing(Class221::Method389)).orElse(null);
                    if (entityEnderCrystal != null) {
                        this.Method387(Items.TOTEM_OF_UNDYING);
                        return;
                    }
                } else if (this.Field239.getValue() == Class333.DAMAGE) {
                    float f = 0.0f;
                    List list = Class221.mc.world.loadedEntityList.stream().filter(Class221::Method392).filter(this::Method395).collect(Collectors.toList());
                    for (Entity entity : list) {
                        f += Class475.Method2156((EntityEnderCrystal)entity, Class221.mc.player);
                    }
                    if (Class221.mc.player.getHealth() + Class221.mc.player.getAbsorptionAmount() - f <= this.Field237.getValue().floatValue()) {
                        this.Method387(Items.TOTEM_OF_UNDYING);
                        return;
                    }
                }
                if (this.Field244.getValue().booleanValue() && this.Method394()) {
                    this.Method387(Items.TOTEM_OF_UNDYING);
                    return;
                }
            }
            if (this.Field233.getValue().booleanValue() && this.Method383(Class221.mc.player.getHeldItemMainhand().getItem())) {
                if (this.Field238.getValue().booleanValue() && !Class221.mc.gameSettings.keyBindUseItem.isKeyDown()) {
                    if (this.Field245.getValue().booleanValue()) {
                        this.Method387(this.Field248.getValue().Field559);
                    }
                    return;
                }
                this.Method387(Items.GOLDEN_APPLE);
                return;
            }
            if (this.Field234.getValue().booleanValue()) {
                if (Class167.Method1610(AutoCrystal.class).isEnabled()) {
                    this.Method387(Items.END_CRYSTAL);
                    return;
                }
                if (this.Field245.getValue().booleanValue()) {
                    this.Method387(this.Field248.getValue().Field559);
                    return;
                }
            }
            if (!this.Field246.getValue().booleanValue()) break block25;
            this.Method387(this.Field248.getValue().Field559);
        }
    }

    public int Method391(Item item) {
        int n;
        int n2 = -1;
        int n3 = n = this.Field247.getValue() != false ? 9 : 0;
        while (n < 36) {
            ItemStack itemStack = Class221.mc.player.inventory.getStackInSlot(n);
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
        return this.Field241.getValue();
    }

    public boolean Method394() {
        float f = 0.0f;
        ArrayList<Float> arrayList = new ArrayList<Float>();
        arrayList.add(Float.valueOf(this.Method385(Class221.mc.player.getPosition().add(1, 0, 0))));
        arrayList.add(Float.valueOf(this.Method385(Class221.mc.player.getPosition().add(-1, 0, 0))));
        arrayList.add(Float.valueOf(this.Method385(Class221.mc.player.getPosition().add(0, 0, 1))));
        arrayList.add(Float.valueOf(this.Method385(Class221.mc.player.getPosition().add(0, 0, -1))));
        arrayList.add(Float.valueOf(this.Method385(Class221.mc.player.getPosition())));
        Iterator iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            float f2 = ((Float)iterator.next()).floatValue();
            f += f2;
            if (!(Class221.mc.player.getHealth() + Class221.mc.player.getAbsorptionAmount() - f2 <= this.Field237.getValue().floatValue())) continue;
            return true;
        }
        return Class221.mc.player.getHealth() + Class221.mc.player.getAbsorptionAmount() - f <= this.Field237.getValue().floatValue();
    }

    public boolean Method395(Entity entity) {
        return Class221.mc.player.getDistance(entity) <= this.Field240.getValue().floatValue();
    }

    @Override
    public boolean Method396() {
        return this.Field233.getValue();
    }
}
