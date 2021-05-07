package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;
import me.darki.konas.module.ModuleManager;

public class Class580
extends Command {
    @Override
    public void Method174(String[] stringArray) {
        if (stringArray.length != this.Method189().size() + 1) {
            Logger.Method1119(this.Method191());
            return;
        }
        if (ModuleManager.Method1612(stringArray[1]) != null) {
            ModuleManager.Method1612(stringArray[1]).toggle();
        } else {
            Logger.Method1119("Invalid Module");
        }
    }

    public Class580() {
        super("toggle", "Toggle Modules", new String[]{"t"}, new Class605("<module>"));
    }
}