package me.darki.konas.unremaped;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

import me.darki.konas.module.client.KonasGlobals;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class Class160
extends GuiListExtended {
    public static Logger Field1764 = LogManager.getLogger();
    public Class166 Field1765;
    public ArrayList<Class170> Field1766 = new ArrayList();
    public int Field1767 = -1;

    public void Method1666(int n) {
        this.Field1767 = n;
        this.Field1765.Method1661(this.Method1668());
    }

    public void drawScreen(int n, int n2, float f) {
        block5: {
            int n3;
            if (!this.visible) break block5;
            this.mouseX = n;
            this.mouseY = n2;
            this.drawBackground();
            int n4 = this.getScrollBarX();
            int n5 = n4 + 6;
            this.bindAmountScrolled();
            GlStateManager.disableLighting();
            GlStateManager.disableFog();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            this.drawContainerBackground(tessellator);
            int n6 = this.left + this.width / 2 - this.getListWidth() / 2 + 2;
            int n7 = this.top + 4 - (int)this.amountScrolled;
            if (this.hasListHeader) {
                this.drawListHeader(n6, n7, tessellator);
            }
            ArrayList<Class170> arrayList = this.Field1766;
            boolean bl = false;
            for (n3 = 0; n3 < this.Field1766.size(); ++n3) {
                if (!this.Field1766.get((int)n3).Field1542.Method311()) continue;
                Collections.swap(this.Field1766, 0, n3);
                bl = true;
                break;
            }
            if (bl) {
                this.Method1670(this.left, this.right, this.top, this.top + 39);
            }
            this.drawSelectionBox(n6, n7, n, n2, f);
            this.Field1766 = arrayList;
            GlStateManager.disableDepth();
            this.overlayBackground(0, this.top, 255, 255);
            this.overlayBackground(this.bottom, this.height, 255, 255);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ZERO, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE);
            GlStateManager.disableAlpha();
            GlStateManager.shadeModel((int)7425);
            GlStateManager.disableTexture2D();
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferBuilder.pos((double)this.left, (double)(this.top + 4), 0.0).tex(0.0, 1.0).color(0, 0, 0, 0).endVertex();
            bufferBuilder.pos((double)this.right, (double)(this.top + 4), 0.0).tex(1.0, 1.0).color(0, 0, 0, 0).endVertex();
            bufferBuilder.pos((double)this.right, (double)this.top, 0.0).tex(1.0, 0.0).color(0, 0, 0, 255).endVertex();
            bufferBuilder.pos((double)this.left, (double)this.top, 0.0).tex(0.0, 0.0).color(0, 0, 0, 255).endVertex();
            tessellator.draw();
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferBuilder.pos((double)this.left, (double)this.bottom, 0.0).tex(0.0, 1.0).color(0, 0, 0, 255).endVertex();
            bufferBuilder.pos((double)this.right, (double)this.bottom, 0.0).tex(1.0, 1.0).color(0, 0, 0, 255).endVertex();
            bufferBuilder.pos((double)this.right, (double)(this.bottom - 4), 0.0).tex(1.0, 0.0).color(0, 0, 0, 0).endVertex();
            bufferBuilder.pos((double)this.left, (double)(this.bottom - 4), 0.0).tex(0.0, 0.0).color(0, 0, 0, 0).endVertex();
            tessellator.draw();
            n3 = this.getMaxScroll();
            if (n3 > 0) {
                int n8 = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();
                int n9 = (int)this.amountScrolled * (this.bottom - this.top - (n8 = MathHelper.clamp((int)n8, (int)32, (int)(this.bottom - this.top - 8)))) / n3 + this.top;
                if (n9 < this.top) {
                    n9 = this.top;
                }
                bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                bufferBuilder.pos((double)n4, (double)this.bottom, 0.0).tex(0.0, 1.0).color(0, 0, 0, 255).endVertex();
                bufferBuilder.pos((double)n5, (double)this.bottom, 0.0).tex(1.0, 1.0).color(0, 0, 0, 255).endVertex();
                bufferBuilder.pos((double)n5, (double)this.top, 0.0).tex(1.0, 0.0).color(0, 0, 0, 255).endVertex();
                bufferBuilder.pos((double)n4, (double)this.top, 0.0).tex(0.0, 0.0).color(0, 0, 0, 255).endVertex();
                tessellator.draw();
                bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                bufferBuilder.pos((double)n4, (double)(n9 + n8), 0.0).tex(0.0, 1.0).color(128, 128, 128, 255).endVertex();
                bufferBuilder.pos((double)n5, (double)(n9 + n8), 0.0).tex(1.0, 1.0).color(128, 128, 128, 255).endVertex();
                bufferBuilder.pos((double)n5, (double)n9, 0.0).tex(1.0, 0.0).color(128, 128, 128, 255).endVertex();
                bufferBuilder.pos((double)n4, (double)n9, 0.0).tex(0.0, 0.0).color(128, 128, 128, 255).endVertex();
                tessellator.draw();
                bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                bufferBuilder.pos((double)n4, (double)(n9 + n8 - 1), 0.0).tex(0.0, 1.0).color(192, 192, 192, 255).endVertex();
                bufferBuilder.pos((double)(n5 - 1), (double)(n9 + n8 - 1), 0.0).tex(1.0, 1.0).color(192, 192, 192, 255).endVertex();
                bufferBuilder.pos((double)(n5 - 1), (double)n9, 0.0).tex(1.0, 0.0).color(192, 192, 192, 255).endVertex();
                bufferBuilder.pos((double)n4, (double)n9, 0.0).tex(0.0, 0.0).color(192, 192, 192, 255).endVertex();
                tessellator.draw();
            }
            this.renderDecorations(n, n2);
            GlStateManager.enableTexture2D();
            GlStateManager.shadeModel((int)7424);
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
        }
    }

    public Class160(Class166 class166, Minecraft minecraft, int n, int n2, int n3, int n4, int n5) {
        super(minecraft, n, n2, n3, n4, n5);
        this.Field1765 = class166;
    }

    public int getScrollBarX() {
        return super.getScrollBarX() + 20;
    }

    public Class166 Method1667() {
        return this.Field1765;
    }

    public boolean isSelected(int n) {
        return n == this.Field1767;
    }

    @NotNull
    public GuiListExtended.IGuiListEntry getListEntry(int n) {
        return this.Method1672(n);
    }

    public int getSize() {
        return this.Field1766.size();
    }

    @Nullable
    public Class170 Method1668() {
        return this.Field1767 >= 0 && this.Field1767 < this.getSize() ? this.Method1672(this.Field1767) : null;
    }

    public void Method1669(Class170 class170) {
        this.Field1766.add(class170);
        KonasGlobals.INSTANCE.Field1132.Field1739.add(class170);
    }

    public void Method1670(float f, double d, double d2, double d3) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        this.mc.getTextureManager().bindTexture(Gui.OPTIONS_BACKGROUND);
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        float f2 = 32.0f;
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferBuilder.pos((double)f, d3, 0.0).tex((double)(f / f2), d3 / (double)f2).color(20, 20, 20, 255).endVertex();
        bufferBuilder.pos(d, d3, 0.0).tex(d / (double)f2, d3 / (double)f2).color(20, 20, 20, 255).endVertex();
        bufferBuilder.pos(d, d2, 0.0).tex(d / (double)f2, d2 / (double)f2).color(20, 20, 20, 255).endVertex();
        bufferBuilder.pos((double)f, d2, 0.0).tex((double)(f / f2), d2 / (double)f2).color(20, 20, 20, 255).endVertex();
        Tessellator.getInstance().draw();
    }

    public List<Class170> Method1671() {
        return this.Field1766;
    }

    public int getListWidth() {
        return super.getListWidth() + 50;
    }

    @NotNull
    public Class170 Method1672(int n) {
        return this.Field1766.get(n);
    }

    public void Method1673(Class170 class170) {
        this.Field1766.remove(class170);
    }
}