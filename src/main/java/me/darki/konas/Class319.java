package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.mixin.mixins.ICPacketPlayer;
import me.darki.konas.mixin.mixins.IEntityPlayerSP;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.NewGui;
import me.darki.konas.setting.Setting;
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

public class Class319
extends Module {
    public Setting<Class334> Field734 = new Setting<>("Mode", Class334.NORMAL);
    public Setting<Class410> Field735 = new Setting<>("Timing", Class410.SEQUENTIAL);
    public Setting<Float> Field736 = new Setting<>("Range", Float.valueOf(3.5f), Float.valueOf(6.0f), Float.valueOf(1.0f), Float.valueOf(0.5f));
    public Setting<Boolean> Field737 = new Setting<>("Below", false);
    public Setting<Boolean> Field738 = new Setting<>("Rotate", true);
    public Setting<Boolean> Field739 = new Setting<>("Filter", false);
    public static Setting<Class443> Field740 = new Setting<>("ValidBlocks", new Class443(new String[0]));
    public Block Field741 = null;
    public float Field742;
    public float Field743;
    public Class566 Field744 = new Class566();
    public BlockPos Field745 = null;

    public void Method789(BlockPos blockPos) {
        block4: {
            Vec3d vec3d = null;
            double[] dArray = null;
            for (double d = 0.1; d < 0.9; d += 0.1) {
                for (double d2 = 0.1; d2 < 0.9; d2 += 0.1) {
                    for (double d3 = 0.1; d3 < 0.9; d3 += 0.1) {
                        Vec3d vec3d2 = new Vec3d(Class319.mc.player.posX, Class319.mc.player.getEntityBoundingBox().minY + (double)Class319.mc.player.getEyeHeight(), Class319.mc.player.posZ);
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
                        RayTraceResult rayTraceResult = Class319.mc.world.rayTraceBlocks(vec3d2, vec3d5, false, false, true);
                        if (rayTraceResult == null || rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) continue;
                        Vec3d vec3d6 = vec3d3;
                        double[] dArray3 = dArray2;
                        if (vec3d != null && dArray != null) {
                            if (!(Math.hypot(((dArray3[0] - (double)((IEntityPlayerSP)Class319.mc.player).Method238()) % 360.0 + 540.0) % 360.0 - 180.0, dArray3[1] - (double)((IEntityPlayerSP)Class319.mc.player).Method240()) < Math.hypot(((dArray[0] - (double)((IEntityPlayerSP)Class319.mc.player).Method238()) % 360.0 + 540.0) % 360.0 - 180.0, dArray[1] - (double)((IEntityPlayerSP)Class319.mc.player).Method240()))) continue;
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
            this.Field744.Method739();
        }
    }

    public BlockPos Method790(boolean bl) {
        float f;
        BlockPos blockPos = null;
        for (float f2 = f = ((Float)this.Field736.getValue()).floatValue(); f2 >= -f; f2 -= 1.0f) {
            for (float f3 = f; f3 >= -f; f3 -= 1.0f) {
                for (float f4 = f; f4 >= -f; f4 -= 1.0f) {
                    double d;
                    BlockPos blockPos2 = new BlockPos(Class319.mc.player.posX + (double)f2, Class319.mc.player.posY + (double)f3, Class319.mc.player.posZ + (double)f4);
                    IBlockState iBlockState = Class319.mc.world.getBlockState(blockPos2);
                    if (((Boolean)this.Field739.getValue()).booleanValue() && !((Class443)Field740.getValue()).Method682().contains(iBlockState.getBlock()) || !((d = Class319.mc.player.getDistance((double)blockPos2.getX(), (double)blockPos2.getY(), (double)blockPos2.getZ())) <= (double)f) || Class319.mc.world.getBlockState(blockPos2).getBlock() == Blocks.AIR || iBlockState.getBlock() instanceof BlockLiquid || !this.Method512(blockPos2) || bl && (this.Field741 == null || !Class319.mc.world.getBlockState(blockPos2).getBlock().equals(this.Field741)) || (double)blockPos2.getY() < Class319.mc.player.posY && !((Boolean)this.Field737.getValue()).booleanValue()) continue;
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
            if (this.Field735.getValue() != Class410.VANILLA) {
                return;
            }
            if (Class319.mc.player == null || Class319.mc.world == null) {
                return;
            }
            BlockPos blockPos = null;
            switch (Class336.Field328[((Class334)((Object)this.Field734.getValue())).ordinal()]) {
                case 1: {
                    blockPos = this.Method790(false);
                    break;
                }
                case 2: {
                    blockPos = this.Method790(true);
                }
            }
            if (((Boolean)this.Field738.getValue()).booleanValue() && blockPos != null) {
                this.Method789(blockPos);
            }
            if (blockPos == null || !this.Method512(blockPos)) break block8;
            Class319.mc.playerController.onPlayerDamageBlock(blockPos, Class319.mc.player.getHorizontalFacing());
            Class319.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }

    @Subscriber
    public void Method536(Class24 class24) {
        block3: {
            if (Class319.mc.world == null || Class319.mc.player == null) {
                return;
            }
            if (!(class24.getPacket() instanceof CPacketPlayer) || this.Field744.Method737(350.0) || !((Boolean)this.Field738.getValue()).booleanValue() || this.Field735.getValue() != Class410.VANILLA) break block3;
            CPacketPlayer cPacketPlayer = (CPacketPlayer)class24.getPacket();
            if (class24.getPacket() instanceof CPacketPlayer.Position) {
                class24.setCanceled(true);
                Class319.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(cPacketPlayer.getX(Class319.mc.player.posX), cPacketPlayer.getY(Class319.mc.player.posY), cPacketPlayer.getZ(Class319.mc.player.posZ), this.Field742, this.Field743, cPacketPlayer.isOnGround()));
            } else {
                ((ICPacketPlayer)cPacketPlayer).Method1695(this.Field742);
                ((ICPacketPlayer)cPacketPlayer).Method1697(this.Field743);
            }
        }
    }

    public boolean Method512(BlockPos blockPos) {
        IBlockState iBlockState = Class319.mc.world.getBlockState(blockPos);
        Block block = iBlockState.getBlock();
        return block.getBlockHardness(iBlockState, (World)Class319.mc.world, blockPos) != -1.0f;
    }

    @Subscriber(priority=18)
    public void Method123(Class50 class50) {
        block1: {
            if (this.Field735.getValue() != Class410.SEQUENTIAL) {
                return;
            }
            if (this.Field745 == null || !this.Method512(this.Field745)) break block1;
            Class319.mc.playerController.onPlayerDamageBlock(this.Field745, Class319.mc.player.getHorizontalFacing());
            Class319.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }

    @Subscriber(priority=10)
    public void Method135(UpdateEvent updateEvent) {
        block7: {
            this.Field745 = null;
            if (this.Field735.getValue() != Class410.SEQUENTIAL) {
                return;
            }
            switch (Class336.Field328[((Class334)((Object)this.Field734.getValue())).ordinal()]) {
                case 1: {
                    this.Field745 = this.Method790(false);
                    break;
                }
                case 2: {
                    this.Field745 = this.Method790(true);
                }
            }
            if (((Boolean)this.Field738.getValue()).booleanValue() && this.Field745 != null) {
                this.Method789(this.Field745);
            }
            if (this.Field744.Method737(350.0)) break block7;
            if (((Boolean)this.Field738.getValue()).booleanValue()) {
                NewGui.INSTANCE.Field1139.Method1937(this.Field742, this.Field743);
            }
        }
    }

    @Override
    public void onEnable() {
        this.Field741 = null;
    }

    @Subscriber
    public void Method791(Class17 class17) {
        block0: {
            Block block;
            if (this.Field734.getValue() != Class334.RIGHTCLICK || (block = Class319.mc.world.getBlockState(class17.Method251()).getBlock()) == this.Field741) break block0;
            this.Field741 = block;
            class17.setCanceled(true);
        }
    }

    public Class319() {
        super("Nuker", "Automatically mines blocks around you", Category.MISC, "AutoDig");
    }
}
