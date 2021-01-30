package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.MetaListType;
import com.viaversion.viaversion.api.type.types.minecraft.Particle1_13Type;

public class Types1_13 {
   public static final Type METADATA = new Metadata1_13Type();
   public static final Type METADATA_LIST;
   public static final Type CHUNK_SECTION;
   public static final Type PARTICLE;

   static {
      METADATA_LIST = new MetaListType(METADATA);
      CHUNK_SECTION = new ChunkSectionType1_13();
      PARTICLE = new Particle1_13Type();
   }
}
