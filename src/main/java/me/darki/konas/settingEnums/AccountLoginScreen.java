package me.darki.konas.settingEnums;

import java.io.IOException;
import me.darki.konas.gui.screen.altManagerGuiThing;
import me.darki.konas.unremaped.Class166;
import me.darki.konas.unremaped.Class170;
import me.darki.konas.unremaped.Class68;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

public class AccountLoginScreen
extends GuiScreen {
    public Class166 Field1658;
    public GuiTextField Field1659;
    public altManagerGuiThing Field1660;
    public boolean Field1661;

    public void mouseClicked(int n, int n2, int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.Field1659.mouseClicked(n, n2, n3);
        this.Field1660.Method1717(n, n2, n3);
    }

    public void keyTyped(char c, int n) {
        block3: {
            block2: {
                if (!this.Field1659.isFocused()) break block2;
                this.Field1659.textboxKeyTyped(c, n);
                if (n == 28 || n == 156) {
                    this.actionPerformed(this.buttonList.get(0));
                }
                if (n != 15) break block3;
                this.Field1660.Method1719(true);
                this.Field1659.setFocused(false);
                break block3;
            }
            if (!this.Field1660.Method1714()) break block3;
            this.Field1660.Method1729(c, n);
            if (n == 28 || n == 156) {
                this.actionPerformed(this.buttonList.get(0));
            }
        }
    }

    public void updateScreen() {
        this.Field1659.updateCursorCounter();
        this.Field1660.Method1722();
    }

    public void drawScreen(int n, int n2, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "Direct Login", this.width / 2, 17, 0xFFFFFF);
        this.drawString(this.fontRenderer, "Username", this.width / 2 - 100, 53, 0xA0A0A0);
        this.drawString(this.fontRenderer, "Password", this.width / 2 - 100, 94, 0xA0A0A0);
        if (this.Field1661) {
            this.drawCenteredString(this.fontRenderer, TextFormatting.DARK_RED + "Invalid Credentials", this.width / 2, this.height / 4 + 96, 0xFFFFFF);
        }
        this.Field1659.drawTextBox();
        this.Field1660.Method613();
        super.drawScreen(n, n2, f);
    }

    public AccountLoginScreen(Class166 class166) {
        this.Field1658 = class166;
    }

    public void actionPerformed(GuiButton guiButton) {
        block4: {
            block5: {
                if (!guiButton.enabled) break block4;
                if (guiButton.id != 1) break block5;
                this.mc.displayGuiScreen(this.Field1658);
                break block4;
            }
            if (guiButton.id != 0) break block4;
            if (this.Field1659.getText().isEmpty()) {
                return;
            }
            if (this.Field1660.Method1732().isEmpty()) {
                Class68 class68 = new Class68(this.Field1659.getText().trim(), this.Field1660.Method1732().trim(), false, false);
                Class170 class170 = new Class170(class68);
                class170.Method1540();
            } else {
                Class68 class68 = new Class68(this.Field1659.getText().trim(), this.Field1660.Method1732().trim(), true, false);
                Class170 class170 = new Class170(class68);
                if (!class170.Method1545()) {
                    this.Field1661 = true;
                    return;
                }
                class170.Method1542();
            }
            this.mc.displayGuiScreen(this.Field1658);
        }
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 18, "Login"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 18, "Cancel"));
        this.Field1659 = new GuiTextField(2, this.fontRenderer, this.width / 2 - 100, 66, 200, 20);
        this.Field1660 = new altManagerGuiThing(3, this.fontRenderer, this.width / 2 - 100, 106, 200, 20);
        this.Field1659.setFocused(true);
    }
}