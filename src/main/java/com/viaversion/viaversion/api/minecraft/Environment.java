package com.viaversion.viaversion.api.minecraft;

public enum Environment {
   NORMAL(0),
   NETHER(-1),
   END(1);

   // $FF: renamed from: id int
   private final int field_135;

   private Environment(int id) {
      this.field_135 = id;
   }

   public int getId() {
      return this.field_135;
   }

   public static Environment getEnvironmentById(int id) {
      switch(id) {
      case -1:
      default:
         return NETHER;
      case 0:
         return NORMAL;
      case 1:
         return END;
      }
   }
}
