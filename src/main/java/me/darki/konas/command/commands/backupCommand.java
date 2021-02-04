package me.darki.konas.command.commands;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.Class167;
import me.darki.konas.Class299;
import me.darki.konas.Class645;
import me.darki.konas.SyntaxChunk;
import me.darki.konas.command.Command;

public class backupCommand
extends Command {
    public backupCommand() {
        super("backup", "Notifies your party that you need backup");
    }

    @Override
    public void Method174(String[] stringArray) {
        String string = "I need backup at X:" + backupCommand.Field123.player.getPosition().getX() + " Y:" + backupCommand.Field123.player.getPosition().getY() + " Z:" + backupCommand.Field123.player.getPosition().getZ() + " in the " + (backupCommand.Field123.player.dimension == -1 ? "Nether" : "Overworld");
        if (Class167.Method1610(Class299.class).isEnabled() && Class299.Field1442.getValue().booleanValue()) {
            backupCommand.Field123.player.sendChatMessage(string);
        } else {
            for (String string2 : Party.Field2509) {
                EventDispatcher.Companion.dispatch(new Class645(string2, string));
            }
        }
    }
}
