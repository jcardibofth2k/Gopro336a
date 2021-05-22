package me.darki.konas.unremaped;

import cookiedragon.eventsystem.Subscriber;
import me.darki.konas.module.Category;
import me.darki.konas.module.Module;

public class Class419
extends Module {
    public static float Field1083 = 0.0f;
    public static float Field1084 = 0.0f;

    @Subscriber
    public void Method536(SendPacketEvent sendPacketEvent) {
    }

    public static void Method1113(float f) {
        Field1083 = f;
    }

    public static float Method1114() {
        return Field1083;
    }

    public Class419() {
        super("PacketRender", Category.RENDER, new String[0]);
    }

    public static float Method1115() {
        return Field1084;
    }

    public static void Method1116(float f) {
        Field1084 = f;
    }
}