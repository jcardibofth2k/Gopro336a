package com.viaversion.viaversion.util;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EntityTypeUtil {
   public static EntityType[] toOrderedArray(EntityType[] values) {
      List types = new ArrayList();
      EntityType[] var2 = values;
      int var3 = values.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         EntityType type = var2[var4];
         if (type.getId() != -1) {
            types.add(type);
         }
      }

      types.sort(Comparator.comparingInt(EntityType::getId));
      return (EntityType[])types.toArray(new EntityType[0]);
   }

   public static EntityType getTypeFromId(EntityType[] values, int typeId, EntityType fallback) {
      EntityType type;
      if (typeId >= 0 && typeId < values.length && (type = values[typeId]) != null) {
         return type;
      } else {
         Via.getPlatform().getLogger().severe("Could not find " + fallback.getClass().getSimpleName() + " type id " + typeId);
         return fallback;
      }
   }
}
