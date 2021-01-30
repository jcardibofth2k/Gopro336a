package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.translation.Translatable;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface TranslatableComponent extends BuildableComponent, ScopedComponent {
   @NotNull
   String key();

   @Contract(
      pure = true
   )
   @NotNull
   default TranslatableComponent key(@NotNull final Translatable translatable) {
      return this.key(((Translatable)Objects.requireNonNull(translatable, "translatable")).translationKey());
   }

   @Contract(
      pure = true
   )
   @NotNull
   TranslatableComponent key(@NotNull final String key);

   @NotNull
   List args();

   @Contract(
      pure = true
   )
   @NotNull
   TranslatableComponent args(@NotNull final ComponentLike... args);

   @Contract(
      pure = true
   )
   @NotNull
   TranslatableComponent args(@NotNull final List args);

   public interface Builder extends ComponentBuilder {
      @Contract(
         pure = true
      )
      @NotNull
      default TranslatableComponent.Builder key(@NotNull final Translatable translatable) {
         return this.key(((Translatable)Objects.requireNonNull(translatable, "translatable")).translationKey());
      }

      @Contract("_ -> this")
      @NotNull
      TranslatableComponent.Builder key(@NotNull final String key);

      @Contract("_ -> this")
      @NotNull
      TranslatableComponent.Builder args(@NotNull final ComponentBuilder arg);

      @Contract("_ -> this")
      @NotNull
      TranslatableComponent.Builder args(@NotNull final ComponentBuilder... args);

      @Contract("_ -> this")
      @NotNull
      TranslatableComponent.Builder args(@NotNull final Component arg);

      @Contract("_ -> this")
      @NotNull
      TranslatableComponent.Builder args(@NotNull final ComponentLike... args);

      @Contract("_ -> this")
      @NotNull
      TranslatableComponent.Builder args(@NotNull final List args);
   }
}
