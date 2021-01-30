package com.viaversion.viaversion.rewriter;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.rewriter.meta.MetaFilter;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEvent;
import com.viaversion.viaversion.rewriter.meta.MetaHandlerEventImpl;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class EntityRewriter extends RewriterBase implements com.viaversion.viaversion.api.rewriter.EntityRewriter {
   private static final Metadata[] EMPTY_ARRAY = new Metadata[0];
   protected final List metadataFilters;
   protected final boolean trackMappedType;
   protected Int2IntMap typeMappings;

   protected EntityRewriter(Protocol protocol) {
      this(protocol, true);
   }

   protected EntityRewriter(Protocol protocol, boolean trackMappedType) {
      super(protocol);
      this.metadataFilters = new ArrayList();
      this.trackMappedType = trackMappedType;
      protocol.put(this);
   }

   public MetaFilter.Builder filter() {
      return new MetaFilter.Builder(this);
   }

   public void registerFilter(MetaFilter filter) {
      Preconditions.checkArgument(!this.metadataFilters.contains(filter));
      this.metadataFilters.add(filter);
   }

   public void handleMetadata(int entityId, List metadataList, UserConnection connection) {
      EntityType type = this.tracker(connection).entityType(entityId);
      int i = 0;
      Metadata[] var6 = (Metadata[])metadataList.toArray(EMPTY_ARRAY);
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Metadata metadata = var6[var8];
         if (!this.callOldMetaHandler(entityId, type, metadata, metadataList, connection)) {
            metadataList.remove(i--);
         } else {
            MetaHandlerEvent event = null;
            Iterator var11 = this.metadataFilters.iterator();

            while(var11.hasNext()) {
               MetaFilter filter = (MetaFilter)var11.next();
               if (filter.isFiltered(type, metadata)) {
                  if (event == null) {
                     event = new MetaHandlerEventImpl(connection, type, entityId, metadata, metadataList);
                  }

                  try {
                     filter.handler().handle(event, metadata);
                  } catch (Exception var14) {
                     this.logException(var14, type, metadataList, metadata);
                     metadataList.remove(i--);
                     break;
                  }

                  if (event.cancelled()) {
                     metadataList.remove(i--);
                     break;
                  }
               }
            }

            if (event != null && event.extraMeta() != null) {
               metadataList.addAll(event.extraMeta());
            }

            ++i;
         }
      }

   }

   /** @deprecated */
   @Deprecated
   private boolean callOldMetaHandler(int entityId, @Nullable EntityType type, Metadata metadata, List metadataList, UserConnection connection) {
      try {
         this.handleMetadata(entityId, type, metadata, metadataList, connection);
         return true;
      } catch (Exception var7) {
         this.logException(var7, type, metadataList, metadata);
         return false;
      }
   }

   /** @deprecated */
   @Deprecated
   protected void handleMetadata(int entityId, @Nullable EntityType type, Metadata metadata, List metadatas, UserConnection connection) throws Exception {
   }

   public int newEntityId(int id) {
      return this.typeMappings != null ? this.typeMappings.getOrDefault(id, id) : id;
   }

   public void mapEntityType(EntityType type, EntityType mappedType) {
      Preconditions.checkArgument(type.getClass() != mappedType.getClass(), "EntityTypes should not be of the same class/enum");
      this.mapEntityType(type.getId(), mappedType.getId());
   }

   protected void mapEntityType(int id, int mappedId) {
      if (this.typeMappings == null) {
         this.typeMappings = new Int2IntOpenHashMap();
         this.typeMappings.defaultReturnValue(-1);
      }

      this.typeMappings.put(id, mappedId);
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
            if (!this.typeMappings.containsKey(oldType.getId())) {
               Via.getPlatform().getLogger().warning("Could not find new entity type for " + oldType + "! Old type: " + oldType.getClass().getEnclosingClass().getSimpleName() + ", new type: " + newTypeClass.getEnclosingClass().getSimpleName());
            }
         }
      }

   }

   public void registerMetaTypeHandler(@Nullable MetaType itemType, @Nullable MetaType blockType, @Nullable MetaType particleType) {
      this.filter().handler((event, meta) -> {
         if (itemType != null && meta.metaType() == itemType) {
            this.protocol.getItemRewriter().handleItemToClient((Item)meta.value());
         } else if (blockType != null && meta.metaType() == blockType) {
            int data = (Integer)meta.value();
            meta.setValue(this.protocol.getMappingData().getNewBlockStateId(data));
         } else if (particleType != null && meta.metaType() == particleType) {
            this.rewriteParticle((Particle)meta.value());
         }

      });
   }

   public void registerTracker(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.VAR_INT);
            this.handler(EntityRewriter.this.trackerHandler());
         }
      });
   }

   public void registerTrackerWithData(ClientboundPacketType packetType, final EntityType fallingBlockType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.VAR_INT);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.INT);
            this.handler(EntityRewriter.this.trackerHandler());
            this.handler((wrapper) -> {
               int entityId = (Integer)wrapper.get(Type.VAR_INT, 0);
               EntityType entityType = EntityRewriter.this.tracker(wrapper.user()).entityType(entityId);
               if (entityType == fallingBlockType) {
                  wrapper.set(Type.INT, 0, EntityRewriter.this.protocol.getMappingData().getNewBlockStateId((Integer)wrapper.get(Type.INT, 0)));
               }

            });
         }
      });
   }

   public void registerTracker(ClientboundPacketType packetType, final EntityType entityType, final Type intType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               int entityId = (Integer)wrapper.passthrough(intType);
               EntityRewriter.this.tracker(wrapper.user()).addEntity(entityId, entityType);
            });
         }
      });
   }

   public void registerTracker(ClientboundPacketType packetType, EntityType entityType) {
      this.registerTracker(packetType, entityType, Type.VAR_INT);
   }

   public void registerRemoveEntities(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               int[] entityIds = (int[])wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
               EntityTracker entityTracker = EntityRewriter.this.tracker(wrapper.user());
               int[] var4 = entityIds;
               int var5 = entityIds.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  int entity = var4[var6];
                  entityTracker.removeEntity(entity);
               }

            });
         }
      });
   }

   public void registerRemoveEntity(ClientboundPacketType packetType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.handler((wrapper) -> {
               int entityId = (Integer)wrapper.passthrough(Type.VAR_INT);
               EntityRewriter.this.tracker(wrapper.user()).removeEntity(entityId);
            });
         }
      });
   }

   public void registerMetadataRewriter(ClientboundPacketType packetType, @Nullable final Type oldMetaType, final Type newMetaType) {
      this.protocol.registerClientbound(packetType, new PacketRemapper() {
         public void registerMap() {
            this.map(Type.VAR_INT);
            if (oldMetaType != null) {
               this.map(oldMetaType, newMetaType);
            } else {
               this.map(newMetaType);
            }

            this.handler((wrapper) -> {
               int entityId = (Integer)wrapper.get(Type.VAR_INT, 0);
               List metadata = (List)wrapper.get(newMetaType, 0);
               EntityRewriter.this.handleMetadata(entityId, metadata, wrapper.user());
            });
         }
      });
   }

   public void registerMetadataRewriter(ClientboundPacketType packetType, Type metaType) {
      this.registerMetadataRewriter(packetType, (Type)null, metaType);
   }

   public PacketHandler trackerHandler() {
      return this.trackerAndRewriterHandler((Type)null);
   }

   protected PacketHandler worldDataTrackerHandler(int nbtIndex) {
      return (wrapper) -> {
         EntityTracker tracker = this.tracker(wrapper.user());
         CompoundTag registryData = (CompoundTag)wrapper.get(Type.NBT, nbtIndex);
         Tag height = registryData.get("height");
         if (height instanceof IntTag) {
            int blockHeight = ((IntTag)height).asInt();
            tracker.setCurrentWorldSectionHeight(blockHeight >> 4);
         } else {
            Via.getPlatform().getLogger().warning("Height missing in dimension data: " + registryData);
         }

         Tag minY = registryData.get("min_y");
         if (minY instanceof IntTag) {
            tracker.setCurrentMinY(((IntTag)minY).asInt());
         } else {
            Via.getPlatform().getLogger().warning("Min Y missing in dimension data: " + registryData);
         }

      };
   }

   public PacketHandler trackerAndRewriterHandler(@Nullable Type metaType) {
      return (wrapper) -> {
         int entityId = (Integer)wrapper.get(Type.VAR_INT, 0);
         int type = (Integer)wrapper.get(Type.VAR_INT, 1);
         int newType = this.newEntityId(type);
         if (newType != type) {
            wrapper.set(Type.VAR_INT, 1, newType);
         }

         EntityType entType = this.typeFromId(this.trackMappedType ? newType : type);
         this.tracker(wrapper.user()).addEntity(entityId, entType);
         if (metaType != null) {
            this.handleMetadata(entityId, (List)wrapper.get(metaType, 0), wrapper.user());
         }

      };
   }

   public PacketHandler trackerAndRewriterHandler(@Nullable Type metaType, EntityType entityType) {
      return (wrapper) -> {
         int entityId = (Integer)wrapper.get(Type.VAR_INT, 0);
         this.tracker(wrapper.user()).addEntity(entityId, entityType);
         if (metaType != null) {
            this.handleMetadata(entityId, (List)wrapper.get(metaType, 0), wrapper.user());
         }

      };
   }

   public PacketHandler objectTrackerHandler() {
      return (wrapper) -> {
         int entityId = (Integer)wrapper.get(Type.VAR_INT, 0);
         byte type = (Byte)wrapper.get(Type.BYTE, 0);
         EntityType entType = this.objectTypeFromId(type);
         this.tracker(wrapper.user()).addEntity(entityId, entType);
      };
   }

   /** @deprecated */
   @Deprecated
   @Nullable
   protected Metadata metaByIndex(int index, List metadataList) {
      Iterator var3 = metadataList.iterator();

      Metadata metadata;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         metadata = (Metadata)var3.next();
      } while(metadata.method_71() != index);

      return metadata;
   }

   protected void rewriteParticle(Particle particle) {
      ParticleMappings mappings = this.protocol.getMappingData().getParticleMappings();
      int id = particle.getId();
      Particle.ParticleData data;
      if (id != mappings.getBlockId() && id != mappings.getFallingDustId()) {
         if (id == mappings.getItemId()) {
            data = (Particle.ParticleData)particle.getArguments().get(0);
            data.setValue(this.protocol.getMappingData().getNewItemId((Integer)data.get()));
         }
      } else {
         data = (Particle.ParticleData)particle.getArguments().get(0);
         data.setValue(this.protocol.getMappingData().getNewBlockStateId((Integer)data.get()));
      }

      particle.setId(this.protocol.getMappingData().getNewParticleId(id));
   }

   private void logException(Exception e, @Nullable EntityType type, List metadataList, Metadata metadata) {
      if (!Via.getConfig().isSuppressMetadataErrors() || Via.getManager().isDebug()) {
         Logger logger = Via.getPlatform().getLogger();
         logger.severe("An error occurred in metadata handler " + this.getClass().getSimpleName() + " for " + (type != null ? type.name() : "untracked") + " entity type: " + metadata);
         logger.severe((String)metadataList.stream().sorted(Comparator.comparingInt(Metadata::id)).map(Metadata::toString).collect(Collectors.joining("\n", "Full metadata: ", "")));
         e.printStackTrace();
      }

   }
}
