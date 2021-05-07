package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.module.Module;
import me.darki.konas.module.player.GuiMove;
import me.darki.konas.setting.IRunnable;

public class Class356
implements IRunnable {
    public GuiMove Field2513;

    public Class356(GuiMove guiMove) {
        this.Field2513 = guiMove;
    }

    public void Method631(Boolean bl) {
        if (bl.booleanValue()) {
            if (!GuiMove.Method1835(this.Field2513).contains(Module.mc.gameSettings.keyBindSneak)) {
                GuiMove.Method1835(this.Field2513).add(Module.mc.gameSettings.keyBindSneak);
            }
        } else {
            GuiMove.Method1835(this.Field2513).remove(Module.mc.gameSettings.keyBindSneak);
        }
    }

    public void Method630(Object object) {
        this.Method631((Boolean)object);
    }
}