package me.darki.konas.unremaped;

import me.darki.konas.*;
public class Class420
extends Enum {
    public static Class420 NONE = new Class420("NONE", 0, 0.0f, 0.0f);
    public static Class420 ARROW = new Class420("ARROW", 1, 1.5f, 0.05f);
    public static Class420 POTION = new Class420("POTION", 2, 0.5f, 0.05f);
    public static Class420 EXPERIENCE = new Class420("EXPERIENCE", 3, 0.7f, 0.07f);
    public static Class420 FISHING_ROD = new Class420("FISHING_ROD", 4, 1.5f, 0.04f);
    public static Class420 NORMAL = new Class420("NORMAL", 5, 1.5f, 0.03f);
    public static Class420[] Field1030;
    public float Field1028;
    public float Field1029;

    public float Method1046() {
        return this.Field1029;
    }

    static {
        Field1030 = new Class420[]{NONE, ARROW, POTION, EXPERIENCE, FISHING_ROD, NORMAL};
    }

    /*
     * WARNING - void declaration
     */
    public Class420() {
        void var4_2;
        void var3_1;
        void var2_-1;
        void var1_-1;
        this.Field1028 = var3_1;
        this.Field1029 = var4_2;
    }

    public static Class420 Method1047(String string) {
        return Enum.valueOf(Class420.class, string);
    }

    public float Method1048() {
        return this.Field1028;
    }

    public static Class420[] Method1049() {
        return (Class420[])Field1030.clone();
    }
}