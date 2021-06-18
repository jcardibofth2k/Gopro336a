package me.darki.konas.module.combat;

import cookiedragon.eventsystem.Subscriber;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import me.darki.konas.module.Category;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.util.CrystalUtils;
import me.darki.konas.util.RotationUtil;
import me.darki.konas.util.rotation.Rotation;
import me.darki.konas.unremaped.Class523;
import me.darki.konas.util.PlayerUtil;
import me.darki.konas.unremaped.Class545;
import me.darki.konas.util.TimerUtil;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.unremaped.Render3DEvent;
import me.darki.konas.mixin.mixins.IEntityPlayerSP;
import me.darki.konas.mixin.mixins.IMinecraft;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.*;

public class Surround
extends Module {
    public static Setting<Boolean> rotate = new Setting<>("Rotate", true);
    public static Setting<Boolean> swing = new Setting<>("Swing", true);
    public static Setting<Boolean> packet = new Setting<>("Packet", true);
    public static Setting<Boolean> force = new Setting<>("Force", false);
    public static Setting<Boolean> full = new Setting<>("Full", true);
    public static Setting<Boolean> strict = new Setting<>("Strict", false);
    public static Setting<Integer> actionShift = new Setting<>("ActionShift", 3, 8, 1, 1);
    public static Setting<Float> actionInterval = new Setting<>("ActionInterval", Float.valueOf(0.0f), Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(0.1f));
    public static Setting<Boolean> center = new Setting<>("Center", true);
    public static Setting<Boolean> queue = new Setting<>("Queue", true);
    public static Setting<Boolean> clear = new Setting<>("Clear", false);
    public static Setting<Boolean> autoDisable = new Setting<>("AutoDisable", true);
    public static Setting<Boolean> eChest = new Setting<>("EChest", false);
    public static boolean Field2433 = false;
    public TimerUtil Field2434 = new TimerUtil();
    public BlockPos Field2435;
    public int Field2436;
    public boolean Field2437;
    public int Field2438 = 0;
    public int Field2439 = -1;
    public boolean Field2440 = false;
    public List<Vec3d> Field2441 = new ArrayList<Vec3d>();
    public ConcurrentHashMap<BlockPos, Long> Field2442 = new ConcurrentHashMap();
    public BlockPos Field2443;

    public int getBlockInHotbar() {
        Block block;
        ItemStack itemStack;
        int n;
        int n2 = -1;
        if (eChest.getValue().booleanValue()) {
            for (n = 0; n < 9; ++n) {
                itemStack = mc.player.inventory.getStackInSlot(n);
                if (itemStack == ItemStack.EMPTY || !(itemStack.getItem() instanceof ItemBlock) || !((block = ((ItemBlock)itemStack.getItem()).getBlock()) instanceof BlockEnderChest)) continue;
                n2 = n;
                return n2;
            }
        }
        for (n = 0; n < 9; ++n) {
            itemStack = mc.player.inventory.getStackInSlot(n);
            if (itemStack == ItemStack.EMPTY || !(itemStack.getItem() instanceof ItemBlock) || !((block = ((ItemBlock)itemStack.getItem()).getBlock()) instanceof BlockObsidian)) continue;
            n2 = n;
            break;
        }
        return n2;
    }

    public void Method557() {
        BlockPos blockPos;
        if (this.Method980()) {
            return;
        }
        this.Field2443 = null;
        this.Field2441 = new ArrayList<Vec3d>();
        if (this.Method539()) {
            if (full.getValue().booleanValue()) {
                this.Field2441.add(mc.player.getPositionVector().addVector(1.0, 0.0, 0.0));
                this.Field2441.add(mc.player.getPositionVector().addVector(-1.0, 0.0, 0.0));
                this.Field2441.add(mc.player.getPositionVector().addVector(0.0, 0.0, 1.0));
                this.Field2441.add(mc.player.getPositionVector().addVector(0.0, 0.0, -1.0));
            }
            this.Field2441.add(mc.player.getPositionVector().addVector(1.0, 1.0, 0.0));
            this.Field2441.add(mc.player.getPositionVector().addVector(-1.0, 1.0, 0.0));
            this.Field2441.add(mc.player.getPositionVector().addVector(0.0, 1.0, 1.0));
            this.Field2441.add(mc.player.getPositionVector().addVector(0.0, 1.0, -1.0));
        } else {
            this.Field2441.add(mc.player.getPositionVector().addVector(0.0, -1.0, 0.0));
            if (full.getValue().booleanValue()) {
                this.Field2441.add(mc.player.getPositionVector().addVector(1.0, -1.0, 0.0));
                this.Field2441.add(mc.player.getPositionVector().addVector(-1.0, -1.0, 0.0));
                this.Field2441.add(mc.player.getPositionVector().addVector(0.0, -1.0, 1.0));
                this.Field2441.add(mc.player.getPositionVector().addVector(0.0, -1.0, -1.0));
            }
            this.Field2441.add(mc.player.getPositionVector().addVector(1.0, 0.0, 0.0));
            this.Field2441.add(mc.player.getPositionVector().addVector(-1.0, 0.0, 0.0));
            this.Field2441.add(mc.player.getPositionVector().addVector(0.0, 0.0, 1.0));
            this.Field2441.add(mc.player.getPositionVector().addVector(0.0, 0.0, -1.0));
        }
        final ArrayList<BlockPos> list = new ArrayList<BlockPos>();
        final Iterator<Vec3d> iterator = this.Field2441.iterator();
        while (iterator.hasNext()) {
            final BlockPos blockPos3 = new BlockPos((Vec3d)iterator.next());
            if (mc.world.getBlockState(blockPos3).getBlock() == Blocks.AIR) {
                list.add(blockPos3);
            }
        }
        if (list.isEmpty()) {
            return;
        }
        for (final BlockPos blockPos2 : list) {
            if (this.Field2438 > (int)actionShift.getValue()) {
                return;
            }
            if (this.Field2442.containsKey(blockPos2)) {
                continue;
            }
            if (this.Method512(blockPos2)) {
                continue;
            }
            if (this.Method526(blockPos2)) {
                if (!clear.getValue()) {
                    continue;
                }
                Object o = null;
                for (final Entity entity : mc.world.loadedEntityList) {
                    if (entity == null) {
                        continue;
                    }
                    if (mc.player.getDistance(entity) > 2.4) {
                        continue;
                    }
                    if (!(entity instanceof EntityEnderCrystal)) {
                        continue;
                    }
                    if (entity.isDead) {
                        continue;
                    }
                    o = entity;
                }
                if (o != null) {
                    if (rotate.getValue()) {
                        final float[] method1946 = RotationUtil.Method1946(mc.player.getPositionEyes(mc.getRenderPartialTicks()), ((EntityEnderCrystal)o).getPositionEyes(mc.getRenderPartialTicks()));
                        mc.player.connection.sendPacket(new CPacketPlayer.Rotation(method1946[0], (float)MathHelper.normalizeAngle((int)method1946[1], 360), mc.player.onGround));
                        ((IEntityPlayerSP)mc.player).setLastReportedYaw(method1946[0]);
                        ((IEntityPlayerSP)mc.player).setLastReportedPitch((float)MathHelper.normalizeAngle((int)method1946[1], 360));
                    }
                    mc.getConnection().sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                    mc.getConnection().sendPacket(new CPacketUseEntity((Entity)o));
                }
            }
            this.Method789(this.Field2443 = blockPos2);
            ++this.Field2438;
        }
    }

    public void Method2104() {
        this.Method557();
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        if (mc.world == null || mc.player == null) {
            return;
        }
        if (packetEvent.getPacket() instanceof SPacketBlockChange && queue.getValue().booleanValue()) {
            SPacketBlockChange sPacketBlockChange = (SPacketBlockChange) packetEvent.getPacket();
            if (sPacketBlockChange.blockState.getBlock() == Blocks.AIR && mc.player.getDistance(sPacketBlockChange.getBlockPosition().getX(), sPacketBlockChange.getBlockPosition().getY(), sPacketBlockChange.getBlockPosition().getZ()) < 1.75) {
                mc.addScheduledTask(this::Method124);
            }
        }
    }

    public boolean Method539() {
        return !this.Method519() && this.Method386(mc.player);
    }

    @Override
    public void onDisable() {
        if (mc.player == null || mc.world == null) {
            return;
        }
        this.Field2443 = null;
        Field2433 = false;
        this.Field2437 = this.Method2105(this.Field2437);
    }

    public void Method124() {
        this.Method557();
    }

    public boolean Method512(BlockPos blockPos) {
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity instanceof EntityEnderCrystal || entity instanceof EntityItem || !new AxisAlignedBB(blockPos).intersects(entity.getEntityBoundingBox())) continue;
            return true;
        }
        return false;
    }

    public void Method792(BlockPos blockPos, Long l) {
        if (System.currentTimeMillis() - l > (long)(CrystalUtils.Method2142() + 40)) {
            this.Field2442.remove(blockPos);
        }
    }

    @Subscriber
    public void Method139(Render3DEvent render3DEvent) {
        block1: {
            if (mc.world == null || mc.player == null) {
                return;
            }
            if (this.Field2443 == null) break block1;
            Class523.Method1218(this.Field2443);
        }
    }

    public boolean Method519() {
        Block block = mc.world.getBlockState(new BlockPos(mc.player.getPositionVector().addVector(0.0, 0.2, 0.0))).getBlock();
        return block == Blocks.OBSIDIAN || block == Blocks.ENDER_CHEST;
    }

    public boolean Method980() {
        if (mc.player == null || mc.world == null) {
            return true;
        }
        Field2433 = false;
        this.Field2438 = 0;
        this.Field2439 = this.getBlockInHotbar();
        if (!this.isEnabled()) {
            return true;
        }
        if (this.Field2439 == -1) {
            this.toggle();
            return true;
        }
        this.Field2437 = this.Method2105(this.Field2437);
        if (mc.player.inventory.currentItem != this.Field2436 && mc.player.inventory.currentItem != this.Field2439) {
            this.Field2436 = mc.player.inventory.currentItem;
        }
        if (autoDisable.getValue().booleanValue() && !this.Field2435.equals(new BlockPos(mc.player))) {
            this.toggle();
            return true;
        }
        return !this.Field2434.GetDifferenceTiming(actionInterval.getValue().floatValue() * 10.0f);
    }

    @Override
    public void onEnable() {
        if (mc.player == null || mc.world == null) {
            this.toggle();
            return;
        }
        this.Field2436 = mc.player.inventory.currentItem;
        this.Field2435 = new BlockPos(mc.player);
        if (center.getValue().booleanValue()) {
            PlayerUtil.Method1083();
        }
        this.Field2442.clear();
    }

    public Surround() {
        super("Surround", 0, Category.COMBAT, "AutoObsidian");
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (mc.player == null || mc.world == null) {
            this.toggle();
            return;
        }
        this.Field2442.forEach(this::Method792);
        mc.addScheduledTask(this::Method2104);
    }

    public boolean Method2105(boolean bl) {
        block0: {
            if (!bl || mc.player == null) break block0;
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        return false;
    }





    public boolean Method2106(final BlockPos key, final EnumHand enumHand, final boolean b, final boolean b2, final boolean b3) {
        boolean b4 = false;
        EnumFacing down = null;
        final double n = 69420.0;
        for (final EnumFacing enumFacing : Rotation.Method1963(key, (boolean)strict.getValue(), false)) {
            if (mc.player.getPositionVector().addVector(0.0, (double)mc.player.getEyeHeight(), 0.0).distanceTo(new Vec3d((Vec3i)key.offset(enumFacing)).addVector(0.5, 0.5, 0.5).add(new Vec3d(enumFacing.getDirectionVec()).scale(0.5))) < n) {
                down = enumFacing;
            }
        }
        if (down == null) {
            down = EnumFacing.DOWN;
        }
        final BlockPos offset = key.offset(down);
        final EnumFacing opposite = down.getOpposite();
        final Vec3d add = new Vec3d((Vec3i)offset).addVector(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        if (!mc.player.isSneaking() && Class545.Method1004(offset)) {
            mc.player.connection.sendPacket(new CPacketEntityAction((Entity)mc.player, CPacketEntityAction.Action.START_SNEAKING));
            mc.player.setSneaking(true);
            b4 = true;
        }
        if (b) {
            PlayerUtil.Method1081(add);
        }
        Rotation.Method1969(offset, add, enumHand, opposite, b2, (boolean)swing.getValue());
        mc.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
        if (!(boolean)force.getValue()) {
            this.Field2442.put(key, System.currentTimeMillis());
        }
        ((IMinecraft)mc).setRightClickDelayTimer(0);
        return b4 || b3;
    }

    public boolean Method386(final Entity entity) {
        final BlockPos blockPos = new BlockPos(entity.posX, entity.posY, entity.posZ);
        return mc.world.getBlockState(blockPos).getBlock().equals(Blocks.OBSIDIAN) || mc.world.getBlockState(blockPos).getBlock().equals(Blocks.ENDER_CHEST);
    }

    public void Method789(final BlockPos blockPos) {
        try {
            final int currentItem = mc.player.inventory.currentItem;
            if (this.Field2439 == -1) {
                this.toggle();
                return;
            }
            Field2433 = true;
            mc.player.inventory.currentItem = this.Field2439;
            mc.playerController.updateController();
            this.Field2437 = this.Method2106(blockPos, this.Field2440 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, (boolean)rotate.getValue(), (boolean)packet.getValue(), this.Field2437);
            mc.player.inventory.currentItem = currentItem;
            mc.playerController.updateController();
        }
        catch (Exception ex) {}
    }

    public boolean Method526(BlockPos blockPos) {
        for (Entity entity : mc.world.loadedEntityList) {
            if (!(entity instanceof EntityEnderCrystal) || entity.equals(mc.player) || entity instanceof EntityItem || !new AxisAlignedBB(blockPos).intersects(entity.getEntityBoundingBox())) continue;
            return true;
        }
        return false;
    }
}