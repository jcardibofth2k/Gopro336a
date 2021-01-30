package com.viaversion.viaversion.libs.kyori.adventure.text.flattener;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface FlattenerListener {
   default void pushStyle(@NotNull final Style style) {
   }

   void component(@NotNull final String text);

   default void popStyle(@NotNull final Style style) {
   }
}
