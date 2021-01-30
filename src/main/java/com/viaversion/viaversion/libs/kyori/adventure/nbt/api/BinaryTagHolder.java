package com.viaversion.viaversion.libs.kyori.adventure.nbt.api;

import com.viaversion.viaversion.libs.kyori.adventure.util.Codec;
import org.jetbrains.annotations.NotNull;

public interface BinaryTagHolder {
   @NotNull
   static BinaryTagHolder encode(@NotNull final Object nbt, @NotNull final Codec codec) throws Exception {
      return new BinaryTagHolderImpl((String)codec.encode(nbt));
   }

   // $FF: renamed from: of (java.lang.String) com.viaversion.viaversion.libs.kyori.adventure.nbt.api.BinaryTagHolder
   @NotNull
   static BinaryTagHolder method_21(@NotNull final String string) {
      return new BinaryTagHolderImpl(string);
   }

   @NotNull
   String string();

   @NotNull
   Object get(@NotNull final Codec codec) throws Exception;
}
