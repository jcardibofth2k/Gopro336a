package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class EntityData {
   // $FF: renamed from: id int
   private final int field_371;
   private final int replacementId;
   private Object mobName;
   private EntityData.MetaCreator defaultMeta;

   public EntityData(int id, int replacementId) {
      this.field_371 = id;
      this.replacementId = replacementId;
   }

   public EntityData jsonName(String name) {
      this.mobName = ChatRewriter.legacyTextToJson(name);
      return this;
   }

   public EntityData mobName(String name) {
      this.mobName = name;
      return this;
   }

   public EntityData spawnMetadata(EntityData.MetaCreator handler) {
      this.defaultMeta = handler;
      return this;
   }

   public boolean hasBaseMeta() {
      return this.defaultMeta != null;
   }

   public int typeId() {
      return this.field_371;
   }

   @Nullable
   public Object mobName() {
      return this.mobName;
   }

   public int replacementId() {
      return this.replacementId;
   }

   @Nullable
   public EntityData.MetaCreator defaultMeta() {
      return this.defaultMeta;
   }

   public boolean isObjectType() {
      return false;
   }

   public int objectData() {
      return -1;
   }

   public String toString() {
      return "EntityData{id=" + this.field_371 + ", mobName='" + this.mobName + '\'' + ", replacementId=" + this.replacementId + ", defaultMeta=" + this.defaultMeta + '}';
   }

   @FunctionalInterface
   public interface MetaCreator {
      void createMeta(WrappedMetadata var1);
   }
}
