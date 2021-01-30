package com.viaversion.viaversion.protocols.protocol1_13_1to1_13;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_13Types;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.metadata.MetadataRewriter1_13_1To1_13;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.EntityPackets;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.WorldPackets;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;

public class Protocol1_13_1To1_13 extends AbstractProtocol {
   public static final MappingData MAPPINGS = new MappingDataBase("1.13", "1.13.2", true);
   private final EntityRewriter entityRewriter = new MetadataRewriter1_13_1To1_13(this);
   private final ItemRewriter itemRewriter = new InventoryPackets(this);

   public Protocol1_13_1To1_13() {
      super(ClientboundPackets1_13.class, ClientboundPackets1_13.class, ServerboundPackets1_13.class, ServerboundPackets1_13.class);
   }

   protected void registerPackets() {
      this.entityRewriter.register();
      this.itemRewriter.register();
      EntityPackets.register(this);
      WorldPackets.register(this);
      this.registerServerbound(ServerboundPackets1_13.TAB_COMPLETE, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.STRING, new ValueTransformer(Type.STRING) {
               public String transform(PacketWrapper wrapper, String inputValue) {
                  return inputValue.startsWith("/") ? inputValue.substring(1) : inputValue;
               }
            });
         }
      });
      this.registerServerbound(ServerboundPackets1_13.EDIT_BOOK, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.FLAT_ITEM);
            this.map(Type.BOOLEAN);
            this.handler((wrapper) -> {
               Item item = (Item)wrapper.get(Type.FLAT_ITEM, 0);
               Protocol1_13_1To1_13.this.itemRewriter.handleItemToServer(item);
            });
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int hand = (Integer)wrapper.read(Type.VAR_INT);
                  if (hand == 1) {
                     wrapper.cancel();
                  }

               }
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_13.TAB_COMPLETE, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int start = (Integer)wrapper.get(Type.VAR_INT, 1);
                  wrapper.set(Type.VAR_INT, 1, start + 1);
                  int count = (Integer)wrapper.get(Type.VAR_INT, 3);

                  for(int i = 0; i < count; ++i) {
                     wrapper.passthrough(Type.STRING);
                     boolean hasTooltip = (Boolean)wrapper.passthrough(Type.BOOLEAN);
                     if (hasTooltip) {
                        wrapper.passthrough(Type.STRING);
                     }
                  }

               }
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_13.BOSSBAR, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.UUID);
            this.map(Type.VAR_INT);
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int action = (Integer)wrapper.get(Type.VAR_INT, 0);
                  if (action == 0) {
                     wrapper.passthrough(Type.COMPONENT);
                     wrapper.passthrough(Type.FLOAT);
                     wrapper.passthrough(Type.VAR_INT);
                     wrapper.passthrough(Type.VAR_INT);
                     short flags = (short)(Byte)wrapper.read(Type.BYTE);
                     if ((flags & 2) != 0) {
                        flags = (short)(flags | 4);
                     }

                     wrapper.write(Type.UNSIGNED_BYTE, flags);
                  }

               }
            });
         }
      });
      (new TagRewriter(this)).register(ClientboundPackets1_13.TAGS, RegistryType.ITEM);
      (new StatisticsRewriter(this)).register(ClientboundPackets1_13.STATISTICS);
   }

   public void init(UserConnection userConnection) {
      userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_13Types.EntityType.PLAYER));
      if (!userConnection.has(ClientWorld.class)) {
         userConnection.put(new ClientWorld(userConnection));
      }

   }

   public MappingData getMappingData() {
      return MAPPINGS;
   }

   public EntityRewriter getEntityRewriter() {
      return this.entityRewriter;
   }

   public ItemRewriter getItemRewriter() {
      return this.itemRewriter;
   }
}
