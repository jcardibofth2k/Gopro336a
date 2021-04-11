package me.darki.konas.util;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;
import me.darki.konas.managers.CommandManager;
import me.darki.konas.unremaped.Class648;

public class CommandUtil {
    public static CommandUtil Field163 = new CommandUtil();

    @Subscriber
    public void Method276(Class648 class648) {
        block2: {
            String string = class648.Method1201();
            if (!string.startsWith(Command.Method190())) break block2;
            class648.setCanceled(true);
            String[] stringArray = string.replaceAll("([\\s])\\1+", "$1").split(" ");
            if (CommandManager.Method208(stringArray[0]) != null) {
                CommandManager.Method208(stringArray[0]).Method174(stringArray);
            } else {
                Logger.Method1119("Command not found! To view a list of all commands type " + Command.Method190() + "commands");
            }
        }
    }
}