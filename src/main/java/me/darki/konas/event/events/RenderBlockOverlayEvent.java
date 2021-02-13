package me.darki.konas.event.events;

import me.darki.konas.event.CancelableEvent;

public class RenderBlockOverlayEvent
extends CancelableEvent {
    public net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType Field1998;

    public net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType Method1825() {
        return this.Field1998;
    }

    public RenderBlockOverlayEvent(net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType overlayType) {
        this.Field1998 = overlayType;
    }
}
