package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ComponentBuilder extends Buildable.Builder, ComponentBuilderApplicable, ComponentLike {
   @Contract("_ -> this")
   @NotNull
   ComponentBuilder append(@NotNull final Component component);

   @Contract("_ -> this")
   @NotNull
   default ComponentBuilder append(@NotNull final ComponentLike component) {
      return this.append(component.asComponent());
   }

   @Contract("_ -> this")
   @NotNull
   default ComponentBuilder append(@NotNull final ComponentBuilder builder) {
      return this.append((Component)builder.build());
   }

   @Contract("_ -> this")
   @NotNull
   ComponentBuilder append(@NotNull final Component... components);

   @Contract("_ -> this")
   @NotNull
   ComponentBuilder append(@NotNull final ComponentLike... components);

   @Contract("_ -> this")
   @NotNull
   ComponentBuilder append(@NotNull final Iterable components);

   @Contract("_ -> this")
   @NotNull
   default ComponentBuilder apply(@NotNull final Consumer consumer) {
      consumer.accept(this);
      return this;
   }

   @Contract("_ -> this")
   @NotNull
   ComponentBuilder applyDeep(@NotNull final Consumer action);

   @Contract("_ -> this")
   @NotNull
   ComponentBuilder mapChildren(@NotNull final Function function);

   @Contract("_ -> this")
   @NotNull
   ComponentBuilder mapChildrenDeep(@NotNull final Function function);

   @NotNull
   List children();

   @Contract("_ -> this")
   @NotNull
   ComponentBuilder style(@NotNull final Style style);

   @Contract("_ -> this")
   @NotNull
   ComponentBuilder style(@NotNull final Consumer consumer);

   @Contract("_ -> this")
   @NotNull
   ComponentBuilder font(@Nullable final Key font);

   @Contract("_ -> this")
   @NotNull
   ComponentBuilder color(@Nullable final TextColor color);

   @Contract("_ -> this")
   @NotNull
   ComponentBuilder colorIfAbsent(@Nullable final TextColor color);

   @Contract("_, _ -> this")
   @NotNull
   default ComponentBuilder decorations(@NotNull final Set decorations, final boolean flag) {
      TextDecoration.State state = TextDecoration.State.byBoolean(flag);
      decorations.forEach((decoration) -> {
         this.decoration(decoration, state);
      });
      return this;
   }

   @Contract("_ -> this")
   @NotNull
   default ComponentBuilder decorate(@NotNull final TextDecoration decoration) {
      return this.decoration(decoration, TextDecoration.State.TRUE);
   }

   @Contract("_ -> this")
   @NotNull
   default ComponentBuilder decorate(@NotNull final TextDecoration... decorations) {
      int i = 0;

      for(int length = decorations.length; i < length; ++i) {
         this.decorate(decorations[i]);
      }

      return this;
   }

   @Contract("_, _ -> this")
   @NotNull
   default ComponentBuilder decoration(@NotNull final TextDecoration decoration, final boolean flag) {
      return this.decoration(decoration, TextDecoration.State.byBoolean(flag));
   }

   @Contract("_, _ -> this")
   @NotNull
   ComponentBuilder decoration(@NotNull final TextDecoration decoration, @NotNull final TextDecoration.State state);

   @Contract("_ -> this")
   @NotNull
   ComponentBuilder clickEvent(@Nullable final ClickEvent event);

   @Contract("_ -> this")
   @NotNull
   ComponentBuilder hoverEvent(@Nullable final HoverEventSource source);

   @Contract("_ -> this")
   @NotNull
   ComponentBuilder insertion(@Nullable final String insertion);

   @Contract("_ -> this")
   @NotNull
   default ComponentBuilder mergeStyle(@NotNull final Component that) {
      return this.mergeStyle(that, Style.Merge.all());
   }

   @Contract("_, _ -> this")
   @NotNull
   default ComponentBuilder mergeStyle(@NotNull final Component that, @NotNull final Style.Merge... merges) {
      return this.mergeStyle(that, Style.Merge.method_437(merges));
   }

   @Contract("_, _ -> this")
   @NotNull
   ComponentBuilder mergeStyle(@NotNull final Component that, @NotNull final Set merges);

   @NotNull
   ComponentBuilder resetStyle();

   @NotNull
   BuildableComponent build();

   @Contract("_ -> this")
   @NotNull
   default ComponentBuilder applicableApply(@NotNull final ComponentBuilderApplicable applicable) {
      applicable.componentBuilderApply(this);
      return this;
   }

   default void componentBuilderApply(@NotNull final ComponentBuilder component) {
      component.append(this);
   }

   @NotNull
   default Component asComponent() {
      return this.build();
   }
}
