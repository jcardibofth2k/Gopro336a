package me.darki.konas.command.commands;

import me.darki.konas.module.ModuleManager;
import me.darki.konas.module.movement.AutoWalk;
import me.darki.konas.unremaped.BlockUtil;
import me.darki.konas.util.SyntaxChunk;
import me.darki.konas.command.Command;
import me.darki.konas.command.Logger;
import me.darki.konas.module.Module;
import net.minecraft.util.math.BlockPos;

public class gotoCommand
extends Command {
    public gotoCommand() {
        super("goto", "Go to coordinates", new String[]{"go"}, new SyntaxChunk("<X>"), new SyntaxChunk("<Y>"), new SyntaxChunk("<Z>"));
    }

    @Override
    public void Method174(String[] stringArray) {
        block17: {
            String string;
            BlockPos blockPos;
            BlockPos blockPos2;
            BlockUtil blockUtil;
            BlockUtil class5012;
            if (stringArray.length != this.Method189().size() + 1) {
                Logger.Method1118(this.Method191());
                return;
            }
            Module module = ModuleManager.Method1612("AutoWalk");
            if (module == null) {
                return;
            }
            if (!(module instanceof AutoWalk)) {
                return;
            }
            AutoWalk autoWalk = (AutoWalk)module;
            autoWalk.Field1813 = 0;
            autoWalk.Field1814 = false;
            try {
                BlockPos blockPos3;
                BlockUtil class5013;
                AutoWalk class3992 = autoWalk;
                class5012 = class5013;
                blockUtil = class5013;
                blockPos2 = blockPos3;
                blockPos = blockPos3;
                string = stringArray[1];
            }
            catch (Exception exception) {
                Logger.Method1118(this.Method191());
                return;
            }
            int n = Integer.parseInt(string);
            String string2 = stringArray[2];
            int n2 = Integer.parseInt(string2);
            String string3 = stringArray[3];
            int n3 = Integer.parseInt(string3);
            blockPos2(n, n2, n3);
            String string4 = "GOTO-WALK";
            class5012(blockPos, string4);
            class3992.Field1812 = blockUtil;
            autoWalk.Field1812.Method1422();
            autoWalk.pathFind.setValue(Boolean.valueOf(true));
            if (autoWalk.isEnabled()) break block17;
            autoWalk.toggle();
        }
    }
}
