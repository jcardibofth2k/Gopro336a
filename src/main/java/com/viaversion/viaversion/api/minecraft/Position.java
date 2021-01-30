package com.viaversion.viaversion.api.minecraft;

public class Position {
   // $FF: renamed from: x int
   private final int field_394;
   // $FF: renamed from: y int
   private final int field_395;
   // $FF: renamed from: z int
   private final int field_396;

   public Position(int x, int y, int z) {
      this.field_394 = x;
      this.field_395 = y;
      this.field_396 = z;
   }

   public Position(int x, short y, int z) {
      this.field_394 = x;
      this.field_395 = y;
      this.field_396 = z;
   }

   public Position(Position toCopy) {
      this(toCopy.getX(), toCopy.getY(), toCopy.getZ());
   }

   public Position getRelative(BlockFace face) {
      return new Position(this.field_394 + face.getModX(), (short)(this.field_395 + face.getModY()), this.field_396 + face.getModZ());
   }

   public int getX() {
      return this.field_394;
   }

   public int getY() {
      return this.field_395;
   }

   public int getZ() {
      return this.field_396;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Position position = (Position)o;
         if (this.field_394 != position.field_394) {
            return false;
         } else if (this.field_395 != position.field_395) {
            return false;
         } else {
            return this.field_396 == position.field_396;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.field_394;
      result = 31 * result + this.field_395;
      result = 31 * result + this.field_396;
      return result;
   }

   public String toString() {
      return "Position{x=" + this.field_394 + ", y=" + this.field_395 + ", z=" + this.field_396 + '}';
   }
}
