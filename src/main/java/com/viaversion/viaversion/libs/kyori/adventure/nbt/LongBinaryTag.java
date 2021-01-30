package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

public interface LongBinaryTag extends NumberBinaryTag {
   // $FF: renamed from: of (long) com.viaversion.viaversion.libs.kyori.adventure.nbt.LongBinaryTag
   @NotNull
   static LongBinaryTag method_17(final long value) {
      return new LongBinaryTagImpl(value);
   }

   @NotNull
   default BinaryTagType type() {
      return BinaryTagTypes.LONG;
   }

   long value();
}
