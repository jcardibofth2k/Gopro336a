package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.util.SyntaxChunk;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;

public class Class591
extends Command {
    /*
     * Unable to fully structure code
     */
    @Override
    public void Method174(String[] var1_1) {
        block51: {
            block50: {
                block49: {
                    block48: {
                        block47: {
                            if (var1_1.length != 2) break block50;
                            v0 = Class591.Field123.player;
                            v1 = v0.getRidingEntity();
                            if (v1 == null) ** GOTO lbl37
                            v2 = Class591.Field123.player;
                            v3 = v2.getRidingEntity();
                            v4 = Class591.Field123.player;
                            v5 = v4.getRidingEntity();
                            v6 = v5.posX;
                            v7 = Class591.Field123.player;
                            v8 = v7.getRidingEntity();
                            v9 = v8.posY;
                            v10 = var1_1[1];
                            v11 = Double.parseDouble(v10);
                            v12 = v9 + v11;
                            v13 = Class591.Field123.player;
                            v14 = v13.getRidingEntity();
                            v15 = v14.posZ;
                            v3.setPosition(v6, v12, v15);
                            break block47;
lbl37:
                            // 1 sources

                            v16 = Class591.Field123.player;
                            v17 = Class591.Field123.player.posX;
                            v18 = Class591.Field123.player.posY;
                            v19 = var1_1[1];
                            v20 = Double.parseDouble(v19);
                            v21 = v18 + v20;
                            v22 = Class591.Field123.player.posZ;
                            v16.setPosition(v17, v21, v22);
                        }
                        v23 = v24;
                        v25 = v24;
                        v23();
                        v26 = "Teleported you ";
                        v27 = v25.append(v26);
                        v28 = var1_1[1];
                        v29 = Double.parseDouble(v28);
                        if (!(v29 > 0.0)) break block48;
                        v30 = "up ";
                        break block49;
                    }
                    v30 = "down ";
                }
                v31 = v27.append(v30);
                v32 = var1_1[1];
                v33 = Double.parseDouble(v32);
                v34 = Math.abs(v33);
                v35 = v31.append(v34);
                v36 = " blocks.";
                v37 = v35.append(v36);
                v38 = v37.toString();
                try {
                    Logger.Method1118(v38);
                }
                catch (NumberFormatException var2_2) {
                    Logger.Method1119("Please enter a valid distance!");
                }
                break block51;
            }
            Logger.Method1118(this.Method191());
        }
    }

    public Class591() {
        super("VClip", "Teleport you vertically", new String[]{"PosV"}, new SyntaxChunk("<Distance>"));
    }
}