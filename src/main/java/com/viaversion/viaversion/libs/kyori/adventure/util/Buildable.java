package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.function.Consumer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Buildable {
   @Contract(
      mutates = "param1"
   )
   @NotNull
   static Buildable configureAndBuild(@NotNull final Buildable.Builder builder, @Nullable final Consumer consumer) {
      if (consumer != null) {
         consumer.accept(builder);
      }

      return (Buildable)builder.build();
   }

   @Contract(
      value = "-> new",
      pure = true
   )
   @NotNull
   Buildable.Builder toBuilder();

   public interface Builder {
      @Contract(
         value = "-> new",
         pure = true
      )
      @NotNull
      Object build();
   }
}
