package me.darki.konas.command.commands;

import me.darki.konas.Class309;
import me.darki.konas.Class531;
import me.darki.konas.SyntaxChunk;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;

public class noDesyncCommand
extends Command {
    public static void Method175(StringBuilder stringBuilder, String string) {
        stringBuilder.append(string + "\n");
    }

    @Override
    public void Method174(String[] stringArray) {
        if (stringArray.length == 2) {
            if (stringArray[1].equalsIgnoreCase("list")) {
                if (!Class309.Field881.getValue().Method1146().isEmpty()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("\n");
                    Class309.Field881.getValue().Method1146().forEach(arg_0 -> noDesyncCommand.Method175(stringBuilder, arg_0));
                    Logger.Method1118(stringBuilder.toString());
                } else {
                    Logger.Method1118("You dont have any items added :(");
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
            Class309.Field881.getValue().Method1147(stringArray[2]);
        } else if (stringArray[1].equalsIgnoreCase("del")) {
            Class309.Field881.getValue().Method1144(stringArray[2]);
        } else {
            Logger.Method1118(this.Method191());
        }
    }

    public noDesyncCommand() {
        super("nodesync", "Add and remove items from nodesync use whitelist", new SyntaxChunk("<add/del/list>"), new SyntaxChunk("[item]"));
    }
}
