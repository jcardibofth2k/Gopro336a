package me.darki.konas;

import net.minecraftforge.client.event.RenderBlockOverlayEvent;

public class Class148
extends CancelableEvent {
    public RenderBlockOverlayEvent.OverlayType Field1998;

    public RenderBlockOverlayEvent.OverlayType Method1825() {
        return this.Field1998;
    }

    public Class148(RenderBlockOverlayEvent.OverlayType overlayType) {
        this.Field1998 = overlayType;
    }
}
