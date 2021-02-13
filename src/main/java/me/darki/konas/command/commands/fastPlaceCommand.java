package me.darki.konas.command.commands;

import me.darki.konas.module.player.FastUse;
import me.darki.konas.util.SyntaxChunk;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;

public class fastPlaceCommand
extends Command {
    public static void Method175(StringBuilder stringBuilder, String string) {
        stringBuilder.append(string + "\n");
    }

    public fastPlaceCommand() {
        super("fastplace", "Add and remove blocks from fastplace whitelist", new SyntaxChunk("<add/del/list>"), new SyntaxChunk("[block]"));
    }

    @Override
    public void Method174(String[] stringArray) {
        if (stringArray.length == 2) {
            if (stringArray[1].equalsIgnoreCase("list")) {
                if (!FastUse.whitelist.getValue().Method684().isEmpty()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("\n");
                    FastUse.whitelist.getValue().Method684().forEach(arg_0 -> fastPlaceCommand.Method175(stringBuilder, arg_0));
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
            FastUse.whitelist.getValue().Method681(stringArray[2]);
        } else if (stringArray[1].equalsIgnoreCase("del")) {
            FastUse.whitelist.getValue().Method677(stringArray[2]);
        } else {
            Logger.Method1118(this.Method191());
        }
        FastUse.whitelist.getValue().Method680();
    }
}
