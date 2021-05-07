package me.darki.konas.command.commands;

import me.darki.konas.util.PlayerUtil;
import me.darki.konas.util.SyntaxChunk;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;

public class HClipCommand
extends Command {
    public HClipCommand() {
        super("HClip", "Teleport you horizontally", new String[]{"PosH"}, new SyntaxChunk("<Distance>"));
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void Method174(String[] var1_1) {
        block57: {
            block56: {
                block55: {
                    block54: {
                        block53: {
                            if (var1_1.length != 2) break block56;
                            v0 = HClipCommand.Field123.player.rotationYaw;
                            v1 = PlayerUtil.Method1089(v0);
                            var2_2 = v1;
                            v2 = HClipCommand.Field123.player;
                            v3 = v2.getRidingEntity();
                            if (v3 == null) ** GOTO lbl49
                            v4 = HClipCommand.Field123.player;
                            v5 = v4.getRidingEntity();
                            v6 = HClipCommand.Field123.player;
                            v7 = v6.getRidingEntity();
                            v8 = v7.posX;
                            v9 = var2_2[0];
                            v10 = var1_1[1];
                            v11 = Double.parseDouble(v10);
                            v12 = v8 + v9 * v11;
                            v13 = HClipCommand.Field123.player;
                            v14 = v13.getRidingEntity();
                            v15 = v14.posY;
                            v16 = HClipCommand.Field123.player;
                            v17 = v16.getRidingEntity();
                            v18 = v17.posZ;
                            v19 = var2_2[1];
                            v20 = var1_1[1];
                            v21 = Double.parseDouble(v20);
                            v22 = v18 + v19 * v21;
                            v5.setPosition(v12, v15, v22);
                            break block53;
lbl49:
                            // 1 sources

                            v23 = HClipCommand.Field123.player;
                            v24 = HClipCommand.Field123.player.posX;
                            v25 = var2_2[0];
                            v26 = var1_1[1];
                            v27 = Double.parseDouble(v26);
                            v28 = v24 + v25 * v27;
                            v29 = HClipCommand.Field123.player.posY;
                            v30 = HClipCommand.Field123.player.posZ;
                            v31 = var2_2[1];
                            v32 = var1_1[1];
                            v33 = Double.parseDouble(v32);
                            v34 = v30 + v31 * v33;
                            v23.setPosition(v28, v29, v34);
                        }
                        v35 = v36;
                        v37 = v36;
                        v35();
                        v38 = "Teleported you ";
                        v39 = v37.append(v38);
                        v40 = var1_1[1];
                        v41 = Double.parseDouble(v40);
                        if (!(v41 > 0.0)) break block54;
                        v42 = "forwards ";
                        break block55;
                    }
                    v42 = "backwards ";
                }
                v43 = v39.append(v42);
                v44 = var1_1[1];
                v45 = Double.parseDouble(v44);
                v46 = Math.abs(v45);
                v47 = v43.append(v46);
                v48 = " blocks.";
                v49 = v47.append(v48);
                v50 = v49.toString();
                try {
                    Logger.Method1118(v50);
                }
                catch (NumberFormatException var2_3) {
                    Logger.Method1119("Please enter a valid distance!");
                }
                break block57;
            }
            Logger.Method1118(this.Method191());
        }
    }
}
