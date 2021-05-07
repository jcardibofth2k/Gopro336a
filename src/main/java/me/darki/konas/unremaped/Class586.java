package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.util.SyntaxChunk;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.MathHelper;

public class Class586
extends Command {
    public Class586() {
        super("yaw", "Set Player's Yaw", new String[]{"setyaw"}, new SyntaxChunk("<x/yaw>"), new SyntaxChunk("[z]"));
    }

    @Override
    public void Method174(String[] stringArray) {
        block18: {
            block19: {
                if (stringArray.length != 2) break block19;
                if (Class586.Field123.player == null) break block18;
                EntityPlayerSP entityPlayerSP = Class586.Field123.player;
                String string = stringArray[1];
                double d = Double.parseDouble(string);
                double d2 = MathHelper.wrapDegrees((double)d);
                try {
                    entityPlayerSP.rotationYaw = (float)d2;
                }
                catch (NumberFormatException numberFormatException) {
                    Logger.Method1119("Please enter a valid yaw!");
                }
                break block18;
            }
            if (stringArray.length != 3) break block18;
            if (Class586.Field123.player == null) break block18;
            EntityPlayerSP entityPlayerSP = Class586.Field123.player;
            double d = Class586.Field123.player.posZ;
            String string = stringArray[2];
            double d3 = Double.parseDouble(string);
            double d4 = d - d3;
            double d5 = Class586.Field123.player.posX;
            String string2 = stringArray[1];
            double d6 = Double.parseDouble(string2);
            double d7 = d5 - d6;
            double d8 = Math.atan2(d4, d7);
            double d9 = Math.toDegrees(d8);
            double d10 = d9 + 90.0;
            double d11 = MathHelper.wrapDegrees((double)d10);
            try {
                entityPlayerSP.rotationYaw = (float)d11;
            }
            catch (NumberFormatException numberFormatException) {
                Logger.Method1119("Please enter a valid yaw!");
            }
        }
    }
}