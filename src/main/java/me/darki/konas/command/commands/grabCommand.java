package me.darki.konas.command.commands;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;

public class grabCommand
extends Command {
    public grabCommand() {
        super("Grab", "Copies your current coords to your clipboard", new String[]{"Coords", "CopyCoords", "CopyPos", "CopyPosition"});
    }

    @Override
    public void Method174(String[] stringArray) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(Math.round(grabCommand.Field123.player.posX) + ", " + Math.round(grabCommand.Field123.player.posY) + ", " + Math.round(grabCommand.Field123.player.posZ));
        clipboard.setContents(stringSelection, null);
        Logger.Method1118("Copied coords to clipboard!");
    }
}
