package com.viaversion.viaversion.protocols.protocol1_9to1_8.sounds;

public enum SoundCategory {
   MASTER("master", 0),
   MUSIC("music", 1),
   RECORD("record", 2),
   WEATHER("weather", 3),
   BLOCK("block", 4),
   HOSTILE("hostile", 5),
   NEUTRAL("neutral", 6),
   PLAYER("player", 7),
   AMBIENT("ambient", 8),
   VOICE("voice", 9);

   private final String name;
   // $FF: renamed from: id int
   private final int field_3222;

   private SoundCategory(String name, int id) {
      this.name = name;
      this.field_3222 = id;
   }

   public int getId() {
      return this.field_3222;
   }

   public String getName() {
      return this.name;
   }
}
