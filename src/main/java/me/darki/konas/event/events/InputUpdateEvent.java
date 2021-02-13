package me.darki.konas.event.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovementInput;

public class InputUpdateEvent {
    public MovementInput Field37;
    public EntityPlayer Field38;

    public MovementInput Method81() {
        return this.Field37;
    }

    public EntityPlayer Method82() {
        return this.Field38;
    }

    public InputUpdateEvent(EntityPlayer entityPlayer, MovementInput movementInput) {
        this.Field37 = movementInput;
        this.Field38 = entityPlayer;
    }
}
