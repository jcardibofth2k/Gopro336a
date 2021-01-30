package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class SelectorComponentImpl extends AbstractComponent implements SelectorComponent {
   private final String pattern;
   @Nullable
   private final Component separator;

   SelectorComponentImpl(@NotNull final List children, @NotNull final Style style, @NotNull final String pattern, @Nullable final ComponentLike separator) {
      super(children, style);
      this.pattern = pattern;
      this.separator = ComponentLike.unbox(separator);
   }

   @NotNull
   public String pattern() {
      return this.pattern;
   }

   @NotNull
   public SelectorComponent pattern(@NotNull final String pattern) {
      return Objects.equals(this.pattern, pattern) ? this : new SelectorComponentImpl(this.children, this.style, (String)Objects.requireNonNull(pattern, "pattern"), this.separator);
   }

   @Nullable
   public Component separator() {
      return this.separator;
   }

   @NotNull
   public SelectorComponent separator(@Nullable final ComponentLike separator) {
      return new SelectorComponentImpl(this.children, this.style, this.pattern, separator);
   }

   @NotNull
   public SelectorComponent children(@NotNull final List children) {
      return new SelectorComponentImpl(children, this.style, this.pattern, this.separator);
   }

   @NotNull
   public SelectorComponent style(@NotNull final Style style) {
      return new SelectorComponentImpl(this.children, style, this.pattern, this.separator);
   }

   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof SelectorComponent)) {
         return false;
      } else if (!super.equals(other)) {
         return false;
      } else {
         SelectorComponent that = (SelectorComponent)other;
         return Objects.equals(this.pattern, that.pattern()) && Objects.equals(this.separator, that.separator());
      }
   }

   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + this.pattern.hashCode();
      result = 31 * result + Objects.hashCode(this.separator);
      return result;
   }

   @NotNull
   protected Stream examinablePropertiesWithoutChildren() {
      return Stream.concat(Stream.of(ExaminableProperty.method_54("pattern", this.pattern), ExaminableProperty.method_53("separator", this.separator)), super.examinablePropertiesWithoutChildren());
   }

   @NotNull
   public SelectorComponent.Builder toBuilder() {
      return new SelectorComponentImpl.BuilderImpl(this);
   }

   static final class BuilderImpl extends AbstractComponentBuilder implements SelectorComponent.Builder {
      @Nullable
      private String pattern;
      @Nullable
      private Component separator;

      BuilderImpl() {
      }

      BuilderImpl(@NotNull final SelectorComponent component) {
         super(component);
         this.pattern = component.pattern();
      }

      @NotNull
      public SelectorComponent.Builder pattern(@NotNull final String pattern) {
         this.pattern = pattern;
         return this;
      }

      @NotNull
      public SelectorComponent.Builder separator(@Nullable final ComponentLike separator) {
         this.separator = ComponentLike.unbox(separator);
         return this;
      }

      @NotNull
      public SelectorComponent build() {
         if (this.pattern == null) {
            throw new IllegalStateException("pattern must be set");
         } else {
            return new SelectorComponentImpl(this.children, this.buildStyle(), this.pattern, this.separator);
         }
      }
   }
}
