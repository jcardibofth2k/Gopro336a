package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.MetaListType;
import com.viaversion.viaversion.api.type.types.minecraft.Particle1_17Type;

public final class Types1_17 {
   public static final Type METADATA = new Metadata1_17Type();
   public static final Type METADATA_LIST;
   public static final Type PARTICLE;

   static {
      METADATA_LIST = new MetaListType(METADATA);
      PARTICLE = new Particle1_17Type();
   }
}
