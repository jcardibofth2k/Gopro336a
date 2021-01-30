package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public abstract class ModernMetaListType extends AbstractMetaListType {
   protected void writeEnd(Type type, ByteBuf buffer) throws Exception {
      type.write(buffer, (Object)null);
   }
}
