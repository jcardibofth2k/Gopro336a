package me.darki.konas.settingEnums;

public class SecretShaderMode
extends Enum {
    public static SecretShaderMode ANTIALIAS = new SecretShaderMode("ANTIALIAS", 0);
    public static SecretShaderMode ART = new SecretShaderMode("ART", 1);
    public static SecretShaderMode BITS = new SecretShaderMode("BITS", 2);
    public static SecretShaderMode BLOBS = new SecretShaderMode("BLOBS", 3);
    public static SecretShaderMode BLOBS2 = new SecretShaderMode("BLOBS2", 4);
    public static SecretShaderMode BLUR = new SecretShaderMode("BLUR", 5);
    public static SecretShaderMode BUMPY = new SecretShaderMode("BUMPY", 6);
    public static SecretShaderMode COLOR_CONVOLVE = new SecretShaderMode("COLOR_CONVOLVE", 7);
    public static SecretShaderMode CREEPER = new SecretShaderMode("CREEPER", 8);
    public static SecretShaderMode DECONVERGE = new SecretShaderMode("DECONVERGE", 9);
    public static SecretShaderMode DESATURATE = new SecretShaderMode("DESATURATE", 10);
    public static SecretShaderMode ENTITY_OUTLINE = new SecretShaderMode("ENTITY_OUTLINE", 11);
    public static SecretShaderMode FLIP = new SecretShaderMode("FLIP", 12);
    public static SecretShaderMode FXAA = new SecretShaderMode("FXAA", 13);
    public static SecretShaderMode GREEN = new SecretShaderMode("GREEN", 14);
    public static SecretShaderMode INVERT = new SecretShaderMode("INVERT", 15);
    public static SecretShaderMode NOTCH = new SecretShaderMode("NOTCH", 16);
    public static SecretShaderMode NTSC = new SecretShaderMode("NTSC", 17);
    public static SecretShaderMode OUTLINE = new SecretShaderMode("OUTLINE", 18);
    public static SecretShaderMode PENCIL = new SecretShaderMode("PENCIL", 19);
    public static SecretShaderMode PHOSPHOR = new SecretShaderMode("PHOSPHOR", 20);
    public static SecretShaderMode SCAN_PINCUSION = new SecretShaderMode("SCAN_PINCUSION", 21);
    public static SecretShaderMode SOBEL = new SecretShaderMode("SOBEL", 22);
    public static SecretShaderMode SPIDER = new SecretShaderMode("SPIDER", 23);
    public static SecretShaderMode WOBBLE = new SecretShaderMode("WOBBLE", 24);
    public static SecretShaderMode[] Field431;

    public static SecretShaderMode[] Method548() {
        return Field431.clone();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public SecretShaderMode() {
        void var2_-1;
        void var1_-1;
    }

    public static SecretShaderMode Method549(String string) {
        return Enum.valueOf(SecretShaderMode.class, string);
    }

    static {
        Field431 = new SecretShaderMode[]{ANTIALIAS, ART, BITS, BLOBS, BLOBS2, BLUR, BUMPY, COLOR_CONVOLVE, CREEPER, DECONVERGE, DESATURATE, ENTITY_OUTLINE, FLIP, FXAA, GREEN, INVERT, NOTCH, NTSC, OUTLINE, PENCIL, PHOSPHOR, SCAN_PINCUSION, SOBEL, SPIDER, WOBBLE};
    }
}
