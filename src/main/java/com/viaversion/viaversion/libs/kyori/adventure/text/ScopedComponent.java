package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.util.MonkeyBars;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ScopedComponent extends Component {
   @NotNull
   Component children(@NotNull final List children);

   @NotNull
   Component style(@NotNull final Style style);

   @NotNull
   default Component style(@NotNull final Consumer style) {
      return Component.super.style(style);
   }

   @NotNull
   default Component style(@NotNull final Style.Builder style) {
      return Component.super.style(style);
   }

   @NotNull
   default Component mergeStyle(@NotNull final Component that) {
      return Component.super.mergeStyle(that);
   }

   @NotNull
   default Component mergeStyle(@NotNull final Component that, @NotNull final Style.Merge... merges) {
      return Component.super.mergeStyle(that, merges);
   }

   @NotNull
   default Component append(@NotNull final Component component) {
      if (component == Component.empty()) {
         return this;
      } else {
         List oldChildren = this.children();
         return this.children(MonkeyBars.addOne(oldChildren, component));
      }
   }

   @NotNull
   default Component append(@NotNull final ComponentLike component) {
      return Component.super.append(component);
   }

   @NotNull
   default Component append(@NotNull final ComponentBuilder builder) {
      return Component.super.append(builder);
   }

   @NotNull
   default Component mergeStyle(@NotNull final Component that, @NotNull final Set merges) {
      return Component.super.mergeStyle(that, merges);
   }

   @NotNull
   default Component color(@Nullable final TextColor color) {
      return Component.super.color(color);
   }

   @NotNull
   default Component colorIfAbsent(@Nullable final TextColor color) {
      return Component.super.colorIfAbsent(color);
   }

   @NotNull
   default Component decorate(@NotNull final TextDecoration decoration) {
      return Component.super.decorate(decoration);
   }

   @NotNull
   default Component decoration(@NotNull final TextDecoration decoration, final boolean flag) {
      return Component.super.decoration(decoration, flag);
   }

   @NotNull
   default Component decoration(@NotNull final TextDecoration decoration, @NotNull final TextDecoration.State state) {
      return Component.super.decoration(decoration, state);
   }

   @NotNull
   default Component clickEvent(@Nullable final ClickEvent event) {
      return Component.super.clickEvent(event);
   }

   @NotNull
   default Component hoverEvent(@Nullable final HoverEventSource event) {
      return Component.super.hoverEvent(event);
   }

   @NotNull
   default Component insertion(@Nullable final String insertion) {
      return Component.super.insertion(insertion);
   }
}
