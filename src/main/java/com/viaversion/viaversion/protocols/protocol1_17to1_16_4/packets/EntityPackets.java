package com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets;

import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16_2Types;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_17Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_17;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.api.type.types.version.Types1_17;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.Protocol1_17To1_16_4;
import com.viaversion.viaversion.rewriter.EntityRewriter;

public final class EntityPackets extends EntityRewriter {
   public EntityPackets(Protocol1_17To1_16_4 protocol) {
      super(protocol);
      this.mapTypes(Entity1_16_2Types.values(), Entity1_17Types.class);
   }

   public void registerPackets() {
      this.registerTrackerWithData(ClientboundPackets1_16_2.SPAWN_ENTITY, Entity1_17Types.FALLING_BLOCK);
      this.registerTracker(ClientboundPackets1_16_2.SPAWN_MOB);
      this.registerTracker(ClientboundPackets1_16_2.SPAWN_PLAYER, Entity1_17Types.PLAYER);
      this.registerMetadataRewriter(ClientboundPackets1_16_2.ENTITY_METADATA, Types1_16.METADATA_LIST, Types1_17.METADATA_LIST);
      this.protocol.registerClientbound(ClientboundPackets1_16_2.DESTROY_ENTITIES, null, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               int[] entityIds = (int[])wrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
               wrapper.cancel();
               EntityTracker entityTracker = wrapper.user().getEntityTracker(Protocol1_17To1_16_4.class);
               int[] var3 = entityIds;
               int var4 = entityIds.length;

               for(int var5 = 0; var5 < var4; ++var5) {
                  int entityId = var3[var5];
                  entityTracker.removeEntity(entityId);
                  PacketWrapper newPacket = wrapper.create(ClientboundPackets1_17.REMOVE_ENTITY);
                  newPacket.write(Type.VAR_INT, entityId);
                  newPacket.send(Protocol1_17To1_16_4.class);
               }

            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_16_2.ENTITY_PROPERTIES, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.handler((wrapper) -> {
               wrapper.write(Type.VAR_INT, wrapper.read(Type.INT));
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_16_2.PLAYER_POSITION, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.BYTE);
            this.map(Type.VAR_INT);
            this.handler((wrapper) -> {
               wrapper.write(Type.BOOLEAN, false);
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_16_2.COMBAT_EVENT, null, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               int type = (Integer)wrapper.read(Type.VAR_INT);
               ClientboundPackets1_17 packetType;
               switch(type) {
               case 0:
                  packetType = ClientboundPackets1_17.COMBAT_ENTER;
                  break;
               case 1:
                  packetType = ClientboundPackets1_17.COMBAT_END;
                  break;
               case 2:
                  packetType = ClientboundPackets1_17.COMBAT_KILL;
                  break;
               default:
                  throw new IllegalArgumentException("Invalid combat type received: " + type);
               }

               wrapper.setId(packetType.getId());
            });
         }
      });
      this.protocol.cancelClientbound(ClientboundPackets1_16_2.ENTITY_MOVEMENT);
   }

   protected void registerRewrites() {
      this.filter().handler((event, meta) -> {
         meta.setMetaType(MetaType1_17.byId(meta.metaType().typeId()));
         if (meta.metaType() == MetaType1_17.POSE) {
            int pose = (Integer)meta.value();
            if (pose > 5) {
               meta.setValue(pose + 1);
            }
         }

      });
      this.registerMetaTypeHandler(MetaType1_17.ITEM, MetaType1_17.BLOCK_STATE, MetaType1_17.PARTICLE);
      this.filter().filterFamily(Entity1_17Types.ENTITY).addIndex(7);
      this.filter().filterFamily(Entity1_17Types.MINECART_ABSTRACT).index(11).handler((event, meta) -> {
         int data = (Integer)meta.getValue();
         meta.setValue(this.protocol.getMappingData().getNewBlockStateId(data));
      });
      this.filter().type(Entity1_17Types.SHULKER).removeIndex(17);
   }

   public EntityType typeFromId(int type) {
      return Entity1_17Types.getTypeFromId(type);
   }
}
