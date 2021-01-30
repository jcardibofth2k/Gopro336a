package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

abstract class NBTComponentImpl extends AbstractComponent implements NBTComponent {
   static final boolean INTERPRET_DEFAULT = false;
   final String nbtPath;
   final boolean interpret;
   @Nullable
   final Component separator;

   NBTComponentImpl(@NotNull final List children, @NotNull final Style style, final String nbtPath, final boolean interpret, @Nullable final ComponentLike separator) {
      super(children, style);
      this.nbtPath = nbtPath;
      this.interpret = interpret;
      this.separator = ComponentLike.unbox(separator);
   }

   @NotNull
   public String nbtPath() {
      return this.nbtPath;
   }

   public boolean interpret() {
      return this.interpret;
   }

   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof NBTComponent)) {
         return false;
      } else if (!super.equals(other)) {
         return false;
      } else {
         NBTComponent that = (NBTComponent)other;
         return Objects.equals(this.nbtPath, that.nbtPath()) && this.interpret == that.interpret() && Objects.equals(this.separator, that.separator());
      }
   }

   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + this.nbtPath.hashCode();
      result = 31 * result + Boolean.hashCode(this.interpret);
      result = 31 * result + Objects.hashCode(this.separator);
      return result;
   }

   @NotNull
   protected Stream examinablePropertiesWithoutChildren() {
      return Stream.concat(Stream.of(ExaminableProperty.method_54("nbtPath", this.nbtPath), ExaminableProperty.method_55("interpret", this.interpret), ExaminableProperty.method_53("separator", this.separator)), super.examinablePropertiesWithoutChildren());
   }

   abstract static class BuilderImpl extends AbstractComponentBuilder implements NBTComponentBuilder {
      @Nullable
      protected String nbtPath;
      protected boolean interpret = false;
      @Nullable
      protected Component separator;

      BuilderImpl() {
      }

      BuilderImpl(@NotNull final NBTComponent component) {
         super(component);
         this.nbtPath = component.nbtPath();
         this.interpret = component.interpret();
      }

      @NotNull
      public NBTComponentBuilder nbtPath(@NotNull final String nbtPath) {
         this.nbtPath = nbtPath;
         return this;
      }

      @NotNull
      public NBTComponentBuilder interpret(final boolean interpret) {
         this.interpret = interpret;
         return this;
      }

      @NotNull
      public NBTComponentBuilder separator(@Nullable final ComponentLike separator) {
         this.separator = ComponentLike.unbox(separator);
         return this;
      }
   }
}
