package me.darki.konas.unremaped;

import me.darki.konas.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

import me.darki.konas.mixin.mixins.IMinecraft;
import me.darki.konas.module.client.KonasGlobals;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Class170
implements GuiListExtended.IGuiListEntry {
    public static Logger Field1538 = LogManager.getLogger();
    public static ResourceLocation Field1539 = new ResourceLocation("konas/textures/steve.png");
    public Minecraft Field1540;
    public Class166 Field1541;
    public Class68 Field1542;
    public DynamicTexture Field1543;
    public ResourceLocation Field1544;
    public long Field1545;
    public BufferedImage Field1546 = null;

    public void Method1538() {
        this.Field1540.displayGuiScreen((GuiScreen)new GuiYesNo(this::Method1539, "Are you sure you want to delete '" + this.Field1542.Method309() + "'?", "This process cannot be reverted.", "Delete", "Cancel", 0));
    }

    public void Method1539(boolean bl, int n) {
        if (bl) {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiScreenWorking());
            KonasGlobals.INSTANCE.Field1132.Method1659().Method1673(this);
        }
        this.Field1540.displayGuiScreen((GuiScreen)this.Field1541);
    }

    public void Method1540() {
        this.Field1541.Method1659().Method1671().forEach(Class170::Method1541);
        this.Field1542.Method313(true);
        this.Field1542.Method308(true);
        this.Field1542.Method318(System.currentTimeMillis());
    }

    public void updatePosition(int n, int n2, int n3, float f) {
    }

    public static void Method1541(Class170 class170) {
        class170.Field1542.Method308(false);
    }

    public void Method1542() {
        this.Field1541.Method1659().Method1671().forEach(Class170::Method1544);
        this.Field1542.Method313(false);
        this.Field1542.Method308(true);
        this.Field1542.Method318(System.currentTimeMillis());
        this.Field1542.Method304(Minecraft.getMinecraft().getSession().getUsername());
    }

    public void Method1543() {
        boolean bl;
        if (this.Field1542.Method311()) {
            return;
        }
        this.Field1540.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
        File file = new File(this.Field1540.mcDataDir + File.separator + "exploit.txt");
        if (this.Field1542.Method302()) {
            ((IMinecraft)Minecraft.getMinecraft()).setSession(new Session(this.Field1542.Method309(), this.Field1542.Method305(), this.Field1542.Method312(), "mojang"));
            bl = !Class540.Method1096() ? Class540.Method1093(this.Field1542, this.Field1542.Method303(), this.Field1542.Method317()) : true;
        } else if (!file.exists() || this.Field1542.Method312().isEmpty() || this.Field1542.Method305().isEmpty()) {
            bl = Class540.Method1097(this.Field1542, this.Field1542.Method303(), this.Field1542.Method317());
        } else {
            bl = true;
            ((IMinecraft)Minecraft.getMinecraft()).setSession(new Session(this.Field1542.Method309(), this.Field1542.Method305(), this.Field1542.Method312(), "mojang"));
        }
        if (bl) {
            this.Method1542();
        } else if (Class540.Method1094(this.Field1542.Method309())) {
            this.Method1540();
        }
        this.Field1541.Field1742 = Class540.Method1096();
    }

    public Class170(Class68 class68) {
        this.Field1541 = KonasGlobals.INSTANCE.Field1132;
        this.Field1542 = class68;
        this.Field1540 = Minecraft.getMinecraft();
        if (!this.Field1542.Method315()) {
            new Thread(this::Method1546).start();
        }
    }

    public static void Method1544(Class170 class170) {
        class170.Field1542.Method308(false);
    }

    public boolean Method1545() {
        return Class540.Method1097(this.Field1542, this.Field1542.Method303(), this.Field1542.Method317());
    }

    public void Method1546() {
        String string = Class509.Method1349(this.Field1542.Method309());
        if (string == null) {
            this.Field1542.Method313(true);
            return;
        }
        string = string.replaceAll("-", "");
        this.Field1542.Method306(string);
        Class170 class170 = this;
        String string2 = string;
        InputStream inputStream = Class509.Method1348(string2);
        //fixed by Gopro336
        try {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            class170.Field1546 = bufferedImage;
        }catch (IOException ignored){}

        BufferedImage bufferedImage2 = this.Field1546;
        int n = bufferedImage2.getWidth();
        boolean bl = n == 64;
        String string3 = "Must be 64 pixels wide";
        Object[] objectArray = new Object[]{};
        Validate.validState(bl, string3, objectArray);
        BufferedImage bufferedImage3 = this.Field1546;
        int n2 = bufferedImage3.getHeight();
        boolean bl2 = n2 == 64;
        String string4 = "Must be 64 pixels high";
        Object[] objectArray2 = new Object[]{};
        try {
            Validate.validState(bl2, string4, objectArray2);
        }
        catch (Throwable throwable) {
            System.err.println("Couldn't load face");
        }
    }

    public boolean mousePressed(int n, int n2, int n3, int n4, int n5, int n6) {
        KonasGlobals.INSTANCE.Field1132.Method1659().Method1666(n);
        if (n5 <= 32 && n5 < 32) {
            this.Method1543();
            return true;
        }
        if (Minecraft.getSystemTime() - this.Field1545 < 250L) {
            this.Method1543();
            return true;
        }
        this.Field1545 = Minecraft.getSystemTime();
        return false;
    }

    public void Method1547() {
    }

    public boolean Method1548() {
        return Class540.Method1093(this.Field1542, this.Field1542.Method303(), this.Field1542.Method317());
    }

    public void mouseReleased(int n, int n2, int n3, int n4, int n5, int n6) {
    }

    public void drawEntry(int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
        String string = this.Field1542.Method311() ? TextFormatting.GREEN + this.Field1542.Method309() : this.Field1542.Method309();
        String string2 = this.Field1542.Method303();
        String string3 = this.Field1542.Method315() ? TextFormatting.RED + "Cracked" : TextFormatting.GREEN + "Premium";
        this.Field1540.fontRenderer.drawString(string, n2 + 32 + 3, n3 + 1, 0xFFFFFF);
        if (!this.Field1542.Method315()) {
            this.Field1540.fontRenderer.drawString(string2, n2 + 32 + 3, n3 + this.Field1540.fontRenderer.FONT_HEIGHT + 2, 0x808080);
            this.Field1540.fontRenderer.drawString(string3, n2 + 32 + 3, n3 + this.Field1540.fontRenderer.FONT_HEIGHT + this.Field1540.fontRenderer.FONT_HEIGHT + 3, 0x808080);
        } else {
            this.Field1540.fontRenderer.drawString(string3, n2 + 32 + 3, n3 + this.Field1540.fontRenderer.FONT_HEIGHT + 3, 0x808080);
        }
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        if (this.Field1546 != null) {
            if (this.Field1543 == null) {
                this.Field1543 = new DynamicTexture(this.Field1546);
                this.Field1544 = this.Field1540.getTextureManager().getDynamicTextureLocation(this.Field1542.Method305(), this.Field1543);
                this.Field1540.getTextureManager().loadTexture(this.Field1544, (ITextureObject)this.Field1543);
                this.Field1546.getRGB(0, 0, this.Field1546.getWidth(), this.Field1546.getHeight(), this.Field1543.getTextureData(), 0, this.Field1546.getWidth());
                this.Field1543.updateDynamicTexture();
            }
        } else if (!this.Field1542.Method315()) {
            new Thread(this::Method1546).start();
        }
        this.Field1540.getTextureManager().bindTexture(this.Field1543 != null ? this.Field1544 : Field1539);
        GlStateManager.enableBlend();
        Gui.drawModalRectWithCustomSizedTexture((int)n2, (int)n3, (float)0.0f, (float)0.0f, (int)32, (int)32, (float)32.0f, (float)32.0f);
        GlStateManager.disableBlend();
    }
}