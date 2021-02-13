package me.darki.konas.command.commands;

import me.darki.konas.util.SyntaxChunk;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;
import me.darki.konas.module.render.Search;

public class SearchCommand
extends Command {
    @Override
    public void Method174(String[] stringArray) {
        if (stringArray.length == 2) {
            if (stringArray[1].equalsIgnoreCase("list")) {
                if (!Search.customBlocks.getValue().Method684().isEmpty()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("\n");
                    Search.customBlocks.getValue().Method684().forEach(arg_0 -> SearchCommand.Method175(stringBuilder, arg_0));
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
            if (Search.customBlocks.getValue().Method681(stringArray[2])) {
                Logger.Method1118("Added Block &b" + stringArray[2]);
                if (Search.softReload.getValue().booleanValue()) {
                    Search.Method134();
                }
            } else {
                Logger.Method1119("Couldn't find block &b" + stringArray[2]);
            }
        } else if (stringArray[1].equalsIgnoreCase("del")) {
            if (Search.customBlocks.getValue().Method677(stringArray[2])) {
                Logger.Method1118("Removed Block &b" + stringArray[2]);
            } else {
                Logger.Method1119("Couldn't find block &b" + stringArray[2]);
            }
        } else {
            Logger.Method1118(this.Method191());
        }
        Search.customBlocks.getValue().Method680();
    }

    public SearchCommand() {
        super("search", "Add and remove blocks from search", new SyntaxChunk("<add/del/list>"), new SyntaxChunk("[block]"));
    }

    public static void Method175(StringBuilder stringBuilder, String string) {
        stringBuilder.append(string + "\n");
    }
}
