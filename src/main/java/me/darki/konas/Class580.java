package me.darki.konas;

import me.darki.konas.Class167;
import me.darki.konas.Class605;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;

public class Class580
extends Command {
    @Override
    public void Method174(String[] stringArray) {
        if (stringArray.length != this.Method189().size() + 1) {
            Logger.Method1119(this.Method191());
            return;
        }
        if (Class167.Method1612(stringArray[1]) != null) {
            Class167.Method1612(stringArray[1]).toggle();
        } else {
            Logger.Method1119("Invalid Module");
        }
    }

    public Class580() {
        super("toggle", "Toggle Modules", new String[]{"t"}, new Class605("<module>"));
    }
}
