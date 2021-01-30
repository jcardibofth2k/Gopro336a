package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

public class VarIntType extends Type implements TypeConverter {
   public VarIntType() {
      super("VarInt", Integer.class);
   }

   public int readPrimitive(ByteBuf buffer) {
      int out = 0;
      int bytes = 0;

      byte in;
      do {
         in = buffer.readByte();
         out |= (in & 127) << bytes++ * 7;
         if (bytes > 5) {
            throw new RuntimeException("VarInt too big");
         }
      } while((in & 128) == 128);

      return out;
   }

   public void writePrimitive(ByteBuf buffer, int object) {
      do {
         int part = object & 127;
         object >>>= 7;
         if (object != 0) {
            part |= 128;
         }

         buffer.writeByte(part);
      } while(object != 0);

   }

   /** @deprecated */
   @Deprecated
   public Integer read(ByteBuf buffer) {
      return this.readPrimitive(buffer);
   }

   /** @deprecated */
   @Deprecated
   public void write(ByteBuf buffer, Integer object) {
      this.writePrimitive(buffer, object);
   }

   public Integer from(Object o) {
      if (o instanceof Number) {
         return ((Number)o).intValue();
      } else {
         return o instanceof Boolean ? (Boolean)o ? 1 : 0 : (Integer)o;
      }
   }
}
