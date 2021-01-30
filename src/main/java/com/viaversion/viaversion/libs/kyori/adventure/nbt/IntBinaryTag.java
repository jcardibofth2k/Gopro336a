package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

public interface IntBinaryTag extends NumberBinaryTag {
   // $FF: renamed from: of (int) com.viaversion.viaversion.libs.kyori.adventure.nbt.IntBinaryTag
   @NotNull
   static IntBinaryTag method_15(final int value) {
      return new IntBinaryTagImpl(value);
   }

   @NotNull
   default BinaryTagType type() {
      return BinaryTagTypes.INT;
   }

   int value();
}
