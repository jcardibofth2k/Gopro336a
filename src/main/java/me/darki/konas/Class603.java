package me.darki.konas;

import java.util.ArrayList;

import me.darki.konas.command.Command;
import me.darki.konas.command.commands.HClip;
import me.darki.konas.command.commands.Party;
import me.darki.konas.command.commands.backupCommand;
import me.darki.konas.command.commands.bindCommand;
import me.darki.konas.command.commands.bookCommand;
import me.darki.konas.command.commands.drawCommand;
import me.darki.konas.command.commands.fastPlaceCommand;
import me.darki.konas.command.commands.fontCommand;
import me.darki.konas.command.commands.fovCommand;
import me.darki.konas.command.commands.friendCommand;
import me.darki.konas.command.commands.friendSyncCommand;
import me.darki.konas.command.commands.gotoCommand;
import me.darki.konas.command.commands.grabCommand;
import me.darki.konas.command.commands.holeFillCommand;
import me.darki.konas.command.commands.languageCommand;
import me.darki.konas.command.commands.macroCommand;
import me.darki.konas.command.commands.moduleConfigCommand;
import me.darki.konas.command.commands.modulesCommand;
import me.darki.konas.command.commands.muteCommand;
import me.darki.konas.command.commands.nameMcCommand;
import me.darki.konas.command.commands.noDesyncCommand;
import me.darki.konas.command.commands.nukerCommand;
import me.darki.konas.command.commands.prefixCommand;
import me.darki.konas.command.commands.rubberFillCommand;
import me.darki.konas.command.commands.settingsCommand;
import me.darki.konas.command.commands.xrayCommand;

public class Class603 {
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
        Field130.add(new HClip());
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
        Field130.add(new Party());
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
