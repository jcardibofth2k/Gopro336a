package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class MappingData extends MappingDataBase {
   private IntSet motionBlocking;
   private IntSet nonFullBlocks;

   public MappingData() {
      super("1.13.2", "1.14");
   }

   public void loadExtras(JsonObject oldMappings, JsonObject newMappings, JsonObject diffMappings) {
      JsonObject blockStates = newMappings.getAsJsonObject("blockstates");
      Map blockStateMap = new HashMap(blockStates.entrySet().size());
      Iterator var6 = blockStates.entrySet().iterator();

      while(var6.hasNext()) {
         Entry entry = (Entry)var6.next();
         blockStateMap.put(((JsonElement)entry.getValue()).getAsString(), Integer.parseInt((String)entry.getKey()));
      }

      JsonObject heightMapData = MappingDataLoader.loadData("heightMapData-1.14.json");
      JsonArray motionBlocking = heightMapData.getAsJsonArray("MOTION_BLOCKING");
      this.motionBlocking = new IntOpenHashSet(motionBlocking.size(), 1.0F);
      Iterator var8 = motionBlocking.iterator();

      String state;
      while(var8.hasNext()) {
         JsonElement blockState = (JsonElement)var8.next();
         state = blockState.getAsString();
         Integer id = (Integer)blockStateMap.get(state);
         if (id == null) {
            Via.getPlatform().getLogger().warning("Unknown blockstate " + state + " :(");
         } else {
            this.motionBlocking.add(id);
         }
      }

      if (Via.getConfig().isNonFullBlockLightFix()) {
         this.nonFullBlocks = new IntOpenHashSet(1611, 1.0F);
         var8 = oldMappings.getAsJsonObject("blockstates").entrySet().iterator();

         label42:
         while(true) {
            Entry blockstates;
            do {
               if (!var8.hasNext()) {
                  this.nonFullBlocks.add(this.blockStateMappings.getNewId(8163));

                  for(int i = 3060; i <= 3067; ++i) {
                     this.nonFullBlocks.add(this.blockStateMappings.getNewId(i));
                  }
                  break label42;
               }

               blockstates = (Entry)var8.next();
               state = ((JsonElement)blockstates.getValue()).getAsString();
            } while(!state.contains("_slab") && !state.contains("_stairs") && !state.contains("_wall["));

            this.nonFullBlocks.add(this.blockStateMappings.getNewId(Integer.parseInt((String)blockstates.getKey())));
         }
      }

   }

   public IntSet getMotionBlocking() {
      return this.motionBlocking;
   }

   public IntSet getNonFullBlocks() {
      return this.nonFullBlocks;
   }
}
