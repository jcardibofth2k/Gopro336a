package me.darki.konas.module.combat;

import cookiedragon.eventsystem.Subscriber;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import me.darki.konas.*;
import me.darki.konas.mixin.mixins.IRenderManager;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class Auto32k
extends Module {
    public static Setting<Double> playerRange = new Setting<>("PlayerRange", 6.0, 10.0, 1.0, 0.1);
    public static Setting<Double> attackRange = new Setting<>("AttackRange", 3.6, 10.0, 1.0, 0.1);
    public static Setting<Boolean> armorCheck = new Setting<>("ArmorCheck", false);
    public static Setting<Boolean> blockShulker = new Setting<>("BlockShulker", false);
    public static Setting<Boolean> killAura = new Setting<>("KillAura", true);
    public static Setting<Auto32kMode> mode = new Setting<>("Mode", Auto32kMode.TICK);
    public static Setting<Double> delay = new Setting<>("Delay", 0.02, 1.0, 0.01, 0.1);
    public static Setting<Integer> ticks = new Setting<>("Ticks", 3, 20, 0, 1);
    public static Setting<Boolean> friends = new Setting<>("Friends", false);
    public static Setting<Boolean> walls = new Setting<>("Walls", true);
    public static Setting<Boolean> rangeCircles = new Setting<>("RangeCircles", false);
    public static Setting<Boolean> combine = new Setting<>("Combine", false).Method1191(rangeCircles::getValue);
    public static Setting<Float> width = new Setting<>("Width", Float.valueOf(2.5f), Float.valueOf(5.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).Method1191(rangeCircles::getValue);
    public static Setting<ColorValue> color = new Setting<>("Color", new ColorValue(-65536)).Method1191(rangeCircles::getValue);
    public boolean Field1504;
    public int Field1505;
    public EntityPlayer Field1506;
    public BlockPos Field1507;
    public BlockPos Field1508;
    public boolean Field1509;
    public int Field1510;
    public int Field1511;
    public int Field1512;
    public Class566 Field1513 = new Class566();

    @Subscriber
    public void onTickEvent(TickEvent tickEvent) {
        int n;
        int n2;
        if (Auto32k.mc.player == null || Auto32k.mc.world == null) {
            return;
        }
        this.Field1506 = this.Method1518(this.Method1516(false));
        if (!this.Field1504) {
            this.Field1510 = Auto32k.Method1517((Block)Blocks.HOPPER);
            this.Field1511 = this.getBlockInHotbar();
            n2 = Auto32k.Method1517(Blocks.DISPENSER);
            n = Auto32k.Method1517(Blocks.REDSTONE_BLOCK);
            this.Field1512 = Auto32k.Method1517(Blocks.OBSIDIAN);
            if (this.Field1510 == -1 || this.Field1511 == -1 || n2 == -1 || n == -1 || this.Field1512 == -1) {
                return;
            }
            if (this.Field1506 == null) {
                this.Method1514((EntityLivingBase) Auto32k.mc.player, n2, n, this.Field1512);
            } else {
                this.Method1514((EntityLivingBase)this.Field1506, n2, n, this.Field1512);
            }
            this.Field1504 = true;
        }
        if (Auto32k.mc.currentScreen instanceof GuiDispenser) {
            EnumFacing enumFacing = Auto32k.Method1520(this.Field1508);
            if (!(((Slot) Auto32k.mc.player.openContainer.inventorySlots.get((int)0)).inventory.getStackInSlot(0).getItem() instanceof ItemShulkerBox)) {
                if (Auto32k.mc.world.getBlockState(this.Field1507.up()).getBlock() instanceof BlockShulkerBox) {
                    BlockPos blockPos = this.Field1508.offset(enumFacing).offset(enumFacing);
                    if (((Boolean) blockShulker.getValue()).booleanValue() && Blocks.OBSIDIAN.canPlaceBlockAt((World) Auto32k.mc.world, blockPos) && Auto32k.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(blockPos)).isEmpty()) {
                        Class542.Method1041(blockPos);
                    }
                    Auto32k.mc.player.inventory.currentItem = this.Field1510;
                    Class542.Method1041(this.Field1507);
                    Auto32k.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.Field1507, EnumFacing.UP, EnumHand.MAIN_HAND, 0.5f, 0.5f, 0.5f));
                } else {
                    Auto32k.mc.playerController.windowClick(Auto32k.mc.player.openContainer.windowId, 0, this.Field1511, ClickType.SWAP, (EntityPlayer) Auto32k.mc.player);
                }
            } else if (!this.Field1509) {
                EnumFacing enumFacing2 = this.Method1525(this.Field1508, enumFacing);
                Class542.Method1041(this.Field1508.offset(enumFacing2));
                Auto32k.mc.player.inventory.currentItem = this.Field1512;
                this.Field1509 = true;
            }
        }
        if (!this.Method938(Auto32k.mc.player.getHeldItemMainhand()) && Auto32k.mc.currentScreen instanceof GuiHopper) {
            n2 = Auto32k.Method391(Items.AIR) == -1 ? Auto32k.mc.player.inventory.currentItem : Auto32k.Method391(Items.AIR);
            for (n = 0; n < 5; ++n) {
                if (!this.Method938(((Slot) Auto32k.mc.player.openContainer.inventorySlots.get((int)0)).inventory.getStackInSlot(n))) continue;
                Auto32k.mc.playerController.windowClick(Auto32k.mc.player.openContainer.windowId, n, n2, ClickType.SWAP, (EntityPlayer) Auto32k.mc.player);
                Auto32k.mc.player.inventory.currentItem = n2;
                break;
            }
        }
        if (((Boolean) killAura.getValue()).booleanValue() && this.Method938(Auto32k.mc.player.getHeldItemMainhand()) && this.Field1506 != null && mode.getValue() == Auto32kMode.TICK && this.Field1505 >= (Integer) ticks.getValue() && (double) Auto32k.mc.player.getDistance((Entity)this.Field1506) <= (Double) attackRange.getValue()) {
            Auto32k.mc.playerController.attackEntity((EntityPlayer) Auto32k.mc.player, (Entity)this.Field1506);
            Auto32k.mc.player.swingArm(EnumHand.MAIN_HAND);
            this.Field1505 = 0;
        }
        ++this.Field1505;
    }

    public Auto32k() {
        super("Auto32k", "Automatically kills someone using 32k weapons", Category.COMBAT, new String[0]);
    }

    public void Method1514(EntityLivingBase entityLivingBase, int n, int n2, int n3) {
        BlockPos blockPos;
        BlockPos blockPos2 = blockPos = Auto32k.mc.player == entityLivingBase ? this.Method1524() : this.Method1522(entityLivingBase);
        if (blockPos == null) {
            return;
        }
        this.Field1508 = blockPos.up();
        EnumFacing enumFacing = Auto32k.Method1520(this.Field1508);
        this.Field1507 = blockPos.offset(enumFacing);
        Auto32k.mc.player.inventory.currentItem = n3;
        Class542.Method1041(blockPos);
        Auto32k.mc.player.inventory.currentItem = n;
        Class542.Method1041(this.Field1508);
        Auto32k.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.Field1508, EnumFacing.UP, EnumHand.MAIN_HAND, 0.5f, 0.5f, 0.5f));
        Auto32k.mc.player.inventory.currentItem = n2;
    }

    public void Method1515(Entity entity, Class89 class89) {
        int n;
        Class507.Method1386();
        IRenderManager iRenderManager = (IRenderManager)mc.getRenderManager();
        float[] fArray = Color.RGBtoHSB(((ColorValue) color.getValue()).Method769(), ((ColorValue) color.getValue()).Method770(), ((ColorValue) color.getValue()).Method779(), null);
        float f = (float)(System.currentTimeMillis() % 7200L) / 7200.0f;
        int n2 = Color.getHSBColor(f, fArray[1], fArray[2]).getRGB();
        ArrayList<Vec3d> arrayList = new ArrayList<Vec3d>();
        double d = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)class89.Method436() - iRenderManager.Method69();
        double d2 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)class89.Method436() - iRenderManager.Method70();
        double d3 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)class89.Method436() - iRenderManager.Method71();
        GL11.glLineWidth((float)((Float) width.getValue()).floatValue());
        GL11.glBegin((int)1);
        for (n = 0; n <= 360; ++n) {
            Vec3d vec3d = new Vec3d(d + Math.sin((double)n * Math.PI / 180.0) * (Double) attackRange.getValue(), d2 + 0.01, d3 + Math.cos((double)n * Math.PI / 180.0) * (Double) attackRange.getValue());
            arrayList.add(vec3d);
        }
        for (n = 0; n < arrayList.size() - 1; ++n) {
            int n3 = n2 >> 24 & 0xFF;
            int n4 = n2 >> 16 & 0xFF;
            int n5 = n2 >> 8 & 0xFF;
            int n6 = n2 & 0xFF;
            if (((ColorValue) color.getValue()).Method783()) {
                GL11.glColor4f((float)((float)n4 / 255.0f), (float)((float)n5 / 255.0f), (float)((float)n6 / 255.0f), (float)((float)n3 / 255.0f));
            } else {
                GL11.glColor4f((float)((float)((ColorValue) color.getValue()).Method769() / 255.0f), (float)((float)((ColorValue) color.getValue()).Method770() / 255.0f), (float)((float)((ColorValue) color.getValue()).Method779() / 255.0f), (float)((float)((ColorValue) color.getValue()).Method782() / 255.0f));
            }
            if (!((Boolean) combine.getValue()).booleanValue() || this.Method1521(entity, ((Vec3d)arrayList.get((int)n)).x + iRenderManager.Method69(), entity.posY, ((Vec3d)arrayList.get((int)n)).z + iRenderManager.Method71())) {
                GL11.glVertex3d((double)((Vec3d)arrayList.get((int)n)).x, (double)((Vec3d)arrayList.get((int)n)).y, (double)((Vec3d)arrayList.get((int)n)).z);
                GL11.glVertex3d((double)((Vec3d)arrayList.get((int)(n + 1))).x, (double)((Vec3d)arrayList.get((int)(n + 1))).y, (double)((Vec3d)arrayList.get((int)(n + 1))).z);
            }
            n2 = Color.getHSBColor(f += 0.0027777778f, fArray[1], fArray[2]).getRGB();
        }
        GL11.glEnd();
        Class507.Method1385();
    }

    public List<EntityPlayer> Method1516(boolean bl) {
        ArrayList<EntityPlayer> arrayList = new ArrayList<EntityPlayer>();
        for (Entity entity : Auto32k.mc.world.loadedEntityList) {
            if (entity == null || !(entity instanceof EntityPlayer)) continue;
            EntityPlayer entityPlayer = (EntityPlayer)entity;
            if ((double) Auto32k.mc.player.getDistance((Entity)entityPlayer) > (bl ? (Double) playerRange.getValue() * 4.0 : (Double) playerRange.getValue()) || entityPlayer == Auto32k.mc.player || !((Boolean) walls.getValue()).booleanValue() && !Auto32k.mc.player.canEntityBeSeen((Entity)entityPlayer) && !Auto32k.Method386((Entity)entityPlayer) || entityPlayer.getHealth() <= 0.0f || entityPlayer.isDead || !((Boolean) friends.getValue()).booleanValue() && Class492.Method1989(entityPlayer.getName())) continue;
            arrayList.add(entityPlayer);
        }
        return arrayList;
    }

    @Override
    public void onEnable() {
        this.Field1509 = false;
        this.Field1505 = 0;
        this.Field1504 = false;
    }

    public static int Method1517(Block block) {
        for (int i = 0; i < 9; ++i) {
            ItemBlock itemBlock;
            ItemStack itemStack = Auto32k.mc.player.inventory.getStackInSlot(i);
            if (!(itemStack.getItem() instanceof ItemBlock) || (itemBlock = (ItemBlock)itemStack.getItem()).getBlock() != block) continue;
            return i;
        }
        return -1;
    }

    @Subscriber
    public void Method139(Class89 class89) {
        block2: {
            if (Auto32k.mc.player == null || Auto32k.mc.world == null) {
                return;
            }
            if (!((Boolean) rangeCircles.getValue()).booleanValue()) break block2;
            for (Entity entity : this.Method1516(true)) {
                this.Method1515(entity, class89);
            }
            this.Method1515((Entity) Auto32k.mc.player, class89);
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
    }

    public EntityPlayer Method1518(List<EntityPlayer> list) {
        EntityPlayer entityPlayer = null;
        double d = (Double) playerRange.getValue();
        for (EntityPlayer entityPlayer2 : list) {
            block17: {
                if (((Boolean) armorCheck.getValue()).booleanValue() && entityPlayer != null) {
                    EntityPlayer entityPlayer3 = entityPlayer;
                    EntityEquipmentSlot entityEquipmentSlot = EntityEquipmentSlot.HEAD;
                    ItemStack itemStack = entityPlayer3.getItemStackFromSlot(entityEquipmentSlot);
                    Item item = itemStack.getItem();
                    if (item == Items.DIAMOND_HELMET) break block17;
                    EntityPlayer entityPlayer4 = entityPlayer;
                    EntityEquipmentSlot entityEquipmentSlot2 = EntityEquipmentSlot.LEGS;
                    ItemStack itemStack2 = entityPlayer4.getItemStackFromSlot(entityEquipmentSlot2);
                    Item item2 = itemStack2.getItem();
                    if (item2 == Items.DIAMOND_LEGGINGS) break block17;
                    EntityPlayer entityPlayer5 = entityPlayer;
                    EntityEquipmentSlot entityEquipmentSlot3 = EntityEquipmentSlot.FEET;
                    ItemStack itemStack3 = entityPlayer5.getItemStackFromSlot(entityEquipmentSlot3);
                    Item item3 = itemStack3.getItem();
                    try {
                        if (item3 != Items.DIAMOND_BOOTS) {
                            continue;
                        }
                    }
                    catch (NullPointerException nullPointerException) {
                        System.out.println("Failed getting armor info fromt target");
                    }
                }
            }
            if (!((double) Auto32k.mc.player.getDistance((Entity)entityPlayer2) < d)) continue;
            d = Auto32k.mc.player.getDistance((Entity)entityPlayer2);
            entityPlayer = entityPlayer2;
        }
        return entityPlayer;
    }

    public static int Method391(Item item) {
        for (int i = 0; i < 9; ++i) {
            Item item2 = Auto32k.mc.player.inventory.getStackInSlot(i).getItem();
            if (item2 != item) continue;
            return i;
        }
        return -1;
    }

    public int getBlockInHotbar() {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = Auto32k.mc.player.inventory.getStackInSlot(i);
            if (!(itemStack.getItem() instanceof ItemShulkerBox)) continue;
            return i;
        }
        return -1;
    }

    public List<BlockPos> Method1519() {
        if (Auto32k.mc.player == null) {
            return null;
        }
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        for (BlockPos blockPos : Class545.Method1003(new BlockPos((Entity) Auto32k.mc.player), 3)) {
            EnumFacing enumFacing = Auto32k.Method1520(blockPos.up());
            if (enumFacing == null || enumFacing == EnumFacing.DOWN || enumFacing == EnumFacing.UP || !Class542.Method1040(blockPos, Blocks.OBSIDIAN, true) || !Blocks.DISPENSER.canPlaceBlockAt((World) Auto32k.mc.world, blockPos.up()) || !Auto32k.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(blockPos.up())).isEmpty() || this.Method1525(blockPos.up(), enumFacing) == null || !Blocks.HOPPER.canPlaceBlockAt((World) Auto32k.mc.world, blockPos.offset(enumFacing)) || !Auto32k.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(blockPos.offset(enumFacing))).isEmpty() || !Blocks.SILVER_SHULKER_BOX.canPlaceBlockAt((World) Auto32k.mc.world, blockPos.offset(enumFacing).up()) || !Auto32k.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(blockPos.offset(enumFacing).up())).isEmpty()) continue;
            arrayList.add(blockPos);
        }
        return arrayList;
    }

    public static boolean Method386(Entity entity) {
        return Auto32k.mc.world.rayTraceBlocks(new Vec3d(Auto32k.mc.player.posX, Auto32k.mc.player.posX + (double) Auto32k.mc.player.getEyeHeight(), Auto32k.mc.player.posZ), new Vec3d(entity.posX, entity.posY, entity.posZ), false, true, false) == null;
    }

    public static EnumFacing Method1520(BlockPos blockPos) {
        Vec3d vec3d = new Vec3d(Auto32k.mc.player.posX, Auto32k.mc.player.posY + (double) Auto32k.mc.player.getEyeHeight(), Auto32k.mc.player.posZ);
        Vec3d vec3d2 = new Vec3d((Vec3i)blockPos).add(0.5, 0.5, 0.5).add(new Vec3d(EnumFacing.DOWN.getDirectionVec()).scale(0.5));
        double d = vec3d2.x - vec3d.x;
        double d2 = vec3d2.z - vec3d.z;
        float f = (float)Math.toDegrees(Math.atan2(d2, d)) - 90.0f;
        float f2 = Auto32k.mc.player.rotationYaw + MathHelper.wrapDegrees((float)(f - Auto32k.mc.player.rotationYaw));
        if (Math.abs(Auto32k.mc.player.posX - (double)((float)blockPos.getX() + 0.5f)) < 2.0 && Math.abs(Auto32k.mc.player.posZ - (double)((float)blockPos.getZ() + 0.5f)) < 2.0) {
            double d3 = Auto32k.mc.player.posY + (double) Auto32k.mc.player.getEyeHeight();
            if (d3 - (double)blockPos.getY() > 2.0) {
                return EnumFacing.UP;
            }
            if ((double)blockPos.getY() - d3 > 0.0) {
                return EnumFacing.DOWN;
            }
        }
        return EnumFacing.byHorizontalIndex((int)(MathHelper.floor((double)((double)(f2 * 4.0f / 360.0f) + 0.5)) & 3)).getOpposite();
    }

    public boolean Method1521(Entity entity, double d, double d2, double d3) {
        List<EntityPlayer> list = this.Method1516(true);
        list.add((EntityPlayer) Auto32k.mc.player);
        for (EntityPlayer entityPlayer : list) {
            double d4;
            if (entityPlayer.equals((Object)entity) || entityPlayer.posY != d2 || !((d4 = Math.sqrt(Math.pow(d - ShaderUtil.Method842((Entity)entityPlayer), 2.0) + Math.pow(d3 - ShaderUtil.Method846((Entity)entityPlayer), 2.0))) < (Double) attackRange.getValue())) continue;
            return false;
        }
        return true;
    }

    public BlockPos Method1522(EntityLivingBase entityLivingBase) {
        BlockPos blockPos = null;
        double d = 0.0;
        for (BlockPos blockPos2 : this.Method1519()) {
            if (!(entityLivingBase.getDistance((double)blockPos2.getX(), (double)blockPos2.getY(), (double)blockPos2.getZ()) > d)) continue;
            blockPos = blockPos2;
        }
        return blockPos;
    }

    @Subscriber
    public void Method1523(Class89 class89) {
        block1: {
            if (Auto32k.mc.player == null || Auto32k.mc.world == null) {
                return;
            }
            if (!((Boolean) killAura.getValue()).booleanValue() || !this.Method938(Auto32k.mc.player.getHeldItemMainhand()) || this.Field1506 == null || mode.getValue() != Auto32kMode.ALWAYS || !this.Field1513.Method737((Double) delay.getValue() * 1000.0) || !((double) Auto32k.mc.player.getDistance((Entity)this.Field1506) <= (Double) attackRange.getValue())) break block1;
            Auto32k.mc.playerController.attackEntity((EntityPlayer) Auto32k.mc.player, (Entity)this.Field1506);
            Auto32k.mc.player.swingArm(EnumHand.MAIN_HAND);
            this.Field1513.Method739();
        }
    }

    public BlockPos Method1524() {
        BlockPos blockPos = null;
        double d = 420.0;
        for (BlockPos blockPos2 : this.Method1519()) {
            if (!(Auto32k.mc.player.getDistance((double)blockPos2.getX(), (double)blockPos2.getY(), (double)blockPos2.getZ()) < d)) continue;
            blockPos = blockPos2;
        }
        return blockPos;
    }

    public EnumFacing Method1525(BlockPos blockPos, EnumFacing enumFacing) {
        for (EnumFacing enumFacing2 : EnumFacing.values()) {
            if (enumFacing2 == EnumFacing.DOWN || enumFacing2 == enumFacing || !Blocks.REDSTONE_BLOCK.canPlaceBlockAt((World) Auto32k.mc.world, blockPos.offset(enumFacing2)) || !Auto32k.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(blockPos.offset(enumFacing2))).isEmpty()) continue;
            return enumFacing2;
        }
        return null;
    }

    public boolean Method938(ItemStack itemStack) {
        return EnchantmentHelper.getEnchantmentLevel((Enchantment)Enchantments.SHARPNESS, (ItemStack)itemStack) >= 1000;
    }
}
