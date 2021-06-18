package me.darki.konas.gui.screen;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.math.MathHelper;

public class altManagerGuiThing
extends Gui {
    public int Field1821;
    public FontRenderer Field1822;
    public int Field1823;
    public int Field1824;
    public int Field1825;
    public int Field1826;
    public String Field1827 = "";
    public int Field1828 = 256;
    public int Field1829;
    public boolean Field1830 = true;
    public boolean Field1831 = true;
    public boolean Field1832;
    public boolean Field1833 = true;
    public int Field1834;
    public int Field1835;
    public int Field1836;
    public int Field1837 = 0xE0E0E0;
    public int Field1838 = 0x707070;
    public boolean Field1839 = true;
    public GuiPageButtonList.GuiResponder Field1840;
    public Predicate<String> Field1841 = Predicates.alwaysTrue();

    public boolean Method1712() {
        return this.Field1830;
    }

    public void Method1713(int n) {
        this.Method619(this.Field1836 + n);
    }

    public boolean Method1714() {
        return this.Field1832;
    }

    public int Method1715(int n, int n2, boolean bl) {
        int n3 = n2;
        boolean bl2 = n < 0;
        int n4 = Math.abs(n);
        for (int i = 0; i < n4; ++i) {
            if (!bl2) {
                int n5 = this.Field1827.length();
                if ((n3 = this.Field1827.indexOf(32, n3)) == -1) {
                    n3 = n5;
                    continue;
                }
                while (bl && n3 < n5 && this.Field1827.charAt(n3) == ' ') {
                    ++n3;
                }
                continue;
            }
            while (bl && n3 > 0 && this.Field1827.charAt(n3 - 1) == ' ') {
                --n3;
            }
            while (n3 > 0 && this.Field1827.charAt(n3 - 1) != ' ') {
                --n3;
            }
        }
        return n3;
    }

    public void Method1716(int n) {
        this.Field1837 = n;
    }

    public boolean Method1717(int n, int n2, int n3) {
        boolean bl;
        boolean bl2 = bl = n >= this.Field1823 && n < this.Field1823 + this.Field1825 && n2 >= this.Field1824 && n2 < this.Field1824 + this.Field1826;
        if (this.Field1831) {
            this.Method1719(bl);
        }
        if (this.Field1832 && bl && n3 == 0) {
            int n4 = n - this.Field1823;
            if (this.Field1830) {
                n4 -= 4;
            }
            String string = this.Field1822.trimStringToWidth(this.Field1827.substring(this.Field1834), this.Method1734());
            this.Method619(this.Field1822.trimStringToWidth(string, n4).length() + this.Field1834);
            return true;
        }
        return false;
    }

    public void Method1718(String string) {
        block4: {
            int n;
            String string2 = "";
            String string3 = ChatAllowedCharacters.filterAllowedCharacters((String)string);
            int n2 = this.Field1835 < this.Field1836 ? this.Field1835 : this.Field1836;
            int n3 = this.Field1835 < this.Field1836 ? this.Field1836 : this.Field1835;
            int n4 = this.Field1828 - this.Field1827.length() - (n2 - n3);
            if (!this.Field1827.isEmpty()) {
                string2 = string2 + this.Field1827.substring(0, n2);
            }
            if (n4 < string3.length()) {
                string2 = string2 + string3.substring(0, n4);
                n = n4;
            } else {
                string2 = string2 + string3;
                n = string3.length();
            }
            if (!this.Field1827.isEmpty() && n3 < this.Field1827.length()) {
                string2 = string2 + this.Field1827.substring(n3);
            }
            if (!this.Field1841.apply(string2)) break block4;
            this.Field1827 = string2;
            this.Method1713(n2 - this.Field1836 + n);
            this.Method1728(this.Field1821, this.Field1827);
        }
    }

    public void Method1719(boolean bl) {
        block1: {
            if (bl && !this.Field1832) {
                this.Field1829 = 0;
            }
            this.Field1832 = bl;
            if (Minecraft.getMinecraft().currentScreen == null) break block1;
            Minecraft.getMinecraft().currentScreen.setFocused(bl);
        }
    }

    public void Method1720() {
        this.Method619(0);
    }

    public int Method616() {
        return this.Field1828;
    }

    public altManagerGuiThing(int n, FontRenderer fontRenderer, int n2, int n3, int n4, int n5) {
        this.Field1821 = n;
        this.Field1822 = fontRenderer;
        this.Field1823 = n2;
        this.Field1824 = n3;
        this.Field1825 = n4;
        this.Field1826 = n5;
    }

    public int Method1721(int n, int n2) {
        return this.Method1715(n, n2, true);
    }

    public void Method1722() {
        ++this.Field1829;
    }

    public int Method1723(int n) {
        return this.Method1721(n, this.Method1735());
    }

    public void Method1724(String string) {
        block0: {
            if (!this.Field1841.apply(string)) break block0;
            this.Field1827 = string.length() > this.Field1828 ? string.substring(0, this.Field1828) : string;
            this.Method1658();
        }
    }

    public void Method1725(int n) {
        block2: {
            if (this.Field1827.isEmpty()) break block2;
            if (this.Field1836 != this.Field1835) {
                this.Method1718("");
            } else {
                this.Method1739(this.Method1723(n) - this.Field1835);
            }
        }
    }

    public int Method1726() {
        return this.Field1821;
    }

    public void Method1727(int n) {
        this.Field1828 = n;
        if (this.Field1827.length() > n) {
            this.Field1827 = this.Field1827.substring(0, n);
        }
    }

    public void Method115(boolean bl) {
        this.Field1833 = bl;
    }

    public void Method1728(int n, String string) {
        block0: {
            if (this.Field1840 == null) break block0;
            this.Field1840.setEntryValue(n, string);
        }
    }

    public boolean Method1729(char c, int n) {
        if (!this.Field1832) {
            return false;
        }
        if (GuiScreen.isKeyComboCtrlA((int)n)) {
            this.Method1658();
            this.Method1742(0);
            return true;
        }
        if (GuiScreen.isKeyComboCtrlC((int)n)) {
            GuiScreen.setClipboardString((String)this.Method1731());
            return true;
        }
        if (GuiScreen.isKeyComboCtrlV((int)n)) {
            if (this.Field1833) {
                this.Method1718(GuiScreen.getClipboardString());
            }
            return true;
        }
        if (GuiScreen.isKeyComboCtrlX((int)n)) {
            GuiScreen.setClipboardString((String)this.Method1731());
            if (this.Field1833) {
                this.Method1718("");
            }
            return true;
        }
        switch (n) {
            case 14: {
                if (GuiScreen.isCtrlKeyDown()) {
                    if (this.Field1833) {
                        this.Method1725(-1);
                    }
                } else if (this.Field1833) {
                    this.Method1739(-1);
                }
                return true;
            }
            case 199: {
                if (GuiScreen.isShiftKeyDown()) {
                    this.Method1742(0);
                } else {
                    this.Method1720();
                }
                return true;
            }
            case 203: {
                if (GuiScreen.isShiftKeyDown()) {
                    if (GuiScreen.isCtrlKeyDown()) {
                        this.Method1742(this.Method1721(-1, this.Method1738()));
                    } else {
                        this.Method1742(this.Method1738() - 1);
                    }
                } else if (GuiScreen.isCtrlKeyDown()) {
                    this.Method619(this.Method1723(-1));
                } else {
                    this.Method1713(-1);
                }
                return true;
            }
            case 205: {
                if (GuiScreen.isShiftKeyDown()) {
                    if (GuiScreen.isCtrlKeyDown()) {
                        this.Method1742(this.Method1721(1, this.Method1738()));
                    } else {
                        this.Method1742(this.Method1738() + 1);
                    }
                } else if (GuiScreen.isCtrlKeyDown()) {
                    this.Method619(this.Method1723(1));
                } else {
                    this.Method1713(1);
                }
                return true;
            }
            case 207: {
                if (GuiScreen.isShiftKeyDown()) {
                    this.Method1742(this.Field1827.length());
                } else {
                    this.Method1658();
                }
                return true;
            }
            case 211: {
                if (GuiScreen.isCtrlKeyDown()) {
                    if (this.Field1833) {
                        this.Method1725(1);
                    }
                } else if (this.Field1833) {
                    this.Method1739(1);
                }
                return true;
            }
        }
        if (ChatAllowedCharacters.isAllowedCharacter((char)c)) {
            if (this.Field1833) {
                this.Method1718(Character.toString(c));
            }
            return true;
        }
        return false;
    }

    public void Method1730(boolean bl) {
        this.Field1839 = bl;
    }

    public String Method1731() {
        int n = this.Field1835 < this.Field1836 ? this.Field1835 : this.Field1836;
        int n2 = this.Field1835 < this.Field1836 ? this.Field1836 : this.Field1835;
        return this.Field1827.substring(n, n2);
    }

    public String Method1732() {
        return this.Field1827;
    }

    public void Method1733(int n, int n2, int n3, int n4) {
        int n5;
        if (n < n3) {
            n5 = n;
            n = n3;
            n3 = n5;
        }
        if (n2 < n4) {
            n5 = n2;
            n2 = n4;
            n4 = n5;
        }
        if (n3 > this.Field1823 + this.Field1825) {
            n3 = this.Field1823 + this.Field1825;
        }
        if (n > this.Field1823 + this.Field1825) {
            n = this.Field1823 + this.Field1825;
        }
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.color((float)0.0f, (float)0.0f, (float)255.0f, (float)255.0f);
        GlStateManager.disableTexture2D();
        GlStateManager.enableColorLogic();
        GlStateManager.colorLogicOp((GlStateManager.LogicOp)GlStateManager.LogicOp.OR_REVERSE);
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferBuilder.pos((double)n, (double)n4, 0.0).endVertex();
        bufferBuilder.pos((double)n3, (double)n4, 0.0).endVertex();
        bufferBuilder.pos((double)n3, (double)n2, 0.0).endVertex();
        bufferBuilder.pos((double)n, (double)n2, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.disableColorLogic();
        GlStateManager.enableTexture2D();
    }

    public int Method1734() {
        return this.Method1712() ? this.Field1825 - 8 : this.Field1825;
    }

    public int Method1735() {
        return this.Field1835;
    }

    public void Method1736(GuiPageButtonList.GuiResponder guiResponder) {
        this.Field1840 = guiResponder;
    }

    public void Method1737(Predicate<String> predicate) {
        this.Field1841 = predicate;
    }

    public int Method1738() {
        return this.Field1836;
    }

    public void Method619(int n) {
        this.Field1835 = n;
        int n2 = this.Field1827.length();
        this.Field1835 = MathHelper.clamp((int)this.Field1835, (int)0, (int)n2);
        this.Method1742(this.Field1835);
    }

    public void Method1739(int n) {
        block3: {
            block4: {
                if (this.Field1827.isEmpty()) break block3;
                if (this.Field1836 == this.Field1835) break block4;
                this.Method1718("");
                break block3;
            }
            boolean bl = n < 0;
            int n2 = bl ? this.Field1835 + n : this.Field1835;
            int n3 = bl ? this.Field1835 : this.Field1835 + n;
            String string = "";
            if (n2 >= 0) {
                string = this.Field1827.substring(0, n2);
            }
            if (n3 < this.Field1827.length()) {
                string = string + this.Field1827.substring(n3);
            }
            if (!this.Field1841.apply(string)) break block3;
            this.Field1827 = string;
            if (bl) {
                this.Method1713(n);
            }
            this.Method1728(this.Field1821, this.Field1827);
        }
    }

    public void Method1740(boolean bl) {
        this.Field1830 = bl;
    }

    public void Method613() {
        block11: {
            if (!this.Method1741()) break block11;
            if (this.Method1712()) {
                altManagerGuiThing.drawRect((int)(this.Field1823 - 1), (int)(this.Field1824 - 1), (int)(this.Field1823 + this.Field1825 + 1), (int)(this.Field1824 + this.Field1826 + 1), (int)-6250336);
                altManagerGuiThing.drawRect((int)this.Field1823, (int)this.Field1824, (int)(this.Field1823 + this.Field1825), (int)(this.Field1824 + this.Field1826), (int)-16777216);
            }
            int n = this.Field1833 ? this.Field1837 : this.Field1838;
            int n2 = this.Field1835 - this.Field1834;
            int n3 = this.Field1836 - this.Field1834;
            String string = this.Field1822.trimStringToWidth(this.Field1827.substring(this.Field1834), this.Method1734());
            boolean bl = n2 >= 0 && n2 <= string.length();
            boolean bl2 = this.Field1832 && this.Field1829 / 6 % 2 == 0 && bl;
            int n4 = this.Field1830 ? this.Field1823 + 4 : this.Field1823;
            int n5 = this.Field1830 ? this.Field1824 + (this.Field1826 - 8) / 2 : this.Field1824;
            int n6 = n4;
            if (n3 > string.length()) {
                n3 = string.length();
            }
            if (!string.isEmpty()) {
                String string2 = bl ? string.substring(0, n2) : string;
                n6 = this.Field1822.drawStringWithShadow(string2.replaceAll(".", "*"), (float)n4, (float)n5, n);
            }
            boolean bl3 = this.Field1835 < this.Field1827.length() || this.Field1827.length() >= this.Method616();
            int n7 = n6;
            if (!bl) {
                n7 = n2 > 0 ? n4 + this.Field1825 : n4;
            } else if (bl3) {
                n7 = n6 - 1;
                --n6;
            }
            if (!string.isEmpty() && bl && n2 < string.length()) {
                n6 = this.Field1822.drawStringWithShadow(string.replaceAll(".", "*").substring(n2), (float)n6, (float)n5, n);
            }
            if (bl2) {
                if (bl3) {
                    Gui.drawRect((int)n7, (int)(n5 - 1), (int)(n7 + 1), (int)(n5 + 1 + this.Field1822.FONT_HEIGHT), (int)-3092272);
                } else {
                    this.Field1822.drawStringWithShadow("_", (float)n7, (float)n5, n);
                }
            }
            if (n3 != n2) {
                int n8 = n4 + this.Field1822.getStringWidth(string.replaceAll(".", "*").substring(0, n3));
                this.Method1733(n7, n5 - 1, n8 - 1, n5 + 1 + this.Field1822.FONT_HEIGHT);
            }
        }
    }

    public boolean Method1741() {
        return this.Field1839;
    }

    public void Method1742(int n) {
        int n2 = this.Field1827.length();
        if (n > n2) {
            n = n2;
        }
        if (n < 0) {
            n = 0;
        }
        this.Field1836 = n;
        if (this.Field1822 != null) {
            if (this.Field1834 > n2) {
                this.Field1834 = n2;
            }
            int n3 = this.Method1734();
            String string = this.Field1822.trimStringToWidth(this.Field1827.substring(this.Field1834), n3);
            int n4 = string.length() + this.Field1834;
            if (n == this.Field1834) {
                this.Field1834 -= this.Field1822.trimStringToWidth(this.Field1827, n3, true).length();
            }
            if (n > n4) {
                this.Field1834 += n - n4;
            } else if (n <= this.Field1834) {
                this.Field1834 -= this.Field1834 - n;
            }
            this.Field1834 = MathHelper.clamp((int)this.Field1834, (int)0, (int)n2);
        }
    }

    public void Method118(boolean bl) {
        this.Field1831 = bl;
    }

    public void Method1743(int n) {
        this.Field1838 = n;
    }

    public void Method1658() {
        this.Method619(this.Field1827.length());
    }
}