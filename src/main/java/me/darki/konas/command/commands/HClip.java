package me.darki.konas.command.commands;

import me.darki.konas.util.PlayerUtil;
import me.darki.konas.util.SyntaxChunk;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;
import net.minecraft.entity.Entity;

public class HClip
        extends Command {
    public HClip() {
        super("HClip", "Teleport you horizontally", new String[]{"PosH"}, new SyntaxChunk("<Distance>"));
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void Method174(String[] var1_1) {
        if (var1_1.length != 2)
            Logger.Method1118(this.Method191());
        else {
            float yaw = Field123.player.rotationYaw;
            double[] var2_2 = PlayerUtil.Method1089(yaw);

            try {
                if (Field123.player.getRidingEntity() == null) {
                    double v24 = Field123.player.posY;
                    double v25 = var2_2[0];
                    double v26 = Double.parseDouble(var1_1[1]);
                    double v28 = v24 + v25 * v26;

                    double v29 = Field123.player.posY;
                    double v30 = Field123.player.posZ;
                    double v31 = var2_2[1];
                    double v34 = v30 + v31 * Double.parseDouble(var1_1[1]);

                    Field123.player.setPosition(v28, v29, v34);
                } else {
                    Entity entity = Field123.player.getRidingEntity();
                    double v12 = entity.posX + var2_2[0] * Double.parseDouble(var1_1[1]);
                    double v15 = entity.posY;
                    double v22 = entity.posZ + var2_2[1] * Double.parseDouble(var1_1[1]);

                    entity.setPosition(v12, v15, v22);
                }

                double height = Double.parseDouble(var1_1[1]);
                String v50 = "Teleported you " + height + " ";
                if (height > 0)
                    v50 += "forwards ";
                else
                    v50 += "backwards ";

                v50 += "blocks.";

                Logger.Method1118(v50);
            } catch (NumberFormatException e) {
                Logger.Method1119("Please enter a valid distance!");
            }
        }
    }
}
