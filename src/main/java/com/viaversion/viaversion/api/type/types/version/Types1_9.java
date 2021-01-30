package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.MetaListType;

public class Types1_9 {
   public static final Type METADATA = new Metadata1_9Type();
   public static final Type METADATA_LIST;
   public static final Type CHUNK_SECTION;

   static {
      METADATA_LIST = new MetaListType(METADATA);
      CHUNK_SECTION = new ChunkSectionType1_9();
   }
}
