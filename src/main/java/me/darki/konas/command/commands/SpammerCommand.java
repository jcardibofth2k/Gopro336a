package me.darki.konas.command.commands;

import me.darki.konas.util.SyntaxChunk;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;
import me.darki.konas.module.misc.Spammer;

public class SpammerCommand
extends Command {
    @Override
    public void Method174(String[] stringArray) {
        if (stringArray.length == this.Method189().size()) {
            if (stringArray[1].equalsIgnoreCase("list")) {
                if (Spammer.spamFileList.getValue().Method1225().isEmpty()) {
                    Logger.Method1118("You don't have any spammer files loaded");
                } else {
                    Logger.Method1118("Spammer Files:");
                    for (String string : Spammer.spamFileList.getValue().Method1225()) {
                        Logger.Method1118(string);
                    }
                }
                return;
            }
        }
        if (stringArray.length != this.Method189().size() + 1) {
            Logger.Method1119(this.Method191());
            return;
        }
        if (stringArray[1].equalsIgnoreCase("add")) {
            String string = stringArray[2].replaceAll(".txt", "") + ".txt";
            if (Spammer.spamFileList.getValue().Method1225().contains(string)) {
                Logger.Method1118("This Spammer File has already been added!");
                return;
            }
            Spammer.spamFileList.getValue().Method1227(string);
            Logger.Method1118("Added Spammer File: \u00c2\u00a7b" + string);
        } else if (stringArray[1].equalsIgnoreCase("del")) {
            String string = stringArray[2].replaceAll(".txt", "") + ".txt";
                if (!Spammer.spamFileList.getValue().Method1225().contains(string)) {
                Logger.Method1118("Couldn't find this Spammer File!");
                return;
            }
            Spammer.spamFileList.getValue().Method1228(string);
            Logger.Method1118("Deleted Spammer File: \u00c2\u00a7b" + string);
        } else {
            Logger.Method1119(this.Method191());
        }
    }

    public SpammerCommand() {
        super("spammer", "Load spammer files", new SyntaxChunk("<add/del/list>"), new SyntaxChunk("<name>"));
    }
}
