package com.viaversion.viaversion.libs.kyori.adventure.text.serializer;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;

public interface ComponentSerializer {
   @NotNull
   Component deserialize(@NotNull final Object input);

   /** @deprecated */
   @Deprecated
   @ScheduledForRemoval
   @Contract(
      value = "!null -> !null; null -> null",
      pure = true
   )
   @Nullable
   default Component deseializeOrNull(@Nullable final Object input) {
      return this.deserializeOrNull(input);
   }

   @Contract(
      value = "!null -> !null; null -> null",
      pure = true
   )
   @Nullable
   default Component deserializeOrNull(@Nullable final Object input) {
      return this.deserializeOr(input, null);
   }

   @Contract(
      value = "!null, _ -> !null; null, _ -> param2",
      pure = true
   )
   @Nullable
   default Component deserializeOr(@Nullable final Object input, @Nullable final Component fallback) {
      return input == null ? fallback : this.deserialize(input);
   }

   @NotNull
   Object serialize(@NotNull final Component component);

   @Contract(
      value = "!null -> !null; null -> null",
      pure = true
   )
   @Nullable
   default Object serializeOrNull(@Nullable final Component component) {
      return this.serializeOr(component, null);
   }

   @Contract(
      value = "!null, _ -> !null; null, _ -> param2",
      pure = true
   )
   @Nullable
   default Object serializeOr(@Nullable final Component component, @Nullable final Object fallback) {
      return component == null ? fallback : this.serialize(component);
   }
}
