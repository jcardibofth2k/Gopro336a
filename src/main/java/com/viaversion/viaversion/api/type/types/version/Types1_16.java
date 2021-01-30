package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.MetaListType;
import com.viaversion.viaversion.api.type.types.minecraft.Particle1_16Type;

public final class Types1_16 {
   public static final Type CHUNK_SECTION = new ChunkSectionType1_16();
   public static final Type METADATA = new Metadata1_16Type();
   public static final Type METADATA_LIST;
   public static final Type PARTICLE;

   static {
      METADATA_LIST = new MetaListType(METADATA);
      PARTICLE = new Particle1_16Type();
   }
}
