package me.darki.konas.command.commands;

import cookiedragon.eventsystem.EventDispatcher;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.unremaped.ExtraChat;
import me.darki.konas.unremaped.Class645;
import me.darki.konas.command.Command;

public class backupCommand
extends Command {
    public backupCommand() {
        super("backup", "Notifies your party that you need backup");
    }

    @Override
    public void Method174(String[] stringArray) {
        String string = "I need backup at X:" + backupCommand.Field123.player.getPosition().getX() + " Y:" + backupCommand.Field123.player.getPosition().getY() + " Z:" + backupCommand.Field123.player.getPosition().getZ() + " in the " + (backupCommand.Field123.player.dimension == -1 ? "Nether" : "Overworld");
        if (ModuleManager.getModuleByClass(ExtraChat.class).isEnabled() && ExtraChat.Field1442.getValue().booleanValue()) {
            backupCommand.Field123.player.sendChatMessage(string);
        } else {
            for (String string2 : PartyCommand.Field2509) {
                EventDispatcher.Companion.dispatch(new Class645(string2, string));
            }
        }
    }
}
