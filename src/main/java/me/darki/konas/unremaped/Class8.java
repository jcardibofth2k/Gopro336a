package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.event.CancelableEvent;
import net.minecraft.util.ResourceLocation;

public class Class8
extends CancelableEvent {
    public ResourceLocation Field39;
    public String Field40;

    public ResourceLocation Method83() {
        return this.Field39;
    }

    public void Method84(ResourceLocation resourceLocation) {
        this.Field39 = resourceLocation;
    }

    public Class8(String string) {
        this.Field40 = string;
        this.Field39 = null;
    }

    public String Method85() {
        return this.Field40;
    }

    public void Method86(String string) {
        this.Field40 = string;
    }
}