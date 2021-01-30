package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.TagData;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.util.Int2IntBiHashMap;
import com.viaversion.viaversion.util.Int2IntBiMap;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MappingDataBase implements MappingData {
   protected final String oldVersion;
   protected final String newVersion;
   protected final boolean hasDiffFile;
   protected Int2IntBiMap itemMappings;
   protected ParticleMappings particleMappings;
   protected Mappings blockMappings;
   protected Mappings blockStateMappings;
   protected Mappings soundMappings;
   protected Mappings statisticsMappings;
   protected Map tags;
   protected boolean loadItems;

   public MappingDataBase(String oldVersion, String newVersion) {
      this(oldVersion, newVersion, false);
   }

   public MappingDataBase(String oldVersion, String newVersion, boolean hasDiffFile) {
      this.loadItems = true;
      this.oldVersion = oldVersion;
      this.newVersion = newVersion;
      this.hasDiffFile = hasDiffFile;
   }

   public void load() {
      this.getLogger().info("Loading " + this.oldVersion + " -> " + this.newVersion + " mappings...");
      JsonObject diffmapping = this.hasDiffFile ? this.loadDiffFile() : null;
      JsonObject oldMappings = MappingDataLoader.loadData("mapping-" + this.oldVersion + ".json", true);
      JsonObject newMappings = MappingDataLoader.loadData("mapping-" + this.newVersion + ".json", true);
      this.blockMappings = this.loadFromObject(oldMappings, newMappings, diffmapping, "blocks");
      this.blockStateMappings = this.loadFromObject(oldMappings, newMappings, diffmapping, "blockstates");
      this.soundMappings = this.loadFromArray(oldMappings, newMappings, diffmapping, "sounds");
      this.statisticsMappings = this.loadFromArray(oldMappings, newMappings, diffmapping, "statistics");
      Mappings particles = this.loadFromArray(oldMappings, newMappings, diffmapping, "particles");
      if (particles != null) {
         this.particleMappings = new ParticleMappings(oldMappings.getAsJsonArray("particles"), particles);
      }

      if (this.loadItems && newMappings.has("items")) {
         this.itemMappings = new Int2IntBiHashMap();
         this.itemMappings.defaultReturnValue(-1);
         MappingDataLoader.mapIdentifiers(this.itemMappings, oldMappings.getAsJsonObject("items"), newMappings.getAsJsonObject("items"), diffmapping != null ? diffmapping.getAsJsonObject("items") : null);
      }

      if (diffmapping != null && diffmapping.has("tags")) {
         this.tags = new EnumMap(RegistryType.class);
         JsonObject tags = diffmapping.getAsJsonObject("tags");
         if (tags.has(RegistryType.ITEM.getResourceLocation())) {
            this.loadTags(RegistryType.ITEM, tags, MappingDataLoader.indexedObjectToMap(newMappings.getAsJsonObject("items")));
         }

         if (tags.has(RegistryType.BLOCK.getResourceLocation())) {
            this.loadTags(RegistryType.BLOCK, tags, MappingDataLoader.indexedObjectToMap(newMappings.getAsJsonObject("blocks")));
         }
      }

      this.loadExtras(oldMappings, newMappings, diffmapping);
   }

   private void loadTags(RegistryType type, JsonObject object, Object2IntMap typeMapping) {
      JsonObject tags = object.getAsJsonObject(type.getResourceLocation());
      List tagsList = new ArrayList(tags.size());

      Entry entry;
      int[] entries;
      label28:
      for(Iterator var6 = tags.entrySet().iterator(); var6.hasNext(); tagsList.add(new TagData((String)entry.getKey(), entries))) {
         entry = (Entry)var6.next();
         JsonArray array = ((JsonElement)entry.getValue()).getAsJsonArray();
         entries = new int[array.size()];
         int i = 0;
         Iterator var11 = array.iterator();

         while(true) {
            while(true) {
               if (!var11.hasNext()) {
                  continue label28;
               }

               JsonElement element = (JsonElement)var11.next();
               String stringId = element.getAsString();
               if (!typeMapping.containsKey(stringId) && !typeMapping.containsKey(stringId = stringId.replace("minecraft:", ""))) {
                  this.getLogger().warning(type + " Tags contains invalid type identifier " + stringId + " in tag " + (String)entry.getKey());
               } else {
                  entries[i++] = typeMapping.getInt(stringId);
               }
            }
         }
      }

      this.tags.put(type, tagsList);
   }

   public int getNewBlockStateId(int id) {
      return this.checkValidity(id, this.blockStateMappings.getNewId(id), "blockstate");
   }

   public int getNewBlockId(int id) {
      return this.checkValidity(id, this.blockMappings.getNewId(id), "block");
   }

   public int getNewItemId(int id) {
      return this.checkValidity(id, this.itemMappings.get(id), "item");
   }

   public int getOldItemId(int id) {
      int oldId = this.itemMappings.inverse().get(id);
      return oldId != -1 ? oldId : 1;
   }

   public int getNewParticleId(int id) {
      return this.checkValidity(id, this.particleMappings.getMappings().getNewId(id), "particles");
   }

   @Nullable
   public List getTags(RegistryType type) {
      return this.tags != null ? (List)this.tags.get(type) : null;
   }

   @Nullable
   public Int2IntBiMap getItemMappings() {
      return this.itemMappings;
   }

   @Nullable
   public ParticleMappings getParticleMappings() {
      return this.particleMappings;
   }

   @Nullable
   public Mappings getBlockMappings() {
      return this.blockMappings;
   }

   @Nullable
   public Mappings getBlockStateMappings() {
      return this.blockStateMappings;
   }

   @Nullable
   public Mappings getSoundMappings() {
      return this.soundMappings;
   }

   @Nullable
   public Mappings getStatisticsMappings() {
      return this.statisticsMappings;
   }

   @Nullable
   protected Mappings loadFromArray(JsonObject oldMappings, JsonObject newMappings, @Nullable JsonObject diffMappings, String key) {
      if (oldMappings.has(key) && newMappings.has(key)) {
         JsonObject diff = diffMappings != null ? diffMappings.getAsJsonObject(key) : null;
         return new IntArrayMappings(oldMappings.getAsJsonArray(key), newMappings.getAsJsonArray(key), diff);
      } else {
         return null;
      }
   }

   @Nullable
   protected Mappings loadFromObject(JsonObject oldMappings, JsonObject newMappings, @Nullable JsonObject diffMappings, String key) {
      if (oldMappings.has(key) && newMappings.has(key)) {
         JsonObject diff = diffMappings != null ? diffMappings.getAsJsonObject(key) : null;
         return new IntArrayMappings(oldMappings.getAsJsonObject(key), newMappings.getAsJsonObject(key), diff);
      } else {
         return null;
      }
   }

   @Nullable
   protected JsonObject loadDiffFile() {
      return MappingDataLoader.loadData("mappingdiff-" + this.oldVersion + "to" + this.newVersion + ".json");
   }

   protected Logger getLogger() {
      return Via.getPlatform().getLogger();
   }

   protected int checkValidity(int id, int mappedId, String type) {
      if (mappedId == -1) {
         this.getLogger().warning(String.format("Missing %s %s for %s %s %d", this.newVersion, type, this.oldVersion, type, id));
         return 0;
      } else {
         return mappedId;
      }
   }

   protected void loadExtras(JsonObject oldMappings, JsonObject newMappings, @Nullable JsonObject diffMappings) {
   }
}
