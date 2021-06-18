package me.darki.konas.module.misc;

import cookiedragon.eventsystem.Subscriber;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import me.darki.konas.command.Logger;
import me.darki.konas.mixin.mixins.IGuiBrewingStand;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.*;
import me.darki.konas.util.PlayerUtil;
import me.darki.konas.util.PotionUtil;
import me.darki.konas.util.TimerUtil;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.client.gui.inventory.GuiBrewingStand;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class AutoBrew
extends Module {
    public static Setting<PotionUtil> type = new Setting<>("Type", PotionUtil.StrengthII);
    public static Setting<Class388> mod = new Setting<>("Mod", Class388.NONE);
    public static Setting<Boolean> autoOpen = new Setting<>("AutoOpen", true);
    public static Setting<Boolean> autoClose = new Setting<>("AutoClose", false);
    public static Setting<Boolean> autoPlace = new Setting<>("AutoPlace", false);
    public int Field2197;
    public boolean Field2198;
    public int Field2199;
    public TimerUtil Field2200 = new TimerUtil();
    public boolean Field2201 = false;
    public boolean Field2202 = false;

    @Subscriber
    public void Method130(Class19 class19) {
        if (AutoBrew.mc.player == null && AutoBrew.mc.world == null) {
            this.toggle();
            return;
        }
        if (AutoBrew.mc.currentScreen instanceof GuiBrewingStand && AutoBrew.mc.player.openContainer instanceof ContainerBrewingStand) {
            GuiBrewingStand guiBrewingStand;
            ++this.Field2199;
            if (!this.Field2198) {
                this.Field2198 = true;
                this.Field2197 = -2;
                this.Field2199 = 0;
            }
            if (((IGuiBrewingStand)(guiBrewingStand = (GuiBrewingStand) AutoBrew.mc.currentScreen)).getTileBrewingStand().getField(0) != 0 || this.Field2199 < 5) {
                return;
            }
            if (this.Field2197 == -2) {
                for (int i = 0; i < 3; ++i) {
                    AutoBrew.mc.playerController.windowClick(AutoBrew.mc.player.openContainer.windowId, i, 0, ClickType.QUICK_MOVE, AutoBrew.mc.player);
                    if (((IGuiBrewingStand)guiBrewingStand).getTileBrewingStand().getStackInSlot(i).isEmpty()) continue;
                    this.toggle();
                    return;
                }
                ++this.Field2197;
                this.Field2199 = 0;
            } else if (this.Field2197 == -1) {
                for (int i = 0; i < 3; ++i) {
                    int n = -1;
                    for (int j = 5; j < AutoBrew.mc.player.openContainer.getInventory().size(); ++j) {
                        if (!(AutoBrew.mc.player.openContainer.getInventory().get(j).getItem() instanceof ItemPotion) || !PotionUtils.getPotionFromItem(AutoBrew.mc.player.openContainer.getInventory().get(j)).getNamePrefixed("").equalsIgnoreCase("water")) continue;
                        n = j;
                        break;
                    }
                    if (n == -1) {
                        Logger.Method1118("No water bottles found!");
                        this.toggle();
                        return;
                    }
                    AutoBrew.mc.playerController.windowClick(AutoBrew.mc.player.openContainer.windowId, n, 0, ClickType.PICKUP, AutoBrew.mc.player);
                    AutoBrew.mc.playerController.windowClick(AutoBrew.mc.player.openContainer.windowId, i, 0, ClickType.PICKUP, AutoBrew.mc.player);
                }
                ++this.Field2197;
                this.Field2199 = 0;
            } else if (this.Field2197 < AutoBrew.type.getValue().Field2031.length) {
                if (AutoBrew.mc.player.openContainer.getInventory().get(4).isEmpty()) {
                    int n = -1;
                    for (int i = 5; i < AutoBrew.mc.player.openContainer.getInventory().size(); ++i) {
                        if (AutoBrew.mc.player.openContainer.getInventory().get(i).getItem() != Items.BLAZE_POWDER) continue;
                        n = i;
                        break;
                    }
                    if (n == -1) {
                        Logger.Method1118("No blaze powder found!");
                        this.toggle();
                        return;
                    }
                    AutoBrew.mc.playerController.windowClick(AutoBrew.mc.player.openContainer.windowId, n, 0, ClickType.PICKUP, AutoBrew.mc.player);
                    AutoBrew.mc.playerController.windowClick(AutoBrew.mc.player.openContainer.windowId, 4, 0, ClickType.PICKUP, AutoBrew.mc.player);
                    this.Field2199 = 0;
                    return;
                }
                Item item = AutoBrew.type.getValue().Field2031[this.Field2197];
                int n = -69;
                for (int i = 5; i < AutoBrew.mc.player.openContainer.getInventory().size(); ++i) {
                    if (!AutoBrew.mc.player.openContainer.getInventory().get(i).getItem().equals(item)) continue;
                    n = i;
                    break;
                }
                if (n == -69) {
                    Logger.Method1118("You don't have ingredients left");
                    this.toggle();
                    return;
                }
                AutoBrew.mc.playerController.windowClick(AutoBrew.mc.player.openContainer.windowId, n, 0, ClickType.PICKUP, AutoBrew.mc.player);
                AutoBrew.mc.playerController.windowClick(AutoBrew.mc.player.openContainer.windowId, 3, 1, ClickType.PICKUP, AutoBrew.mc.player);
                AutoBrew.mc.playerController.windowClick(AutoBrew.mc.player.openContainer.windowId, n, 0, ClickType.PICKUP, AutoBrew.mc.player);
                ++this.Field2197;
                this.Field2199 = 0;
            } else if (this.Field2197 == AutoBrew.type.getValue().Field2031.length) {
                if (mod.getValue() != Class388.NONE) {
                    Item item = mod.getValue() == Class388.SPLASH ? Items.GUNPOWDER : Items.DRAGON_BREATH;
                    int n = -1;
                    for (int i = 5; i < AutoBrew.mc.player.openContainer.getInventory().size(); ++i) {
                        if (AutoBrew.mc.player.openContainer.getInventory().get(i).getItem() != item) continue;
                        n = i;
                        break;
                    }
                    if (n == -1) {
                        Logger.Method1118("You don't have your modifier");
                        this.toggle();
                        return;
                    }
                    AutoBrew.mc.playerController.windowClick(AutoBrew.mc.player.openContainer.windowId, n, 0, ClickType.PICKUP, AutoBrew.mc.player);
                    AutoBrew.mc.playerController.windowClick(AutoBrew.mc.player.openContainer.windowId, 3, 1, ClickType.PICKUP, AutoBrew.mc.player);
                    AutoBrew.mc.playerController.windowClick(AutoBrew.mc.player.openContainer.windowId, n, 0, ClickType.PICKUP, AutoBrew.mc.player);
                }
                ++this.Field2197;
                this.Field2199 = 0;
            } else {
                this.Field2197 = -1;
                this.Field2199 = 0;
            }
        } else {
            this.Field2198 = false;
            if (autoOpen.getValue().booleanValue() && !this.Field2201 && this.Field2200.GetDifferenceTiming(150.0)) {
                this.Field2200.UpdateCurrentTime();
                List<BlockPos> list = Class545.Method1003(AutoBrew.mc.player.getPosition(), 4);
                BlockPos blockPos = list.stream().filter(AutoBrew::Method515).min(Comparator.comparing(AutoBrew::Method127)).orElse(null);
                if (blockPos != null) {
                    PlayerUtil.Method1081(new Vec3d(blockPos.getX(), (double)blockPos.getY() + 0.5, blockPos.getZ()));
                    AutoBrew.mc.playerController.processRightClickBlock(AutoBrew.mc.player, AutoBrew.mc.world, blockPos, EnumFacing.UP, new Vec3d(blockPos.getX(), (double)blockPos.getY() + 0.5, blockPos.getZ()), EnumHand.MAIN_HAND);
                    AutoBrew.mc.player.swingArm(EnumHand.MAIN_HAND);
                    this.Field2201 = true;
                } else if (autoPlace.getValue().booleanValue() && !this.Field2202) {
                    int n = this.Method464();
                    if (n == -1) {
                        Logger.Method1118("You don't have a brewing stand");
                        this.toggle();
                        return;
                    }
                    for (BlockPos blockPos2 : list) {
                        Optional<Class534> optional = Class545.Method997(blockPos2);
                        if (!optional.isPresent()) continue;
                        AutoBrew.mc.player.inventory.currentItem = n;
                        AutoBrew.mc.playerController.updateController();
                        BlockPos blockPos3 = optional.get().Field1089;
                        EnumFacing enumFacing = optional.get().Field1090;
                        double[] dArray = Class545.Method1008(blockPos3.getX(), blockPos3.getY(), blockPos3.getZ(), enumFacing, AutoBrew.mc.player);
                        AutoBrew.mc.player.connection.sendPacket(new CPacketPlayer.Rotation((float)dArray[0], (float)dArray[1], AutoBrew.mc.player.onGround));
                        boolean bl = AutoBrew.mc.player.isSprinting();
                        boolean bl2 = Class545.Method1004(blockPos3);
                        if (bl) {
                            AutoBrew.mc.player.connection.sendPacket(new CPacketEntityAction(AutoBrew.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                        }
                        if (bl2) {
                            AutoBrew.mc.player.connection.sendPacket(new CPacketEntityAction(AutoBrew.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                        }
                        AutoBrew.mc.playerController.processRightClickBlock(AutoBrew.mc.player, AutoBrew.mc.world, blockPos3, enumFacing, new Vec3d(blockPos3).addVector(0.5, 0.5, 0.5).add(new Vec3d(enumFacing.getDirectionVec()).scale(0.5)), EnumHand.MAIN_HAND);
                        AutoBrew.mc.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                        if (bl2) {
                            AutoBrew.mc.player.connection.sendPacket(new CPacketEntityAction(AutoBrew.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                        }
                        if (bl) {
                            AutoBrew.mc.player.connection.sendPacket(new CPacketEntityAction(AutoBrew.mc.player, CPacketEntityAction.Action.START_SPRINTING));
                        }
                        this.Field2202 = true;
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void onDisable() {
        block0: {
            if (AutoBrew.mc.player == null || AutoBrew.mc.world == null || !autoClose.getValue().booleanValue() || !(AutoBrew.mc.currentScreen instanceof GuiBrewingStand)) break block0;
            AutoBrew.mc.player.closeScreen();
        }
    }

    @Override
    public void onEnable() {
        this.Field2198 = false;
        this.Field2201 = false;
        this.Field2202 = false;
    }

    public AutoBrew() {
        super("AutoBrew", Category.MISC, new String[0]);
    }

    public int Method464() {
        ItemStack itemStack = AutoBrew.mc.player.getHeldItemMainhand();
        if (!itemStack.isEmpty() && itemStack.getItem() == Items.BREWING_STAND) {
            return AutoBrew.mc.player.inventory.currentItem;
        }
        for (int i = 0; i < 9; ++i) {
            itemStack = AutoBrew.mc.player.inventory.getStackInSlot(i);
            if (itemStack.isEmpty() || itemStack.getItem() != Items.BREWING_STAND) continue;
            return i;
        }
        return -1;
    }

    public static boolean Method515(BlockPos blockPos) {
        return AutoBrew.mc.world.getBlockState(blockPos).getBlock() instanceof BlockBrewingStand;
    }

    public static Double Method127(BlockPos blockPos) {
        return AutoBrew.mc.player.getDistanceSq(blockPos);
    }
}