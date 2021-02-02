package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.NewGui;
import me.darki.konas.setting.Setting;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Class290
extends Module {
    public Setting<ParentSetting> Field1663 = new Setting<>("AntiCheat", new ParentSetting(false));
    public Setting<Boolean> Field1664 = new Setting<>("Rotate", false).setParentSetting(this.Field1663);
    public Setting<Boolean> Field1665 = new Setting<>("Swing", true).setParentSetting(this.Field1663);
    public Setting<Boolean> Field1666 = new Setting<>("AirPlace", false).setParentSetting(this.Field1663);
    public Setting<Boolean> Field1667 = new Setting<>("RayTrace", false).setParentSetting(this.Field1663);
    public Setting<Boolean> Field1668 = new Setting<>("StrictDirection", false).setParentSetting(this.Field1663);
    public Setting<ParentSetting> Field1669 = new Setting<>("Ranges", new ParentSetting(false));
    public Setting<Float> Field1670 = new Setting<>("BreakRange", Float.valueOf(6.0f), Float.valueOf(6.0f), Float.valueOf(1.0f), Float.valueOf(0.1f)).setParentSetting(this.Field1669);
    public Setting<Float> Field1671 = new Setting<>("PlaceRange", Float.valueOf(5.0f), Float.valueOf(6.0f), Float.valueOf(1.0f), Float.valueOf(0.1f)).setParentSetting(this.Field1669);
    public Setting<ParentSetting> Field1672 = new Setting<>("Speeds", new ParentSetting(false));
    public Setting<Integer> Field1673 = new Setting<>("BreakSpeed", 20, 20, 1, 1).setParentSetting(this.Field1672);
    public Setting<Integer> Field1674 = new Setting<>("PlaceSpeed", 20, 20, 1, 1).setParentSetting(this.Field1672);
    public Setting<ParentSetting> Field1675 = new Setting<>("Misc", new ParentSetting(false));
    public Setting<Boolean> Field1676 = new Setting<>("Swap", true).setParentSetting(this.Field1675);
    public Setting<Boolean> Field1677 = new Setting<>("AutoMove", true).setParentSetting(this.Field1675);
    public Class566 Field1678 = new Class566();
    public Class566 Field1679 = new Class566();
    public Class566 Field1680 = new Class566();
    public static boolean Field1681;
    public static double Field1682;
    public static double Field1683;
    public BlockPos Field1684 = null;
    public BlockPos Field1685 = null;
    public EnumFacing Field1686 = null;
    public int Field1687 = -1;

    public static boolean Method1584(EntityPlayer entityPlayer) {
        return !entityPlayer.isDead;
    }

    public static float[] Method1585(EnumFacing enumFacing) {
        switch (enumFacing) {
            case DOWN: {
                return new float[]{Class290.mc.player.rotationYaw, 90.0f};
            }
            case UP: {
                return new float[]{Class290.mc.player.rotationYaw, -90.0f};
            }
            case NORTH: {
                return new float[]{180.0f, 0.0f};
            }
            case SOUTH: {
                return new float[]{0.0f, 0.0f};
            }
            case WEST: {
                return new float[]{90.0f, 0.0f};
            }
        }
        return new float[]{270.0f, 0.0f};
    }

    public static boolean Method1586(TileEntity tileEntity) {
        return ((TileEntityBed)tileEntity).isHeadPiece();
    }

    public static float Method1587(float f) {
        int n = Minecraft.getMinecraft().world.getDifficulty().getId();
        return f * (n == 0 ? 0.0f : (n == 2 ? 1.0f : (n == 1 ? 0.5f : 1.5f)));
    }

    @Subscriber(priority=8)
    public void Method123(Class50 class50) {
        if (this.Field1684 != null) {
            this.Method1598(this.Field1684);
        } else if (this.Field1685 != null) {
            this.Method124();
        }
        if (this.Field1687 != -1) {
            if (!this.Method519()) {
                Class290.mc.player.inventory.currentItem = this.Field1687;
                Class290.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.Field1687));
                this.Field1687 = -1;
            }
        }
    }

    public boolean Method512(BlockPos blockPos) {
        return (double)(Class290.mc.player.getHealth() + Class290.mc.player.getAbsorptionAmount() - Class290.Method1590((double)blockPos.getX() + 0.5, blockPos.getY() + 1, (double)blockPos.getZ() + 0.5, (Entity)Class290.mc.player)) > 0.5;
    }

    public static double Method1588(BlockPos blockPos) {
        return Class290.mc.player.getDistanceSq(blockPos);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Subscriber(priority=100)
    public void Method135(UpdateEvent updateEvent) {
        block11: {
            int n;
            block12: {
                block9: {
                    block10: {
                        if (Class290.mc.player == null || Class290.mc.world == null) {
                            return;
                        }
                        this.Field1684 = null;
                        this.Field1685 = null;
                        if (updateEvent.isCanceled() || !Class496.Method1959((Boolean)this.Field1664.getValue())) {
                            return;
                        }
                        if (Class290.mc.player.dimension == 0) {
                            return;
                        }
                        if (this.Field1678.Method737(1000 - (Integer)this.Field1673.getValue() * 50)) {
                            this.Field1684 = this.Method1593();
                        }
                        if (this.Field1684 != null || !this.Field1679.Method737(1000 - (Integer)this.Field1674.getValue() * 50)) break block9;
                        if (Class290.mc.player.inventory.getCurrentItem().getItem() != Items.BED && !this.Method519()) break block10;
                        this.Method517();
                        break block11;
                    }
                    if (this.Method1553().isEmpty() || !((Boolean)this.Field1676.getValue()).booleanValue() || this.Method519()) break block11;
                    break block12;
                }
                if (this.Field1684 == null) break block11;
                double[] dArray = MathUtil.Method1088((double)this.Field1684.getX() + 0.5, (double)this.Field1684.getY() + 0.5, (double)this.Field1684.getZ() + 0.5, (EntityPlayer)Class290.mc.player);
                Field1682 = dArray[0];
                Field1683 = dArray[1];
                Field1681 = true;
                this.Field1680.Method739();
                break block11;
            }
            for (n = 0; n < 9; ++n) {
                ItemStack itemStack = (ItemStack)Class290.mc.player.inventory.mainInventory.get(n);
                if (itemStack.getItem() != Items.BED) continue;
                this.Field1687 = Class290.mc.player.inventory.currentItem;
                Class290.mc.player.inventory.currentItem = n;
                Class290.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n));
                this.Method517();
                break;
            }
            if (((Boolean)this.Field1677.getValue()).booleanValue() && Class290.mc.player.inventory.getCurrentItem().getItem() != Items.BED) {
                for (n = 9; n <= 35; ++n) {
                    if (Class290.mc.player.inventory.getStackInSlot(n).getItem() != Items.BED) continue;
                    Class290.mc.playerController.windowClick(0, n, 0, ClickType.PICKUP, (EntityPlayer)Class290.mc.player);
                    Class290.mc.playerController.windowClick(0, Class290.mc.player.inventory.currentItem < 9 ? Class290.mc.player.inventory.currentItem + 36 : Class290.mc.player.inventory.currentItem, 0, ClickType.PICKUP, (EntityPlayer)Class290.mc.player);
                    Class290.mc.playerController.windowClick(0, n, 0, ClickType.PICKUP, (EntityPlayer)Class290.mc.player);
                }
            }
        }
        if (Field1681) {
            NewGui.INSTANCE.Field1139.Method1937((float)Field1682, (float)Field1683);
        }
        if (this.Field1680.Method737(450.0)) {
            Field1681 = false;
        }
    }

    public static boolean Method1589(TileEntity tileEntity) {
        return tileEntity instanceof TileEntityBed;
    }

    public boolean Method522(BlockPos blockPos) {
        IBlockState iBlockState = Class290.mc.world.getBlockState(blockPos.up());
        if (iBlockState.getBlock() == Blocks.AIR) {
            return Class290.mc.world.getBlockState(blockPos).isSideSolid((IBlockAccess)Class290.mc.world, blockPos, EnumFacing.UP);
        }
        return false;
    }

    public static float Method1590(double d, double d2, double d3, Entity entity) {
        float f = 12.0f;
        double d4 = entity.getDistance(d, d2, d3) / 12.0;
        Vec3d vec3d = new Vec3d(d, d2, d3);
        double d5 = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        double d6 = (1.0 - d4) * d5;
        float f2 = (int)((d6 * d6 + d6) / 2.0 * 7.0 * 12.0 + 1.0);
        double d7 = 1.0;
        if (entity instanceof EntityLivingBase) {
            d7 = Class290.Method1597((EntityLivingBase)entity, Class290.Method1587(f2), new Explosion((World)Minecraft.getMinecraft().world, (Entity)null, d, d2, d3, 6.0f, false, true));
        }
        return (float)d7;
    }

    public List<EntityPlayer> Method1553() {
        return Class290.mc.world.playerEntities.stream().filter(Class290::Method126).filter(Class290::Method132).filter(Class290::Method1584).filter(Class290::Method138).filter(Class290::Method141).filter(this::Method122).sorted(Comparator.comparing(Class290::Method1056)).collect(Collectors.toList());
    }

    public static boolean Method141(EntityPlayer entityPlayer) {
        return entityPlayer != Class290.mc.player;
    }

    public boolean Method519() {
        return Class290.mc.player.getHeldItemOffhand().getItem() instanceof ItemBed;
    }

    public static Float Method1056(EntityPlayer entityPlayer) {
        return Float.valueOf(Class290.mc.player.getDistance((Entity)entityPlayer));
    }

    public void Method1591(BlockPos blockPos, boolean bl) {
        if (Class290.mc.world.getBlockState(blockPos).getBlock() == Blocks.BED) {
            return;
        }
        float f = this.Method1595(blockPos, (Entity)Class290.mc.player);
        if ((double)f > (double)Class290.mc.player.getHealth() + (double)Class290.mc.player.getAbsorptionAmount() + 0.5) {
            if (bl && ((Boolean)this.Field1666.getValue()).booleanValue()) {
                this.Method1591(blockPos.up(), false);
            }
            return;
        }
        if (!Class290.mc.world.getBlockState(blockPos).getMaterial().isReplaceable()) {
            if (bl && ((Boolean)this.Field1666.getValue()).booleanValue()) {
                this.Method1591(blockPos.up(), false);
            }
            return;
        }
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        HashMap<BlockPos, EnumFacing> hashMap = new HashMap<BlockPos, EnumFacing>();
        for (EnumFacing enumFacing : EnumFacing.values()) {
            BlockPos blockPos2;
            if (enumFacing == EnumFacing.DOWN || enumFacing == EnumFacing.UP) continue;
            BlockPos blockPos3 = blockPos.offset(enumFacing);
            if (!(Class290.mc.player.getDistanceSq(blockPos2) <= Math.pow(((Float)this.Field1671.getValue()).floatValue(), 2.0)) || !Class290.mc.world.getBlockState(blockPos3).getMaterial().isReplaceable() || Class290.mc.world.getBlockState(blockPos3.down()).getMaterial().isReplaceable() || ((Boolean)this.Field1664.getValue()).booleanValue() && EnumFacing.fromAngle((double)Class545.Method999(blockPos3, EnumFacing.UP, (EntityPlayer)Class290.mc.player)[0]).getOpposite() != enumFacing) continue;
            if (((Boolean)this.Field1667.getValue()).booleanValue()) {
                if (Class290.mc.world.rayTraceBlocks(Class290.mc.player.getPositionEyes(1.0f), new Vec3d((double)blockPos3.getX() + 0.5, (double)(blockPos3.getY() + 1), (double)blockPos3.getZ() + 0.5)) != null) continue;
            }
            arrayList.add(blockPos3);
            hashMap.put(blockPos3, enumFacing.getOpposite());
        }
        if (arrayList.isEmpty()) {
            if (bl && ((Boolean)this.Field1666.getValue()).booleanValue()) {
                this.Method1591(blockPos.up(), false);
            }
            return;
        }
        arrayList.sort(Comparator.comparingDouble(Class290::Method1588));
        this.Field1685 = (BlockPos)arrayList.get(0);
        this.Field1686 = (EnumFacing)hashMap.get(this.Field1685);
        if (((Boolean)this.Field1664.getValue()).booleanValue()) {
            float[] fArray = RotationUtil.Method1946(Class290.mc.player.getPositionEyes(1.0f), new Vec3d((double)this.Field1685.down().getX() + 0.5, (double)(this.Field1685.down().getY() + 1), (double)this.Field1685.down().getZ() + 0.5));
            Field1682 = fArray[0];
            Field1683 = fArray[1];
            Field1681 = true;
        } else {
            float[] fArray = Class290.Method1585(this.Field1686);
            Field1682 = fArray[0];
            Field1683 = fArray[1];
            Field1681 = true;
        }
        this.Field1680.Method739();
    }

    public static Double Method1592(TileEntity tileEntity) {
        return Class290.mc.player.getDistance((double)tileEntity.getPos().getX(), (double)tileEntity.getPos().getY(), (double)tileEntity.getPos().getZ());
    }

    public BlockPos Method1593() {
        TileEntityBed tileEntityBed = Class290.mc.world.loadedTileEntityList.stream().filter(Class290::Method1589).filter(Class290::Method1586).filter(this::Method1600).filter(this::Method1594).min(Comparator.comparing(Class290::Method1592)).orElse(null);
        if (tileEntityBed != null) {
            return tileEntityBed.getPos();
        }
        return null;
    }

    public static boolean Method138(EntityPlayer entityPlayer) {
        return !Class492.Method1989(entityPlayer.getName());
    }

    public void Method124() {
        Vec3d vec3d = new Vec3d((Vec3i)this.Field1685.down()).add(0.5, 0.5, 0.5).add(new Vec3d(this.Field1686.getOpposite().getDirectionVec()).scale(0.5));
        Class290.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Class290.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        Class496.Method1969(this.Field1685.down(), vec3d, this.Method519() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, EnumFacing.UP, true, (Boolean)this.Field1665.getValue());
        Class290.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Class290.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        this.Field1679.Method739();
        this.Field1685 = null;
    }

    public boolean Method1594(TileEntity tileEntity) {
        return this.Method512(tileEntity.getPos());
    }

    public float Method1595(BlockPos blockPos, Entity entity) {
        return Class290.Method1590((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 1.0, (double)blockPos.getZ() + 0.5, entity);
    }

    public static BlockPos Method1596() {
        return new BlockPos(Math.floor(Class290.mc.player.posX), Math.floor(Class290.mc.player.posY), Math.floor(Class290.mc.player.posZ));
    }

    public Class290() {
        super("BedAura", Category.COMBAT, new String[0]);
    }

    public static float Method1597(EntityLivingBase entityLivingBase, float f, Explosion explosion) {
        if (entityLivingBase instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer)entityLivingBase;
            DamageSource damageSource = DamageSource.causeExplosionDamage((Explosion)explosion);
            f = CombatRules.getDamageAfterAbsorb((float)f, (float)entityPlayer.getTotalArmorValue(), (float)((float)entityPlayer.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue()));
            int n = EnchantmentHelper.getEnchantmentModifierDamage((Iterable)entityPlayer.getArmorInventoryList(), (DamageSource)damageSource);
            float f2 = MathHelper.clamp((float)n, (float)0.0f, (float)20.0f);
            f *= 1.0f - f2 / 25.0f;
            if (entityLivingBase.isPotionActive(Potion.getPotionById((int)11))) {
                f -= f / 4.0f;
            }
            return f;
        }
        f = CombatRules.getDamageAfterAbsorb((float)f, (float)entityLivingBase.getTotalArmorValue(), (float)((float)entityLivingBase.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue()));
        return f;
    }

    public static boolean Method126(EntityPlayer entityPlayer) {
        return !Class546.Method963((Entity)entityPlayer);
    }

    public void Method517() {
        List<EntityPlayer> list = this.Method1553();
        if (list.isEmpty()) {
            return;
        }
        this.Method1591(new BlockPos((Entity)list.get(0)), true);
    }

    public void Method1598(BlockPos blockPos) {
        if (blockPos == null) {
            return;
        }
        RayTraceResult rayTraceResult = (Boolean)this.Field1668.getValue() != false ? Class290.mc.world.rayTraceBlocks(Class290.mc.player.getPositionEyes(1.0f), new Vec3d((double)blockPos.getX() + 0.5, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5)) : null;
        Vec3d vec3d = new Vec3d((Vec3i)blockPos).add(0.5, 0.5, 0.5);
        EnumFacing enumFacing = rayTraceResult == null || rayTraceResult.sideHit == null ? EnumFacing.UP : rayTraceResult.sideHit;
        Class496.Method1969(blockPos, vec3d, EnumHand.MAIN_HAND, enumFacing, true, (Boolean)this.Field1665.getValue());
        this.Field1678.Method739();
    }

    public List<BlockPos> Method1599(BlockPos blockPos, float f, float f2, boolean bl, boolean bl2, int n) {
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        int n2 = blockPos.getX();
        int n3 = blockPos.getY();
        int n4 = blockPos.getZ();
        int n5 = n2 - (int)f;
        while ((float)n5 <= (float)n2 + f) {
            int n6 = n4 - (int)f;
            while ((float)n6 <= (float)n4 + f) {
                int n7 = bl2 ? n3 - (int)f : n3;
                while (true) {
                    float f3 = n7;
                    float f4 = bl2 ? (float)n3 + f : (float)n3 + f2;
                    if (!(f3 < f4)) break;
                    double d = (n2 - n5) * (n2 - n5) + (n4 - n6) * (n4 - n6) + (bl2 ? (n3 - n7) * (n3 - n7) : 0);
                    if (!(!(d < (double)(f * f)) || bl && d < (double)((f - 1.0f) * (f - 1.0f)))) {
                        BlockPos blockPos2 = new BlockPos(n5, n7 + n, n6);
                        arrayList.add(blockPos2);
                    }
                    ++n7;
                }
                ++n6;
            }
            ++n5;
        }
        return arrayList;
    }

    public boolean Method1600(TileEntity tileEntity) {
        return Class290.mc.player.getDistance((double)tileEntity.getPos().getX(), (double)tileEntity.getPos().getY(), (double)tileEntity.getPos().getZ()) <= (double)((Float)this.Field1670.getValue()).floatValue();
    }

    public boolean Method122(EntityPlayer entityPlayer) {
        return Class290.mc.player.getDistance((Entity)entityPlayer) < ((Float)this.Field1671.getValue()).floatValue() + 2.0f;
    }

    public static boolean Method132(EntityPlayer entityPlayer) {
        return !Class546.Method963((Entity)entityPlayer);
    }
}
