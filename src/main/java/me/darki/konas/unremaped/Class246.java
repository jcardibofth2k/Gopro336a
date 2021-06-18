package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import java.awt.Color;
import java.lang.invoke.LambdaMetafactory;
import java.util.LinkedList;

import me.darki.konas.setting.ColorValue;
import me.darki.konas.event.events.UpdateEvent;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.module.player.AutoTool;
import me.darki.konas.setting.Setting;
import me.darki.konas.util.TimerUtil;
import me.darki.konas.util.rotation.Rotation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Class246
extends Module {
    public static Setting<Float> delay = new Setting<>("Delay", Float.valueOf(0.1f), Float.valueOf(5.0f), Float.valueOf(0.0f), Float.valueOf(0.1f));
    public static Setting<Float> reach = new Setting<>("Reach", Float.valueOf(4.2f), Float.valueOf(6.0f), Float.valueOf(1.0f), Float.valueOf(0.1f));
    public static Setting<Boolean> rotate = new Setting<>("Rotate", false);
    public static Setting<Boolean> autoSwap = new Setting<>("AutoSwap", false);
    public static Setting<Boolean> queue = new Setting<>("Queue", false);
    public static Setting<Boolean> render = new Setting<>("Render", false);
    public Setting<ColorValue> queue = new Setting<>("Queue", new ColorValue(0x550000FF)).visibleIf(Field2275::getValue);
    public Setting<ColorValue> queueOutline = new Setting<>("QueueOutline", new ColorValue(Color.BLUE.hashCode())).visibleIf(Field2275::getValue);
    public Setting<ColorValue> mining = new Setting<>("Mining", new ColorValue(0x55FF0000)).visibleIf(Field2275::getValue);
    public Setting<ColorValue> miningOutline = new Setting<>("MiningOutline", new ColorValue(Color.RED.hashCode())).visibleIf(Field2275::getValue);
    public Setting<ColorValue> ready = new Setting<>("Ready", new ColorValue(0x5500FF00)).visibleIf(Field2275::getValue);
    public Setting<ColorValue> readyOutline = new Setting<>("ReadyOutline", new ColorValue(Color.GREEN.hashCode())).visibleIf(Field2275::getValue);
    public Setting<Float> width = new Setting<>("Width", Float.valueOf(1.5f), Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(0.1f)).visibleIf(Field2275::getValue);
    public float Field2283;
    public int Field2284;
    public Class249 Field2285;
    public TimerUtil Field2286 = new TimerUtil();
    public Runnable Field2287;
    public LinkedList<Class249> Field2288 = new LinkedList();

    @Override
    public void onEnable() {
        this.Field2288.clear();
        this.Field2285 = null;
        this.Field2283 = 0.0f;
        this.Field2287 = null;
        this.Field2284 = -1;
    }

    public ColorValue Method2046(Class249 class249) {
        if (class249.equals(this.Field2285)) {
            if (this.Field2283 >= 1.0f) {
                return (ColorValue)this.readyOutline.getValue();
            }
            return (ColorValue)this.miningOutline.getValue();
        }
        return (ColorValue)this.queueOutline.getValue();
    }

    public Class246() {
        super("PacketMine", Category.EXPLOIT, "ServerSideMine");
    }

    @Subscriber
    public void Method1846(Class646 class646) {
        class646.Cancel();
        if (class646.Method1149() == null || !this.Method512(class646.Method1149())) {
            return;
        }
        Class249 class249 = new Class249(class646.Method1149(), class646.Method1232());
        if (!this.Field2288.contains(class249) && (((Boolean)queue.getValue()).booleanValue() || this.Field2288.isEmpty() && this.Field2285 == null)) {
            this.Field2288.add(class249);
        }
    }

    public void Method124() {
        block0: {
            if (this.Field2285 == null) break block0;
            Class246.mc.player.swingArm(EnumHand.MAIN_HAND);
            Class246.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.Field2285.Method2002(), this.Field2285.Method2000()));
            Class246.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.Field2285.Method2002(), this.Field2285.Method2000()));
        }
    }

    @Subscriber
    public void Method123(Class50 class50) {
        if (this.Field2287 != null && this.Field2286.GetDifferenceTiming(((Float)delay.getValue()).floatValue() * 50.0f)) {
            this.Field2287.run();
            this.Field2287 = null;
        }
    }

    public boolean Method512(BlockPos blockPos) {
        IBlockState iBlockState;
        BlockPos blockPos2;
        WorldClient worldClient;
        try {
            worldClient = Class246.mc.world;
            blockPos2 = blockPos;
        }
        catch (NullPointerException nullPointerException) {
            return false;
        }
        IBlockState iBlockState2 = worldClient.getBlockState(blockPos2);
        IBlockState iBlockState3 = iBlockState = iBlockState2;
        WorldClient worldClient2 = Class246.mc.world;
        BlockPos blockPos3 = blockPos;
        float f = iBlockState3.getBlockHardness((World)worldClient2, blockPos3);
        return f != -1.0f;
    }

    @Subscriber
    public void Method139(Render3DEvent render3DEvent) {
        LinkedList linkedList = (LinkedList)this.Field2288.clone();
        if (this.Field2285 != null) {
            linkedList.add(this.Field2285);
        }
        while (!linkedList.isEmpty()) {
            Class249 class249 = (Class249)linkedList.poll();
            AxisAlignedBB axisAlignedBB = Class246.mc.world.getBlockState(class249.Method2002()).getBoundingBox((IBlockAccess)Class246.mc.world, class249.Method2002()).offset(class249.Method2002());
            if (axisAlignedBB == null) continue;
            EspRenderUtil.Method1386();
            EspRenderUtil.Method1371(axisAlignedBB, true, 1.0, this.Method2047(class249), 63);
            EspRenderUtil.Method1374(axisAlignedBB, ((Float)this.width.getValue()).floatValue(), this.Method2046(class249));
            EspRenderUtil.Method1385();
        }
    }

    public void Method134() {
        if (this.Field2283 >= 1.0f) {
            return;
        }
        IBlockState iBlockState = this.Field2285.Method2001();
        int n = AutoTool.Method1850(this.Field2285.Method2002());
        if (n == -1) {
            n = Class246.mc.player.inventory.currentItem;
        }
        int n2 = Class246.mc.player.inventory.currentItem;
        Class246.mc.player.inventory.currentItem = n;
        this.Field2283 += iBlockState.getPlayerRelativeBlockHardness((EntityPlayer)Class246.mc.player, (World)Class246.mc.world, this.Field2285.Method2002());
        Class246.mc.player.inventory.currentItem = n2;
    }

    /*
     * Unable to fully structure code
     */
    @Subscriber
    public void Method135(UpdateEvent var1_1) {
        block9: {
            block8: {
                block7: {
                    if (!Rotation.Method1959((Boolean)Class246.rotate.getValue())) {
                        return;
                    }
                    if (this.Field2285 == null) break block7;
                    if (!Class246.mc.world.getBlockState(this.Field2285.Method2002()).getBlock().equals(this.Field2285.Method2001().getBlock())) ** GOTO lbl-1000
                    v0 = new Vec3d((Vec3i)this.Field2285.Method2002());
                    v1 = new Vec3d(this.Field2285.Method2000().getDirectionVec());
                    if (Class246.mc.player.getPositionEyes(1.0f).distanceTo(v0.add(v1.scale(0.5))) <= (double)((Float)Class246.reach.getValue()).floatValue()) {
                        this.Method134();
                        if (((Boolean)Class246.autoSwap.getValue()).booleanValue() && this.Field2283 >= 1.0f && this.Field2284 == -1 && (var2_2 = AutoTool.Method1850(this.Field2285.Method2002())) != -1 && var2_2 != Class246.mc.player.inventory.currentItem) {
                            Class246.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(var2_2));
                            this.Field2284 = var2_2;
                        }
                    } else lbl-1000:
                    // 2 sources

                    {
                        this.Field2285 = null;
                        if (this.Field2284 != -1) {
                            Class246.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.Field2284));
                            this.Field2284 = -1;
                        }
                    }
                    break block8;
                }
                if (this.Field2288.isEmpty()) break block8;
                var2_3 = this.Field2288.peek();
                if (!Class246.mc.world.getBlockState(var2_3.Method2002()).getBlock().equals(var2_3.Method2001().getBlock())) ** GOTO lbl-1000
                v2 = new Vec3d((Vec3i)var2_3.Method2002());
                v3 = new Vec3d(var2_3.Method2000().getDirectionVec());
                if (Class246.mc.player.getPositionEyes(1.0f).distanceTo(v2.add(v3.scale(0.5))) <= (double)((Float)Class246.reach.getValue()).floatValue()) {
                    this.Field2288.poll();
                    this.Field2285 = var2_3;
                    this.Field2283 = 0.0f;
                    this.Field2286.UpdateCurrentTime();
                    this.Field2287 = (Runnable)LambdaMetafactory.metafactory(null, null, null, ()V, Method124(), ()V)((Class246)this);
                    this.Method134();
                } else lbl-1000:
                // 2 sources

                {
                    this.Field2288.poll();
                }
            }
            if (!((Boolean)Class246.rotate.getValue()).booleanValue() || this.Field2285 == null) break block9;
            KonasGlobals.INSTANCE.Field1139.Method1942(new Vec3d((Vec3i)this.Field2285.Method2002()).add(new Vec3d(this.Field2285.Method2000().getDirectionVec()).scale(0.5)));
        }
    }

    public ColorValue Method2047(Class249 class249) {
        if (class249.equals(this.Field2285)) {
            if (this.Field2283 >= 1.0f) {
                return (ColorValue)this.ready.getValue();
            }
            return (ColorValue)this.mining.getValue();
        }
        return (ColorValue)this.queue.getValue();
    }
}