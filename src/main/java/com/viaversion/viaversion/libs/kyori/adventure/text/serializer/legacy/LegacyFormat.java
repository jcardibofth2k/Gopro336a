package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.NamedTextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class LegacyFormat implements Examinable {
   static final LegacyFormat RESET = new LegacyFormat(true);
   @Nullable
   private final NamedTextColor color;
   @Nullable
   private final TextDecoration decoration;
   private final boolean reset;

   LegacyFormat(@Nullable final NamedTextColor color) {
      this.color = color;
      this.decoration = null;
      this.reset = false;
   }

   LegacyFormat(@Nullable final TextDecoration decoration) {
      this.color = null;
      this.decoration = decoration;
      this.reset = false;
   }

   private LegacyFormat(final boolean reset) {
      this.color = null;
      this.decoration = null;
      this.reset = reset;
   }

   @Nullable
   public TextColor color() {
      return this.color;
   }

   @Nullable
   public TextDecoration decoration() {
      return this.decoration;
   }

   public boolean reset() {
      return this.reset;
   }

   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      } else if (other != null && this.getClass() == other.getClass()) {
         LegacyFormat that = (LegacyFormat)other;
         return this.color == that.color && this.decoration == that.decoration && this.reset == that.reset;
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = Objects.hashCode(this.color);
      result = 31 * result + Objects.hashCode(this.decoration);
      result = 31 * result + Boolean.hashCode(this.reset);
      return result;
   }

   @NotNull
   public Stream examinableProperties() {
      return Stream.of(ExaminableProperty.method_53("color", this.color), ExaminableProperty.method_53("decoration", this.decoration), ExaminableProperty.method_55("reset", this.reset));
   }
}
