package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class KeybindComponentImpl extends AbstractComponent implements KeybindComponent {
   private final String keybind;

   KeybindComponentImpl(@NotNull final List children, @NotNull final Style style, @NotNull final String keybind) {
      super(children, style);
      this.keybind = Objects.requireNonNull(keybind, "keybind");
   }

   @NotNull
   public String keybind() {
      return this.keybind;
   }

   @NotNull
   public KeybindComponent keybind(@NotNull final String keybind) {
      return Objects.equals(this.keybind, keybind) ? this : new KeybindComponentImpl(this.children, this.style, Objects.requireNonNull(keybind, "keybind"));
   }

   @NotNull
   public KeybindComponent children(@NotNull final List children) {
      return new KeybindComponentImpl(children, this.style, this.keybind);
   }

   @NotNull
   public KeybindComponent style(@NotNull final Style style) {
      return new KeybindComponentImpl(this.children, style, this.keybind);
   }

   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof KeybindComponent)) {
         return false;
      } else if (!super.equals(other)) {
         return false;
      } else {
         KeybindComponent that = (KeybindComponent)other;
         return Objects.equals(this.keybind, that.keybind());
      }
   }

   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + this.keybind.hashCode();
      return result;
   }

   @NotNull
   protected Stream examinablePropertiesWithoutChildren() {
      return Stream.concat(Stream.of(ExaminableProperty.method_54("keybind", this.keybind)), super.examinablePropertiesWithoutChildren());
   }

   @NotNull
   public KeybindComponent.Builder toBuilder() {
      return new KeybindComponentImpl.BuilderImpl(this);
   }

   static final class BuilderImpl extends AbstractComponentBuilder implements KeybindComponent.Builder {
      @Nullable
      private String keybind;

      BuilderImpl() {
      }

      BuilderImpl(@NotNull final KeybindComponent component) {
         super(component);
         this.keybind = component.keybind();
      }

      @NotNull
      public KeybindComponent.Builder keybind(@NotNull final String keybind) {
         this.keybind = keybind;
         return this;
      }

      @NotNull
      public KeybindComponent build() {
         if (this.keybind == null) {
            throw new IllegalStateException("keybind must be set");
         } else {
            return new KeybindComponentImpl(this.children, this.buildStyle(), this.keybind);
         }
      }
   }
}
