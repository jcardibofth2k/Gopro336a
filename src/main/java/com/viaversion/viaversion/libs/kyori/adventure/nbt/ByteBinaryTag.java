package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

public interface ByteBinaryTag extends NumberBinaryTag {
   ByteBinaryTag ZERO = new ByteBinaryTagImpl((byte)0);
   ByteBinaryTag ONE = new ByteBinaryTagImpl((byte)1);

   // $FF: renamed from: of (byte) com.viaversion.viaversion.libs.kyori.adventure.nbt.ByteBinaryTag
   @NotNull
   static ByteBinaryTag method_14(final byte value) {
      if (value == 0) {
         return ZERO;
      } else {
         return (ByteBinaryTag)(value == 1 ? ONE : new ByteBinaryTagImpl(value));
      }
   }

   @NotNull
   default BinaryTagType type() {
      return BinaryTagTypes.BYTE;
   }

   byte value();
}
