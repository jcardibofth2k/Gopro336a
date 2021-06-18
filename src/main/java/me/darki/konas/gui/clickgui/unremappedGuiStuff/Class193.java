package me.darki.konas.gui.clickgui.unremappedGuiStuff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import me.darki.konas.gui.clickgui.component.Component;
import me.darki.konas.gui.clickgui.frame.CategoryFrame;
import me.darki.konas.gui.clickgui.frame.Frame;
import me.darki.konas.gui.hud.Element;
import me.darki.konas.module.Category;
import me.darki.konas.module.client.KonasGlobals;
import me.darki.konas.unremaped.Class196;
import me.darki.konas.unremaped.Class200;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class Class193
extends GuiScreen {
    public ArrayList<Frame> Field50 = new ArrayList();
    public boolean Field51 = false;
    public static boolean Field52 = false;
    public static boolean Field53 = false;

    public boolean Method108() {
        return this.Field51;
    }

    public void mouseClickMove(int n, int n2, int n3, long l) {
        super.mouseClickMove(n, n2, n3, l);
        this.Method119().forEach(arg_0 -> Class193.Method113(n, n2, n3, l, arg_0));
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public static void Method109(int n, int n2, float f, Frame class90) {
        class90.onRender(n, n2, f);
    }

    public static void Method110(Frame class90) {
        class90.setDragging(false);
    }

    public static boolean Method111() {
        return Field52;
    }

    public static void Method112(char c, int n, Frame class90) {
        class90.onKeyTyped(c, n);
    }

    public static void Method113(int n, int n2, int n3, long l, Frame class90) {
        class90.Method704(n, n2, n3, l);
    }

    public void drawScreen(int n, int n2, float f) {
        super.drawScreen(n, n2, f);
        this.Method119().forEach(arg_0 -> Class193.Method109(n, n2, f, arg_0));
    }

    public static void Method114(Frame class90, Component class183) {
        class183.onMove(class90.getPosX(), class90.Method921() + (float)((Class200)class90).Method708());
    }

    public static void Method115(boolean bl) {
        Field53 = bl;
    }

    public static void Method116(Frame class90) {
        block0: {
            if (class90 instanceof Class88) break block0;
            class90.setExtended(true);
        }
    }

    public static void Method117(int n, int n2, int n3, Frame class90) {
        class90.onMouseReleased(n, n2, n3);
    }

    public void mouseReleased(int n, int n2, int n3) {
        super.mouseReleased(n, n2, n3);
        this.Method119().forEach(arg_0 -> Class193.Method117(n, n2, n3, arg_0));
        for (Frame class90 : this.Method119()) {
            for (Frame class902 : this.Method119()) {
                if (class90 == class902 || class90 instanceof Class88 || class90.getPosX() != class902.getPosX() || class90.Method921() != class902.Method921()) continue;
                class902.setPosX(class902.getPosX() + 10.0f);
                class902.Method442(class902.Method921() + 10.0f);
                if (!(class902 instanceof Class200)) continue;
                ((Class200)class902).Method711().forEach(arg_0 -> Class193.Method114(class902, arg_0));
            }
        }
    }

    public static void Method118(boolean bl) {
        Field52 = bl;
    }

    public void onGuiClosed() {
        this.Method119().forEach(Class193::Method110);
    }

    public ArrayList<Frame> Method119() {
        return this.Field50;
    }

    public void keyTyped(char c, int n) throws IOException {
        if (!Field52) {
            super.keyTyped(c, n);
        }
        this.Method119().forEach(arg_0 -> Class193.Method112(c, n, arg_0));
    }

    public void mouseClicked(int n, int n2, int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        for (int i = this.Method119().size() - 1; i >= 0; --i) {
            Frame class90 = this.Method119().get(i);
            if (!class90.onMouseClicked(n, n2, n3)) continue;
            Collections.swap(this.Method119(), i, this.Method119().size() - 2);
            break;
        }
    }

    public static boolean Method120() {
        return Field53;
    }

    public void Method121() {
        int n = new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() / 2 - 350;
        int n2 = 20;
        for (Category category : Category.Method1764()) {
            this.Method119().add(new Class200(category, (float)n, (float)n2, 95.0f, 14.0f));
            n += 110;
        }
        this.Method119().add(new CategoryFrame(300.0f, n2, 95.0f, 14.0f));
        for (Element element : KonasGlobals.INSTANCE.Field1136.Method2196()) {
            this.Method119().add(new Class88(element));
        }
        this.Method119().add(new Class196());
        this.Method119().forEach(Class193::Method116);
        this.Method119().add(new Class92());
        this.Method119().forEach(Frame::Method712);
        this.Field51 = true;
    }
}