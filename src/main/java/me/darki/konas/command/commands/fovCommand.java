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
        if (var1_1.length != 2)
            Logger.Method1118(this.Method191());
        else {
            if (var1_1[1].equalsIgnoreCase("ergeisacoolgamer"))
                Logger.Method1118("epicjbug is cooler!");
            else {
                try {
                    float fov = Float.parseFloat(var1_1[1]);
                    fovCommand.Field123.gameSettings.fovSetting = fov;
                    Logger.Method1118("Set FOV to " + fov);
                } catch (Exception e) {
                    Logger.Method1119("Please enter a valid FOV!");
                }
            }
        }
    }

    public fovCommand() {
        super("FOV", "Sets your FOV to the value entered.", new String[]{"SetFOV", "FieldOfView"}, new SyntaxChunk("<FOV>"));
    }
}
