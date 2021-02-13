package me.darki.konas.command.commands;

import me.darki.konas.util.SyntaxChunk;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;

public class fovCommand
extends Command {
    /*
     * Unable to fully structure code
     */
    @Override
    public void Method174(String[] var1_1) {
        block25: {
            block26: {
                if (var1_1.length != 2) break block26;
                v0 = var1_1[1];
                v1 = "ergeisacoolgamer";
                v2 = v0.equalsIgnoreCase(v1);
                if (!v2) ** GOTO lbl17
                v3 = "epicjbug is cooler!";
                Logger.Method1118(v3);
                break block25;
lbl17:
                // 1 sources

                v4 = fovCommand.Field123.gameSettings;
                v5 = var1_1[1];
                v6 = Float.parseFloat(v5);
                v4.fovSetting = v6;
                v7 = v8;
                v9 = v8;
                v7();
                v10 = "Set FOV to ";
                v11 = v9.append(v10);
                v12 = var1_1[1];
                v13 = Float.parseFloat(v12);
                v14 = v11.append(v13);
                v15 = v14.toString();
                try {
                    Logger.Method1118(v15);
                }
                catch (NumberFormatException var2_2) {
                    Logger.Method1119("Please enter a valid FOV!");
                }
                break block25;
            }
            Logger.Method1118(this.Method191());
        }
    }

    public fovCommand() {
        super("FOV", "Sets your FOV to the value entered.", new String[]{"SetFOV", "FieldOfView"}, new SyntaxChunk("<FOV>"));
    }
}
