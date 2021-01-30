package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.PartialType;
import io.netty.buffer.ByteBuf;

public class CustomByteType extends PartialType {
   public CustomByteType(Integer param) {
      super(param, byte[].class);
   }

   public byte[] read(ByteBuf byteBuf, Integer integer) throws Exception {
      if (byteBuf.readableBytes() < integer) {
         throw new RuntimeException("Readable bytes does not match expected!");
      } else {
         byte[] byteArray = new byte[integer];
         byteBuf.readBytes(byteArray);
         return byteArray;
      }
   }

   public void write(ByteBuf byteBuf, Integer integer, byte[] bytes) throws Exception {
      byteBuf.writeBytes(bytes);
   }
}
