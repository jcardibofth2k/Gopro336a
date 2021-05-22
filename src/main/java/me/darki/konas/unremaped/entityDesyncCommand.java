package me.darki.konas.unremaped;

import me.darki.konas.util.SyntaxChunk;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;
import net.minecraft.entity.Entity;

public class entityDesyncCommand
extends Command {
    public Entity Field2568;

    @Override
    public void Method174(String[] stringArray) {
        if (stringArray.length == 2) {
            if (stringArray[1].equalsIgnoreCase("Dismount") || stringArray[1].equalsIgnoreCase("D") || stringArray[1].equalsIgnoreCase("Dis")) {
                if (entityDesyncCommand.Field123.player.getRidingEntity() != null) {
                    this.Field2568 = entityDesyncCommand.Field123.player.getRidingEntity();
                    entityDesyncCommand.Field123.player.dismountRidingEntity();
                    entityDesyncCommand.Field123.world.removeEntity(this.Field2568);
                    Logger.Method1118("Dismounted entity.");
                } else {
                    Logger.Method1118("You are not riding an entity.");
                }
            } else if (stringArray[1].equalsIgnoreCase("Remount") || stringArray[1].equalsIgnoreCase("R") || stringArray[1].equalsIgnoreCase("Re")) {
                if (this.Field2568 != null) {
                    this.Field2568.isDead = false;
                    entityDesyncCommand.Field123.world.addEntityToWorld(this.Field2568.getEntityId(), this.Field2568);
                    entityDesyncCommand.Field123.player.startRiding(this.Field2568, true);
                    this.Field2568 = null;
                } else {
                    Logger.Method1118("No entity to remount.");
                }
            } else {
                Logger.Method1119("Please enter either \"Dismount\" or \"Remount\"");
            }
        } else {
            Logger.Method1118(this.Method191());
        }
    }

    public entityDesyncCommand() {
        super("entitydesync", "Entity Desync", new String[]{"vanish"}, new SyntaxChunk("<Dismount/Remount>"));
    }
}