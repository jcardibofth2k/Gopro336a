package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface KeybindComponent extends BuildableComponent, ScopedComponent {
   @NotNull
   String keybind();

   @Contract(
      pure = true
   )
   @NotNull
   KeybindComponent keybind(@NotNull final String keybind);

   public interface Builder extends ComponentBuilder {
      @Contract("_ -> this")
      @NotNull
      KeybindComponent.Builder keybind(@NotNull final String keybind);
   }
}
