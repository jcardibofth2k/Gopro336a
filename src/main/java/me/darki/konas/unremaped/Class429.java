package me.darki.konas.unremaped;

import me.darki.konas.*;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;

public class Class429 {
    public static int[] Field863 = new int[RenderBlockOverlayEvent.OverlayType.values().length];

    static {
        try {
            Class429.Field863[RenderBlockOverlayEvent.OverlayType.FIRE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class429.Field863[RenderBlockOverlayEvent.OverlayType.WATER.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            Class429.Field863[RenderBlockOverlayEvent.OverlayType.BLOCK.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}