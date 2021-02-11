package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets;

import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.Protocol1_14_4To1_15;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.RecipeRewriter1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.types.Chunk1_14Type;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.types.Chunk1_15Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;

public class BlockItemPackets1_15 extends ItemRewriter {
   public BlockItemPackets1_15(Protocol1_14_4To1_15 protocol, TranslatableRewriter translatableRewriter) {
      super(protocol, translatableRewriter);
   }

   protected void registerPackets() {
      BlockRewriter blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
      (new RecipeRewriter1_14(this.protocol)).registerDefaultHandler(ClientboundPackets1_15.DECLARE_RECIPES);
      this.protocol.registerServerbound(ServerboundPackets1_14.EDIT_BOOK, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               BlockItemPackets1_15.this.handleItemToServer((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            });
         }
      });
      this.registerSetCooldown(ClientboundPackets1_15.COOLDOWN);
      this.registerWindowItems(ClientboundPackets1_15.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
      this.registerSetSlot(ClientboundPackets1_15.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
      this.registerTradeList(ClientboundPackets1_15.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
      this.registerEntityEquipment(ClientboundPackets1_15.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
      this.registerAdvancements(ClientboundPackets1_15.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
      this.registerClickWindow(ServerboundPackets1_14.CLICK_WINDOW, Type.FLAT_VAR_INT_ITEM);
      this.registerCreativeInvAction(ServerboundPackets1_14.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
      blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_15.ACKNOWLEDGE_PLAYER_DIGGING);
      blockRewriter.registerBlockAction(ClientboundPackets1_15.BLOCK_ACTION);
      blockRewriter.registerBlockChange(ClientboundPackets1_15.BLOCK_CHANGE);
      blockRewriter.registerMultiBlockChange(ClientboundPackets1_15.MULTI_BLOCK_CHANGE);
      this.protocol.registerClientbound(ClientboundPackets1_15.CHUNK_DATA, new PacketRemapper() {
         public void registerMap() {
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  Chunk chunk = (Chunk)wrapper.read(new Chunk1_15Type());
                  wrapper.write(new Chunk1_14Type(), chunk);
                  int j;
                  int old;
                  int newId;
                  if (chunk.isFullChunk()) {
                     int[] biomeData = chunk.getBiomeData();
                     int[] newBiomeData = new int[256];

                     for(j = 0; j < 4; ++j) {
                        for(old = 0; old < 4; ++old) {
                           newId = old << 2;
                           int z = j << 2;
                           int newIndex = z << 4 | newId;
                           int oldIndex = j << 2 | old;
                           int biome = biomeData[oldIndex];

                           for(int k = 0; k < 4; ++k) {
                              int offX = newIndex + (k << 4);

                              for(int l = 0; l < 4; ++l) {
                                 newBiomeData[offX + l] = biome;
                              }
                           }
                        }
                     }

                     chunk.setBiomeData(newBiomeData);
                  }

                  for(int i = 0; i < chunk.getSections().length; ++i) {
                     ChunkSection section = chunk.getSections()[i];
                     if (section != null) {
                        for(j = 0; j < section.getPaletteSize(); ++j) {
                           old = section.getPaletteEntry(j);
                           newId = ((Protocol1_14_4To1_15)BlockItemPackets1_15.this.protocol).getMappingData().getNewBlockStateId(old);
                           section.setPaletteEntry(j, newId);
                        }
                     }
                  }

               }
            });
         }
      });
      blockRewriter.registerEffect(ClientboundPackets1_15.EFFECT, 1010, 2001);
      this.protocol.registerClientbound(ClientboundPackets1_15.SPAWN_PARTICLE, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.INT);
            this.map(Type.BOOLEAN);
            this.map(Type.DOUBLE, Type.FLOAT);
            this.map(Type.DOUBLE, Type.FLOAT);
            this.map(Type.DOUBLE, Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int id = (Integer)wrapper.get(Type.INT, 0);
                  int mappedId;
                  if (id != 3 && id != 23) {
                     if (id == 32) {
                        Item item = BlockItemPackets1_15.this.handleItemToClient((Item)wrapper.read(Type.FLAT_VAR_INT_ITEM));
                        wrapper.write(Type.FLAT_VAR_INT_ITEM, item);
                     }
                  } else {
                     mappedId = (Integer)wrapper.passthrough(Type.VAR_INT);
                     wrapper.set(Type.VAR_INT, 0, ((Protocol1_14_4To1_15)BlockItemPackets1_15.this.protocol).getMappingData().getNewBlockStateId(mappedId));
                  }

                  mappedId = ((Protocol1_14_4To1_15)BlockItemPackets1_15.this.protocol).getMappingData().getNewParticleId(id);
                  if (id != mappedId) {
                     wrapper.set(Type.INT, 0, mappedId);
                  }

               }
            });
         }
      });
   }
}
