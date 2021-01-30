package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

public interface DoubleBinaryTag extends NumberBinaryTag {
   // $FF: renamed from: of (double) com.viaversion.viaversion.libs.kyori.adventure.nbt.DoubleBinaryTag
   @NotNull
   static DoubleBinaryTag method_16(final double value) {
      return new DoubleBinaryTagImpl(value);
   }

   @NotNull
   default BinaryTagType type() {
      return BinaryTagTypes.DOUBLE;
   }

   double value();
}
