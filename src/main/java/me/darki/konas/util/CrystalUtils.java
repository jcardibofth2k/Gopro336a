package me.darki.konas.util;

import java.util.Arrays;

import me.darki.konas.settingEnums.ACInteractMode;
import net.minecraft.util.math.Vec3i;
import net.minecraft.init.MobEffects;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.CombatRules;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.Explosion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.math.MathHelper;
import net.minecraft.init.Items;
import me.darki.konas.module.Module;
import javax.annotation.Nullable;
import net.minecraft.util.math.RayTraceResult;
import java.util.Iterator;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockAir;
import me.darki.konas.module.combat.AutoCrystal;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.Block;
import java.util.List;
import net.minecraft.client.Minecraft;
// I think this is ca utils
public class CrystalUtils
{
    public static Minecraft Field2499;
    public static List<Block> Field2500;
    
    public static float Method2140(final float n) {
        final int id = CrystalUtils.Field2499.world.getDifficulty().getDifficultyId();
        return n * ((id == 0) ? 0.0f : ((id == 2) ? 1.0f : ((id == 1) ? 0.5f : 1.5f)));
    }
    
    public static boolean Method2141(final double n, final double n2, final double n3) {
        return CrystalUtils.Field2499.world.rayTraceBlocks(new Vec3d(CrystalUtils.Field2499.player.posX, CrystalUtils.Field2499.player.posY + CrystalUtils.Field2499.player.getEyeHeight(), CrystalUtils.Field2499.player.posZ), new Vec3d(n, n2 + 1.8, n3), false, true, false) == null || CrystalUtils.Field2499.world.rayTraceBlocks(new Vec3d(CrystalUtils.Field2499.player.posX, CrystalUtils.Field2499.player.posY + CrystalUtils.Field2499.player.getEyeHeight(), CrystalUtils.Field2499.player.posZ), new Vec3d(n, n2 + 1.5, n3), false, true, false) == null || CrystalUtils.Field2499.world.rayTraceBlocks(new Vec3d(CrystalUtils.Field2499.player.posX, CrystalUtils.Field2499.player.posY + CrystalUtils.Field2499.player.getEyeHeight(), CrystalUtils.Field2499.player.posZ), new Vec3d(n, n2, n3), false, true, false) == null;
    }
    
    public static int Method2142() {
        if (CrystalUtils.Field2499.getConnection() == null) {
            return 50;
        }
        if (CrystalUtils.Field2499.player == null) {
            return 50;
        }
        try {
            return CrystalUtils.Field2499.getConnection().getPlayerInfo(CrystalUtils.Field2499.player.getUniqueID()).getResponseTime();
        }
        catch (NullPointerException ex) {
            return 50;
        }
    }
    
    public static boolean Method2143(final Vec3d vec3d) {
        return CrystalUtils.Field2499.world.rayTraceBlocks(new Vec3d(CrystalUtils.Field2499.player.posX, CrystalUtils.Field2499.player.getEntityBoundingBox().minY + CrystalUtils.Field2499.player.getEyeHeight(), CrystalUtils.Field2499.player.posZ), vec3d) == null;
    }
    
    public static Vec3d Method2144(final Entity entity, final int n) {
        final double n2 = entity.posX - entity.prevPosX;
        final double n3 = entity.posZ - entity.prevPosZ;
        double n4 = 0.0;
        double n5 = 0.0;
        if (AutoCrystal.collision.getValue()) {
            for (int n6 = 1; n6 <= n && CrystalUtils.Field2499.world.getBlockState(new BlockPos(entity.posX + n2 * n6, entity.posY, entity.posZ + n3 * n6)).getBlock() instanceof BlockAir; ++n6) {
                n4 = n2 * n6;
                n5 = n3 * n6;
            }
        }
        else {
            n4 = n2 * n;
            n5 = n3 * n;
        }
        return new Vec3d(n4, 0.0, n5);
    }
    
    public static boolean Method2145(final BlockPos blockPos) {
        final BlockPos add = blockPos.add(0, 1, 0);
        final BlockPos add2 = blockPos.add(0, 2, 0);
        try {
            if (CrystalUtils.Field2499.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && CrystalUtils.Field2499.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
                return false;
            }
            if (CrystalUtils.Field2499.world.getBlockState(add).getBlock() != Blocks.AIR || CrystalUtils.Field2499.world.getBlockState(add2).getBlock() != Blocks.AIR) {
                return false;
            }
            final Iterator iterator = CrystalUtils.Field2499.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(add)).iterator();
            while (iterator.hasNext()) {
                if (!(iterator.next() instanceof EntityEnderCrystal)) {
                    return false;
                }
            }
            final Iterator iterator2 = CrystalUtils.Field2499.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(add2)).iterator();
            while (iterator2.hasNext()) {
                if (!(iterator2.next() instanceof EntityEnderCrystal)) {
                    return false;
                }
            }
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    @Nullable
    public static RayTraceResult Method2146(final Vec3d vec3d, final Vec3d vec3d2) {
        return Method2148(vec3d, vec3d2, false, false, false);
    }
    
    public static int getSlotEndCrystal() {
        int currentItem = -1;
        if (Module.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) {
            currentItem = Module.mc.player.inventory.currentItem;
        }
        if (currentItem == -1) {
            for (int i = 0; i < 9; ++i) {
                if (Module.mc.player.inventory.getStackInSlot(i).getItem() == Items.END_CRYSTAL) {
                    currentItem = i;
                    break;
                }
            }
        }
        return currentItem;
    }
    
    @Nullable
    public static RayTraceResult Method2148(Vec3d vec3d, final Vec3d vec3d2, final boolean b, final boolean b2, final boolean b3) {
        if (Double.isNaN(vec3d.x) || Double.isNaN(vec3d.y) || Double.isNaN(vec3d.z)) {
            return null;
        }
        if (!Double.isNaN(vec3d2.x) && !Double.isNaN(vec3d2.y) && !Double.isNaN(vec3d2.z)) {
            final int floor = MathHelper.floor(vec3d2.x);
            final int floor2 = MathHelper.floor(vec3d2.y);
            final int floor3 = MathHelper.floor(vec3d2.z);
            int floor4 = MathHelper.floor(vec3d.x);
            int floor5 = MathHelper.floor(vec3d.y);
            int floor6 = MathHelper.floor(vec3d.z);
            final BlockPos blockPos = new BlockPos(floor4, floor5, floor6);
            IBlockState blockState = CrystalUtils.Field2499.world.getBlockState(blockPos);
            Block block = blockState.getBlock();
            if (!CrystalUtils.Field2500.contains(block)) {
                block = Blocks.AIR;
                blockState = Blocks.AIR.getBlockState().getBaseState();
            }
            if ((!b2 || blockState.getCollisionBoundingBox((IBlockAccess) CrystalUtils.Field2499.world, blockPos) != Block.NULL_AABB) && block.canCollideCheck(blockState, b)) {
                final RayTraceResult collisionRayTrace = blockState.collisionRayTrace((World) CrystalUtils.Field2499.world, blockPos, vec3d, vec3d2);
                if (collisionRayTrace != null) {
                    return collisionRayTrace;
                }
            }
            RayTraceResult rayTraceResult = null;
            int n = 200;
            while (n-- >= 0) {
                if (!Double.isNaN(vec3d.x)) {
                    if (!Double.isNaN(vec3d.y)) {
                        if (!Double.isNaN(vec3d.z)) {
                            if (floor4 == floor && floor5 == floor2 && floor6 == floor3) {
                                return b3 ? rayTraceResult : null;
                            }
                            boolean b4 = true;
                            boolean b5 = true;
                            boolean b6 = true;
                            double n2 = 999.0;
                            double n3 = 999.0;
                            double n4 = 999.0;
                            if (floor > floor4) {
                                n2 = floor4 + 1.0;
                            }
                            else if (floor < floor4) {
                                n2 = floor4 + 0.0;
                            }
                            else {
                                b4 = false;
                            }
                            if (floor2 > floor5) {
                                n3 = floor5 + 1.0;
                            }
                            else if (floor2 < floor5) {
                                n3 = floor5 + 0.0;
                            }
                            else {
                                b5 = false;
                            }
                            if (floor3 > floor6) {
                                n4 = floor6 + 1.0;
                            }
                            else if (floor3 < floor6) {
                                n4 = floor6 + 0.0;
                            }
                            else {
                                b6 = false;
                            }
                            double n5 = 999.0;
                            double n6 = 999.0;
                            double n7 = 999.0;
                            final double n8 = vec3d2.x - vec3d.x;
                            final double n9 = vec3d2.y - vec3d.y;
                            final double n10 = vec3d2.z - vec3d.z;
                            if (b4) {
                                n5 = (n2 - vec3d.x) / n8;
                            }
                            if (b5) {
                                n6 = (n3 - vec3d.y) / n9;
                            }
                            if (b6) {
                                n7 = (n4 - vec3d.z) / n10;
                            }
                            if (n5 == -0.0) {
                                n5 = -1.0E-4;
                            }
                            if (n6 == -0.0) {
                                n6 = -1.0E-4;
                            }
                            if (n7 == -0.0) {
                                n7 = -1.0E-4;
                            }
                            EnumFacing enumFacing;
                            if (n5 < n6 && n5 < n7) {
                                enumFacing = ((floor > floor4) ? EnumFacing.WEST : EnumFacing.EAST);
                                vec3d = new Vec3d(n2, vec3d.y + n9 * n5, vec3d.z + n10 * n5);
                            }
                            else if (n6 < n7) {
                                enumFacing = ((floor2 > floor5) ? EnumFacing.DOWN : EnumFacing.UP);
                                vec3d = new Vec3d(vec3d.x + n8 * n6, n3, vec3d.z + n10 * n6);
                            }
                            else {
                                enumFacing = ((floor3 > floor6) ? EnumFacing.NORTH : EnumFacing.SOUTH);
                                vec3d = new Vec3d(vec3d.x + n8 * n7, vec3d.y + n9 * n7, n4);
                            }
                            floor4 = MathHelper.floor(vec3d.x) - ((enumFacing == EnumFacing.EAST) ? 1 : 0);
                            floor5 = MathHelper.floor(vec3d.y) - ((enumFacing == EnumFacing.UP) ? 1 : 0);
                            floor6 = MathHelper.floor(vec3d.z) - ((enumFacing == EnumFacing.SOUTH) ? 1 : 0);
                            final BlockPos blockPos2 = new BlockPos(floor4, floor5, floor6);
                            IBlockState blockState2 = CrystalUtils.Field2499.world.getBlockState(blockPos2);
                            Block block2 = blockState2.getBlock();
                            if (!CrystalUtils.Field2500.contains(block2)) {
                                block2 = Blocks.AIR;
                                blockState2 = Blocks.AIR.getBlockState().getBaseState();
                            }
                            if (b2 && blockState2.getMaterial() != Material.PORTAL && blockState2.getCollisionBoundingBox((IBlockAccess) CrystalUtils.Field2499.world, blockPos2) == Block.NULL_AABB) {
                                continue;
                            }
                            if (block2.canCollideCheck(blockState2, b)) {
                                final RayTraceResult collisionRayTrace2 = blockState2.collisionRayTrace((World) CrystalUtils.Field2499.world, blockPos2, vec3d, vec3d2);
                                if (collisionRayTrace2 != null) {
                                    return collisionRayTrace2;
                                }
                                continue;
                            }
                            else {
                                rayTraceResult = new RayTraceResult(RayTraceResult.Type.MISS, vec3d, enumFacing, blockPos2);
                            }
                            continue;
                        }
                    }
                }
                return null;
            }
            return b3 ? rayTraceResult : null;
        }
        return null;
    }
    
    public static float Method2149(final EntityLivingBase entityLivingBase, final float n, final Explosion explosion) {
        if (entityLivingBase instanceof EntityPlayer) {
            final EntityPlayer entityPlayer = (EntityPlayer)entityLivingBase;
            final DamageSource causeExplosionDamage = DamageSource.causeExplosionDamage(explosion);
            final float damageAfterAbsorb = CombatRules.getDamageAfterAbsorb(n, (float)entityPlayer.getTotalArmorValue(), (float)entityPlayer.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            int enchantmentModifierDamage = 0;
            try {
                enchantmentModifierDamage = EnchantmentHelper.getEnchantmentModifierDamage(entityPlayer.getArmorInventoryList(), causeExplosionDamage);
            }
            catch (Exception ex) {}
            float a = damageAfterAbsorb * (1.0f - MathHelper.clamp((float)enchantmentModifierDamage, 0.0f, 20.0f) / 25.0f);
            if (entityLivingBase.isPotionActive(MobEffects.RESISTANCE)) {
                a -= a / 4.0f;
            }
            return Math.max(a, 0.0f);
        }
        return CombatRules.getDamageAfterAbsorb(n, (float)entityLivingBase.getTotalArmorValue(), (float)entityLivingBase.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
    }
    
    public static float Method2150(final double n, final double n2, final double n3, final Entity entity) {
        final float n4 = 12.0f;
        final double n5 = Method2153(entity, ((int)AutoCrystal.predictTicks.getValue() > 0) ? ((int)AutoCrystal.predictTicks.getValue()) : 0).distanceTo(new Vec3d(n, n2, n3)) / n4;
        final Vec3d vec3d = new Vec3d(n, n2, n3);
        double n6 = 0.0;
        try {
            if (AutoCrystal.predictDestruction.getValue()) {
                n6 = Method2155(vec3d, ((int)AutoCrystal.predictTicks.getValue() > 0) ? entity.getEntityBoundingBox().offset(Method2144(entity, (int)AutoCrystal.predictTicks.getValue())) : entity.getEntityBoundingBox());
            }
            else {
                n6 = entity.world.getBlockDensity(vec3d, ((int)AutoCrystal.predictTicks.getValue() > 0) ? entity.getEntityBoundingBox().offset(Method2144(entity, (int)AutoCrystal.predictTicks.getValue())) : entity.getEntityBoundingBox());
            }
        }
        catch (Exception ex) {}
        final double n7 = (1.0 - n5) * n6;
        final float n8 = (float)(int)((n7 * n7 + n7) / 2.0 * 7.0 * n4 + 1.0);
        double n9 = 1.0;
        if (entity instanceof EntityLivingBase) {
            n9 = Method2149((EntityLivingBase)entity, Method2140(n8), new Explosion((World) CrystalUtils.Field2499.world, (Entity) CrystalUtils.Field2499.player, n, n2, n3, 6.0f, false, true));
        }
        return (float)n9;
    }
    
    public static boolean Method2151(final BlockPos blockPos) {
        if (AutoCrystal.interact.getValue() != ACInteractMode.VANILLA) {
            final double n = 0.45;
            final double n2 = 0.05;
            final double n3 = 0.95;
            final Vec3d vec3d = new Vec3d(CrystalUtils.Field2499.player.posX, CrystalUtils.Field2499.player.getEntityBoundingBox().minY + CrystalUtils.Field2499.player.getEyeHeight(), CrystalUtils.Field2499.player.posZ);
            for (double n4 = n2; n4 <= n3; n4 += n) {
                for (double n5 = n2; n5 <= n3; n5 += n) {
                    for (double n6 = n2; n6 <= n3; n6 += n) {
                        final Vec3d add = new Vec3d((Vec3i)blockPos).addVector(n4, n5, n6);
                        final double distanceTo = vec3d.distanceTo(add);
                        final double x = add.x - vec3d.x;
                        final double y = add.y - vec3d.y;
                        final double y2 = add.z - vec3d.z;
                        final double[] array = { MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(y2, x)) - 90.0f), MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(y, MathHelper.sqrt(x * x + y2 * y2))))) };
                        final float cos = MathHelper.cos((float)(-array[0] * 0.01745329238474369 - 3.1415927410125732));
                        final float sin = MathHelper.sin((float)(-array[0] * 0.01745329238474369 - 3.1415927410125732));
                        final float n7 = -MathHelper.cos((float)(-array[1] * 0.01745329238474369));
                        final Vec3d vec3d2 = new Vec3d((double)(sin * n7), (double)MathHelper.sin((float)(-array[1] * 0.01745329238474369)), (double)(cos * n7));
                        final RayTraceResult rayTraceBlocks = CrystalUtils.Field2499.world.rayTraceBlocks(vec3d, vec3d.addVector(vec3d2.x * distanceTo, vec3d2.y * distanceTo, vec3d2.z * distanceTo), false, false, false);
                        if (rayTraceBlocks != null && rayTraceBlocks.typeOfHit == RayTraceResult.Type.BLOCK && rayTraceBlocks.getBlockPos().equals((Object)blockPos)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        for (final EnumFacing enumFacing : EnumFacing.values()) {
            final RayTraceResult rayTraceBlocks2 = CrystalUtils.Field2499.world.rayTraceBlocks(new Vec3d(CrystalUtils.Field2499.player.posX, CrystalUtils.Field2499.player.posY + CrystalUtils.Field2499.player.getEyeHeight(), CrystalUtils.Field2499.player.posZ), new Vec3d(blockPos.getX() + 0.5 + enumFacing.getDirectionVec().getX() * 0.5, blockPos.getY() + 0.5 + enumFacing.getDirectionVec().getY() * 0.5, blockPos.getZ() + 0.5 + enumFacing.getDirectionVec().getZ() * 0.5), false, true, false);
            if (rayTraceBlocks2 != null && rayTraceBlocks2.typeOfHit.equals((Object)RayTraceResult.Type.BLOCK) && rayTraceBlocks2.getBlockPos().equals((Object)blockPos)) {
                return true;
            }
        }
        return false;
    }
    
    public static float calculateDamageEndCrystalBlockPos(final BlockPos blockPos, final Entity entity) {
        return Method2150(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, entity);
    }
    
    public static Vec3d Method2153(final Entity entity, final int n) {
        return entity.getPositionVector().add(Method2144(entity, n));
    }
    
    static {
        CrystalUtils.Field2499 = Minecraft.getMinecraft();
        CrystalUtils.Field2500 = Arrays.asList(Blocks.OBSIDIAN, Blocks.BEDROCK, Blocks.ENDER_CHEST, Blocks.ANVIL);
    }
    
    public static int getSwordSlot() {
        int currentItem = -1;
        if (Module.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SWORD) {
            currentItem = Module.mc.player.inventory.currentItem;
        }
        if (currentItem == -1) {
            for (int i = 0; i < 9; ++i) {
                if (Module.mc.player.inventory.getStackInSlot(i).getItem() == Items.DIAMOND_SWORD) {
                    currentItem = i;
                    break;
                }
            }
        }
        return currentItem;
    }
    
    public static float Method2155(final Vec3d vec3d, final AxisAlignedBB axisAlignedBB) {
        final double n = 1.0 / ((axisAlignedBB.maxX - axisAlignedBB.minX) * 2.0 + 1.0);
        final double n2 = 1.0 / ((axisAlignedBB.maxY - axisAlignedBB.minY) * 2.0 + 1.0);
        final double n3 = 1.0 / ((axisAlignedBB.maxZ - axisAlignedBB.minZ) * 2.0 + 1.0);
        final double n4 = (1.0 - Math.floor(1.0 / n) * n) / 2.0;
        final double n5 = (1.0 - Math.floor(1.0 / n3) * n3) / 2.0;
        if (n >= 0.0 && n2 >= 0.0 && n3 >= 0.0) {
            int n6 = 0;
            int n7 = 0;
            for (float n8 = 0.0f; n8 <= 1.0f; n8 += (float)n) {
                for (float n9 = 0.0f; n9 <= 1.0f; n9 += (float)n2) {
                    for (float n10 = 0.0f; n10 <= 1.0f; n10 += (float)n3) {
                        if (Method2146(new Vec3d(axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) * n8 + n4, axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) * n9, axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) * n10 + n5), vec3d) == null) {
                            ++n6;
                        }
                        ++n7;
                    }
                }
            }
            return n6 / (float)n7;
        }
        return 0.0f;
    }
    
    public static float CalculateDamageEndCrystal(final EntityEnderCrystal entityEnderCrystal, final Entity entity) {
        return Method2150(entityEnderCrystal.posX, entityEnderCrystal.posY, entityEnderCrystal.posZ, entity);
    }
}