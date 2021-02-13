package me.darki.konas.event.events;

import me.darki.konas.event.CancelableEvent;
import net.minecraft.client.gui.GuiScreen;

public class OpenGuiEvent
extends CancelableEvent {
    public GuiScreen Field1152;

    public void Method1160(GuiScreen guiScreen) {
        this.Field1152 = guiScreen;
    }

    public OpenGuiEvent(GuiScreen guiScreen) {
        this.Field1152 = guiScreen;
    }

    public GuiScreen Method1161() {
        return this.Field1152;
    }
}
