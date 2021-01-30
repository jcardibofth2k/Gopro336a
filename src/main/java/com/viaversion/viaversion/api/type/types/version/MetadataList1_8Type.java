package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.AbstractMetaListType;
import io.netty.buffer.ByteBuf;

public class MetadataList1_8Type extends AbstractMetaListType {
   protected Type getType() {
      return Types1_8.METADATA;
   }

   protected void writeEnd(Type type, ByteBuf buffer) throws Exception {
      buffer.writeByte(127);
   }
}
