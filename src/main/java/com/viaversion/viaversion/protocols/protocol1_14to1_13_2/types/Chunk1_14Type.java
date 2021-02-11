package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.types;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chunk1_14Type extends Type {
   public Chunk1_14Type() {
      super(Chunk.class);
   }

   public Chunk read(ByteBuf input) throws Exception {
      int chunkX = input.readInt();
      int chunkZ = input.readInt();
      boolean fullChunk = input.readBoolean();
      int primaryBitmask = Type.VAR_INT.readPrimitive(input);
      CompoundTag heightMap = (CompoundTag)Type.NBT.read(input);
      Type.VAR_INT.readPrimitive(input);
      ChunkSection[] sections = new ChunkSection[16];

      for(int i = 0; i < 16; ++i) {
         if ((primaryBitmask & 1 << i) != 0) {
            short nonAirBlocksCount = input.readShort();
            ChunkSection section = (ChunkSection)Types1_13.CHUNK_SECTION.read(input);
            section.setNonAirBlocksCount(nonAirBlocksCount);
            sections[i] = section;
         }
      }

      int[] biomeData = fullChunk ? new int[256] : null;
      if (fullChunk) {
         for(int i = 0; i < 256; ++i) {
            biomeData[i] = input.readInt();
         }
      }

      List nbtData = new ArrayList(Arrays.asList((CompoundTag[])Type.NBT_ARRAY.read(input)));
      if (input.readableBytes() > 0) {
         byte[] array = (byte[])Type.REMAINING_BYTES.read(input);
         if (Via.getManager().isDebug()) {
            Via.getPlatform().getLogger().warning("Found " + array.length + " more bytes than expected while reading the chunk: " + chunkX + "/" + chunkZ);
         }
      }

      return new BaseChunk(chunkX, chunkZ, fullChunk, false, primaryBitmask, sections, biomeData, heightMap, nbtData);
   }

   public void write(ByteBuf output, Chunk chunk) throws Exception {
      output.writeInt(chunk.getX());
      output.writeInt(chunk.getZ());
      output.writeBoolean(chunk.isFullChunk());
      Type.VAR_INT.writePrimitive(output, chunk.getBitmask());
      Type.NBT.write(output, chunk.getHeightMap());
      ByteBuf buf = output.alloc().buffer();

      try {
         for(int i = 0; i < 16; ++i) {
            ChunkSection section = chunk.getSections()[i];
            if (section != null) {
               buf.writeShort(section.getNonAirBlocksCount());
               Types1_13.CHUNK_SECTION.write(buf, section);
            }
         }

         buf.readerIndex(0);
         Type.VAR_INT.writePrimitive(output, buf.readableBytes() + (chunk.isBiomeData() ? 1024 : 0));
         output.writeBytes(buf);
      } finally {
         buf.release();
      }

      if (chunk.isBiomeData()) {
         int[] var10 = chunk.getBiomeData();
         int var11 = var10.length;

         for(int var6 = 0; var6 < var11; ++var6) {
            int value = var10[var6];
            output.writeInt(value & 255);
         }
      }

      Type.NBT_ARRAY.write(output, chunk.getBlockEntities().toArray(new CompoundTag[0]));
   }

   public Class getBaseClass() {
      return BaseChunkType.class;
   }
}
