package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface StorageNBTComponent extends NBTComponent, ScopedComponent {
   @NotNull
   Key storage();

   @Contract(
      pure = true
   )
   @NotNull
   StorageNBTComponent storage(@NotNull final Key storage);

   interface Builder extends NBTComponentBuilder {
      @Contract("_ -> this")
      @NotNull
      StorageNBTComponent.Builder storage(@NotNull final Key storage);
   }
}
