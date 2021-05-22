package me.darki.konas.unremaped;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockVine;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class BlockUtil
implements Class515 {
    public static int Field1357;
    public static int Field1358;
    public static int Field1359;
    public Minecraft mc = Minecraft.getMinecraft();
    public Class498 Field1361;
    public Class498 Field1362;
    public BlockPos Field1363;
    public HashMap<Class498, Float> Field1364 = new HashMap();
    public HashMap<Class498, Class498> Field1365 = new HashMap();
    public Class504 Field1366 = new Class504();
    public ArrayList<Class498> Field1367 = new ArrayList();
    public int Field1368 = 0;
    public String Field1369;

    public boolean Method1415(BlockPos blockPos) {
        Block block = this.mc.world.getBlockState(blockPos).getBlock();
        if (!(block instanceof BlockLadder) && !(block instanceof BlockVine)) {
            return false;
        }
        BlockPos blockPos2 = blockPos.up();
        return this.Method1428(blockPos.north()) || this.Method1428(blockPos.east()) || this.Method1428(blockPos.south()) || this.Method1428(blockPos.west()) || this.Method1428(blockPos2.north()) || this.Method1428(blockPos2.east()) || this.Method1428(blockPos2.south()) || this.Method1428(blockPos2.west());
    }

    public float Method1416(BlockPos blockPos) {
        float f = Math.abs(blockPos.getX() - ~this.Field1363.getX() + 1);
        float f2 = Math.abs(blockPos.getY() - this.Field1363.getY());
        float f3 = Math.abs(blockPos.getZ() - this.Field1363.getZ());
        return 1.001f * (f + f2 + f3 - 0.58578646f * Math.min(f, f3));
    }

    public boolean Method1417(BlockPos blockPos) {
        if (!this.mc.world.isBlockLoaded(blockPos)) {
            return false;
        }
        Material material = this.mc.world.getBlockState(blockPos).getMaterial();
        Block block = this.mc.world.getBlockState(blockPos).getBlock();
        if (material.blocksMovement() && !(block instanceof BlockSign)) {
            return false;
        }
        if (block instanceof BlockTripWire || block instanceof BlockPressurePlate) {
            return false;
        }
        return material != Material.LAVA && material != Material.FIRE;
    }

    public float Method1418(BlockPos blockPos, BlockPos blockPos2) {
        float[] fArray = new float[]{0.5f, 0.5f};
        BlockPos[] blockPosArray = new BlockPos[]{blockPos, blockPos2};
        for (int i = 0; i < blockPosArray.length; ++i) {
            Material material = this.mc.world.getBlockState(blockPosArray[i]).getMaterial();
            if (material == Material.WATER) {
                int n = i;
                fArray[n] = fArray[n] * 1.3164438f;
            } else if (material == Material.LAVA) {
                int n = i;
                fArray[n] = fArray[n] * 4.5395155f;
            }
            if (!(this.mc.world.getBlockState(blockPosArray[i].down()).getBlock() instanceof BlockSoulSand)) continue;
            int n = i;
            fArray[n] = fArray[n] * 2.5f;
        }
        float f = fArray[0] + fArray[1];
        if (blockPos.getX() != blockPos2.getX()) {
            if (blockPos.getZ() != blockPos2.getZ()) {
                f *= 1.4142135f;
            }
        }
        return f;
    }

    public BlockPos Method1419() {
        return this.Field1363;
    }

    public boolean Method1420(BlockPos blockPos) {
        Material material = this.mc.world.getBlockState(blockPos).getMaterial();
        if (!this.Method1428(blockPos)) {
            return false;
        }
        return material != Material.CACTUS && material != Material.LAVA;
    }

    @Override
    public String Method1311() {
        return this.Field1369;
    }

    public boolean Method1421(BlockPos blockPos) {
        Block block = this.mc.world.getBlockState(blockPos).getBlock();
        return !(block instanceof BlockFence) && !(block instanceof BlockWall) && !(block instanceof BlockFenceGate);
    }

    public ArrayList<Class498> Method1422() {
        this.Field1361 = new Class498(new BlockPos(this.mc.player.posX, this.mc.player.onGround ? this.mc.player.posY + 0.5 : this.mc.player.posY, this.mc.player.posZ));
        this.Field1364.clear();
        this.Field1365.clear();
        this.Field1366.Method1436();
        this.Field1364.put(this.Field1361, Float.valueOf(0.0f));
        this.Field1366.Method1437(this.Field1361, this.Method1416(this.Field1361));
        for (int i = 0; i < 100000; ++i) {
            ++this.Field1368;
            if (this.Field1366.Method1438().isEmpty()) continue;
            this.Field1362 = this.Field1366.Method1439();
            if (this.Field1363.equals((Object)this.Field1362)) {
                this.Method1427();
                return this.Field1367;
            }
            if (Math.abs(this.Field1361.getX() - this.Field1362.getX()) > 200 || Math.abs(this.Field1361.getZ() - this.Field1362.getZ()) > 200) {
                this.Method1427();
                return this.Field1367;
            }
            for (Class498 class498 : this.Method1425(this.Field1362)) {
                float f = this.Field1364.get((Object)this.Field1362).floatValue() + this.Method1418(this.Field1362, class498);
                if (this.Field1364.containsKey((Object)class498) && this.Field1364.get((Object)class498).floatValue() <= f) continue;
                this.Field1364.put(class498, Float.valueOf(f));
                this.Field1365.put(class498, this.Field1362);
                this.Field1366.Method1437(class498, f + this.Method1416(class498));
            }
        }
        this.Method1427();
        return this.Field1367;
    }

    public boolean Method1423(Class498 class498) {
        for (int i = 0; i <= 5; ++i) {
            if (!this.Method1420(class498.down(i))) continue;
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean Method1424(BlockPos blockPos, EnumFacing enumFacing, EnumFacing enumFacing2) {
        BlockPos blockPos2 = blockPos.offset(enumFacing);
        BlockPos blockPos3 = blockPos.offset(enumFacing2);
        BlockPos blockPos4 = blockPos2.offset(enumFacing2);
        if (!this.Method1426(blockPos2)) return false;
        if (!this.Method1426(blockPos3)) return false;
        if (!this.Method1429(blockPos, blockPos4)) return false;
        return true;
    }

    public ArrayList<Class498> Method1425(Class498 class498) {
        ArrayList<Class498> arrayList = new ArrayList<Class498>();
        if (Math.abs(this.Field1361.getX() - class498.getX()) > 200 || Math.abs(this.Field1361.getZ() - class498.getZ()) > 200) {
            return arrayList;
        }
        BlockPos blockPos = class498.north();
        BlockPos blockPos2 = class498.east();
        BlockPos blockPos3 = class498.south();
        BlockPos blockPos4 = class498.west();
        BlockPos blockPos5 = blockPos.east();
        BlockPos blockPos6 = blockPos3.east();
        BlockPos blockPos7 = blockPos3.west();
        BlockPos blockPos8 = blockPos.west();
        BlockPos blockPos9 = class498.up();
        BlockPos blockPos10 = class498.down();
        boolean bl = this.Method1428(blockPos10);
        if (bl || class498.Method930()) {
            if (this.Method1429(class498, blockPos)) {
                arrayList.add(new Class498(blockPos));
            }
            if (this.Method1429(class498, blockPos2)) {
                arrayList.add(new Class498(blockPos2));
            }
            if (this.Method1429(class498, blockPos3)) {
                arrayList.add(new Class498(blockPos3));
            }
            if (this.Method1429(class498, blockPos4)) {
                arrayList.add(new Class498(blockPos4));
            }
            if (this.Method1424(class498, EnumFacing.NORTH, EnumFacing.EAST)) {
                arrayList.add(new Class498(blockPos5));
            }
            if (this.Method1424(class498, EnumFacing.SOUTH, EnumFacing.EAST)) {
                arrayList.add(new Class498(blockPos6));
            }
            if (this.Method1424(class498, EnumFacing.SOUTH, EnumFacing.WEST)) {
                arrayList.add(new Class498(blockPos7));
            }
            if (this.Method1424(class498, EnumFacing.NORTH, EnumFacing.WEST)) {
                arrayList.add(new Class498(blockPos8));
            }
        }
        if (class498.getY() < 256 && this.Method1417(blockPos9.up()) && (bl || this.Method1415(class498)) && (this.Method1415(class498) || this.Field1363.equals((Object)blockPos9) || this.Method1420(blockPos) || this.Method1420(blockPos2) || this.Method1420(blockPos3) || this.Method1420(blockPos4))) {
            arrayList.add(new Class498(blockPos9, bl));
        }
        if (class498.getY() > 0 && this.Method1417(blockPos10) && this.Method1421(blockPos10.down()) && this.Method1423(class498)) {
            arrayList.add(new Class498(blockPos10));
        }
        return arrayList;
    }

    public BlockUtil(BlockPos blockPos, String string) {
        this.Field1361 = new Class498(new BlockPos(this.mc.player.posX, this.mc.player.onGround ? this.mc.player.posY + 0.5 : this.mc.player.posY, this.mc.player.posZ));
        this.Field1363 = blockPos;
        this.Field1364.put(this.Field1361, Float.valueOf(0.0f));
        this.Field1366.Method1437(this.Field1361, this.Method1416(this.Field1361));
        this.Field1369 = string;
    }

    static {
        Field1359 = 5;
        Field1358 = 200;
        Field1357 = 100000;
    }

    public boolean Method1426(BlockPos blockPos) {
        return this.Method1417(blockPos) && this.Method1417(blockPos.up()) && this.Method1421(blockPos.down());
    }

    public void Method1427() {
        this.Field1367.clear();
        Class498 class498 = this.Field1361;
        for (Class498 class4982 : this.Field1365.keySet()) {
            if (!(this.Method1416(class4982) < this.Method1416(class498))) continue;
            class498 = class4982;
        }
        while (class498 != null) {
            this.Field1367.add(class498);
            class498 = this.Field1365.get((Object)class498);
        }
        Collections.reverse(this.Field1367);
    }

    public boolean Method1428(BlockPos blockPos) {
        Material material = this.mc.world.getBlockState(blockPos).getMaterial();
        Block block = this.mc.world.getBlockState(blockPos).getBlock();
        return material.blocksMovement() && !(block instanceof BlockSign) || block instanceof BlockLadder;
    }

    public boolean Method1429(BlockPos blockPos, BlockPos blockPos2) {
        return this.Method1426(blockPos2) && (this.Method1417(blockPos2.down()) || this.Method1420(blockPos2.down()));
    }

    public ArrayList<Class498> Method1430() {
        return this.Field1367;
    }
}