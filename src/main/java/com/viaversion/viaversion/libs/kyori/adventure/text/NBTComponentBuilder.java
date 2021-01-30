package com.viaversion.viaversion.libs.kyori.adventure.text;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface NBTComponentBuilder extends ComponentBuilder {
   @Contract("_ -> this")
   @NotNull
   NBTComponentBuilder nbtPath(@NotNull final String nbtPath);

   @Contract("_ -> this")
   @NotNull
   NBTComponentBuilder interpret(final boolean interpret);

   @Contract("_ -> this")
   @NotNull
   NBTComponentBuilder separator(@Nullable final ComponentLike separator);
}
