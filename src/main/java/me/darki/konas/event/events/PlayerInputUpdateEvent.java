package me.darki.konas.event.events;

import net.minecraft.util.MovementInput;

public class PlayerInputUpdateEvent {
    public MovementInput Field138;

    public MovementInput Method218() {
        return this.Field138;
    }

    public PlayerInputUpdateEvent(MovementInput movementInput) {
        this.Field138 = movementInput;
    }
}
