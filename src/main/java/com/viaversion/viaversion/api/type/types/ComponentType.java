package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import io.netty.buffer.ByteBuf;

public class ComponentType extends Type {
   private static final StringType STRING_TAG = new StringType(262144);

   public ComponentType() {
      super(JsonElement.class);
   }

   public JsonElement read(ByteBuf buffer) throws Exception {
      String s = STRING_TAG.read(buffer);

      try {
         return JsonParser.parseString(s);
      } catch (JsonSyntaxException var4) {
         Via.getPlatform().getLogger().severe("Error when trying to parse json: " + s);
         throw var4;
      }
   }

   public void write(ByteBuf buffer, JsonElement object) throws Exception {
      STRING_TAG.write(buffer, object.toString());
   }
}
