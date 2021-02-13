package me.darki.konas.event.events;

import net.minecraft.client.gui.GuiScreen;

public class DrawScreenEvent {
    public static DrawScreenEvent Field159 = new DrawScreenEvent();
    public GuiScreen Field160;

    public static DrawScreenEvent Method271(GuiScreen guiScreen) {
        DrawScreenEvent.Field159.Field160 = guiScreen;
        return Field159;
    }

    public GuiScreen Method272() {
        return this.Field160;
    }
}
