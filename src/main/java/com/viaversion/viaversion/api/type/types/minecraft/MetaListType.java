package com.viaversion.viaversion.api.type.types.minecraft;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.type.Type;

public final class MetaListType extends ModernMetaListType {
   private final Type type;

   public MetaListType(Type type) {
      Preconditions.checkNotNull(type);
      this.type = type;
   }

   protected Type getType() {
      return this.type;
   }
}
