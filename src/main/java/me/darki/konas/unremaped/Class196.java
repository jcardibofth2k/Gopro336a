package me.darki.konas.unremaped;

import me.darki.konas.setting.ColorValue;
import me.darki.konas.util.RenderUtil2;
import me.darki.konas.module.client.ClickGUIModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class Class196
extends Class90 {
    public Class196() {
        super("HUD Editor", 20.0f, 100.0f, 100.0f, 16.0f);
    }

    @Override
    public boolean Method100(int n, int n2, int n3) {
        super.Method100(n, n2, n3);
        if (Class196.Method916(n, n2, this.Method922(), this.Method921(), this.Method910(), this.Method911())) {
            if (n3 == 0) {
                Class193.Method115(!Class193.Method120());
                return true;
            }
        }
        return false;
    }

    @Override
    public void Method101(int n, int n2, float f) {
        super.Method101(n, n2, f);
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        this.Method445((float)scaledResolution.getScaledWidth() - this.Method910() - 5.0f);
        this.Method442((float)scaledResolution.getScaledHeight() - this.Method911() - 5.0f);
        int n3 = ((ColorValue) ClickGUIModule.color.getValue()).Method774();
        if (((Boolean) ClickGUIModule.hover.getValue()).booleanValue() && Class196.Method916(n, n2, this.Method922(), this.Method921(), this.Method910(), this.Method911())) {
            n3 = ((ColorValue) ClickGUIModule.color.getValue()).Method775().brighter().hashCode();
        }
        RenderUtil2.Method1338(this.Method922() - 2.0f, this.Method921() - 2.0f, this.Method910() + 4.0f, this.Method911() + 4.0f, ((ColorValue) ClickGUIModule.secondary.getValue()).Method774());
        RenderUtil2.Method1338(this.Method922(), this.Method921(), this.Method910(), this.Method911(), n3);
        String string = Class193.Method120() ? "Modules" : "HUD Editor";
        Class548.Method1019(string, (int)(this.Method922() + this.Method910() / 2.0f - Class548.Method1022(string) / 2.0f), (int)(this.Method921() + this.Method911() / 2.0f - (float)Class548.Method1020() / 2.0f), 0xFFFFFF);
    }
}