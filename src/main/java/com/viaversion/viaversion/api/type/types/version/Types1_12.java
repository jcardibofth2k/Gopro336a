package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.MetaListType;

public class Types1_12 {
   public static final Type METADATA = new Metadata1_12Type();
   public static final Type METADATA_LIST;

   static {
      METADATA_LIST = new MetaListType(METADATA);
   }
}
