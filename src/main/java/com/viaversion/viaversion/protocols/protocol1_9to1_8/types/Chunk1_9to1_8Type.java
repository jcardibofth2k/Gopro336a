package com.viaversion.viaversion.protocols.protocol1_9to1_8.types;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk1_8;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.PartialType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.ClientChunks;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.logging.Level;

public class Chunk1_9to1_8Type extends PartialType {
   public static final int SECTION_COUNT = 16;
   private static final int SECTION_SIZE = 16;
   private static final int BIOME_DATA_LENGTH = 256;

   public Chunk1_9to1_8Type(ClientChunks chunks) {
      super(chunks, Chunk.class);
   }

   private static long toLong(int msw, int lsw) {
      return ((long)msw << 32) + (long)lsw - -2147483648L;
   }

   public Class getBaseClass() {
      return BaseChunkType.class;
   }

   public Chunk read(ByteBuf input, ClientChunks param) throws Exception {
      boolean replacePistons = param.getUser().getProtocolInfo().getPipeline().contains(Protocol1_10To1_9_3_4.class) && Via.getConfig().isReplacePistons();
      int replacementId = Via.getConfig().getPistonReplacementId();
      int chunkX = input.readInt();
      int chunkZ = input.readInt();
      long chunkHash = toLong(chunkX, chunkZ);
      boolean fullChunk = input.readByte() != 0;
      int bitmask = input.readUnsignedShort();
      int dataLength = Type.VAR_INT.readPrimitive(input);
      BitSet usedSections = new BitSet(16);
      ChunkSection[] sections = new ChunkSection[16];
      int[] biomeData = null;

      int i;
      for(i = 0; i < 16; ++i) {
         if ((bitmask & 1 << i) != 0) {
            usedSections.set(i);
         }
      }

      i = usedSections.cardinality();
      boolean isBulkPacket = param.getBulkChunks().remove(chunkHash);
      if (i == 0 && fullChunk && !isBulkPacket && param.getLoadedChunks().contains(chunkHash)) {
         param.getLoadedChunks().remove(chunkHash);
         return new Chunk1_8(chunkX, chunkZ);
      } else {
         int startIndex = input.readerIndex();
         param.getLoadedChunks().add(chunkHash);

         int bytesLeft;
         for(bytesLeft = 0; bytesLeft < 16; ++bytesLeft) {
            if (usedSections.get(bytesLeft)) {
               ChunkSection section = (ChunkSection)Types1_8.CHUNK_SECTION.read(input);
               sections[bytesLeft] = section;
               if (replacePistons) {
                  section.replacePaletteEntry(36, replacementId);
               }
            }
         }

         for(bytesLeft = 0; bytesLeft < 16; ++bytesLeft) {
            if (usedSections.get(bytesLeft)) {
               sections[bytesLeft].getLight().readBlockLight(input);
            }
         }

         bytesLeft = dataLength - (input.readerIndex() - startIndex);
         int i;
         if (bytesLeft >= 2048) {
            for(i = 0; i < 16; ++i) {
               if (usedSections.get(i)) {
                  sections[i].getLight().readSkyLight(input);
                  bytesLeft -= 2048;
               }
            }
         }

         if (bytesLeft >= 256) {
            biomeData = new int[256];

            for(i = 0; i < 256; ++i) {
               biomeData[i] = input.readByte() & 255;
            }

            bytesLeft -= 256;
         }

         if (bytesLeft > 0) {
            Via.getPlatform().getLogger().log(Level.WARNING, bytesLeft + " Bytes left after reading chunks! (" + fullChunk + ")");
         }

         return new Chunk1_8(chunkX, chunkZ, fullChunk, bitmask, sections, biomeData, new ArrayList());
      }
   }

   public void write(ByteBuf output, ClientChunks param, Chunk input) throws Exception {
      if (!(input instanceof Chunk1_8)) {
         throw new Exception("Incompatible chunk, " + input.getClass());
      } else {
         Chunk1_8 chunk = (Chunk1_8)input;
         output.writeInt(chunk.getX());
         output.writeInt(chunk.getZ());
         if (!chunk.isUnloadPacket()) {
            output.writeByte(chunk.isFullChunk() ? 1 : 0);
            Type.VAR_INT.writePrimitive(output, chunk.getBitmask());
            ByteBuf buf = output.alloc().buffer();

            try {
               for(int i = 0; i < 16; ++i) {
                  ChunkSection section = chunk.getSections()[i];
                  if (section != null) {
                     Types1_9.CHUNK_SECTION.write(buf, section);
                     section.getLight().writeBlockLight(buf);
                     if (section.getLight().hasSkyLight()) {
                        section.getLight().writeSkyLight(buf);
                     }
                  }
               }

               buf.readerIndex(0);
               Type.VAR_INT.writePrimitive(output, buf.readableBytes() + (chunk.hasBiomeData() ? 256 : 0));
               output.writeBytes(buf);
            } finally {
               buf.release();
            }

            if (chunk.hasBiomeData()) {
               int[] var12 = chunk.getBiomeData();
               int var13 = var12.length;

               for(int var8 = 0; var8 < var13; ++var8) {
                  int biome = var12[var8];
                  output.writeByte((byte)biome);
               }
            }

         }
      }
   }
}
