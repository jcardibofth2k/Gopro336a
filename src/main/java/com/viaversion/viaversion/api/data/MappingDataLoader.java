package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonIOException;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.util.GsonUtil;
import com.viaversion.viaversion.util.Int2IntBiMap;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MappingDataLoader {
   private static final Map MAPPINGS_CACHE = new ConcurrentHashMap();
   private static boolean cacheJsonMappings;

   public static boolean isCacheJsonMappings() {
      return cacheJsonMappings;
   }

   public static void enableMappingsCache() {
      cacheJsonMappings = true;
   }

   public static Map getMappingsCache() {
      return MAPPINGS_CACHE;
   }

   @Nullable
   public static JsonObject loadFromDataDir(String name) {
      File file = new File(Via.getPlatform().getDataFolder(), name);
      if (!file.exists()) {
         return loadData(name);
      } else {
         try {
            FileReader reader = new FileReader(file);

            JsonObject var3;
            try {
               var3 = (JsonObject)GsonUtil.getGson().fromJson(reader, JsonObject.class);
            } catch (Throwable var6) {
               try {
                  reader.close();
               } catch (Throwable var5) {
                  var6.addSuppressed(var5);
               }

               throw var6;
            }

            reader.close();
            return var3;
         } catch (JsonSyntaxException var7) {
            Via.getPlatform().getLogger().warning(name + " is badly formatted!");
            var7.printStackTrace();
         } catch (JsonIOException | IOException var8) {
            var8.printStackTrace();
         }

         return null;
      }
   }

   @Nullable
   public static JsonObject loadData(String name) {
      return loadData(name, false);
   }

   @Nullable
   public static JsonObject loadData(String name, boolean cacheIfEnabled) {
      if (cacheJsonMappings) {
         JsonObject cached = (JsonObject)MAPPINGS_CACHE.get(name);
         if (cached != null) {
            return cached;
         }
      }

      InputStream stream = getResource(name);
      if (stream == null) {
         return null;
      } else {
         InputStreamReader reader = new InputStreamReader(stream);

         JsonObject var5;
         try {
            JsonObject object = (JsonObject)GsonUtil.getGson().fromJson(reader, JsonObject.class);
            if (cacheIfEnabled && cacheJsonMappings) {
               MAPPINGS_CACHE.put(name, object);
            }

            var5 = object;
         } finally {
            try {
               reader.close();
            } catch (IOException var12) {
            }

         }

         return var5;
      }
   }

   public static void mapIdentifiers(Int2IntBiMap output, JsonObject oldIdentifiers, JsonObject newIdentifiers, @Nullable JsonObject diffIdentifiers) {
      Object2IntMap newIdentifierMap = indexedObjectToMap(newIdentifiers);
      Iterator var5 = oldIdentifiers.entrySet().iterator();

      while(var5.hasNext()) {
         Entry entry = (Entry)var5.next();
         int value = mapIdentifierEntry(entry, newIdentifierMap, diffIdentifiers);
         if (value != -1) {
            output.put(Integer.parseInt((String)entry.getKey()), value);
         }
      }

   }

   public static void mapIdentifiers(int[] output, JsonObject oldIdentifiers, JsonObject newIdentifiers) {
      mapIdentifiers(output, oldIdentifiers, newIdentifiers, null);
   }

   public static void mapIdentifiers(int[] output, JsonObject oldIdentifiers, JsonObject newIdentifiers, @Nullable JsonObject diffIdentifiers) {
      Object2IntMap newIdentifierMap = indexedObjectToMap(newIdentifiers);
      Iterator var5 = oldIdentifiers.entrySet().iterator();

      while(var5.hasNext()) {
         Entry entry = (Entry)var5.next();
         int value = mapIdentifierEntry(entry, newIdentifierMap, diffIdentifiers);
         if (value != -1) {
            output[Integer.parseInt((String)entry.getKey())] = value;
         }
      }

   }

   private static int mapIdentifierEntry(Entry entry, Object2IntMap newIdentifierMap, @Nullable JsonObject diffIdentifiers) {
      int value = newIdentifierMap.getInt(((JsonElement)entry.getValue()).getAsString());
      if (value == -1) {
         if (diffIdentifiers != null) {
            JsonElement diffElement = diffIdentifiers.get((String)entry.getKey());
            if (diffElement != null) {
               value = newIdentifierMap.getInt(diffElement.getAsString());
            }
         }

         if (value == -1) {
            if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
               Via.getPlatform().getLogger().warning("No key for " + entry.getValue() + " :( ");
            }

            return -1;
         }
      }

      return value;
   }

   public static void mapIdentifiers(int[] output, JsonArray oldIdentifiers, JsonArray newIdentifiers, boolean warnOnMissing) {
      mapIdentifiers(output, oldIdentifiers, newIdentifiers, null, warnOnMissing);
   }

   public static void mapIdentifiers(int[] output, JsonArray oldIdentifiers, JsonArray newIdentifiers, @Nullable JsonObject diffIdentifiers, boolean warnOnMissing) {
      Object2IntMap newIdentifierMap = arrayToMap(newIdentifiers);

      for(int i = 0; i < oldIdentifiers.size(); ++i) {
         JsonElement oldIdentifier = oldIdentifiers.get(i);
         int mappedId = newIdentifierMap.getInt(oldIdentifier.getAsString());
         if (mappedId == -1) {
            if (diffIdentifiers != null) {
               JsonElement diffElement = diffIdentifiers.get(oldIdentifier.getAsString());
               if (diffElement != null) {
                  String mappedName = diffElement.getAsString();
                  if (mappedName.isEmpty()) {
                     continue;
                  }

                  mappedId = newIdentifierMap.getInt(mappedName);
               }
            }

            if (mappedId == -1) {
               if (warnOnMissing && !Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                  Via.getPlatform().getLogger().warning("No key for " + oldIdentifier + " :( ");
               }
               continue;
            }
         }

         output[i] = mappedId;
      }

   }

   public static Object2IntMap indexedObjectToMap(JsonObject object) {
      Object2IntMap map = new Object2IntOpenHashMap(object.size(), 1.0F);
      map.defaultReturnValue(-1);
      Iterator var2 = object.entrySet().iterator();

      while(var2.hasNext()) {
         Entry entry = (Entry)var2.next();
         map.put(((JsonElement)entry.getValue()).getAsString(), Integer.parseInt((String)entry.getKey()));
      }

      return map;
   }

   public static Object2IntMap arrayToMap(JsonArray array) {
      Object2IntMap map = new Object2IntOpenHashMap(array.size(), 1.0F);
      map.defaultReturnValue(-1);

      for(int i = 0; i < array.size(); ++i) {
         map.put(array.get(i).getAsString(), i);
      }

      return map;
   }

   @Nullable
   public static InputStream getResource(String name) {
      return MappingDataLoader.class.getClassLoader().getResourceAsStream("assets/viaversion/data/" + name);
   }
}
