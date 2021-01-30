package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.PrimitiveIterator.OfInt;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import org.jetbrains.annotations.NotNull;

public interface IntArrayBinaryTag extends ArrayBinaryTag, Iterable {
   // $FF: renamed from: of (int[]) com.viaversion.viaversion.libs.kyori.adventure.nbt.IntArrayBinaryTag
   @NotNull
   static IntArrayBinaryTag method_13(@NotNull final int... value) {
      return new IntArrayBinaryTagImpl(value);
   }

   @NotNull
   default BinaryTagType type() {
      return BinaryTagTypes.INT_ARRAY;
   }

   @NotNull
   int[] value();

   int size();

   int get(final int index);

   @NotNull
   OfInt iterator();

   @NotNull
   java.util.Spliterator.OfInt spliterator();

   @NotNull
   IntStream stream();

   void forEachInt(@NotNull final IntConsumer action);
}
