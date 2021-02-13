package me.darki.konas.command.commands;

import me.darki.konas.util.FriendSyncUtil;
import me.darki.konas.unremaped.Class492;
import me.darki.konas.unremaped.Class509;
import me.darki.konas.util.command.friendSyncclients;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;

public class friendSyncCommand
extends Command {
    public static void Method1431(String string) {
        if (Class492.Method1989(string)) {
            Logger.Method1118("Is already a friend: " + string);
            return;
        }
        String string2 = Class509.Method1349(string);
        if (string2 != null && !string2.isEmpty()) {
            Class492.Method1990(string, string2);
            Logger.Method1118("Added friend: " + string + " (" + string2 + ")");
        } else {
            Logger.Method1119("Unable to add friend: " + string);
        }
    }

    public friendSyncCommand() {
        super("friendsync", "Sync friends with other clients", new friendSyncclients("<client>"));
    }

    public static void Method1432() {
        FriendSyncUtil.Method2313().forEach(friendSyncCommand::Method1434);
    }

    public static void Method1433(String string) {
        if (Class492.Method1989(string)) {
            Logger.Method1118("Is already a friend: " + string);
            return;
        }
        String string2 = Class509.Method1349(string);
        if (string2 != null && !string2.isEmpty()) {
            Class492.Method1990(string, string2);
            Logger.Method1118("Added friend: " + string + " (" + string2 + ")");
        } else {
            Logger.Method1119("Unable to add friend: " + string);
        }
    }

    public static void Method508() {
        FriendSyncUtil.Method2314().forEach(friendSyncCommand::Method1433);
    }

    public static void Method1434(String string) {
        if (Class492.Method1989(string)) {
            Logger.Method1118("Is already a friend: " + string);
            return;
        }
        String string2 = Class509.Method1349(string);
        if (string2 != null && !string2.isEmpty()) {
            Class492.Method1990(string, string2);
            Logger.Method1118("Added friend: " + string + " (" + string2 + ")");
        } else {
            Logger.Method1119("Unable to add friend: " + string);
        }
    }

    @Override
    public void Method174(String[] stringArray) {
        if (stringArray.length != this.Method189().size() + 1) {
            Logger.Method1118(this.Method191());
            return;
        }
        if (stringArray[1].equalsIgnoreCase("future")) {
            new Thread(friendSyncCommand::Method1435).start();
            return;
        }
        if (stringArray[1].equalsIgnoreCase("pyro")) {
            new Thread(friendSyncCommand::Method1432).start();
            return;
        }
        if (stringArray[1].equalsIgnoreCase("rusherhack")) {
            new Thread(friendSyncCommand::Method508).start();
            return;
        }
        Logger.Method1118(this.Method191());
    }

    public static void Method1435() {
        FriendSyncUtil.Method2311().forEach(friendSyncCommand::Method1431);
    }
}
