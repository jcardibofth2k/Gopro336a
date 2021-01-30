package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.NotNull;

public interface StringBinaryTag extends BinaryTag {
   // $FF: renamed from: of (java.lang.String) com.viaversion.viaversion.libs.kyori.adventure.nbt.StringBinaryTag
   @NotNull
   static StringBinaryTag method_10(@NotNull final String value) {
      return new StringBinaryTagImpl(value);
   }

   @NotNull
   default BinaryTagType type() {
      return BinaryTagTypes.STRING;
   }

   @NotNull
   String value();
}
