package com.viaversion.viaversion.libs.kyori.adventure.util;

import org.jetbrains.annotations.NotNull;

public interface Codec {
   // $FF: renamed from: of (com.viaversion.viaversion.libs.kyori.adventure.util.Codec$Decoder, com.viaversion.viaversion.libs.kyori.adventure.util.Codec$Encoder) com.viaversion.viaversion.libs.kyori.adventure.util.Codec
   @NotNull
   static Codec method_46(@NotNull final Codec.Decoder decoder, @NotNull final Codec.Encoder encoder) {
      return new Codec() {
         @NotNull
         public Object decode(@NotNull final Object encoded) throws Throwable {
            return decoder.decode(encoded);
         }

         @NotNull
         public Object encode(@NotNull final Object decoded) throws Throwable {
            return encoder.encode(decoded);
         }
      };
   }

   @NotNull
   Object decode(@NotNull final Object encoded) throws Throwable;

   @NotNull
   Object encode(@NotNull final Object decoded) throws Throwable;

   public interface Decoder {
      @NotNull
      Object decode(@NotNull final Object encoded) throws Throwable;
   }

   public interface Encoder {
      @NotNull
      Object encode(@NotNull final Object decoded) throws Throwable;
   }
}
