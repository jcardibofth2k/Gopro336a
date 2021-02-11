package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets;

import com.viaversion.viabackwards.api.rewriters.LegacyEntityRewriter;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.Protocol1_11_1To1_12;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.ParrotStorage;
import com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data.ShoulderTracker;
import com.viaversion.viabackwards.utils.Block;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_12Types;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.ObjectType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_12;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_12;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.ClientboundPackets1_12;
import io.netty.buffer.ByteBuf;
import java.util.Optional;

public class EntityPackets1_12 extends LegacyEntityRewriter {
   public EntityPackets1_12(Protocol1_11_1To1_12 protocol) {
      super(protocol);
   }

   protected void registerPackets() {
      this.protocol.registerClientbound(ClientboundPackets1_12.SPAWN_ENTITY, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.BYTE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.INT);
            this.handler(EntityPackets1_12.this.getObjectTrackerHandler());
            this.handler(EntityPackets1_12.this.getObjectRewriter((id) -> {
               return Entity1_12Types.ObjectType.findById(id).orElse(null);
            }));
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  Optional type = Entity1_12Types.ObjectType.findById((Byte)wrapper.get(Type.BYTE, 0));
                  if (type.isPresent() && type.get() == Entity1_12Types.ObjectType.FALLING_BLOCK) {
                     int objectData = (Integer)wrapper.get(Type.INT, 0);
                     int objType = objectData & 4095;
                     int data = objectData >> 12 & 15;
                     Block block = ((Protocol1_11_1To1_12)EntityPackets1_12.this.protocol).getItemRewriter().handleBlock(objType, data);
                     if (block == null) {
                        return;
                     }

                     wrapper.set(Type.INT, 0, block.getId() | block.getData() << 12);
                  }

               }
            });
         }
      });
      this.registerTracker(ClientboundPackets1_12.SPAWN_EXPERIENCE_ORB, Entity1_12Types.EntityType.EXPERIENCE_ORB);
      this.registerTracker(ClientboundPackets1_12.SPAWN_GLOBAL_ENTITY, Entity1_12Types.EntityType.WEATHER);
      this.protocol.registerClientbound(ClientboundPackets1_12.SPAWN_MOB, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.VAR_INT);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Types1_12.METADATA_LIST);
            this.handler(EntityPackets1_12.this.getTrackerHandler());
            this.handler(EntityPackets1_12.this.getMobSpawnRewriter(Types1_12.METADATA_LIST));
         }
      });
      this.registerTracker(ClientboundPackets1_12.SPAWN_PAINTING, Entity1_12Types.EntityType.PAINTING);
      this.protocol.registerClientbound(ClientboundPackets1_12.SPAWN_PLAYER, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Types1_12.METADATA_LIST);
            this.handler(EntityPackets1_12.this.getTrackerAndMetaHandler(Types1_12.METADATA_LIST, Entity1_12Types.EntityType.PLAYER));
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_12.JOIN_GAME, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.INT);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.INT);
            this.handler(EntityPackets1_12.this.getTrackerHandler(Entity1_12Types.EntityType.PLAYER, Type.INT));
            this.handler(EntityPackets1_12.this.getDimensionHandler(1));
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper wrapper) throws Exception {
                  ShoulderTracker tracker = (ShoulderTracker)wrapper.user().get(ShoulderTracker.class);
                  tracker.setEntityId((Integer)wrapper.get(Type.INT, 0));
               }
            });
            this.handler(new PacketHandler() {
               public void handle(PacketWrapper packetWrapper) throws Exception {
                  PacketWrapper wrapper = PacketWrapper.create(7, null, packetWrapper.user());
                  wrapper.write(Type.VAR_INT, 1);
                  wrapper.write(Type.STRING, "achievement.openInventory");
                  wrapper.write(Type.VAR_INT, 1);
                  wrapper.scheduleSend(Protocol1_11_1To1_12.class);
               }
            });
         }
      });
      this.registerRespawn(ClientboundPackets1_12.RESPAWN);
      this.registerRemoveEntities(ClientboundPackets1_12.DESTROY_ENTITIES);
      this.registerMetadataRewriter(ClientboundPackets1_12.ENTITY_METADATA, Types1_12.METADATA_LIST);
      this.protocol.registerClientbound(ClientboundPackets1_12.ENTITY_PROPERTIES, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.INT);
            this.handler((wrapper) -> {
               int size = (Integer)wrapper.get(Type.INT, 0);
               int newSize = size;

               for(int i = 0; i < size; ++i) {
                  String key = (String)wrapper.read(Type.STRING);
                  int modSize;
                  int j;
                  if (key.equals("generic.flyingSpeed")) {
                     --newSize;
                     wrapper.read(Type.DOUBLE);
                     modSize = (Integer)wrapper.read(Type.VAR_INT);

                     for(j = 0; j < modSize; ++j) {
                        wrapper.read(Type.UUID);
                        wrapper.read(Type.DOUBLE);
                        wrapper.read(Type.BYTE);
                     }
                  } else {
                     wrapper.write(Type.STRING, key);
                     wrapper.passthrough(Type.DOUBLE);
                     modSize = (Integer)wrapper.passthrough(Type.VAR_INT);

                     for(j = 0; j < modSize; ++j) {
                        wrapper.passthrough(Type.UUID);
                        wrapper.passthrough(Type.DOUBLE);
                        wrapper.passthrough(Type.BYTE);
                     }
                  }
               }

               if (newSize != size) {
                  wrapper.set(Type.INT, 0, newSize);
               }

            });
         }
      });
   }

   protected void registerRewrites() {
      this.mapEntityTypeWithData(Entity1_12Types.EntityType.PARROT, Entity1_12Types.EntityType.BAT).mobName("Parrot").spawnMetadata((storage) -> {
         storage.add(new Metadata(12, MetaType1_12.Byte, (byte)0));
      });
      this.mapEntityTypeWithData(Entity1_12Types.EntityType.ILLUSION_ILLAGER, Entity1_12Types.EntityType.EVOCATION_ILLAGER).mobName("Illusioner");
      this.filter().filterFamily(Entity1_12Types.EntityType.EVOCATION_ILLAGER).cancel(12);
      this.filter().filterFamily(Entity1_12Types.EntityType.EVOCATION_ILLAGER).index(13).toIndex(12);
      this.filter().type(Entity1_12Types.EntityType.ILLUSION_ILLAGER).index(0).handler((event, meta) -> {
         byte mask = (Byte)meta.getValue();
         if ((mask & 32) == 32) {
            mask &= -33;
         }

         meta.setValue(mask);
      });
      this.filter().filterFamily(Entity1_12Types.EntityType.PARROT).handler((event, meta) -> {
         StoredEntityData data = this.storedEntityData(event);
         if (!data.has(ParrotStorage.class)) {
            data.put(new ParrotStorage());
         }

      });
      this.filter().type(Entity1_12Types.EntityType.PARROT).cancel(12);
      this.filter().type(Entity1_12Types.EntityType.PARROT).index(13).handler((event, meta) -> {
         StoredEntityData data = this.storedEntityData(event);
         ParrotStorage storage = (ParrotStorage)data.get(ParrotStorage.class);
         boolean isSitting = ((Byte)meta.getValue() & 1) == 1;
         boolean isTamed = ((Byte)meta.getValue() & 4) == 4;
         if (!storage.isTamed() && isTamed) {
         }

         storage.setTamed(isTamed);
         if (isSitting) {
            event.setIndex(12);
            meta.setValue((byte)1);
            storage.setSitting(true);
         } else if (storage.isSitting()) {
            event.setIndex(12);
            meta.setValue((byte)0);
            storage.setSitting(false);
         } else {
            event.cancel();
         }

      });
      this.filter().type(Entity1_12Types.EntityType.PARROT).cancel(14);
      this.filter().type(Entity1_12Types.EntityType.PARROT).cancel(15);
      this.filter().type(Entity1_12Types.EntityType.PLAYER).index(15).handler((event, meta) -> {
         CompoundTag tag = (CompoundTag)meta.getValue();
         ShoulderTracker tracker = (ShoulderTracker)event.user().get(ShoulderTracker.class);
         if (tag.isEmpty() && tracker.getLeftShoulder() != null) {
            tracker.setLeftShoulder(null);
            tracker.update();
         } else if (tag.contains("id") && event.entityId() == tracker.getEntityId()) {
            String id = (String)tag.get("id").getValue();
            if (tracker.getLeftShoulder() == null || !tracker.getLeftShoulder().equals(id)) {
               tracker.setLeftShoulder(id);
               tracker.update();
            }
         }

         event.cancel();
      });
      this.filter().type(Entity1_12Types.EntityType.PLAYER).index(16).handler((event, meta) -> {
         CompoundTag tag = (CompoundTag)event.meta().getValue();
         ShoulderTracker tracker = (ShoulderTracker)event.user().get(ShoulderTracker.class);
         if (tag.isEmpty() && tracker.getRightShoulder() != null) {
            tracker.setRightShoulder(null);
            tracker.update();
         } else if (tag.contains("id") && event.entityId() == tracker.getEntityId()) {
            String id = (String)tag.get("id").getValue();
            if (tracker.getRightShoulder() == null || !tracker.getRightShoulder().equals(id)) {
               tracker.setRightShoulder(id);
               tracker.update();
            }
         }

         event.cancel();
      });
   }

   public EntityType typeFromId(int typeId) {
      return Entity1_12Types.getTypeFromId(typeId, false);
   }

   protected EntityType getObjectTypeFromId(int typeId) {
      return Entity1_12Types.getTypeFromId(typeId, true);
   }
}
