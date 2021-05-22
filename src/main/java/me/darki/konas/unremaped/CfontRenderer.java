package me.darki.konas.unremaped;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.darki.konas.config.Config;
import me.darki.konas.util.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class CfontRenderer
implements fontHelper {
    public int Field807 = 9;
    public Map<String, Float> Field808 = new HashMap<String, Float>();
    public float Field809;
    public UnicodeFont Field810;
    public int Field811 = new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor();
    public String Field812;
    public float Field813;
    public static File Field814 = new File(Config.KONAS_FOLDER, "fonts");
    public static Font Field815 = null;

    public void Method852() throws IOException, FontFormatException, SlickException {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        this.Field811 = scaledResolution.getScaleFactor();
        this.Field810 = new UnicodeFont(CfontRenderer.Method860("default").deriveFont(this.Field813 * (float)this.Field811 / 2.0f));
        this.Field810.addAsciiGlyphs();
        this.Field810.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        this.Field810.loadGlyphs();
    }

    public UnicodeFont Method853() {
        return this.Field810;
    }

    public String Method854(String string, int n, boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = 0;
        int n3 = bl ? string.length() - 1 : 0;
        int n4 = bl ? -1 : 1;
        boolean bl2 = false;
        boolean bl3 = false;
        for (int i = n3; i >= 0 && i < string.length(); i += n4) {
            if (n2 >= n) break;
            char c = string.charAt(i);
            float f = this.Method862(string);
            if (bl2) {
                bl2 = false;
                if (c != 'l' && c != 'L') {
                    if (c == 'r' || c == 'R') {
                        bl3 = false;
                    }
                } else {
                    bl3 = true;
                }
            } else if (f < 0.0f) {
                bl2 = true;
            } else {
                n2 = (int)((float)n2 + f);
                if (bl3) {
                    ++n2;
                }
            }
            if (n2 > n) break;
            if (bl) {
                stringBuilder.insert(0, c);
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public void Method855(ArrayList<String> arrayList, int n, int n2, int n3) {
        this.Method828(String.join((CharSequence)"\n\r", arrayList), n, n2, n3);
    }

    public void Method856(String string, float f, float f2, int n) {
        this.Method827(StringUtils.stripControlCodes((String)string), f + 0.5f, f2 + 0.5f, n);
        this.Method827(string, f, f2, n);
    }

    public void Method857() throws IOException, FontFormatException, SlickException {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        this.Field811 = scaledResolution.getScaleFactor();
        this.Field810 = new UnicodeFont(CfontRenderer.Method860(this.Field812).deriveFont(this.Field813 * (float)this.Field811 / 2.0f));
        this.Field810.addAsciiGlyphs();
        this.Field810.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        this.Field810.loadGlyphs();
    }

    @Override
    public float Method830(String string) {
        return (float)this.Field810.getWidth(ColorUtil.Method932(string)) / 2.0f / ((float)new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor() / 2.0f);
    }

    @Override
    public int Method826(String string, float f, float f2, int n) {
        if (string == null || string.isEmpty()) {
            return 0;
        }
        this.Method828(StringUtils.stripControlCodes((String)string), f + 0.5f, f2 + 0.5f, 0);
        return this.Method828(string, f, f2, n);
    }

    @Override
    public int Method829() {
        return 9;
    }

    public List<String> Method858(String string, int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String[] stringArray = string.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (String string2 : stringArray) {
            String string3 = stringBuilder + " " + string2;
            if (this.Method862(string3) >= (float)n) {
                arrayList.add(stringBuilder.toString());
                stringBuilder = new StringBuilder();
            }
            stringBuilder.append(string2).append(" ");
        }
        arrayList.add(stringBuilder.toString());
        return arrayList;
    }

    @Override
    public int Method828(String string, float f, float f2, int n) {
        return this.Method867(string, f, f2, n, null);
    }

    public void Method859(String string, int n, int n2, int n3, double d) {
        GL11.glPushMatrix();
        GL11.glTranslated((double)n, (double)n2, (double)0.0);
        GL11.glScaled((double)d, (double)d, (double)d);
        this.Method827(string, 0.0f, 0.0f, n3);
        GL11.glPopMatrix();
    }

    public static Font Method860(String string) throws IOException, FontFormatException {
        if (string.equalsIgnoreCase("geometric")) {
            return CfontRenderer.Method868("/assets/konas/fonts/geometric.ttf");
        }
        if (string.equalsIgnoreCase("verdana") || string.equalsIgnoreCase("default")) {
            return CfontRenderer.Method868("/assets/konas/fonts/verdana.ttf");
        }
        File file = new File(Field814, string + ".ttf");
        if (file.exists()) {
            Field815 = Font.createFont(0, file);
            return Field815;
        }
        return CfontRenderer.Method868("/assets/konas/fonts/verdana.ttf");
    }

    public void Method861(String string, int n, int n2, int n3, double d) {
        GL11.glPushMatrix();
        GL11.glTranslated((double)n, (double)n2, (double)0.0);
        GL11.glScaled((double)d, (double)d, (double)d);
        this.Method828(string, 0.0f, 0.0f, n3);
        GL11.glPopMatrix();
    }

    public float Method862(String string) {
        block0: {
            if (this.Field808.size() <= 1000) break block0;
            this.Field808.clear();
        }
        return this.Field808.computeIfAbsent(string, arg_0 -> this.Method865(string, arg_0)).floatValue();
    }

    public int Method863(String string, float f, float f2, Class255 class255) {
        return this.Method867(string, f, f2, -1, class255);
    }

    public String Method864(String string, int n) {
        return this.Method854(string, n, false);
    }

    @Override
    public float Method831(String string) {
        return this.Method866(string) / ((float)new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor() / 2.0f);
    }

    public Float Method865(String string, String string2) {
        return Float.valueOf((float)this.Field810.getWidth(ColorUtil.Method932(string)) / this.Field809);
    }

    public CfontRenderer(Font font) {
        this(font.getFontName(), font.getSize());
    }

    public CfontRenderer(String string, float f) {
        this.Field812 = string;
        this.Field813 = f;
        try {
            this.Method857();
        }
        catch (FontFormatException | IOException | SlickException exception) {
            try {
                this.Method852();
            }
            catch (Exception exception2) {
                // empty catch block
            }
        }
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        this.Field809 = scaledResolution.getScaleFactor();
    }

    public float Method866(String string) {
        return (float)this.Field810.getHeight(string) / 2.0f;
    }

    @Override
    public int Method827(String string, float f, float f2, int n) {
        return this.Method828(string, f - (float)((int)this.Method862(string) >> 1), f2, n);
    }

    public int Method867(String string, float f, float f2, int n, Class255 class255) {
        ScaledResolution scaledResolution;
        block31: {
            ColorEffect colorEffect;
            UnicodeFont unicodeFont;
            if (string == null) {
                return 0;
            }
            string = string.replaceAll("\u00c2\u00a7", String.valueOf('\u00a7'));
            scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
            ScaledResolution scaledResolution2 = scaledResolution;
            int n2 = scaledResolution2.getScaleFactor();
            if (n2 == this.Field811) break block31;
            CfontRenderer cfontRenderer = this;
            ScaledResolution scaledResolution3 = scaledResolution;
            int n3 = scaledResolution3.getScaleFactor();
            cfontRenderer.Field811 = n3;
            CfontRenderer class5552 = this;
            UnicodeFont unicodeFont2 = unicodeFont;
            UnicodeFont unicodeFont3 = unicodeFont;
            String string2 = this.Field812;
            Font font = CfontRenderer.Method860(string2);
            float f3 = this.Field813 * (float)this.Field811 / 2.0f;
            Font font2 = font.deriveFont(f3);
            unicodeFont2(font2);
            class5552.Field810 = unicodeFont3;
            UnicodeFont unicodeFont4 = this.Field810;
            unicodeFont4.addAsciiGlyphs();
            UnicodeFont unicodeFont5 = this.Field810;
            List list = unicodeFont5.getEffects();
            ColorEffect colorEffect2 = colorEffect;
            ColorEffect colorEffect3 = colorEffect;
            java.awt.Color color = java.awt.Color.WHITE;
            colorEffect2(color);
            list.add(colorEffect3);
            UnicodeFont unicodeFont6 = this.Field810;
            unicodeFont6.loadGlyphs();
            try {
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        this.Field809 = scaledResolution.getScaleFactor();
        GL11.glPushMatrix();
        GlStateManager.scale((float)(1.0f / this.Field809), (float)(1.0f / this.Field809), (float)(1.0f / this.Field809));
        f2 *= this.Field809;
        float f4 = f *= this.Field809;
        float f5 = (float)(n >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(n >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(n & 0xFF) / 255.0f;
        float f8 = (float)(n >> 24 & 0xFF) / 255.0f;
        GlStateManager.color((float)f5, (float)f6, (float)f7, (float)f8);
        int n4 = n;
        char[] cArray = string.toCharArray();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.blendFunc((int)770, (int)771);
        String[] stringArray = ColorUtil.Field867.split(string);
        int n5 = 0;
        for (String string3 : stringArray) {
            char c;
            for (String string4 : string3.split("\n")) {
                for (String string5 : string4.split("\r")) {
                    if (class255 == null) {
                        this.Field810.drawString(f, f2, string5, new Color(n4));
                    } else {
                        this.Field810.drawFillDisplayList(f, f2, string5, class255, 0, string.length());
                    }
                    f += (float)this.Field810.getWidth(string5);
                    if ((n5 += string5.length()) >= cArray.length || cArray[n5] != '\r') continue;
                    f = f4;
                    ++n5;
                }
                if (n5 >= cArray.length || cArray[n5] != '\n') continue;
                f = f4;
                f2 += this.Method866(string4) * 2.0f;
                ++n5;
            }
            if (n5 >= cArray.length || (c = cArray[n5]) != '\u00a7') continue;
            int n6 = cArray[n5 + 1];
            int n7 = "0123456789abcdef".indexOf(n6);
            if (n7 < 0) {
                if (n6 == 114) {
                    n4 = n;
                }
            } else {
                n4 = ColorUtil.Field868[n7];
            }
            n5 += 2;
        }
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.bindTexture((int)0);
        GlStateManager.popMatrix();
        return (int)this.Method862(string);
    }

    public static Font Method868(String string) throws IOException, FontFormatException {
        Field815 = Font.createFont(0, CfontRenderer.class.getResourceAsStream(string));
        return Field815;
    }

    public float Method869(char c) {
        return this.Field810.getWidth(String.valueOf(c));
    }
}