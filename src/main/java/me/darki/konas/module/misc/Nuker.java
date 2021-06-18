package me.darki.konas.module.misc;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.event.events.TickEvent;
import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.mixin.mixins.ICPacketPlayer;
import me.darki.konas.mixin.mixins.IEntityPlayerSP;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.*;
import me.darki.konas.util.TimerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class Nuker
extends Module {
    public Setting<Class334> mode = new Setting<>("Mode", Class334.NORMAL);
    public Setting<Class410> timing = new Setting<>("Timing", Class410.SEQUENTIAL);
    public Setting<Float> range = new Setting<>("Range", Float.valueOf(3.5f), Float.valueOf(6.0f), Float.valueOf(1.0f), Float.valueOf(0.5f));
    public Setting<Boolean> below = new Setting<>("Below", false);
    public Setting<Boolean> rotate = new Setting<>("Rotate", true);
    public Setting<Boolean> filter = new Setting<>("Filter", false);
    public static Setting<Class443> validBlocks = new Setting<>("ValidBlocks", new Class443(new String[0]));
    public Block Field741 = null;
    public float Field742;
    public float Field743;
    public TimerUtil Field744 = new TimerUtil();
    public BlockPos Field745 = null;

    public void Method789(BlockPos blockPos) {
        block4: {
            Vec3d vec3d = null;
            double[] dArray = null;
            for (double d = 0.1; d < 0.9; d += 0.1) {
                for (double d2 = 0.1; d2 < 0.9; d2 += 0.1) {
                    for (double d3 = 0.1; d3 < 0.9; d3 += 0.1) {
                        Vec3d vec3d2 = new Vec3d(Nuker.mc.player.posX, Nuker.mc.player.getEntityBoundingBox().minY + (double) Nuker.mc.player.getEyeHeight(), Nuker.mc.player.posZ);
                        Vec3d vec3d3 = new Vec3d((Vec3i)blockPos).add(d, d2, d3);
                        double d4 = vec3d2.distanceTo(vec3d3);
                        double d5 = vec3d3.x - vec3d2.x;
                        double d6 = vec3d3.y - vec3d2.y;
                        double d7 = vec3d3.z - vec3d2.z;
                        double d8 = MathHelper.sqrt((double)(d5 * d5 + d7 * d7));
                        double[] dArray2 = new double[]{MathHelper.wrapDegrees((float)((float)Math.toDegrees(Math.atan2(d7, d5)) - 90.0f)), MathHelper.wrapDegrees((float)((float)(-Math.toDegrees(Math.atan2(d6, d8)))))};
                        float f = MathHelper.cos((float)((float)(-dArray2[0] * 0.01745329238474369 - 3.1415927410125732)));
                        float f2 = MathHelper.sin((float)((float)(-dArray2[0] * 0.01745329238474369 - 3.1415927410125732)));
                        float f3 = -MathHelper.cos((float)((float)(-dArray2[1] * 0.01745329238474369)));
                        float f4 = MathHelper.sin((float)((float)(-dArray2[1] * 0.01745329238474369)));
                        Vec3d vec3d4 = new Vec3d((double)(f2 * f3), (double)f4, (double)(f * f3));
                        Vec3d vec3d5 = vec3d2.add(vec3d4.x * d4, vec3d4.y * d4, vec3d4.z * d4);
                        RayTraceResult rayTraceResult = Nuker.mc.world.rayTraceBlocks(vec3d2, vec3d5, false, false, true);
                        if (rayTraceResult == null || rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) continue;
                        Vec3d vec3d6 = vec3d3;
                        double[] dArray3 = dArray2;
                        if (vec3d != null && dArray != null) {
                            if (!(Math.hypot(((dArray3[0] - (double)((IEntityPlayerSP) Nuker.mc.player).getLastReportedYaw()) % 360.0 + 540.0) % 360.0 - 180.0, dArray3[1] - (double)((IEntityPlayerSP) Nuker.mc.player).getLastReportedPitch()) < Math.hypot(((dArray[0] - (double)((IEntityPlayerSP) Nuker.mc.player).getLastReportedYaw()) % 360.0 + 540.0) % 360.0 - 180.0, dArray[1] - (double)((IEntityPlayerSP) Nuker.mc.player).getLastReportedPitch()))) continue;
                            vec3d = vec3d6;
                            dArray = dArray3;
                            continue;
                        }
                        vec3d = vec3d6;
                        dArray = dArray3;
                    }
                }
            }
            if (dArray == null) break block4;
            this.Field742 = (float)dArray[0];
            this.Field743 = (float)dArray[1];
            this.Field744.UpdateCurrentTime();
        }
    }

    public BlockPos Method790(boolean bl) {
        float f;
        BlockPos blockPos = null;
        for (float f2 = f = ((Float)this.range.getValue()).floatValue(); f2 >= -f; f2 -= 1.0f) {
            for (float f3 = f; f3 >= -f; f3 -= 1.0f) {
                for (float f4 = f; f4 >= -f; f4 -= 1.0f) {
                    double d;
                    BlockPos blockPos2 = new BlockPos(Nuker.mc.player.posX + (double)f2, Nuker.mc.player.posY + (double)f3, Nuker.mc.player.posZ + (double)f4);
                    IBlockState iBlockState = Nuker.mc.world.getBlockState(blockPos2);
                    if (((Boolean)this.filter.getValue()).booleanValue() && !((Class443) validBlocks.getValue()).Method682().contains(iBlockState.getBlock()) || !((d = Nuker.mc.player.getDistance((double)blockPos2.getX(), (double)blockPos2.getY(), (double)blockPos2.getZ())) <= (double)f) || Nuker.mc.world.getBlockState(blockPos2).getBlock() == Blocks.AIR || iBlockState.getBlock() instanceof BlockLiquid || !this.Method512(blockPos2) || bl && (this.Field741 == null || !Nuker.mc.world.getBlockState(blockPos2).getBlock().equals(this.Field741)) || (double)blockPos2.getY() < Nuker.mc.player.posY && !((Boolean)this.below.getValue()).booleanValue()) continue;
                    f = (float)d;
                    blockPos = blockPos2;
                }
            }
        }
        return blockPos;
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        block8: {
            if (tickEvent.Method324() == net.minecraftforge.fml.common.gameevent.TickEvent.Phase.END) {
                return;
            }
            if (this.timing.getValue() != Class410.VANILLA) {
                return;
            }
            if (Nuker.mc.player == null || Nuker.mc.world == null) {
                return;
            }
            BlockPos blockPos = null;
            switch (Class336.Field328[((Class334)((Object)this.mode.getValue())).ordinal()]) {
                case 1: {
                    blockPos = this.Method790(false);
                    break;
                }
                case 2: {
                    blockPos = this.Method790(true);
                }
            }
            if (((Boolean)this.rotate.getValue()).booleanValue() && blockPos != null) {
                this.Method789(blockPos);
            }
            if (blockPos == null || !this.Method512(blockPos)) break block8;
            Nuker.mc.playerController.onPlayerDamageBlock(blockPos, Nuker.mc.player.getHorizontalFacing());
            Nuker.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        block3: {
            if (Nuker.mc.world == null || Nuker.mc.player == null) {
                return;
            }
            if (!(sendPacketEvent.getPacket() instanceof CPacketPlayer) || this.Field744.GetDifferenceTiming(350.0) || !((Boolean)this.rotate.getValue()).booleanValue() || this.timing.getValue() != Class410.VANILLA) break block3;
            CPacketPlayer cPacketPlayer = (CPacketPlayer) sendPacketEvent.getPacket();
            if (sendPacketEvent.getPacket() instanceof CPacketPlayer.Position) {
                sendPacketEvent.setCanceled(true);
                Nuker.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(cPacketPlayer.getX(Nuker.mc.player.posX), cPacketPlayer.getY(Nuker.mc.player.posY), cPacketPlayer.getZ(Nuker.mc.player.posZ), this.Field742, this.Field743, cPacketPlayer.isOnGround()));
            } else {
                ((ICPacketPlayer)cPacketPlayer).setYaw(this.Field742);
                ((ICPacketPlayer)cPacketPlayer).setPitch(this.Field743);
            }
        }
    }

    public boolean Method512(BlockPos blockPos) {
        IBlockState iBlockState = Nuker.mc.world.getBlockState(blockPos);
        Block block = iBlockState.getBlock();
        return block.getBlockHardness(iBlockState, (World) Nuker.mc.world, blockPos) != -1.0f;
    }

    @Subscriber(priority=18)
    public void Method123(Class50 class50) {
        block1: {
            if (this.timing.getValue() != Class410.SEQUENTIAL) {
                return;
            }
            if (this.Field745 == null || !this.Method512(this.Field745)) break block1;
            Nuker.mc.playerController.onPlayerDamageBlock(this.Field745, Nuker.mc.player.getHorizontalFacing());
            Nuker.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }

    @Subscriber(priority=10)
    public void Method135(UpdateEvent updateEvent) {
        block7: {
            this.Field745 = null;
            if (this.timing.getValue() != Class410.SEQUENTIAL) {
                return;
            }
            switch (Class336.Field328[((Class334)((Object)this.mode.getValue())).ordinal()]) {
                case 1: {
                    this.Field745 = this.Method790(false);
                    break;
                }
                case 2: {
                    this.Field745 = this.Method790(true);
                }
            }
            if (((Boolean)this.rotate.getValue()).booleanValue() && this.Field745 != null) {
                this.Method789(this.Field745);
            }
            if (this.Field744.GetDifferenceTiming(350.0)) break block7;
            if (((Boolean)this.rotate.getValue()).booleanValue()) {
                KonasGlobals.INSTANCE.Field1139.Method1937(this.Field742, this.Field743);
            }
        }
    }

    @Override
    public void onEnable() {
        this.Field741 = null;
    }

    @Subscriber
    public void Method791(rightClickBlockEvent rightClickBlockEvent) {
        block0: {
            Block block;
            if (this.mode.getValue() != Class334.RIGHTCLICK || (block = Nuker.mc.world.getBlockState(rightClickBlockEvent.Method251()).getBlock()) == this.Field741) break block0;
            this.Field741 = block;
            rightClickBlockEvent.setCanceled(true);
        }
    }

    public Nuker() {
        super("Nuker", "Automatically mines blocks around you", Category.MISC, "AutoDig");
    }
}