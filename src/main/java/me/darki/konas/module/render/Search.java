package me.darki.konas.module.render;

import cookiedragon.eventsystem.Subscriber;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import me.darki.konas.*;
import me.darki.konas.command.Logger;
import me.darki.konas.mixin.mixins.IEntityRenderer;
import me.darki.konas.mixin.mixins.IRenderManager;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBarrier;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class Search
extends Module {
    public ArrayList<Block> Field662;
    public CopyOnWriteArrayList<Class448> copyOnWriteArrayList = new CopyOnWriteArrayList();
    public static Setting<Class443> customBlocks = new Setting<>("CustomBlocks", new Class443(new String[0]));
    public static Setting<Float> range = new Setting<>("Range", Float.valueOf(100.0f), Float.valueOf(500.0f), Float.valueOf(1.0f), Float.valueOf(1.0f));
    public static Setting<ColorValue> color = new Setting<>("Color", new ColorValue(-16711681));
    public static Setting<Boolean> aDefault = new Setting<>("Default", true);
    public static Setting<Boolean> custom = new Setting<>("Custom", true);
    public static Setting<Boolean> illegals = new Setting<>("Illegals", true);
    public static Setting<Boolean> tracers = new Setting<>("Tracers", false);
    public static Setting<Boolean> fill = new Setting<>("Fill", true);
    public static Setting<Boolean> outline = new Setting<>("Outline", true);
    public static Setting<Boolean> softReload = new Setting<>("SoftReload", true);
    public static Setting<Boolean> slowRender = new Setting<>("SlowRender", false);
    public Class566 Field675 = new Class566();

    @Override
    public void onEnable() {
        block1: {
            if (GlStateManager.glGetString((int)7936).contains("Intel") && !((Boolean) slowRender.getValue()).booleanValue()) {
                Logger.Method1118("You have an integrated graphics card. To increase fps, use SlowRender.");
            }
            if (!((Boolean) softReload.getValue()).booleanValue()) break block1;
            Search.Method134();
        }
    }

    public boolean Method731(Class448 class448) {
        if (((Boolean) aDefault.getValue()).booleanValue() && this.Field662.contains(Search.mc.world.getBlockState(new BlockPos(class448.Field615, class448.Field616, class448.Field617)).getBlock())) {
            return true;
        }
        if (((Boolean) custom.getValue()).booleanValue() && ((Class443) customBlocks.getValue()).Method682().contains(Search.mc.world.getBlockState(new BlockPos(class448.Field615, class448.Field616, class448.Field617)).getBlock())) {
            return true;
        }
        return (Boolean) illegals.getValue() != false && this.Method733(Search.mc.world.getBlockState(new BlockPos(class448.Field615, class448.Field616, class448.Field617)).getBlock(), new BlockPos(class448.Field615, class448.Field616, class448.Field617));
    }

    public static void Method732(double d, double d2, double d3, double d4, double d5, double d6, int n) {
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)1.5f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4f((float)((float)(n >> 16 & 0xFF) / 255.0f), (float)((float)(n >> 8 & 0xFF) / 255.0f), (float)((float)(n & 0xFF) / 255.0f), (float)((float)(n >> 24 & 0xFF) / 255.0f));
        GlStateManager.disableLighting();
        GL11.glLoadIdentity();
        ((IEntityRenderer) Search.mc.entityRenderer).Method1909(mc.getRenderPartialTicks());
        GL11.glEnable((int)2848);
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)d, (double)d2, (double)d3);
        GL11.glVertex3d((double)d4, (double)d5, (double)d6);
        GL11.glVertex3d((double)d4, (double)d5, (double)d6);
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glColor3d((double)1.0, (double)1.0, (double)1.0);
        GlStateManager.enableLighting();
    }

    public static void Method134() {
        block0: {
            if (Search.mc.world == null || Search.mc.player == null) break block0;
            int n = (int) Search.mc.player.posX;
            int n2 = (int) Search.mc.player.posY;
            int n3 = (int) Search.mc.player.posZ;
            int n4 = Search.mc.gameSettings.renderDistanceChunks * 16;
            Search.mc.renderGlobal.markBlockRangeForRenderUpdate(n - n4, n2 - n4, n3 - n4, n + n4, n2 + n4, n3 + n4);
        }
    }

    @Subscriber
    public void Method139(Class89 class89) {
        BlockPos blockPos;
        if (Search.mc.world == null || Search.mc.player == null || this.copyOnWriteArrayList.isEmpty()) {
            return;
        }
        if (((Boolean) fill.getValue()).booleanValue() || ((Boolean) outline.getValue()).booleanValue()) {
            for (Class448 class448 : this.copyOnWriteArrayList) {
                if (class448.Method662(new Class448(Search.mc.player.posX, Search.mc.player.posY, Search.mc.player.posZ)) > (double)((Float) range.getValue()).floatValue() || !this.Method731(class448)) {
                    this.copyOnWriteArrayList.remove(class448);
                    continue;
                }
                blockPos = new BlockPos(class448.Field615, class448.Field616, class448.Field617);
                AxisAlignedBB axisAlignedBB = Search.mc.world.getBlockState(blockPos).getBoundingBox((IBlockAccess) Search.mc.world, blockPos).offset(blockPos);
                if (((Boolean) fill.getValue()).booleanValue()) {
                    Class507.Method1386();
                    Class507.Method1379(axisAlignedBB, (ColorValue) color.getValue());
                    Class507.Method1385();
                }
                if (!((Boolean) outline.getValue()).booleanValue()) continue;
                Class507.Method1386();
                Class507.Method1374(axisAlignedBB, 1.5, (ColorValue) color.getValue());
                Class507.Method1385();
            }
        }
        if (((Boolean) tracers.getValue()).booleanValue()) {
            for (Class448 class448 : this.copyOnWriteArrayList) {
                if (class448.Method662(new Class448(Search.mc.player.posX, Search.mc.player.posY, Search.mc.player.posZ)) > (double)((Float) range.getValue()).floatValue() || !this.Method731(class448)) {
                    this.copyOnWriteArrayList.remove(class448);
                    continue;
                }
                blockPos = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-((float)Math.toRadians(Search.mc.player.rotationPitch))).rotateYaw(-((float)Math.toRadians(Search.mc.player.rotationYaw)));
                Search.Method732(blockPos.x, blockPos.y + (double) Search.mc.player.getEyeHeight(), blockPos.z, class448.Field615 - ((IRenderManager)mc.getRenderManager()).Method69() + 0.5, class448.Field616 - ((IRenderManager)mc.getRenderManager()).Method70() + 0.5, class448.Field617 - ((IRenderManager)mc.getRenderManager()).Method71() + 0.5, ((ColorValue) color.getValue()).Method774());
            }
        }
    }

    public boolean Method733(Block block, BlockPos blockPos) {
        if (block instanceof BlockCommandBlock || block instanceof BlockBarrier) {
            return true;
        }
        if (block == Blocks.BEDROCK) {
            if (Search.mc.player.dimension == 0) {
                if (blockPos.getY() > 4) {
                    return true;
                }
            } else if (Search.mc.player.dimension == -1) {
                if (blockPos.getY() > 127 || blockPos.getY() < 123 && blockPos.getY() > 4) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    public Search() {
        super("Search", Category.RENDER, new String[0]);
        this.Method124();
    }

    public void Method124() {
        this.Field662 = new ArrayList();
        this.Field662.add((Block)Blocks.PORTAL);
        this.Field662.add(Blocks.MOB_SPAWNER);
        this.Field662.add(Blocks.END_PORTAL_FRAME);
        this.Field662.add(Blocks.END_PORTAL);
        this.Field662.add(Blocks.DISPENSER);
        this.Field662.add(Blocks.DROPPER);
        this.Field662.add((Block)Blocks.HOPPER);
        this.Field662.add(Blocks.FURNACE);
        this.Field662.add(Blocks.LIT_FURNACE);
        this.Field662.add((Block)Blocks.CHEST);
        this.Field662.add(Blocks.TRAPPED_CHEST);
        this.Field662.add(Blocks.ENDER_CHEST);
        this.Field662.add(Blocks.WHITE_SHULKER_BOX);
        this.Field662.add(Blocks.ORANGE_SHULKER_BOX);
        this.Field662.add(Blocks.MAGENTA_SHULKER_BOX);
        this.Field662.add(Blocks.LIGHT_BLUE_SHULKER_BOX);
        this.Field662.add(Blocks.YELLOW_SHULKER_BOX);
        this.Field662.add(Blocks.LIME_SHULKER_BOX);
        this.Field662.add(Blocks.PINK_SHULKER_BOX);
        this.Field662.add(Blocks.GRAY_SHULKER_BOX);
        this.Field662.add(Blocks.SILVER_SHULKER_BOX);
        this.Field662.add(Blocks.CYAN_SHULKER_BOX);
        this.Field662.add(Blocks.PURPLE_SHULKER_BOX);
        this.Field662.add(Blocks.BLUE_SHULKER_BOX);
        this.Field662.add(Blocks.BROWN_SHULKER_BOX);
        this.Field662.add(Blocks.GREEN_SHULKER_BOX);
        this.Field662.add(Blocks.RED_SHULKER_BOX);
        this.Field662.add(Blocks.BLACK_SHULKER_BOX);
    }

    @Subscriber
    public void Method734(Class568 class568) {
        Class448 class448;
        if (Search.mc.world == null || Search.mc.player == null) {
            return;
        }
        if (((Boolean) slowRender.getValue()).booleanValue()) {
            if (this.Field675.Method737(1000.0)) {
                this.Field675.Method739();
            } else {
                return;
            }
        }
        if (this.copyOnWriteArrayList.size() > 100000) {
            this.copyOnWriteArrayList.clear();
        }
        if (this.Method735(class568.Method636(), class568.Method72()) && !this.copyOnWriteArrayList.contains(class448 = new Class448(class568.Method72().getX(), class568.Method72().getY(), class568.Method72().getZ()))) {
            this.copyOnWriteArrayList.add(class448);
        }
    }

    public boolean Method735(Block block, BlockPos blockPos) {
        if (((Boolean) aDefault.getValue()).booleanValue() && this.Field662.contains(block)) {
            return true;
        }
        if (((Boolean) custom.getValue()).booleanValue()) {
            if (((Class443) customBlocks.getValue()).Method682().contains(block)) {
                return true;
            }
        }
        return (Boolean) illegals.getValue() != false && this.Method733(block, blockPos);
    }
}
