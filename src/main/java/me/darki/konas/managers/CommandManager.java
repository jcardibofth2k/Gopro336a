package me.darki.konas.managers;

import java.util.ArrayList;

import me.darki.konas.command.Command;
import me.darki.konas.command.SpectateCommand;
import me.darki.konas.command.commands.*;
import me.darki.konas.unremaped.*;

public class CommandManager {
    public static ArrayList<Command> Field130 = new ArrayList();

    public static void Method206() {
        Field130.add(new bindCommand());
        Field130.add(new Class574());
        Field130.add(new drawCommand());
        Field130.add(new languageCommand());
        Field130.add(new Class580());
        Field130.add(new friendCommand());
        Field130.add(new SpectateCommand());
        Field130.add(new friendSyncCommand());
        Field130.add(new prefixCommand());
        Field130.add(new Class585());
        Field130.add(new modulesCommand());
        Field130.add(new settingsCommand());
        Field130.add(new SearchCommand());
        Field130.add(new fastPlaceCommand());
        Field130.add(new noDesyncCommand());
        Field130.add(new gotoCommand());
        Field130.add(new Class586());
        Field130.add(new Class590());
        Field130.add(new Class591());
        Field130.add(new HClipCommand());
        Field130.add(new Class581());
        Field130.add(new fovCommand());
        Field130.add(new grabCommand());
        Field130.add(new macroCommand());
        Field130.add(new Class576());
        Field130.add(new fontCommand());
        Field130.add(new WaypointCommand());
        Field130.add(new SpammerCommand());
        Field130.add(new xrayCommand());
        Field130.add(new bookCommand());
        Field130.add(new PartyCommand());
        Field130.add(new backupCommand());
        Field130.add(new muteCommand());
        Field130.add(new moduleConfigCommand());
        Field130.add(new Class575());
        Field130.add(new Class600());
        Field130.add(new SeisureCommand());
        Field130.add(new Class577());
        Field130.add(new nameMcCommand());
        Field130.add(new Class593());
        Field130.add(new nukerCommand());
        Field130.add(new holeFillCommand());
        Field130.add(new rubberFillCommand());
    }

    public static ArrayList<Command> Method207() {
        return Field130;
    }

    public static Command Method208(String string) {
        for (Command command : Field130) {
            if (string.startsWith(Command.Field121)) {
                string = string.substring(1);
            }
            if (!command.Method188(string)) continue;
            return command;
        }
        return null;
    }
}
