package me.darki.konas.module.combat;

import cookiedragon.eventsystem.Subscriber;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import me.darki.konas.command.Logger;
import me.darki.konas.event.events.PacketEvent;
import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.mixin.mixins.IRenderManager;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.module.player.FastUse;
import me.darki.konas.setting.ParentSetting;
import me.darki.konas.setting.Setting;
import me.darki.konas.unremaped.*;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.util.RotationUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.*;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class PistonAura
extends Module {
    public Setting<Class214> mode = new Setting<>("Mode", Class214.DAMAGE);
    public Setting<Boolean> smart = new Setting<>("Smart", true).visibleIf(this::Method396);
    public Setting<Boolean> disableAfterPush = new Setting<>("DisableAfterPush", true).visibleIf(this::Method394);
    public Setting<Boolean> disableWhenNone = new Setting<>("DisableWhenNone", false).visibleIf(this::Method388);
    public Setting<Integer> targetRange = new Setting<>("TargetRange", 3, 6, 1, 1);
    public Setting<Integer> delay = new Setting<>("Delay", 25, 100, 0, 1);
    public static Setting<Integer> actionShift = new Setting<>("ActionShift", 3, 8, 1, 1);
    public static Setting<Integer> actionInterval = new Setting<>("ActionInterval", 0, 10, 0, 1);
    public Setting<Boolean> strict = new Setting<>("Strict", false);
    public Setting<Boolean> rayTrace = new Setting<>("RayTrace", false);
    public Setting<Boolean> antiSuicide = new Setting<>("AntiSuicide", false);
    public Setting<Boolean> mine = new Setting<>("Mine", false);
    public static Setting<ParentSetting> render = new Setting<>("Render", new ParentSetting(false));
    public static Setting<Boolean> current = new Setting<>("Current", true).setParentSetting(Field361);
    public static Setting<ColorValue> colorC = new Setting<>("ColorC", new ColorValue(1354711231)).setParentSetting(Field361);
    public static Setting<ColorValue> outlineC = new Setting<>("OutlineC", new ColorValue(-4243265)).setParentSetting(Field361);
    public static Setting<Boolean> full = new Setting<>("Full", true).setParentSetting(Field361);
    public static Setting<ColorValue> colorF = new Setting<>("ColorF", new ColorValue(817840192)).setParentSetting(Field361);
    public static Setting<ColorValue> outlineF = new Setting<>("OutlineF", new ColorValue(-809549760)).setParentSetting(Field361);
    public static Setting<Boolean> arrow = new Setting<>("Arrow", true).setParentSetting(Field361);
    public static Setting<ColorValue> arrowColor = new Setting<>("ArrowColor", new ColorValue(-65281)).setParentSetting(Field361);
    public static Setting<Boolean> top = new Setting<>("Top", false).setParentSetting(Field361);
    public static Setting<Boolean> bottom = new Setting<>("Bottom", true).setParentSetting(Field361);
    public Class220 Field372 = Class220.SEARCHING;
    public BlockPos Field373;
    public EnumFacing Field374;
    public BlockPos Field375;
    public BlockPos Field376;
    public EnumFacing Field377;
    public BlockPos Field378;
    public TimerUtil Field379 = new TimerUtil();
    public boolean Field380;
    public TimerUtil Field381 = new TimerUtil();
    public int Field382;
    public TimerUtil Field383 = new TimerUtil();
    public Runnable Field384 = null;
    public int Field385 = 0;
    public BlockPos Field386 = null;
    public TimerUtil Field387 = new TimerUtil();
    public TimerUtil Field388 = new TimerUtil();

    public boolean Method138(EntityPlayer entityPlayer) {
        if (this.Method527() == -1) {
            Logger.Method1119("No redstone found!");
            this.toggle();
            return false;
        }
        BlockPos blockPos = new BlockPos(entityPlayer).up();
        if (this.Method522(blockPos)) {
            return true;
        }
        blockPos = new BlockPos(entityPlayer).up().up();
        return this.Method522(blockPos);
    }

    public static boolean Method512(BlockPos blockPos) {
        if (PistonAura.mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && PistonAura.mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
            return false;
        }
        BlockPos blockPos2 = blockPos.add(0, 1, 0);
        if (PistonAura.mc.world.getBlockState(blockPos2).getBlock() != Blocks.AIR && PistonAura.mc.world.getBlockState(blockPos2).getBlock() != Blocks.PISTON_HEAD) {
            return false;
        }
        BlockPos blockPos3 = blockPos.add(0, 2, 0);
        if (!AutoCrystal.protocol.getValue().booleanValue() && PistonAura.mc.world.getBlockState(blockPos3).getBlock() != Blocks.AIR) {
            return false;
        }
        return PistonAura.mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(blockPos2, blockPos3.add(1, 1, 1))).isEmpty();
    }

    public static boolean Method128(EntityPlayer entityPlayer) {
        return !Class546.Method963(entityPlayer);
    }

    public static boolean Method513(Entity entity) {
        return entity instanceof EntityEnderCrystal;
    }

    public boolean Method126(EntityPlayer entityPlayer) {
        return PistonAura.mc.player.getDistance(entityPlayer) < (float) this.targetRange.getValue().intValue();
    }

    public static Float Method514(Entity entity) {
        return Float.valueOf(PistonAura.mc.player.getDistance(entity));
    }

    @Override
    public void onEnable() {
        if (PistonAura.mc.player == null || PistonAura.mc.world == null) {
            return;
        }
        this.Field372 = Class220.SEARCHING;
        this.Field373 = null;
        this.Field374 = null;
        this.Field375 = null;
        this.Field376 = null;
        this.Field377 = null;
        this.Field382 = 0;
        this.Field385 = 0;
        this.Field384 = null;
        this.Field378 = null;
        this.Field380 = false;
        this.Field386 = null;
    }

    public static boolean Method141(EntityPlayer entityPlayer) {
        return !Class492.Method1989(entityPlayer.getName());
    }

    public boolean Method515(BlockPos blockPos) {
        return PistonAura.mc.world.getBlockState(blockPos).getBlock() instanceof BlockAir;
    }

    @Subscriber
    public void Method139(final Render3DEvent render3DEvent) {
        if (this.Field373 == null || this.Field374 == null) {
            return;
        }
        if (this.Field383.Method737(1000.0)) {
            return;
        }
        if (current.getValue()) {
            BlockPos blockPos = null;
            switch (Class216.Field286[this.Field372.ordinal()]) {
                case 1: {
                    blockPos = this.Field373.down().offset(this.Field374, 2);
                    break;
                }
                case 2:
                case 4: {
                    blockPos = this.Field373.down().offset(this.Field374, 1);
                    break;
                }
                case 3: {
                    blockPos = this.Field373.down().offset(this.Field374, 3);
                    break;
                }
            }
            if (blockPos != null) {
                final AxisAlignedBB offset = mc.world.getBlockState(blockPos).getBoundingBox((IBlockAccess)mc.world, blockPos).offset(blockPos).offset(-((IRenderManager)mc.getRenderManager()).getRenderPosX(), -((IRenderManager)mc.getRenderManager()).getRenderPosY(), -((IRenderManager)mc.getRenderManager()).getRenderPosZ());
                Class523.Method1216();
                Class523.Method1217(offset, (colorC.getValue()).Method774());
                Class523.Method1214();
                Class523.Method1216();
                Class523.Method1215(offset, (outlineC.getValue()).Method774(), 1.5f);
                Class523.Method1214();
            }
        }
        if (full.getValue()) {
            AxisAlignedBB axisAlignedBB = null;
            switch (Class216.Field287[this.Field374.ordinal()]) {
                case 1: {
                    axisAlignedBB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, -3.0).offset(this.Field373.down());
                    break;
                }
                case 2: {
                    axisAlignedBB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 3.0).offset(this.Field373.down());
                    break;
                }
                case 3: {
                    axisAlignedBB = new AxisAlignedBB(0.0, 0.0, 0.0, 3.0, 1.0, 1.0).offset(this.Field373.down());
                    break;
                }
                case 4: {
                    axisAlignedBB = new AxisAlignedBB(0.0, 0.0, 0.0, -3.0, 1.0, 1.0).offset(this.Field373.down());
                    break;
                }
            }
            if (axisAlignedBB != null) {
                final AxisAlignedBB offset2 = axisAlignedBB.offset(-((IRenderManager)mc.getRenderManager()).getRenderPosX(), -((IRenderManager)mc.getRenderManager()).getRenderPosY(), -((IRenderManager)mc.getRenderManager()).getRenderPosZ());
                Class523.Method1216();
                Class523.Method1217(offset2, (colorF.getValue()).Method774());
                Class523.Method1214();
                Class523.Method1216();
                Class523.Method1215(offset2, (outlineF.getValue()).Method774(), 1.5f);
                Class523.Method1214();
            }
        }
        if (arrow.getValue()) {
            Vec3d vec3d = null;
            Vec3d vec3d2 = null;
            Vec3d vec3d3 = null;
            final BlockPos offset3 = this.Field373.offset(this.Field374, 2);
            final Vec3d vec3d4 = new Vec3d(offset3.getX() + 0.5 - ((IRenderManager)mc.getRenderManager()).getRenderPosX(), offset3.getY() + 1 - ((IRenderManager)mc.getRenderManager()).getRenderPosY(), offset3.getZ() + 0.5 - ((IRenderManager)mc.getRenderManager()).getRenderPosZ());
            switch (Class216.Field287[this.Field374.ordinal()]) {
                case 1: {
                    vec3d = new Vec3d(vec3d4.x - 0.5, vec3d4.y, vec3d4.z - 0.5);
                    vec3d2 = new Vec3d(vec3d4.x, vec3d4.y, vec3d4.z + 0.5);
                    vec3d3 = new Vec3d(vec3d4.x + 0.5, vec3d4.y, vec3d4.z - 0.5);
                    break;
                }
                case 2: {
                    vec3d = new Vec3d(vec3d4.x - 0.5, vec3d4.y, vec3d4.z + 0.5);
                    vec3d2 = new Vec3d(vec3d4.x, vec3d4.y, vec3d4.z - 0.5);
                    vec3d3 = new Vec3d(vec3d4.x + 0.5, vec3d4.y, vec3d4.z + 0.5);
                    break;
                }
                case 3: {
                    vec3d = new Vec3d(vec3d4.x + 0.5, vec3d4.y, vec3d4.z - 0.5);
                    vec3d2 = new Vec3d(vec3d4.x - 0.5, vec3d4.y, vec3d4.z);
                    vec3d3 = new Vec3d(vec3d4.x + 0.5, vec3d4.y, vec3d4.z + 0.5);
                    break;
                }
                case 4: {
                    vec3d = new Vec3d(vec3d4.x - 0.5, vec3d4.y, vec3d4.z - 0.5);
                    vec3d2 = new Vec3d(vec3d4.x + 0.5, vec3d4.y, vec3d4.z);
                    vec3d3 = new Vec3d(vec3d4.x - 0.5, vec3d4.y, vec3d4.z + 0.5);
                    break;
                }
            }
            if (vec3d != null) {
                Class523.Method1216();
                GL11.glPushMatrix();
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glDisable(2896);
                GL11.glDisable(3553);
                GL11.glEnable(2848);
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                GL11.glLineWidth(5.0f);
                GL11.glColor4f(((arrowColor.getValue()).Method774() >> 16 & 0xFF) / 255.0f, ((arrowColor.getValue()).Method774() >> 8 & 0xFF) / 255.0f, ((arrowColor.getValue()).Method774() & 0xFF) / 255.0f, ((arrowColor.getValue()).Method774() >> 24 & 0xFF) / 255.0f);
                if (top.getValue()) {
                    GL11.glBegin(1);
                    GL11.glVertex3d(vec3d.x, vec3d.y, vec3d.z);
                    GL11.glVertex3d(vec3d2.x, vec3d2.y, vec3d2.z);
                    GL11.glEnd();
                    GL11.glBegin(1);
                    GL11.glVertex3d(vec3d3.x, vec3d3.y, vec3d3.z);
                    GL11.glVertex3d(vec3d2.x, vec3d2.y, vec3d2.z);
                    GL11.glEnd();
                }
                if (bottom.getValue()) {
                    GL11.glBegin(1);
                    GL11.glVertex3d(vec3d.x, vec3d.y - 1.0, vec3d.z);
                    GL11.glVertex3d(vec3d2.x, vec3d2.y - 1.0, vec3d2.z);
                    GL11.glEnd();
                    GL11.glBegin(1);
                    GL11.glVertex3d(vec3d3.x, vec3d3.y - 1.0, vec3d3.z);
                    GL11.glVertex3d(vec3d2.x, vec3d2.y - 1.0, vec3d2.z);
                    GL11.glEnd();
                }
                GL11.glLineWidth(1.0f);
                GL11.glDisable(2848);
                GL11.glEnable(3553);
                GL11.glEnable(2896);
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                GL11.glDisable(3042);
                GL11.glPopMatrix();
                Class523.Method1214();
            }
        }
    }

    public void Method516(boolean bl, int n, boolean bl2, boolean bl3, Vec3d vec3d) {
        this.Field383.Method739();
        if (bl) {
            PistonAura.mc.player.inventory.currentItem = n;
            PistonAura.mc.player.connection.sendPacket(new CPacketHeldItemChange(n));
        }
        if (bl2) {
            PistonAura.mc.player.connection.sendPacket(new CPacketEntityAction(PistonAura.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
        }
        if (bl3) {
            PistonAura.mc.player.connection.sendPacket(new CPacketEntityAction(PistonAura.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        }
        PistonAura.mc.playerController.processRightClickBlock(PistonAura.mc.player, PistonAura.mc.world, this.Field376, this.Field377, vec3d, EnumHand.MAIN_HAND);
        PistonAura.mc.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
        if (bl3) {
            PistonAura.mc.player.connection.sendPacket(new CPacketEntityAction(PistonAura.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        if (bl2) {
            PistonAura.mc.player.connection.sendPacket(new CPacketEntityAction(PistonAura.mc.player, CPacketEntityAction.Action.START_SPRINTING));
        }
        this.Field372 = Class220.CRYSTAL;
    }

    public void Method517() {
        this.Field383.Method739();
        RayTraceResult rayTraceResult = PistonAura.mc.world.rayTraceBlocks(new Vec3d(PistonAura.mc.player.posX, PistonAura.mc.player.posY + (double) PistonAura.mc.player.getEyeHeight(), PistonAura.mc.player.posZ), new Vec3d((double)this.Field375.getX() + 0.5, (double)this.Field375.getY() - 0.5, (double)this.Field375.getZ() + 0.5));
        EnumFacing enumFacing = rayTraceResult == null || rayTraceResult.sideHit == null ? EnumFacing.UP : rayTraceResult.sideHit;
        FastUse.Field1871 = true;
        Class545.Method996(this.Field375, PistonAura.mc.player.getPositionVector().addVector(0.0, (double) PistonAura.mc.player.getEyeHeight(), 0.0), this.Method519() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, enumFacing, true);
        this.Field372 = Class220.REDSTONE;
        this.Field379.Method738(0L);
    }

    public void Method518(Entity entity) {
        PistonAura.mc.playerController.attackEntity(PistonAura.mc.player, entity);
        PistonAura.mc.player.connection.sendPacket(new CPacketAnimation(this.Method519() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND));
    }

    public boolean Method519() {
        return PistonAura.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL;
    }

    public static Float Method520(EntityPlayer entityPlayer) {
        return Float.valueOf(PistonAura.mc.player.getDistance(entityPlayer));
    }

    public List<EntityPlayer> Method521() {
        return PistonAura.mc.world.playerEntities.stream().filter(PistonAura::Method128).filter(PistonAura::Method141).filter(PistonAura::Method132).filter(this::Method126).sorted(Comparator.comparing(PistonAura::Method520)).collect(Collectors.toList());
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        if (packetEvent.getPacket() instanceof SPacketBlockChange && this.Field378 != null && ((SPacketBlockChange) packetEvent.getPacket()).getBlockPosition().equals(this.Field378) && ((SPacketBlockChange) packetEvent.getPacket()).getBlockState().getBlock() instanceof BlockAir) {
            this.Field378 = null;
        }
    }

    public boolean Method522(BlockPos blockPos) {
        if (!this.Method515(blockPos) && !this.mine.getValue().booleanValue()) {
            return false;
        }
        for (EnumFacing enumFacing : EnumFacing.HORIZONTALS) {
            Optional<Class534> optional;
            this.Field378 = null;
            this.Field380 = false;
            if (!PistonAura.Method512(blockPos.offset(enumFacing).down())) continue;
            if (this.Method527() == -1) {
                return false;
            }
            ItemStack itemStack = PistonAura.mc.player.inventory.getStackInSlot(this.Method527());
            Block block = ((ItemBlock)itemStack.getItem()).getBlock();
            if (block == Blocks.REDSTONE_BLOCK) {
                if (!this.Method515(blockPos.offset(enumFacing, 3))) {
                    if (!this.mine.getValue().booleanValue() || PistonAura.mc.world.getBlockState(blockPos.offset(enumFacing, 3)).getBlock() != Blocks.REDSTONE_TORCH && PistonAura.mc.world.getBlockState(blockPos.offset(enumFacing, 3)).getBlock() != Blocks.REDSTONE_BLOCK) continue;
                    this.Field378 = blockPos.offset(enumFacing, 3);
                }
            } else {
                optional = Class545.Method1006(blockPos.offset(enumFacing, 3), false, true);
                if (!optional.isPresent() && this.mine.getValue().booleanValue() && (PistonAura.mc.world.getBlockState(blockPos.offset(enumFacing, 3)).getBlock() == Blocks.REDSTONE_TORCH || PistonAura.mc.world.getBlockState(blockPos.offset(enumFacing, 3)).getBlock() == Blocks.REDSTONE_BLOCK)) {
                    this.Field378 = blockPos.offset(enumFacing, 3);
                }
                if (!optional.isPresent() && this.Field378 == null && ((ItemBlock) PistonAura.mc.player.inventory.getStackInSlot(this.Method527()).getItem()).getBlock() == Blocks.REDSTONE_TORCH) {
                    for (BlockPos blockPos2 : EnumFacing.HORIZONTALS) {
                        if (blockPos2.equals(enumFacing) || blockPos2.equals(enumFacing.getOpposite())) continue;
                        optional = Class545.Method1006(blockPos.offset(enumFacing, 2).offset((EnumFacing)blockPos2), false, true);
                        if (optional.isPresent()) break;
                        if (!this.mine.getValue().booleanValue() || PistonAura.mc.world.getBlockState(blockPos.offset(enumFacing, 2).offset((EnumFacing)blockPos2)).getBlock() != Blocks.REDSTONE_TORCH) continue;
                        this.Field378 = blockPos.offset(enumFacing, 2).offset((EnumFacing)blockPos2);
                        break;
                    }
                }
                if (!optional.isPresent() && this.Field378 == null) continue;
            }
            optional = Class545.Method997(blockPos.offset(enumFacing, 2));
            boolean bl = this.Field380 = this.mine.getValue() != false && PistonAura.mc.world.getBlockState(blockPos.offset(enumFacing, 2)).getBlock() instanceof BlockPistonBase;
            if (!optional.isPresent() && !this.Field380) continue;
            if (!this.Field380) {
                BlockPos blockPos2;
                BlockPos blockPos3 = optional.get().Field1089;
                EnumFacing enumFacing2 = optional.get().Field1090;
                double[] dArray = Class545.Method1008(blockPos3.getX(), blockPos3.getY(), blockPos3.getZ(), enumFacing2, PistonAura.mc.player);
                blockPos2 = EnumFacing.fromAngle(dArray[0]);
                if (Math.abs(dArray[1]) > 55.0 || blockPos2 != enumFacing || this.rayTrace.getValue().booleanValue() && !this.Method526(optional.get().Field1089)) continue;
                this.Field376 = blockPos3;
                this.Field377 = enumFacing2;
            }
            this.Field373 = blockPos;
            this.Field374 = enumFacing;
            this.Field375 = blockPos.offset(enumFacing).down();
            return true;
        }
        return false;
    }

    public boolean Method394() {
        return this.mode.getValue() == Class214.PUSH;
    }

    public PistonAura() {
        super("PistonAura", "Automatically faceplaces people using pistons", Category.COMBAT, new String[0]);
    }

    public void Method523(int n, Class490 class490, BlockPos blockPos, EnumFacing enumFacing) {
        block1: {
            boolean bl = PistonAura.mc.player.inventory.currentItem != n;
            int n2 = PistonAura.mc.player.inventory.currentItem;
            if (bl) {
                PistonAura.mc.player.inventory.currentItem = n;
                PistonAura.mc.player.connection.sendPacket(new CPacketHeldItemChange(n));
            }
            Rotation.Method1958(class490, EnumHand.MAIN_HAND, true);
            this.Field386 = blockPos.offset(enumFacing);
            this.Field387.Method739();
            if (!bl) break block1;
            PistonAura.mc.player.inventory.currentItem = n2;
            PistonAura.mc.player.connection.sendPacket(new CPacketHeldItemChange(n2));
        }
    }

    @Subscriber(priority=20)
    public void Method123(Class50 class50) {
        if (this.Field384 != null) {
            this.Field388.Method739();
            this.Field385 = 0;
            this.Field384.run();
            this.Field384 = null;
            for (int i = 0; i < actionShift.getValue() - 1; ++i) {
                this.Method528(true);
                if (this.Field384 == null) {
                    return;
                }
                this.Field384.run();
                this.Field384 = null;
            }
        }
        this.Field384 = null;
    }

    public static int Method524() {
        int n = -1;
        for (int i = 0; i < 9; ++i) {
            Block block;
            ItemStack itemStack = PistonAura.mc.player.inventory.getStackInSlot(i);
            if (itemStack == ItemStack.EMPTY || !(itemStack.getItem() instanceof ItemBlock) || !((block = ((ItemBlock)itemStack.getItem()).getBlock()) instanceof BlockPistonBase)) continue;
            n = i;
            break;
        }
        return n;
    }

    @Override
    public boolean Method396() {
        return this.mode.getValue() == Class214.PUSH;
    }

    public static boolean Method132(EntityPlayer entityPlayer) {
        return entityPlayer != PistonAura.mc.player;
    }

    public void Method525(boolean bl, int n, boolean bl2, boolean bl3, Optional optional, Vec3d vec3d) {
        this.Field381.Method739();
        this.Field383.Method739();
        this.Field382 = this.delay.getValue() * 10;
        if (bl) {
            PistonAura.mc.player.inventory.currentItem = n;
            PistonAura.mc.player.connection.sendPacket(new CPacketHeldItemChange(n));
        }
        if (bl2) {
            PistonAura.mc.player.connection.sendPacket(new CPacketEntityAction(PistonAura.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
        }
        if (bl3) {
            PistonAura.mc.player.connection.sendPacket(new CPacketEntityAction(PistonAura.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        }
        PistonAura.mc.playerController.processRightClickBlock(PistonAura.mc.player, PistonAura.mc.world, ((Class534)optional.get()).Field1089, ((Class534)optional.get()).Field1090, vec3d, EnumHand.MAIN_HAND);
        PistonAura.mc.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
        if (bl3) {
            PistonAura.mc.player.connection.sendPacket(new CPacketEntityAction(PistonAura.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        if (bl2) {
            PistonAura.mc.player.connection.sendPacket(new CPacketEntityAction(PistonAura.mc.player, CPacketEntityAction.Action.START_SPRINTING));
        }
        this.Field372 = Class220.BREAKING;
    }

    public boolean Method386(Entity entity) {
        return PistonAura.mc.player.getDistance(entity) <= (float)(this.targetRange.getValue() + 4);
    }

    @Subscriber(priority=99)
    public void Method135(UpdateEvent updateEvent) {
        if (this.Field385 < actionInterval.getValue()) {
            ++this.Field385;
        }
        if (updateEvent.isCanceled() || !Rotation.Method1966()) {
            return;
        }
        if (this.Field372 == Class220.BREAKING) {
            KonasGlobals.INSTANCE.Field1139.Method1941((double)this.Field373.getX() + 0.5, this.Field373.getY(), (double)this.Field373.getZ() + 0.5);
        }
        if (this.Field385 < actionInterval.getValue()) {
            return;
        }
        this.Method528(false);
    }

    public boolean Method526(BlockPos blockPos) {
        for (double d = 0.1; d < 0.9; d += 0.1) {
            for (double d2 = 0.1; d2 < 0.9; d2 += 0.1) {
                for (double d3 = 0.1; d3 < 0.9; d3 += 0.1) {
                    Vec3d vec3d = new Vec3d(PistonAura.mc.player.posX, PistonAura.mc.player.getEntityBoundingBox().minY + (double) PistonAura.mc.player.getEyeHeight(), PistonAura.mc.player.posZ);
                    Vec3d vec3d2 = new Vec3d(blockPos).addVector(d, d2, d3);
                    double d4 = vec3d.distanceTo(vec3d2);
                    double d5 = vec3d2.x - vec3d.x;
                    double d6 = vec3d2.y - vec3d.y;
                    double d7 = vec3d2.z - vec3d.z;
                    double d8 = MathHelper.sqrt(d5 * d5 + d7 * d7);
                    double[] dArray = new double[]{MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(d7, d5)) - 90.0f), MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(d6, d8))))};
                    float f = MathHelper.cos((float)(-dArray[0] * 0.01745329238474369 - 3.1415927410125732));
                    float f2 = MathHelper.sin((float)(-dArray[0] * 0.01745329238474369 - 3.1415927410125732));
                    float f3 = -MathHelper.cos((float)(-dArray[1] * 0.01745329238474369));
                    float f4 = MathHelper.sin((float)(-dArray[1] * 0.01745329238474369));
                    Vec3d vec3d3 = new Vec3d(f2 * f3, f4, f * f3);
                    Vec3d vec3d4 = vec3d.addVector(vec3d3.x * d4, vec3d3.y * d4, vec3d3.z * d4);
                    RayTraceResult rayTraceResult = PistonAura.mc.world.rayTraceBlocks(vec3d, vec3d4, false, false, false);
                    if (rayTraceResult == null || rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK || !rayTraceResult.getBlockPos().equals(blockPos)) continue;
                    return true;
                }
            }
        }
        return false;
    }

    public int Method527() {
        int n = -1;
        for (int i = 0; i < 9; ++i) {
            Block block;
            ItemStack itemStack = PistonAura.mc.player.inventory.getStackInSlot(i);
            if (itemStack == ItemStack.EMPTY || !(itemStack.getItem() instanceof ItemBlock) || (block = ((ItemBlock)itemStack.getItem()).getBlock()) != Blocks.REDSTONE_BLOCK && block != Blocks.REDSTONE_TORCH) continue;
            n = i;
            break;
        }
        return n;
    }

    public void Method528(boolean bl) {
        block43: {
            block42: {
                if (this.Field388.Method737(1000.0) && this.disableWhenNone.getValue().booleanValue()) {
                    this.toggle();
                }
                if (!this.Field381.Method737(this.Field382)) {
                    return;
                }
                if (this.strict.getValue().booleanValue() && Math.sqrt(PistonAura.mc.player.motionX * PistonAura.mc.player.motionX + PistonAura.mc.player.motionZ * PistonAura.mc.player.motionZ) > 9.0E-4) {
                    return;
                }
                if (this.mode.getValue() != Class214.DAMAGE) break block42;
                switch (Class216.Field286[this.Field372.ordinal()]) {
                    case 1: {
                        List<EntityPlayer> list = this.Method521();
                        for (EntityPlayer entityPlayer : list) {
                            if (!this.Method138(entityPlayer)) continue;
                            int n = PistonAura.Method524();
                            if (n == -1) {
                                Logger.Method1119("No pistons found!");
                                this.toggle();
                                return;
                            }
                            if (this.Field380) {
                                this.Field372 = Class220.CRYSTAL;
                                this.Field380 = false;
                                return;
                            }
                            boolean bl2 = PistonAura.mc.player.inventory.currentItem != n;
                            boolean bl3 = PistonAura.mc.player.isSprinting();
                            boolean bl4 = Class545.Method1004(this.Field376);
                            Vec3d vec3d = new Vec3d(this.Field376).addVector(0.5, 0.5, 0.5).add(new Vec3d(this.Field377.getDirectionVec()).scale(0.5));
                            if (bl) {
                                float[] fArray = RotationUtil.Method1946(PistonAura.mc.player.getPositionEyes(mc.getRenderPartialTicks()), vec3d);
                                Class550.Method883(fArray[0], fArray[1]);
                            } else {
                                KonasGlobals.INSTANCE.Field1139.Method1942(vec3d);
                            }
                            this.Field384 = () -> this.Method516(bl2, n, bl3, bl4, vec3d);
                            return;
                        }
                        break block43;
                    }
                    case 2: {
                        if (this.Field378 != null && PistonAura.mc.world.getBlockState(this.Field378).getBlock() == Blocks.AIR) {
                            this.Field378 = null;
                        }
                        if (this.Field378 != null) {
                            if (this.Field379.Method737(1000.0)) {
                                RayTraceResult rayTraceResult = PistonAura.mc.world.rayTraceBlocks(new Vec3d(PistonAura.mc.player.posX, PistonAura.mc.player.posY + (double) PistonAura.mc.player.getEyeHeight(), PistonAura.mc.player.posZ), new Vec3d((double)this.Field378.getX() + 0.5, (double)this.Field378.getY() + 0.5, (double)this.Field378.getZ() + 0.5));
                                EnumFacing enumFacing = rayTraceResult == null || rayTraceResult.sideHit == null ? EnumFacing.UP : rayTraceResult.sideHit;
                                Vec3d vec3d = new Vec3d(this.Field378).addVector(0.5, 0.5, 0.5).add(new Vec3d(enumFacing.getDirectionVec()).scale(0.5));
                                if (bl) {
                                    float[] fArray = RotationUtil.Method1946(PistonAura.mc.player.getPositionEyes(mc.getRenderPartialTicks()), vec3d);
                                    Class550.Method883(fArray[0], fArray[1]);
                                } else {
                                    KonasGlobals.INSTANCE.Field1139.Method1942(vec3d);
                                }
                                this.Field384 = () -> this.Method529(enumFacing);
                            }
                            return;
                        }
                        if (!this.Method519()) {
                            int n = Class475.Method2147();
                            if (n == -1) {
                                Logger.Method1119("No crystals found!");
                                this.toggle();
                                return;
                            }
                            if (PistonAura.mc.player.inventory.currentItem != n) {
                                PistonAura.mc.player.inventory.currentItem = n;
                                PistonAura.mc.playerController.updateController();
                            }
                        }
                        if (this.Field375 == null) {
                            this.Field372 = Class220.SEARCHING;
                            return;
                        }
                        if (bl) {
                            float[] fArray = RotationUtil.Method1946(PistonAura.mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d((double)this.Field375.getX() + 0.5, (double)this.Field375.getY() + 0.5, (double)this.Field375.getZ() + 0.5));
                            Class550.Method883(fArray[0], fArray[1]);
                        } else {
                            KonasGlobals.INSTANCE.Field1139.Method1942(new Vec3d((double)this.Field375.getX() + 0.5, (double)this.Field375.getY() + 0.5, (double)this.Field375.getZ() + 0.5));
                        }
                        this.Field384 = this::Method517;
                        return;
                    }
                    case 3: {
                        if (this.Field373 == null) {
                            this.Field372 = Class220.SEARCHING;
                            return;
                        }
                        int n = this.Method527();
                        if (n == -1) {
                            Logger.Method1119("No redstone found!");
                            this.toggle();
                            return;
                        }
                        Optional<Class534> optional = Class545.Method1006(this.Field373.offset(this.Field374, 3), false, ((ItemBlock) PistonAura.mc.player.inventory.getStackInSlot(this.Method527()).getItem()).getBlock() == Blocks.REDSTONE_TORCH);
                        if (!optional.isPresent() && ((ItemBlock) PistonAura.mc.player.inventory.getStackInSlot(this.Method527()).getItem()).getBlock() == Blocks.REDSTONE_TORCH) {
                            for (EnumFacing enumFacing : EnumFacing.HORIZONTALS) {
                                if (!enumFacing.equals(this.Field374) && !enumFacing.equals(this.Field374.getOpposite()) && (optional = Class545.Method1006(this.Field373.offset(this.Field374, 2).offset(enumFacing), false, ((ItemBlock) PistonAura.mc.player.inventory.getStackInSlot(this.Method527()).getItem()).getBlock() == Blocks.REDSTONE_TORCH)).isPresent()) break;
                            }
                        }
                        if (optional.isPresent()) {
                            Object object;
                            EnumFacing enumFacing;
                            boolean bl5 = PistonAura.mc.player.inventory.currentItem != n;
                            int n2 = PistonAura.mc.player.isSprinting();
                            int n3 = Class545.Method1004(optional.get().Field1089);
                            enumFacing = new Vec3d(optional.get().Field1089).addVector(0.5, 0.5, 0.5).add(new Vec3d(optional.get().Field1090.getDirectionVec()).scale(0.5));
                            if (bl) {
                                object = RotationUtil.Method1946(PistonAura.mc.player.getPositionEyes(mc.getRenderPartialTicks()), (Vec3d)enumFacing);
                                Class550.Method883((float)object[0], (float)object[1]);
                            } else {
                                KonasGlobals.INSTANCE.Field1139.Method1942((Vec3d)enumFacing);
                            }
                            object = optional;
                            this.Field384 = () -> this.Method525(bl5, n, n2 != 0, n3 != 0, (Optional)object, (Vec3d)enumFacing);
                            return;
                        }
                        /*if (optional.isPresent()) {
                            final boolean b3 = mc.player.inventory.currentItem != n;
                            final boolean sprinting2 = mc.player.isSprinting();
                            final boolean method531 = Class545.Method1004(optional.get().Field1089);
                            final Vec3d add3 = new Vec3d((Vec3i)optional.get().Field1089).addVector(0.5, 0.5, 0.5).add(new Vec3d(optional.get().Field1090.getDirectionVec()).scale(0.5));
                            if (bl) {
                                final float[] method532 = RotationUtil.Method1946(mc.player.getPositionEyes(mc.getRenderPartialTicks()), add3);
                                Class550.Method883(method532[0], method532[1]);
                            }
                            else {
                                NewGui.INSTANCE.Field1139.Method1942((Vec3d)add3);
                            }
                            object = optional;
                            this.Field384 = () -> this.Method525(b3, n, sprinting2 != 0, n3 != 0, (Optional)object, (Vec3d)enumFacing);
                            //this.Field384 = this::Method525;
                            return;
                        }*/
                        this.Field372 = Class220.BREAKING;
                        return;
                    }
                    case 4: {
                        Entity entity = PistonAura.mc.world.loadedEntityList.stream().filter(PistonAura::Method513).filter(this::Method386).min(Comparator.comparing(PistonAura::Method514)).orElse(null);
                        if (entity != null) {
                            if (this.antiSuicide.getValue().booleanValue() && Class475.Method2156((EntityEnderCrystal)entity, PistonAura.mc.player) >= PistonAura.mc.player.getHealth() + PistonAura.mc.player.getAbsorptionAmount()) {
                                return;
                            }
                            this.Field381.Method739();
                            this.Field383.Method739();
                            this.Field382 = this.delay.getValue() * 10;
                            if (bl) {
                                float[] fArray = RotationUtil.Method1946(PistonAura.mc.player.getPositionEyes(mc.getRenderPartialTicks()), entity.getPositionVector());
                                Class550.Method883(fArray[0], fArray[1]);
                            } else {
                                KonasGlobals.INSTANCE.Field1139.Method1942(entity.getPositionVector());
                            }
                            this.Field384 = () -> this.Method518(entity);
                            break;
                        }
                        if (bl) {
                            float[] fArray = RotationUtil.Method1946(PistonAura.mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d((double)this.Field373.getX() + 0.5, this.Field373.getY(), (double)this.Field373.getZ() + 0.5));
                            Class550.Method883(fArray[0], fArray[1]);
                            break;
                        }
                        KonasGlobals.INSTANCE.Field1139.Method1941((double)this.Field373.getX() + 0.5, this.Field373.getY(), (double)this.Field373.getZ() + 0.5);
                        break;
                    }
                }
                break block43;
            }
            this.Field372 = Class220.SEARCHING;
            int n = PistonAura.Method524();
            if (n == -1) {
                Logger.Method1119("No pistons found!");
                this.toggle();
                return;
            }
            int n4 = this.Method527();
            if (n4 == -1) {
                Logger.Method1119("No redstone found!");
                this.toggle();
                return;
            }
            List<EntityPlayer> list = this.Method521();
            block8: for (EntityPlayer entityPlayer : list) {
                Object object;
                if (this.smart.getValue().booleanValue() && !Class545.Method1009(new BlockPos(entityPlayer)) && PistonAura.mc.world.getBlockState(new BlockPos(entityPlayer)).getBlock() == Blocks.AIR) continue;
                BlockPos blockPos = new BlockPos(entityPlayer).up();
                if (this.antiSuicide.getValue().booleanValue() && blockPos.equals(new BlockPos(PistonAura.mc.player))) continue;
                for (EnumFacing enumFacing : EnumFacing.HORIZONTALS) {
                    if (!(PistonAura.mc.world.getBlockState(blockPos.offset(enumFacing)).getBlock() instanceof BlockPistonBase) && (this.Field387.Method737(Class475.Method2142() + 150) || !blockPos.offset(enumFacing).equals(this.Field386)) || PistonAura.mc.world.getBlockState(blockPos.offset(enumFacing)).getBlock() instanceof BlockPistonBase && !(object = PistonAura.mc.world.getBlockState(blockPos.offset(enumFacing)).getValue((IProperty)BlockDirectional.FACING)).equals(enumFacing.getOpposite())) continue;
                    if (PistonAura.mc.world.getBlockState(blockPos.offset(enumFacing, 2)).getBlock() == Blocks.REDSTONE_BLOCK || PistonAura.mc.world.getBlockState(blockPos.offset(enumFacing, 2)).getBlock() == Blocks.REDSTONE_TORCH || !Rotation.Method1967(blockPos.offset(enumFacing, 2), this.rayTrace.getValue()) || (object = Rotation.Method1968(blockPos.offset(enumFacing, 2), true, bl, this.rayTrace.getValue())) == null) break block8;
                    this.Field384 = () -> this.Method530(n4, (Class490)object);
                    return;
                }
                for (EnumFacing enumFacing : EnumFacing.HORIZONTALS) {
                    Class490 class490;
                    if (!Rotation.Method1967(blockPos.offset(enumFacing), this.rayTrace.getValue()) || !(this.rayTrace.getValue() != false ? Rotation.Method1967(blockPos.offset(enumFacing, 2), true) : PistonAura.mc.world.getBlockState(blockPos.offset(enumFacing, 2)).getBlock() == Blocks.AIR)) continue;
                    object = RotationUtil.Method1946(PistonAura.mc.player.getPositionEyes(1.0f), new Vec3d((double)blockPos.offset(enumFacing).getX() + 0.5, (double)blockPos.offset(enumFacing).getY() + 1.0, (double)blockPos.offset(enumFacing).getZ() + 0.5));
                    EnumFacing enumFacing2 = EnumFacing.fromAngle((double)object[0]);
                    if (Math.abs((float)object[1]) > 55.0f || enumFacing2 != enumFacing || (class490 = Rotation.Method1968(blockPos.offset(enumFacing), true, bl, this.rayTrace.getValue())) == null) continue;
                    this.Field384 = () -> this.Method523(n, class490, blockPos, enumFacing);
                    return;
                }
            }
        }
    }

    public void Method529(EnumFacing enumFacing) {
        PistonAura.mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.Field378, enumFacing));
        PistonAura.mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.Field378, enumFacing));
        this.Field379.Method739();
    }

    public boolean Method388() {
        return this.mode.getValue() == Class214.DAMAGE;
    }

    public void Method530(int n, Class490 class490) {
        block2: {
            boolean bl = PistonAura.mc.player.inventory.currentItem != n;
            int n2 = PistonAura.mc.player.inventory.currentItem;
            if (bl) {
                PistonAura.mc.player.inventory.currentItem = n;
                PistonAura.mc.player.connection.sendPacket(new CPacketHeldItemChange(n));
            }
            Rotation.Method1958(class490, EnumHand.MAIN_HAND, true);
            this.Field381.Method739();
            this.Field382 = Class475.Method2142() + 150;
            if (bl) {
                PistonAura.mc.player.inventory.currentItem = n2;
                PistonAura.mc.player.connection.sendPacket(new CPacketHeldItemChange(n2));
            }
            if (!this.disableAfterPush.getValue().booleanValue()) break block2;
            this.toggle();
        }
    }

    @Subscriber
    public void Method531(PacketEvent packetEvent) {
        if (packetEvent.getPacket() instanceof SPacketSoundEffect) {
            SPacketSoundEffect sPacketSoundEffect = (SPacketSoundEffect) packetEvent.getPacket();
            if (this.Field375 == null) {
                return;
            }
            if (sPacketSoundEffect.getCategory() == SoundCategory.BLOCKS && sPacketSoundEffect.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE && this.Field375.up().getDistance((int)sPacketSoundEffect.getX(), (int)sPacketSoundEffect.getY(), (int)sPacketSoundEffect.getZ()) <= 2.0) {
                this.Field372 = Class220.SEARCHING;
                this.Field382 = 0;
            }
        }
    }
}