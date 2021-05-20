package me.darki.konas.command.commands;

import me.darki.konas.module.client.ClickGUIModule;
import me.darki.konas.module.render.Hud;
import me.darki.konas.unremaped.Class247;
import me.darki.konas.unremaped.Class425;
import me.darki.konas.unremaped.Class548;
import me.darki.konas.unremaped.CfontRenderer;
import me.darki.konas.unremaped.Class557;
import me.darki.konas.util.SyntaxChunk;
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
        Hud.Field1405 = new CfontRenderer(stringArray[1], 18.0f);
        Class557.Method796(Hud.Field1405);
        ClickGUIModule.Field1536 = new CfontRenderer(stringArray[1], 17.0f);
        Class548.Method1018(ClickGUIModule.Field1536);
        Class425.Field957 = new CfontRenderer(stringArray[1], 20.0f);
        Class425.Field958 = new CfontRenderer(stringArray[1], 60.0f);
        Class425.Method955(Class425.Field954.getValue());
        Class247.cfontRenderer = new CfontRenderer(stringArray[1], 18.0f);
        Class247.smallCFontRenderer = new CfontRenderer(stringArray[1], 16.0f);
    }
}
