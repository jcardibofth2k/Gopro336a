package me.darki.konas.gui.clickgui.unremappedGuiStuff;

import java.io.IOException;

import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class215;
import me.darki.konas.gui.clickgui.unremappedGuiStuff.Class230;
import me.darki.konas.module.client.KonasGui;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import org.lwjgl.input.Mouse;

public class Class147
extends GuiScreen {
    public Class230 Field2000;
    public static boolean Field2001 = false;

    public void Method613() {
        this.Field2000 = new Class230((Boolean) KonasGui.Field109.getValue() != false ? 488.0f : 800.0f, ((Integer) KonasGui.Field108.getValue()).intValue());
    }

    public void mouseClickMove(int n, int n2, int n3, long l) {
        super.mouseClickMove(n, n2, n3, l);
        this.Field2000.Method491(n, n2, n3, l);
    }

    public void mouseClicked(int n, int n2, int n3) throws IOException {
        block0: {
            super.mouseClicked(n, n2, n3);
            if (!this.Field2000.Method494(n, n2, n3)) break block0;
            this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
        }
    }

    public Class230 Method1828() {
        return this.Field2000;
    }

    public void mouseReleased(int n, int n2, int n3) {
        super.mouseReleased(n, n2, n3);
        this.Field2000.Method481(n, n2, n3);
    }

    public void initGui() {
        super.initGui();
        this.Field2000.Method485(0.0f, 0.0f);
    }

    public void keyTyped(char c, int n) throws IOException {
        this.Field2000.Method479(c, n);
        if (Field2001) {
            Field2001 = false;
            return;
        }
        super.keyTyped(c, n);
    }

    public void drawScreen(int n, int n2, float f) {
        super.drawScreen(n, n2, f);
        if (((Boolean) KonasGui.Field107.getValue()).booleanValue()) {
            KonasGui.Field107.setValue(Boolean.valueOf(false));
            this.Method613();
            this.Field2000.Method485(0.0f, 0.0f);
        }
        Class215.Method480(Mouse.getDWheel());
        this.Field2000.Method497(n, n2, f);
        this.Field2000.Method477(n, n2, f);
    }
}