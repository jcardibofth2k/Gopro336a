package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.io.CharStreams;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.IntArrayMappings;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MappingData extends MappingDataBase {
   private final Map blockTags = new HashMap();
   private final Map itemTags = new HashMap();
   private final Map fluidTags = new HashMap();
   private final BiMap oldEnchantmentsIds = HashBiMap.create();
   private final Map translateMapping = new HashMap();
   private final Map mojangTranslation = new HashMap();
   private final BiMap channelMappings = HashBiMap.create();
   private Mappings enchantmentMappings;

   public MappingData() {
      super("1.12", "1.13");
   }

   public void loadExtras(JsonObject oldMappings, JsonObject newMappings, JsonObject diffMappings) {
      this.loadTags(this.blockTags, newMappings.getAsJsonObject("block_tags"));
      this.loadTags(this.itemTags, newMappings.getAsJsonObject("item_tags"));
      this.loadTags(this.fluidTags, newMappings.getAsJsonObject("fluid_tags"));
      this.loadEnchantments(this.oldEnchantmentsIds, oldMappings.getAsJsonObject("enchantments"));
      this.enchantmentMappings = new IntArrayMappings(72, oldMappings.getAsJsonObject("enchantments"), newMappings.getAsJsonObject("enchantments"));
      if (Via.getConfig().isSnowCollisionFix()) {
         this.blockMappings.setNewId(1248, 3416);
      }

      if (Via.getConfig().isInfestedBlocksFix()) {
         this.blockMappings.setNewId(1552, 1);
         this.blockMappings.setNewId(1553, 14);
         this.blockMappings.setNewId(1554, 3983);
         this.blockMappings.setNewId(1555, 3984);
         this.blockMappings.setNewId(1556, 3985);
         this.blockMappings.setNewId(1557, 3986);
      }

      JsonObject object = MappingDataLoader.loadFromDataDir("channelmappings-1.13.json");
      if (object != null) {
         Iterator var5 = object.entrySet().iterator();

         while(var5.hasNext()) {
            Entry entry = (Entry)var5.next();
            String oldChannel = (String)entry.getKey();
            String newChannel = ((JsonElement)entry.getValue()).getAsString();
            if (!isValid1_13Channel(newChannel)) {
               Via.getPlatform().getLogger().warning("Channel '" + newChannel + "' is not a valid 1.13 plugin channel, please check your configuration!");
            } else {
               this.channelMappings.put(oldChannel, newChannel);
            }
         }
      }

      Map translateData = (Map)GsonUtil.getGson().fromJson(new InputStreamReader(MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/mapping-lang-1.12-1.13.json")), (new TypeToken() {
      }).getType());

      try {
         InputStreamReader reader = new InputStreamReader(MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/en_US.properties"), StandardCharsets.UTF_8);

         String[] lines;
         try {
            lines = CharStreams.toString(reader).split("\n");
         } catch (Throwable var15) {
            try {
               reader.close();
            } catch (Throwable var14) {
               var15.addSuppressed(var14);
            }

            throw var15;
         }

         reader.close();
         String[] var20 = lines;
         int var21 = lines.length;

         for(int var9 = 0; var9 < var21; ++var9) {
            String line = var20[var9];
            if (!line.isEmpty()) {
               String[] keyAndTranslation = line.split("=", 2);
               if (keyAndTranslation.length == 2) {
                  String key = keyAndTranslation[0];
                  String dataValue;
                  if (!translateData.containsKey(key)) {
                     dataValue = keyAndTranslation[1].replaceAll("%(\\d\\$)?d", "%$1s");
                     this.mojangTranslation.put(key, dataValue);
                  } else {
                     dataValue = (String)translateData.get(key);
                     if (dataValue != null) {
                        this.translateMapping.put(key, dataValue);
                     }
                  }
               }
            }
         }
      } catch (IOException var16) {
         var16.printStackTrace();
      }

   }

   protected Mappings loadFromObject(JsonObject oldMappings, JsonObject newMappings, @Nullable JsonObject diffMappings, String key) {
      return key.equals("blocks") ? new IntArrayMappings(4084, oldMappings.getAsJsonObject("blocks"), newMappings.getAsJsonObject("blockstates")) : super.loadFromObject(oldMappings, newMappings, diffMappings, key);
   }

   public static String validateNewChannel(String newId) {
      if (!isValid1_13Channel(newId)) {
         return null;
      } else {
         int separatorIndex = newId.indexOf(58);
         if ((separatorIndex == -1 || separatorIndex == 0) && newId.length() <= 10) {
            newId = "minecraft:" + newId;
         }

         return newId;
      }
   }

   public static boolean isValid1_13Channel(String channelId) {
      return channelId.matches("([0-9a-z_.-]+):([0-9a-z_/.-]+)");
   }

   private void loadTags(Map output, JsonObject newTags) {
      Iterator var3 = newTags.entrySet().iterator();

      while(var3.hasNext()) {
         Entry entry = (Entry)var3.next();
         JsonArray ids = ((JsonElement)entry.getValue()).getAsJsonArray();
         Integer[] idsArray = new Integer[ids.size()];

         for(int i = 0; i < ids.size(); ++i) {
            idsArray[i] = ids.get(i).getAsInt();
         }

         output.put(entry.getKey(), idsArray);
      }

   }

   private void loadEnchantments(Map output, JsonObject enchantments) {
      Iterator var3 = enchantments.entrySet().iterator();

      while(var3.hasNext()) {
         Entry enchantment = (Entry)var3.next();
         output.put(Short.parseShort((String)enchantment.getKey()), ((JsonElement)enchantment.getValue()).getAsString());
      }

   }

   public Map getBlockTags() {
      return this.blockTags;
   }

   public Map getItemTags() {
      return this.itemTags;
   }

   public Map getFluidTags() {
      return this.fluidTags;
   }

   public BiMap getOldEnchantmentsIds() {
      return this.oldEnchantmentsIds;
   }

   public Map getTranslateMapping() {
      return this.translateMapping;
   }

   public Map getMojangTranslation() {
      return this.mojangTranslation;
   }

   public BiMap getChannelMappings() {
      return this.channelMappings;
   }

   public Mappings getEnchantmentMappings() {
      return this.enchantmentMappings;
   }
}
