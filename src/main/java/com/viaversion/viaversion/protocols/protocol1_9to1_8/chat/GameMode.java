package com.viaversion.viaversion.protocols.protocol1_9to1_8.chat;

public enum GameMode {
   SURVIVAL(0, "Survival Mode"),
   CREATIVE(1, "Creative Mode"),
   ADVENTURE(2, "Adventure Mode"),
   SPECTATOR(3, "Spectator Mode");

   // $FF: renamed from: id int
   private final int field_149;
   private final String text;

   GameMode(int id, String text) {
      this.field_149 = id;
      this.text = text;
   }

   public int getId() {
      return this.field_149;
   }

   public String getText() {
      return this.text;
   }

   public static GameMode getById(int id) {
      GameMode[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         GameMode gm = var1[var3];
         if (gm.getId() == id) {
            return gm;
         }
      }

      return null;
   }
}
