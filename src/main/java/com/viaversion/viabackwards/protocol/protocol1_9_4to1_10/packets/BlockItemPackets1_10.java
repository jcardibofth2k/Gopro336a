package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets;

import com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;

public class BlockItemPackets1_10 extends LegacyBlockItemRewriter {
   public BlockItemPackets1_10(Protocol1_9_4To1_10 protocol) {
      super(protocol);
   }

   protected void registerPackets() {
      this.registerSetSlot(ClientboundPackets1_9_3.SET_SLOT, Type.ITEM);
      this.registerWindowItems(ClientboundPackets1_9_3.WINDOW_ITEMS, Type.ITEM_ARRAY);
      this.registerEntityEquipment(ClientboundPackets1_9_3.ENTITY_EQUIPMENT, Type.ITEM);
      this.protocol.registerClientbound(ClientboundPackets1_9_3.PLUGIN_MESSAGE, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.STRING);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  if (((String)wrapper.get(Type.STRING, 0)).equalsIgnoreCase("MC|TrList")) {
                     wrapper.passthrough(Type.INT);
                     int size = (Short)wrapper.passthrough(Type.UNSIGNED_BYTE);

                     for(int i = 0; i < size; ++i) {
                        wrapper.write(Type.ITEM, BlockItemPackets1_10.this.handleItemToClient((Item)wrapper.read(Type.ITEM)));
                        wrapper.write(Type.ITEM, BlockItemPackets1_10.this.handleItemToClient((Item)wrapper.read(Type.ITEM)));
                        boolean secondItem = (Boolean)wrapper.passthrough(Type.BOOLEAN);
                        if (secondItem) {
                           wrapper.write(Type.ITEM, BlockItemPackets1_10.this.handleItemToClient((Item)wrapper.read(Type.ITEM)));
                        }

                        wrapper.passthrough(Type.BOOLEAN);
                        wrapper.passthrough(Type.INT);
                        wrapper.passthrough(Type.INT);
                     }
                  }

               }
            });
         }
      });
      this.registerClickWindow(ServerboundPackets1_9_3.CLICK_WINDOW, Type.ITEM);
      this.registerCreativeInvAction(ServerboundPackets1_9_3.CREATIVE_INVENTORY_ACTION, Type.ITEM);
      this.protocol.registerClientbound(ClientboundPackets1_9_3.CHUNK_DATA, new PacketRemapper() {
         public void registerMap() {
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
                  Chunk1_9_3_4Type type = new Chunk1_9_3_4Type(clientWorld);
                  Chunk chunk = (Chunk)wrapper.passthrough(type);
                  BlockItemPackets1_10.this.handleChunk(chunk);
               }
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_9_3.BLOCK_CHANGE, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.POSITION);
            this.map(Type.VAR_INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int idx = (Integer)wrapper.get(Type.VAR_INT, 0);
                  wrapper.set(Type.VAR_INT, 0, BlockItemPackets1_10.this.handleBlockID(idx));
               }
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_9_3.MULTI_BLOCK_CHANGE, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  BlockChangeRecord[] var2 = (BlockChangeRecord[])wrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0);
                  int var3 = var2.length;

                  for(int var4 = 0; var4 < var3; ++var4) {
                     BlockChangeRecord record = var2[var4];
                     record.setBlockId(BlockItemPackets1_10.this.handleBlockID(record.getBlockId()));
                  }

               }
            });
         }
      });
      ((Protocol1_9_4To1_10)this.protocol).getEntityRewriter().filter().handler((event, meta) -> {
         if (meta.metaType().type().equals(Type.ITEM)) {
            meta.setValue(this.handleItemToClient((Item)meta.getValue()));
         }

      });
      this.protocol.registerClientbound(ClientboundPackets1_9_3.SPAWN_PARTICLE, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.INT);
            this.map(Type.BOOLEAN);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int id = (Integer)wrapper.get(Type.INT, 0);
                  if (id == 46) {
                     wrapper.set(Type.INT, 0, 38);
                  }

               }
            });
         }
      });
   }
}
