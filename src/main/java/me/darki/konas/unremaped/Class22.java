package me.darki.konas;

import net.minecraft.network.Packet;

public class Class22
extends CancelableEvent {
    public Packet Field137;

    public Packet getPacket() {
        return this.Field137;
    }

    public Class22(Packet packet) {
        this.Field137 = packet;
    }
}
