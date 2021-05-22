package me.darki.konas.module.movement;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.mixin.mixins.ICPacketPlayer;
import me.darki.konas.mixin.mixins.ICPacketVehicleMove;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class Jesus
extends Module {
    public static Setting<Class435> mode = new Setting<>("Mode", Class435.SOLID);
    public static Setting<Boolean> glide = new Setting<>("Glide", false);
    public static Setting<Boolean> entityJesus = new Setting<>("EntityJesus", true);
    public static Setting<Boolean> strict = new Setting<>("Strict", false);
    public static Setting<Boolean> boost = new Setting<>("Boost", false);
    public boolean Field786;
    public int Field787 = 0;
    public float Field788;

    public static IBlockState Method834(Class<? extends Block> clazz, int n) {
        for (int i = MathHelper.floor(Jesus.mc.player.getEntityBoundingBox().minX); i < MathHelper.ceil(Jesus.mc.player.getEntityBoundingBox().maxX); ++i) {
            for (int j = MathHelper.floor(Jesus.mc.player.getEntityBoundingBox().minZ); j < MathHelper.ceil(Jesus.mc.player.getEntityBoundingBox().maxZ); ++j) {
                IBlockState iBlockState = Jesus.mc.world.getBlockState(new BlockPos(i, n, j));
                if (!clazz.isInstance(iBlockState.getBlock())) continue;
                return iBlockState;
            }
        }
        return null;
    }

    public static boolean Method393() {
        if (Jesus.mc.player.fallDistance >= 3.0f) {
            return false;
        }
        AxisAlignedBB axisAlignedBB = Jesus.mc.player.getRidingEntity() != null ? Jesus.mc.player.getRidingEntity().getEntityBoundingBox().contract(0.0, 0.0, 0.0).offset(0.0, -0.05f, 0.0) : Jesus.mc.player.getEntityBoundingBox().contract(0.0, 0.0, 0.0).offset(0.0, -0.05f, 0.0);
        boolean bl = false;
        int n = (int)axisAlignedBB.minY;
        for (int i = MathHelper.floor(axisAlignedBB.minX); i < MathHelper.floor(axisAlignedBB.maxX + 1.0); ++i) {
            for (int j = MathHelper.floor(axisAlignedBB.minZ); j < MathHelper.floor(axisAlignedBB.maxZ + 1.0); ++j) {
                Block block = Jesus.mc.world.getBlockState(new BlockPos(i, n, j)).getBlock();
                if (block == Blocks.AIR) continue;
                if (!(block instanceof BlockLiquid)) {
                    return false;
                }
                bl = true;
            }
        }
        return bl;
    }

    @Subscriber
    public void Method135(UpdateEvent updateEvent) {
        if (mode.getValue() == Class435.TRAMPOLINE) {
            boolean bl;
            int n = MathHelper.floor(Jesus.mc.player.getEntityBoundingBox().minY - 0.2);
            boolean bl2 = bl = Jesus.Method834(BlockLiquid.class, n) != null;
            if (bl && !Jesus.mc.player.isSneaking()) {
                Jesus.mc.player.onGround = false;
            }
            Block block = Jesus.mc.world.getBlockState(new BlockPos((int)Math.floor(Jesus.mc.player.posX), (int)Math.floor(Jesus.mc.player.posY), (int)Math.floor(Jesus.mc.player.posZ))).getBlock();
            if (this.Field786 && !Jesus.mc.player.capabilities.isFlying && !Jesus.mc.player.isInWater()) {
                if (Jesus.mc.player.motionY < -0.3 || Jesus.mc.player.onGround || Jesus.mc.player.isOnLadder()) {
                    this.Field786 = false;
                    return;
                }
                Jesus.mc.player.motionY = Jesus.mc.player.motionY / (double)0.98f + 0.08;
                Jesus.mc.player.motionY -= 0.03120000000005;
            }
            if (Jesus.mc.player.isInWater() || Jesus.mc.player.isInLava()) {
                Jesus.mc.player.motionY = 0.1;
            }
            if (!Jesus.mc.player.isInLava() && (!Jesus.mc.player.isInWater() || boost.getValue().booleanValue()) && block instanceof BlockLiquid && Jesus.mc.player.motionY < 0.2) {
                Jesus.mc.player.motionY = 0.5;
                this.Field786 = true;
            }
        }
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        if (packetEvent.getPacket() instanceof SPacketPlayerPosLook) {
            this.Field787 = 0;
        }
    }

    public boolean Method388() {
        if (Jesus.mc.player.isSneaking()) {
            return false;
        }
        if (Jesus.mc.player.getRidingEntity() != null && Jesus.mc.player.getRidingEntity().fallDistance >= 3.0f) {
            return false;
        }
        return Jesus.mc.player.fallDistance <= 3.0f;
    }

    public static boolean Method394() {
        if (Jesus.mc.player.fallDistance >= 3.0f) {
            return false;
        }
        boolean bl = false;
        AxisAlignedBB axisAlignedBB = Jesus.mc.player.getRidingEntity() != null ? Jesus.mc.player.getRidingEntity().getEntityBoundingBox() : Jesus.mc.player.getEntityBoundingBox();
        int n = (int)axisAlignedBB.minY;
        for (int i = MathHelper.floor(axisAlignedBB.minX); i < MathHelper.floor(axisAlignedBB.maxX) + 1; ++i) {
            for (int j = MathHelper.floor(axisAlignedBB.minZ); j < MathHelper.floor(axisAlignedBB.maxZ) + 1; ++j) {
                Block block = Jesus.mc.world.getBlockState(new BlockPos(i, n, j)).getBlock();
                if (block instanceof BlockAir) continue;
                if (!(block instanceof BlockLiquid)) {
                    return false;
                }
                bl = true;
            }
        }
        return bl;
    }

    @Subscriber
    public void Method835(Class74 class74) {
        block0: {
            if (!Jesus.mc.player.isInWater() && !Jesus.mc.player.isInLava() || Jesus.mc.player.motionY != 0.1 && Jesus.mc.player.motionY != 0.5) break block0;
            class74.setCanceled(true);
        }
    }

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
        block17: {
            block18: {
                if (Jesus.mc.world == null || Jesus.mc.player == null) {
                    return;
                }
                if (mode.getValue() != Class435.SOLID) break block17;
                if (!(sendPacketEvent.getPacket() instanceof CPacketPlayer) || Jesus.mc.player.ticksExisted <= 20 || !mode.getValue().equals(Class435.SOLID) || Jesus.mc.player.getRidingEntity() != null || Jesus.mc.gameSettings.keyBindJump.isKeyDown() || !(Jesus.mc.player.fallDistance < 3.0f)) break block18;
                CPacketPlayer cPacketPlayer = (CPacketPlayer) sendPacketEvent.getPacket();
                if (!Jesus.Method393() || Jesus.Method394()) break block17;
                ((ICPacketPlayer)cPacketPlayer).setOnGround(false);
                if (strict.getValue().booleanValue()) {
                    this.Field788 += 0.12f;
                    if (this.Field788 > 0.4f) {
                        this.Field788 = 0.2f;
                    }
                    ((ICPacketPlayer)cPacketPlayer).setY(cPacketPlayer.getY(Jesus.mc.player.posY) - (double)this.Field788);
                } else {
                    ((ICPacketPlayer)cPacketPlayer).setY(Jesus.mc.player.ticksExisted % 2 == 0 ? cPacketPlayer.getY(Jesus.mc.player.posY) - 0.05 : cPacketPlayer.getY(Jesus.mc.player.posY));
                }
                break block17;
            }
            if (!(sendPacketEvent.getPacket() instanceof CPacketVehicleMove) || !strict.getValue().booleanValue() || !entityJesus.getValue().booleanValue()) break block17;
            CPacketVehicleMove cPacketVehicleMove = (CPacketVehicleMove) sendPacketEvent.getPacket();
            if (Jesus.Method393() && Jesus.mc.player.fallDistance < 3.0f && !Jesus.mc.player.movementInput.jump && !Jesus.Method394() && !Jesus.mc.player.isSneaking()) {
                double d = cPacketVehicleMove.getY();
                if (Jesus.mc.player.ticksExisted % 3 == 0) {
                    ((ICPacketVehicleMove)cPacketVehicleMove).setY(cPacketVehicleMove.getY() - 0.48);
                } else if (Jesus.mc.player.ticksExisted % 4 == 0) {
                    ((ICPacketVehicleMove)cPacketVehicleMove).setY(cPacketVehicleMove.getY() - 0.33);
                } else if (Jesus.mc.player.ticksExisted % 5 == 0) {
                    ((ICPacketVehicleMove)cPacketVehicleMove).setY(cPacketVehicleMove.getY() - 0.73);
                } else if (Jesus.mc.player.ticksExisted % 6 == 0) {
                    ((ICPacketVehicleMove)cPacketVehicleMove).setY(cPacketVehicleMove.getY() - 0.63);
                } else if (Jesus.mc.player.ticksExisted % 7 == 0) {
                    ((ICPacketVehicleMove)cPacketVehicleMove).setY(cPacketVehicleMove.getY() - 0.42);
                } else if (Jesus.mc.player.ticksExisted % 8 == 0) {
                    ((ICPacketVehicleMove)cPacketVehicleMove).setY(cPacketVehicleMove.getY() - 0.52);
                }
                if (cPacketVehicleMove.getY() == d) {
                    ((ICPacketVehicleMove)cPacketVehicleMove).setY(cPacketVehicleMove.getY() - 0.3);
                }
            }
        }
    }

    @Subscriber
    public void Method836(Class643 class643) {
        block0: {
            if (!(class643.Method1245() instanceof BlockLiquid) || class643.Method1243() != Jesus.mc.player || !((double)class643.Method1244().getY() <= Jesus.mc.player.posY) || Jesus.Method834(BlockLiquid.class, MathHelper.floor(Jesus.mc.player.getEntityBoundingBox().minY + 0.01)) == null || Jesus.Method834(BlockLiquid.class, MathHelper.floor(Jesus.mc.player.getEntityBoundingBox().minY - 0.02)) == null || !(Jesus.mc.player.fallDistance < 3.0f) || Jesus.mc.player.isSneaking()) break block0;
            class643.Method1239(Block.FULL_BLOCK_AABB);
        }
    }

    public Jesus() {
        super("Jesus", Category.MOVEMENT);
    }

    @Subscriber
    public void Method130(Class19 class19) {
        if (mode.getValue() == Class435.TRAMPOLINE) {
            return;
        }
        if (!Jesus.mc.player.movementInput.sneak && !Jesus.mc.player.movementInput.jump && Jesus.Method394()) {
            Jesus.mc.player.motionY = 0.1;
        }
        if (Jesus.Method393() && Jesus.mc.player.fallDistance < 3.0f && !Jesus.mc.player.movementInput.jump && !Jesus.Method394() && !Jesus.mc.player.isSneaking() && glide.getValue().booleanValue()) {
            switch (this.Field787) {
                case 0: {
                    Jesus.mc.player.motionX *= 1.1;
                    Jesus.mc.player.motionZ *= 1.1;
                    break;
                }
                case 1: {
                    Jesus.mc.player.motionX *= 1.27;
                    Jesus.mc.player.motionZ *= 1.27;
                    break;
                }
                case 2: {
                    Jesus.mc.player.motionX *= 1.51;
                    Jesus.mc.player.motionZ *= 1.51;
                    break;
                }
                case 3: {
                    Jesus.mc.player.motionX *= 1.15;
                    Jesus.mc.player.motionZ *= 1.15;
                    break;
                }
                case 4: {
                    Jesus.mc.player.motionX *= 1.23;
                    Jesus.mc.player.motionZ *= 1.23;
                }
            }
            ++this.Field787;
            if (this.Field787 > 4) {
                this.Field787 = 0;
            }
        }
    }
}