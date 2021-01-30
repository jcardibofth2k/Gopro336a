package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class ScoreComponentImpl extends AbstractComponent implements ScoreComponent {
   private final String name;
   private final String objective;
   /** @deprecated */
   @Deprecated
   @Nullable
   private final String value;

   ScoreComponentImpl(@NotNull final List children, @NotNull final Style style, @NotNull final String name, @NotNull final String objective, @Nullable final String value) {
      super(children, style);
      this.name = name;
      this.objective = objective;
      this.value = value;
   }

   @NotNull
   public String name() {
      return this.name;
   }

   @NotNull
   public ScoreComponent name(@NotNull final String name) {
      return Objects.equals(this.name, name) ? this : new ScoreComponentImpl(this.children, this.style, (String)Objects.requireNonNull(name, "name"), this.objective, this.value);
   }

   @NotNull
   public String objective() {
      return this.objective;
   }

   @NotNull
   public ScoreComponent objective(@NotNull final String objective) {
      return Objects.equals(this.objective, objective) ? this : new ScoreComponentImpl(this.children, this.style, this.name, (String)Objects.requireNonNull(objective, "objective"), this.value);
   }

   /** @deprecated */
   @Deprecated
   @Nullable
   public String value() {
      return this.value;
   }

   /** @deprecated */
   @Deprecated
   @NotNull
   public ScoreComponent value(@Nullable final String value) {
      return Objects.equals(this.value, value) ? this : new ScoreComponentImpl(this.children, this.style, this.name, this.objective, value);
   }

   @NotNull
   public ScoreComponent children(@NotNull final List children) {
      return new ScoreComponentImpl(children, this.style, this.name, this.objective, this.value);
   }

   @NotNull
   public ScoreComponent style(@NotNull final Style style) {
      return new ScoreComponentImpl(this.children, style, this.name, this.objective, this.value);
   }

   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof ScoreComponent)) {
         return false;
      } else if (!super.equals(other)) {
         return false;
      } else {
         ScoreComponent that = (ScoreComponent)other;
         return Objects.equals(this.name, that.name()) && Objects.equals(this.objective, that.objective()) && Objects.equals(this.value, that.value());
      }
   }

   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + this.name.hashCode();
      result = 31 * result + this.objective.hashCode();
      result = 31 * result + Objects.hashCode(this.value);
      return result;
   }

   @NotNull
   protected Stream examinablePropertiesWithoutChildren() {
      return Stream.concat(Stream.of(ExaminableProperty.method_54("name", this.name), ExaminableProperty.method_54("objective", this.objective), ExaminableProperty.method_54("value", this.value)), super.examinablePropertiesWithoutChildren());
   }

   @NotNull
   public ScoreComponent.Builder toBuilder() {
      return new ScoreComponentImpl.BuilderImpl(this);
   }

   static final class BuilderImpl extends AbstractComponentBuilder implements ScoreComponent.Builder {
      @Nullable
      private String name;
      @Nullable
      private String objective;
      @Nullable
      private String value;

      BuilderImpl() {
      }

      BuilderImpl(@NotNull final ScoreComponent component) {
         super(component);
         this.name = component.name();
         this.objective = component.objective();
         this.value = component.value();
      }

      @NotNull
      public ScoreComponent.Builder name(@NotNull final String name) {
         this.name = name;
         return this;
      }

      @NotNull
      public ScoreComponent.Builder objective(@NotNull final String objective) {
         this.objective = objective;
         return this;
      }

      /** @deprecated */
      @Deprecated
      @NotNull
      public ScoreComponent.Builder value(@Nullable final String value) {
         this.value = value;
         return this;
      }

      @NotNull
      public ScoreComponent build() {
         if (this.name == null) {
            throw new IllegalStateException("name must be set");
         } else if (this.objective == null) {
            throw new IllegalStateException("objective must be set");
         } else {
            return new ScoreComponentImpl(this.children, this.buildStyle(), this.name, this.objective, this.value);
         }
      }
   }
}
