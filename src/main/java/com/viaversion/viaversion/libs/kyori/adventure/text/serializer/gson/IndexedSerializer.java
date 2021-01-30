package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.kyori.adventure.util.Index;
import java.io.IOException;

final class IndexedSerializer extends TypeAdapter {
   private final String name;
   private final Index map;

   // $FF: renamed from: of (java.lang.String, com.viaversion.viaversion.libs.kyori.adventure.util.Index) com.viaversion.viaversion.libs.gson.TypeAdapter
   public static TypeAdapter method_49(final String name, final Index map) {
      return (new IndexedSerializer(name, map)).nullSafe();
   }

   private IndexedSerializer(final String name, final Index map) {
      this.name = name;
      this.map = map;
   }

   public void write(final JsonWriter out, final Object value) throws IOException {
      out.value((String)this.map.key(value));
   }

   public Object read(final JsonReader in) throws IOException {
      String string = in.nextString();
      Object value = this.map.value(string);
      if (value != null) {
         return value;
      } else {
         throw new JsonParseException("invalid " + this.name + ":  " + string);
      }
   }
}
