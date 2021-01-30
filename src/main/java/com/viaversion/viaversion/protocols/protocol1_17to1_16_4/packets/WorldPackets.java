package com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_16_2;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.types.Chunk1_16_2Type;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.Protocol1_17To1_16_4;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.types.Chunk1_17Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

public final class WorldPackets {
   public static void register(final Protocol1_17To1_16_4 protocol) {
      BlockRewriter blockRewriter = new BlockRewriter(protocol, Type.POSITION1_14);
      blockRewriter.registerBlockAction(ClientboundPackets1_16_2.BLOCK_ACTION);
      blockRewriter.registerBlockChange(ClientboundPackets1_16_2.BLOCK_CHANGE);
      blockRewriter.registerVarLongMultiBlockChange(ClientboundPackets1_16_2.MULTI_BLOCK_CHANGE);
      blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_16_2.ACKNOWLEDGE_PLAYER_DIGGING);
      protocol.registerClientbound(ClientboundPackets1_16_2.WORLD_BORDER, (ClientboundPacketType)null, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               int type = (Integer)wrapper.read(Type.VAR_INT);
               ClientboundPackets1_17 packetType;
               switch(type) {
               case 0:
                  packetType = ClientboundPackets1_17.WORLD_BORDER_SIZE;
                  break;
               case 1:
                  packetType = ClientboundPackets1_17.WORLD_BORDER_LERP_SIZE;
                  break;
               case 2:
                  packetType = ClientboundPackets1_17.WORLD_BORDER_CENTER;
                  break;
               case 3:
                  packetType = ClientboundPackets1_17.WORLD_BORDER_INIT;
                  break;
               case 4:
                  packetType = ClientboundPackets1_17.WORLD_BORDER_WARNING_DELAY;
                  break;
               case 5:
                  packetType = ClientboundPackets1_17.WORLD_BORDER_WARNING_DISTANCE;
                  break;
               default:
                  throw new IllegalArgumentException("Invalid world border type received: " + type);
               }

               wrapper.setId(packetType.getId());
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_16_2.UPDATE_LIGHT, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.BOOLEAN);
            this.handler((wrapper) -> {
               int skyLightMask = (Integer)wrapper.read(Type.VAR_INT);
               int blockLightMask = (Integer)wrapper.read(Type.VAR_INT);
               wrapper.write(Type.LONG_ARRAY_PRIMITIVE, this.toBitSetLongArray(skyLightMask));
               wrapper.write(Type.LONG_ARRAY_PRIMITIVE, this.toBitSetLongArray(blockLightMask));
               wrapper.write(Type.LONG_ARRAY_PRIMITIVE, this.toBitSetLongArray((Integer)wrapper.read(Type.VAR_INT)));
               wrapper.write(Type.LONG_ARRAY_PRIMITIVE, this.toBitSetLongArray((Integer)wrapper.read(Type.VAR_INT)));
               this.writeLightArrays(wrapper, skyLightMask);
               this.writeLightArrays(wrapper, blockLightMask);
            });
         }

         private void writeLightArrays(PacketWrapper wrapper, int bitMask) throws Exception {
            List light = new ArrayList();

            for(int i = 0; i < 18; ++i) {
               if (this.isSet(bitMask, i)) {
                  light.add((byte[])wrapper.read(Type.BYTE_ARRAY_PRIMITIVE));
               }
            }

            wrapper.write(Type.VAR_INT, light.size());
            Iterator var6 = light.iterator();

            while(var6.hasNext()) {
               byte[] bytes = (byte[])var6.next();
               wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, bytes);
            }

         }

         private long[] toBitSetLongArray(int bitmask) {
            return new long[]{(long)bitmask};
         }

         private boolean isSet(int mask, int i) {
            return (mask & 1 << i) != 0;
         }
      });
      protocol.registerClientbound(ClientboundPackets1_16_2.CHUNK_DATA, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               Chunk chunk = (Chunk)wrapper.read(new Chunk1_16_2Type());
               if (!chunk.isFullChunk()) {
                  WorldPackets.writeMultiBlockChangePacket(wrapper, chunk);
                  wrapper.cancel();
               } else {
                  wrapper.write(new Chunk1_17Type(chunk.getSections().length), chunk);
                  chunk.setChunkMask(BitSet.valueOf(new long[]{(long)chunk.getBitmask()}));

                  for(int s = 0; s < chunk.getSections().length; ++s) {
                     ChunkSection section = chunk.getSections()[s];
                     if (section != null) {
                        for(int i = 0; i < section.getPaletteSize(); ++i) {
                           int old = section.getPaletteEntry(i);
                           section.setPaletteEntry(i, protocol.getMappingData().getNewBlockStateId(old));
                        }
                     }
                  }

               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_16_2.JOIN_GAME, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.INT);
            this.map(Type.BOOLEAN);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.BYTE);
            this.map(Type.STRING_ARRAY);
            this.map(Type.NBT);
            this.map(Type.NBT);
            this.handler((wrapper) -> {
               CompoundTag dimensionRegistry = (CompoundTag)((CompoundTag)wrapper.get(Type.NBT, 0)).get("minecraft:dimension_type");
               ListTag dimensions = (ListTag)dimensionRegistry.get("value");
               Iterator var3 = dimensions.iterator();

               while(var3.hasNext()) {
                  Tag dimension = (Tag)var3.next();
                  CompoundTag dimensionCompound = (CompoundTag)((CompoundTag)dimension).get("element");
                  WorldPackets.addNewDimensionData(dimensionCompound);
               }

               CompoundTag currentDimensionTag = (CompoundTag)wrapper.get(Type.NBT, 1);
               WorldPackets.addNewDimensionData(currentDimensionTag);
               UserConnection user = wrapper.user();
               user.getEntityTracker(Protocol1_17To1_16_4.class).addEntity((Integer)wrapper.get(Type.INT, 0), Entity1_17Types.PLAYER);
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_16_2.RESPAWN, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               CompoundTag dimensionData = (CompoundTag)wrapper.passthrough(Type.NBT);
               WorldPackets.addNewDimensionData(dimensionData);
            });
         }
      });
      blockRewriter.registerEffect(ClientboundPackets1_16_2.EFFECT, 1010, 2001);
   }

   private static void writeMultiBlockChangePacket(PacketWrapper wrapper, Chunk chunk) throws Exception {
      long chunkPosition = ((long)chunk.getX() & 4194303L) << 42;
      chunkPosition |= ((long)chunk.getZ() & 4194303L) << 20;
      ChunkSection[] sections = chunk.getSections();

      for(int chunkY = 0; chunkY < sections.length; ++chunkY) {
         ChunkSection section = sections[chunkY];
         if (section != null) {
            PacketWrapper blockChangePacket = wrapper.create(ClientboundPackets1_17.MULTI_BLOCK_CHANGE);
            blockChangePacket.write(Type.LONG, chunkPosition | (long)chunkY & 1048575L);
            blockChangePacket.write(Type.BOOLEAN, true);
            BlockChangeRecord[] blockChangeRecords = new BlockChangeRecord[4096];
            int j = 0;

            for(int x = 0; x < 16; ++x) {
               for(int y = 0; y < 16; ++y) {
                  for(int z = 0; z < 16; ++z) {
                     int blockStateId = Protocol1_17To1_16_4.MAPPINGS.getNewBlockStateId(section.getFlatBlock(x, y, z));
                     blockChangeRecords[j++] = new BlockChangeRecord1_16_2(x, y, z, blockStateId);
                  }
               }
            }

            blockChangePacket.write(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY, blockChangeRecords);
            blockChangePacket.send(Protocol1_17To1_16_4.class);
         }
      }

   }

   private static void addNewDimensionData(CompoundTag tag) {
      tag.put("min_y", new IntTag(0));
      tag.put("height", new IntTag(256));
   }
}
