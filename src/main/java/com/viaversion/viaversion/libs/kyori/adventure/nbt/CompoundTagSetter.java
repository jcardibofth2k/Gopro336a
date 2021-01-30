package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.Map;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CompoundTagSetter {
   @NotNull
   Object put(@NotNull final String key, @NotNull final BinaryTag tag);

   @NotNull
   Object put(@NotNull final CompoundBinaryTag tag);

   @NotNull
   Object put(@NotNull final Map tags);

   @NotNull
   default Object remove(@NotNull final String key) {
      return this.remove(key, (Consumer)null);
   }

   @NotNull
   Object remove(@NotNull final String key, @Nullable final Consumer removed);

   @NotNull
   default Object putBoolean(@NotNull final String key, final boolean value) {
      return this.put(key, value ? ByteBinaryTag.ONE : ByteBinaryTag.ZERO);
   }

   @NotNull
   default Object putByte(@NotNull final String key, final byte value) {
      return this.put(key, ByteBinaryTag.method_14(value));
   }

   @NotNull
   default Object putShort(@NotNull final String key, final short value) {
      return this.put(key, ShortBinaryTag.method_19(value));
   }

   @NotNull
   default Object putInt(@NotNull final String key, final int value) {
      return this.put(key, IntBinaryTag.method_15(value));
   }

   @NotNull
   default Object putLong(@NotNull final String key, final long value) {
      return this.put(key, LongBinaryTag.method_17(value));
   }

   @NotNull
   default Object putFloat(@NotNull final String key, final float value) {
      return this.put(key, FloatBinaryTag.method_18(value));
   }

   @NotNull
   default Object putDouble(@NotNull final String key, final double value) {
      return this.put(key, DoubleBinaryTag.method_16(value));
   }

   @NotNull
   default Object putByteArray(@NotNull final String key, @NotNull final byte[] value) {
      return this.put(key, ByteArrayBinaryTag.method_12(value));
   }

   @NotNull
   default Object putString(@NotNull final String key, @NotNull final String value) {
      return this.put(key, StringBinaryTag.method_10(value));
   }

   @NotNull
   default Object putIntArray(@NotNull final String key, @NotNull final int[] value) {
      return this.put(key, IntArrayBinaryTag.method_13(value));
   }

   @NotNull
   default Object putLongArray(@NotNull final String key, @NotNull final long[] value) {
      return this.put(key, LongArrayBinaryTag.method_11(value));
   }
}
