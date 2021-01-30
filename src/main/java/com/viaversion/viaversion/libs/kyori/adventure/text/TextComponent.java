package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import java.util.Arrays;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface TextComponent extends BuildableComponent, ScopedComponent {
   @NotNull
   static TextComponent ofChildren(@NotNull final ComponentLike... components) {
      return (TextComponent)(components.length == 0 ? Component.empty() : new TextComponentImpl(Arrays.asList(components), Style.empty(), ""));
   }

   @NotNull
   String content();

   @Contract(
      pure = true
   )
   @NotNull
   TextComponent content(@NotNull final String content);

   public interface Builder extends ComponentBuilder {
      @NotNull
      String content();

      @Contract("_ -> this")
      @NotNull
      TextComponent.Builder content(@NotNull final String content);
   }
}
