package me.darki.konas.module.render;

import cookiedragon.eventsystem.Subscriber;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import me.darki.konas.module.Category;
import me.darki.konas.unremaped.Class28;
import me.darki.konas.unremaped.Class408;
import me.darki.konas.unremaped.Class409;
import me.darki.konas.unremaped.Class413;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.unremaped.Class465;
import me.darki.konas.unremaped.Class492;
import me.darki.konas.unremaped.EspRenderUtil;
import me.darki.konas.unremaped.Class516;
import me.darki.konas.unremaped.Class523;
import me.darki.konas.unremaped.Class545;
import me.darki.konas.unremaped.Render3DEvent;
import me.darki.konas.mixin.mixins.IRenderManager;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class HoleEsp
extends Module {
    public Setting<Integer> rangeXY = new Setting<>("RangeXZ", 8, 25, 1, 1);
    public Setting<Integer> rangeY = new Setting<>("RangeY", 5, 25, 1, 1);
    public Setting<Float> width = new Setting<>("Width", Float.valueOf(1.5f), Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(0.1f));
    public Setting<Float> height = new Setting<>("Height", Float.valueOf(1.0f), Float.valueOf(8.0f), Float.valueOf(-2.0f), Float.valueOf(0.1f));
    public Setting<Class408> mode = new Setting<>("Mode", Class408.FULL);
    public Setting<Integer> fadeAlpha = new Setting<>("FadeAlpha", 0, 255, 0, 1).visibleIf(this::Method396);
    public Setting<Boolean> depth = new Setting<>("Depth", true).visibleIf(this::Method388);
    public Setting<Boolean> notLines = new Setting<>("NotLines", true).visibleIf(this::Method394);
    public Setting<Class409> lines = new Setting<>("Lines", Class409.BOTTOM).visibleIf(this::Method393);
    public Setting<Boolean> sides = new Setting<>("Sides", false).visibleIf(this::Method539);
    public Setting<Boolean> notSelf = new Setting<>("NotSelf", true).visibleIf(this::Method519);
    public Setting<Boolean> twoBlock = new Setting<>("TwoBlock", false);
    public Setting<Boolean> bedrock = new Setting<>("Bedrock", true);
    public Setting<ColorValue> bedrockColor = new Setting<>("BedrockColor", new ColorValue(-2013200640)).visibleIf(this.bedrock::getValue);
    public Setting<ColorValue> bedrockLineColor = new Setting<>("BedrockLineColor", new ColorValue(-16711936)).visibleIf(this.bedrock::getValue);
    public Setting<Boolean> obsidian = new Setting<>("Obsidian", true);
    public Setting<ColorValue> obiColor;
    public Setting<ColorValue> obiLineColor;
    public Setting<Boolean> vulnerable;
    public Setting<Boolean> self;
    public Setting<ColorValue> vunColor;
    public Setting<ColorValue> vunLineColor;
    public List<BlockPos> Field1175;
    public List<BlockPos> Field1176;
    public List<Class413> Field1177;
    public List<Class413> Field1178;

    public HoleEsp() {
        super("HoleESP", "Shows you holes", Category.RENDER);
        Setting setting = new Setting<>("ObiColor", new ColorValue(-1996554240));
        Setting<Boolean> setting2 = this.obsidian;
        setting2.getClass();
        this.obiColor = setting.visibleIf(setting2::getValue);
        this.obiLineColor = new Setting<>("ObiLineColor", new ColorValue(-65536)).visibleIf(this.obsidian::getValue);
        this.vulnerable = new Setting<>("Vulnerable", false);
        this.self = new Setting<>("Self", false);
        this.vunColor = new Setting<>("VunColor", new ColorValue(0x66FF00FF)).visibleIf(this.vulnerable::getValue);
        this.vunLineColor = new Setting<>("VunLineColor", new ColorValue(-65281)).visibleIf(this.vulnerable::getValue);
        this.Field1175 = new ArrayList<BlockPos>();
        this.Field1176 = new ArrayList<BlockPos>();
        this.Field1177 = new ArrayList<Class413>();
        this.Field1178 = new ArrayList<Class413>();
    }

    public boolean Method539() {
        return this.mode.getValue() == Class408.FULL || this.mode.getValue() == Class408.FADE;
    }

    public boolean Method388() {
        return this.mode.getValue() == Class408.FADE;
    }

    public boolean Method392(Entity entity) {
        return entity != HoleEsp.mc.player || this.self.getValue() != false;
    }

    public void Method1162(BlockPos blockPos, BlockPos blockPos2, ColorValue colorValue, ColorValue class4402) {
        block14: {
            AxisAlignedBB axisAlignedBB;
            block13: {
                block12: {
                    axisAlignedBB = new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos2.getX() + 1, (float)blockPos2.getY() + this.height.getValue().floatValue(), blockPos2.getZ() + 1);
                    if (this.mode.getValue() == Class408.FULL) {
                        EspRenderUtil.Method1386();
                        EspRenderUtil.Method1381(axisAlignedBB, true, 1.0, colorValue, colorValue.Method782(), this.sides.getValue() != false ? 60 : 63);
                        EspRenderUtil.Method1385();
                    }
                    if (this.mode.getValue() == Class408.FULL) break block12;
                    if (this.mode.getValue() != Class408.OUTLINE) break block13;
                }
                EspRenderUtil.Method1386();
                EspRenderUtil.Method1374(axisAlignedBB, this.width.getValue().floatValue(), class4402);
                EspRenderUtil.Method1385();
            }
            if (this.mode.getValue() == Class408.WIREFRAME) {
                Class523.Method1216();
                Class523.Method1219(axisAlignedBB.offset(-((IRenderManager)mc.getRenderManager()).getRenderPosX(), -((IRenderManager)mc.getRenderManager()).getRenderPosY(), -((IRenderManager)mc.getRenderManager()).getRenderPosZ()), class4402.Method774(), this.width.getValue().floatValue());
                Class523.Method1214();
            }
            if (this.mode.getValue() != Class408.FADE) break block14;
            AxisAlignedBB axisAlignedBB2 = new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos2.getX() + 1, (float)blockPos2.getY() + this.height.getValue().floatValue(), blockPos2.getZ() + 1);
            if (axisAlignedBB2.intersects(HoleEsp.mc.player.getEntityBoundingBox()) && this.notSelf.getValue().booleanValue()) {
                axisAlignedBB2 = axisAlignedBB2.setMaxY(Math.min(axisAlignedBB2.maxY, HoleEsp.mc.player.posY + 1.0));
            }
            EspRenderUtil.Method1386();
            if (this.depth.getValue().booleanValue()) {
                GlStateManager.enableDepth();
                axisAlignedBB2 = axisAlignedBB2.shrink(0.01);
            }
            EspRenderUtil.Method1381(axisAlignedBB2, true, this.height.getValue().floatValue(), colorValue, this.fadeAlpha.getValue(), this.sides.getValue() != false ? 60 : 63);
            if (this.width.getValue().floatValue() >= 0.1f) {
                if (this.lines.getValue() == Class409.BOTTOM) {
                    axisAlignedBB2 = new AxisAlignedBB(axisAlignedBB2.minX, axisAlignedBB2.minY, axisAlignedBB2.minZ, axisAlignedBB2.maxX, axisAlignedBB2.minY, axisAlignedBB2.maxZ);
                } else if (this.lines.getValue() == Class409.TOP) {
                    axisAlignedBB2 = new AxisAlignedBB(axisAlignedBB2.minX, axisAlignedBB2.maxY, axisAlignedBB2.minZ, axisAlignedBB2.maxX, axisAlignedBB2.maxY, axisAlignedBB2.maxZ);
                }
                if (this.notLines.getValue().booleanValue()) {
                    GlStateManager.disableDepth();
                }
                EspRenderUtil.Method1370(axisAlignedBB2, this.width.getValue().floatValue(), class4402, this.fadeAlpha.getValue());
            }
            EspRenderUtil.Method1385();
        }
    }

    public boolean Method386(Entity entity) {
        return entity.getDistance(HoleEsp.mc.player) < (float) this.rangeXY.getValue().intValue();
    }

    @Override
    public boolean Method396() {
        return this.mode.getValue() == Class408.FADE;
    }

    public boolean Method394() {
        return this.mode.getValue() == Class408.FADE && this.depth.getValue() != false;
    }

    @Subscriber
    public void Method390(Class28 class28) {
        if (HoleEsp.mc.world == null || HoleEsp.mc.player == null) {
            return;
        }
        this.Field1175.clear();
        this.Field1176.clear();
        this.Field1177.clear();
        this.Field1178.clear();
        Iterable iterable = BlockPos.getAllInBox(HoleEsp.mc.player.getPosition().add(-this.rangeXY.getValue().intValue(), -this.rangeY.getValue().intValue(), -this.rangeXY.getValue().intValue()), HoleEsp.mc.player.getPosition().add(this.rangeXY.getValue().intValue(), this.rangeY.getValue().intValue(), this.rangeXY.getValue().intValue()));
        for (BlockPos blockPos : iterable) {
            BlockPos blockPos2;
            if (HoleEsp.mc.world.getBlockState(blockPos).getMaterial().blocksMovement() && HoleEsp.mc.world.getBlockState(blockPos.add(0, 1, 0)).getMaterial().blocksMovement() && HoleEsp.mc.world.getBlockState(blockPos.add(0, 2, 0)).getMaterial().blocksMovement()) continue;
            if (Class545.Method1010(blockPos) && this.obsidian.getValue().booleanValue()) {
                this.Field1175.add(blockPos);
            } else {
                blockPos2 = Class545.Method995(blockPos);
                if (blockPos2 != null && this.obsidian.getValue().booleanValue() && this.twoBlock.getValue().booleanValue()) {
                    this.Field1177.add(new Class413(blockPos, blockPos.add(blockPos2.getX(), blockPos2.getY(), blockPos2.getZ())));
                }
            }
            if (Class545.Method1001(blockPos) && this.bedrock.getValue().booleanValue()) {
                this.Field1176.add(blockPos);
                continue;
            }
            blockPos2 = Class545.Method1007(blockPos);
            if (blockPos2 == null || !this.bedrock.getValue().booleanValue() || !this.twoBlock.getValue().booleanValue()) continue;
            this.Field1178.add(new Class413(blockPos, blockPos.add(blockPos2.getX(), blockPos2.getY(), blockPos2.getZ())));
        }
    }

    public static Float Method389(Entity entity) {
        return Float.valueOf(HoleEsp.mc.player.getDistance(entity));
    }

    public void Method1163(BlockPos blockPos, ColorValue colorValue, ColorValue class4402) {
        block10: {
            AxisAlignedBB axisAlignedBB = HoleEsp.mc.world.getBlockState(blockPos).getBoundingBox(HoleEsp.mc.world, blockPos).offset(blockPos);
            axisAlignedBB = axisAlignedBB.setMaxY(axisAlignedBB.minY + (double) this.height.getValue().floatValue());
            if (this.mode.getValue() == Class408.FULL) {
                EspRenderUtil.Method1386();
                EspRenderUtil.Method1381(axisAlignedBB, true, 1.0, colorValue, colorValue.Method782(), this.sides.getValue() != false ? 60 : 63);
                EspRenderUtil.Method1385();
            }
            if (this.mode.getValue() == Class408.FULL || this.mode.getValue() == Class408.OUTLINE) {
                EspRenderUtil.Method1386();
                EspRenderUtil.Method1374(axisAlignedBB, this.width.getValue().floatValue(), class4402);
                EspRenderUtil.Method1385();
            }
            if (this.mode.getValue() == Class408.WIREFRAME) {
                Class523.Method1216();
                Class523.Method1219(axisAlignedBB.offset(-((IRenderManager)mc.getRenderManager()).getRenderPosX(), -((IRenderManager)mc.getRenderManager()).getRenderPosY(), -((IRenderManager)mc.getRenderManager()).getRenderPosZ()), class4402.Method774(), this.width.getValue().floatValue());
                Class523.Method1214();
            }
            if (this.mode.getValue() != Class408.FADE) break block10;
            AxisAlignedBB axisAlignedBB2 = HoleEsp.mc.world.getBlockState(blockPos).getBoundingBox(HoleEsp.mc.world, blockPos).offset(blockPos);
            axisAlignedBB2 = axisAlignedBB2.setMaxY(axisAlignedBB2.minY + (double) this.height.getValue().floatValue());
            if (HoleEsp.mc.player.getEntityBoundingBox() != null && axisAlignedBB2.intersects(HoleEsp.mc.player.getEntityBoundingBox()) && this.notSelf.getValue().booleanValue()) {
                axisAlignedBB2 = axisAlignedBB2.setMaxY(Math.min(axisAlignedBB2.maxY, HoleEsp.mc.player.posY + 1.0));
            }
            EspRenderUtil.Method1386();
            if (this.depth.getValue().booleanValue()) {
                GlStateManager.enableDepth();
                axisAlignedBB2 = axisAlignedBB2.shrink(0.01);
            }
            EspRenderUtil.Method1381(axisAlignedBB2, true, this.height.getValue().floatValue(), colorValue, this.fadeAlpha.getValue(), this.sides.getValue() != false ? 60 : 63);
            if (this.width.getValue().floatValue() >= 0.1f) {
                if (this.lines.getValue() == Class409.BOTTOM) {
                    axisAlignedBB2 = new AxisAlignedBB(axisAlignedBB2.minX, axisAlignedBB2.minY, axisAlignedBB2.minZ, axisAlignedBB2.maxX, axisAlignedBB2.minY, axisAlignedBB2.maxZ);
                } else if (this.lines.getValue() == Class409.TOP) {
                    axisAlignedBB2 = new AxisAlignedBB(axisAlignedBB2.minX, axisAlignedBB2.maxY, axisAlignedBB2.minZ, axisAlignedBB2.maxX, axisAlignedBB2.maxY, axisAlignedBB2.maxZ);
                }
                if (this.notLines.getValue().booleanValue()) {
                    GlStateManager.disableDepth();
                }
                EspRenderUtil.Method1370(axisAlignedBB2, this.width.getValue().floatValue(), class4402, this.fadeAlpha.getValue());
            }
            EspRenderUtil.Method1385();
        }
    }

    @Override
    public String Method756() {
        return this.mode.getValue().toString().charAt(0) + this.mode.getValue().toString().substring(1).toLowerCase();
    }

    public static boolean Method395(Entity entity) {
        return !Class492.Method1989(entity.getName());
    }

    @Subscriber
    public void Method139(Render3DEvent render3DEvent) {
        AxisAlignedBB axisAlignedBB;
        if (HoleEsp.mc.world == null || HoleEsp.mc.player == null) {
            return;
        }
        if (this.mode.getValue() == Class408.BOTTOM) {
            GlStateManager.pushMatrix();
            Class516.Method1289();
            GlStateManager.enableBlend();
            GlStateManager.glLineWidth(5.0f);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            for (BlockPos object : this.Field1176) {
                axisAlignedBB = new AxisAlignedBB(object.getX(), object.getY(), object.getZ(), object.getX() + 1, object.getY(), object.getZ() + 1);
                Class516.Method1281(axisAlignedBB, this.bedrockColor.getValue().Method774());
            }
            for (BlockPos blockPos : this.Field1175) {
                axisAlignedBB = new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX() + 1, blockPos.getY(), blockPos.getZ() + 1);
                Class516.Method1281(axisAlignedBB, this.obiColor.getValue().Method774());
            }
            for (Class413 class413 : this.Field1178) {
                axisAlignedBB = new AxisAlignedBB(class413.Method1123().getX(), class413.Method1123().getY(), class413.Method1123().getZ(), class413.Method1124().getX() + 1, class413.Method1124().getY(), class413.Method1124().getZ() + 1);
                Class516.Method1281(axisAlignedBB, this.bedrockColor.getValue().Method774());
            }
            for (Class413 class413 : this.Field1177) {
                axisAlignedBB = new AxisAlignedBB(class413.Method1123().getX(), class413.Method1123().getY(), class413.Method1123().getZ(), class413.Method1124().getX() + 1, class413.Method1124().getY(), class413.Method1124().getZ() + 1);
                Class516.Method1281(axisAlignedBB, this.obiColor.getValue().Method774());
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            Class516.Method1261();
            GlStateManager.popMatrix();
        } else {
            for (BlockPos blockPos : this.Field1176) {
                this.Method1163(blockPos, this.bedrockColor.getValue(), this.bedrockLineColor.getValue());
            }
            for (BlockPos blockPos : this.Field1175) {
                this.Method1163(blockPos, this.obiColor.getValue(), this.obiLineColor.getValue());
            }
            for (Class413 class413 : this.Field1178) {
                this.Method1162(class413.Method1123(), class413.Method1124(), this.bedrockColor.getValue(), this.bedrockLineColor.getValue());
            }
            for (Class413 class413 : this.Field1177) {
                this.Method1162(class413.Method1123(), class413.Method1124(), this.obiColor.getValue(), this.obiLineColor.getValue());
            }
        }
        if (this.vulnerable.getValue().booleanValue()) {
            List list = HoleEsp.mc.world.loadedEntityList.stream().filter(HoleEsp::Method384).filter(this::Method386).filter(this::Method392).filter(HoleEsp::Method395).sorted(Comparator.comparing(HoleEsp::Method389)).collect(Collectors.toList());
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                axisAlignedBB = (Entity)iterator.next();
                ArrayList<BlockPos> arrayList = Class465.Method2284(new BlockPos((Entity)axisAlignedBB));
                for (BlockPos blockPos : arrayList) {
                    AxisAlignedBB axisAlignedBB2 = HoleEsp.mc.world.getBlockState(blockPos).getBoundingBox(HoleEsp.mc.world, blockPos).offset(blockPos);
                    EspRenderUtil.Method1386();
                    EspRenderUtil.Method1381(axisAlignedBB2, true, 1.0, this.vunColor.getValue(), this.vunColor.getValue().Method782(), 63);
                    EspRenderUtil.Method1374(axisAlignedBB2, this.width.getValue().floatValue(), this.vunLineColor.getValue());
                    EspRenderUtil.Method1385();
                }
            }
        }
    }

    public boolean Method393() {
        return this.mode.getValue() == Class408.FADE;
    }

    public static boolean Method384(Entity entity) {
        return entity instanceof EntityPlayer;
    }

    public boolean Method519() {
        return this.mode.getValue() == Class408.FADE;
    }
}