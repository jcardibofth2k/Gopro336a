package me.darki.konas.command.commands;

import java.util.ArrayList;
import me.darki.konas.module.ModuleManager;
import me.darki.konas.setting.ColorValue;
import me.darki.konas.unremaped.Class537;
import me.darki.konas.unremaped.Class605;
import me.darki.konas.setting.Keybind;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;
import me.darki.konas.module.Module;
import me.darki.konas.setting.Setting;
import org.lwjgl.input.Keyboard;

public class settingsCommand
extends Command {
    @Override
    public void Method174(String[] stringArray) {
        if (stringArray.length != this.Method189().size() + 1) {
            Logger.Method1118(this.Method191());
            return;
        }
        if (ModuleManager.Method1612(stringArray[1]) == null) {
            Logger.Method1118("Module not found");
            return;
        }
        Module module = ModuleManager.Method1612(stringArray[1]);
        if (ModuleManager.Method1615(module) == null) {
            Logger.Method1119(module.getName() + " does not have any settings");
            return;
        }
        Logger.Method1118(module.getName() + "'s Settings:");
        Logger.Method1118(" ");
        for (Setting setting : ModuleManager.Method1615(module)) {
            if (setting.getValue() instanceof Enum) {
                ArrayList<String> arrayList = new ArrayList<String>();
                for (Object obj : setting.getValue().getClass().getEnumConstants()) {
                    arrayList.add(obj.toString());
                }
                Logger.Method1118(setting.Method1183() + " [&b" + setting.getValue() + "&f] &b" + arrayList + "&f");
                continue;
            }
            if (setting.getValue() instanceof Keybind) {
                Logger.Method1118(setting.Method1183() + " [&b" + Keyboard.getKeyName(((Keybind)setting.getValue()).Method851()) + "&f]");
                continue;
            }
            if (setting.getValue() instanceof Class537) {
                Logger.Method1118(setting.Method1183() + " [&b" + Keyboard.getKeyName(((Class537)setting.getValue()).Method851()) + "&f]");
                continue;
            }
            if (setting.getValue() instanceof ColorValue) {
                Logger.Method1118(setting.Method1183() + " [&b" + ((ColorValue)setting.getValue()).Method778() + ", " + ((ColorValue)setting.getValue()).Method783() + "&f]");
                continue;
            }
            Logger.Method1118(setting.Method1183() + " [&b" + setting.getValue() + "&f]" + (setting.Method1187() != null && setting.Method1182() != null ? " Min:&b " + setting.Method1187() + "&f Max:&b " + setting.Method1182() : ""));
        }
    }

    public settingsCommand() {
        super("settings", "Shows you a modules settings", new Class605("<module>"));
    }
}
