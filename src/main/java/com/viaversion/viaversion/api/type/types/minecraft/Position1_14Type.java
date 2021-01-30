package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public class Position1_14Type extends Type {
   public Position1_14Type() {
      super(Position.class);
   }

   public Position read(ByteBuf buffer) {
      long val = buffer.readLong();
      long x = val >> 38;
      long y = val << 52 >> 52;
      long z = val << 26 >> 38;
      return new Position((int)x, (int)y, (int)z);
   }

   public void write(ByteBuf buffer, Position object) {
      buffer.writeLong(((long)object.getX() & 67108863L) << 38 | (long)(object.getY() & 4095) | ((long)object.getZ() & 67108863L) << 12);
   }
}
