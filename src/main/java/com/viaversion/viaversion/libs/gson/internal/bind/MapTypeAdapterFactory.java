package com.viaversion.viaversion.libs.gson.internal.bind;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.gson.TypeAdapter;
import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
import com.viaversion.viaversion.libs.gson.internal.$Gson$Types;
import com.viaversion.viaversion.libs.gson.internal.ConstructorConstructor;
import com.viaversion.viaversion.libs.gson.internal.JsonReaderInternalAccess;
import com.viaversion.viaversion.libs.gson.internal.ObjectConstructor;
import com.viaversion.viaversion.libs.gson.internal.Streams;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import com.viaversion.viaversion.libs.gson.stream.JsonToken;
import com.viaversion.viaversion.libs.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class MapTypeAdapterFactory implements TypeAdapterFactory {
   private final ConstructorConstructor constructorConstructor;
   final boolean complexMapKeySerialization;

   public MapTypeAdapterFactory(ConstructorConstructor constructorConstructor, boolean complexMapKeySerialization) {
      this.constructorConstructor = constructorConstructor;
      this.complexMapKeySerialization = complexMapKeySerialization;
   }

   public TypeAdapter create(Gson gson, TypeToken typeToken) {
      Type type = typeToken.getType();
      Class rawType = typeToken.getRawType();
      if (!Map.class.isAssignableFrom(rawType)) {
         return null;
      } else {
         Class rawTypeOfSrc = $Gson$Types.getRawType(type);
         Type[] keyAndValueTypes = $Gson$Types.getMapKeyAndValueTypes(type, rawTypeOfSrc);
         TypeAdapter keyAdapter = this.getKeyAdapter(gson, keyAndValueTypes[0]);
         TypeAdapter valueAdapter = gson.getAdapter(TypeToken.get(keyAndValueTypes[1]));
         ObjectConstructor constructor = this.constructorConstructor.get(typeToken);
         TypeAdapter result = new MapTypeAdapterFactory.Adapter(gson, keyAndValueTypes[0], keyAdapter, keyAndValueTypes[1], valueAdapter, constructor);
         return result;
      }
   }

   private TypeAdapter getKeyAdapter(Gson context, Type keyType) {
      return keyType != Boolean.TYPE && keyType != Boolean.class ? context.getAdapter(TypeToken.get(keyType)) : TypeAdapters.BOOLEAN_AS_STRING;
   }

   private final class Adapter extends TypeAdapter {
      private final TypeAdapter keyTypeAdapter;
      private final TypeAdapter valueTypeAdapter;
      private final ObjectConstructor constructor;

      public Adapter(Gson context, Type keyType, TypeAdapter keyTypeAdapter, Type valueType, TypeAdapter valueTypeAdapter, ObjectConstructor constructor) {
         this.keyTypeAdapter = new TypeAdapterRuntimeTypeWrapper(context, keyTypeAdapter, keyType);
         this.valueTypeAdapter = new TypeAdapterRuntimeTypeWrapper(context, valueTypeAdapter, valueType);
         this.constructor = constructor;
      }

      public Map read(JsonReader in) throws IOException {
         JsonToken peek = in.peek();
         if (peek == JsonToken.NULL) {
            in.nextNull();
            return null;
         } else {
            Map map = (Map)this.constructor.construct();
            Object key;
            Object value;
            Object replaced;
            if (peek == JsonToken.BEGIN_ARRAY) {
               in.beginArray();

               while(in.hasNext()) {
                  in.beginArray();
                  key = this.keyTypeAdapter.read(in);
                  value = this.valueTypeAdapter.read(in);
                  replaced = map.put(key, value);
                  if (replaced != null) {
                     throw new JsonSyntaxException("duplicate key: " + key);
                  }

                  in.endArray();
               }

               in.endArray();
            } else {
               in.beginObject();

               while(in.hasNext()) {
                  JsonReaderInternalAccess.INSTANCE.promoteNameToValue(in);
                  key = this.keyTypeAdapter.read(in);
                  value = this.valueTypeAdapter.read(in);
                  replaced = map.put(key, value);
                  if (replaced != null) {
                     throw new JsonSyntaxException("duplicate key: " + key);
                  }
               }

               in.endObject();
            }

            return map;
         }
      }

      public void write(JsonWriter out, Map map) throws IOException {
         if (map == null) {
            out.nullValue();
         } else if (!MapTypeAdapterFactory.this.complexMapKeySerialization) {
            out.beginObject();
            Iterator var9 = map.entrySet().iterator();

            while(var9.hasNext()) {
               Entry entryx = (Entry)var9.next();
               out.name(String.valueOf(entryx.getKey()));
               this.valueTypeAdapter.write(out, entryx.getValue());
            }

            out.endObject();
         } else {
            boolean hasComplexKeys = false;
            List keys = new ArrayList(map.size());
            List values = new ArrayList(map.size());

            JsonElement keyElement;
            for(Iterator var6 = map.entrySet().iterator(); var6.hasNext(); hasComplexKeys |= keyElement.isJsonArray() || keyElement.isJsonObject()) {
               Entry entry = (Entry)var6.next();
               keyElement = this.keyTypeAdapter.toJsonTree(entry.getKey());
               keys.add(keyElement);
               values.add(entry.getValue());
            }

            int i;
            int size;
            if (hasComplexKeys) {
               out.beginArray();
               i = 0;

               for(size = keys.size(); i < size; ++i) {
                  out.beginArray();
                  Streams.write((JsonElement)keys.get(i), out);
                  this.valueTypeAdapter.write(out, values.get(i));
                  out.endArray();
               }

               out.endArray();
            } else {
               out.beginObject();
               i = 0;

               for(size = keys.size(); i < size; ++i) {
                  keyElement = (JsonElement)keys.get(i);
                  out.name(this.keyToString(keyElement));
                  this.valueTypeAdapter.write(out, values.get(i));
               }

               out.endObject();
            }

         }
      }

      private String keyToString(JsonElement keyElement) {
         if (keyElement.isJsonPrimitive()) {
            JsonPrimitive primitive = keyElement.getAsJsonPrimitive();
            if (primitive.isNumber()) {
               return String.valueOf(primitive.getAsNumber());
            } else if (primitive.isBoolean()) {
               return Boolean.toString(primitive.getAsBoolean());
            } else if (primitive.isString()) {
               return primitive.getAsString();
            } else {
               throw new AssertionError();
            }
         } else if (keyElement.isJsonNull()) {
            return "null";
         } else {
            throw new AssertionError();
         }
      }
   }
}
