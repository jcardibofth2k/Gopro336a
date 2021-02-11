package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ScoreComponent extends BuildableComponent, ScopedComponent {
   @NotNull
   String name();

   @Contract(
      pure = true
   )
   @NotNull
   ScoreComponent name(@NotNull final String name);

   @NotNull
   String objective();

   @Contract(
      pure = true
   )
   @NotNull
   ScoreComponent objective(@NotNull final String objective);

   /** @deprecated */
   @Deprecated
   @Nullable
   String value();

   /** @deprecated */
   @Deprecated
   @Contract(
      pure = true
   )
   @NotNull
   ScoreComponent value(@Nullable final String value);

   interface Builder extends ComponentBuilder {
      @Contract("_ -> this")
      @NotNull
      ScoreComponent.Builder name(@NotNull final String name);

      @Contract("_ -> this")
      @NotNull
      ScoreComponent.Builder objective(@NotNull final String objective);

      /** @deprecated */
      @Deprecated
      @Contract("_ -> this")
      @NotNull
      ScoreComponent.Builder value(@Nullable final String value);
   }
}
