package com.viaversion.viaversion.libs.kyori.adventure.text.renderer;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

public interface ComponentRenderer {
   @NotNull
   Component render(@NotNull final Component component, @NotNull final Object context);

   default ComponentRenderer mapContext(final Function transformer) {
      return (component, ctx) -> {
         return this.render(component, transformer.apply(ctx));
      };
   }
}
