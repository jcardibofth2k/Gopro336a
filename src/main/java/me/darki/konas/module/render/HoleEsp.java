package me.darki.konas.module.render;

import cookiedragon.eventsystem.Subscriber;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import me.darki.konas.Category;
import me.darki.konas.Class28;
import me.darki.konas.Class408;
import me.darki.konas.Class409;
import me.darki.konas.Class413;
import me.darki.konas.ColorValue;
import me.darki.konas.Class465;
import me.darki.konas.Class492;
import me.darki.konas.Class507;
import me.darki.konas.Class516;
import me.darki.konas.Class523;
import me.darki.konas.Class545;
import me.darki.konas.Class89;
import me.darki.konas.mixin.mixins.IRenderManager;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class HoleEsp
extends Module {
    public Setting<Integer> rangeXY = new Setting<>("RangeXZ", 8, 25, 1, 1);
    public Setting<Integer> rangeY = new Setting<>("RangeY", 5, 25, 1, 1);
    public Setting<Float> width = new Setting<>("Width", Float.valueOf(1.5f), Float.valueOf(10.0f), Float.valueOf(0.0f), Float.valueOf(0.1f));
    public Setting<Float> height = new Setting<>("Height", Float.valueOf(1.0f), Float.valueOf(8.0f), Float.valueOf(-2.0f), Float.valueOf(0.1f));
    public Setting<Class408> mode = new Setting<>("Mode", Class408.FULL);
    public Setting<Integer> fadeAlpha = new Setting<>("FadeAlpha", 0, 255, 0, 1).Method1191(this::Method396);
    public Setting<Boolean> depth = new Setting<>("Depth", true).Method1191(this::Method388);
    public Setting<Boolean> notLines = new Setting<>("NotLines", true).Method1191(this::Method394);
    public Setting<Class409> lines = new Setting<>("Lines", Class409.BOTTOM).Method1191(this::Method393);
    public Setting<Boolean> sides = new Setting<>("Sides", false).Method1191(this::Method539);
    public Setting<Boolean> notSelf = new Setting<>("NotSelf", true).Method1191(this::Method519);
    public Setting<Boolean> twoBlock = new Setting<>("TwoBlock", false);
    public Setting<Boolean> bedrock = new Setting<>("Bedrock", true);
    public Setting<ColorValue> bedrockColor = new Setting<>("BedrockColor", new ColorValue(-2013200640)).Method1191(this.bedrock::getValue);
    public Setting<ColorValue> bedrockLineColor = new Setting<>("BedrockLineColor", new ColorValue(-16711936)).Method1191(this.bedrock::getValue);
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
        super("HoleESP", "Shows you holes", Category.RENDER, new String[0]);
        Setting setting = new Setting<>("ObiColor", new ColorValue(-1996554240));
        Setting<Boolean> setting2 = this.obsidian;
        setting2.getClass();
        this.obiColor = setting.Method1191(setting2::getValue);
        this.obiLineColor = new Setting<>("ObiLineColor", new ColorValue(-65536)).Method1191(this.obsidian::getValue);
        this.vulnerable = new Setting<>("Vulnerable", false);
        this.self = new Setting<>("Self", false);
        this.vunColor = new Setting<>("VunColor", new ColorValue(0x66FF00FF)).Method1191(this.vulnerable::getValue);
        this.vunLineColor = new Setting<>("VunLineColor", new ColorValue(-65281)).Method1191(this.vulnerable::getValue);
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
        return entity != HoleEsp.mc.player || (Boolean)this.self.getValue() != false;
    }

    public void Method1162(BlockPos blockPos, BlockPos blockPos2, ColorValue colorValue, ColorValue class4402) {
        block14: {
            AxisAlignedBB axisAlignedBB;
            block13: {
                block12: {
                    axisAlignedBB = new AxisAlignedBB((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), (double)(blockPos2.getX() + 1), (double)((float)blockPos2.getY() + ((Float)this.height.getValue()).floatValue()), (double)(blockPos2.getZ() + 1));
                    if (this.mode.getValue() == Class408.FULL) {
                        Class507.Method1386();
                        Class507.Method1381(axisAlignedBB, true, 1.0, colorValue, colorValue.Method782(), (Boolean)this.sides.getValue() != false ? 60 : 63);
                        Class507.Method1385();
                    }
                    if (this.mode.getValue() == Class408.FULL) break block12;
                    if (this.mode.getValue() != Class408.OUTLINE) break block13;
                }
                Class507.Method1386();
                Class507.Method1374(axisAlignedBB, ((Float)this.width.getValue()).floatValue(), class4402);
                Class507.Method1385();
            }
            if (this.mode.getValue() == Class408.WIREFRAME) {
                Class523.Method1216();
                Class523.Method1219(axisAlignedBB.offset(-((IRenderManager)mc.getRenderManager()).Method69(), -((IRenderManager)mc.getRenderManager()).Method70(), -((IRenderManager)mc.getRenderManager()).Method71()), class4402.Method774(), ((Float)this.width.getValue()).floatValue());
                Class523.Method1214();
            }
            if (this.mode.getValue() != Class408.FADE) break block14;
            AxisAlignedBB axisAlignedBB2 = new AxisAlignedBB((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), (double)(blockPos2.getX() + 1), (double)((float)blockPos2.getY() + ((Float)this.height.getValue()).floatValue()), (double)(blockPos2.getZ() + 1));
            if (axisAlignedBB2.intersects(HoleEsp.mc.player.getEntityBoundingBox()) && ((Boolean)this.notSelf.getValue()).booleanValue()) {
                axisAlignedBB2 = axisAlignedBB2.setMaxY(Math.min(axisAlignedBB2.maxY, HoleEsp.mc.player.posY + 1.0));
            }
            Class507.Method1386();
            if (((Boolean)this.depth.getValue()).booleanValue()) {
                GlStateManager.enableDepth();
                axisAlignedBB2 = axisAlignedBB2.shrink(0.01);
            }
            Class507.Method1381(axisAlignedBB2, true, ((Float)this.height.getValue()).floatValue(), colorValue, (Integer)this.fadeAlpha.getValue(), (Boolean)this.sides.getValue() != false ? 60 : 63);
            if (((Float)this.width.getValue()).floatValue() >= 0.1f) {
                if (this.lines.getValue() == Class409.BOTTOM) {
                    axisAlignedBB2 = new AxisAlignedBB(axisAlignedBB2.minX, axisAlignedBB2.minY, axisAlignedBB2.minZ, axisAlignedBB2.maxX, axisAlignedBB2.minY, axisAlignedBB2.maxZ);
                } else if (this.lines.getValue() == Class409.TOP) {
                    axisAlignedBB2 = new AxisAlignedBB(axisAlignedBB2.minX, axisAlignedBB2.maxY, axisAlignedBB2.minZ, axisAlignedBB2.maxX, axisAlignedBB2.maxY, axisAlignedBB2.maxZ);
                }
                if (((Boolean)this.notLines.getValue()).booleanValue()) {
                    GlStateManager.disableDepth();
                }
                Class507.Method1370(axisAlignedBB2, ((Float)this.width.getValue()).floatValue(), class4402, (Integer)this.fadeAlpha.getValue());
            }
            Class507.Method1385();
        }
    }

    public boolean Method386(Entity entity) {
        return entity.getDistance((Entity)HoleEsp.mc.player) < (float)((Integer)this.rangeXY.getValue()).intValue();
    }

    @Override
    public boolean Method396() {
        return this.mode.getValue() == Class408.FADE;
    }

    public boolean Method394() {
        return this.mode.getValue() == Class408.FADE && (Boolean)this.depth.getValue() != false;
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
        Iterable iterable = BlockPos.getAllInBox((BlockPos)HoleEsp.mc.player.getPosition().add(-((Integer)this.rangeXY.getValue()).intValue(), -((Integer)this.rangeY.getValue()).intValue(), -((Integer)this.rangeXY.getValue()).intValue()), (BlockPos)HoleEsp.mc.player.getPosition().add(((Integer)this.rangeXY.getValue()).intValue(), ((Integer)this.rangeY.getValue()).intValue(), ((Integer)this.rangeXY.getValue()).intValue()));
        for (BlockPos blockPos : iterable) {
            BlockPos blockPos2;
            if (HoleEsp.mc.world.getBlockState(blockPos).getMaterial().blocksMovement() && HoleEsp.mc.world.getBlockState(blockPos.add(0, 1, 0)).getMaterial().blocksMovement() && HoleEsp.mc.world.getBlockState(blockPos.add(0, 2, 0)).getMaterial().blocksMovement()) continue;
            if (Class545.Method1010(blockPos) && ((Boolean)this.obsidian.getValue()).booleanValue()) {
                this.Field1175.add(blockPos);
            } else {
                blockPos2 = Class545.Method995(blockPos);
                if (blockPos2 != null && ((Boolean)this.obsidian.getValue()).booleanValue() && ((Boolean)this.twoBlock.getValue()).booleanValue()) {
                    this.Field1177.add(new Class413(blockPos, blockPos.add(blockPos2.getX(), blockPos2.getY(), blockPos2.getZ())));
                }
            }
            if (Class545.Method1001(blockPos) && ((Boolean)this.bedrock.getValue()).booleanValue()) {
                this.Field1176.add(blockPos);
                continue;
            }
            blockPos2 = Class545.Method1007(blockPos);
            if (blockPos2 == null || !((Boolean)this.bedrock.getValue()).booleanValue() || !((Boolean)this.twoBlock.getValue()).booleanValue()) continue;
            this.Field1178.add(new Class413(blockPos, blockPos.add(blockPos2.getX(), blockPos2.getY(), blockPos2.getZ())));
        }
    }

    public static Float Method389(Entity entity) {
        return Float.valueOf(HoleEsp.mc.player.getDistance(entity));
    }

    public void Method1163(BlockPos blockPos, ColorValue colorValue, ColorValue class4402) {
        block10: {
            AxisAlignedBB axisAlignedBB = HoleEsp.mc.world.getBlockState(blockPos).getBoundingBox((IBlockAccess)HoleEsp.mc.world, blockPos).offset(blockPos);
            axisAlignedBB = axisAlignedBB.setMaxY(axisAlignedBB.minY + (double)((Float)this.height.getValue()).floatValue());
            if (this.mode.getValue() == Class408.FULL) {
                Class507.Method1386();
                Class507.Method1381(axisAlignedBB, true, 1.0, colorValue, colorValue.Method782(), (Boolean)this.sides.getValue() != false ? 60 : 63);
                Class507.Method1385();
            }
            if (this.mode.getValue() == Class408.FULL || this.mode.getValue() == Class408.OUTLINE) {
                Class507.Method1386();
                Class507.Method1374(axisAlignedBB, ((Float)this.width.getValue()).floatValue(), class4402);
                Class507.Method1385();
            }
            if (this.mode.getValue() == Class408.WIREFRAME) {
                Class523.Method1216();
                Class523.Method1219(axisAlignedBB.offset(-((IRenderManager)mc.getRenderManager()).Method69(), -((IRenderManager)mc.getRenderManager()).Method70(), -((IRenderManager)mc.getRenderManager()).Method71()), class4402.Method774(), ((Float)this.width.getValue()).floatValue());
                Class523.Method1214();
            }
            if (this.mode.getValue() != Class408.FADE) break block10;
            AxisAlignedBB axisAlignedBB2 = HoleEsp.mc.world.getBlockState(blockPos).getBoundingBox((IBlockAccess)HoleEsp.mc.world, blockPos).offset(blockPos);
            axisAlignedBB2 = axisAlignedBB2.setMaxY(axisAlignedBB2.minY + (double)((Float)this.height.getValue()).floatValue());
            if (HoleEsp.mc.player.getEntityBoundingBox() != null && axisAlignedBB2.intersects(HoleEsp.mc.player.getEntityBoundingBox()) && ((Boolean)this.notSelf.getValue()).booleanValue()) {
                axisAlignedBB2 = axisAlignedBB2.setMaxY(Math.min(axisAlignedBB2.maxY, HoleEsp.mc.player.posY + 1.0));
            }
            Class507.Method1386();
            if (((Boolean)this.depth.getValue()).booleanValue()) {
                GlStateManager.enableDepth();
                axisAlignedBB2 = axisAlignedBB2.shrink(0.01);
            }
            Class507.Method1381(axisAlignedBB2, true, ((Float)this.height.getValue()).floatValue(), colorValue, (Integer)this.fadeAlpha.getValue(), (Boolean)this.sides.getValue() != false ? 60 : 63);
            if (((Float)this.width.getValue()).floatValue() >= 0.1f) {
                if (this.lines.getValue() == Class409.BOTTOM) {
                    axisAlignedBB2 = new AxisAlignedBB(axisAlignedBB2.minX, axisAlignedBB2.minY, axisAlignedBB2.minZ, axisAlignedBB2.maxX, axisAlignedBB2.minY, axisAlignedBB2.maxZ);
                } else if (this.lines.getValue() == Class409.TOP) {
                    axisAlignedBB2 = new AxisAlignedBB(axisAlignedBB2.minX, axisAlignedBB2.maxY, axisAlignedBB2.minZ, axisAlignedBB2.maxX, axisAlignedBB2.maxY, axisAlignedBB2.maxZ);
                }
                if (((Boolean)this.notLines.getValue()).booleanValue()) {
                    GlStateManager.disableDepth();
                }
                Class507.Method1370(axisAlignedBB2, ((Float)this.width.getValue()).floatValue(), class4402, (Integer)this.fadeAlpha.getValue());
            }
            Class507.Method1385();
        }
    }

    @Override
    public String Method756() {
        return ((Class408)((Object)this.mode.getValue())).toString().charAt(0) + ((Class408)((Object)this.mode.getValue())).toString().substring(1).toLowerCase();
    }

    public static boolean Method395(Entity entity) {
        return !Class492.Method1989(entity.getName());
    }

    @Subscriber
    public void Method139(Class89 class89) {
        AxisAlignedBB axisAlignedBB;
        if (HoleEsp.mc.world == null || HoleEsp.mc.player == null) {
            return;
        }
        if (this.mode.getValue() == Class408.BOTTOM) {
            GlStateManager.pushMatrix();
            Class516.Method1289();
            GlStateManager.enableBlend();
            GlStateManager.glLineWidth((float)5.0f);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask((boolean)false);
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
            for (BlockPos object : this.Field1176) {
                axisAlignedBB = new AxisAlignedBB((double)object.getX(), (double)object.getY(), (double)object.getZ(), (double)(object.getX() + 1), (double)object.getY(), (double)(object.getZ() + 1));
                Class516.Method1281(axisAlignedBB, ((ColorValue)this.bedrockColor.getValue()).Method774());
            }
            for (BlockPos blockPos : this.Field1175) {
                axisAlignedBB = new AxisAlignedBB((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), (double)(blockPos.getX() + 1), (double)blockPos.getY(), (double)(blockPos.getZ() + 1));
                Class516.Method1281(axisAlignedBB, ((ColorValue)this.obiColor.getValue()).Method774());
            }
            for (Class413 class413 : this.Field1178) {
                axisAlignedBB = new AxisAlignedBB((double)class413.Method1123().getX(), (double)class413.Method1123().getY(), (double)class413.Method1123().getZ(), (double)(class413.Method1124().getX() + 1), (double)class413.Method1124().getY(), (double)(class413.Method1124().getZ() + 1));
                Class516.Method1281(axisAlignedBB, ((ColorValue)this.bedrockColor.getValue()).Method774());
            }
            for (Class413 class413 : this.Field1177) {
                axisAlignedBB = new AxisAlignedBB((double)class413.Method1123().getX(), (double)class413.Method1123().getY(), (double)class413.Method1123().getZ(), (double)(class413.Method1124().getX() + 1), (double)class413.Method1124().getY(), (double)(class413.Method1124().getZ() + 1));
                Class516.Method1281(axisAlignedBB, ((ColorValue)this.obiColor.getValue()).Method774());
            }
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.enableDepth();
            GlStateManager.depthMask((boolean)true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            Class516.Method1261();
            GlStateManager.popMatrix();
        } else {
            for (BlockPos blockPos : this.Field1176) {
                this.Method1163(blockPos, (ColorValue)this.bedrockColor.getValue(), (ColorValue)this.bedrockLineColor.getValue());
            }
            for (BlockPos blockPos : this.Field1175) {
                this.Method1163(blockPos, (ColorValue)this.obiColor.getValue(), (ColorValue)this.obiLineColor.getValue());
            }
            for (Class413 class413 : this.Field1178) {
                this.Method1162(class413.Method1123(), class413.Method1124(), (ColorValue)this.bedrockColor.getValue(), (ColorValue)this.bedrockLineColor.getValue());
            }
            for (Class413 class413 : this.Field1177) {
                this.Method1162(class413.Method1123(), class413.Method1124(), (ColorValue)this.obiColor.getValue(), (ColorValue)this.obiLineColor.getValue());
            }
        }
        if (((Boolean)this.vulnerable.getValue()).booleanValue()) {
            List list = HoleEsp.mc.world.loadedEntityList.stream().filter(HoleEsp::Method384).filter(this::Method386).filter(this::Method392).filter(HoleEsp::Method395).sorted(Comparator.comparing(HoleEsp::Method389)).collect(Collectors.toList());
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                axisAlignedBB = (Entity)iterator.next();
                ArrayList<BlockPos> arrayList = Class465.Method2284(new BlockPos((Entity)axisAlignedBB));
                for (BlockPos blockPos : arrayList) {
                    AxisAlignedBB axisAlignedBB2 = HoleEsp.mc.world.getBlockState(blockPos).getBoundingBox((IBlockAccess)HoleEsp.mc.world, blockPos).offset(blockPos);
                    Class507.Method1386();
                    Class507.Method1381(axisAlignedBB2, true, 1.0, (ColorValue)this.vunColor.getValue(), ((ColorValue)this.vunColor.getValue()).Method782(), 63);
                    Class507.Method1374(axisAlignedBB2, ((Float)this.width.getValue()).floatValue(), (ColorValue)this.vunLineColor.getValue());
                    Class507.Method1385();
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
