package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

public interface ShortBinaryTag extends NumberBinaryTag {
   // $FF: renamed from: of (short) com.viaversion.viaversion.libs.kyori.adventure.nbt.ShortBinaryTag
   @NotNull
   static ShortBinaryTag method_19(final short value) {
      return new ShortBinaryTagImpl(value);
   }

   @NotNull
   default BinaryTagType type() {
      return BinaryTagTypes.SHORT;
   }

   short value();
}
