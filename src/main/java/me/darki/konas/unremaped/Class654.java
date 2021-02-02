package me.darki.konas;

import net.minecraft.client.gui.GuiScreen;

public class Class654
extends CancelableEvent {
    public GuiScreen Field1152;

    public void Method1160(GuiScreen guiScreen) {
        this.Field1152 = guiScreen;
    }

    public Class654(GuiScreen guiScreen) {
        this.Field1152 = guiScreen;
    }

    public GuiScreen Method1161() {
        return this.Field1152;
    }
}
