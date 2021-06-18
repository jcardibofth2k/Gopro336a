package me.darki.konas.module.misc;

import cookiedragon.eventsystem.Subscriber;
import java.util.Arrays;
import java.util.List;

import me.darki.konas.event.events.Event;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.unremaped.*;
import me.darki.konas.util.PlayerUtil;
import me.darki.konas.event.events.MoveEvent;
import me.darki.konas.util.RotationUtil;
import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.setting.Setting;
import me.darki.konas.util.TimerUtil;
import me.darki.konas.util.rotation.Rotation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class Scaffold
extends Module {
    public List<Block> Field889 = Arrays.asList(Blocks.ANVIL, Blocks.AIR, Blocks.WEB, Blocks.WATER, Blocks.FIRE, Blocks.FLOWING_WATER, Blocks.LAVA, Blocks.FLOWING_WATER, Blocks.CHEST, Blocks.ENCHANTING_TABLE, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST, Blocks.GRAVEL, Blocks.LADDER, Blocks.VINE, Blocks.BEACON, Blocks.JUKEBOX, Blocks.ACACIA_DOOR, Blocks.BIRCH_DOOR, Blocks.DARK_OAK_DOOR, Blocks.IRON_DOOR, Blocks.JUNGLE_DOOR, Blocks.OAK_DOOR, Blocks.SPRUCE_DOOR, Blocks.IRON_TRAPDOOR, Blocks.TRAPDOOR, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX);
    public TimerUtil Field890 = new TimerUtil();
    public Class325 Field891;
    public static Setting<Class443> customBlocks = new Setting<>("CustomBlocks", new Class443(new String[0]));
    public static Setting<Class322> filter = new Setting<>("Filter", Class322.NONE);
    public static Setting<Double> expand = new Setting<>("Expand", 1.0, 6.0, 0.0, 0.1);
    public static Setting<Double> delay = new Setting<>("Delay", 3.5, 10.0, 1.0, 0.5);
    public static Setting<Boolean> aSwitch = new Setting<>("Switch", true);
    public static Setting<Class307> tower = new Setting<>("Tower", Class307.NORMAL);
    public static Setting<Boolean> center = new Setting<>("Center", true);
    public static Setting<Boolean> safe = new Setting<>("Safe", true);
    public static Setting<Boolean> keepY = new Setting<>("KeepY", true);
    public static Setting<Boolean> sprint = new Setting<>("Sprint", true);
    public static Setting<Boolean> down = new Setting<>("Down", true);
    public static Setting<Boolean> swing = new Setting<>("Swing", false);
    public int Field904;
    public TimerUtil Field905 = new TimerUtil();
    public float Field906;
    public float Field907;
    public BlockPos Field908;
    public boolean Field909;

    public float[] Method942(double d, double d2, double d3) {
        double d4 = d - Scaffold.mc.player.posX;
        double d5 = d2 - Scaffold.mc.player.posY;
        double d6 = d3 - Scaffold.mc.player.posZ;
        double d7 = MathHelper.sqrt((double)(d4 * d4 + d6 * d6));
        return new float[]{(float)(Math.atan2(d6, d4) * 180.0 / Math.PI) - 90.0f, (float)(-(Math.atan2(d5, d7) * 180.0 / Math.PI))};
    }

    @Subscriber(priority=3)
    public void Method135(UpdateEvent updateEvent) {
        block0: {
            if (this.Field905.GetDifferenceTiming(100.0 * (Double) delay.getValue()) || !Rotation.Method1966()) break block0;
            KonasGlobals.INSTANCE.Field1139.Method1937(this.Field906, this.Field907);
        }
    }

    public int Method464() {
        int n = 0;
        for (int i = 36; i < 45; ++i) {
            if (!Scaffold.mc.player.inventoryContainer.getSlot(i).getHasStack()) continue;
            ItemStack itemStack = Scaffold.mc.player.inventoryContainer.getSlot(i).getStack();
            Item item = itemStack.getItem();
            if (!(itemStack.getItem() instanceof ItemBlock) || this.Field889.contains(((ItemBlock)item).getBlock())) continue;
            n += itemStack.getCount();
        }
        return n;
    }

    @Override
    public void onDisable() {
        KonasGlobals.INSTANCE.Field1134.Method749(this);
    }

    public double[] Method943(double d, double d2, double d3, double d4, float f) {
        BlockPos blockPos = new BlockPos(d, Scaffold.mc.player.posY - (double)(Keyboard.isKeyDown((int)56) && ((Boolean) down.getValue()).booleanValue() ? 2 : 1), d2);
        Block block = Scaffold.mc.world.getBlockState(blockPos).getBlock();
        double d5 = -999.0;
        double d6 = -999.0;
        double d7 = 0.0;
        double d8 = (Double) expand.getValue() * 2.0;
        while (!this.Method946(block)) {
            d5 = d;
            d6 = d2;
            if ((d7 += 1.0) > d8) {
                d7 = d8;
            }
            d5 += (d3 * 0.45 * Math.cos(Math.toRadians(f + 90.0f)) + d4 * 0.45 * Math.sin(Math.toRadians(f + 90.0f))) * d7;
            d6 += (d3 * 0.45 * Math.sin(Math.toRadians(f + 90.0f)) - d4 * 0.45 * Math.cos(Math.toRadians(f + 90.0f))) * d7;
            if (d7 == d8) break;
            blockPos = new BlockPos(d5, Scaffold.mc.player.posY - (double)(Keyboard.isKeyDown((int)56) && (Boolean) down.getValue() != false ? 2 : 1), d6);
            block = Scaffold.mc.world.getBlockState(blockPos).getBlock();
        }
        return new double[]{d5, d6};
    }

    public boolean Method944(Block block) {
        if (this.Field889.contains(block)) {
            return false;
        }
        if (filter.getValue() == Class322.BLACKLIST) {
            if (((Class443) customBlocks.getValue()).Method682().contains(block)) {
                return false;
            }
        } else if (filter.getValue() == Class322.WHITELIST && !((Class443) customBlocks.getValue()).Method682().contains(block)) {
            return false;
        }
        return true;
    }

    public Scaffold() {
        super("Scaffold", Category.MISC, new String[0]);
    }

    public Class325 Method945(BlockPos blockPos) {
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos.add(0, -1, 0)).getBlock())) {
            return new Class325(this, blockPos.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos.add(-1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock())) {
            return new Class325(this, blockPos.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos.add(0, 0, -1)).getBlock())) {
            return new Class325(this, blockPos.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock())) {
            return new Class325(this, blockPos.add(0, 1, 0), EnumFacing.DOWN);
        }
        BlockPos blockPos2 = blockPos.add(-1, 0, 0);
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos2.add(0, -1, 0)).getBlock())) {
            return new Class325(this, blockPos2.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos2.add(0, 1, 0)).getBlock())) {
            return new Class325(this, blockPos2.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos2.add(-1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos2.add(1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos2.add(0, 0, 1)).getBlock())) {
            return new Class325(this, blockPos2.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos2.add(0, 0, -1)).getBlock())) {
            return new Class325(this, blockPos2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos blockPos3 = blockPos.add(1, 0, 0);
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos3.add(0, -1, 0)).getBlock())) {
            return new Class325(this, blockPos3.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos3.add(0, 1, 0)).getBlock())) {
            return new Class325(this, blockPos3.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos3.add(-1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos3.add(1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos3.add(0, 0, 1)).getBlock())) {
            return new Class325(this, blockPos3.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos3.add(0, 0, -1)).getBlock())) {
            return new Class325(this, blockPos3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos blockPos4 = blockPos.add(0, 0, 1);
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos4.add(0, -1, 0)).getBlock())) {
            return new Class325(this, blockPos4.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos4.add(0, 1, 0)).getBlock())) {
            return new Class325(this, blockPos4.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos4.add(-1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos4.add(1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos4.add(0, 0, 1)).getBlock())) {
            return new Class325(this, blockPos4.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos4.add(0, 0, -1)).getBlock())) {
            return new Class325(this, blockPos4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos blockPos5 = blockPos.add(0, 0, -1);
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos5.add(0, -1, 0)).getBlock())) {
            return new Class325(this, blockPos5.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos5.add(0, 1, 0)).getBlock())) {
            return new Class325(this, blockPos5.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos5.add(-1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos5.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos5.add(1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos5.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos5.add(0, 0, 1)).getBlock())) {
            return new Class325(this, blockPos5.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos5.add(0, 0, -1)).getBlock())) {
            return new Class325(this, blockPos5.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos blockPos6 = blockPos.add(-2, 0, 0);
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos2.add(0, -1, 0)).getBlock())) {
            return new Class325(this, blockPos2.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos2.add(0, 1, 0)).getBlock())) {
            return new Class325(this, blockPos2.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos2.add(-1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos2.add(1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos2.add(0, 0, 1)).getBlock())) {
            return new Class325(this, blockPos2.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos2.add(0, 0, -1)).getBlock())) {
            return new Class325(this, blockPos2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos blockPos7 = blockPos.add(2, 0, 0);
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos3.add(0, -1, 0)).getBlock())) {
            return new Class325(this, blockPos3.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos3.add(0, 1, 0)).getBlock())) {
            return new Class325(this, blockPos3.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos3.add(-1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos3.add(1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos3.add(0, 0, 1)).getBlock())) {
            return new Class325(this, blockPos3.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos3.add(0, 0, -1)).getBlock())) {
            return new Class325(this, blockPos3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos blockPos8 = blockPos.add(0, 0, 2);
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos4.add(0, -1, 0)).getBlock())) {
            return new Class325(this, blockPos4.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos4.add(0, 1, 0)).getBlock())) {
            return new Class325(this, blockPos4.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos4.add(-1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos4.add(1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos4.add(0, 0, 1)).getBlock())) {
            return new Class325(this, blockPos4.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos4.add(0, 0, -1)).getBlock())) {
            return new Class325(this, blockPos4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos blockPos9 = blockPos.add(0, 0, -2);
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos5.add(0, -1, 0)).getBlock())) {
            return new Class325(this, blockPos5.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos5.add(0, 1, 0)).getBlock())) {
            return new Class325(this, blockPos5.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos5.add(-1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos5.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos5.add(1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos5.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos5.add(0, 0, 1)).getBlock())) {
            return new Class325(this, blockPos5.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos5.add(0, 0, -1)).getBlock())) {
            return new Class325(this, blockPos5.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos blockPos10 = blockPos.add(0, -1, 0);
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos10.add(0, -1, 0)).getBlock())) {
            return new Class325(this, blockPos10.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos10.add(0, 1, 0)).getBlock())) {
            return new Class325(this, blockPos10.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos10.add(-1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos10.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos10.add(1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos10.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos10.add(0, 0, 1)).getBlock())) {
            return new Class325(this, blockPos10.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos10.add(0, 0, -1)).getBlock())) {
            return new Class325(this, blockPos10.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos blockPos11 = blockPos10.add(1, 0, 0);
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos11.add(0, -1, 0)).getBlock())) {
            return new Class325(this, blockPos11.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos11.add(0, 1, 0)).getBlock())) {
            return new Class325(this, blockPos11.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos11.add(-1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos11.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos11.add(1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos11.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos11.add(0, 0, 1)).getBlock())) {
            return new Class325(this, blockPos11.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos11.add(0, 0, -1)).getBlock())) {
            return new Class325(this, blockPos11.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos blockPos12 = blockPos10.add(-1, 0, 0);
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos12.add(0, -1, 0)).getBlock())) {
            return new Class325(this, blockPos12.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos12.add(0, 1, 0)).getBlock())) {
            return new Class325(this, blockPos12.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos12.add(-1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos12.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos12.add(1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos12.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos12.add(0, 0, 1)).getBlock())) {
            return new Class325(this, blockPos12.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos12.add(0, 0, -1)).getBlock())) {
            return new Class325(this, blockPos12.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos blockPos13 = blockPos10.add(0, 0, 1);
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos13.add(0, -1, 0)).getBlock())) {
            return new Class325(this, blockPos13.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos13.add(-1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos13.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos13.add(0, 1, 0)).getBlock())) {
            return new Class325(this, blockPos13.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos13.add(1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos13.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos13.add(0, 0, 1)).getBlock())) {
            return new Class325(this, blockPos13.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos13.add(0, 0, -1)).getBlock())) {
            return new Class325(this, blockPos13.add(0, 0, -1), EnumFacing.SOUTH);
        }
        BlockPos blockPos14 = blockPos10.add(0, 0, -1);
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos14.add(0, -1, 0)).getBlock())) {
            return new Class325(this, blockPos14.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos14.add(0, 1, 0)).getBlock())) {
            return new Class325(this, blockPos14.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos14.add(-1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos14.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos14.add(1, 0, 0)).getBlock())) {
            return new Class325(this, blockPos14.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos14.add(0, 0, 1)).getBlock())) {
            return new Class325(this, blockPos14.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.Field889.contains(Scaffold.mc.world.getBlockState(blockPos14.add(0, 0, -1)).getBlock())) {
            return new Class325(this, blockPos14.add(0, 0, -1), EnumFacing.SOUTH);
        }
        return null;
    }

    @Override
    public void onEnable() {
        if (Scaffold.mc.world != null) {
            this.Field890.UpdateCurrentTime();
            this.Field904 = MathHelper.floor((double) Scaffold.mc.player.posY);
        }
    }

    public boolean Method946(Block block) {
        return (block instanceof BlockAir || block instanceof BlockLiquid) && Scaffold.mc.world != null && Scaffold.mc.player != null && this.Field908 != null && Scaffold.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(this.Field908)).isEmpty();
    }

    public int Method524() {
        int n = 0;
        for (int i = 0; i < 45; ++i) {
            if (!Scaffold.mc.player.inventoryContainer.getSlot(i).getHasStack()) continue;
            ItemStack itemStack = Scaffold.mc.player.inventoryContainer.getSlot(i).getStack();
            Item item = itemStack.getItem();
            if (!(itemStack.getItem() instanceof ItemBlock) || this.Field889.contains(((ItemBlock)item).getBlock())) continue;
            n += itemStack.getCount();
        }
        return n;
    }

    @Subscriber
    public void Method139(Render3DEvent render3DEvent) {
        block0: {
            if (this.Field891 == null || this.Field891.Field566 == null) break block0;
            Class523.Method1218(this.Field891.Field566);
        }
    }

    public float[] Method947(double d, double d2, double d3, EnumFacing enumFacing) {
        EntitySnowball entitySnowball = new EntitySnowball((World) Scaffold.mc.world);
        entitySnowball.posX = d + 0.5;
        entitySnowball.posY = d2 - 2.7035252353;
        entitySnowball.posZ = d3 + 0.5;
        return this.Method942(entitySnowball.posX, entitySnowball.posY, entitySnowball.posZ);
    }

    @Subscriber(priority=11)
    public void Method948(Event event) {
        if (!ModuleManager.getModuleByClass(Sprint.class).isEnabled() && (((Boolean) down.getValue()).booleanValue() && Scaffold.mc.gameSettings.keyBindSneak.isKeyDown() || !((Boolean) sprint.getValue()).booleanValue())) {
            Scaffold.mc.player.setSprinting(false);
        }
        int n = (Boolean) down.getValue() != false && Keyboard.isKeyDown((int)56) ? 2 : 1;
        if (((Boolean) keepY.getValue()).booleanValue()) {
            if (!PlayerUtil.Method1080() && Scaffold.mc.gameSettings.keyBindJump.isKeyDown() || Scaffold.mc.player.collidedVertically || Scaffold.mc.player.onGround) {
                this.Field904 = MathHelper.floor((double) Scaffold.mc.player.posY);
            }
        } else {
            this.Field904 = MathHelper.floor((double) Scaffold.mc.player.posY);
        }
        if (event instanceof UpdateEvent) {
            Object object;
            this.Field891 = null;
            double d = Scaffold.mc.player.posX;
            double d2 = Scaffold.mc.player.posZ;
            double d3 = (Boolean) keepY.getValue() != false ? (double)this.Field904 : Scaffold.mc.player.posY;
            double d4 = Scaffold.mc.player.movementInput.moveForward;
            double d5 = Scaffold.mc.player.movementInput.moveStrafe;
            float f = Scaffold.mc.player.rotationYaw;
            if (!Scaffold.mc.player.collidedHorizontally && (Double) expand.getValue() > 0.0) {
                object = this.Method943(d, d2, d4, d5, f);
                d = object[0];
                d2 = object[1];
            }
            if (this.Method946(Scaffold.mc.world.getBlockState(new BlockPos(Scaffold.mc.player.posX, Scaffold.mc.player.posY - (double)n, Scaffold.mc.player.posZ)).getBlock())) {
                d = Scaffold.mc.player.posX;
                d2 = Scaffold.mc.player.posZ;
            }
            object = new BlockPos(d, d3 - (double)n, d2);
            this.Field908 = (BlockPos)object;
            if (Scaffold.mc.world.getBlockState((BlockPos)object).getBlock() == Blocks.AIR) {
                this.Field891 = this.Method945((BlockPos)object);
                if (this.Field891 != null) {
                    float[] fArray = RotationUtil.Method1946(Scaffold.mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d((double)this.Field891.Field566.getX() + 0.5 + (double)this.Field891.Field567.getDirectionVec().getX() * 0.5, (double)this.Field891.Field566.getY() + 0.5 + (double)this.Field891.Field567.getDirectionVec().getY() * 0.5, (double)this.Field891.Field566.getZ() + 0.5 + (double)this.Field891.Field567.getDirectionVec().getZ() * 0.5));
                    KonasGlobals.INSTANCE.Field1139.Method1937(fArray[0], fArray[1]);
                    this.Field906 = fArray[0];
                    this.Field907 = fArray[1];
                    this.Field905.UpdateCurrentTime();
                }
            }
        } else if (this.Field891 != null) {
            if (this.Method464() <= 0 || !((Boolean) aSwitch.getValue()).booleanValue() && Scaffold.mc.player.getHeldItemMainhand().getItem() != null && !(Scaffold.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock)) {
                return;
            }
            int n2 = Scaffold.mc.player.inventory.currentItem;
            if (!(!((Boolean) aSwitch.getValue()).booleanValue() || Scaffold.mc.player.getHeldItemMainhand().getItem() != null && Scaffold.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock && this.Method944(((ItemBlock) Scaffold.mc.player.getHeldItemMainhand().getItem()).getBlock()))) {
                for (int i = 0; i < 9; ++i) {
                    if (Scaffold.mc.player.inventory.getStackInSlot(i) == null || Scaffold.mc.player.inventory.getStackInSlot(i).getCount() == 0 || !(Scaffold.mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemBlock) || !this.Method944(((ItemBlock) Scaffold.mc.player.inventory.getStackInSlot(i).getItem()).getBlock())) continue;
                    Scaffold.mc.player.inventory.currentItem = i;
                    break;
                }
            }
            if (tower.getValue() != Class307.NONE) {
                if (Scaffold.mc.gameSettings.keyBindJump.isKeyDown() && Scaffold.mc.player.moveForward == 0.0f && Scaffold.mc.player.moveStrafing == 0.0f && tower.getValue() != Class307.NONE && !Scaffold.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                    if (!this.Field909 && ((Boolean) center.getValue()).booleanValue()) {
                        this.Field909 = true;
                        BlockPos blockPos = new BlockPos(Scaffold.mc.player.posX, Scaffold.mc.player.posY, Scaffold.mc.player.posZ);
                        Scaffold.mc.player.setPosition((double)blockPos.getX() + 0.5, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5);
                    }
                    if (((Boolean) center.getValue()).booleanValue() && !this.Field909) {
                        return;
                    }
                    if (tower.getValue() == Class307.FAST) {
                        KonasGlobals.INSTANCE.Field1134.Method746(this, 25, Scaffold.mc.player.ticksExisted % 10 == 0 ? 1.0f : 1.5782f);
                    }
                    Scaffold.mc.player.motionY = 0.42f;
                    Scaffold.mc.player.motionZ = 0.0;
                    Scaffold.mc.player.motionX = 0.0;
                    if (this.Field890.GetDifferenceTiming(1500.0)) {
                        KonasGlobals.INSTANCE.Field1134.Method749(this);
                        this.Field890.UpdateCurrentTime();
                        Scaffold.mc.player.motionY = -0.28;
                    }
                } else {
                    KonasGlobals.INSTANCE.Field1134.Method749(this);
                    this.Field890.UpdateCurrentTime();
                    if (this.Field909 && ((Boolean) center.getValue()).booleanValue()) {
                        this.Field909 = false;
                    }
                }
            } else {
                KonasGlobals.INSTANCE.Field1134.Method749(this);
            }
            if (Scaffold.mc.playerController.processRightClickBlock(Scaffold.mc.player, Scaffold.mc.world, this.Field891.Field566, this.Field891.Field567, new Vec3d((double)this.Field891.Field566.getX() + Math.random(), (double)this.Field891.Field566.getY() + Math.random(), (double)this.Field891.Field566.getZ() + Math.random()), EnumHand.MAIN_HAND) != EnumActionResult.FAIL) {
                if (((Boolean) swing.getValue()).booleanValue()) {
                    Scaffold.mc.player.swingArm(EnumHand.MAIN_HAND);
                } else {
                    Scaffold.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                }
            }
            Scaffold.mc.player.inventory.currentItem = n2;
        }
    }

    @Subscriber
    public void Method503(MoveEvent moveEvent) {
        double d = moveEvent.getX();
        double d2 = moveEvent.getZ();
        if (Scaffold.mc.player.onGround && !Scaffold.mc.player.noClip && ((Boolean) safe.getValue()).booleanValue() && !Keyboard.isKeyDown((int)56)) {
            double d3 = 0.05;
            while (d != 0.0 && Scaffold.mc.world.getCollisionBoxes((Entity) Scaffold.mc.player, Scaffold.mc.player.getEntityBoundingBox().offset(d, -1.0, 0.0)).isEmpty()) {
                if (d < d3 && d >= -d3) {
                    d = 0.0;
                    continue;
                }
                if (d > 0.0) {
                    d -= d3;
                    continue;
                }
                d += d3;
            }
            while (d2 != 0.0 && Scaffold.mc.world.getCollisionBoxes((Entity) Scaffold.mc.player, Scaffold.mc.player.getEntityBoundingBox().offset(0.0, -1.0, d2)).isEmpty()) {
                if (d2 < d3 && d2 >= -d3) {
                    d2 = 0.0;
                    continue;
                }
                if (d2 > 0.0) {
                    d2 -= d3;
                    continue;
                }
                d2 += d3;
            }
            while (d != 0.0 && d2 != 0.0 && Scaffold.mc.world.getCollisionBoxes((Entity) Scaffold.mc.player, Scaffold.mc.player.getEntityBoundingBox().offset(d, -1.0, d2)).isEmpty()) {
                d = d < d3 && d >= -d3 ? 0.0 : (d > 0.0 ? (d -= d3) : (d += d3));
                if (d2 < d3 && d2 >= -d3) {
                    d2 = 0.0;
                    continue;
                }
                if (d2 > 0.0) {
                    d2 -= d3;
                    continue;
                }
                d2 += d3;
            }
        }
        moveEvent.setX(d);
        moveEvent.setZ(d2);
    }
}