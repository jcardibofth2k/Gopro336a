package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class OptionalVarIntType extends Type {
   public OptionalVarIntType() {
      super(Integer.class);
   }

   public Integer read(ByteBuf buffer) throws Exception {
      int read = Type.VAR_INT.readPrimitive(buffer);
      return read == 0 ? null : read - 1;
   }

   public void write(ByteBuf buffer, Integer object) throws Exception {
      if (object == null) {
         Type.VAR_INT.writePrimitive(buffer, 0);
      } else {
         Type.VAR_INT.writePrimitive(buffer, object + 1);
      }

   }
}
