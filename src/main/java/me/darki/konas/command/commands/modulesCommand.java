package me.darki.konas.command.commands;

import me.darki.konas.module.ModuleManager;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;
import me.darki.konas.module.Module;
import org.lwjgl.input.Keyboard;

public class modulesCommand
extends Command {
    public modulesCommand() {
        super("modules", "List all modules including their given keybinds");
    }

    public static void Method1335(Module module) {
        Logger.Method1118(module.getName() + (module.Method1646() != 0 ? " [&b" + Keyboard.getKeyName(module.Method1646()) + "&f]" : ""));
    }

    @Override
    public void Method174(String[] stringArray) {
        Logger.Method1118("&bModules:");
        Logger.Method1118(" ");
        ModuleManager.getModules().forEach(modulesCommand::Method1335);
    }
}
