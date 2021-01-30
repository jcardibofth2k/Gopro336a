package me.darki.konas.command.commands;

import me.darki.konas.module.client.ClickGUIModule;
import me.darki.konas.module.render.Hud;
import me.darki.konas.Class247;
import me.darki.konas.Class425;
import me.darki.konas.Class427;
import me.darki.konas.Class548;
import me.darki.konas.Class555;
import me.darki.konas.Class557;
import me.darki.konas.SyntaxChunk;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;

public class fontCommand
extends Command {
    public static String Field1351 = "default";

    public fontCommand() {
        super("font", "Change the font", new SyntaxChunk("<Font>"));
    }

    @Override
    public void Method174(String[] stringArray) {
        if (stringArray.length != this.Method189().size() + 1) {
            Logger.Method1119(this.Method191());
            return;
        }
        Field1351 = stringArray[1];
        Hud.Field1405 = new Class555(stringArray[1], 18.0f);
        Class557.Method796(Hud.Field1405);
        ClickGUIModule.Field1536 = new Class555(stringArray[1], 17.0f);
        Class548.Method1018(ClickGUIModule.Field1536);
        Class425.Field957 = new Class555(stringArray[1], 20.0f);
        Class425.Field958 = new Class555(stringArray[1], 60.0f);
        Class425.Method955((Class427)((Object)Class425.Field954.getValue()));
        Class247.Field2262 = new Class555(stringArray[1], 18.0f);
        Class247.Field2261 = new Class555(stringArray[1], 16.0f);
    }
}
