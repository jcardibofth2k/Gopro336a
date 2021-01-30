package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.entities.storage.EntityData;
import com.viaversion.viabackwards.api.entities.storage.EntityObjectData;
import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.ObjectType;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_9;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class LegacyEntityRewriter extends EntityRewriterBase {
   private final Map objectTypes;

   protected LegacyEntityRewriter(BackwardsProtocol protocol) {
      this(protocol, MetaType1_9.String, MetaType1_9.Boolean);
   }

   protected LegacyEntityRewriter(BackwardsProtocol protocol, MetaType displayType, MetaType displayVisibilityType) {
      super(protocol, displayType, 2, displayVisibilityType, 3);
      this.objectTypes = new HashMap();
   }

   protected EntityObjectData mapObjectType(ObjectType oldObjectType, ObjectType replacement, int data) {
      EntityObjectData entData = new EntityObjectData(oldObjectType.getId(), true, replacement.getId(), data);
      this.objectTypes.put(oldObjectType, entData);
      return entData;
   }

   @Nullable
   protected EntityData getObjectData(ObjectType type) {
      return (EntityData)this.objectTypes.get(type);
   }

   protected void registerRespawn(ClientboundPacketType packetType) {
      ((BackwardsProtocol)this.protocol).registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.INT);
            this.handler((wrapper) -> {
               ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
               clientWorld.setEnvironment((Integer)wrapper.get(Type.INT, 0));
            });
         }
      });
   }

   protected void registerJoinGame(ClientboundPacketType packetType, final EntityType playerType) {
      ((BackwardsProtocol)this.protocol).registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.INT);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.INT);
            this.handler((wrapper) -> {
               ClientWorld clientChunks = (ClientWorld)wrapper.user().get(ClientWorld.class);
               clientChunks.setEnvironment((Integer)wrapper.get(Type.INT, 1));
               LegacyEntityRewriter.this.addTrackedEntity(wrapper, (Integer)wrapper.get(Type.INT, 0), playerType);
            });
         }
      });
   }

   public void registerMetadataRewriter(ClientboundPacketType packetType, final Type oldMetaType, final Type newMetaType) {
      ((BackwardsProtocol)this.protocol).registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            if (oldMetaType != null) {
               this.map(oldMetaType, newMetaType);
            } else {
               this.map(newMetaType);
            }

            this.handler((wrapper) -> {
               List metadata = (List)wrapper.get(newMetaType, 0);
               LegacyEntityRewriter.this.handleMetadata((Integer)wrapper.get(Type.VAR_INT, 0), metadata, wrapper.user());
            });
         }
      });
   }

   public void registerMetadataRewriter(ClientboundPacketType packetType, Type metaType) {
      this.registerMetadataRewriter(packetType, (Type)null, metaType);
   }

   protected PacketHandler getMobSpawnRewriter(Type metaType) {
      return (wrapper) -> {
         int entityId = (Integer)wrapper.get(Type.VAR_INT, 0);
         EntityType type = this.tracker(wrapper.user()).entityType(entityId);
         List metadata = (List)wrapper.get(metaType, 0);
         this.handleMetadata(entityId, metadata, wrapper.user());
         EntityData entityData = this.entityDataForType(type);
         if (entityData != null) {
            wrapper.set(Type.VAR_INT, 1, entityData.replacementId());
            if (entityData.hasBaseMeta()) {
               entityData.defaultMeta().createMeta(new WrappedMetadata(metadata));
            }
         }

      };
   }

   protected PacketHandler getObjectTrackerHandler() {
      return (wrapper) -> {
         this.addTrackedEntity(wrapper, (Integer)wrapper.get(Type.VAR_INT, 0), this.getObjectTypeFromId((Byte)wrapper.get(Type.BYTE, 0)));
      };
   }

   protected PacketHandler getTrackerAndMetaHandler(Type metaType, EntityType entityType) {
      return (wrapper) -> {
         this.addTrackedEntity(wrapper, (Integer)wrapper.get(Type.VAR_INT, 0), entityType);
         List metadata = (List)wrapper.get(metaType, 0);
         this.handleMetadata((Integer)wrapper.get(Type.VAR_INT, 0), metadata, wrapper.user());
      };
   }

   protected PacketHandler getObjectRewriter(Function objectGetter) {
      return (wrapper) -> {
         ObjectType type = (ObjectType)objectGetter.apply((Byte)wrapper.get(Type.BYTE, 0));
         if (type == null) {
            ViaBackwards.getPlatform().getLogger().warning("Could not find Entity Type" + wrapper.get(Type.BYTE, 0));
         } else {
            EntityData data = this.getObjectData(type);
            if (data != null) {
               wrapper.set(Type.BYTE, 0, (byte)data.replacementId());
               if (data.objectData() != -1) {
                  wrapper.set(Type.INT, 0, data.objectData());
               }
            }

         }
      };
   }

   protected EntityType getObjectTypeFromId(int typeId) {
      return this.typeFromId(typeId);
   }

   /** @deprecated */
   @Deprecated
   protected void addTrackedEntity(PacketWrapper wrapper, int entityId, EntityType type) throws Exception {
      this.tracker(wrapper.user()).addEntity(entityId, type);
   }
}
