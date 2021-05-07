package me.darki.konas.unremaped;

import me.darki.konas.*;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.world.chunk.Chunk;

public class Class649 {
    public Chunk Field1188;
    public SPacketChunkData Field1189;

    public Chunk Method1167() {
        return this.Field1188;
    }

    public SPacketChunkData Method1168() {
        return this.Field1189;
    }

    public Class649(Chunk chunk, SPacketChunkData sPacketChunkData) {
        this.Field1188 = chunk;
        this.Field1189 = sPacketChunkData;
    }
}