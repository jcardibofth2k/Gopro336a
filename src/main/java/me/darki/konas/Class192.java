package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.NewGui;
import me.darki.konas.module.player.AutoTool;
import me.darki.konas.setting.Setting;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;

public class Class192
extends Module {
    public Setting<Boolean> Field54 = new Setting<>("Rotate", true);
    public Setting<Boolean> Field55 = new Setting<>("Swing", true);
    public Setting<Float> Field56 = new Setting<>("Range", Float.valueOf(4.0f), Float.valueOf(6.0f), Float.valueOf(1.0f), Float.valueOf(0.1f));
    public Setting<Float> Field57 = new Setting<>("Delay", Float.valueOf(2.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f));
    public Setting<Boolean> Field58 = new Setting<>("StrictDirection", false);
    public Setting<Class195> Field59 = new Setting<>("Swap", Class195.NORMAL);
    public Setting<Boolean> Field60 = new Setting<>("Instant", false);
    public Setting<Boolean> Field61 = new Setting<>("Limit", false).Method1191(this.Field60::getValue);
    public Setting<ParentSetting> Field62 = new Setting<>("Render", new ParentSetting(false));
    public Setting<Boolean> Field63 = new Setting<>("ShowMining", true).setParentSetting(this.Field62);
    public Setting<ColorValue> Field64 = new Setting<>("Mining", new ColorValue(0x55FF0000)).setParentSetting(this.Field62);
    public Setting<ColorValue> Field65 = new Setting<>("MiningOutline", new ColorValue(Color.RED.hashCode())).setParentSetting(this.Field62);
    public Setting<ColorValue> Field66 = new Setting<>("Ready", new ColorValue(0x5500FF00)).setParentSetting(this.Field62);
    public Setting<ColorValue> Field67 = new Setting<>("ReadyOutline", new ColorValue(Color.GREEN.hashCode())).setParentSetting(this.Field62);
    public Setting<Float> Field68 = new Setting<>("Width", Float.valueOf(1.5f), Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(0.1f)).setParentSetting(this.Field62);
    public Class566 Field69 = new Class566();
    public BlockPos Field70;
    public BlockPos Field71;
    public EnumFacing Field72;
    public float Field73;
    public Class566 Field74 = new Class566();
    public boolean Field75;
    public Class566 Field76 = new Class566();
    public Runnable Field77 = null;
    public int Field78 = -1;

    public boolean Method122(EntityPlayer entityPlayer) {
        return Class192.mc.player.getDistance((Entity)entityPlayer) <= ((Float)this.Field56.getValue()).floatValue();
    }

    public Class192() {
        super("AntiSurround", "Mines enemy surrounds", Category.COMBAT, new String[0]);
    }

    @Subscriber(priority=15)
    public void Method123(Class50 class50) {
        if (this.Field77 != null) {
            this.Field77.run();
            this.Field77 = null;
        }
        if (this.Field78 != -1 && this.Field69.Method737(350.0)) {
            Class192.mc.player.inventory.currentItem = this.Field78;
            Class192.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.Field78));
            this.Field78 = -1;
        }
    }

    public void Method124() {
        block1: {
            if (((Boolean)this.Field61.getValue()).booleanValue()) {
                Class192.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.Field71, this.Field72.getOpposite()));
            }
            Class192.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.Field71, this.Field72));
            if (!((Boolean)this.Field55.getValue()).booleanValue()) break block1;
            Class192.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }

    @Override
    public void onEnable() {
        this.Field70 = null;
        this.Field71 = null;
        this.Field72 = null;
        this.Field73 = 0.0f;
        this.Field77 = null;
        this.Field75 = false;
        this.Field78 = -1;
    }

    public static boolean Method126(EntityPlayer entityPlayer) {
        return entityPlayer.getHealth() > 0.0f;
    }

    public static Double Method127(BlockPos blockPos) {
        return Class192.mc.player.getDistanceSq(blockPos);
    }

    public static boolean Method128(EntityPlayer entityPlayer) {
        return entityPlayer != Class192.mc.player;
    }

    @Override
    public void onDisable() {
        if (this.Field78 != -1 && Class192.mc.player != null) {
            Class192.mc.player.inventory.currentItem = this.Field78;
            Class192.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.Field78));
            this.Field78 = -1;
        }
    }

    @Subscriber
    public void Method130(Class19 class19) {
        block2: {
            if (this.Field71 == null || !(this.Field73 < 1.0f)) break block2;
            IBlockState iBlockState = Class192.mc.world.getBlockState(this.Field71);
            if (iBlockState.getMaterial() == Material.AIR) {
                this.Field70 = this.Field71;
                this.Field71 = null;
                return;
            }
            int n = AutoTool.Method1850(this.Field71);
            if (n == -1) {
                n = Class192.mc.player.inventory.currentItem;
            }
            int n2 = Class192.mc.player.inventory.currentItem;
            Class192.mc.player.inventory.currentItem = n;
            this.Field73 += iBlockState.getPlayerRelativeBlockHardness((EntityPlayer)Class192.mc.player, Class192.mc.player.world, this.Field71);
            Class192.mc.player.inventory.currentItem = n2;
            this.Field74.Method739();
        }
    }

    @Subscriber
    public void Method131(PacketEvent packetEvent) {
        if (packetEvent.getPacket() instanceof SPacketBlockChange && this.Field71 != null && ((SPacketBlockChange) packetEvent.getPacket()).getBlockPosition().equals((Object)this.Field71) && ((SPacketBlockChange) packetEvent.getPacket()).getBlockState().getBlock() instanceof BlockAir) {
            this.Field70 = this.Field71;
            this.Field71 = null;
            this.Field72 = null;
        }
    }

    public static boolean Method132(EntityPlayer entityPlayer) {
        return !Class492.Method1988(entityPlayer.getUniqueID().toString());
    }

    public static Double Method133(BlockPos blockPos, Vec3d vec3d, EnumFacing enumFacing) {
        return new Vec3d((Vec3i)blockPos).add(0.5, 0.5, 0.5).add(new Vec3d(enumFacing.getDirectionVec()).scale(0.5)).distanceTo(vec3d);
    }

    public void Method134() {
        block0: {
            Class192.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.Field71, this.Field72));
            Class192.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.Field71, this.Field72));
            if (!((Boolean)this.Field55.getValue()).booleanValue()) break block0;
            Class192.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }

    @Subscriber(priority=90)
    public void Method135(UpdateEvent updateEvent) {
        block13: {
            EnumFacing enumFacing;
            ArrayList<BlockPos> arrayList;
            BlockPos blockPos;
            EntityPlayer entityPlayer;
            if (updateEvent.isCanceled() || !Class496.Method1959((Boolean)this.Field54.getValue())) {
                return;
            }
            if (this.Field71 != null) {
                if (this.Field73 >= 1.0f) {
                    if (this.Field75) {
                        if (this.Field74.Method737(1500.0)) {
                            this.Field71 = null;
                            this.Field72 = null;
                        }
                    } else {
                        int n;
                        this.Field75 = true;
                        if (this.Field59.getValue() != Class195.OFF && (n = AutoTool.Method1850(this.Field71)) != -1 && n != Class192.mc.player.inventory.currentItem) {
                            if (this.Field59.getValue() == Class195.SILENT) {
                                this.Field78 = Class192.mc.player.inventory.currentItem;
                                Class192.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n));
                                this.Field69.Method739();
                            } else {
                                Class192.mc.player.inventory.currentItem = n;
                                Class192.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n));
                            }
                        }
                    }
                }
            } else if (this.Field76.Method737(((Float)this.Field57.getValue()).floatValue() * 1000.0f) && (entityPlayer = this.Method142()) != null && (blockPos = (BlockPos)(arrayList = Class465.Method2284(new BlockPos((Entity)entityPlayer))).stream().min(Comparator.comparing(Class192::Method127)).orElse(null)) != null && (enumFacing = this.Method140(blockPos, (Boolean)this.Field58.getValue())) != null) {
                this.Field71 = blockPos;
                this.Field72 = enumFacing;
                this.Field73 = 0.0f;
                this.Field75 = false;
                this.Field76.Method739();
                if (((Boolean)this.Field60.getValue()).booleanValue() && this.Field71.equals((Object)this.Field70)) {
                    this.Field73 = 1.0f;
                    this.Field74.Method739();
                    this.Field77 = this::Method124;
                } else {
                    this.Field77 = this::Method134;
                }
            }
            if (!((Boolean)this.Field54.getValue()).booleanValue() || this.Field71 == null) break block13;
            Vec3d vec3d = new Vec3d((Vec3i)this.Field71).add(0.5, 0.5, 0.5).add(new Vec3d(this.Field72.getDirectionVec()).scale(0.5));
            NewGui.INSTANCE.Field1139.Method1942(vec3d);
        }
    }

    public static boolean Method136(Vec3d vec3d, BlockPos blockPos, EnumFacing enumFacing) {
        return Class192.mc.world.rayTraceBlocks(vec3d, new Vec3d((Vec3i)blockPos).add(0.5, 0.5, 0.5).add(new Vec3d(enumFacing.getDirectionVec()).scale(0.5))) == null;
    }

    public static Float Method137(EntityPlayer entityPlayer) {
        return Float.valueOf(Class192.mc.player.getDistance((Entity)entityPlayer));
    }

    public static boolean Method138(EntityPlayer entityPlayer) {
        return !entityPlayer.isDead;
    }

    /*
     * Unable to fully structure code
     */
    @Subscriber
    public void Method139(Class89 var1_1) {
        block31: {
            block32: {
                if (this.Field71 == null) break block31;
                v0 = this.Field63;
                v1 = v0.getValue();
                v2 = (Boolean)v1;
                v3 = v2;
                if (!v3) break block31;
                v4 = Class192.mc.world;
                v5 = this.Field71;
                v6 = v4.getBlockState(v5);
                v7 = Class192.mc.world;
                v8 = this.Field71;
                v9 = v6.getBoundingBox((IBlockAccess)v7, v8);
                v10 = this.Field71;
                v11 = v9.offset(v10);
                var2_2 = v11;
                Class507.Method1386();
                v12 = var2_2;
                v13 = true;
                v14 = 1.0;
                if (!(this.Field73 >= 1.0f)) ** GOTO lbl41
                v15 = this.Field66;
                v16 = v15.getValue();
                v17 = (ColorValue)v16;
                ** GOTO lbl46
lbl41:
                // 1 sources

                v18 = this.Field64;
                v19 = v18.getValue();
                v17 = (ColorValue)v19;
lbl46:
                // 2 sources

                v20 = 63;
                Class507.Method1371(v12, v13, v14, v17, v20);
                v21 = var2_2;
                v22 = this.Field68;
                v23 = v22.getValue();
                v24 = (Float)v23;
                v25 = v24.floatValue();
                v26 = v25;
                if (!(this.Field73 >= 1.0f)) ** GOTO lbl67
                v27 = this.Field67;
                v28 = v27.getValue();
                v29 = (ColorValue)v28;
                break block32;
lbl67:
                // 1 sources

                v30 = this.Field65;
                v31 = v30.getValue();
                v29 = (ColorValue)v31;
            }
            Class507.Method1374(v21, v26, v29);
            try {
                Class507.Method1385();
            }
            catch (NullPointerException var2_3) {
                // empty catch block
            }
        }
    }

    public EnumFacing Method140(BlockPos blockPos, boolean bl) {
        Vec3d vec3d;
        List<Object> list;
        block1: {
            block0: {
                list = new ArrayList();
                vec3d = Class192.mc.player.getPositionEyes(1.0f);
                if (!bl) break block0;
                Vec3d vec3d2 = new Vec3d((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5);
                IBlockState iBlockState = Class192.mc.world.getBlockState(blockPos);
                boolean bl2 = iBlockState.getBlock() == Blocks.AIR || iBlockState.isFullBlock();
                list.addAll(Class496.Method1971(vec3d.x - vec3d2.x, EnumFacing.WEST, EnumFacing.EAST, !bl2));
                list.addAll(Class496.Method1971(vec3d.y - vec3d2.y, EnumFacing.DOWN, EnumFacing.UP, true));
                list.addAll(Class496.Method1971(vec3d.z - vec3d2.z, EnumFacing.NORTH, EnumFacing.SOUTH, !bl2));
                list = list.stream().filter(arg_0 -> Class192.Method136(vec3d, blockPos, arg_0)).collect(Collectors.toList());
                if (!list.isEmpty()) break block1;
                list.addAll(Class496.Method1971(vec3d.x - vec3d2.x, EnumFacing.WEST, EnumFacing.EAST, !bl2));
                list.addAll(Class496.Method1971(vec3d.y - vec3d2.y, EnumFacing.DOWN, EnumFacing.UP, true));
                list.addAll(Class496.Method1971(vec3d.z - vec3d2.z, EnumFacing.NORTH, EnumFacing.SOUTH, !bl2));
                break block1;
            }
            list = Arrays.asList(EnumFacing.values());
        }
        return list.stream().min(Comparator.comparing(arg_0 -> Class192.Method133(blockPos, vec3d, arg_0))).orElse(null);
    }

    public static boolean Method141(EntityPlayer entityPlayer) {
        return !Class546.Method963((Entity)entityPlayer);
    }

    public EntityPlayer Method142() {
        return Class192.mc.world.playerEntities.stream().filter(Class192::Method141).filter(Class192::Method128).filter(Class192::Method138).filter(Class192::Method132).filter(Class192::Method126).filter(this::Method122).filter(Class465::Method2283).min(Comparator.comparing(Class192::Method137)).orElse(null);
    }
}
