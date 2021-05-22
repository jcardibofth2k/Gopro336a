package me.darki.konas.unremaped;

import net.minecraft.client.Minecraft;

public class FontUtil
implements fontHelper {
    public static FontUtil Field779 = new FontUtil();

    @Override
    public int Method826(String string, float f, float f2, int n) {
        string = string.replaceAll("\u00c2\u00a7", String.valueOf('\u00a7'));
        return Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(string, f, f2, n);
    }

    @Override
    public int Method827(String string, float f, float f2, int n) {
        string = string.replaceAll("\u00c2\u00a7", String.valueOf('\u00a7'));
        return Minecraft.getMinecraft().fontRenderer.drawString(string, f - (float)Minecraft.getMinecraft().fontRenderer.getStringWidth(string) / 2.0f, f2, n, false);
    }

    @Override
    public int Method828(String string, float f, float f2, int n) {
        string = string.replaceAll("\u00c2\u00a7", String.valueOf('\u00a7'));
        return Minecraft.getMinecraft().fontRenderer.drawString(string, f, f2, n, false);
    }

    @Override
    public int Method829() {
        return Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
    }

    @Override
    public float Method830(String string) {
        return Minecraft.getMinecraft().fontRenderer.getStringWidth(string);
    }

    @Override
    public float Method831(String string) {
        return Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
    }
}