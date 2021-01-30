package com.viaversion.viabackwards.api.data;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;

public class MappedItem {
   // $FF: renamed from: id int
   private final int field_404;
   private final String jsonName;

   public MappedItem(int id, String name) {
      this.field_404 = id;
      this.jsonName = ChatRewriter.legacyTextToJsonString("§f" + name);
   }

   public int getId() {
      return this.field_404;
   }

   public String getJsonName() {
      return this.jsonName;
   }
}
