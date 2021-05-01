package me.darki.konas.unremaped;

import me.darki.konas.gui.clickgui.frame.Frame;
import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class193;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.util.RenderUtil2;
import me.darki.konas.module.client.ClickGUIModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class Class196
extends Frame {
    public Class196() {
        super("HUD Editor", 20.0f, 100.0f, 100.0f, 16.0f);
    }

    @Override
    public boolean onMouseClicked(int n, int n2, int n3) {
        super.onMouseClicked(n, n2, n3);
        if (Class196.isMouseWithinBounds(n, n2, this.getPosX(), this.Method921(), this.getWidth(), this.getHeight())) {
            if (n3 == 0) {
                Class193.Method115(!Class193.Method120());
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRender(int n, int n2, float f) {
        super.onRender(n, n2, f);
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        this.setPosX((float)scaledResolution.getScaledWidth() - this.getWidth() - 5.0f);
        this.Method442((float)scaledResolution.getScaledHeight() - this.getHeight() - 5.0f);
        int n3 = ((ColorValue) ClickGUIModule.color.getValue()).Method774();
        if (((Boolean) ClickGUIModule.hover.getValue()).booleanValue() && Class196.isMouseWithinBounds(n, n2, this.getPosX(), this.Method921(), this.getWidth(), this.getHeight())) {
            n3 = ((ColorValue) ClickGUIModule.color.getValue()).Method775().brighter().hashCode();
        }
        RenderUtil2.Method1338(this.getPosX() - 2.0f, this.Method921() - 2.0f, this.getWidth() + 4.0f, this.getHeight() + 4.0f, ((ColorValue) ClickGUIModule.secondary.getValue()).Method774());
        RenderUtil2.Method1338(this.getPosX(), this.Method921(), this.getWidth(), this.getHeight(), n3);
        String string = Class193.Method120() ? "Modules" : "HUD Editor";
        Class548.Method1019(string, (int)(this.getPosX() + this.getWidth() / 2.0f - Class548.Method1022(string) / 2.0f), (int)(this.Method921() + this.getHeight() / 2.0f - (float)Class548.Method1020() / 2.0f), 0xFFFFFF);
    }
}