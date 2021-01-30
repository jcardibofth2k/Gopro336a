package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.nio.ByteOrder;

public class ChunkSectionType1_8 extends Type {
   public ChunkSectionType1_8() {
      super("Chunk Section Type", ChunkSection.class);
   }

   public ChunkSection read(ByteBuf buffer) throws Exception {
      ChunkSection chunkSection = new ChunkSectionImpl(true);
      chunkSection.addPaletteEntry(0);
      ByteBuf littleEndianView = buffer.order(ByteOrder.LITTLE_ENDIAN);

      for(int i = 0; i < 4096; ++i) {
         int mask = littleEndianView.readShort();
         int type = mask >> 4;
         int data = mask & 15;
         chunkSection.setBlockWithData(i, type, data);
      }

      return chunkSection;
   }

   public void write(ByteBuf buffer, ChunkSection chunkSection) throws Exception {
      throw new UnsupportedOperationException();
   }
}
