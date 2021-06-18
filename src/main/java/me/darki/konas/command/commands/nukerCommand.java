package me.darki.konas.command.commands;

import me.darki.konas.module.misc.Nuker;
import me.darki.konas.util.SyntaxChunk;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;

public class nukerCommand
extends Command {
    @Override
    public void Method174(String[] stringArray) {
        if (stringArray.length == 2) {
            if (stringArray[1].equalsIgnoreCase("list")) {
                if (!Nuker.validBlocks.getValue().Method684().isEmpty()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("\n");
                    Nuker.validBlocks.getValue().Method684().forEach(arg_0 -> nukerCommand.Method175(stringBuilder, arg_0));
                    Logger.Method1118(stringBuilder.toString());
                } else {
                    Logger.Method1118("You dont have any blocks added :(");
                }
            } else {
                Logger.Method1118(this.Method191());
            }
            return;
        }
        if (stringArray.length != this.Method189().size() + 1) {
            Logger.Method1118(this.Method191());
            return;
        }
        if (stringArray[1].equalsIgnoreCase("add")) {
            if (Nuker.validBlocks.getValue().Method681(stringArray[2])) {
                Logger.Method1118("Added Block &b" + stringArray[2]);
            } else {
                Logger.Method1119("Couldn't find block &b" + stringArray[2]);
            }
        } else if (stringArray[1].equalsIgnoreCase("del")) {
            if (Nuker.validBlocks.getValue().Method677(stringArray[2])) {
                Logger.Method1118("Removed Block &b" + stringArray[2]);
            } else {
                Logger.Method1119("Couldn't find block &b" + stringArray[2]);
            }
        } else {
            Logger.Method1118(this.Method191());
        }
        Nuker.validBlocks.getValue().Method680();
    }

    public nukerCommand() {
        super("nuker", "Add and remove blocks from nuker", new SyntaxChunk("<add/del/list>"), new SyntaxChunk("[block]"));
    }

    public static void Method175(StringBuilder stringBuilder, String string) {
        stringBuilder.append(string + "\n");
    }
}
