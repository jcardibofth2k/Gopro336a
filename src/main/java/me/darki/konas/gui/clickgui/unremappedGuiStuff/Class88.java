package me.darki.konas.gui.clickgui.unremappedGuiStuff;

import java.util.Collections;

import me.darki.konas.gui.clickgui.frame.Frame;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.module.render.Hud;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class Class88
extends Frame {
    public Element Field268;

    public Class88(Element element) {
        super(element.Method2316(), element.Method2320(), element.Method2324(), element.Method2329(), element.Method2322());
        this.Field268 = element;
    }

    public void Method439(float f, float f2) {
        if (!((Boolean) Hud.overlap.getValue()).booleanValue()) {
            for (Element element : KonasGlobals.INSTANCE.Field1136.Method2196()) {
                if (element.equals(this.Field268) || !(f > element.Method2320()) || !(f < element.Method2320() + element.Method2329()) || !(f2 > element.Method2324()) || !(f2 < element.Method2324() + element.Method2322())) continue;
                return;
            }
        }
        float f3 = f + this.getPreviousX();
        float f4 = f2 + this.getPreviousY();
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
        this.setPosX(f3);
        this.Method442(f4);
    }

    @Override
    public void setExtended(boolean bl) {
        super.setExtended(bl);
        this.Field268.Method2325(bl);
    }

    @Override
    public void onRender(int n, int n2, float f) {
        block5: {
            super.onRender(n, n2, f);
            if (!Class193.Method120() || !this.Field268.Method2338()) {
                return;
            }
            if (this.isDragging()) {
                this.Method439(n, n2);
            }
            this.setPosX(this.Field268.Method2320());
            this.Method442(this.Field268.Method2324());
            this.Method918(this.Field268.Method2329());
            this.Method919(this.Field268.Method2322());
            this.setExtended(this.Field268.Method2338());
            ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
            if (this.getPosX() < 0.0f) {
                this.setPosX(0.0f);
            }
            if (this.getPosX() + this.getWidth() > (float)scaledResolution.getScaledWidth()) {
                this.setPosX((float)scaledResolution.getScaledWidth() - this.getWidth());
            }
            if (this.Method921() < 0.0f) {
                this.Method442(0.0f);
            }
            if (!(this.Method921() + this.getHeight() > (float)scaledResolution.getScaledHeight())) break block5;
            this.Method442((float)scaledResolution.getScaledHeight() - this.getHeight());
        }
    }

    @Override
    public boolean onMouseClicked(int n, int n2, int n3) {
        boolean bl;
        block5: {
            super.onMouseClicked(n, n2, n3);
            if (!Class193.Method120()) {
                return false;
            }
            this.Field268.Method2332(n, n2, n3);
            boolean bl2 = Class88.isMouseWithinBounds(n, n2, this.getPosX(), this.Method921(), this.getWidth(), this.getHeight());
            bl = false;
            switch (n3) {
                case 0: {
                    if (!bl2 || !this.isExtended()) break;
                    this.setDragging(true);
                    this.setPreviousX(this.getPosX() - (float)n);
                    this.setPreviousY(this.Method921() - (float)n2);
                    bl = true;
                    break;
                }
                case 1: {
                    if (!bl2) break;
                    this.setExtended(!this.isExtended());
                    bl = true;
                }
            }
            if (!bl) break block5;
            Collections.swap(KonasGlobals.INSTANCE.Field1136.Method2196(), KonasGlobals.INSTANCE.Field1136.Method2196().indexOf(this.Field268), KonasGlobals.INSTANCE.Field1136.Method2196().size() - 1);
        }
        return bl;
    }

    @Override
    public void setDragging(boolean bl) {
        super.setDragging(bl);
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
    public void onMouseReleased(int n, int n2, int n3) {
        block1: {
            super.onMouseReleased(n, n2, n3);
            if (!Class193.Method120()) {
                return;
            }
            if (n3 != 0 || !this.isDragging()) break block1;
            this.setDragging(false);
        }
    }

    @Override
    public void setPosX(float f) {
        super.setPosX(f);
    }
}