package com.viaversion.viaversion.libs.kyori.adventure.text.event;

import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface HoverEventSource {
   @Nullable
   static HoverEvent unbox(@Nullable final HoverEventSource source) {
      return source != null ? source.asHoverEvent() : null;
   }

   @NotNull
   default HoverEvent asHoverEvent() {
      return this.asHoverEvent(UnaryOperator.identity());
   }

   @NotNull
   HoverEvent asHoverEvent(@NotNull final UnaryOperator op);
}
