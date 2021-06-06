package me.darki.konas.settingEnums;

public enum SecretShaderMode {
    ANTIALIAS("ANTIALIAS", 0),
    ART("ART", 1),
    BITS("BITS", 2),
    BLOBS("BLOBS", 3),
    BLOBS2("BLOBS2", 4),
    BLUR("BLUR", 5),
    BUMPY("BUMPY", 6),
    COLOR_CONVOLVE("COLOR_CONVOLVE", 7),
    CREEPER("CREEPER", 8),
    DECONVERGE("DECONVERGE", 9),
    DESATURATE("DESATURATE", 10),
    ENTITY_OUTLINE("ENTITY_OUTLINE", 11),
    FLIP("FLIP", 12),
    FXAA("FXAA", 13),
    GREEN("GREEN", 14),
    INVERT("INVERT", 15),
    NOTCH("NOTCH", 16),
    NTSC("NTSC", 17),
    OUTLINE("OUTLINE", 18),
    PENCIL("PENCIL", 19),
    PHOSPHOR("PHOSPHOR", 20),
    SCAN_PINCUSION("SCAN_PINCUSION", 21),
    SOBEL("SOBEL", 22),
    SPIDER("SPIDER", 23),
    WOBBLE("WOBBLE", 24);
    public static SecretShaderMode[] Field431;

    public static SecretShaderMode[] Method548() {
        return Field431.clone();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    SecretShaderMode(String a, int b) {
    }

    public static SecretShaderMode Method549(String string) {
        return Enum.valueOf(SecretShaderMode.class, string);
    }

    static {
        Field431 = new SecretShaderMode[]{ANTIALIAS, ART, BITS, BLOBS, BLOBS2, BLUR, BUMPY, COLOR_CONVOLVE, CREEPER, DECONVERGE, DESATURATE, ENTITY_OUTLINE, FLIP, FXAA, GREEN, INVERT, NOTCH, NTSC, OUTLINE, PENCIL, PHOSPHOR, SCAN_PINCUSION, SOBEL, SPIDER, WOBBLE};
    }
}