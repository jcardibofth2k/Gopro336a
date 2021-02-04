package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets;

import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.api.rewriters.MapColorRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.Protocol1_16_4To1_17;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.data.MapColorRewrites;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.types.Chunk1_16_2Type;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data.RecipeRewriter1_16;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.types.Chunk1_17Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

public final class BlockItemPackets1_17 extends ItemRewriter {
   public BlockItemPackets1_17(Protocol1_16_4To1_17 protocol, TranslatableRewriter translatableRewriter) {
      super(protocol, translatableRewriter);
   }

   protected void registerPackets() {
      BlockRewriter blockRewriter = new BlockRewriter(this.protocol, Type.POSITION1_14);
      (new RecipeRewriter1_16(this.protocol)).registerDefaultHandler(ClientboundPackets1_17.DECLARE_RECIPES);
      this.registerSetCooldown(ClientboundPackets1_17.COOLDOWN);
      this.registerWindowItems(ClientboundPackets1_17.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
      this.registerSetSlot(ClientboundPackets1_17.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
      this.registerEntityEquipmentArray(ClientboundPackets1_17.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
      this.registerTradeList(ClientboundPackets1_17.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
      this.registerAdvancements(ClientboundPackets1_17.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
      blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_17.ACKNOWLEDGE_PLAYER_DIGGING);
      blockRewriter.registerBlockAction(ClientboundPackets1_17.BLOCK_ACTION);
      blockRewriter.registerEffect(ClientboundPackets1_17.EFFECT, 1010, 2001);
      this.registerCreativeInvAction(ServerboundPackets1_16_2.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
      this.protocol.registerServerbound(ServerboundPackets1_16_2.EDIT_BOOK, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               BlockItemPackets1_17.this.handleItemToServer((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            });
         }
      });
      this.protocol.registerServerbound(ServerboundPackets1_16_2.CLICK_WINDOW, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.SHORT);
            this.map(Type.BYTE);
            this.map(Type.SHORT, Type.NOTHING);
            this.map(Type.VAR_INT);
            this.handler((wrapper) -> {
               wrapper.write(Type.VAR_INT, 0);
               BlockItemPackets1_17.this.handleItemToServer((Item)wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
            });
         }
      });
      this.protocol.cancelServerbound(ServerboundPackets1_16_2.WINDOW_CONFIRMATION);
      this.protocol.registerClientbound(ClientboundPackets1_17.SPAWN_PARTICLE, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.INT);
            this.map(Type.BOOLEAN);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.INT);
            this.handler((wrapper) -> {
               int id = (Integer)wrapper.get(Type.INT, 0);
               if (id == 16) {
                  wrapper.passthrough(Type.FLOAT);
                  wrapper.passthrough(Type.FLOAT);
                  wrapper.passthrough(Type.FLOAT);
                  wrapper.passthrough(Type.FLOAT);
                  wrapper.read(Type.FLOAT);
                  wrapper.read(Type.FLOAT);
                  wrapper.read(Type.FLOAT);
               } else if (id == 37) {
                  wrapper.cancel();
               }

            });
            this.handler(BlockItemPackets1_17.this.getSpawnParticleHandler(Type.FLAT_VAR_INT_ITEM));
         }
      });
      ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_SIZE, ClientboundPackets1_16_2.WORLD_BORDER, 0);
      ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_LERP_SIZE, ClientboundPackets1_16_2.WORLD_BORDER, 1);
      ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_CENTER, ClientboundPackets1_16_2.WORLD_BORDER, 2);
      ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_INIT, ClientboundPackets1_16_2.WORLD_BORDER, 3);
      ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_WARNING_DELAY, ClientboundPackets1_16_2.WORLD_BORDER, 4);
      ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_WARNING_DISTANCE, ClientboundPackets1_16_2.WORLD_BORDER, 5);
      this.protocol.registerClientbound(ClientboundPackets1_17.UPDATE_LIGHT, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.BOOLEAN);
            this.handler((wrapper) -> {
               EntityTracker tracker = wrapper.user().getEntityTracker(Protocol1_16_4To1_17.class);
               int startFromSection = Math.max(0, -(tracker.currentMinY() >> 4));
               long[] skyLightMask = (long[])wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
               long[] blockLightMask = (long[])wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
               int cutSkyLightMask = BlockItemPackets1_17.this.cutLightMask(skyLightMask, startFromSection);
               int cutBlockLightMask = BlockItemPackets1_17.this.cutLightMask(blockLightMask, startFromSection);
               wrapper.write(Type.VAR_INT, cutSkyLightMask);
               wrapper.write(Type.VAR_INT, cutBlockLightMask);
               long[] emptySkyLightMask = (long[])wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
               long[] emptyBlockLightMask = (long[])wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
               wrapper.write(Type.VAR_INT, BlockItemPackets1_17.this.cutLightMask(emptySkyLightMask, startFromSection));
               wrapper.write(Type.VAR_INT, BlockItemPackets1_17.this.cutLightMask(emptyBlockLightMask, startFromSection));
               this.writeLightArrays(wrapper, BitSet.valueOf(skyLightMask), cutSkyLightMask, startFromSection, tracker.currentWorldSectionHeight());
               this.writeLightArrays(wrapper, BitSet.valueOf(blockLightMask), cutBlockLightMask, startFromSection, tracker.currentWorldSectionHeight());
            });
         }

         private void writeLightArrays(PacketWrapper wrapper, BitSet bitMask, int cutBitMask, int startFromSection, int sectionHeight) throws Exception {
            wrapper.read(Type.VAR_INT);
            List light = new ArrayList();

            int i;
            for(i = 0; i < startFromSection; ++i) {
               if (bitMask.get(i)) {
                  wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
               }
            }

            for(i = 0; i < 18; ++i) {
               if (this.isSet(cutBitMask, i)) {
                  light.add(wrapper.read(Type.BYTE_ARRAY_PRIMITIVE));
               }
            }

            for(i = startFromSection + 18; i < sectionHeight + 2; ++i) {
               if (bitMask.get(i)) {
                  wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
               }
            }

            Iterator var9 = light.iterator();

            while(var9.hasNext()) {
               byte[] bytes = (byte[])var9.next();
               wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, bytes);
            }

         }

         private boolean isSet(int mask, int i) {
            return (mask & 1 << i) != 0;
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_17.MULTI_BLOCK_CHANGE, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.LONG);
            this.map(Type.BOOLEAN);
            this.handler((wrapper) -> {
               long chunkPos = (Long)wrapper.get(Type.LONG, 0);
               int chunkY = (int)(chunkPos << 44 >> 44);
               if (chunkY >= 0 && chunkY <= 15) {
                  BlockChangeRecord[] records = (BlockChangeRecord[])wrapper.passthrough(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY);
                  BlockChangeRecord[] var6 = records;
                  int var7 = records.length;

                  for(int var8 = 0; var8 < var7; ++var8) {
                     BlockChangeRecord record = var6[var8];
                     record.setBlockId(((Protocol1_16_4To1_17)BlockItemPackets1_17.this.protocol).getMappingData().getNewBlockStateId(record.getBlockId()));
                  }

               } else {
                  wrapper.cancel();
               }
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_17.BLOCK_CHANGE, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.POSITION1_14);
            this.map(Type.VAR_INT);
            this.handler((wrapper) -> {
               int y = ((Position)wrapper.get(Type.POSITION1_14, 0)).getY();
               if (y >= 0 && y <= 255) {
                  wrapper.set(Type.VAR_INT, 0, ((Protocol1_16_4To1_17)BlockItemPackets1_17.this.protocol).getMappingData().getNewBlockStateId((Integer)wrapper.get(Type.VAR_INT, 0)));
               } else {
                  wrapper.cancel();
               }
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_17.CHUNK_DATA, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               EntityTracker tracker = wrapper.user().getEntityTracker(Protocol1_16_4To1_17.class);
               int currentWorldSectionHeight = tracker.currentWorldSectionHeight();
               Chunk chunk = (Chunk)wrapper.read(new Chunk1_17Type(currentWorldSectionHeight));
               wrapper.write(new Chunk1_16_2Type(), chunk);
               int startFromSection = Math.max(0, -(tracker.currentMinY() >> 4));
               chunk.setBiomeData(Arrays.copyOfRange(chunk.getBiomeData(), startFromSection * 64, startFromSection * 64 + 1024));
               chunk.setBitmask(BlockItemPackets1_17.this.cutMask(chunk.getChunkMask(), startFromSection, false));
               chunk.setChunkMask(null);
               ChunkSection[] sections = Arrays.copyOfRange(chunk.getSections(), startFromSection, startFromSection + 16);
               chunk.setSections(sections);

               for(int i = 0; i < 16; ++i) {
                  ChunkSection section = sections[i];
                  if (section != null) {
                     for(int j = 0; j < section.getPaletteSize(); ++j) {
                        int old = section.getPaletteEntry(j);
                        section.setPaletteEntry(j, ((Protocol1_16_4To1_17)BlockItemPackets1_17.this.protocol).getMappingData().getNewBlockStateId(old));
                     }
                  }
               }

            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_17.BLOCK_ENTITY_DATA, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               int y = ((Position)wrapper.passthrough(Type.POSITION1_14)).getY();
               if (y < 0 || y > 255) {
                  wrapper.cancel();
               }

            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_17.BLOCK_BREAK_ANIMATION, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.handler((wrapper) -> {
               int y = ((Position)wrapper.passthrough(Type.POSITION1_14)).getY();
               if (y < 0 || y > 255) {
                  wrapper.cancel();
               }

            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_17.MAP_DATA, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.BYTE);
            this.handler((wrapper) -> {
               wrapper.write(Type.BOOLEAN, true);
            });
            this.map(Type.BOOLEAN);
            this.handler((wrapper) -> {
               boolean hasMarkers = (Boolean)wrapper.read(Type.BOOLEAN);
               if (!hasMarkers) {
                  wrapper.write(Type.VAR_INT, 0);
               } else {
                  MapColorRewriter.getRewriteHandler(MapColorRewrites::getMappedColor).handle(wrapper);
               }

            });
         }
      });
   }

   private int cutLightMask(long[] mask, int startFromSection) {
      return mask.length == 0 ? 0 : this.cutMask(BitSet.valueOf(mask), startFromSection, true);
   }

   private int cutMask(BitSet mask, int startFromSection, boolean lightMask) {
      int cutMask = 0;
      int to = startFromSection + (lightMask ? 18 : 16);
      int i = startFromSection;

      for(int j = 0; i < to; ++j) {
         if (mask.get(i)) {
            cutMask |= 1 << j;
         }

         ++i;
      }

      return cutMask;
   }
}
