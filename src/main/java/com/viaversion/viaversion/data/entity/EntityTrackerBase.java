package com.viaversion.viaversion.data.entity;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.ClientEntityIdChangeListener;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.checkerframework.checker.nullness.qual.Nullable;

public class EntityTrackerBase implements EntityTracker, ClientEntityIdChangeListener {
   private final Map entityTypes;
   private final Map entityData;
   private final UserConnection connection;
   private final EntityType playerType;
   private int clientEntityId;
   private int currentWorldSectionHeight;
   private int currentMinY;

   public EntityTrackerBase(UserConnection connection, @Nullable EntityType playerType) {
      this(connection, playerType, false);
   }

   public EntityTrackerBase(UserConnection connection, @Nullable EntityType playerType, boolean storesEntityData) {
      this.entityTypes = new ConcurrentHashMap();
      this.clientEntityId = -1;
      this.currentWorldSectionHeight = 16;
      this.connection = connection;
      this.playerType = playerType;
      this.entityData = storesEntityData ? new ConcurrentHashMap() : null;
   }

   public UserConnection user() {
      return this.connection;
   }

   public void addEntity(int id, EntityType type) {
      this.entityTypes.put(id, type);
   }

   public boolean hasEntity(int id) {
      return this.entityTypes.containsKey(id);
   }

   @Nullable
   public EntityType entityType(int id) {
      return (EntityType)this.entityTypes.get(id);
   }

   @Nullable
   public StoredEntityData entityData(int id) {
      EntityType type = this.entityType(id);
      return type != null ? (StoredEntityData)this.entityData.computeIfAbsent(id, (s) -> {
         return new StoredEntityImpl(type);
      }) : null;
   }

   @Nullable
   public StoredEntityData entityDataIfPresent(int id) {
      return (StoredEntityData)this.entityData.get(id);
   }

   public void removeEntity(int id) {
      this.entityTypes.remove(id);
      if (this.entityData != null) {
         this.entityData.remove(id);
      }

   }

   public int clientEntityId() {
      return this.clientEntityId;
   }

   public void setClientEntityId(int clientEntityId) {
      Preconditions.checkNotNull(this.playerType);
      this.entityTypes.put(clientEntityId, this.playerType);
      if (this.clientEntityId != -1 && this.entityData != null) {
         StoredEntityData data = (StoredEntityData)this.entityData.remove(this.clientEntityId);
         if (data != null) {
            this.entityData.put(clientEntityId, data);
         }
      }

      this.clientEntityId = clientEntityId;
   }

   public int currentWorldSectionHeight() {
      return this.currentWorldSectionHeight;
   }

   public void setCurrentWorldSectionHeight(int currentWorldSectionHeight) {
      this.currentWorldSectionHeight = currentWorldSectionHeight;
   }

   public int currentMinY() {
      return this.currentMinY;
   }

   public void setCurrentMinY(int currentMinY) {
      this.currentMinY = currentMinY;
   }
}
