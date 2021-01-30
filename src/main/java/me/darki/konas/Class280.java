package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;

import me.darki.konas.mixin.mixins.ICPacketPlayer;
import me.darki.konas.mixin.mixins.IGuiEditSign;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.block.BlockSign;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class Class280
extends Module {
    public static Setting<Boolean> Field1769 = new Setting<>("Grass", true);
    public static Setting<Boolean> Field1770 = new Setting<>("AutoSign", true);
    public static Setting<Boolean> Field1771 = new Setting<>("SourceRemover", true);
    public static Setting<Boolean> Field1772 = new Setting<>("AutoSwitch", true);
    public static Setting<Integer> Field1773 = new Setting<>("PlaceDelay", 40, 100, 1, 1);
    public static Setting<Integer> Field1774 = new Setting<>("BreakDelay", 2, 20, 1, 1);
    public static Setting<Integer> Field1775 = new Setting<>("RangeXZ", 3, 10, 1, 1);
    public static Setting<Integer> Field1776 = new Setting<>("RangeY", 3, 10, 1, 1);
    public static Setting<Boolean> Field1777 = new Setting<>("Rotations", true);
    public ArrayList<BlockPos> Field1778 = new ArrayList();
    public ArrayList<BlockPos> Field1779 = new ArrayList();
    public static ArrayList<String> Field1780 = new ArrayList();
    public BlockPos Field1781 = null;
    public EnumFacing Field1782 = null;
    public int Field1783;
    public boolean Field1784;
    public float Field1785;
    public float Field1786;
    public Class566 Field1787 = new Class566();

    public static Float Method1676(BlockPos blockPos) {
        return Float.valueOf(MathHelper.sqrt((double)Class280.mc.player.getDistanceSq(blockPos)));
    }

    public boolean Method526(BlockPos blockPos) {
        return !this.Field1778.contains(blockPos);
    }

    @Subscriber
    public void Method536(Class24 class24) {
        block3: {
            if (Class280.mc.world == null || Class280.mc.player == null) {
                return;
            }
            if (!(class24.getPacket() instanceof CPacketPlayer) || !this.Field1784) break block3;
            CPacketPlayer cPacketPlayer = (CPacketPlayer)class24.getPacket();
            if (class24.getPacket() instanceof CPacketPlayer.Position) {
                class24.setCanceled(true);
                Class280.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(cPacketPlayer.getX(Class280.mc.player.posX), cPacketPlayer.getY(Class280.mc.player.posY), cPacketPlayer.getZ(Class280.mc.player.posZ), this.Field1785, this.Field1786, cPacketPlayer.isOnGround()));
            } else {
                ((ICPacketPlayer)cPacketPlayer).Method1695(this.Field1785);
                ((ICPacketPlayer)cPacketPlayer).Method1697(this.Field1786);
            }
        }
    }

    public Class280() {
        super("BlockAura", Category.MISC, "Lawnmower", "LiquidRemover");
        Field1780.add("Signed by <player>");
        Field1780.add("using Konas");
        Field1780.add("<date>");
    }

    @Subscriber
    public void Method948(Class56 class56) {
        block13: {
            block12: {
                if (!((Boolean)Field1771.getValue()).booleanValue()) {
                    return;
                }
                if (!(class56 instanceof UpdateEvent)) break block12;
                this.Field1781 = null;
                this.Field1782 = null;
                int n = this.Method464();
                this.Field1783 = -1;
                Iterable iterable = BlockPos.getAllInBox((BlockPos)Class280.mc.player.getPosition().add(-((Integer)Field1775.getValue()).intValue(), -((Integer)Field1776.getValue()).intValue(), -((Integer)Field1775.getValue()).intValue()), (BlockPos)Class280.mc.player.getPosition().add(((Integer)Field1775.getValue()).intValue(), ((Integer)Field1776.getValue()).intValue(), ((Integer)Field1775.getValue()).intValue()));
                BlockPos blockPos = StreamSupport.stream(iterable.spliterator(), false).filter(Class280::Method512).filter(Class280::Method1683).min(Comparator.comparing(Class280::Method1686)).orElse(null);
                if (blockPos != null) {
                    if (Class280.mc.world.getBlockState(blockPos).getMaterial().isReplaceable()) {
                        Optional<Class534> optional = Class545.Method997(blockPos);
                        this.Field1783 = n;
                        if (optional.isPresent()) {
                            this.Field1781 = optional.get().Field1089;
                            this.Field1782 = optional.get().Field1090;
                            double[] dArray = Class545.Method1008(this.Field1781.getX(), this.Field1781.getY(), this.Field1781.getZ(), this.Field1782, (EntityPlayer)Class280.mc.player);
                            this.Field1785 = (float)dArray[0];
                            this.Field1786 = (float)dArray[1];
                            this.Field1784 = true;
                            return;
                        }
                    } else if (this.Field1787.Method737((Integer)Field1773.getValue() * 10)) {
                        this.Field1784 = false;
                        return;
                    }
                }
                break block13;
            }
            if (this.Field1781 == null || this.Field1782 == null || !this.Field1787.Method737((Integer)Field1773.getValue() * 2) || this.Field1783 == -1) break block13;
            boolean bl = Class280.mc.player.inventory.currentItem != this.Field1783;
            boolean bl2 = Class280.mc.player.isSprinting();
            int n = Class280.mc.player.inventory.currentItem;
            boolean bl3 = Class545.Method1004(this.Field1781);
            if (bl) {
                Class280.mc.player.inventory.currentItem = this.Field1783;
                Class280.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.Field1783));
            }
            if (bl2) {
                Class280.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Class280.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            if (bl3) {
                Class280.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Class280.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            Class280.mc.playerController.processRightClickBlock(Class280.mc.player, Class280.mc.world, this.Field1781, this.Field1782, new Vec3d((Vec3i)this.Field1781).add(0.5, 0.5, 0.5).add(new Vec3d(this.Field1782.getDirectionVec()).scale(0.5)), EnumHand.MAIN_HAND);
            Class280.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
            if (bl3) {
                Class280.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Class280.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            if (bl2) {
                Class280.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Class280.mc.player, CPacketEntityAction.Action.START_SPRINTING));
            }
            if (bl) {
                Class280.mc.player.inventory.currentItem = n;
                Class280.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n));
            }
            this.Field1787.Method739();
        }
    }

    public void Method1677(double d, double d2, double d3, EntityPlayer entityPlayer) {
        double[] dArray = MathUtil.Method1088(d, d2, d3, entityPlayer);
        Class280.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation((float)dArray[0], (float)dArray[1], false));
    }

    public static Float Method1678(BlockPos blockPos) {
        return Float.valueOf(MathHelper.sqrt((double)Class280.mc.player.getDistanceSq(blockPos)));
    }

    public static boolean Method522(BlockPos blockPos) {
        return Class280.mc.world.getBlockState(blockPos).getMaterial() == Material.VINE || Class280.mc.world.getBlockState(blockPos).getMaterial() == Material.PLANTS;
    }

    public boolean Method515(BlockPos blockPos) {
        return !this.Method1682(blockPos);
    }

    public void Method1679(BlockPos blockPos) {
        if (((Boolean)Field1777.getValue()).booleanValue()) {
            this.Method1677((double)blockPos.getX() + 0.5, blockPos.getY(), (double)blockPos.getZ() + 0.5, (EntityPlayer)Class280.mc.player);
        }
        Class280.mc.playerController.clickBlock(blockPos, EnumFacing.UP);
        Class280.mc.player.swingArm(EnumHand.MAIN_HAND);
        this.Field1778.remove(blockPos);
    }

    @Subscriber
    public void Method1451(Class654 class654) {
        block1: {
            if (Class280.mc.world == null || Class280.mc.player == null) {
                return;
            }
            if (!(class654.Method1161() instanceof GuiEditSign) || !((Boolean)Field1770.getValue()).booleanValue()) break block1;
            GuiEditSign guiEditSign = (GuiEditSign)class654.Method1161();
            TileEntitySign tileEntitySign = ((IGuiEditSign)guiEditSign).Method48();
            this.Method1680(tileEntitySign.getPos());
            class654.Cancel();
        }
    }

    public void Method1680(BlockPos blockPos) {
        ITextComponent[] iTextComponentArray = new ITextComponent[]{new TextComponentString(""), new TextComponentString(""), new TextComponentString(""), new TextComponentString("")};
        iTextComponentArray[0] = new TextComponentString(Field1780.get(0).replaceAll("<player>", Class280.mc.player.getName()).replaceAll("<date>", new SimpleDateFormat("dd/MM/yy").format(new Date())));
        iTextComponentArray[1] = new TextComponentString(Field1780.get(1).replaceAll("<player>", Class280.mc.player.getName()).replaceAll("<date>", new SimpleDateFormat("dd/MM/yy").format(new Date())));
        iTextComponentArray[2] = new TextComponentString(Field1780.get(2).replaceAll("<player>", Class280.mc.player.getName()).replaceAll("<date>", new SimpleDateFormat("dd/MM/yy").format(new Date())));
        iTextComponentArray[3] = new TextComponentString(Field1780.get(3).replaceAll("<player>", Class280.mc.player.getName()).replaceAll("<date>", new SimpleDateFormat("dd/MM/yy").format(new Date())));
        Class280.mc.player.connection.sendPacket((Packet)new CPacketUpdateSign(blockPos, iTextComponentArray));
    }

    public boolean Method1681(BlockPos blockPos) {
        return !this.Field1779.contains(blockPos);
    }

    public boolean Method1682(BlockPos blockPos) {
        TileEntitySign tileEntitySign = (TileEntitySign)Class280.mc.world.getTileEntity(blockPos);
        if (tileEntitySign == null) {
            return false;
        }
        return tileEntitySign.signText[0].getUnformattedText().equals(Field1780.get(0)) && tileEntitySign.signText[1].getUnformattedText().equals(Field1780.get(1)) && tileEntitySign.signText[2].getUnformattedText().equals(Field1780.get(2)) && tileEntitySign.signText[3].getUnformattedText().equals(Field1780.get(3));
    }

    public static boolean Method1683(BlockPos blockPos) {
        return Class280.mc.world.getBlockState(blockPos).getBlock().getMetaFromState(Class280.mc.world.getBlockState(blockPos)) == 0;
    }

    public int Method464() {
        ItemStack itemStack = Class280.mc.player.getHeldItemMainhand();
        if (!itemStack.isEmpty() && itemStack.getItem() instanceof ItemBlock) {
            return Class280.mc.player.inventory.currentItem;
        }
        if (((Boolean)Field1772.getValue()).booleanValue()) {
            for (int i = 0; i < 9; ++i) {
                itemStack = Class280.mc.player.inventory.getStackInSlot(i);
                if (itemStack.isEmpty() || !(itemStack.getItem() instanceof ItemBlock)) continue;
                return i;
            }
        }
        return -1;
    }

    public static boolean Method512(BlockPos blockPos) {
        return Class280.mc.world.getBlockState(blockPos).getMaterial() == Material.WATER || Class280.mc.world.getBlockState(blockPos).getMaterial() == Material.LAVA;
    }

    public static boolean Method1684(BlockPos blockPos) {
        return Class280.mc.world.getBlockState(blockPos).getBlock() instanceof BlockSign;
    }

    @Subscriber
    public void Method462(TickEvent tickEvent) {
        block4: {
            if (Class280.mc.world == null || Class280.mc.player == null) {
                return;
            }
            this.Field1778.clear();
            Iterable iterable = BlockPos.getAllInBox((BlockPos)Class280.mc.player.getPosition().add(-((Integer)Field1775.getValue()).intValue(), -((Integer)Field1776.getValue()).intValue(), -((Integer)Field1775.getValue()).intValue()), (BlockPos)Class280.mc.player.getPosition().add(((Integer)Field1775.getValue()).intValue(), ((Integer)Field1776.getValue()).intValue(), ((Integer)Field1775.getValue()).intValue()));
            if (((Boolean)Field1769.getValue()).booleanValue()) {
                this.Field1778 = (ArrayList)StreamSupport.stream(iterable.spliterator(), false).filter(Class280::Method522).filter(this::Method526).sorted(Comparator.comparing(Class280::Method1678)).collect(Collectors.toList());
                if (!this.Field1778.isEmpty() && Class280.mc.player.ticksExisted % (Integer)Field1774.getValue() == 0) {
                    this.Method1679(this.Field1778.get(0));
                }
            }
            if (!((Boolean)Field1770.getValue()).booleanValue()) break block4;
            this.Field1779 = (ArrayList)StreamSupport.stream(iterable.spliterator(), false).filter(Class280::Method1684).filter(this::Method1681).filter(this::Method515).sorted(Comparator.comparing(Class280::Method1676)).collect(Collectors.toList());
            if (!this.Field1779.isEmpty() && Class280.mc.player.ticksExisted % (Integer)Field1774.getValue() == 0) {
                this.Method1680(this.Field1779.get(0));
            }
        }
    }

    public static boolean Method1685(Iterator<?> iterator, @Nullable Object object) {
        if (object == null) {
            while (iterator.hasNext()) {
                if (iterator.next() != null) continue;
                return true;
            }
        } else {
            while (iterator.hasNext()) {
                if (!object.equals(iterator.next())) continue;
                return true;
            }
        }
        return false;
    }

    public static Float Method1686(BlockPos blockPos) {
        return Float.valueOf(MathHelper.sqrt((double)Class280.mc.player.getDistanceSq(blockPos)));
    }
}
