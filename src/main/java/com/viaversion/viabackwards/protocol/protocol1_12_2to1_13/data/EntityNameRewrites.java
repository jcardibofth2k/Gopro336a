package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import java.util.HashMap;
import java.util.Map;

public class EntityNameRewrites {
   private static final Map ENTITY_NAMES = new HashMap();

   private static void reg(String past, String future) {
      ENTITY_NAMES.put("minecraft:" + future, "minecraft:" + past);
   }

   public static String rewrite(String entName) {
      String entityName = (String)ENTITY_NAMES.get(entName);
      if (entityName != null) {
         return entityName;
      } else {
         entityName = (String)ENTITY_NAMES.get("minecraft:" + entName);
         return entityName != null ? entityName : entName;
      }
   }

   static {
      reg("commandblock_minecart", "command_block_minecart");
      reg("ender_crystal", "end_crystal");
      reg("evocation_fangs", "evoker_fangs");
      reg("evocation_illager", "evoker");
      reg("eye_of_ender_signal", "eye_of_ender");
      reg("fireworks_rocket", "firework_rocket");
      reg("illusion_illager", "illusioner");
      reg("snowman", "snow_golem");
      reg("villager_golem", "iron_golem");
      reg("vindication_illager", "vindicator");
      reg("xp_bottle", "experience_bottle");
      reg("xp_orb", "experience_orb");
   }
}
