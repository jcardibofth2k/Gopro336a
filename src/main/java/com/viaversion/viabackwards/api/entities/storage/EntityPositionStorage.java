package com.viaversion.viabackwards.api.entities.storage;

public abstract class EntityPositionStorage {
   // $FF: renamed from: x double
   private double field_94;
   // $FF: renamed from: y double
   private double field_95;
   // $FF: renamed from: z double
   private double field_96;

   public double getX() {
      return this.field_94;
   }

   public double getY() {
      return this.field_95;
   }

   public double getZ() {
      return this.field_96;
   }

   public void setCoordinates(double x, double y, double z, boolean relative) {
      if (relative) {
         this.field_94 += x;
         this.field_95 += y;
         this.field_96 += z;
      } else {
         this.field_94 = x;
         this.field_95 = y;
         this.field_96 = z;
      }

   }
}
