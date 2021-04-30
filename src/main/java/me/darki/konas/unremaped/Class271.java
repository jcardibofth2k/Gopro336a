package me.darki.konas.unremaped;

import me.darki.konas.*;
import me.darki.konas.settingEnums.PacketFlyMode;
import me.darki.konas.settingEnums.PacketFlyType;

public class Class271 {
    public static int[] Field1984;
    public static int[] Field1985;

    static {
        Field1985 = new int[PacketFlyMode.Method1883().length];
        try {
            Class271.Field1985[PacketFlyMode.UP.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class271.Field1985[PacketFlyMode.PRESERVE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class271.Field1985[PacketFlyMode.LIMITJITTER.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class271.Field1985[PacketFlyMode.BYPASS.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class271.Field1985[PacketFlyMode.OBSCURE.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        Field1984 = new int[PacketFlyType.Method2061().length];
        try {
            Class271.Field1984[PacketFlyType.FAST.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class271.Field1984[PacketFlyType.SLOW.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class271.Field1984[PacketFlyType.SETBACK.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class271.Field1984[PacketFlyType.FACTOR.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class271.Field1984[PacketFlyType.DESYNC.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}