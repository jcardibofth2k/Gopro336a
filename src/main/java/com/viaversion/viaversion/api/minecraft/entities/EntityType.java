package com.viaversion.viaversion.api.minecraft.entities;

import org.checkerframework.checker.nullness.qual.Nullable;

public interface EntityType {
   int getId();

   @Nullable
   EntityType getParent();

   String name();

   // $FF: renamed from: is (com.viaversion.viaversion.api.minecraft.entities.EntityType[]) boolean
   default boolean method_22(EntityType... types) {
      EntityType[] var2 = types;
      int var3 = types.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         EntityType type = var2[var4];
         if (this == type) {
            return true;
         }
      }

      return false;
   }

   // $FF: renamed from: is (com.viaversion.viaversion.api.minecraft.entities.EntityType) boolean
   default boolean method_23(EntityType type) {
      return this == type;
   }

   default boolean isOrHasParent(EntityType type) {
      EntityType parent = this;

      while(parent != type) {
         parent = parent.getParent();
         if (parent == null) {
            return false;
         }
      }

      return true;
   }
}
