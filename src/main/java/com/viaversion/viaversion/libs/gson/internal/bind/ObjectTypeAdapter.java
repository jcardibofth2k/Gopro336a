package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.LinkedTreeMap;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ObjectTypeAdapter extends TypeAdapter {
   public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
      public TypeAdapter create(Gson gson, TypeToken type) {
         return type.getRawType() == Object.class ? new ObjectTypeAdapter(gson) : null;
      }
   };
   private final Gson gson;

   ObjectTypeAdapter(Gson gson) {
      this.gson = gson;
   }

   public Object read(JsonReader in) throws IOException {
      JsonToken token = in.peek();
      switch(token) {
      case BEGIN_ARRAY:
         List list = new ArrayList();
         in.beginArray();

         while(in.hasNext()) {
            list.add(this.read(in));
         }

         in.endArray();
         return list;
      case BEGIN_OBJECT:
         Map map = new LinkedTreeMap();
         in.beginObject();

         while(in.hasNext()) {
            map.put(in.nextName(), this.read(in));
         }

         in.endObject();
         return map;
      case STRING:
         return in.nextString();
      case NUMBER:
         return in.nextDouble();
      case BOOLEAN:
         return in.nextBoolean();
      case NULL:
         in.nextNull();
         return null;
      default:
         throw new IllegalStateException();
      }
   }

   public void write(JsonWriter out, Object value) throws IOException {
      if (value == null) {
         out.nullValue();
      } else {
         TypeAdapter typeAdapter = this.gson.getAdapter(value.getClass());
         if (typeAdapter instanceof ObjectTypeAdapter) {
            out.beginObject();
            out.endObject();
         } else {
            typeAdapter.write(out, value);
         }
      }
   }
}
