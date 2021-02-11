package com.viaversion.viabackwards.api.data;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonIOException;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class VBMappingDataLoader {
   public static JsonObject loadFromDataDir(String name) {
      File file = new File(ViaBackwards.getPlatform().getDataFolder(), name);
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
            ViaBackwards.getPlatform().getLogger().warning(name + " is badly formatted!");
            var7.printStackTrace();
            ViaBackwards.getPlatform().getLogger().warning("Falling back to resource's file!");
            return loadData(name);
         } catch (JsonIOException | IOException var8) {
            var8.printStackTrace();
            return null;
         }
      }
   }

   public static JsonObject loadData(String name) {
      InputStream stream = VBMappingDataLoader.class.getClassLoader().getResourceAsStream("assets/viabackwards/data/" + name);

      try {
         InputStreamReader reader = new InputStreamReader(stream);

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
      } catch (IOException var7) {
         var7.printStackTrace();
         return null;
      }
   }

   public static void mapIdentifiers(int[] output, JsonObject oldIdentifiers, JsonObject newIdentifiers, JsonObject diffIdentifiers) {
      mapIdentifiers(output, oldIdentifiers, newIdentifiers, diffIdentifiers, true);
   }

   public static void mapIdentifiers(int[] output, JsonObject oldIdentifiers, JsonObject newIdentifiers, JsonObject diffIdentifiers, boolean warnOnMissing) {
      Object2IntMap newIdentifierMap = MappingDataLoader.indexedObjectToMap(newIdentifiers);
      Iterator var6 = oldIdentifiers.entrySet().iterator();

      while(true) {
         Entry entry;
         label48:
         do {
            int mappedId;
            for(; var6.hasNext(); output[Integer.parseInt((String)entry.getKey())] = mappedId) {
               entry = (Entry)var6.next();
               String key = ((JsonElement)entry.getValue()).getAsString();
               mappedId = newIdentifierMap.getInt(key);
               if (mappedId == -1) {
                  if (diffIdentifiers != null) {
                     JsonPrimitive diffValueJson = diffIdentifiers.getAsJsonPrimitive(key);
                     String diffValue = diffValueJson != null ? diffValueJson.getAsString() : null;
                     int dataIndex;
                     if (diffValue == null && (dataIndex = key.indexOf(91)) != -1 && (diffValueJson = diffIdentifiers.getAsJsonPrimitive(key.substring(0, dataIndex))) != null) {
                        diffValue = diffValueJson.getAsString();
                        if (diffValue.endsWith("[")) {
                           diffValue = diffValue + key.substring(dataIndex + 1);
                        }
                     }

                     if (diffValue != null) {
                        mappedId = newIdentifierMap.getInt(diffValue);
                     }
                  }

                  if (mappedId == -1) {
                     continue label48;
                  }
               }
            }

            return;
         } while((!warnOnMissing || Via.getConfig().isSuppressConversionWarnings()) && !Via.getManager().isDebug());

         ViaBackwards.getPlatform().getLogger().warning("No key for " + entry.getValue() + " :( ");
      }
   }

   public static Map objectToMap(JsonObject object) {
      Map mappings = new HashMap();

      String key;
      String value;
      for(Iterator var2 = object.entrySet().iterator(); var2.hasNext(); mappings.put(key, value)) {
         Entry entry = (Entry)var2.next();
         key = (String)entry.getKey();
         if (key.indexOf(58) == -1) {
            key = "minecraft:" + key;
         }

         value = ((JsonElement)entry.getValue()).getAsString();
         if (value.indexOf(58) == -1) {
            value = "minecraft:" + value;
         }
      }

      return mappings;
   }

   public static Int2ObjectMap loadItemMappings(JsonObject oldMapping, JsonObject newMapping, JsonObject diffMapping) {
      return loadItemMappings(oldMapping, newMapping, diffMapping, false);
   }

   public static Int2ObjectMap loadItemMappings(JsonObject oldMapping, JsonObject newMapping, JsonObject diffMapping, boolean warnOnMissing) {
      Int2ObjectMap itemMapping = new Int2ObjectOpenHashMap(diffMapping.size(), 1.0F);
      Object2IntMap newIdenfierMap = MappingDataLoader.indexedObjectToMap(newMapping);
      Object2IntMap oldIdenfierMap = MappingDataLoader.indexedObjectToMap(oldMapping);
      Iterator var7 = diffMapping.entrySet().iterator();

      while(true) {
         String mappedIdName;
         label50:
         do {
            while(var7.hasNext()) {
               Entry entry = (Entry)var7.next();
               JsonObject object = ((JsonElement)entry.getValue()).getAsJsonObject();
               mappedIdName = object.getAsJsonPrimitive("id").getAsString();
               int mappedId = newIdenfierMap.getInt(mappedIdName);
               if (mappedId == -1) {
                  continue label50;
               }

               int oldId = oldIdenfierMap.getInt(entry.getKey());
               if (oldId == -1) {
                  if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
                     ViaBackwards.getPlatform().getLogger().warning("No old entry for " + mappedIdName + " :( ");
                  }
               } else {
                  String name = object.getAsJsonPrimitive("name").getAsString();
                  itemMapping.put(oldId, new MappedItem(mappedId, name));
               }
            }

            if (warnOnMissing && !Via.getConfig().isSuppressConversionWarnings()) {
               ObjectIterator var14 = oldIdenfierMap.object2IntEntrySet().iterator();

               while(var14.hasNext()) {
                  Object2IntMap.Entry entry = (Object2IntMap.Entry)var14.next();
                  if (!newIdenfierMap.containsKey(entry.getKey()) && !itemMapping.containsKey(entry.getIntValue())) {
                     ViaBackwards.getPlatform().getLogger().warning("No item mapping for " + entry.getKey() + " :( ");
                  }
               }
            }

            return itemMapping;
         } while(Via.getConfig().isSuppressConversionWarnings() && !Via.getManager().isDebug());

         ViaBackwards.getPlatform().getLogger().warning("No key for " + mappedIdName + " :( ");
      }
   }
}
