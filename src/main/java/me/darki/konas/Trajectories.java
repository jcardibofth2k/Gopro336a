package me.darki.konas;

import cookiedragon.eventsystem.Subscriber;
import java.awt.Color;
import java.util.concurrent.CopyOnWriteArrayList;

import me.darki.konas.mixin.mixins.IEntityRenderer;
import me.darki.konas.mixin.mixins.IRenderManager;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;

public class Trajectories
extends Module {
    public static Setting<Boolean> block = new Setting<>("Block", true);
    public static Setting<Boolean> facing = new Setting<>("Facing", true).Method1191(block::getValue);
    public static Setting<Boolean> vector = new Setting<>("Vector", true);
    public static Setting<Float> radius = new Setting<>("Radius", Float.valueOf(0.1f), Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(0.1f)).Method1191(Trajectories::Method393);
    public static Setting<Integer> slices = new Setting<>("Slices", 8, 24, 3, 1).Method1191(Trajectories::Method394);
    public static Setting<ColorValue> fill = new Setting<>("Fill", new ColorValue(584587545, false));
    public static Setting<ColorValue> outline = new Setting<>("Outline", new ColorValue(-2615015, false));
    public static Setting<ColorValue> line = new Setting<>("Line", new ColorValue(-2615015, false));
    public static Setting<ColorValue> vectorColor = new Setting<>("VectorColor", new ColorValue(-2615015, false));
    public static Setting<ColorValue> selfFill = new Setting<>("SelfFill", new ColorValue(575714484, false));
    public static Setting<ColorValue> selfOutline = new Setting<>("SelfOutline", new ColorValue(-11488076, false));
    public static Setting<ColorValue> selfLine = new Setting<>("SelfLine", new ColorValue(-11488076, false));
    public static Setting<ColorValue> selfVector = new Setting<>("SelfVector", new ColorValue(-11488076, false));
    public static Setting<Float> lineWidth = new Setting<>("LineWidth", Float.valueOf(3.0f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f));
    public static Setting<Float> outlineWidth = new Setting<>("OutlineWidth", Float.valueOf(1.5f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f));
    public static Setting<Float> vectorWidth = new Setting<>("VectorWidth", Float.valueOf(1.5f), Float.valueOf(10.0f), Float.valueOf(0.1f), Float.valueOf(0.1f));
    public CopyOnWriteArrayList<Vec3d> Field611 = new CopyOnWriteArrayList();

    public void Method650(Class89 class89, EntityLivingBase entityLivingBase) {
        this.Method654(entityLivingBase, class89.Method436());
    }

    public Trajectories() {
        super("Trajectories", Category.RENDER, "ThrowLines", "PearlLines", "ArrowPaths");
    }

    public static boolean Method651(EntityLivingBase entityLivingBase) {
        Module module = Class167.Method1612("BowAim");
        if (entityLivingBase == Trajectories.mc.player && module != null) {
            return module.Method1651() && !Class317.Field763.Method737(350.0);
        }
        return false;
    }

    public Class420 Method652(EntityLivingBase entityLivingBase) {
        if (entityLivingBase.getHeldItemMainhand().isEmpty()) {
            return Class420.NONE;
        }
        ItemStack itemStack = entityLivingBase.getHeldItem(EnumHand.MAIN_HAND);
        switch (Item.getIdFromItem((Item)itemStack.getItem())) {
            case 261: {
                if (!entityLivingBase.isHandActive()) break;
                return Class420.ARROW;
            }
            case 346: {
                return Class420.FISHING_ROD;
            }
            case 438: 
            case 441: {
                return Class420.POTION;
            }
            case 384: {
                return Class420.EXPERIENCE;
            }
            case 332: 
            case 344: 
            case 368: {
                return Class420.NORMAL;
            }
        }
        return Class420.NONE;
    }

    public static boolean Method394() {
        return (Boolean) vector.getValue();
    }

    public static boolean Method513(Entity entity) {
        return entity instanceof EntityLivingBase;
    }

    public void Method653(EntityLivingBase entityLivingBase, float f) {
        boolean bl = Trajectories.mc.gameSettings.viewBobbing;
        Trajectories.mc.gameSettings.viewBobbing = false;
        ((IEntityRenderer) Trajectories.mc.entityRenderer).Method1908(f, 0);
        GL11.glPushAttrib((int)1048575);
        GL11.glPushMatrix();
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2884);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glDepthMask((boolean)false);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)((Float) lineWidth.getValue()).floatValue());
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        Vec3d vec3d = this.Field611.get(0);
        Color color = entityLivingBase == Trajectories.mc.player ? ((ColorValue) selfLine.getValue()).Method775() : ((ColorValue) line.getValue()).Method775();
        for (Vec3d vec3d2 : this.Field611) {
            bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder.pos(vec3d.x, vec3d.y, vec3d.z).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
            bufferBuilder.pos(vec3d2.x, vec3d2.y, vec3d2.z).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
            vec3d = vec3d2;
            tessellator.draw();
        }
        GL11.glDisable((int)2848);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2884);
        GL11.glEnable((int)2896);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
        Trajectories.mc.gameSettings.viewBobbing = bl;
        ((IEntityRenderer) Trajectories.mc.entityRenderer).Method1908(f, 0);
    }

    public void Method654(EntityLivingBase entityLivingBase, float f) {
        block18: {
            Class420 class420 = this.Method652(entityLivingBase);
            if (class420 == Class420.NONE) {
                return;
            }
            Class423 class423 = new Class423(this, entityLivingBase, class420);
            this.Field611.clear();
            while (!class423.Method1068()) {
                class423.Method1064();
                this.Field611.add(new Vec3d(Class423.Method1066((Class423)class423).x - Trajectories.mc.getRenderManager().viewerPosX, Class423.Method1066((Class423)class423).y - Trajectories.mc.getRenderManager().viewerPosY, Class423.Method1066((Class423)class423).z - Trajectories.mc.getRenderManager().viewerPosZ));
            }
            this.Method653(entityLivingBase, f);
            if (!Class423.Method1063(class423)) break block18;
            RayTraceResult rayTraceResult = Class423.Method1067(class423);
            AxisAlignedBB axisAlignedBB = null;
            if (rayTraceResult == null) {
                return;
            }
            if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos blockPos = rayTraceResult.getBlockPos();
                IBlockState iBlockState = Trajectories.mc.world.getBlockState(blockPos);
                if (iBlockState.getMaterial() != Material.AIR && Trajectories.mc.world.getWorldBorder().contains(blockPos)) {
                    if (((Boolean) vector.getValue()).booleanValue()) {
                        this.Method655(entityLivingBase, rayTraceResult);
                    }
                    axisAlignedBB = iBlockState.getSelectedBoundingBox((World) Trajectories.mc.world, blockPos).grow((double)0.002f);
                }
            } else if (rayTraceResult.typeOfHit == RayTraceResult.Type.ENTITY && rayTraceResult.entityHit != null && rayTraceResult.entityHit != Trajectories.mc.player) {
                axisAlignedBB = rayTraceResult.entityHit.getEntityBoundingBox();
            }
            if (axisAlignedBB != null && ((Boolean) block.getValue()).booleanValue()) {
                if (((Boolean) facing.getValue()).booleanValue() && rayTraceResult.sideHit != null) {
                    switch (rayTraceResult.sideHit) {
                        case DOWN: {
                            axisAlignedBB = new AxisAlignedBB(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ);
                            break;
                        }
                        case UP: {
                            axisAlignedBB = new AxisAlignedBB(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
                            break;
                        }
                        case NORTH: {
                            axisAlignedBB = new AxisAlignedBB(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ);
                            break;
                        }
                        case SOUTH: {
                            axisAlignedBB = new AxisAlignedBB(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ, axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
                            break;
                        }
                        case EAST: {
                            axisAlignedBB = new AxisAlignedBB(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
                            break;
                        }
                        case WEST: {
                            axisAlignedBB = new AxisAlignedBB(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
                        }
                    }
                }
                Class507.Method1386();
                Class507.Method1381(axisAlignedBB, true, 1.0, entityLivingBase == Trajectories.mc.player ? (ColorValue) selfFill.getValue() : (ColorValue) fill.getValue(), entityLivingBase == Trajectories.mc.player ? ((ColorValue) selfFill.getValue()).Method782() : ((ColorValue) fill.getValue()).Method782(), 63);
                Class507.Method1374(axisAlignedBB, ((Float) outlineWidth.getValue()).floatValue(), entityLivingBase == Trajectories.mc.player ? (ColorValue) selfOutline.getValue() : (ColorValue) outline.getValue());
                Class507.Method1385();
                Class468.Field2604.Method739();
            }
        }
    }

    public static boolean Method393() {
        return (Boolean) vector.getValue();
    }

    public void Method655(EntityLivingBase entityLivingBase, RayTraceResult rayTraceResult) {
        GlStateManager.pushMatrix();
        Class516.Method1289();
        GlStateManager.glLineWidth((float)((Float) vectorWidth.getValue()).floatValue());
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GlStateManager.disableDepth();
        GL11.glLineWidth((float)((Float) vectorWidth.getValue()).floatValue());
        if (entityLivingBase == Trajectories.mc.player) {
            GL11.glColor4f((float)((float)((ColorValue) selfVector.getValue()).Method769() / 255.0f), (float)((float)((ColorValue) selfVector.getValue()).Method770() / 255.0f), (float)((float)((ColorValue) selfVector.getValue()).Method779() / 255.0f), (float)((float)((ColorValue) selfVector.getValue()).Method782() / 255.0f));
        } else {
            GL11.glColor4f((float)((float)((ColorValue) vectorColor.getValue()).Method769() / 255.0f), (float)((float)((ColorValue) vectorColor.getValue()).Method770() / 255.0f), (float)((float)((ColorValue) vectorColor.getValue()).Method779() / 255.0f), (float)((float)((ColorValue) vectorColor.getValue()).Method782() / 255.0f));
        }
        GlStateManager.translate((double)(rayTraceResult.hitVec.x - ((IRenderManager)mc.getRenderManager()).Method69()), (double)(rayTraceResult.hitVec.y - ((IRenderManager)mc.getRenderManager()).Method70()), (double)(rayTraceResult.hitVec.z - ((IRenderManager)mc.getRenderManager()).Method71()));
        EnumFacing enumFacing = rayTraceResult.sideHit;
        switch (enumFacing) {
            case NORTH: 
            case SOUTH: {
                GlStateManager.rotate((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                break;
            }
            case EAST: 
            case WEST: {
                GlStateManager.rotate((float)90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                break;
            }
        }
        Cylinder cylinder = new Cylinder();
        GlStateManager.rotate((float)-90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        cylinder.setDrawStyle(100011);
        cylinder.draw(((Float) radius.getValue()).floatValue() * 2.0f, ((Float) radius.getValue()).floatValue(), 0.0f, ((Integer) slices.getValue()).intValue(), 1);
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.enableDepth();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableTexture2D();
        Class516.Method1261();
        GlStateManager.popMatrix();
    }

    @Subscriber
    public void Method139(Class89 class89) {
        Trajectories.mc.world.loadedEntityList.stream().filter(Trajectories::Method513).map(Trajectories::Method656).forEach(arg_0 -> this.Method650(class89, arg_0));
    }

    public static EntityLivingBase Method656(Entity entity) {
        return (EntityLivingBase)entity;
    }
}
