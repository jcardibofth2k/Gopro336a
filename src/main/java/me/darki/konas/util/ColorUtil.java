package me.darki.konas.util;

import java.util.regex.Pattern;

public class ColorUtil
extends Enum {
    public static ColorUtil BLACK;
    public static ColorUtil DARK_BLUE;
    public static ColorUtil DARK_GREEN;
    public static ColorUtil DARK_AQUA;
    public static ColorUtil DARK_RED;
    public static ColorUtil DARK_PURPLE;
    public static ColorUtil GOLD;
    public static ColorUtil GRAY;
    public static ColorUtil DARK_GRAY;
    public static ColorUtil BLUE;
    public static ColorUtil GREEN;
    public static ColorUtil AQUA;
    public static ColorUtil RED;
    public static ColorUtil LIGHT_PURPLE;
    public static ColorUtil YELLOW;
    public static ColorUtil WHITE;
    public static ColorUtil MAGIC;
    public static ColorUtil BOLD;
    public static ColorUtil STRIKETHROUGH;
    public static ColorUtil UNDERLINE;
    public static ColorUtil ITALIC;
    public static ColorUtil RESET;
    public static ColorUtil[] Field872;
    public static char Field866;
    public static Pattern Field867;
    public static int[] Field868;
    public char Field869;
    public boolean Field870;
    public String Field871;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public ColorUtil() {
        this(var1_ -1, (int)var2_-1, (char)var3_1, false);
        void var3_1;
        void var2_-1;
        void var1_-1;
    }

    /*
     * WARNING - void declaration
     */
    public ColorUtil() {
        void var4_2;
        void var3_1;
        void var2_-1;
        void var1_-1;
        this.Field869 = var3_1;
        this.Field870 = var4_2;
        this.Field871 = new String(new char[]{'\u00a7', var3_1});
    }

    public boolean Method930() {
        return this.Field870;
    }

    public static ColorUtil[] Method931() {
        return Field872.clone();
    }

    public static String Method932(String string) {
        return string == null ? null : Pattern.compile("(?i)\u00a7[0-9A-FK-OR]").matcher(string).replaceAll("");
    }

    public char Method933() {
        return this.Field869;
    }

    static {
        Field866 = (char)167;
        BLACK = new ColorUtil("BLACK", 0, '0');
        DARK_BLUE = new ColorUtil("DARK_BLUE", 1, '1');
        DARK_GREEN = new ColorUtil("DARK_GREEN", 2, '2');
        DARK_AQUA = new ColorUtil("DARK_AQUA", 3, '3');
        DARK_RED = new ColorUtil("DARK_RED", 4, '4');
        DARK_PURPLE = new ColorUtil("DARK_PURPLE", 5, '5');
        GOLD = new ColorUtil("GOLD", 6, '6');
        GRAY = new ColorUtil("GRAY", 7, '7');
        DARK_GRAY = new ColorUtil("DARK_GRAY", 8, '8');
        BLUE = new ColorUtil("BLUE", 9, '9');
        GREEN = new ColorUtil("GREEN", 10, 'a');
        AQUA = new ColorUtil("AQUA", 11, 'b');
        RED = new ColorUtil("RED", 12, 'c');
        LIGHT_PURPLE = new ColorUtil("LIGHT_PURPLE", 13, 'd');
        YELLOW = new ColorUtil("YELLOW", 14, 'e');
        WHITE = new ColorUtil("WHITE", 15, 'f');
        MAGIC = new ColorUtil("MAGIC", 16, 'k', true);
        BOLD = new ColorUtil("BOLD", 17, 'l', true);
        STRIKETHROUGH = new ColorUtil("STRIKETHROUGH", 18, 'm', true);
        UNDERLINE = new ColorUtil("UNDERLINE", 19, 'n', true);
        ITALIC = new ColorUtil("ITALIC", 20, 'o', true);
        RESET = new ColorUtil("RESET", 21, 'r');
        Field872 = new ColorUtil[]{BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE, MAGIC, BOLD, STRIKETHROUGH, UNDERLINE, ITALIC, RESET};
        Field867 = Pattern.compile("\u00a7[0123456789abcdefklmnor]");
        Field868 = new int[]{0, 170, 43520, 43690, 0xAA0000, 0xAA00AA, 0xFFAA00, 0xAAAAAA, 0x555555, 0x5555FF, 0x55FF55, 0x55FFFF, 0xFF5555, 0xFF55FF, 0xFFFF55, 0xFFFFFF};
    }

    public static ColorUtil Method934(String string) {
        return Enum.valueOf(ColorUtil.class, string);
    }

    public static String Method935(char c, String string) {
        char[] cArray = string.toCharArray();
        int n = cArray.length - 1;
        for (int i = 0; i < n; ++i) {
            if (cArray[i] != c || "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(cArray[i + 1]) <= -1) continue;
            cArray[i] = 167;
            cArray[i + 1] = Character.toLowerCase(cArray[i + 1]);
        }
        return new String(cArray);
    }

    public String toString() {
        return this.Field871;
    }

    public boolean Method311() {
        return !this.Field870 && this != RESET;
    }
}