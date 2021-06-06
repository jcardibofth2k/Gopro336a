package me.darki.konas.settingEnums;

public enum PacketFlyAntiKick {
    NONE("NONE", 0),
    NORMAL("NORMAL", 1),
    LIMITED("LIMITED", 2),
    STRICT("STRICT", 3);
    public static PacketFlyAntiKick[] Field2004;

    public static PacketFlyAntiKick[] Method1833() {
        return Field2004.clone();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    PacketFlyAntiKick(String a, int b) {
    }

    public static PacketFlyAntiKick Method1834(String string) {
        return Enum.valueOf(PacketFlyAntiKick.class, string);
    }

    static {
        Field2004 = new PacketFlyAntiKick[]{NONE, NORMAL, LIMITED, STRICT};
    }
}