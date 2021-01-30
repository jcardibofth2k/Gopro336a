package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

public interface ByteArrayBinaryTag extends ArrayBinaryTag, Iterable {
   // $FF: renamed from: of (byte[]) com.viaversion.viaversion.libs.kyori.adventure.nbt.ByteArrayBinaryTag
   @NotNull
   static ByteArrayBinaryTag method_12(@NotNull final byte... value) {
      return new ByteArrayBinaryTagImpl(value);
   }

   @NotNull
   default BinaryTagType type() {
      return BinaryTagTypes.BYTE_ARRAY;
   }

   @NotNull
   byte[] value();

   int size();

   byte get(final int index);
}
