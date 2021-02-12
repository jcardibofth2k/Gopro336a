package me.darki.konas.command.commands;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import me.darki.konas.util.ChatUtil;
import me.darki.konas.unremaped.Class595;
import me.darki.konas.command.Command;

public class nameMcCommand
extends Command {
    @Override
    public void Method174(String[] stringArray) {
        if (stringArray.length != 2) {
            ChatUtil.Method1034(this.Method191());
            return;
        }
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            StringBuilder stringBuilder;
            URI uRI;
            Desktop desktop = Desktop.getDesktop();
            URI uRI2 = uRI;
            URI uRI3 = uRI;
            StringBuilder stringBuilder2 = stringBuilder;
            StringBuilder stringBuilder3 = stringBuilder;
            stringBuilder2();
            String string = "https://namemc.com/search?q=";
            StringBuilder stringBuilder4 = stringBuilder3.append(string);
            String string2 = stringArray[1];
            StringBuilder stringBuilder5 = stringBuilder4.append(string2);
            String string3 = stringBuilder5.toString();
            uRI2(string3);
            try {
                desktop.browse(uRI3);
            }
            catch (IOException | URISyntaxException exception) {
                // empty catch block
            }
        }
    }

    public nameMcCommand() {
        super("namemc", "Opens up the NameMC Page for the specified player", new String[]{"nmc"}, new Class595("[player]"));
    }
}
