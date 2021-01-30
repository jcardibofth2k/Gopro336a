package me.darki.konas;

import java.util.Collections;

import me.darki.konas.gui.hud.Element;
import me.darki.konas.module.client.NewGui;
import me.darki.konas.module.render.Hud;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class Class88
extends Class90 {
    public Element Field268;

    public Class88(Element element) {
        super(element.Method2316(), element.Method2320(), element.Method2324(), element.Method2329(), element.Method2322());
        this.Field268 = element;
    }

    public void Method439(float f, float f2) {
        if (!((Boolean) Hud.Field1391.getValue()).booleanValue()) {
            for (Element element : NewGui.INSTANCE.Field1136.Method2196()) {
                if (element.equals(this.Field268) || !(f > element.Method2320()) || !(f < element.Method2320() + element.Method2329()) || !(f2 > element.Method2324()) || !(f2 < element.Method2324() + element.Method2322())) continue;
                return;
            }
        }
        float f3 = f + this.Method913();
        float f4 = f2 + this.Method915();
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f3 + this.Field268.Method2329() > (float)scaledResolution.getScaledWidth()) {
            f3 = (float)scaledResolution.getScaledWidth() - this.Field268.Method2329();
        }
        if (f4 < 0.0f) {
            f4 = 0.0f;
        }
        if (f4 + this.Field268.Method2322() > (float)scaledResolution.getScaledHeight()) {
            f4 = (float)scaledResolution.getScaledHeight() - this.Field268.Method2322();
        }
        this.Field268.Method2337(f3);
        this.Field268.Method2328(f4);
        this.Method445(f3);
        this.Method442(f4);
    }

    @Override
    public void Method440(boolean bl) {
        super.Method440(bl);
        this.Field268.Method2325(bl);
    }

    @Override
    public void Method101(int n, int n2, float f) {
        block5: {
            super.Method101(n, n2, f);
            if (!Class193.Method120() || !this.Field268.Method2338()) {
                return;
            }
            if (this.Method917()) {
                this.Method439(n, n2);
            }
            this.Method445(this.Field268.Method2320());
            this.Method442(this.Field268.Method2324());
            this.Method918(this.Field268.Method2329());
            this.Method919(this.Field268.Method2322());
            this.Method440(this.Field268.Method2338());
            ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
            if (this.Method922() < 0.0f) {
                this.Method445(0.0f);
            }
            if (this.Method922() + this.Method910() > (float)scaledResolution.getScaledWidth()) {
                this.Method445((float)scaledResolution.getScaledWidth() - this.Method910());
            }
            if (this.Method921() < 0.0f) {
                this.Method442(0.0f);
            }
            if (!(this.Method921() + this.Method911() > (float)scaledResolution.getScaledHeight())) break block5;
            this.Method442((float)scaledResolution.getScaledHeight() - this.Method911());
        }
    }

    @Override
    public boolean Method100(int n, int n2, int n3) {
        boolean bl;
        block5: {
            super.Method100(n, n2, n3);
            if (!Class193.Method120()) {
                return false;
            }
            this.Field268.Method2332(n, n2, n3);
            boolean bl2 = Class88.Method916(n, n2, this.Method922(), this.Method921(), this.Method910(), this.Method911());
            bl = false;
            switch (n3) {
                case 0: {
                    if (!bl2 || !this.Method914()) break;
                    this.Method441(true);
                    this.Method923(this.Method922() - (float)n);
                    this.Method912(this.Method921() - (float)n2);
                    bl = true;
                    break;
                }
                case 1: {
                    if (!bl2) break;
                    this.Method440(!this.Method914());
                    bl = true;
                }
            }
            if (!bl) break block5;
            Collections.swap(NewGui.INSTANCE.Field1136.Method2196(), NewGui.INSTANCE.Field1136.Method2196().indexOf(this.Field268), NewGui.INSTANCE.Field1136.Method2196().size() - 1);
        }
        return bl;
    }

    @Override
    public void Method441(boolean bl) {
        super.Method441(bl);
        this.Field268.Method2327(bl);
    }

    @Override
    public void Method442(float f) {
        super.Method442(f);
    }

    public Element Method443() {
        return this.Field268;
    }

    @Override
    public void Method444(int n, int n2, int n3) {
        block1: {
            super.Method444(n, n2, n3);
            if (!Class193.Method120()) {
                return;
            }
            if (n3 != 0 || !this.Method917()) break block1;
            this.Method441(false);
        }
    }

    @Override
    public void Method445(float f) {
        super.Method445(f);
    }
}
