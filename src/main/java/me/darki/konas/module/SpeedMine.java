package me.darki.konas.module;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.Class341;
import me.darki.konas.Class361;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.NewGui;
import me.darki.konas.setting.Setting;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SpeedMine
extends Module {
    public Setting<Class361> mode = new Setting<>("Mode", Class361.INSTANT);
    public Setting<Class341> type = new Setting<>("Type", Class341.MANUAL);
    public Setting<Float> speed = new Setting<>("Speed", Float.valueOf(0.8f), Float.valueOf(1.0f), Float.valueOf(0.0f), Float.valueOf(0.1f)).Method1191(this::Method396);
    public Setting<Integer> intensity = new Setting<>("Intensity", 1, 10, 1, 1).Method1191(this::Method535);
    public Setting<Boolean> fall = new Setting<>("Fall", true).Method1191(this::Method519);
    public Setting<Boolean> rotate = new Setting<>("Rotate", true).Method1191(this::Method539);
    public Setting<Boolean> predict = new Setting<>("Predict", true).Method1191(this::Method987);
    public Setting<Boolean> others = new Setting<>("Others", false).Method1191(this::Method388);
    public Setting<Boolean> noAnim = new Setting<>("NoAnim", false).Method1191(this::Method993);
    public Setting<Boolean> strict = new Setting<>("Strict", false).Method1191(this::Method980);
    public Setting<Boolean> limit = new Setting<>("Limit", false).Method1191(this::Method992);
    public Setting<Boolean> direction = new Setting<>("Direction", false).Method1191(this::Method394);
    public Setting<Boolean> crystals = new Setting<>("Crystals", false).Method1191(this::Method393);
    public Setting<Float> delay = new Setting<>("Delay", Float.valueOf(1.0f), Float.valueOf(5.0f), Float.valueOf(0.0f), Float.valueOf(0.1f)).Method1191(this::Method973);
    public Class566 Field2410 = new Class566();
    public boolean Field2411 = false;
    public boolean Field2412 = false;
    public BlockPos Field2413 = null;
    public EnumFacing Field2414 = null;
    public Class566 Field2415 = new Class566();
    public int Field2416 = 2000;
    

    @Override
    public boolean Method396() {
        return this.mode.getValue() == Class361.PACKET;
    }

    public boolean Method394() {
        return this.mode.getValue() == Class361.INSTANT;
    }

    @Subscriber
    public void Method536(Class24 class24) {
        block1: {
            if (this.mode.getValue() != Class361.INSTANT) {
                return;
            }
            if (!((Boolean)this.predict.getValue()).booleanValue() || this.type.getValue() != Class341.AUTOMATIC || !(class24.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) || !((CPacketPlayerTryUseItemOnBlock)class24.getPacket()).getPos().offset(((CPacketPlayerTryUseItemOnBlock)class24.getPacket()).getDirection()).equals((Object)this.Field2413)) break block1;
            this.Field2411 = true;
            this.Field2410.Method739();
        }
    }

    public boolean Method393() {
        return this.mode.getValue() == Class361.INSTANT && this.type.getValue() == Class341.AUTOMATIC;
    }

    public boolean Method539() {
        return this.mode.getValue() == Class361.INSTANT;
    }

    @Override
    public void onEnable() {
        this.Field2411 = false;
        this.Field2412 = false;
        this.Field2413 = null;
        this.Field2414 = null;
        this.Field2416 = 2000;
    }

    public boolean Method519() {
        return this.mode.getValue() != Class361.INSTANT;
    }

    public boolean Method992() {
        return this.mode.getValue() == Class361.INSTANT;
    }

    public boolean Method538() {
        return SpeedMine.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL;
    }

    public EnumFacing Method1520(BlockPos blockPos) {
        for (EnumFacing enumFacing : EnumFacing.values()) {
            RayTraceResult rayTraceResult = SpeedMine.mc.world.rayTraceBlocks(new Vec3d(SpeedMine.mc.player.posX, SpeedMine.mc.player.posY + (double) SpeedMine.mc.player.getEyeHeight(), SpeedMine.mc.player.posZ), new Vec3d((double)blockPos.getX() + 0.5 + (double)enumFacing.getDirectionVec().getX() * 1.0 / 2.0, (double)blockPos.getY() + 0.5 + (double)enumFacing.getDirectionVec().getY() * 1.0 / 2.0, (double)blockPos.getZ() + 0.5 + (double)enumFacing.getDirectionVec().getZ() * 1.0 / 2.0), false, true, false);
            if (rayTraceResult == null || !rayTraceResult.typeOfHit.equals((Object)RayTraceResult.Type.BLOCK) || !rayTraceResult.getBlockPos().equals((Object)blockPos)) continue;
            return enumFacing;
        }
        if ((double)blockPos.getY() > SpeedMine.mc.player.posY + (double) SpeedMine.mc.player.getEyeHeight()) {
            return EnumFacing.DOWN;
        }
        return EnumFacing.UP;
    }

    public SpeedMine() {
        super("SpeedMine", Category.EXPLOIT, "FastMine", "FakeHaste", "InstantMine", "InstantBreak");
    }

    @Override
    public void onDisable() {
        block0: {
            if (this.mode.getValue() != Class361.VANILLA) break block0;
            SpeedMine.mc.player.removePotionEffect(MobEffects.HASTE);
        }
    }

    @Subscriber(priority=10)
    public void Method135(Class47 class47) {
        block7: {
            if (this.Field2411 && this.Field2413 != null && this.Field2414 != null && this.mode.getValue() == Class361.INSTANT && this.type.getValue() == Class341.AUTOMATIC && this.Field2410.Method737(100.0f * ((Float)this.delay.getValue()).floatValue())) {
                SpeedMine.mc.player.swingArm(EnumHand.MAIN_HAND);
                if (((Boolean)this.crystals.getValue()).booleanValue()) {
                    int n;
                    int n2 = SpeedMine.mc.player.inventory.currentItem;
                    if (!this.Method538() && (n = Class475.Method2147()) != -1 && SpeedMine.mc.player.inventory.currentItem != n) {
                        SpeedMine.mc.player.inventory.currentItem = n;
                        SpeedMine.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n));
                    }
                    if (this.Method538() || SpeedMine.mc.player.inventory.getCurrentItem() != null && SpeedMine.mc.player.inventory.getCurrentItem().getItem() instanceof ItemEndCrystal) {
                        Class545.Method996(this.Field2413, SpeedMine.mc.player.getPositionVector().add(0.0, (double) SpeedMine.mc.player.getEyeHeight(), 0.0), this.Method538() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, this.Method1520(this.Field2413), true);
                    }
                    if (SpeedMine.mc.player.inventory.currentItem != n2) {
                        SpeedMine.mc.player.inventory.currentItem = n2;
                        SpeedMine.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n2));
                    }
                }
                SpeedMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.Field2413, (Boolean)this.direction.getValue() != false ? this.Field2414 : EnumFacing.UP));
                if (((Boolean)this.limit.getValue()).booleanValue()) {
                    SpeedMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.Field2413, (Boolean)this.direction.getValue() != false ? this.Field2414.getOpposite() : EnumFacing.DOWN));
                }
                this.Field2411 = false;
            }
            if (class47.Method1234() || !Class496.Method1966()) {
                return;
            }
            if (this.Field2413 == null || this.Field2414 == null || this.Field2415.Method737(3500.0) || !((Boolean)this.rotate.getValue()).booleanValue()) break block7;
            NewGui.INSTANCE.Field1139.Method1942(new Vec3d((double)this.Field2413.getX() + 0.5 + (double)this.Field2414.getDirectionVec().getX() * 0.5, (double)this.Field2413.getY() + 0.5 + (double)this.Field2414.getDirectionVec().getY() * 0.5, (double)this.Field2413.getZ() + 0.5 + (double)this.Field2414.getDirectionVec().getZ() * 0.5));
        }
    }

    public boolean Method535() {
        return this.mode.getValue() == Class361.VANILLA;
    }

    public boolean Method993() {
        return this.mode.getValue() == Class361.INSTANT;
    }

    @Subscriber
    public void Method1846(Class646 class646) {
        if (SpeedMine.mc.player == null || SpeedMine.mc.world == null) {
            return;
        }
        if (((Boolean)this.fall.getValue()).booleanValue() && SpeedMine.mc.player.onGround && this.mode.getValue() != Class361.INSTANT && class646.Method1149().equals((Object) SpeedMine.mc.player.getPosition().down())) {
            SpeedMine.mc.player.motionY -= 1.0;
        }
        switch (Class343.Field2636[((Class361)((Object)this.mode.getValue())).ordinal()]) {
            case 1: {
                class646.Method451(0);
                SpeedMine.mc.player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 69, ((Integer)this.intensity.getValue()).intValue(), true, false));
                break;
            }
            case 2: {
                IBlockState iBlockState = SpeedMine.mc.world.getBlockState(class646.Method1149());
                if (iBlockState.getMaterial() == Material.AIR) break;
                class646.Method294(class646.Method213() + iBlockState.getPlayerRelativeBlockHardness((EntityPlayer) SpeedMine.mc.player, (World) SpeedMine.mc.world, class646.Method1149()) * ((Float)this.speed.getValue()).floatValue());
                class646.Method451(0);
                break;
            }
            case 3: {
                SpeedMine.mc.player.swingArm(EnumHand.MAIN_HAND);
                if (!this.Method515(class646.Method1149())) break;
                this.Field2415.Method739();
                if (this.type.getValue() == Class341.MANUAL && class646.Method1149().equals((Object)this.Field2413)) {
                    this.Field2416 = 500;
                    SpeedMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, class646.Method1149(), (Boolean)this.direction.getValue() != false ? class646.Method1232() : EnumFacing.UP));
                } else {
                    this.Field2416 = 2000;
                    if (((Boolean)this.strict.getValue()).booleanValue()) {
                        SpeedMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, class646.Method1149(), (Boolean)this.direction.getValue() != false ? class646.Method1232() : EnumFacing.UP));
                        SpeedMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, class646.Method1149(), (Boolean)this.direction.getValue() != false ? class646.Method1232().getOpposite() : EnumFacing.DOWN));
                        if (((Boolean)this.noAnim.getValue()).booleanValue()) {
                            SpeedMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, class646.Method1149(), (Boolean)this.direction.getValue() != false ? class646.Method1232().getOpposite() : EnumFacing.DOWN));
                        }
                        SpeedMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, class646.Method1149(), (Boolean)this.direction.getValue() != false ? class646.Method1232() : EnumFacing.UP));
                    } else {
                        SpeedMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, class646.Method1149(), (Boolean)this.direction.getValue() != false ? class646.Method1232() : EnumFacing.UP));
                        if (((Boolean)this.noAnim.getValue()).booleanValue()) {
                            SpeedMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, class646.Method1149(), (Boolean)this.direction.getValue() != false ? class646.Method1232() : EnumFacing.UP));
                        }
                        SpeedMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, class646.Method1149(), (Boolean)this.direction.getValue() != false ? class646.Method1232() : EnumFacing.UP));
                    }
                }
                this.Field2413 = class646.Method1149();
                this.Field2414 = class646.Method1232();
                class646.Method1236(true);
                break;
            }
        }
    }

    public boolean Method973() {
        return this.mode.getValue() == Class361.INSTANT && this.type.getValue() == Class341.AUTOMATIC;
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        block1: {
            if (this.mode.getValue() != Class361.INSTANT) {
                return;
            }
            if (((Boolean)this.predict.getValue()).booleanValue() || this.type.getValue() != Class341.AUTOMATIC || !(packetEvent.getPacket() instanceof SPacketBlockChange) || !((SPacketBlockChange) packetEvent.getPacket()).getBlockPosition().equals((Object)this.Field2413) || ((SPacketBlockChange) packetEvent.getPacket()).getBlockState().getBlock() == Blocks.AIR || !(SpeedMine.mc.player.getDistance((double)((SPacketBlockChange) packetEvent.getPacket()).getBlockPosition().getX() + 0.5, (double)((SPacketBlockChange) packetEvent.getPacket()).getBlockPosition().getY() + 0.5, (double)((SPacketBlockChange) packetEvent.getPacket()).getBlockPosition().getZ() + 0.5) < 6.0) || ((Boolean)this.others.getValue()).booleanValue() && ((SPacketBlockChange) packetEvent.getPacket()).getBlockState().getBlock().equals(SpeedMine.mc.world.getBlockState(((SPacketBlockChange) packetEvent.getPacket()).getBlockPosition()).getBlock())) break block1;
            this.Field2411 = true;
            this.Field2410.Method739();
        }
    }

    public boolean Method515(BlockPos blockPos) {
        IBlockState iBlockState = SpeedMine.mc.world.getBlockState(blockPos);
        return iBlockState.getBlockHardness((World) SpeedMine.mc.world, blockPos) != -1.0f;
    }

    public boolean Method980() {
        return this.mode.getValue() == Class361.INSTANT;
    }

    public boolean Method987() {
        return this.mode.getValue() == Class361.INSTANT && this.type.getValue() == Class341.AUTOMATIC;
    }

    public boolean Method388() {
        return this.mode.getValue() == Class361.INSTANT && this.type.getValue() == Class341.AUTOMATIC;
    }
}
