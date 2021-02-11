package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent.WorldPos.Coordinate;
import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponentImpl.LocalPosImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponentImpl.WorldPosImpl;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import java.util.regex.Matcher;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface BlockNBTComponent extends NBTComponent, ScopedComponent {
   @NotNull
   BlockNBTComponent.Pos pos();

   @Contract(
      pure = true
   )
   @NotNull
   BlockNBTComponent pos(@NotNull final BlockNBTComponent.Pos pos);

   @Contract(
      pure = true
   )
   @NotNull
   default BlockNBTComponent localPos(final double left, final double up, final double forwards) {
      return this.pos(BlockNBTComponent.LocalPos.method_4(left, up, forwards));
   }

   @Contract(
      pure = true
   )
   @NotNull
   default BlockNBTComponent worldPos(@NotNull final Coordinate x, @NotNull final Coordinate y, @NotNull final Coordinate z) {
      return this.pos(BlockNBTComponent.WorldPos.method_6(x, y, z));
   }

   @Contract(
      pure = true
   )
   @NotNull
   default BlockNBTComponent absoluteWorldPos(final int x, final int y, final int z) {
      return this.worldPos(Coordinate.absolute(x), Coordinate.absolute(y), Coordinate.absolute(z));
   }

   @Contract(
      pure = true
   )
   @NotNull
   default BlockNBTComponent relativeWorldPos(final int x, final int y, final int z) {
      return this.worldPos(Coordinate.relative(x), Coordinate.relative(y), Coordinate.relative(z));
   }

   interface LocalPos extends BlockNBTComponent.Pos {
      // $FF: renamed from: of (double, double, double) com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent$LocalPos
      @NotNull
      static BlockNBTComponent.LocalPos method_4(final double left, final double up, final double forwards) {
         return new LocalPosImpl(left, up, forwards);
      }

      double left();

      // $FF: renamed from: up () double
      double method_5();

      double forwards();
   }

   interface Pos extends Examinable {
      @NotNull
      static BlockNBTComponent.Pos fromString(@NotNull final String input) throws IllegalArgumentException {
         Matcher localMatch = BlockNBTComponentImpl.Tokens.LOCAL_PATTERN.matcher(input);
         if (localMatch.matches()) {
            return BlockNBTComponent.LocalPos.method_4(Double.parseDouble(localMatch.group(1)), Double.parseDouble(localMatch.group(3)), Double.parseDouble(localMatch.group(5)));
         } else {
            Matcher worldMatch = BlockNBTComponentImpl.Tokens.WORLD_PATTERN.matcher(input);
            if (worldMatch.matches()) {
               return BlockNBTComponent.WorldPos.method_6(BlockNBTComponentImpl.Tokens.deserializeCoordinate(worldMatch.group(1), worldMatch.group(2)), BlockNBTComponentImpl.Tokens.deserializeCoordinate(worldMatch.group(3), worldMatch.group(4)), BlockNBTComponentImpl.Tokens.deserializeCoordinate(worldMatch.group(5), worldMatch.group(6)));
            } else {
               throw new IllegalArgumentException("Cannot convert position specification '" + input + "' into a position");
            }
         }
      }

      @NotNull
      String asString();
   }

   interface WorldPos extends BlockNBTComponent.Pos {
      // $FF: renamed from: of (com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent.WorldPos.Coordinate, com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent.WorldPos.Coordinate, com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent.WorldPos.Coordinate) com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent$WorldPos
      @NotNull
      static BlockNBTComponent.WorldPos method_6(@NotNull final Coordinate x, @NotNull final Coordinate y, @NotNull final Coordinate z) {
         return new WorldPosImpl(x, y, z);
      }

      // $FF: renamed from: x () com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent.WorldPos.Coordinate
      @NotNull
      Coordinate method_7();

      // $FF: renamed from: y () com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent.WorldPos.Coordinate
      @NotNull
      Coordinate method_8();

      // $FF: renamed from: z () com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent.WorldPos.Coordinate
      @NotNull
      Coordinate method_9();
   }

   interface Builder extends NBTComponentBuilder {
      @Contract("_ -> this")
      @NotNull
      BlockNBTComponent.Builder pos(@NotNull final BlockNBTComponent.Pos pos);

      @Contract("_, _, _ -> this")
      @NotNull
      default BlockNBTComponent.Builder localPos(final double left, final double up, final double forwards) {
         return this.pos(BlockNBTComponent.LocalPos.method_4(left, up, forwards));
      }

      @Contract("_, _, _ -> this")
      @NotNull
      default BlockNBTComponent.Builder worldPos(@NotNull final Coordinate x, @NotNull final Coordinate y, @NotNull final Coordinate z) {
         return this.pos(BlockNBTComponent.WorldPos.method_6(x, y, z));
      }

      @Contract("_, _, _ -> this")
      @NotNull
      default BlockNBTComponent.Builder absoluteWorldPos(final int x, final int y, final int z) {
         return this.worldPos(Coordinate.absolute(x), Coordinate.absolute(y), Coordinate.absolute(z));
      }

      @Contract("_, _, _ -> this")
      @NotNull
      default BlockNBTComponent.Builder relativeWorldPos(final int x, final int y, final int z) {
         return this.worldPos(Coordinate.relative(x), Coordinate.relative(y), Coordinate.relative(z));
      }
   }
}
