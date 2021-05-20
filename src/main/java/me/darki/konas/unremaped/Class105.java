package me.darki.konas.unremaped;

import java.util.ArrayList;

import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class180;
import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class193;
import me.darki.konas.gui.clickgui.unremappedGuiStuff.guiFrameDuplicatePossiblyForAimwareGui;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.util.RenderUtil2;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.module.client.ClickGUIModule;
import me.darki.konas.module.client.KonasGlobals;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class Class105
extends guiFrameDuplicatePossiblyForAimwareGui {
    public ArrayList<Class183> Field2575 = new ArrayList();
    public int Field2576;
    public int Field2577 = 0;
    public boolean Field2578 = false;

    @Override
    public void Method709(float f, float f2) {
        if (!Class193.Method120()) {
            return;
        }
        this.Field2575.forEach(arg_0 -> Class105.Method698(f, f2, arg_0));
    }

    @Override
    public void Method444(int n, int n2, int n3) {
        block2: {
            super.Method444(n, n2, n3);
            if (!Class193.Method120()) {
                return;
            }
            if (n3 == 0 && this.Method917()) {
                this.Method441(false);
            }
            if (!this.Method914()) break block2;
            this.Method2208().forEach(arg_0 -> Class105.Method701(n, n2, n3, arg_0));
        }
    }

    public void Method700(int n) {
        this.Field2576 = n;
    }

    @Override
    public boolean Method100(int n, int n2, int n3) {
        super.Method100(n, n2, n3);
        if (!Class193.Method120()) {
            return false;
        }
        boolean bl = Class105.Method916(n, n2, this.Method922(), this.Method921(), this.Method910(), this.Method911());
        switch (n3) {
            case 0: {
                if (!bl) break;
                this.Method441(true);
                this.Method923(this.Method922() - (float)n);
                this.Method912(this.Method921() - (float)n2);
                return true;
            }
            case 1: {
                if (!bl) break;
                this.Method440(!this.Method914());
                return true;
            }
        }
        if (this.Method914() && Class105.Method916(n, n2, this.Method922(), this.Method921() + this.Method911(), this.Method910(), this.Method696() > ((Float) ClickGUIModule.maxHeight.getValue()).floatValue() ? (double)((Float) ClickGUIModule.maxHeight.getValue()).floatValue() : (double)this.Method696())) {
            for (Class183 class183 : this.Method2208()) {
                if (!class183.Method106(n, n2, n3)) continue;
                return true;
            }
        }
        return false;
    }

    public void Method2207() {
        float f = this.Method911();
        for (Class183 class183 : this.Method2208()) {
            class183.Method1472(f);
            class183.Method665(this.Method922(), this.Method921() + (float)this.Method2209());
            if (class183 instanceof Class180 && class183.Method1486()) {
                for (Class183 class1832 : ((Class180)class183).Method1494()) {
                    f += class1832.Method1481();
                }
            }
            f += class183.Method1481();
        }
    }

    public void Method693(Class183 class183) {
        class183.Method665(this.Method922(), this.Method921() + (float)this.Method2209());
    }

    @Override
    public void Method704(int n, int n2, int n3, long l) {
        block1: {
            super.Method704(n, n2, n3, l);
            if (!Class193.Method120()) {
                return;
            }
            if (!this.Method914()) break block1;
            this.Method2208().forEach(arg_0 -> Class105.Method695(n, n2, n3, l, arg_0));
        }
    }

    public ArrayList<Class183> Method2208() {
        return this.Field2575;
    }

    public static void Method698(float f, float f2, Class183 class183) {
        class183.Method665(f, f2);
    }

    public void Method705(Class183 class183) {
        class183.Method665(this.Method922(), this.Method921() + (float)this.Method2209());
    }

    @Override
    public void Method712() {
        float f = this.Method911();
        for (Element element : KonasGlobals.INSTANCE.Field1136.Method2196()) {
            this.Method2208().add(new Class180(element, this.Method922(), this.Method921(), 2.0f, f, this.Method910() - 4.0f, 12.0f));
            f += 12.0f;
        }
        this.Method2208().forEach(Class183::Method667);
    }

    public void Method697(Class183 class183) {
        class183.Method665(this.Method922(), this.Method921() + (float)this.Method2209());
    }

    public void Method699(Class183 class183) {
        class183.Method665(this.Method922(), this.Method921() + (float)this.Method2209());
    }

    public Class105(float f, float f2, float f3, float f4) {
        super("Containers", f, f2, f3, f4);
    }

    public static void Method695(int n, int n2, int n3, long l, Class183 class183) {
        class183.Method649(n, n2, n3, l);
    }

    public static void Method701(int n, int n2, int n3, Class183 class183) {
        class183.Method647(n, n2, n3);
    }

    @Override
    public void Method101(int n, int n2, float f) {
        super.Method101(n, n2, f);
        if (!Class193.Method120()) {
            return;
        }
        if (this.Method917()) {
            this.Method445((float)n + this.Method913());
            this.Method442((float)n2 + this.Method915());
            this.Method2208().forEach(this::Method693);
        }
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        if (this.Method922() < 0.0f) {
            this.Method445(0.0f);
            this.Method2208().forEach(this::Method699);
        }
        if (this.Method922() + this.Method910() > (float)scaledResolution.getScaledWidth()) {
            this.Method445((float)scaledResolution.getScaledWidth() - this.Method910());
            this.Method2208().forEach(this::Method705);
        }
        if (this.Method921() < 0.0f) {
            this.Method442(0.0f);
            this.Method2208().forEach(this::Method707);
        }
        if (this.Method921() + this.Method911() + Math.min(this.Method696(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()) > (float)scaledResolution.getScaledHeight()) {
            this.Method442((float)scaledResolution.getScaledHeight() - this.Method911() - Math.min(this.Method696(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()));
            this.Method2208().forEach(this::Method697);
        }
        RenderUtil2.Method1338(this.Method922(), this.Method921(), this.Method910(), this.Method911(), ((ColorValue) ClickGUIModule.header.getValue()).Method774());
        Class548.Method1016(this.Method920(), (int)(this.Method922() + 3.0f), (int)(this.Method921() + this.Method911() / 2.0f - (float)(Class548.Method1020() / 2) - 0.5f), ((ColorValue) ClickGUIModule.fony.getValue()).Method774());
        if (this.Method914()) {
            if (Class105.Method916(n, n2, this.Method922(), this.Method921() + this.Method911(), this.Method910(), Math.min(this.Method696(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue())) && this.Method696() > ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()) {
                int n3 = Mouse.getDWheel();
                if (n3 < 0) {
                    if ((float)(this.Method2209() - (Integer) ClickGUIModule.scrollSpeed.getValue()) < -(this.Method696() - Math.min(this.Method696(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()))) {
                        this.Method700((int)(-(this.Method696() - Math.min(this.Method696(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()))));
                    } else {
                        this.Method700(this.Method2209() - (Integer) ClickGUIModule.scrollSpeed.getValue());
                    }
                } else if (n3 > 0) {
                    this.Method700(this.Method2209() + (Integer) ClickGUIModule.scrollSpeed.getValue());
                }
            }
            if (this.Method2209() > 0) {
                this.Method700(0);
            }
            if (this.Method696() > ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()) {
                if ((float)(this.Method2209() - (Integer) ClickGUIModule.scrollSpeed.getValue()) < -(this.Method696() - ((Float) ClickGUIModule.maxHeight.getValue()).floatValue())) {
                    this.Method700((int)(-(this.Method696() - ((Float) ClickGUIModule.maxHeight.getValue()).floatValue())));
                }
            } else if (this.Method2209() < 0) {
                this.Method700(0);
            }
            GL11.glPushMatrix();
            GL11.glEnable((int)3089);
            if (((Boolean) ClickGUIModule.animate.getValue()).booleanValue()) {
                RenderUtil2.Method1339(scaledResolution, this.Method922(), this.Method921() + this.Method911(), this.Method910(), Math.min(((Float) ClickGUIModule.maxHeight.getValue()).floatValue(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue() * ((float)this.Field2577 / (float)((Integer) ClickGUIModule.animationSpeed.getValue()).intValue())));
                if (this.Field2578) {
                    --this.Field2577;
                    if (this.Field2577 <= 0) {
                        super.Method440(false);
                        this.Field2578 = false;
                    }
                } else if (this.Field2577 < (Integer) ClickGUIModule.animationSpeed.getValue()) {
                    ++this.Field2577;
                }
            } else {
                RenderUtil2.Method1339(scaledResolution, this.Method922(), this.Method921() + this.Method911(), this.Method910(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue());
                if (this.Field2578) {
                    super.Method440(false);
                    this.Field2578 = false;
                }
            }
            RenderUtil2.Method1338(this.Method922(), this.Method921() + this.Method911(), this.Method910(), Math.min(this.Method696(), ((Float) ClickGUIModule.maxHeight.getValue()).floatValue()) + 2.0f, ((ColorValue) ClickGUIModule.b.getValue()).Method774());
            this.Method2208().forEach(arg_0 -> Class105.Method702(n, n2, f, arg_0));
            GL11.glDisable((int)3089);
            GL11.glPopMatrix();
        }
        this.Method2207();
    }

    public int Method2209() {
        return this.Field2576;
    }

    @Override
    public void Method440(boolean bl) {
        if (bl) {
            this.Field2577 = 0;
            super.Method440(bl);
        } else {
            this.Field2578 = true;
            this.Field2577 = (Integer) ClickGUIModule.animationSpeed.getValue();
        }
    }

    @Override
    public void Method713(char c, int n) {
        block1: {
            super.Method713(c, n);
            if (!Class193.Method120()) {
                return;
            }
            if (!this.Method914()) break block1;
            this.Method2208().forEach(arg_0 -> Class105.Method703(c, n, arg_0));
        }
    }

    public static void Method703(char c, int n, Class183 class183) {
        class183.Method102(c, n);
    }

    public void Method707(Class183 class183) {
        class183.Method665(this.Method922(), this.Method921() + (float)this.Method2209());
    }

    public static void Method702(int n, int n2, float f, Class183 class183) {
        class183.Method105(n, n2, f);
    }

    public float Method696() {
        float f = 0.0f;
        if (!this.Method914()) {
            return f;
        }
        for (Class183 class183 : this.Method2208()) {
            f += class183.Method1481();
            if (!(class183 instanceof Class180) || !class183.Method1486()) continue;
            for (Class183 class1832 : ((Class180)class183).Method1494()) {
                f += class1832.Method1481();
            }
        }
        return f;
    }
}
