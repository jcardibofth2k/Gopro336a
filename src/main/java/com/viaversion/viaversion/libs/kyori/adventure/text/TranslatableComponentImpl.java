package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class TranslatableComponentImpl extends AbstractComponent implements TranslatableComponent {
   private final String key;
   private final List args;

   TranslatableComponentImpl(@NotNull final List children, @NotNull final Style style, @NotNull final String key, @NotNull final ComponentLike[] args) {
      this(children, style, key, Arrays.asList(args));
   }

   TranslatableComponentImpl(@NotNull final List children, @NotNull final Style style, @NotNull final String key, @NotNull final List args) {
      super(children, style);
      this.key = Objects.requireNonNull(key, "key");
      this.args = ComponentLike.asComponents(args);
   }

   @NotNull
   public String key() {
      return this.key;
   }

   @NotNull
   public TranslatableComponent key(@NotNull final String key) {
      return Objects.equals(this.key, key) ? this : new TranslatableComponentImpl(this.children, this.style, Objects.requireNonNull(key, "key"), this.args);
   }

   @NotNull
   public List args() {
      return this.args;
   }

   @NotNull
   public TranslatableComponent args(@NotNull final ComponentLike... args) {
      return new TranslatableComponentImpl(this.children, this.style, this.key, args);
   }

   @NotNull
   public TranslatableComponent args(@NotNull final List args) {
      return new TranslatableComponentImpl(this.children, this.style, this.key, args);
   }

   @NotNull
   public TranslatableComponent children(@NotNull final List children) {
      return new TranslatableComponentImpl(children, this.style, this.key, this.args);
   }

   @NotNull
   public TranslatableComponent style(@NotNull final Style style) {
      return new TranslatableComponentImpl(this.children, style, this.key, this.args);
   }

   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof TranslatableComponent)) {
         return false;
      } else if (!super.equals(other)) {
         return false;
      } else {
         TranslatableComponent that = (TranslatableComponent)other;
         return Objects.equals(this.key, that.key()) && Objects.equals(this.args, that.args());
      }
   }

   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + this.key.hashCode();
      result = 31 * result + this.args.hashCode();
      return result;
   }

   @NotNull
   protected Stream examinablePropertiesWithoutChildren() {
      return Stream.concat(Stream.of(ExaminableProperty.method_54("key", this.key), ExaminableProperty.method_53("args", this.args)), super.examinablePropertiesWithoutChildren());
   }

   @NotNull
   public TranslatableComponent.Builder toBuilder() {
      return new TranslatableComponentImpl.BuilderImpl(this);
   }

   static final class BuilderImpl extends AbstractComponentBuilder implements TranslatableComponent.Builder {
      @Nullable
      private String key;
      private List args = Collections.emptyList();

      BuilderImpl() {
      }

      BuilderImpl(@NotNull final TranslatableComponent component) {
         super(component);
         this.key = component.key();
         this.args = component.args();
      }

      @NotNull
      public TranslatableComponent.Builder key(@NotNull final String key) {
         this.key = key;
         return this;
      }

      @NotNull
      public TranslatableComponent.Builder args(@NotNull final ComponentBuilder arg) {
         return this.args(Collections.singletonList(arg.build()));
      }

      @NotNull
      public TranslatableComponent.Builder args(@NotNull final ComponentBuilder... args) {
         return args.length == 0 ? this.args(Collections.emptyList()) : this.args(Stream.of(args).map(ComponentBuilder::build).collect(Collectors.toList()));
      }

      @NotNull
      public TranslatableComponent.Builder args(@NotNull final Component arg) {
         return this.args(Collections.singletonList(arg));
      }

      @NotNull
      public TranslatableComponent.Builder args(@NotNull final ComponentLike... args) {
         return args.length == 0 ? this.args(Collections.emptyList()) : this.args(Arrays.asList(args));
      }

      @NotNull
      public TranslatableComponent.Builder args(@NotNull final List args) {
         this.args = ComponentLike.asComponents(args);
         return this;
      }

      @NotNull
      public TranslatableComponentImpl build() {
         if (this.key == null) {
            throw new IllegalStateException("key must be set");
         } else {
            return new TranslatableComponentImpl(this.children, this.buildStyle(), this.key, this.args);
         }
      }
   }
}
