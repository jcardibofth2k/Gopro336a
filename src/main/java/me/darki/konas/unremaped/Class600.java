package me.darki.konas;

import me.darki.konas.Class304;
import me.darki.konas.Class443;
import me.darki.konas.SyntaxChunk;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;

public class Class600
extends Command {
    @Override
    public void Method174(String[] stringArray) {
        if (stringArray.length == 2) {
            if (stringArray[1].equalsIgnoreCase("list")) {
                if (!Class304.Field892.getValue().Method684().isEmpty()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("\n");
                    Class304.Field892.getValue().Method684().forEach(arg_0 -> Class600.Method175(stringBuilder, arg_0));
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
            if (Class304.Field892.getValue().Method681(stringArray[2])) {
                Logger.Method1118("Added Block &b" + stringArray[2]);
            } else {
                Logger.Method1119("Couldn't find block &b" + stringArray[2]);
            }
        } else if (stringArray[1].equalsIgnoreCase("del")) {
            if (Class304.Field892.getValue().Method677(stringArray[2])) {
                Logger.Method1118("Removed Block &b" + stringArray[2]);
            } else {
                Logger.Method1119("Couldn't find block &b" + stringArray[2]);
            }
        } else {
            Logger.Method1118(this.Method191());
        }
        Class304.Field892.getValue().Method680();
    }

    public static void Method175(StringBuilder stringBuilder, String string) {
        stringBuilder.append(string + "\n");
    }

    public Class600() {
        super("scaffold", "Add and remove blocks from scaffold filter", new SyntaxChunk("<add/del/list>"), new SyntaxChunk("[block]"));
    }
}
