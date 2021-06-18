package me.darki.konas.command.commands;

import java.util.ArrayList;
import me.darki.konas.unremaped.Class595;
import me.darki.konas.util.PartyUtil;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;

public class PartyCommand
extends Command {
    public static ArrayList<String> Field2509 = new ArrayList();

    @Override
    public void Method174(String[] stringArray) {
        block13: {
            block12: {
                if (stringArray.length == 2) {
                    if (stringArray[1].equalsIgnoreCase("list")) {
                        if (!Field2509.isEmpty()) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("\nParty List:\n");
                            Field2509.stream().forEach(arg_0 -> PartyCommand.Method175(stringBuilder, arg_0));
                            Logger.Method1118(stringBuilder.toString());
                        } else {
                            Logger.Method1118("There is no one in your party");
                        }
                    } else if (stringArray[1].equalsIgnoreCase("clear")) {
                        if (!Field2509.isEmpty()) {
                            Field2509.clear();
                            Logger.Method1118("Cleared your party!");
                        } else {
                            Logger.Method1119("Your party is already empty!");
                        }
                    } else {
                        Logger.Method1119("Invalid Argument. Check syntax");
                    }
                    return;
                }
                if (stringArray.length != this.Method189().size() + 1) {
                    Logger.Method1118(this.Method191());
                    return;
                }
                if (!stringArray[1].equalsIgnoreCase("add")) break block12;
                for (String string : Field2509) {
                    if (!string.equalsIgnoreCase(stringArray[2])) continue;
                    Logger.Method1119("Player \u00c2\u00a7b" + stringArray[2] + "\u00c2\u00a7r is already in your party!");
                    return;
                }
                Field2509.add(stringArray[2]);
                Logger.Method1118("Added \u00c2\u00a7b" + stringArray[2] + "\u00c2\u00a7r to your party!");
                break block13;
            }
            if (!stringArray[1].equalsIgnoreCase("del")) break block13;
            for (String string : Field2509) {
                if (!string.equalsIgnoreCase(stringArray[2])) continue;
                Field2509.remove(string);
                Logger.Method1118("Deleted \u00c2\u00a7b" + stringArray[2] + "\u00c2\u00a7r from your party");
                return;
            }
            Logger.Method1119("Player \u00c2\u00a7b" + stringArray[2] + "\u00c2\u00a7r is not in your party!");
        }
    }

    public PartyCommand() {
        super("party", "Add or Remove People to/from your party", new PartyUtil("<add/del/clear/list>"), new Class595("[name]"));
    }

    public static void Method175(StringBuilder stringBuilder, String string) {
        stringBuilder.append(string + "\n");
    }
}
