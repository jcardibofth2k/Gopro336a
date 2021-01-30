package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import java.io.IOException;

final class KeySerializer extends TypeAdapter {
   static final TypeAdapter INSTANCE = (new KeySerializer()).nullSafe();

   private KeySerializer() {
   }

   public void write(final JsonWriter out, final Key value) throws IOException {
      out.value(value.asString());
   }

   public Key read(final JsonReader in) throws IOException {
      return Key.key(in.nextString());
   }
}
