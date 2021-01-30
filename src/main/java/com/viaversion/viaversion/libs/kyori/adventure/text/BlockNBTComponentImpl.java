package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent.WorldPos.Coordinate;
import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent.WorldPos.Coordinate.Type;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class BlockNBTComponentImpl extends NBTComponentImpl implements BlockNBTComponent {
   private final BlockNBTComponent.Pos pos;

   BlockNBTComponentImpl(@NotNull final List children, @NotNull final Style style, final String nbtPath, final boolean interpret, @Nullable final ComponentLike separator, @NotNull final BlockNBTComponent.Pos pos) {
      super(children, style, nbtPath, interpret, separator);
      this.pos = pos;
   }

   @NotNull
   public BlockNBTComponent nbtPath(@NotNull final String nbtPath) {
      return Objects.equals(this.nbtPath, nbtPath) ? this : new BlockNBTComponentImpl(this.children, this.style, nbtPath, this.interpret, this.separator, this.pos);
   }

   @NotNull
   public BlockNBTComponent interpret(final boolean interpret) {
      return this.interpret == interpret ? this : new BlockNBTComponentImpl(this.children, this.style, this.nbtPath, interpret, this.separator, this.pos);
   }

   @Nullable
   public Component separator() {
      return this.separator;
   }

   @NotNull
   public BlockNBTComponent separator(@Nullable final ComponentLike separator) {
      return new BlockNBTComponentImpl(this.children, this.style, this.nbtPath, this.interpret, separator, this.pos);
   }

   @NotNull
   public BlockNBTComponent.Pos pos() {
      return this.pos;
   }

   @NotNull
   public BlockNBTComponent pos(@NotNull final BlockNBTComponent.Pos pos) {
      return new BlockNBTComponentImpl(this.children, this.style, this.nbtPath, this.interpret, this.separator, pos);
   }

   @NotNull
   public BlockNBTComponent children(@NotNull final List children) {
      return new BlockNBTComponentImpl(children, this.style, this.nbtPath, this.interpret, this.separator, this.pos);
   }

   @NotNull
   public BlockNBTComponent style(@NotNull final Style style) {
      return new BlockNBTComponentImpl(this.children, style, this.nbtPath, this.interpret, this.separator, this.pos);
   }

   public boolean equals(@Nullable final Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof BlockNBTComponent)) {
         return false;
      } else if (!super.equals(other)) {
         return false;
      } else {
         BlockNBTComponent that = (BlockNBTComponent)other;
         return Objects.equals(this.pos, that.pos());
      }
   }

   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + this.pos.hashCode();
      return result;
   }

   @NotNull
   protected Stream examinablePropertiesWithoutChildren() {
      return Stream.concat(Stream.of(ExaminableProperty.method_53("pos", this.pos)), super.examinablePropertiesWithoutChildren());
   }

   @NotNull
   public BlockNBTComponent.Builder toBuilder() {
      return new BlockNBTComponentImpl.BuilderImpl(this);
   }

   static final class BuilderImpl extends NBTComponentImpl.BuilderImpl implements BlockNBTComponent.Builder {
      @Nullable
      private BlockNBTComponent.Pos pos;

      BuilderImpl() {
      }

      BuilderImpl(@NotNull final BlockNBTComponent component) {
         super(component);
         this.pos = component.pos();
      }

      @NotNull
      public BlockNBTComponent.Builder pos(@NotNull final BlockNBTComponent.Pos pos) {
         this.pos = pos;
         return this;
      }

      @NotNull
      public BlockNBTComponent build() {
         if (this.nbtPath == null) {
            throw new IllegalStateException("nbt path must be set");
         } else if (this.pos == null) {
            throw new IllegalStateException("pos must be set");
         } else {
            return new BlockNBTComponentImpl(this.children, this.buildStyle(), this.nbtPath, this.interpret, this.separator, this.pos);
         }
      }
   }

   static final class Tokens {
      static final Pattern LOCAL_PATTERN = Pattern.compile("^\\^(\\d+(\\.\\d+)?) \\^(\\d+(\\.\\d+)?) \\^(\\d+(\\.\\d+)?)$");
      static final Pattern WORLD_PATTERN = Pattern.compile("^(~?)(\\d+) (~?)(\\d+) (~?)(\\d+)$");
      static final String LOCAL_SYMBOL = "^";
      static final String RELATIVE_SYMBOL = "~";
      static final String ABSOLUTE_SYMBOL = "";

      private Tokens() {
      }

      static Coordinate deserializeCoordinate(final String prefix, final String value) {
         int i = Integer.parseInt(value);
         if (prefix.equals("")) {
            return Coordinate.absolute(i);
         } else if (prefix.equals("~")) {
            return Coordinate.relative(i);
         } else {
            throw new AssertionError();
         }
      }

      static String serializeLocal(final double value) {
         return "^" + value;
      }

      static String serializeCoordinate(final Coordinate coordinate) {
         return (coordinate.type() == Type.RELATIVE ? "~" : "") + coordinate.value();
      }
   }
}
