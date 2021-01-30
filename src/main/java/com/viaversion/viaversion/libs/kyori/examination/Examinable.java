package com.viaversion.viaversion.libs.kyori.examination;

import java.util.stream.Stream;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface Examinable {
   @NonNull
   default String examinableName() {
      return this.getClass().getSimpleName();
   }

   @NonNull
   default Stream examinableProperties() {
      return Stream.empty();
   }

   @NonNull
   default Object examine(@NonNull final Examiner examiner) {
      return examiner.examine(this);
   }
}
