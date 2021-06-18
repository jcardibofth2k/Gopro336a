package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.event.CancelableEvent;
import net.minecraft.client.particle.Particle;

public class ParticalEffectAddEvent
extends CancelableEvent {
    public Particle Field153;

    public Particle Method261() {
        return this.Field153;
    }

    public ParticalEffectAddEvent(Particle particle) {
        this.Field153 = particle;
    }
}