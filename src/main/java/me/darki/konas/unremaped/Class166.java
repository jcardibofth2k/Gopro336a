package me.darki.konas.unremaped;

import java.awt.Color;
import java.io.IOException;
import java.util.LinkedHashSet;
import javax.annotation.Nullable;

import me.darki.konas.settingEnums.AccountLoginScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class Class166
extends GuiScreen {
    public GuiScreen Field1735;
    public String Field1736 = "Select Account";
    public GuiButton Field1737;
    public GuiButton Field1738;
    public LinkedHashSet<Class170> Field1739 = new LinkedHashSet();
    public Class160 Field1740;
    public String[] Field1741 = null;
    public boolean Field1742 = false;

    public void initGui() {
        this.mc = Minecraft.getMinecraft();
        if (this.Field1740 == null) {
            Class160 class160 = this.Field1740 = new Class160(this, this.mc, this.width, this.height, 52, this.height - 64, 36);
            ((Object)((Object)class160)).getClass();
            this.Field1739.forEach(class160::Method1669);
        }
        this.Field1740.Method1671().forEach(this::Method1657);
        this.Field1740.setDimensions(this.width, this.height, 52, this.height - 64);
        this.Field1740.right = (int)((double)this.width - (double)this.width * 0.025);
        this.Field1740.left = (int)((double)this.width * 0.025);
        this.Method1658();
    }

    public void Method1657(Class170 class170) {
        block0: {
            if (!class170.Field1542.Method311() || this.mc.getSession().getProfile().getName().equals(class170.Field1542.Method309())) break block0;
            class170.Field1542.Method308(false);
        }
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.Field1740.handleMouseInput();
    }

    public void Method1658() {
        this.addButton(new GuiButton(0, this.width / 2 + 82, this.height - 28, 72, 20, "Cancel"));
        this.addButton(this.addButton(new GuiButton(1, this.width / 2 - 154, this.height - 52, 150, 20, "Add Account")));
        this.Field1737 = this.addButton(new GuiButton(2, this.width / 2 - 154, this.height - 28, 150, 20, "Delete Account"));
        this.Field1738 = this.addButton(new GuiButton(3, this.width / 2 + 4, this.height - 52, 150, 20, "Login"));
        this.addButton(new GuiButton(5, this.width / 2 + 4, this.height - 28, 72, 20, "Direct"));
        this.Field1738.enabled = false;
        this.Field1737.enabled = false;
    }

    public void Method121() {
        this.Field1742 = Class540.Method1096();
        this.Field1741 = Class509.Method1344();
    }

    public void actionPerformed(GuiButton guiButton) {
        block0: {
            block4: {
                Class170 class170;
                block3: {
                    block2: {
                        block1: {
                            if (!guiButton.enabled) break block0;
                            class170 = this.Field1740.Method1668();
                            if (guiButton.id != 0) break block1;
                            this.mc.displayGuiScreen(this.Field1735);
                            break block0;
                        }
                        if (guiButton.id != 1) break block2;
                        this.mc.displayGuiScreen((GuiScreen)new loginScreen());
                        break block0;
                    }
                    if (guiButton.id != 2) break block3;
                    if (class170 == null) break block0;
                    class170.Method1538();
                    break block0;
                }
                if (guiButton.id != 3) break block4;
                if (class170 == null) break block0;
                Class168 class168 = new Class168(this, null);
                class168.start();
                break block0;
            }
            if (guiButton.id != 5) break block0;
            this.mc.displayGuiScreen((GuiScreen)new AccountLoginScreen(this));
        }
    }

    public Class160 Method1659() {
        return this.Field1740;
    }

    public Class166(GuiScreen guiScreen) {
        this.Field1735 = guiScreen;
    }

    public void mouseClicked(int n, int n2, int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.Field1740.mouseClicked(n, n2, n3);
    }

    public void Method1660(GuiScreen guiScreen) {
        this.Field1735 = guiScreen;
    }

    public void Method1661(@Nullable Class170 class170) {
        boolean bl;
        this.Field1738.enabled = bl = class170 != null;
        this.Field1737.enabled = bl;
    }

    public void drawScreen(int n, int n2, float f) {
        this.drawDefaultBackground();
        this.Field1740.drawScreen(n, n2, f);
        this.drawCenteredString(this.fontRenderer, this.Field1736, this.width / 2, 20, 0xFFFFFF);
        if (this.Field1741 != null && this.Field1741.length != 0) {
            this.drawString(this.mc.fontRenderer, "Account Server: " + Class509.Method1352(this.Field1741[0]).getSecond() + (String)Class509.Method1352(this.Field1741[0]).getFirst(), 2, 2, Color.WHITE.hashCode());
            this.drawString(this.mc.fontRenderer, "Auth Server: " + Class509.Method1352(this.Field1741[1]).getSecond() + (String)Class509.Method1352(this.Field1741[1]).getFirst(), 2, this.mc.fontRenderer.FONT_HEIGHT + 2, Color.WHITE.hashCode());
            this.drawString(this.mc.fontRenderer, "Session Server: " + Class509.Method1352(this.Field1741[2]).getSecond() + (String)Class509.Method1352(this.Field1741[2]).getFirst(), 2, this.mc.fontRenderer.FONT_HEIGHT * 2 + 2, Color.WHITE.hashCode());
        }
        super.drawScreen(n, n2, f);
    }

    public void mouseReleased(int n, int n2, int n3) {
        super.mouseReleased(n, n2, n3);
        this.Field1740.mouseReleased(n, n2, n3);
    }
}