package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

public interface FloatBinaryTag extends NumberBinaryTag {
   // $FF: renamed from: of (float) com.viaversion.viaversion.libs.kyori.adventure.nbt.FloatBinaryTag
   @NotNull
   static FloatBinaryTag method_18(final float value) {
      return new FloatBinaryTagImpl(value);
   }

   @NotNull
   default BinaryTagType type() {
      return BinaryTagTypes.FLOAT;
   }

   float value();
}
