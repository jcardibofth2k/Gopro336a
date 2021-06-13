package me.darki.konas.managers;

import java.util.ArrayList;

import me.darki.konas.command.Command;
import me.darki.konas.command.commands.SpectateCommand;
import me.darki.konas.command.commands.*;
import me.darki.konas.command.commands.configCommand;
import me.darki.konas.unremaped.*;

public class CommandManager {
    public static ArrayList<Command> commands = new ArrayList();

    public static void add() {
        commands.add(new bindCommand());
        commands.add(new commandsCommand());
        commands.add(new drawCommand());
        commands.add(new languageCommand());
        commands.add(new Class580());
        commands.add(new friendCommand());
        commands.add(new SpectateCommand());
        commands.add(new friendSyncCommand());
        commands.add(new prefixCommand());
        commands.add(new Class585());
        commands.add(new modulesCommand());
        commands.add(new settingsCommand());
        commands.add(new SearchCommand());
        commands.add(new fastPlaceCommand());
        commands.add(new noDesyncCommand());
        commands.add(new gotoCommand());
        commands.add(new Class586());
        commands.add(new entityDesyncCommand());
        commands.add(new Class591());
        commands.add(new HClip());
        commands.add(new Class581());
        commands.add(new fovCommand());
        commands.add(new grabCommand());
        commands.add(new macroCommand());
        commands.add(new configCommand());
        commands.add(new fontCommand());
        commands.add(new WaypointCommand());
        commands.add(new SpammerCommand());
        commands.add(new xrayCommand());
        commands.add(new bookCommand());
        commands.add(new Party());
        commands.add(new backupCommand());
        commands.add(new muteCommand());
        commands.add(new moduleConfigCommand());
        commands.add(new disconectCommand());
        commands.add(new Class600());
        commands.add(new SeisureCommand());
        commands.add(new Class577());
        commands.add(new nameMcCommand());
        commands.add(new watermarkCommand());
        commands.add(new nukerCommand());
        commands.add(new holeFillCommand());
        commands.add(new rubberFillCommand());
    }

    public static ArrayList<Command> Method207() {
        return commands;
    }

    public static Command Method208(String string) {
        for (Command command : commands) {
            if (string.startsWith(Command.prefix)) {
                string = string.substring(1);
            }
            if (!command.Method188(string)) continue;
            return command;
        }
        return null;
    }
}