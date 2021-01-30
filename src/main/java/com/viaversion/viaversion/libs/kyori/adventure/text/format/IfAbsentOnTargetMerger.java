package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class IfAbsentOnTargetMerger implements Merger {
   static final IfAbsentOnTargetMerger INSTANCE = new IfAbsentOnTargetMerger();

   private IfAbsentOnTargetMerger() {
   }

   public void mergeColor(final StyleImpl.BuilderImpl target, @Nullable final TextColor color) {
      if (target.color == null) {
         target.color(color);
      }

   }

   public void mergeDecoration(final StyleImpl.BuilderImpl target, @NotNull final TextDecoration decoration, @NotNull final TextDecoration.State state) {
      target.decorationIfAbsent(decoration, state);
   }

   public void mergeClickEvent(final StyleImpl.BuilderImpl target, @Nullable final ClickEvent event) {
      if (target.clickEvent == null) {
         target.clickEvent(event);
      }

   }

   public void mergeHoverEvent(final StyleImpl.BuilderImpl target, @Nullable final HoverEvent event) {
      if (target.hoverEvent == null) {
         target.hoverEvent(event);
      }

   }

   public void mergeInsertion(final StyleImpl.BuilderImpl target, @Nullable final String insertion) {
      if (target.insertion == null) {
         target.insertion(insertion);
      }

   }

   public void mergeFont(final StyleImpl.BuilderImpl target, @Nullable final Key font) {
      if (target.font == null) {
         target.font(font);
      }

   }
}
