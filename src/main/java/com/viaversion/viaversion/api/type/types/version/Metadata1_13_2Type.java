package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaType1_13_2;
import com.viaversion.viaversion.api.type.types.minecraft.ModernMetaType;

public class Metadata1_13_2Type extends ModernMetaType {
   protected MetaType getType(int index) {
      return MetaType1_13_2.byId(index);
   }
}
