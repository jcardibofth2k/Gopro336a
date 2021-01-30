package com.viaversion.viaversion.api.minecraft;

public class EulerAngle {
   // $FF: renamed from: x float
   private final float field_150;
   // $FF: renamed from: y float
   private final float field_151;
   // $FF: renamed from: z float
   private final float field_152;

   public EulerAngle(float x, float y, float z) {
      this.field_150 = x;
      this.field_151 = y;
      this.field_152 = z;
   }

   public float getX() {
      return this.field_150;
   }

   public float getY() {
      return this.field_151;
   }

   public float getZ() {
      return this.field_152;
   }
}
