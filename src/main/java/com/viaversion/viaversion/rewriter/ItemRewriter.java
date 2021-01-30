package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Type;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class ItemRewriter extends RewriterBase implements com.viaversion.viaversion.api.rewriter.ItemRewriter {
   protected ItemRewriter(Protocol protocol) {
      super(protocol);
   }

   @Nullable
   public Item handleItemToClient(@Nullable Item item) {
      if (item == null) {
         return null;
      } else {
         if (this.protocol.getMappingData() != null && this.protocol.getMappingData().getItemMappings() != null) {
            item.setIdentifier(this.protocol.getMappingData().getNewItemId(item.identifier()));
         }

         return item;
      }
   }

   @Nullable
   public Item handleItemToServer(@Nullable Item item) {
      if (item == null) {
         return null;
      } else {
         if (this.protocol.getMappingData() != null && this.protocol.getMappingData().getItemMappings() != null) {
            item.setIdentifier(this.protocol.getMappingData().getOldItemId(item.identifier()));
         }

         return item;
      }
   }

   public void registerWindowItems(ClientboundPacketType packetType, final Type type) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(type);
            this.handler(ItemRewriter.this.itemArrayHandler(type));
         }
      });
   }

   public void registerSetSlot(ClientboundPacketType packetType, final Type type) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.SHORT);
            this.map(type);
            this.handler(ItemRewriter.this.itemToClientHandler(type));
         }
      });
   }

   public void registerEntityEquipment(ClientboundPacketType packetType, final Type type) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(type);
            this.handler(ItemRewriter.this.itemToClientHandler(type));
         }
      });
   }

   public void registerEntityEquipmentArray(ClientboundPacketType packetType, final Type type) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.handler((wrapper) -> {
               byte slot;
               do {
                  slot = (Byte)wrapper.passthrough(Type.BYTE);
                  ItemRewriter.this.handleItemToClient((Item)wrapper.passthrough(type));
               } while((slot & -128) != 0);

            });
         }
      });
   }

   public void registerCreativeInvAction(ServerboundPacketType packetType, final Type type) {
      this.protocol.registerServerbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.SHORT);
            this.map(type);
            this.handler(ItemRewriter.this.itemToServerHandler(type));
         }
      });
   }

   public void registerClickWindow(ServerboundPacketType packetType, final Type type) {
      this.protocol.registerServerbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.SHORT);
            this.map(Type.BYTE);
            this.map(Type.SHORT);
            this.map(Type.VAR_INT);
            this.map(type);
            this.handler(ItemRewriter.this.itemToServerHandler(type));
         }
      });
   }

   public void registerClickWindow1_17(ServerboundPacketType packetType, final Type type) {
      this.protocol.registerServerbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.SHORT);
            this.map(Type.BYTE);
            this.map(Type.VAR_INT);
            this.handler((wrapper) -> {
               int length = (Integer)wrapper.passthrough(Type.VAR_INT);

               for(int i = 0; i < length; ++i) {
                  wrapper.passthrough(Type.SHORT);
                  ItemRewriter.this.handleItemToServer((Item)wrapper.passthrough(type));
               }

               ItemRewriter.this.handleItemToServer((Item)wrapper.passthrough(type));
            });
         }
      });
   }

   public void registerSetCooldown(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               int itemId = (Integer)wrapper.read(Type.VAR_INT);
               wrapper.write(Type.VAR_INT, ItemRewriter.this.protocol.getMappingData().getNewItemId(itemId));
            });
         }
      });
   }

   public void registerTradeList(ClientboundPacketType packetType, final Type type) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               wrapper.passthrough(Type.VAR_INT);
               int size = (Short)wrapper.passthrough(Type.UNSIGNED_BYTE);

               for(int i = 0; i < size; ++i) {
                  ItemRewriter.this.handleItemToClient((Item)wrapper.passthrough(type));
                  ItemRewriter.this.handleItemToClient((Item)wrapper.passthrough(type));
                  if ((Boolean)wrapper.passthrough(Type.BOOLEAN)) {
                     ItemRewriter.this.handleItemToClient((Item)wrapper.passthrough(type));
                  }

                  wrapper.passthrough(Type.BOOLEAN);
                  wrapper.passthrough(Type.INT);
                  wrapper.passthrough(Type.INT);
                  wrapper.passthrough(Type.INT);
                  wrapper.passthrough(Type.INT);
                  wrapper.passthrough(Type.FLOAT);
                  wrapper.passthrough(Type.INT);
               }

            });
         }
      });
   }

   public void registerAdvancements(ClientboundPacketType packetType, final Type type) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               wrapper.passthrough(Type.BOOLEAN);
               int size = (Integer)wrapper.passthrough(Type.VAR_INT);

               for(int i = 0; i < size; ++i) {
                  wrapper.passthrough(Type.STRING);
                  if ((Boolean)wrapper.passthrough(Type.BOOLEAN)) {
                     wrapper.passthrough(Type.STRING);
                  }

                  int arrayLength;
                  if ((Boolean)wrapper.passthrough(Type.BOOLEAN)) {
                     wrapper.passthrough(Type.COMPONENT);
                     wrapper.passthrough(Type.COMPONENT);
                     ItemRewriter.this.handleItemToClient((Item)wrapper.passthrough(type));
                     wrapper.passthrough(Type.VAR_INT);
                     arrayLength = (Integer)wrapper.passthrough(Type.INT);
                     if ((arrayLength & 1) != 0) {
                        wrapper.passthrough(Type.STRING);
                     }

                     wrapper.passthrough(Type.FLOAT);
                     wrapper.passthrough(Type.FLOAT);
                  }

                  wrapper.passthrough(Type.STRING_ARRAY);
                  arrayLength = (Integer)wrapper.passthrough(Type.VAR_INT);

                  for(int array = 0; array < arrayLength; ++array) {
                     wrapper.passthrough(Type.STRING_ARRAY);
                  }
               }

            });
         }
      });
   }

   public void registerSpawnParticle(ClientboundPacketType packetType, final Type itemType, final Type coordType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.INT);
            this.map(Type.BOOLEAN);
            this.map(coordType);
            this.map(coordType);
            this.map(coordType);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.INT);
            this.handler(ItemRewriter.this.getSpawnParticleHandler(itemType));
         }
      });
   }

   public PacketHandler getSpawnParticleHandler(Type itemType) {
      return (wrapper) -> {
         int id = (Integer)wrapper.get(Type.INT, 0);
         if (id != -1) {
            ParticleMappings mappings = this.protocol.getMappingData().getParticleMappings();
            int newId;
            if (id != mappings.getBlockId() && id != mappings.getFallingDustId()) {
               if (id == mappings.getItemId()) {
                  this.handleItemToClient((Item)wrapper.passthrough(itemType));
               }
            } else {
               newId = (Integer)wrapper.passthrough(Type.VAR_INT);
               wrapper.set(Type.VAR_INT, 0, this.protocol.getMappingData().getNewBlockStateId(newId));
            }

            newId = this.protocol.getMappingData().getNewParticleId(id);
            if (newId != id) {
               wrapper.set(Type.INT, 0, newId);
            }

         }
      };
   }

   public PacketHandler itemArrayHandler(Type type) {
      return (wrapper) -> {
         Item[] items = (Item[])wrapper.get(type, 0);
         Item[] var4 = items;
         int var5 = items.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Item item = var4[var6];
            this.handleItemToClient(item);
         }

      };
   }

   public PacketHandler itemToClientHandler(Type type) {
      return (wrapper) -> {
         this.handleItemToClient((Item)wrapper.get(type, 0));
      };
   }

   public PacketHandler itemToServerHandler(Type type) {
      return (wrapper) -> {
         this.handleItemToServer((Item)wrapper.get(type, 0));
      };
   }
}
