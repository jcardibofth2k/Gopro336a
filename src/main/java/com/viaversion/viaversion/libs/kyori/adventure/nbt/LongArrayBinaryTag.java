package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.PrimitiveIterator.OfLong;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;
import org.jetbrains.annotations.NotNull;

public interface LongArrayBinaryTag extends ArrayBinaryTag, Iterable {
   // $FF: renamed from: of (long[]) com.viaversion.viaversion.libs.kyori.adventure.nbt.LongArrayBinaryTag
   @NotNull
   static LongArrayBinaryTag method_11(@NotNull final long... value) {
      return new LongArrayBinaryTagImpl(value);
   }

   @NotNull
   default BinaryTagType type() {
      return BinaryTagTypes.LONG_ARRAY;
   }

   @NotNull
   long[] value();

   int size();

   long get(final int index);

   @NotNull
   OfLong iterator();

   @NotNull
   java.util.Spliterator.OfLong spliterator();

   @NotNull
   LongStream stream();

   void forEachLong(@NotNull final LongConsumer action);
}
