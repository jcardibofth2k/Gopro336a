package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.util.Nag;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

final class TextComponentImpl extends AbstractComponent implements TextComponent {
   private static final boolean WARN_WHEN_LEGACY_FORMATTING_DETECTED = Boolean.getBoolean(String.join(".", "net", "kyori", "adventure", "text", "warnWhenLegacyFormattingDetected"));
   @VisibleForTesting
   static final char SECTION_CHAR = '§';
   static final TextComponent EMPTY = createDirect("");
   static final TextComponent NEWLINE = createDirect("\n");
   static final TextComponent SPACE = createDirect(" ");
   private final String content;

   @NotNull
   private static TextComponent createDirect(@NotNull final String content) {
      return new TextComponentImpl(Collections.emptyList(), Style.empty(), content);
   }

   TextComponentImpl(@NotNull final List children, @NotNull final Style style, @NotNull final String content) {
      super(children, style);
      this.content = content;
      if (WARN_WHEN_LEGACY_FORMATTING_DETECTED) {
         LegacyFormattingDetected nag = this.warnWhenLegacyFormattingDetected();
         if (nag != null) {
            Nag.print(nag);
         }
      }

   }

   @VisibleForTesting
   @Nullable
   final LegacyFormattingDetected warnWhenLegacyFormattingDetected() {
      return this.content.indexOf(167) != -1 ? new LegacyFormattingDetected(this) : null;
   }

   @NotNull
   public String content() {
      return this.content;
   }

   @NotNull
   public TextComponent content(@NotNull final String content) {
      return Objects.equals(this.content, content) ? this : new TextComponentImpl(this.children, this.style, (String)Objects.requireNonNull(content, "content"));
   }

   @NotNull
   public TextComponent children(@NotNull final List children) {
      return new TextComponentImpl(children, this.style, this.content);
   }

   @NotNull
   public TextComponent style(@NotNull final Style style) {
      return new TextComponentImpl(this.children, style, this.content);
   }

   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof TextComponentImpl)) {
         return false;
      } else if (!super.equals(other)) {
         return false;
      } else {
         TextComponentImpl that = (TextComponentImpl)other;
         return Objects.equals(this.content, that.content);
      }
   }

   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + this.content.hashCode();
      return result;
   }

   @NotNull
   protected Stream examinablePropertiesWithoutChildren() {
      return Stream.concat(Stream.of(ExaminableProperty.method_54("content", this.content)), super.examinablePropertiesWithoutChildren());
   }

   @NotNull
   public TextComponent.Builder toBuilder() {
      return new TextComponentImpl.BuilderImpl(this);
   }

   static final class BuilderImpl extends AbstractComponentBuilder implements TextComponent.Builder {
      private String content = "";

      BuilderImpl() {
      }

      BuilderImpl(@NotNull final TextComponent component) {
         super(component);
         this.content = component.content();
      }

      @NotNull
      public TextComponent.Builder content(@NotNull final String content) {
         this.content = (String)Objects.requireNonNull(content, "content");
         return this;
      }

      @NotNull
      public String content() {
         return this.content;
      }

      @NotNull
      public TextComponent build() {
         return (TextComponent)(this.isEmpty() ? Component.empty() : new TextComponentImpl(this.children, this.buildStyle(), this.content));
      }

      private boolean isEmpty() {
         return this.content.isEmpty() && this.children.isEmpty() && !this.hasStyle();
      }
   }
}
