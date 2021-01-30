package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.data.VBMappings;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.IntArrayMappings;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.StatisticMappings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BackwardsMappings extends com.viaversion.viabackwards.api.data.BackwardsMappings {
   private final Int2ObjectMap statisticMappings = new Int2ObjectOpenHashMap();
   private final Map translateMappings = new HashMap();
   private Mappings enchantmentMappings;

   public BackwardsMappings() {
      super("1.13", "1.12", Protocol1_13To1_12_2.class, true);
   }

   public void loadVBExtras(JsonObject oldMappings, JsonObject newMappings) {
      this.enchantmentMappings = new VBMappings(oldMappings.getAsJsonObject("enchantments"), newMappings.getAsJsonObject("enchantments"), false);
      Iterator var3 = StatisticMappings.CUSTOM_STATS.entrySet().iterator();

      Entry entry;
      while(var3.hasNext()) {
         entry = (Entry)var3.next();
         this.statisticMappings.put((Integer)entry.getValue(), (String)entry.getKey());
      }

      var3 = Protocol1_13To1_12_2.MAPPINGS.getTranslateMapping().entrySet().iterator();

      while(var3.hasNext()) {
         entry = (Entry)var3.next();
         this.translateMappings.put((String)entry.getValue(), (String)entry.getKey());
      }

   }

   private static void mapIdentifiers(int[] output, JsonObject newIdentifiers, JsonObject oldIdentifiers, JsonObject mapping) {
      Object2IntMap newIdentifierMap = MappingDataLoader.indexedObjectToMap(oldIdentifiers);
      Iterator var5 = newIdentifiers.entrySet().iterator();

      while(true) {
         Entry entry;
         JsonPrimitive replacement;
         label46:
         do {
            int value;
            short hardId;
            for(; var5.hasNext(); output[Integer.parseInt((String)entry.getKey())] = hardId != -1 ? hardId : (short)value) {
               entry = (Entry)var5.next();
               String key = ((JsonElement)entry.getValue()).getAsString();
               value = newIdentifierMap.getInt(key);
               hardId = -1;
               if (value == -1) {
                  replacement = mapping.getAsJsonPrimitive(key);
                  int propertyIndex;
                  if (replacement == null && (propertyIndex = key.indexOf(91)) != -1) {
                     replacement = mapping.getAsJsonPrimitive(key.substring(0, propertyIndex));
                  }

                  if (replacement != null) {
                     if (replacement.getAsString().startsWith("id:")) {
                        String id = replacement.getAsString().replace("id:", "");
                        hardId = Short.parseShort(id);
                        value = newIdentifierMap.getInt(oldIdentifiers.getAsJsonPrimitive(id).getAsString());
                     } else {
                        value = newIdentifierMap.getInt(replacement.getAsString());
                     }
                  }

                  if (value == -1) {
                     continue label46;
                  }
               }
            }

            return;
         } while(Via.getConfig().isSuppressConversionWarnings() && !Via.getManager().isDebug());

         if (replacement != null) {
            ViaBackwards.getPlatform().getLogger().warning("No key for " + entry.getValue() + "/" + replacement.getAsString() + " :( ");
         } else {
            ViaBackwards.getPlatform().getLogger().warning("No key for " + entry.getValue() + " :( ");
         }
      }
   }

   @Nullable
   protected Mappings loadFromObject(JsonObject oldMappings, JsonObject newMappings, @Nullable JsonObject diffMappings, String key) {
      if (key.equals("blockstates")) {
         int[] oldToNew = new int[8582];
         Arrays.fill(oldToNew, -1);
         mapIdentifiers(oldToNew, oldMappings.getAsJsonObject("blockstates"), newMappings.getAsJsonObject("blocks"), diffMappings.getAsJsonObject("blockstates"));
         return new IntArrayMappings(oldToNew);
      } else {
         return super.loadFromObject(oldMappings, newMappings, diffMappings, key);
      }
   }

   public int getNewBlockStateId(int id) {
      int mappedId = super.getNewBlockStateId(id);
      switch(mappedId) {
      case 1595:
      case 1596:
      case 1597:
         return 1584;
      case 1598:
      case 1599:
      case 1600:
      case 1601:
      case 1602:
      case 1603:
      case 1604:
      case 1605:
      case 1606:
      case 1607:
      case 1608:
      case 1609:
      case 1610:
      default:
         return mappedId;
      case 1611:
      case 1612:
      case 1613:
         return 1600;
      }
   }

   protected int checkValidity(int id, int mappedId, String type) {
      return mappedId;
   }

   protected boolean shouldWarnOnMissing(String key) {
      return super.shouldWarnOnMissing(key) && !key.equals("items");
   }

   public Int2ObjectMap getStatisticMappings() {
      return this.statisticMappings;
   }

   public Map getTranslateMappings() {
      return this.translateMappings;
   }

   public Mappings getEnchantmentMappings() {
      return this.enchantmentMappings;
   }
}
