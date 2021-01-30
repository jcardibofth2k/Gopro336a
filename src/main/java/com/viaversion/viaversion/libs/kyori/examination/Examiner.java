package com.viaversion.viaversion.libs.kyori.examination;

import java.util.stream.Stream;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface Examiner {
   @NonNull
   default Object examine(@NonNull final Examinable examinable) {
      return this.examine(examinable.examinableName(), examinable.examinableProperties());
   }

   @NonNull
   Object examine(@NonNull final String name, @NonNull final Stream properties);

   @NonNull
   Object examine(@Nullable final Object value);

   @NonNull
   Object examine(final boolean value);

   @NonNull
   Object examine(@Nullable final boolean[] values);

   @NonNull
   Object examine(final byte value);

   @NonNull
   Object examine(@Nullable final byte[] values);

   @NonNull
   Object examine(final char value);

   @NonNull
   Object examine(@Nullable final char[] values);

   @NonNull
   Object examine(final double value);

   @NonNull
   Object examine(@Nullable final double[] values);

   @NonNull
   Object examine(final float value);

   @NonNull
   Object examine(@Nullable final float[] values);

   @NonNull
   Object examine(final int value);

   @NonNull
   Object examine(@Nullable final int[] values);

   @NonNull
   Object examine(final long value);

   @NonNull
   Object examine(@Nullable final long[] values);

   @NonNull
   Object examine(final short value);

   @NonNull
   Object examine(@Nullable final short[] values);

   @NonNull
   Object examine(@Nullable final String value);
}
