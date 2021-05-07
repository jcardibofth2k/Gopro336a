package me.darki.konas.unremaped;

import java.util.ArrayList;
import java.util.Comparator;

import me.darki.konas.gui.clickgui.component.Component;
import me.darki.konas.gui.clickgui.frame.Frame;
import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class193;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.util.RenderUtil2;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;
import me.darki.konas.module.client.ClickGUIModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class Class200
extends Frame {
    public Category Field639;
    public ArrayList<Component> Field640 = new ArrayList();
    public int Field641;
    public int Field642 = 0;
    public boolean Field643 = false;

    public void Method693(Component class183) {
        class183.onMove(this.getPosX(), this.Method921() + (float)this.Method708());
    }

    public Category Method694() {
        return this.Field639;
    }

    public static void Method695(int n, int n2, int n3, long l, Component class183) {
        class183.Method649(n, n2, n3, l);
    }

    public float getTotalHeight() {
        float f = 0.0f;
        if (!this.isExtended()) {
            return f;
        }
        for (Component class183 : this.Method711()) {
            f += class183.Method1481();
            if (!(class183 instanceof Class201) || !class183.Method1486()) continue;
            for (Component class1832 : ((Class201)class183).Method672()) {
                f += class1832.Method1481();
            }
        }
        return f;
    }

    public void Method697(Component class183) {
        class183.onMove(this.getPosX(), this.Method921() + (float)this.Method708());
    }

    public static void Method698(float f, float f2, Component class183) {
        class183.onMove(f, f2);
    }

    public void Method699(Component class183) {
        class183.onMove(this.getPosX(), this.Method921() + (float)this.Method708());
    }

    public void setScrollY(int n) {
        this.Field641 = n;
    }

    public static void Method701(int n, int n2, int n3, Component class183) {
        class183.Method647(n, n2, n3);
    }

    @Override
    public void onMouseReleased(int n, int n2, int n3) {
        block2: {
            super.onMouseReleased(n, n2, n3);
            if (Class193.Method120()) {
                return;
            }
            if (n3 == 0 && this.isDragging()) {
                this.setDragging(false);
            }
            if (!this.isExtended()) break block2;
            this.Method711().forEach(arg_0 -> Class200.Method701(n, n2, n3, arg_0));
        }
    }

    public static void Method702(int n, int n2, float f, Component class183) {
        class183.Method105(n, n2, f);
    }

    @Override
    public void setExtended(boolean bl) {
        if (bl) {
            this.Field642 = 0;
            super.setExtended(bl);
        } else {
            this.Field643 = true;
            this.Field642 = (Integer) ClickGUIModule.animationSpeed.getValue();
        }
    }

    public static void Method703(char c, int n, Component class183) {
        class183.Method102(c, n);
    }

    @Override
    public void Method704(int n, int n2, int n3, long l) {
        block1: {
            super.Method704(n, n2, n3, l);
            if (Class193.Method120()) {
                return;
            }
            if (!this.isExtended()) break block1;
            this.Method711().forEach(arg_0 -> Class200.Method695(n, n2, n3, l, arg_0));
        }
    }

    public void Method705(Component class183) {
        class183.onMove(this.getPosX(), this.Method921() + (float)this.Method708());
    }

    @Override
    public void onRender(int n, int n2, float f) {
        if (Class193.Method120()) {
            return;
        }
        if (this.isDragging()) {
            this.setPosX((float)n + this.getPreviousX());
            this.Method442((float)n2 + this.getPreviousY());
            this.Method711().forEach(this::Method693);
        }
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        if (this.getPosX() < 0.0f) {
            this.setPosX(0.0f);
            this.Method711().forEach(this::Method705);
        }
        if (this.getPosX() + this.getWidth() > (float)scaledResolution.getScaledWidth()) {
            this.setPosX((float)scaledResolution.getScaledWidth() - this.getWidth());
            this.Method711().forEach(this::Method697);
        }
        if (this.Method921() < 0.0f) {
            this.Method442(0.0f);
            this.Method711().forEach(this::Method707);
        }
        if (this.Method921() + this.getHeight() + Math.min(this.getTotalHeight(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()) > (float)scaledResolution.getScaledHeight()) {
            this.Method442((float)scaledResolution.getScaledHeight() - this.getHeight() - Math.min(this.getTotalHeight(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()));
            this.Method711().forEach(this::Method699);
        }
        RenderUtil2.Method1338(this.getPosX(), this.Method921(), this.getWidth(), this.getHeight(), ((ColorValue) ClickGUIModule.header.getValue()).Method774());
        Class548.Method1016(this.Method920(), (int)(this.getPosX() + 3.0f), (int)(this.Method921() + this.getHeight() / 2.0f - (float)(Class548.Method1020() / 2) - 0.5f), ((ColorValue) ClickGUIModule.fony.getValue()).Method774());
        if (this.isExtended()) {
            if (Class200.isMouseWithinBounds(n, n2, this.getPosX(), this.Method921() + this.getHeight(), this.getWidth(), Math.min(this.getTotalHeight(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue())) && this.getTotalHeight() > ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()) {
                int n3 = Mouse.getDWheel();
                if (n3 < 0) {
                    if ((float)(this.Method708() - (Integer) ClickGUIModule.scrollSpeed.getValue()) < -(this.getTotalHeight() - Math.min(this.getTotalHeight(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()))) {
                        this.setScrollY((int)(-(this.getTotalHeight() - Math.min(this.getTotalHeight(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()))));
                    } else {
                        this.setScrollY(this.Method708() - (Integer) ClickGUIModule.scrollSpeed.getValue());
                    }
                } else if (n3 > 0) {
                    this.setScrollY(this.Method708() + (Integer) ClickGUIModule.scrollSpeed.getValue());
                }
            }
            if (this.Method708() > 0) {
                this.setScrollY(0);
            }
            if (this.getTotalHeight() > ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()) {
                if ((float)(this.Method708() - (Integer) ClickGUIModule.scrollSpeed.getValue()) < -(this.getTotalHeight() - ((Float) ClickGUIModule.maxHeight.getValue()).floatValue())) {
                    this.setScrollY((int)(-(this.getTotalHeight() - ((Float) ClickGUIModule.maxHeight.getValue()).floatValue())));
                }
            } else if (this.Method708() < 0) {
                this.setScrollY(0);
            }
            GL11.glPushMatrix();
            GL11.glEnable((int)3089);
            if (((Boolean) ClickGUIModule.animate.getValue()).booleanValue()) {
                RenderUtil2.Method1339(scaledResolution, this.getPosX(), this.Method921() + this.getHeight(), this.getWidth(), Math.min(((Float) ClickGUIModule.maxHeight.getValue()).floatValue(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue() * ((float)this.Field642 / (float)((Integer) ClickGUIModule.animationSpeed.getValue()).intValue())));
                if (this.Field643) {
                    --this.Field642;
                    if (this.Field642 <= 0) {
                        super.setExtended(false);
                        this.Field643 = false;
                    }
                } else if (this.Field642 < (Integer) ClickGUIModule.animationSpeed.getValue()) {
                    ++this.Field642;
                }
            } else {
                RenderUtil2.Method1339(scaledResolution, this.getPosX(), this.Method921() + this.getHeight(), this.getWidth(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue());
                if (this.Field643) {
                    super.setExtended(false);
                    this.Field643 = false;
                }
            }
            RenderUtil2.Method1338(this.getPosX(), this.Method921() + this.getHeight(), this.getWidth(), Math.min(this.getTotalHeight(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()), ((ColorValue) ClickGUIModule.b.getValue()).Method774());
            this.Method711().forEach(arg_0 -> Class200.Method702(n, n2, f, arg_0));
            GL11.glDisable((int)3089);
            if (((Boolean) ClickGUIModule.outline.getValue()).booleanValue() && !((Boolean) ClickGUIModule.animate.getValue()).booleanValue()) {
                float f2 = ((Integer) ClickGUIModule.thickness.getValue()).intValue();
                RenderUtil2.Method1338(this.getPosX() - f2, this.Method921() - f2, f2, this.getHeight() - f2 + Math.min(this.getTotalHeight(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()) + f2 * 2.0f, ((ColorValue) ClickGUIModule.color.getValue()).Method774());
                RenderUtil2.Method1338(this.getPosX(), this.Method921() - f2, this.getWidth(), f2, ((ColorValue) ClickGUIModule.color.getValue()).Method774());
                RenderUtil2.Method1338(this.getPosX() + this.getWidth(), this.Method921() - f2, f2, this.getHeight() - f2 + Math.min(this.getTotalHeight(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()) + f2 * 2.0f, ((ColorValue) ClickGUIModule.color.getValue()).Method774());
                RenderUtil2.Method1338(this.getPosX(), this.Method921() + this.getHeight() + Math.min(this.getTotalHeight(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()) - f2, this.getWidth(), f2, ((ColorValue) ClickGUIModule.color.getValue()).Method774());
            }
            GL11.glPopMatrix();
        } else if (((Boolean) ClickGUIModule.outline.getValue()).booleanValue() && !((Boolean) ClickGUIModule.animate.getValue()).booleanValue()) {
            float f3 = ((Integer) ClickGUIModule.thickness.getValue()).intValue();
            RenderUtil2.Method1338(this.getPosX() - f3, this.Method921() - f3, f3, this.getHeight() + f3 * 2.0f, ((ColorValue) ClickGUIModule.color.getValue()).Method774());
            RenderUtil2.Method1338(this.getPosX(), this.Method921() - f3, this.getWidth(), f3, ((ColorValue) ClickGUIModule.color.getValue()).Method774());
            RenderUtil2.Method1338(this.getPosX() + this.getWidth(), this.Method921() - f3, f3, this.getHeight() + f3 * 2.0f, ((ColorValue) ClickGUIModule.color.getValue()).Method774());
            RenderUtil2.Method1338(this.getPosX(), this.Method921() + this.getHeight(), this.getWidth(), f3, ((ColorValue) ClickGUIModule.color.getValue()).Method774());
        }
        this.Method710(n, n2);
    }

    public ArrayList<Component> Method706() {
        return this.Field640;
    }

    public void Method707(Component class183) {
        class183.onMove(this.getPosX(), this.Method921() + (float)this.Method708());
    }

    public int Method708() {
        return this.Field641;
    }

    @Override
    public void onMove(float f, float f2) {
        this.Field640.forEach(arg_0 -> Class200.Method698(f, f2, arg_0));
    }

    @Override
    public boolean onMouseClicked(int n, int n2, int n3) {
        super.onMouseClicked(n, n2, n3);
        if (Class193.Method120()) {
            return false;
        }
        boolean bl = Class200.isMouseWithinBounds(n, n2, this.getPosX(), this.Method921(), this.getWidth(), this.getHeight());
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
                Minecraft.getMinecraft().getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
                this.setExtended(!this.isExtended());
                return true;
            }
        }
        if (this.isExtended() && Class200.isMouseWithinBounds(n, n2, this.getPosX(), this.Method921() + this.getHeight(), this.getWidth(), this.getTotalHeight() > ((Float) ClickGUIModule.maxHeight.getValue()).floatValue() ? (double)((Float) ClickGUIModule.maxHeight.getValue()).floatValue() : (double)this.getTotalHeight())) {
            for (Component class183 : this.Method711()) {
                if (!class183.Method106(n, n2, n3)) continue;
                return true;
            }
        }
        return false;
    }

    public void Method710(int n, int n2) {
        float f = this.getHeight();
        for (Component class183 : this.Method711()) {
            class183.setYOffset(f);
            class183.onMove(this.getPosX(), this.Method921() + (float)this.Method708());
            if (class183 instanceof Class201) {
                if (f <= ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()) {
                    ((Class201)class183).Method674(n, n2);
                }
                if (class183.Method1486()) {
                    for (Component class1832 : ((Class201)class183).Method672()) {
                        f += class1832.Method1481();
                    }
                }
            }
            f += class183.Method1481();
        }
    }

    public Class200(Category category, float f, float f2, float f3, float f4) {
        super(category.name(), f, f2, f3, f4);
        this.Field639 = category;
    }

    public ArrayList<Component> Method711() {
        ArrayList<Component> arrayList = new ArrayList<Component>();
        for (Component class183 : this.Method706()) {
            if (class183 instanceof Class201) {
                if (!((Class201)class183).Method146().isValidViaFabricVers()) continue;
                arrayList.add(class183);
                continue;
            }
            arrayList.add(class183);
        }
        return arrayList;
    }

    @Override
    public void Method712() {
        float f = this.getHeight();
        ArrayList<Module> arrayList = ModuleManager.getModulesByCategory(this.Method694());
        arrayList.sort(Comparator.comparing(Module::getName));
        for (Module module : arrayList) {
            this.Method706().add(new Class201(module, this.getPosX(), this.Method921(), 2.0f, f, this.getWidth() - 4.0f, 14.0f));
            f += 14.0f;
        }
        this.Method706().forEach(Component::Method667);
    }

    @Override
    public void onKeyTyped(char c, int n) {
        block1: {
            super.onKeyTyped(c, n);
            if (Class193.Method120()) {
                return;
            }
            if (!this.isExtended()) break block1;
            this.Method711().forEach(arg_0 -> Class200.Method703(c, n, arg_0));
        }
    }
}