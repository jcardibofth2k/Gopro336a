package com.viaversion.viaversion.protocols.protocol1_14to1_13_2;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.CommandRewriter1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.ComponentRewriter1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.MappingData;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.metadata.MetadataRewriter1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.EntityPackets;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.PlayerPackets;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.packets.WorldPackets;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.storage.EntityTracker1_14;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;

public class Protocol1_14To1_13_2 extends AbstractProtocol {
   public static final MappingData MAPPINGS = new MappingData();
   private final EntityRewriter metadataRewriter = new MetadataRewriter1_14To1_13_2(this);
   private final ItemRewriter itemRewriter = new InventoryPackets(this);

   public Protocol1_14To1_13_2() {
      super(ClientboundPackets1_13.class, ClientboundPackets1_14.class, ServerboundPackets1_13.class, ServerboundPackets1_14.class);
   }

   protected void registerPackets() {
      this.metadataRewriter.register();
      this.itemRewriter.register();
      EntityPackets.register(this);
      WorldPackets.register(this);
      PlayerPackets.register(this);
      (new SoundRewriter(this)).registerSound(ClientboundPackets1_13.SOUND);
      (new StatisticsRewriter(this)).register(ClientboundPackets1_13.STATISTICS);
      ComponentRewriter componentRewriter = new ComponentRewriter1_14(this);
      componentRewriter.registerChatMessage(ClientboundPackets1_13.CHAT_MESSAGE);
      CommandRewriter1_14 commandRewriter = new CommandRewriter1_14(this);
      commandRewriter.registerDeclareCommands(ClientboundPackets1_13.DECLARE_COMMANDS);
      this.registerClientbound(ClientboundPackets1_13.TAGS, new PacketRemapper() {
         public void registerMap() {
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  int blockTagsSize = (Integer)wrapper.read(Type.VAR_INT);
                  wrapper.write(Type.VAR_INT, blockTagsSize + 6);

                  int itemTagsSize;
                  int i;
                  for(itemTagsSize = 0; itemTagsSize < blockTagsSize; ++itemTagsSize) {
                     wrapper.passthrough(Type.STRING);
                     int[] blockIds = (int[])wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);

                     for(i = 0; i < blockIds.length; ++i) {
                        blockIds[i] = Protocol1_14To1_13_2.this.getMappingData().getNewBlockId(blockIds[i]);
                     }
                  }

                  wrapper.write(Type.STRING, "minecraft:signs");
                  wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{Protocol1_14To1_13_2.this.getMappingData().getNewBlockId(150), Protocol1_14To1_13_2.this.getMappingData().getNewBlockId(155)});
                  wrapper.write(Type.STRING, "minecraft:wall_signs");
                  wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{Protocol1_14To1_13_2.this.getMappingData().getNewBlockId(155)});
                  wrapper.write(Type.STRING, "minecraft:standing_signs");
                  wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{Protocol1_14To1_13_2.this.getMappingData().getNewBlockId(150)});
                  wrapper.write(Type.STRING, "minecraft:fences");
                  wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{189, 248, 472, 473, 474, 475});
                  wrapper.write(Type.STRING, "minecraft:walls");
                  wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{271, 272});
                  wrapper.write(Type.STRING, "minecraft:wooden_fences");
                  wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{189, 472, 473, 474, 475});
                  itemTagsSize = (Integer)wrapper.read(Type.VAR_INT);
                  wrapper.write(Type.VAR_INT, itemTagsSize + 2);

                  int fluidTagsSize;
                  for(fluidTagsSize = 0; fluidTagsSize < itemTagsSize; ++fluidTagsSize) {
                     wrapper.passthrough(Type.STRING);
                     int[] itemIds = (int[])wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);

                     for(int j = 0; j < itemIds.length; ++j) {
                        itemIds[j] = Protocol1_14To1_13_2.this.getMappingData().getNewItemId(itemIds[j]);
                     }
                  }

                  wrapper.write(Type.STRING, "minecraft:signs");
                  wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{Protocol1_14To1_13_2.this.getMappingData().getNewItemId(541)});
                  wrapper.write(Type.STRING, "minecraft:arrows");
                  wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, new int[]{526, 825, 826});
                  fluidTagsSize = (Integer)wrapper.passthrough(Type.VAR_INT);

                  for(i = 0; i < fluidTagsSize; ++i) {
                     wrapper.passthrough(Type.STRING);
                     wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
                  }

                  wrapper.write(Type.VAR_INT, 0);
               }
            });
         }
      });
      this.cancelServerbound(ServerboundPackets1_14.SET_DIFFICULTY);
      this.cancelServerbound(ServerboundPackets1_14.LOCK_DIFFICULTY);
      this.cancelServerbound(ServerboundPackets1_14.UPDATE_JIGSAW_BLOCK);
   }

   protected void onMappingDataLoaded() {
      WorldPackets.air = this.getMappingData().getBlockStateMappings().getNewId(0);
      WorldPackets.voidAir = this.getMappingData().getBlockStateMappings().getNewId(8591);
      WorldPackets.caveAir = this.getMappingData().getBlockStateMappings().getNewId(8592);
   }

   public void init(UserConnection userConnection) {
      userConnection.addEntityTracker(this.getClass(), new EntityTracker1_14(userConnection));
      if (!userConnection.has(ClientWorld.class)) {
         userConnection.put(new ClientWorld(userConnection));
      }

   }

   public MappingData getMappingData() {
      return MAPPINGS;
   }

   public EntityRewriter getEntityRewriter() {
      return this.metadataRewriter;
   }

   public ItemRewriter getItemRewriter() {
      return this.itemRewriter;
   }
}
