package me.darki.konas.unremaped;

import me.darki.konas.setting.ColorValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public class EspRenderUtil {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static void Method1367(double d, double d2, double d3, double d4, double d5, double d6, ColorValue colorValue, int n, int n2) {
        GlStateManager.disableAlpha();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        colorValue.Method774();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        EspRenderUtil.Method1383(new AxisAlignedBB(d, d2, d3, d + d4, d2 + d5, d3 + d6), colorValue, n, bufferBuilder, n2, false);
        tessellator.draw();
        GlStateManager.enableAlpha();
    }

    public static void Method1368(double d, double d2, double d3, double d4, double d5, double d6, ColorValue colorValue, float f) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.glLineWidth((float)f);
        colorValue.Method774();
        bufferBuilder.begin(1, DefaultVertexFormats.POSITION);
        EspRenderUtil.Method1375(d, d2, d3, bufferBuilder);
        EspRenderUtil.Method1375(d4, d5, d6, bufferBuilder);
        tessellator.draw();
    }

    public static void Method1369(BlockPos blockPos, int n, ColorValue colorValue, int n2, int n3) {
        EspRenderUtil.Method1377(EspRenderUtil.Method1384(blockPos, 1.0, 1.0, 1.0), n, colorValue, n2, n3);
    }

    public static void Method1370(AxisAlignedBB axisAlignedBB, double d, ColorValue colorValue, int n) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.glLineWidth((float)((float)d));
        colorValue.Method774();
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        EspRenderUtil.Method1372(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, colorValue, colorValue.Method782(), bufferBuilder);
        EspRenderUtil.Method1372(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ, colorValue, colorValue.Method782(), bufferBuilder);
        EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ, colorValue, colorValue.Method782(), bufferBuilder);
        EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ, colorValue, colorValue.Method782(), bufferBuilder);
        EspRenderUtil.Method1372(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, colorValue, colorValue.Method782(), bufferBuilder);
        EspRenderUtil.Method1372(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ, colorValue, n, bufferBuilder);
        EspRenderUtil.Method1372(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ, colorValue, n, bufferBuilder);
        EspRenderUtil.Method1372(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ, colorValue, colorValue.Method782(), bufferBuilder);
        EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ, colorValue, colorValue.Method782(), bufferBuilder);
        EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ, colorValue, n, bufferBuilder);
        EspRenderUtil.Method1372(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ, colorValue, n, bufferBuilder);
        EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ, colorValue, n, bufferBuilder);
        EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ, colorValue, n, bufferBuilder);
        EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ, colorValue, colorValue.Method782(), bufferBuilder);
        EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ, colorValue, n, bufferBuilder);
        EspRenderUtil.Method1372(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ, colorValue, n, bufferBuilder);
        tessellator.draw();
    }

    public static void Method1371(AxisAlignedBB axisAlignedBB, boolean bl, double d, ColorValue colorValue, int n) {
        EspRenderUtil.Method1381(axisAlignedBB, bl, d, colorValue, colorValue.Method782(), n);
    }

    public static void Method1372(double d, double d2, double d3, ColorValue colorValue, int n, BufferBuilder bufferBuilder) {
        bufferBuilder.pos(d - EspRenderUtil.mc.getRenderManager().viewerPosX, d2 - EspRenderUtil.mc.getRenderManager().viewerPosY, d3 - EspRenderUtil.mc.getRenderManager().viewerPosZ).color(colorValue.Method769(), colorValue.Method770(), colorValue.Method779(), n).endVertex();
    }

    public static void Method1373(double d, double d2, double d3, double d4, double d5, double d6, ColorValue colorValue) {
        EspRenderUtil.Method1368(d, d2, d3, d4, d5, d6, colorValue, 1.0f);
    }

    public static void Method1374(AxisAlignedBB axisAlignedBB, double d, ColorValue colorValue) {
        EspRenderUtil.Method1370(axisAlignedBB, d, colorValue, colorValue.Method782());
    }

    public static void Method1375(double d, double d2, double d3, BufferBuilder bufferBuilder) {
        bufferBuilder.pos(d - EspRenderUtil.mc.getRenderManager().viewerPosX, d2 - EspRenderUtil.mc.getRenderManager().viewerPosY, d3 - EspRenderUtil.mc.getRenderManager().viewerPosZ).endVertex();
    }

    public static void Method1376(AxisAlignedBB axisAlignedBB, double d, ColorValue colorValue, int n) {
        EspRenderUtil.Method1381(axisAlignedBB, false, d, colorValue, colorValue.Method782(), n);
    }

    public static void Method1377(AxisAlignedBB axisAlignedBB, int n, ColorValue colorValue, int n2, int n3) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.glLineWidth((float)n);
        bufferBuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        EspRenderUtil.Method1383(axisAlignedBB, colorValue, n2, bufferBuilder, n3, true);
        tessellator.draw();
    }

    public static void Method1378(Class499 class499, ColorValue colorValue, float f) {
        int n;
        for (n = 0; n < 4; ++n) {
            EspRenderUtil.Method1368(class499.Method1934(n)[0], class499.Field2080, class499.Method1934(n)[1], class499.Method1934((n + 1) % 4)[0], class499.Field2080, class499.Method1934((n + 1) % 4)[1], colorValue, f);
        }
        for (n = 0; n < 4; ++n) {
            EspRenderUtil.Method1368(class499.Method1934(n)[0], class499.Field2081, class499.Method1934(n)[1], class499.Method1934((n + 1) % 4)[0], class499.Field2081, class499.Method1934((n + 1) % 4)[1], colorValue, f);
        }
        for (n = 0; n < 4; ++n) {
            EspRenderUtil.Method1368(class499.Method1934(n)[0], class499.Field2080, class499.Method1934(n)[1], class499.Method1934(n)[0], class499.Field2081, class499.Method1934(n)[1], colorValue, f);
        }
    }

    public static void Method1379(AxisAlignedBB axisAlignedBB, ColorValue colorValue) {
        EspRenderUtil.Method1381(axisAlignedBB, true, 1.0, colorValue, colorValue.Method782(), 63);
    }

    public static void Method1380(AxisAlignedBB axisAlignedBB, int n, ColorValue colorValue, int n2) {
        EspRenderUtil.Method1377(axisAlignedBB, n, colorValue, colorValue.Method782(), n2);
    }

    public static void Method1381(AxisAlignedBB axisAlignedBB, boolean bl, double d, ColorValue colorValue, int n, int n2) {
        if (bl) {
            EspRenderUtil.Method1367(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX - axisAlignedBB.minX, axisAlignedBB.maxY - axisAlignedBB.minY, axisAlignedBB.maxZ - axisAlignedBB.minZ, colorValue, n, n2);
        } else {
            EspRenderUtil.Method1367(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX - axisAlignedBB.minX, d, axisAlignedBB.maxZ - axisAlignedBB.minZ, colorValue, n, n2);
        }
    }

    public static void Method1382(BlockPos blockPos, double d, ColorValue colorValue, int n) {
        EspRenderUtil.Method1367(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1.0, d, 1.0, colorValue, colorValue.Method782(), n);
    }

    public static void Method1383(AxisAlignedBB axisAlignedBB, ColorValue colorValue, int n, BufferBuilder bufferBuilder, int n2, boolean bl) {
        block11: {
            if ((n2 & 0x20) != 0) {
                EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ, colorValue, colorValue.Method782(), bufferBuilder);
                EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ, colorValue, colorValue.Method782(), bufferBuilder);
                EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ, colorValue, n, bufferBuilder);
                EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ, colorValue, n, bufferBuilder);
                if (bl) {
                    EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ, colorValue, colorValue.Method782(), bufferBuilder);
                }
            }
            if ((n2 & 0x10) != 0) {
                EspRenderUtil.Method1372(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, colorValue, colorValue.Method782(), bufferBuilder);
                EspRenderUtil.Method1372(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ, colorValue, colorValue.Method782(), bufferBuilder);
                EspRenderUtil.Method1372(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ, colorValue, n, bufferBuilder);
                EspRenderUtil.Method1372(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ, colorValue, n, bufferBuilder);
                if (bl) {
                    EspRenderUtil.Method1372(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, colorValue, colorValue.Method782(), bufferBuilder);
                }
            }
            if ((n2 & 4) != 0) {
                EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ, colorValue, colorValue.Method782(), bufferBuilder);
                EspRenderUtil.Method1372(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, colorValue, colorValue.Method782(), bufferBuilder);
                EspRenderUtil.Method1372(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ, colorValue, n, bufferBuilder);
                EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ, colorValue, n, bufferBuilder);
                if (bl) {
                    EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ, colorValue, colorValue.Method782(), bufferBuilder);
                }
            }
            if ((n2 & 8) != 0) {
                EspRenderUtil.Method1372(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ, colorValue, colorValue.Method782(), bufferBuilder);
                EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ, colorValue, colorValue.Method782(), bufferBuilder);
                EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ, colorValue, n, bufferBuilder);
                EspRenderUtil.Method1372(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ, colorValue, n, bufferBuilder);
                if (bl) {
                    EspRenderUtil.Method1372(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ, colorValue, colorValue.Method782(), bufferBuilder);
                }
            }
            if ((n2 & 2) != 0) {
                EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ, colorValue, n, bufferBuilder);
                EspRenderUtil.Method1372(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ, colorValue, n, bufferBuilder);
                EspRenderUtil.Method1372(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ, colorValue, n, bufferBuilder);
                EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ, colorValue, n, bufferBuilder);
                if (bl) {
                    EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ, colorValue, n, bufferBuilder);
                }
            }
            if ((n2 & 1) == 0) break block11;
            EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ, colorValue, colorValue.Method782(), bufferBuilder);
            EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ, colorValue, colorValue.Method782(), bufferBuilder);
            EspRenderUtil.Method1372(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ, colorValue, colorValue.Method782(), bufferBuilder);
            EspRenderUtil.Method1372(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, colorValue, colorValue.Method782(), bufferBuilder);
            if (bl) {
                EspRenderUtil.Method1372(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ, colorValue, colorValue.Method782(), bufferBuilder);
            }
        }
    }

    public static AxisAlignedBB Method1384(BlockPos blockPos, double d, double d2, double d3) {
        double d4 = blockPos.getX();
        double d5 = blockPos.getY();
        double d6 = blockPos.getZ();
        return new AxisAlignedBB(d4, d5, d6, d4 + d, d5 + d2, d6 + d3);
    }

    public static void Method1385() {
        GL11.glDisable((int)34383);
        GL11.glDisable((int)2848);
        GlStateManager.enableAlpha();
        GlStateManager.enableCull();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.glLineWidth((float)1.0f);
        GlStateManager.shadeModel((int)7424);
        GL11.glHint((int)3154, (int)4352);
        GL11.glPopAttrib();
    }

    public static void Method1386() {
        GL11.glPushAttrib((int)1048575);
        GL11.glHint((int)3154, (int)4354);
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
        GlStateManager.shadeModel((int)7425);
        GlStateManager.depthMask((boolean)false);
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GL11.glEnable((int)2848);
        GL11.glEnable((int)34383);
    }

    public static void Method1387(BlockPos blockPos, double d, float f, ColorValue colorValue) {
        EspRenderUtil.Method1370(EspRenderUtil.Method1384(blockPos, 1.0, d, 1.0), f, colorValue, colorValue.Method782());
    }

    public static void Method1388(BlockPos blockPos, int n, ColorValue colorValue, int n2) {
        EspRenderUtil.Method1377(EspRenderUtil.Method1384(blockPos, 1.0, 1.0, 1.0), n, colorValue, colorValue.Method782(), n2);
    }
}