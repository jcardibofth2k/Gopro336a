package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.util.UUID;

public class UUIDIntArrayType extends Type {
   public UUIDIntArrayType() {
      super(UUID.class);
   }

   public UUID read(ByteBuf buffer) {
      int[] ints = new int[]{buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()};
      return uuidFromIntArray(ints);
   }

   public void write(ByteBuf buffer, UUID object) {
      int[] ints = uuidToIntArray(object);
      buffer.writeInt(ints[0]);
      buffer.writeInt(ints[1]);
      buffer.writeInt(ints[2]);
      buffer.writeInt(ints[3]);
   }

   public static UUID uuidFromIntArray(int[] ints) {
      return new UUID((long)ints[0] << 32 | (long)ints[1] & 4294967295L, (long)ints[2] << 32 | (long)ints[3] & 4294967295L);
   }

   public static int[] uuidToIntArray(UUID uuid) {
      return bitsToIntArray(uuid.getMostSignificantBits(), uuid.getLeastSignificantBits());
   }

   public static int[] bitsToIntArray(long long1, long long2) {
      return new int[]{(int)(long1 >> 32), (int)long1, (int)(long2 >> 32), (int)long2};
   }
}
