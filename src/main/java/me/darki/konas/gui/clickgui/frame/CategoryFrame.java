package me.darki.konas.gui.clickgui.frame;

import java.util.ArrayList;

import me.darki.konas.gui.clickgui.component.Component;
import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class180;
import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class193;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.unremaped.*;
import me.darki.konas.util.RenderUtil2;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.module.client.ClickGUIModule;
import me.darki.konas.module.client.KonasGlobals;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class CategoryFrame
extends Frame {
    public ArrayList<Component> components = new ArrayList();
    public int scrollY;
    public int ticks = 0;
    public boolean shouldDisable = false;

    @Override
    public void onMove(float f, float f2) {
        if (!Class193.Method120()) {
            return;
        }
        this.components.forEach(component -> onMove(f, f2));
    }

    @Override
    public void onMouseReleased(int n, int n2, int n3) {
        block2: {
            super.onMouseReleased(n, n2, n3);
            if (!Class193.Method120()) {
                return;
            }
            if (n3 == 0 && this.isDragging()) {
                this.setDragging(false);
            }
            if (!this.isExtended()) break block2;
            this.getComponents().forEach(arg_0 -> CategoryFrame.Method701(n, n2, n3, arg_0));
        }
    }

    

    public void setScrollY(int n) {
        this.scrollY = n;
    }

    @Override
    public boolean onMouseClicked(int n, int n2, int n3) {
        super.onMouseClicked(n, n2, n3);
        if (!Class193.Method120()) {
            return false;
        }
        boolean bl = CategoryFrame.isMouseWithinBounds(n, n2, this.getPosX(), this.Method921(), this.getWidth(), this.getHeight());
        switch (n3) {
            case 0: {
                if (!bl) break;
                this.setDragging(true);
                this.setPreviousX(this.getPosX() - (float)n);
                this.setPreviousY(this.Method921() - (float)n2);
                return true;
            }
            case 1: {
                if (!bl) break;
                this.setExtended(!this.isExtended());
                return true;
            }
        }
        if (this.isExtended() && CategoryFrame.isMouseWithinBounds(n, n2, this.getPosX(), this.Method921() + this.getHeight(), this.getWidth(), this.getTotalHeight() > ((Float) ClickGUIModule.maxHeight.getValue()).floatValue() ? (double)((Float) ClickGUIModule.maxHeight.getValue()).floatValue() : (double)this.getTotalHeight())) {
            for (Component class183 : this.getComponents()) {
                if (!class183.Method106(n, n2, n3)) continue;
                return true;
            }
        }
        return false;
    }

    public void initialise() {
        float f = this.getHeight();
        for (Component class183 : this.getComponents()) {
            class183.setYOffset(f);
            class183.onMove(this.getPosX(), this.Method921() + (float)this.getScrollY());
            if (class183 instanceof Class180 && class183.Method1486()) {
                for (Component class1832 : ((Class180)class183).Method1494()) {
                    f += class1832.Method1481();
                }
            }
            f += class183.Method1481();
        }
    }

    public void Method693(Component class183) {
        class183.onMove(this.getPosX(), this.Method921() + (float)this.getScrollY());
    }

    @Override
    public void Method704(int n, int n2, int n3, long l) {
        block1: {
            super.Method704(n, n2, n3, l);
            if (!Class193.Method120()) {
                return;
            }
            if (!this.isExtended()) break block1;
            this.getComponents().forEach(arg_0 -> CategoryFrame.Method695(n, n2, n3, l, arg_0));
        }
    }

    public ArrayList<Component> getComponents() {
        return this.components;
    }
    

    public void Method705(Component class183) {
        class183.onMove(this.getPosX(), this.Method921() + (float)this.getScrollY());
    }

    @Override
    public void Method712() {
        float f = this.getHeight();
        for (Element element : KonasGlobals.INSTANCE.Field1136.Method2196()) {
            this.getComponents().add(new Class180(element, this.getPosX(), this.Method921(), 2.0f, f, this.getWidth() - 4.0f, 12.0f));
            f += 12.0f;
        }
        this.getComponents().forEach(Component::Method667);
    }

    public void Method697(Component class183) {
        class183.onMove(this.getPosX(), this.Method921() + (float)this.getScrollY());
    }

    public void Method699(Component class183) {
        class183.onMove(this.getPosX(), this.Method921() + (float)this.getScrollY());
    }

    public CategoryFrame(float f, float f2, float f3, float f4) {
        super("Containers", f, f2, f3, f4);
    }

    public static void Method695(int n, int n2, int n3, long l, Component class183) {
        class183.Method649(n, n2, n3, l);
    }

    public static void Method701(int n, int n2, int n3, Component class183) {
        class183.Method647(n, n2, n3);
    }

    @Override
    public void onRender(int n, int n2, float f) {
        super.onRender(n, n2, f);
        if (!Class193.Method120()) {
            return;
        }
        if (this.isDragging()) {
            this.setPosX((float)n + this.getPreviousX());
            this.Method442((float)n2 + this.getPreviousY());
            this.getComponents().forEach(this::Method693);
        }
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        if (this.getPosX() < 0.0f) {
            this.setPosX(0.0f);
            this.getComponents().forEach(this::Method699);
        }
        if (this.getPosX() + this.getWidth() > (float)scaledResolution.getScaledWidth()) {
            this.setPosX((float)scaledResolution.getScaledWidth() - this.getWidth());
            this.getComponents().forEach(this::Method705);
        }
        if (this.Method921() < 0.0f) {
            this.Method442(0.0f);
            this.getComponents().forEach(this::Method707);
        }
        if (this.Method921() + this.getHeight() + Math.min(this.getTotalHeight(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()) > (float)scaledResolution.getScaledHeight()) {
            this.Method442((float)scaledResolution.getScaledHeight() - this.getHeight() - Math.min(this.getTotalHeight(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()));
            this.getComponents().forEach(this::Method697);
        }
        RenderUtil2.Method1338(this.getPosX(), this.Method921(), this.getWidth(), this.getHeight(), ((ColorValue) ClickGUIModule.header.getValue()).Method774());
        Class548.Method1016(this.Method920(), (int)(this.getPosX() + 3.0f), (int)(this.Method921() + this.getHeight() / 2.0f - (float)(Class548.Method1020() / 2) - 0.5f), ((ColorValue) ClickGUIModule.fony.getValue()).Method774());
        if (this.isExtended()) {
            if (CategoryFrame.isMouseWithinBounds(n, n2, this.getPosX(), this.Method921() + this.getHeight(), this.getWidth(), Math.min(this.getTotalHeight(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue())) && this.getTotalHeight() > ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()) {
                int n3 = Mouse.getDWheel();
                if (n3 < 0) {
                    if ((float)(this.getScrollY() - (Integer) ClickGUIModule.scrollSpeed.getValue()) < -(this.getTotalHeight() - Math.min(this.getTotalHeight(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()))) {
                        this.setScrollY((int)(-(this.getTotalHeight() - Math.min(this.getTotalHeight(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()))));
                    } else {
                        this.setScrollY(this.getScrollY() - (Integer) ClickGUIModule.scrollSpeed.getValue());
                    }
                } else if (n3 > 0) {
                    this.setScrollY(this.getScrollY() + (Integer) ClickGUIModule.scrollSpeed.getValue());
                }
            }
            if (this.getScrollY() > 0) {
                this.setScrollY(0);
            }
            if (this.getTotalHeight() > ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()) {
                if ((float)(this.getScrollY() - (Integer) ClickGUIModule.scrollSpeed.getValue()) < -(this.getTotalHeight() - ((Float) ClickGUIModule.maxHeight.getValue()).floatValue())) {
                    this.setScrollY((int)(-(this.getTotalHeight() - ((Float) ClickGUIModule.maxHeight.getValue()).floatValue())));
                }
            } else if (this.getScrollY() < 0) {
                this.setScrollY(0);
            }
            GL11.glPushMatrix();
            GL11.glEnable((int)3089);
            if (((Boolean) ClickGUIModule.animate.getValue()).booleanValue()) {
                RenderUtil2.Method1339(scaledResolution, this.getPosX(), this.Method921() + this.getHeight(), this.getWidth(), Math.min(((Float) ClickGUIModule.maxHeight.getValue()).floatValue(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue() * ((float)this.ticks / (float)((Integer) ClickGUIModule.animationSpeed.getValue()).intValue())));
                if (this.shouldDisable) {
                    --this.ticks;
                    if (this.ticks <= 0) {
                        super.setExtended(false);
                        this.shouldDisable = false;
                    }
                } else if (this.ticks < (Integer) ClickGUIModule.animationSpeed.getValue()) {
                    ++this.ticks;
                }
            } else {
                RenderUtil2.Method1339(scaledResolution, this.getPosX(), this.Method921() + this.getHeight(), this.getWidth(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue());
                if (this.shouldDisable) {
                    super.setExtended(false);
                    this.shouldDisable = false;
                }
            }
            RenderUtil2.Method1338(this.getPosX(), this.Method921() + this.getHeight(), this.getWidth(), Math.min(this.getTotalHeight(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()) + 2.0f, ((ColorValue) ClickGUIModule.color.getValue()).Method774());
            this.getComponents().forEach(arg_0 -> CategoryFrame.Method702(n, n2, f, arg_0));
            GL11.glDisable((int)3089);
            GL11.glPopMatrix();
        }
        this.initialise();
    }

    public int getScrollY() {
        return this.scrollY;
    }

    @Override
    public void setExtended(boolean bl) {
        if (bl) {
            this.ticks = 0;
            super.setExtended(bl);
        } else {
            this.shouldDisable = true;
            this.ticks = (Integer) ClickGUIModule.animationSpeed.getValue();
        }
    }

    @Override
    public void onKeyTyped(char c, int n) {
        block1: {
            super.onKeyTyped(c, n);
            if (!Class193.Method120()) {
                return;
            }
            if (!this.isExtended()) break block1;
            this.getComponents().forEach(arg_0 -> CategoryFrame.Method703(c, n, arg_0));
        }
    }

    public static void Method703(char c, int n, Component class183) {
        class183.Method102(c, n);
    }

    public void Method707(Component class183) {
        class183.onMove(this.getPosX(), this.Method921() + (float)this.getScrollY());
    }

    public static void Method702(int n, int n2, float f, Component class183) {
        class183.Method105(n, n2, f);
    }

    public float getTotalHeight() {
        float f = 0.0f;
        if (!this.isExtended()) {
            return f;
        }
        for (Component class183 : this.getComponents()) {
            f += class183.Method1481();
            if (!(class183 instanceof Class180) || !class183.Method1486()) continue;
            for (Component class1832 : ((Class180)class183).Method1494()) {
                f += class1832.Method1481();
            }
        }
        return f;
    }
}
