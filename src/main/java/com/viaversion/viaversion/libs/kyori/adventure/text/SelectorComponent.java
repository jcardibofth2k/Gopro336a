package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SelectorComponent extends BuildableComponent, ScopedComponent {
   @NotNull
   String pattern();

   @Contract(
      pure = true
   )
   @NotNull
   SelectorComponent pattern(@NotNull final String pattern);

   @Nullable
   Component separator();

   @NotNull
   SelectorComponent separator(@Nullable final ComponentLike separator);

   public interface Builder extends ComponentBuilder {
      @Contract("_ -> this")
      @NotNull
      SelectorComponent.Builder pattern(@NotNull final String pattern);

      @Contract("_ -> this")
      @NotNull
      SelectorComponent.Builder separator(@Nullable final ComponentLike separator);
   }
}
