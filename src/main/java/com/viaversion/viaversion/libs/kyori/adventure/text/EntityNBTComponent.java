package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface EntityNBTComponent extends NBTComponent, ScopedComponent {
   @NotNull
   String selector();

   @Contract(
      pure = true
   )
   @NotNull
   EntityNBTComponent selector(@NotNull final String selector);

   interface Builder extends NBTComponentBuilder {
      @Contract("_ -> this")
      @NotNull
      EntityNBTComponent.Builder selector(@NotNull final String selector);
   }
}
