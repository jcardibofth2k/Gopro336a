package com.viaversion.viaversion.api.minecraft.chunks;

import java.util.ArrayList;
import java.util.List;

public class Chunk1_8 extends BaseChunk {
   private boolean unloadPacket;

   public Chunk1_8(int x, int z, boolean groundUp, int bitmask, ChunkSection[] sections, int[] biomeData, List blockEntities) {
      super(x, z, groundUp, false, bitmask, sections, biomeData, blockEntities);
   }

   public Chunk1_8(int x, int z) {
      this(x, z, true, 0, new ChunkSection[16], (int[])null, new ArrayList());
      this.unloadPacket = true;
   }

   public boolean hasBiomeData() {
      return this.biomeData != null && this.fullChunk;
   }

   public boolean isUnloadPacket() {
      return this.unloadPacket;
   }
}
