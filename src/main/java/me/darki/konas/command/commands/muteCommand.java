package me.darki.konas.command.commands;

import me.darki.konas.unremaped.Class484;
import me.darki.konas.unremaped.Class595;
import me.darki.konas.util.MuteCommandOptions;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;

public class muteCommand
extends Command {
    public muteCommand() {
        super("mute", "Mute or unmute people", new MuteCommandOptions("<add/del/clear/list>"), new Class595("[name]"));
    }

    public static void Method175(StringBuilder stringBuilder, String string) {
        stringBuilder.append(string + "\n");
    }

    @Override
    public void Method174(String[] stringArray) {
        block13: {
            block12: {
                if (stringArray.length == 2) {
                    if (stringArray[1].equalsIgnoreCase("list")) {
                        if (!Class484.Method2068().isEmpty()) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("\nMute List:\n");
                            Class484.Method2068().stream().forEach(arg_0 -> muteCommand.Method175(stringBuilder, arg_0));
                            Logger.Method1118(stringBuilder.toString());
                        } else {
                            Logger.Method1118("You don't have anyone muted");
                        }
                    } else if (stringArray[1].equalsIgnoreCase("clear")) {
                        if (!Class484.Method2068().isEmpty()) {
                            Class484.Method2070();
                            Logger.Method1118("Cleared your muted list!");
                        } else {
                            Logger.Method1119("Your muted list is already clear!");
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
                for (String string : Class484.Method2068()) {
                    if (!string.equalsIgnoreCase(stringArray[2])) continue;
                    Logger.Method1119("Player \u00c2\u00a7b" + stringArray[2] + "\u00c2\u00a7r is already muted!");
                    return;
                }
                Class484.Method2067(stringArray[2]);
                Logger.Method1118("Muted \u00c2\u00a7b" + stringArray[2]);
                break block13;
            }
            if (!stringArray[1].equalsIgnoreCase("del")) break block13;
            for (String string : Class484.Method2068()) {
                if (!string.equalsIgnoreCase(stringArray[2])) continue;
                Class484.Method2069(string);
                Logger.Method1118("Unmuted \u00c2\u00a7b" + stringArray[2]);
                return;
            }
            Logger.Method1119("Player \u00c2\u00a7b" + stringArray[2] + "\u00c2\u00a7r is not muted!");
        }
    }
}
