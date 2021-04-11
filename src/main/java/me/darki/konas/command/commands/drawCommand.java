package me.darki.konas.command.commands;

import me.darki.konas.module.ModuleManager;
import me.darki.konas.unremaped.Class605;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;
import me.darki.konas.module.Module;

public class drawCommand
extends Command {
    @Override
    public void Method174(String[] stringArray) {
        if (stringArray.length != this.Method189().size() + 1) {
            Logger.Method1119(this.Method191());
            return;
        }
        Module module = ModuleManager.Method1612(stringArray[1]);
        if (module != null) {
            module.setVisible(!module.isVisible());
            Logger.Method1118("Drawn Module &b" + module.getName());
        } else {
            Logger.Method1119("Unknown Module &b" + stringArray[1]);
        }
    }

    public drawCommand() {
        super("draw", "Makes Modules Visible or Invisible on the ArrayList", new Class605("<Module>"));
    }
}
