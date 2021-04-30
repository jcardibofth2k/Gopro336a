package me.darki.konas.unremaped;

import me.darki.konas.*;
import net.minecraft.util.EnumFacing;

public class Class528 {
    public static int[] Field1186;
    public static int[] Field1187;

    static {
        Field1187 = new int[EnumFacing.values().length];
        try {
            Class528.Field1187[EnumFacing.DOWN.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class528.Field1187[EnumFacing.UP.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class528.Field1187[EnumFacing.NORTH.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class528.Field1187[EnumFacing.SOUTH.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class528.Field1187[EnumFacing.EAST.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class528.Field1187[EnumFacing.WEST.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        Field1186 = new int[Class420.Method1049().length];
        try {
            Class528.Field1186[Class420.FISHING_ROD.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class528.Field1186[Class420.NORMAL.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class528.Field1186[Class420.ARROW.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}