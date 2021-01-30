package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class AlwaysMerger implements Merger {
   static final AlwaysMerger INSTANCE = new AlwaysMerger();

   private AlwaysMerger() {
   }

   public void mergeColor(final StyleImpl.BuilderImpl target, @Nullable final TextColor color) {
      target.color(color);
   }

   public void mergeDecoration(final StyleImpl.BuilderImpl target, @NotNull final TextDecoration decoration, @NotNull final TextDecoration.State state) {
      target.decoration(decoration, state);
   }

   public void mergeClickEvent(final StyleImpl.BuilderImpl target, @Nullable final ClickEvent event) {
      target.clickEvent(event);
   }

   public void mergeHoverEvent(final StyleImpl.BuilderImpl target, @Nullable final HoverEvent event) {
      target.hoverEvent(event);
   }

   public void mergeInsertion(final StyleImpl.BuilderImpl target, @Nullable final String insertion) {
      target.insertion(insertion);
   }

   public void mergeFont(final StyleImpl.BuilderImpl target, @Nullable final Key font) {
      target.font(font);
   }
}
