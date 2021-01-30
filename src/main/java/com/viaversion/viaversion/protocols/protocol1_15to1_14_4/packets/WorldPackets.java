package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets;

import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.types.Chunk1_14Type;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.types.Chunk1_15Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;

public class WorldPackets {
   public static void register(final Protocol1_15To1_14_4 protocol) {
      BlockRewriter blockRewriter = new BlockRewriter(protocol, Type.POSITION1_14);
      blockRewriter.registerBlockAction(ClientboundPackets1_14.BLOCK_ACTION);
      blockRewriter.registerBlockChange(ClientboundPackets1_14.BLOCK_CHANGE);
      blockRewriter.registerMultiBlockChange(ClientboundPackets1_14.MULTI_BLOCK_CHANGE);
      blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_14.ACKNOWLEDGE_PLAYER_DIGGING);
      protocol.registerClientbound(ClientboundPackets1_14.CHUNK_DATA, new PacketRemapper() {
         public void registerMap() {
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  Chunk chunk = (Chunk)wrapper.read(new Chunk1_14Type());
                  wrapper.write(new Chunk1_15Type(), chunk);
                  int i;
                  int j;
                  int x;
                  if (chunk.isFullChunk()) {
                     int[] biomeData = chunk.getBiomeData();
                     int[] newBiomeData = new int[1024];
                     if (biomeData != null) {
                        i = 0;

                        label52:
                        while(true) {
                           if (i >= 4) {
                              i = 1;

                              while(true) {
                                 if (i >= 64) {
                                    break label52;
                                 }

                                 System.arraycopy(newBiomeData, 0, newBiomeData, i * 16, 16);
                                 ++i;
                              }
                           }

                           for(j = 0; j < 4; ++j) {
                              x = (j << 2) + 2;
                              int z = (i << 2) + 2;
                              int oldIndex = z << 4 | x;
                              newBiomeData[i << 2 | j] = biomeData[oldIndex];
                           }

                           ++i;
                        }
                     }

                     chunk.setBiomeData(newBiomeData);
                  }

                  for(int s = 0; s < chunk.getSections().length; ++s) {
                     ChunkSection section = chunk.getSections()[s];
                     if (section != null) {
                        for(i = 0; i < section.getPaletteSize(); ++i) {
                           j = section.getPaletteEntry(i);
                           x = protocol.getMappingData().getNewBlockStateId(j);
                           section.setPaletteEntry(i, x);
                        }
                     }
                  }

               }
            });
         }
      });
      blockRewriter.registerEffect(ClientboundPackets1_14.EFFECT, 1010, 2001);
      protocol.registerClientbound(ClientboundPackets1_14.SPAWN_PARTICLE, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.INT);
            this.map(Type.BOOLEAN);
            this.map(Type.FLOAT, Type.DOUBLE);
            this.map(Type.FLOAT, Type.DOUBLE);
            this.map(Type.FLOAT, Type.DOUBLE);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int id = (Integer)wrapper.get(Type.INT, 0);
                  if (id != 3 && id != 23) {
                     if (id == 32) {
                        protocol.getItemRewriter().handleItemToClient((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                     }
                  } else {
                     int data = (Integer)wrapper.passthrough(Type.VAR_INT);
                     wrapper.set(Type.VAR_INT, 0, protocol.getMappingData().getNewBlockStateId(data));
                  }

               }
            });
         }
      });
   }
}
