package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.BiIntConsumer;
import com.viaversion.viaversion.util.CompactArrayUtil;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import java.util.function.IntToLongFunction;

public class ChunkSectionType1_13 extends Type {
   private static final int GLOBAL_PALETTE = 14;

   public ChunkSectionType1_13() {
      super("Chunk Section Type", ChunkSection.class);
   }

   public ChunkSection read(ByteBuf buffer) throws Exception {
      int bitsPerBlock = buffer.readUnsignedByte();
      int originalBitsPerBlock = bitsPerBlock;
      if (bitsPerBlock == 0 || bitsPerBlock > 8) {
         bitsPerBlock = 14;
      }

      ChunkSectionImpl chunkSection;
      int expectedLength;
      if (bitsPerBlock != 14) {
         int paletteLength = Type.VAR_INT.readPrimitive(buffer);
         chunkSection = new ChunkSectionImpl(true, paletteLength);

         for(expectedLength = 0; expectedLength < paletteLength; ++expectedLength) {
            chunkSection.addPaletteEntry(Type.VAR_INT.readPrimitive(buffer));
         }
      } else {
         chunkSection = new ChunkSectionImpl(true);
      }

      long[] blockData = new long[Type.VAR_INT.readPrimitive(buffer)];
      if (blockData.length > 0) {
         expectedLength = (int)Math.ceil((double)(4096 * bitsPerBlock) / 64.0D);
         if (blockData.length != expectedLength) {
            throw new IllegalStateException("Block data length (" + blockData.length + ") does not match expected length (" + expectedLength + ")! bitsPerBlock=" + bitsPerBlock + ", originalBitsPerBlock=" + originalBitsPerBlock);
         }

         for(int i = 0; i < blockData.length; ++i) {
            blockData[i] = buffer.readLong();
         }

         BiIntConsumer var10003;
         if (bitsPerBlock == 14) {
            Objects.requireNonNull(chunkSection);
            var10003 = chunkSection::setFlatBlock;
         } else {
            Objects.requireNonNull(chunkSection);
            var10003 = chunkSection::setPaletteIndex;
         }

         CompactArrayUtil.iterateCompactArray(bitsPerBlock, 4096, blockData, var10003);
      }

      return chunkSection;
   }

   public void write(ByteBuf buffer, ChunkSection chunkSection) throws Exception {
      int bitsPerBlock;
      for(bitsPerBlock = 4; chunkSection.getPaletteSize() > 1 << bitsPerBlock; ++bitsPerBlock) {
      }

      if (bitsPerBlock > 8) {
         bitsPerBlock = 14;
      }

      buffer.writeByte(bitsPerBlock);
      if (bitsPerBlock != 14) {
         Type.VAR_INT.writePrimitive(buffer, chunkSection.getPaletteSize());

         for(int i = 0; i < chunkSection.getPaletteSize(); ++i) {
            Type.VAR_INT.writePrimitive(buffer, chunkSection.getPaletteEntry(i));
         }
      }

      IntToLongFunction var10002;
      if (bitsPerBlock == 14) {
         Objects.requireNonNull(chunkSection);
         var10002 = chunkSection::getFlatBlock;
      } else {
         Objects.requireNonNull(chunkSection);
         var10002 = chunkSection::getPaletteIndex;
      }

      long[] data = CompactArrayUtil.createCompactArray(bitsPerBlock, 4096, var10002);
      Type.VAR_INT.writePrimitive(buffer, data.length);
      long[] var5 = data;
      int var6 = data.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         long l = var5[var7];
         buffer.writeLong(l);
      }

   }
}
