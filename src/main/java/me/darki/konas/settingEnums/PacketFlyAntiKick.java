package me.darki.konas.settingEnums;

public class PacketFlyAntiKick
extends Enum {
    public static PacketFlyAntiKick NONE = new PacketFlyAntiKick("NONE", 0);
    public static PacketFlyAntiKick NORMAL = new PacketFlyAntiKick("NORMAL", 1);
    public static PacketFlyAntiKick LIMITED = new PacketFlyAntiKick("LIMITED", 2);
    public static PacketFlyAntiKick STRICT = new PacketFlyAntiKick("STRICT", 3);
    public static PacketFlyAntiKick[] Field2004;

    public static PacketFlyAntiKick[] Method1833() {
        return Field2004.clone();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public PacketFlyAntiKick() {
        void var2_-1;
        void var1_-1;
    }

    public static PacketFlyAntiKick Method1834(String string) {
        return Enum.valueOf(PacketFlyAntiKick.class, string);
    }

    static {
        Field2004 = new PacketFlyAntiKick[]{NONE, NORMAL, LIMITED, STRICT};
    }
}