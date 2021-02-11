package com.viaversion.viabackwards.api.rewriters;

import com.google.common.base.Preconditions;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.entities.storage.EntityData;
import com.viaversion.viabackwards.api.entities.storage.WrappedMetadata;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
import java.util.Iterator;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class EntityRewriterBase extends com.viaversion.viaversion.rewriter.EntityRewriter {
   private final Int2ObjectMap entityDataMappings = new Int2ObjectOpenHashMap();
   private final MetaType displayNameMetaType;
   private final MetaType displayVisibilityMetaType;
   private final int displayNameIndex;
   private final int displayVisibilityIndex;

   EntityRewriterBase(BackwardsProtocol protocol, MetaType displayNameMetaType, int displayNameIndex, MetaType displayVisibilityMetaType, int displayVisibilityIndex) {
      super(protocol, false);
      this.displayNameMetaType = displayNameMetaType;
      this.displayNameIndex = displayNameIndex;
      this.displayVisibilityMetaType = displayVisibilityMetaType;
      this.displayVisibilityIndex = displayVisibilityIndex;
   }

   public void handleMetadata(int entityId, List metadataList, UserConnection connection) {
      super.handleMetadata(entityId, metadataList, connection);
      EntityType type = this.tracker(connection).entityType(entityId);
      if (type != null) {
         EntityData entityData = this.entityDataForType(type);
         Metadata meta = this.getMeta(this.displayNameIndex, metadataList);
         if (meta != null && entityData != null && entityData.mobName() != null && (meta.getValue() == null || meta.getValue().toString().isEmpty()) && meta.metaType().typeId() == this.displayNameMetaType.typeId()) {
            meta.setValue(entityData.mobName());
            if (ViaBackwards.getConfig().alwaysShowOriginalMobName()) {
               this.removeMeta(this.displayVisibilityIndex, metadataList);
               metadataList.add(new Metadata(this.displayVisibilityIndex, this.displayVisibilityMetaType, true));
            }
         }

         if (entityData != null && entityData != null && entityData.hasBaseMeta()) {
            entityData.defaultMeta().createMeta(new WrappedMetadata(metadataList));
         }

      }
   }

   @Nullable
   protected Metadata getMeta(int metaIndex, List metadataList) {
      Iterator var3 = metadataList.iterator();

      Metadata metadata;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         metadata = (Metadata)var3.next();
      } while(metadata.method_71() != metaIndex);

      return metadata;
   }

   protected void removeMeta(int metaIndex, List metadataList) {
      metadataList.removeIf((meta) -> {
         return meta.method_71() == metaIndex;
      });
   }

   protected boolean hasData(EntityType type) {
      return this.entityDataMappings.containsKey(type.getId());
   }

   @Nullable
   protected EntityData entityDataForType(EntityType type) {
      return (EntityData)this.entityDataMappings.get(type.getId());
   }

   @Nullable
   protected StoredEntityData storedEntityData(MetaHandlerEvent event) {
      return this.tracker(event.user()).entityData(event.entityId());
   }

   protected EntityData mapEntityTypeWithData(EntityType type, EntityType mappedType) {
      Preconditions.checkArgument(type.getClass() == mappedType.getClass());
      int mappedReplacementId = this.newEntityId(mappedType.getId());
      EntityData data = new EntityData(type.getId(), mappedReplacementId);
      this.mapEntityType(type.getId(), mappedReplacementId);
      this.entityDataMappings.put(type.getId(), data);
      return data;
   }

   public void mapTypes(EntityType[] oldTypes, Class newTypeClass) {
      if (this.typeMappings == null) {
         this.typeMappings = new Int2IntOpenHashMap(oldTypes.length, 1.0F);
         this.typeMappings.defaultReturnValue(-1);
      }

      EntityType[] var3 = oldTypes;
      int var4 = oldTypes.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         EntityType oldType = var3[var5];

         try {
            Enum newType = Enum.valueOf(newTypeClass, oldType.name());
            this.typeMappings.put(oldType.getId(), ((EntityType)newType).getId());
         } catch (IllegalArgumentException var8) {
         }
      }

   }

   public void registerMetaTypeHandler(@Nullable MetaType itemType, @Nullable MetaType blockType, @Nullable MetaType particleType, @Nullable MetaType optChatType) {
      this.filter().handler((event, meta) -> {
         MetaType type = meta.metaType();
         if (itemType != null && type == itemType) {
            this.protocol.getItemRewriter().handleItemToClient((Item)meta.value());
         } else if (blockType != null && type == blockType) {
            int data = (Integer)meta.value();
            meta.setValue(((BackwardsProtocol)this.protocol).getMappingData().getNewBlockStateId(data));
         } else if (particleType != null && type == particleType) {
            this.rewriteParticle((Particle)meta.value());
         } else if (optChatType != null && type == optChatType) {
            JsonElement text = (JsonElement)meta.value();
            if (text != null) {
               ((BackwardsProtocol)this.protocol).getTranslatableRewriter().processText(text);
            }
         }

      });
   }

   protected PacketHandler getTrackerHandler(Type intType, int typeIndex) {
      return (wrapper) -> {
         Number id = (Number)wrapper.get(intType, typeIndex);
         this.tracker(wrapper.user()).addEntity((Integer)wrapper.get(Type.VAR_INT, 0), this.typeFromId(id.intValue()));
      };
   }

   protected PacketHandler getTrackerHandler() {
      return this.getTrackerHandler(Type.VAR_INT, 1);
   }

   protected PacketHandler getTrackerHandler(EntityType entityType, Type intType) {
      return (wrapper) -> {
         this.tracker(wrapper.user()).addEntity((Integer)wrapper.get(intType, 0), entityType);
      };
   }

   protected PacketHandler getDimensionHandler(int index) {
      return (wrapper) -> {
         ClientWorld clientWorld = (ClientWorld)wrapper.user().get(ClientWorld.class);
         int dimensionId = (Integer)wrapper.get(Type.INT, index);
         clientWorld.setEnvironment(dimensionId);
      };
   }
}
