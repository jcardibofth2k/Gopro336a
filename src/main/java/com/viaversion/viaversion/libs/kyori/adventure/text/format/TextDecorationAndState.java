package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.stream.Stream;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface TextDecorationAndState extends Examinable, StyleBuilderApplicable {
   @NonNull
   TextDecoration decoration();

   @NonNull
   TextDecoration.State state();

   default void styleApply(@NonNull final Style.Builder style) {
      style.decoration(this.decoration(), this.state());
   }

   @NonNull
   default Stream examinableProperties() {
      return Stream.of(ExaminableProperty.method_53("decoration", this.decoration()), ExaminableProperty.method_53("state", this.state()));
   }
}
