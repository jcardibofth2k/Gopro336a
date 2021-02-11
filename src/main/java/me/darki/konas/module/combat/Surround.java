package me.darki.konas.module.combat;

import cookiedragon.eventsystem.Subscriber;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import me.darki.konas.module.Category;
import me.darki.konas.PacketEvent;
import me.darki.konas.Class475;
import me.darki.konas.RotationUtil;
import me.darki.konas.Class496;
import me.darki.konas.Class523;
import me.darki.konas.MathUtil;
import me.darki.konas.Class545;
import me.darki.konas.Class566;
import me.darki.konas.TickEvent;
import me.darki.konas.Class89;
import me.darki.konas.mixin.mixins.IEntityPlayerSP;
import me.darki.konas.mixin.mixins.IMinecraft;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class Surround
extends Module {
    public static Setting<Boolean> Field2420 = new Setting<>("Rotate", true);
    public static Setting<Boolean> Field2421 = new Setting<>("Swing", true);
    public static Setting<Boolean> Field2422 = new Setting<>("Packet", true);
    public static Setting<Boolean> Field2423 = new Setting<>("Force", false);
    public static Setting<Boolean> Field2424 = new Setting<>("Full", true);
    public static Setting<Boolean> Field2425 = new Setting<>("Strict", false);
    public static Setting<Integer> Field2426 = new Setting<>("ActionShift", 3, 8, 1, 1);
    public static Setting<Float> Field2427 = new Setting<>("ActionInterval", Float.valueOf(0.0f), Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(0.1f));
    public static Setting<Boolean> Field2428 = new Setting<>("Center", true);
    public static Setting<Boolean> Field2429 = new Setting<>("Queue", true);
    public static Setting<Boolean> Field2430 = new Setting<>("Clear", false);
    public static Setting<Boolean> Field2431 = new Setting<>("AutoDisable", true);
    public static Setting<Boolean> Field2432 = new Setting<>("EChest", false);
    public static boolean Field2433 = false;
    public Class566 Field2434 = new Class566();
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
        if (Field2432.getValue().booleanValue()) {
            for (n = 0; n < 9; ++n) {
                itemStack = Surround.mc.player.inventory.getStackInSlot(n);
                if (itemStack == ItemStack.EMPTY || !(itemStack.getItem() instanceof ItemBlock) || !((block = ((ItemBlock)itemStack.getItem()).getBlock()) instanceof BlockEnderChest)) continue;
                n2 = n;
                return n2;
            }
        }
        for (n = 0; n < 9; ++n) {
            itemStack = Surround.mc.player.inventory.getStackInSlot(n);
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
            if (Field2424.getValue().booleanValue()) {
                this.Field2441.add(Surround.mc.player.getPositionVector().add(1.0, 0.0, 0.0));
                this.Field2441.add(Surround.mc.player.getPositionVector().add(-1.0, 0.0, 0.0));
                this.Field2441.add(Surround.mc.player.getPositionVector().add(0.0, 0.0, 1.0));
                this.Field2441.add(Surround.mc.player.getPositionVector().add(0.0, 0.0, -1.0));
            }
            this.Field2441.add(Surround.mc.player.getPositionVector().add(1.0, 1.0, 0.0));
            this.Field2441.add(Surround.mc.player.getPositionVector().add(-1.0, 1.0, 0.0));
            this.Field2441.add(Surround.mc.player.getPositionVector().add(0.0, 1.0, 1.0));
            this.Field2441.add(Surround.mc.player.getPositionVector().add(0.0, 1.0, -1.0));
        } else {
            this.Field2441.add(Surround.mc.player.getPositionVector().add(0.0, -1.0, 0.0));
            if (Field2424.getValue().booleanValue()) {
                this.Field2441.add(Surround.mc.player.getPositionVector().add(1.0, -1.0, 0.0));
                this.Field2441.add(Surround.mc.player.getPositionVector().add(-1.0, -1.0, 0.0));
                this.Field2441.add(Surround.mc.player.getPositionVector().add(0.0, -1.0, 1.0));
                this.Field2441.add(Surround.mc.player.getPositionVector().add(0.0, -1.0, -1.0));
            }
            this.Field2441.add(Surround.mc.player.getPositionVector().add(1.0, 0.0, 0.0));
            this.Field2441.add(Surround.mc.player.getPositionVector().add(-1.0, 0.0, 0.0));
            this.Field2441.add(Surround.mc.player.getPositionVector().add(0.0, 0.0, 1.0));
            this.Field2441.add(Surround.mc.player.getPositionVector().add(0.0, 0.0, -1.0));
        }
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        for (Vec3d vec3d : this.Field2441) {
            blockPos = new BlockPos(vec3d);
            if (Surround.mc.world.getBlockState(blockPos).getBlock() != Blocks.AIR) continue;
            arrayList.add(blockPos);
        }
        if (arrayList.isEmpty()) {
            return;
        }
        for (BlockPos blockPos2 : arrayList) {
            if (this.Field2438 > Field2426.getValue()) {
                return;
            }
            if (this.Field2442.containsKey(blockPos2) || this.Method512(blockPos2)) continue;
            if (this.Method526(blockPos2)) {
                if (!Field2430.getValue().booleanValue()) continue;
                blockPos = null;
                for (Entity entity : Surround.mc.world.loadedEntityList) {
                    if (entity == null || (double)Surround.mc.player.getDistance(entity) > 2.4 || !(entity instanceof EntityEnderCrystal) || entity.isDead) continue;
                    blockPos = (EntityEnderCrystal)entity;
                }
                if (blockPos != null) {
                    if (Field2420.getValue().booleanValue()) {
                        Object object = RotationUtil.Method1946(Surround.mc.player.getPositionEyes(mc.getRenderPartialTicks()), blockPos.getPositionEyes(mc.getRenderPartialTicks()));
                        Surround.mc.player.connection.sendPacket(new CPacketPlayer.Rotation((float)object[0], (float)MathHelper.normalizeAngle((int)object[1], 360), Surround.mc.player.onGround));
                        ((IEntityPlayerSP)Surround.mc.player).Method237((float)object[0]);
                        ((IEntityPlayerSP)Surround.mc.player).Method239(MathHelper.normalizeAngle((int)object[1], 360));
                    }
                    mc.getConnection().sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                    mc.getConnection().sendPacket(new CPacketUseEntity((Entity)blockPos));
                }
            }
            this.Field2443 = blockPos2;
            this.Method789(blockPos2);
            ++this.Field2438;
        }
    }

    public void Method2104() {
        this.Method557();
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        if (Surround.mc.world == null || Surround.mc.player == null) {
            return;
        }
        if (packetEvent.getPacket() instanceof SPacketBlockChange && Field2429.getValue().booleanValue()) {
            SPacketBlockChange sPacketBlockChange = (SPacketBlockChange) packetEvent.getPacket();
            if (sPacketBlockChange.blockState.getBlock() == Blocks.AIR && Surround.mc.player.getDistance(sPacketBlockChange.getBlockPosition().getX(), sPacketBlockChange.getBlockPosition().getY(), sPacketBlockChange.getBlockPosition().getZ()) < 1.75) {
                mc.addScheduledTask(this::Method124);
            }
        }
    }

    public boolean Method539() {
        return !this.Method519() && this.Method386(Surround.mc.player);
    }

    @Override
    public void onDisable() {
        if (Surround.mc.player == null || Surround.mc.world == null) {
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
        for (Entity entity : Surround.mc.world.loadedEntityList) {
            if (entity instanceof EntityEnderCrystal || entity instanceof EntityItem || !new AxisAlignedBB(blockPos).intersects(entity.getEntityBoundingBox())) continue;
            return true;
        }
        return false;
    }

    public void Method792(BlockPos blockPos, Long l) {
        if (System.currentTimeMillis() - l > (long)(Class475.Method2142() + 40)) {
            this.Field2442.remove(blockPos);
        }
    }

    @Subscriber
    public void Method139(Class89 class89) {
        block1: {
            if (Surround.mc.world == null || Surround.mc.player == null) {
                return;
            }
            if (this.Field2443 == null) break block1;
            Class523.Method1218(this.Field2443);
        }
    }

    public boolean Method519() {
        Block block = Surround.mc.world.getBlockState(new BlockPos(Surround.mc.player.getPositionVector().add(0.0, 0.2, 0.0))).getBlock();
        return block == Blocks.OBSIDIAN || block == Blocks.ENDER_CHEST;
    }

    public boolean Method980() {
        if (Surround.mc.player == null || Surround.mc.world == null) {
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
        if (Surround.mc.player.inventory.currentItem != this.Field2436 && Surround.mc.player.inventory.currentItem != this.Field2439) {
            this.Field2436 = Surround.mc.player.inventory.currentItem;
        }
        if (Field2431.getValue().booleanValue() && !this.Field2435.equals(new BlockPos(Surround.mc.player))) {
            this.toggle();
            return true;
        }
        return !this.Field2434.Method737(Field2427.getValue().floatValue() * 10.0f);
    }

    @Override
    public void onEnable() {
        if (Surround.mc.player == null || Surround.mc.world == null) {
            this.toggle();
            return;
        }
        this.Field2436 = Surround.mc.player.inventory.currentItem;
        this.Field2435 = new BlockPos(Surround.mc.player);
        if (Field2428.getValue().booleanValue()) {
            MathUtil.Method1083();
        }
        this.Field2442.clear();
    }

    public Surround() {
        super("Surround", 0, Category.COMBAT, "AutoObsidian");
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        if (Surround.mc.player == null || Surround.mc.world == null) {
            this.toggle();
            return;
        }
        this.Field2442.forEach(this::Method792);
        mc.addScheduledTask(this::Method2104);
    }

    public boolean Method2105(boolean bl) {
        block0: {
            if (!bl || Surround.mc.player == null) break block0;
            Surround.mc.player.connection.sendPacket(new CPacketEntityAction(Surround.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        return false;
    }

    public boolean Method2106(BlockPos blockPos, EnumHand enumHand, boolean bl, boolean bl2, boolean bl3) {
        BlockPos blockPos2;
        EnumFacing enumFacing3;
        boolean bl4 = false;
        EnumFacing enumFacing2 = null;
        double d = 69420.0;
        for (EnumFacing enumFacing3 : Class496.Method1963(blockPos, Field2425.getValue(), false)) {
            blockPos2 = blockPos.offset(enumFacing3);
            Vec3d vec3d = new Vec3d(blockPos2).add(0.5, 0.5, 0.5).add(new Vec3d(enumFacing3.getDirectionVec()).scale(0.5));
            if (!(Surround.mc.player.getPositionVector().add(0.0, (double)Surround.mc.player.getEyeHeight(), 0.0).distanceTo(vec3d) < d)) continue;
            enumFacing2 = enumFacing3;
        }
        if (enumFacing2 == null) {
            enumFacing2 = EnumFacing.DOWN;
        }
        BlockPos blockPos3 = blockPos.offset(enumFacing2);
        enumFacing3 = enumFacing2.getOpposite();
        blockPos2 = new Vec3d(blockPos3).add(0.5, 0.5, 0.5).add(new Vec3d(enumFacing3.getDirectionVec()).scale(0.5));
        if (!Surround.mc.player.isSneaking() && Class545.Method1004(blockPos3)) {
            Surround.mc.player.connection.sendPacket(new CPacketEntityAction(Surround.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            Surround.mc.player.setSneaking(true);
            bl4 = true;
        }
        if (bl) {
            MathUtil.Method1081((Vec3d)blockPos2);
        }
        Class496.Method1969(blockPos3, (Vec3d)blockPos2, enumHand, enumFacing3, bl2, Field2421.getValue());
        Surround.mc.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
        if (!Field2423.getValue().booleanValue()) {
            this.Field2442.put(blockPos, System.currentTimeMillis());
        }
        ((IMinecraft)mc).Method57(0);
        return bl4 || bl3;
    }

    public boolean Method386(Entity entity) {
        BlockPos blockPos = new BlockPos(entity.posX, entity.posY, entity.posZ);
        return Surround.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.OBSIDIAN) || Surround.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.ENDER_CHEST);
    }

    public void Method789(BlockPos blockPos) {
        int n;
        block17: {
            n = Surround.mc.player.inventory.currentItem;
            if (this.Field2439 != -1) break block17;
            Surround surround = this;
            surround.toggle();
            return;
        }
        Field2433 = true;
        Surround.mc.player.inventory.currentItem = this.Field2439;
        PlayerControllerMP playerControllerMP = Surround.mc.playerController;
        playerControllerMP.updateController();
        Surround surround = this;
        Surround surround2 = this;
        BlockPos blockPos2 = blockPos;
        EnumHand enumHand = this.Field2440 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
        Setting<Boolean> setting = Field2420;
        Object t = setting.getValue();
        Boolean bl = (Boolean)t;
        boolean bl2 = bl;
        Setting<Boolean> setting2 = Field2422;
        Object t2 = setting2.getValue();
        Boolean bl3 = (Boolean)t2;
        boolean bl4 = bl3;
        boolean bl5 = this.Field2437;
        boolean bl6 = surround2.Method2106(blockPos2, enumHand, bl2, bl4, bl5);
        surround.Field2437 = bl6;
        Surround.mc.player.inventory.currentItem = n;
        PlayerControllerMP playerControllerMP2 = Surround.mc.playerController;
        try {
            playerControllerMP2.updateController();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public boolean Method526(BlockPos blockPos) {
        for (Entity entity : Surround.mc.world.loadedEntityList) {
            if (!(entity instanceof EntityEnderCrystal) || entity.equals(Surround.mc.player) || entity instanceof EntityItem || !new AxisAlignedBB(blockPos).intersects(entity.getEntityBoundingBox())) continue;
            return true;
        }
        return false;
    }
}
