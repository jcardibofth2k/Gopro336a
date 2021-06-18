package me.darki.konas.gui.screen;

import java.util.List;
import me.darki.konas.util.TimerUtil;
import me.darki.konas.mixin.mixins.IGuiDisconnected;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;

public class AutoReconnectScreen
extends GuiScreen {
    public String Field1843;
    public ITextComponent Field1844;
    public List<String> Field1845;
    public GuiScreen Field1846;
    public int Field1847;
    public ServerData Field1848;
    public TimerUtil Field1849;
    public int Field1850;

    public void Method613() {
        this.mc.displayGuiScreen(new GuiConnecting(this.Field1846, this.mc, this.Field1848 == null ? this.mc.getCurrentServerData() : this.Field1848));
    }

    public void initGui() {
        this.buttonList.clear();
        this.Field1845 = this.fontRenderer.listFormattedStringToWidth(this.Field1844.getFormattedText(), this.width - 50);
        this.Field1847 = this.Field1845.size() * this.fontRenderer.FONT_HEIGHT;
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, Math.min(this.height / 2 + this.Field1847 / 2 + this.fontRenderer.FONT_HEIGHT, this.height - 30), I18n.format("gui.toMenu")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, (int)Math.min((double)this.height / 1.85 + (double)this.Field1847 / 1.85 + (double)this.fontRenderer.FONT_HEIGHT, this.height + 80), "Reconnect"));
    }

    public void keyTyped(char c, int n) {
    }

    public void actionPerformed(GuiButton guiButton) {
        switch (guiButton.id) {
            case 0: {
                this.mc.displayGuiScreen(this.Field1846);
                break;
            }
            case 1: {
                this.Method613();
                break;
            }
        }
    }

    public void drawScreen(int n, int n2, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.Field1843, this.width / 2, this.height / 2 - this.Field1847 / 2 - this.fontRenderer.FONT_HEIGHT * 2, 0xAAAAAA);
        int n3 = this.height / 2 - this.Field1847 / 2;
        if (this.Field1845 != null) {
            for (String string : this.Field1845) {
                this.drawCenteredString(this.fontRenderer, string, this.width / 2, n3, 0xFFFFFF);
                n3 += this.fontRenderer.FONT_HEIGHT;
            }
        }
        if (this.Field1849.GetDifferenceTiming(this.Field1850 * 1000)) {
            this.Method613();
        }
        float f2 = (float)this.Field1850 - (float)(System.currentTimeMillis() - this.Field1849.GetCurrentTime()) / 1000.0f;
        this.buttonList.get(1).displayString = "Reconnecting: " + Math.round(f2);
        super.drawScreen(n, n2, f);
    }

    public AutoReconnectScreen(GuiDisconnected guiDisconnected, ServerData serverData, int n) {
        this.Field1846 = ((IGuiDisconnected)guiDisconnected).getParentScreen();
        this.Field1843 = ((IGuiDisconnected)guiDisconnected).getReason();
        this.Field1844 = ((IGuiDisconnected)guiDisconnected).getMessage();
        this.Field1848 = serverData;
        this.Field1849 = new TimerUtil();
        this.Field1850 = n;
    }
}