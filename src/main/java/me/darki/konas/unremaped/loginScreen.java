package me.darki.konas.unremaped;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import me.darki.konas.gui.screen.altManagerGuiThing;
import me.darki.konas.mixin.mixins.IGuiTextField;
import me.darki.konas.module.client.KonasGlobals;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

public class loginScreen
extends GuiScreen {
    public GuiTextField Field1965;
    public altManagerGuiThing Field1966;
    public boolean Field1967;
    public boolean Field1968;

    public void updateScreen() {
        this.Field1965.updateCursorCounter();
        this.Field1966.Method1722();
    }

    public void actionPerformed(GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == 1) {
                this.mc.displayGuiScreen((GuiScreen) KonasGlobals.INSTANCE.Field1132);
            } else if (guiButton.id == 0) {
                Class170 class170;
                if (this.Field1965.getText().isEmpty()) {
                    return;
                }
                if (this.Field1966.Method1732().isEmpty()) {
                    Class68 class68 = new Class68(this.Field1965.getText().trim(), this.Field1966.Method1732().trim(), false, false);
                    class170 = new Class170(class68);
                    class170.Method1540();
                } else {
                    Class68 class68 = new Class68(this.Field1965.getText().trim(), this.Field1966.Method1732().trim(), true, false);
                    class170 = new Class170(class68);
                    if (!class170.Method1545()) {
                        this.Field1967 = true;
                        return;
                    }
                    class170.Method1542();
                }
                KonasGlobals.INSTANCE.Field1132.Method1659().Method1669(class170);
                this.mc.displayGuiScreen((GuiScreen) KonasGlobals.INSTANCE.Field1132);
            } else if (guiButton.id == 3) {
                if (this.Field1965.getText().isEmpty()) {
                    return;
                }
                if (this.Field1966.Method1732().isEmpty()) {
                    this.Field1967 = true;
                } else {
                    Class68 class68 = new Class68(this.Field1965.getText().trim(), this.Field1966.Method1732().trim(), true, true);
                    Class170 class170 = new Class170(class68);
                    if (!class170.Method1548()) {
                        this.Field1967 = true;
                        this.Field1968 = true;
                        return;
                    }
                    class170.Method1542();
                    KonasGlobals.INSTANCE.Field1132.Method1659().Method1669(class170);
                    this.mc.displayGuiScreen((GuiScreen) KonasGlobals.INSTANCE.Field1132);
                }
            } else if (guiButton.id == 5 && Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                URI uRI;
                Desktop desktop = Desktop.getDesktop();
                URI uRI2 = uRI;
                URI uRI3 = uRI;
                String string = "https://login.live.com/oauth20_authorize.srf?redirect_uri=https://login.live.com/oauth20_desktop.srf&scope=service::user.auth.xboxlive.com::MBI_SSL&display=touch&response_type=code&locale=en&client_id=00000000402b5328";
                uRI2(string);
                try {
                    desktop.browse(uRI3);
                }
                catch (IOException | URISyntaxException exception) {
                    // empty catch block
                }
            }
        }
    }

    public void drawScreen(int n, int n2, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "Add Account", this.width / 2, 17, 0xFFFFFF);
        this.drawString(this.fontRenderer, "Username", this.width / 2 - 100, 53, 0xA0A0A0);
        this.drawString(this.fontRenderer, "Password", this.width / 2 - 100, 94, 0xA0A0A0);
        if (this.Field1967) {
            this.drawCenteredString(this.fontRenderer, TextFormatting.DARK_RED + "Invalid Credentials", this.width / 2, this.height / 4 + 96, 0xFFFFFF);
        }
        if (this.Field1968) {
            ((GuiButton)this.buttonList.get((int)3)).visible = true;
        }
        this.Field1965.drawTextBox();
        this.Field1966.Method613();
        super.drawScreen(n, n2, f);
    }

    public void keyTyped(char c, int n) {
        block3: {
            block2: {
                if (!this.Field1965.isFocused()) break block2;
                this.Field1965.textboxKeyTyped(c, n);
                if (n == 28 || n == 156) {
                    this.actionPerformed((GuiButton)this.buttonList.get(0));
                }
                if (n != 15) break block3;
                this.Field1966.Method1719(true);
                this.Field1965.setFocused(false);
                break block3;
            }
            if (!this.Field1966.Method1714()) break block3;
            this.Field1966.Method1729(c, n);
            if (n == 28 || n == 156) {
                this.actionPerformed((GuiButton)this.buttonList.get(0));
            }
        }
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 18, "Add"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 120 + 18, "Add through Microsoft"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 144 + 18, "Cancel"));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 168 + 18, "Login through browser"));
        ((GuiButton)this.buttonList.get((int)3)).visible = false;
        this.Field1965 = new GuiTextField(2, this.fontRenderer, this.width / 2 - 100, 66, 200, 20);
        ((IGuiTextField)this.Field1965).setMaxStringLength(320);
        this.Field1966 = new altManagerGuiThing(3, this.fontRenderer, this.width / 2 - 100, 106, 200, 20);
        this.Field1965.setFocused(true);
    }

    public void mouseClicked(int n, int n2, int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.Field1965.mouseClicked(n, n2, n3);
        this.Field1966.Method1717(n, n2, n3);
    }
}