package me.darki.konas.command.commands;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import me.darki.konas.unremaped.Class332;
import me.darki.konas.util.LanguageUtil;
import me.darki.konas.util.SyntaxChunk;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;

public class languageCommand
extends Command {
    @Override
    public void Method174(String[] stringArray) {
        block30: {
            StringBuilder stringBuilder;
            if (stringArray.length != this.Method189().size() + 1) {
                Logger.Method1118(this.Method191());
                return;
            }
            Map<String, String> map = LanguageUtil.Method754();
            Map<String, String> map2 = map;
            PrintStream printStream = System.out;
            Map<String, String> map3 = map2;
            printStream.println(map3);
            Map<String, String> map4 = map2;
            String string = stringArray[1];
            String string2 = LanguageUtil.Method755(map4, string);
            String string3 = string2;
            if (string3 == null) break block30;
            Class332.Field435 = string3;
            StringBuilder stringBuilder2 = stringBuilder;
            StringBuilder stringBuilder3 = stringBuilder;
            stringBuilder2();
            String string4 = "Set target language to ";
            StringBuilder stringBuilder4 = stringBuilder3.append(string4);
            char c = Command.Field122;
            StringBuilder stringBuilder5 = stringBuilder4.append(c);
            String string5 = "b";
            StringBuilder stringBuilder6 = stringBuilder5.append(string5);
            Map<String, String> map5 = map2;
            String string6 = string3;
            String string7 = map5.get(string6);
            String string8 = string7;
            StringBuilder stringBuilder7 = stringBuilder6.append(string8);
            String string9 = stringBuilder7.toString();
            Logger.Method1118(string9);
        }
        String string = "Couldn't find language!";
        try {
            Logger.Method1119(string);
        }
        catch (IOException iOException) {
            Logger.Method1119("Error while fetching languages!");
        }
    }

    public languageCommand() {
        super("language", "Lets you choose your target language for the Translate Module", new SyntaxChunk("<language>"));
    }
}
