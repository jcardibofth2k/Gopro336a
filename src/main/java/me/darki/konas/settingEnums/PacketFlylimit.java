package me.darki.konas.settingEnums;

public class PacketFlylimit
extends Enum {
    public static PacketFlylimit NONE = new PacketFlylimit("NONE", 0);
    public static PacketFlylimit STRONG = new PacketFlylimit("STRONG", 1);
    public static PacketFlylimit STRICT = new PacketFlylimit("STRICT", 2);
    public static PacketFlylimit[] Field2083;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    public PacketFlylimit() {
        void var2_-1;
        void var1_-1;
    }

    public static PacketFlylimit Method1935(String string) {
        return Enum.valueOf(PacketFlylimit.class, string);
    }

    public static PacketFlylimit[] Method1936() {
        return Field2083.clone();
    }

    static {
        Field2083 = new PacketFlylimit[]{NONE, STRONG, STRICT};
    }
}