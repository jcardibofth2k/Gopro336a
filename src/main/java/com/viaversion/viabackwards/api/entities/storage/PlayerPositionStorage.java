package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;

public abstract class PlayerPositionStorage implements StorableObject {
   // $FF: renamed from: x double
   private double field_120;
   // $FF: renamed from: y double
   private double field_121;
   // $FF: renamed from: z double
   private double field_122;

   protected PlayerPositionStorage() {
   }

   public double getX() {
      return this.field_120;
   }

   public double getY() {
      return this.field_121;
   }

   public double getZ() {
      return this.field_122;
   }

   public void setX(double x) {
      this.field_120 = x;
   }

   public void setY(double y) {
      this.field_121 = y;
   }

   public void setZ(double z) {
      this.field_122 = z;
   }

   public void setCoordinates(PacketWrapper wrapper, boolean relative) throws Exception {
      this.setCoordinates((Double)wrapper.get(Type.DOUBLE, 0), (Double)wrapper.get(Type.DOUBLE, 1), (Double)wrapper.get(Type.DOUBLE, 2), relative);
   }

   public void setCoordinates(double x, double y, double z, boolean relative) {
      if (relative) {
         this.field_120 += x;
         this.field_121 += y;
         this.field_122 += z;
      } else {
         this.field_120 = x;
         this.field_121 = y;
         this.field_122 = z;
      }

   }
}
